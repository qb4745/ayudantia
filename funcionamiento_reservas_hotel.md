# Análisis del Funcionamiento: Sistema de Reservas con ArrayList

¡Hola! Como tu ayudante, vamos a desglosar juntos la aplicación de reservas de hotel en su versión actual. El objetivo es que entiendas no solo **qué** hace el código, sino **por qué** la elección de usar `ArrayList` para todo tiene consecuencias importantes en el rendimiento.

Usaremos la arquitectura de 3 capas como nuestro mapa para este recorrido.

---

## El Mapa de la Aplicación: Las 3 Capas

Nuestra aplicación está organizada en tres "departamentos". Esta separación es una práctica profesional clave. En esta versión, todas las colecciones son `ArrayList`.

```mermaid
graph TD
    subgraph "Usuario"
        U(👤 Usuario)
    end

    subgraph "Capa de Interfaz (main)"
        A["`GestionReservas.java`<br/>(El Recepcionista)"]
    end

    subgraph "Capa de Lógica (service)"
        B["`ServicioReservas.java`<br/>(El Cerebro / Gerente)<br/><b>Usa ArrayList para todo</b>"]
    end

    subgraph "Capa de Datos (model)"
        C["`Cliente.java`, `Hotel.java`, etc.<br/>(Las Fichas / Archivos)"]
    end

    U -- Interactúa con --> A
    A -- Llama a --> B
    B -- Utiliza y modifica --> C
```

1.  **`model` (El Almacén de Datos):** Contiene las plantillas de nuestros datos.
2.  **`service` (El Cerebro de Operaciones):** Contiene la lógica de negocio.
3.  **`main` (La Interfaz de Usuario):** Habla con el usuario.

---

### Capa 1: Las Fichas de Información (Paquete `model`)

El uso de `ArrayList` en este paquete es una **excelente decisión**.

*   **`Hotel.java`**: Tiene un `ArrayList<Habitacion>`. Correcto, porque un hotel tiene una lista de habitaciones.
*   **`Cliente.java`**: Tiene un `ArrayList<Reserva>`. Correcto, para mantener un historial ordenado de las pocas reservas de un cliente.
*   **`Reserva.java`**: Conecta a las otras clases.

---

### Capa 2: El Cerebro de la Operación (Paquete `service`)

Aquí es donde se ha realizado el cambio más importante y donde debemos centrar nuestro análisis. El `service` ahora también usa `ArrayList`.

*   **`ServicioReservas.java`**: Este es el cerebro. Su característica distintiva ahora es cómo almacena los datos principales:

    ```java
    // Antes usaba HashMap, ahora usa ArrayList
    private final List<Hotel> hoteles = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();
    ```
    **¿Qué significa esto en la práctica?** Que para cualquier operación que requiera encontrar un hotel o un cliente por su ID, el servicio debe **recorrer la lista completa** desde el principio.

    **Así es la búsqueda ahora (La Realidad de Nuestro Código):**

    ```java
    // Esta es la implementación ACTUAL en nuestro servicio.
    // Para encontrar un hotel, debemos recorrer toda la lista.
    public Hotel findHotelById(String id) {
        for (Hotel hotel : hoteles) {
            if (hotel.getIdHotel().equalsIgnoreCase(id)) {
                return hotel; // Encontrado, pero podría tardar mucho.
            }
        }
        return null; // No encontrado.
    }
    ```
    *   **La Consecuencia:** Esta aproximación es simple de entender, pero tiene un grave problema de **rendimiento a gran escala**. Si la lista de hoteles crece, las búsquedas se vuelven cada vez más lentas.

---

### Capa 3: El Recepcionista Amable (Paquete `main`)

Esta capa no necesita saber *cómo* busca los datos el servicio, solo le pide que lo haga.

*   **`GestionReservas.java`**: Sigue siendo nuestro recepcionista. Llama a los métodos del servicio como `servicio.findHotelById(id)`, sin ser consciente de que esa llamada puede ser muy lenta.

---

## Flujo Completo: Un Análisis Detallado (Versión `ArrayList`)

### El Camino Feliz (pero potencialmente lento): Creando una Reserva

Veamos cómo la nueva estructura afecta el flujo de creación de una reserva.

1.  **El Usuario Actúa:** El usuario elige la opción `6`.
2.  **El Recepcionista (`GestionReservas`) Atiende:** Pide al usuario los datos (ID cliente, ID hotel, etc.).
3.  **Llamada al Gerente (`ServicioReservas`):** Delega la responsabilidad: `servicio.createReserva("C1", "H101", "202", 5);`.
4.  **El Cerebro (`ServicioReservas`) Procesa:**
    *   **Paso A: Validar (Búsqueda Lenta).** Para encontrar el cliente "C1", el servicio inicia un **bucle `for`** que recorre el `ArrayList` de clientes desde el principio. Luego, para encontrar el hotel "H101", inicia **otro bucle `for`** sobre el `ArrayList` de hoteles. Si las listas son grandes, estos dos pasos consumen la mayor parte del tiempo.
    *   **Paso B: Ejecutar.** Si finalmente encuentra ambos, crea la `Reserva` y la añade al `ArrayList` del cliente (esta operación, `add()`, es rápida).
    *   **Paso C: Informar.** Devuelve el mensaje de éxito: `"RESERVA_CREADA_EXITOSAMENTE"`.
5.  **El Recepcionista Comunica el Resultado:** Muestra el mensaje de éxito. El usuario no sabe que, por dentro, el sistema tuvo que trabajar mucho.

### ¿Y si algo sale mal? El Flujo de un Error

Imaginemos que el usuario introduce un ID de cliente que no existe, "C99".

1.  **El Usuario y el Recepcionista:** Los primeros pasos son idénticos.
2.  **El Cerebro (`ServicioReservas`) Detecta el Problema:**
    *   **Paso A: Validar Cliente (Búsqueda Lenta y Exhaustiva).** El servicio inicia un bucle `for` para buscar "C99". Recorre **toda la lista de clientes**, desde el primero hasta el último. Al llegar al final y no encontrarlo, el método `findClienteById` devuelve `null`.
    *   **Paso B: Detener y Notificar.** El servicio detecta que el cliente es `null` y devuelve el código de error: `"ERROR_CLIENTE_NO_ENCONTRADO"`.
3.  **El Recepcionista (`GestionReservas`) Informa el Error:** Recibe el código y muestra el mensaje "Error: Cliente no encontrado.".

Este flujo de error sigue funcionando, pero es importante entender que para confirmar que un cliente **no existe**, el sistema está obligado a revisar **toda la lista**, lo cual es el peor escenario en términos de rendimiento.
