# Guía de Estudio: Uso Práctico de Colecciones en Java (`ArrayList` vs. `HashMap`)
## Caso de Estudio: Sistema de Gestión de Reservas

¡Hola a todos! Hoy vamos a hablar de una de las herramientas más poderosas de Java: las **Colecciones**. Vamos a analizar un programa real para entender **cuándo y por qué** un buen programador elige `ArrayList` o `HashMap`, y veremos las **acciones (métodos)** más comunes que podemos realizar con ellas.

---

## Parte 1: El Escenario - Una Aplicación Organizada

Para nuestro análisis, usaremos una aplicación de reservas de hotel. Esta aplicación está bien diseñada y separada en 3 capas. Solo necesitamos saber dónde viven nuestras colecciones:

```mermaid
graph TD
    A["`main` (Interfaz)"] --> B["`service` (Lógica)"]
    B --> C["`model` (Datos)"]

    subgraph "`service`"
        subgraph "`ServicioReservas.java`"
            B_hash1["HashMap<String, Hotel>"]
            B_hash2["HashMap<String, Cliente>"]
        end
    end

    subgraph "`model`"
        subgraph "`Hotel.java`"
            C_arr1["ArrayList<Habitacion>"]
        end
        subgraph "`Cliente.java`"
            C_arr2["ArrayList<Reserva>"]
        end
    end
```
Hoy, nuestros protagonistas viven en las capas de **Lógica (`service`)** y **Datos (`model`)**.

---

## Parte 2: Los Protagonistas - `ArrayList` vs. `HashMap`

### El Trade-Off Clave: Tabla Comparativa

| Característica      | `ArrayList` (Lista de Compras) | `HashMap` (Agenda de Contactos) |
| ------------------- | ------------------------------ | ------------------------------- |
| **Orden**           | ✅ **Garantizado** (orden de inserción) | ❌ **No garantizado** (el orden puede cambiar) |
| **Búsqueda**        | 🐢 **Lenta** (recorre la lista) | 🚀 **Instantánea** (usa una clave) |
| **Acceso a Elementos**| Por **índice numérico** (posición `0, 1, 2...`) | Por **clave única** (ej: "H101") |
| **Duplicados**      | Permite elementos duplicados | No permite **claves** duplicadas |
| **Caso de Uso Ideal** | Cuando el **orden importa** y sueles recorrer toda la lista. | Cuando la **velocidad de búsqueda es crítica** y tienes un identificador único. |

### 2.1 `ArrayList`: La Lista de Compras
*   **¿Qué es?** Una lista ordenada de elementos. Mantiene el orden exacto en que se añaden las cosas.
*   **Debilidad:** Para encontrar algo, debes recorrer la lista (como buscar leche en una lista de 50 ítems). Esto es lento en listas muy grandes.
*   **Uso en Nuestro Proyecto:** La clase `Cliente` tiene un `ArrayList<Reserva>` para guardar el historial de reservas de ese cliente.
*   **¿Por qué?** Porque las reservas de un cliente tienen un orden cronológico natural. Nos interesa mantener ese orden y, generalmente, vamos a querer ver *todas* sus reservas, no solo una específica. La velocidad de búsqueda no es la prioridad aquí.

#### **Métodos Comunes de `ArrayList` (Acciones con nuestra lista)**
*   **`add(elemento)`**
    *   **Analogía:** "Añadir un nuevo ítem al final de la lista de compras."
    *   **Uso:** Cuando un cliente hace una nueva reserva, la añadimos a su historial con `cliente.getReservas().add(nuevaReserva);`.
*   **`get(indice)`**
    *   **Analogía:** "Dame el ítem en la **posición número 2** de la lista (los índices empiezan en 0)."
    *   **Uso:** Para ver la primera reserva que hizo un cliente, usaríamos su índice: `cliente.getReservas().get(0);`.
*   **`remove(indice)`**
    *   **Analogía:** "Tachar el ítem que está en la **posición número X** de la lista."
    *   **Uso:** Si implementáramos la cancelación, buscaríamos el **índice** de la reserva y la eliminaríamos con `remove(indice)`.
*   **`size()`**
    *   **Analogía:** "¿Cuántos ítems tengo apuntados en la lista?"
    *   **Uso:** Para saber cuántas reservas tiene un cliente: `cliente.getReservas().size();`. Lo usamos para comprobar si un cliente tiene o no reservas.

### 2.2 `HashMap`: La Agenda de Contactos
*   **¿Qué es?** Una colección de pares "clave-valor". No garantiza ningún orden. Piensa en una agenda donde cada nombre (clave) tiene un número de teléfono (valor).
*   **Fortaleza:** Búsqueda casi instantánea si tienes la clave. No tienes que recorrer toda la agenda para encontrar el número de "Juan", vas directo a la "J".
*   **Uso en Nuestro Proyecto:** La clase `ServicioReservas` usa `HashMap<String, Hotel>` para guardar todos los hoteles del sistema, usando el ID del hotel como clave.
*   **¿Por qué?** Porque la velocidad de búsqueda por ID es crítica para el negocio. Cuando un cliente quiere reservar en el hotel "H101", no podemos permitirnos recorrer una lista de 10,000 hoteles. Necesitamos encontrarlo *ya*.

#### **Métodos Comunes de `HashMap` (Acciones con nuestra agenda)**
*   **`put(clave, valor)`**
    *   **Analogía:** "Añadir un nuevo contacto a la agenda: el nombre es 'Juan Pérez' (clave), y su número es '555-1234' (valor)."
    *   **Uso:** Para registrar un hotel nuevo en el sistema: `hoteles.put("H101", nuevoHotelObjeto);`.
*   **`get(clave)`**
    *   **Analogía:** "Buscar en la agenda el número de 'Juan Pérez'."
    *   **Uso:** La operación más importante del servicio. `hoteles.get("H101");` nos devuelve el objeto Hotel completo al instante.
*   **`remove(clave)`**
    *   **Analogía:** "Borrar el contacto de 'Juan Pérez' de la agenda."
    *   **Uso:** Si un hotel cierra, lo eliminaríamos del sistema con `hoteles.remove("H101");`.
*   **`size()`**
    *   **Analogía:** "¿Cuántos contactos tengo en total en la agenda?"
    *   **Uso:** Para saber cuántos hoteles hay registrados en el sistema: `hoteles.size();`.
*   **`containsKey(clave)`**
    *   **Analogía:** "Antes de añadir a 'Juan Pérez', revisar rápidamente si ya existe un contacto con ese nombre para no duplicarlo."
    *   **Uso:** Crucial para las reglas de negocio. Antes de registrar un hotel, verificamos que no exista ya uno con ese ID: `if (hoteles.containsKey("H101")) { ... lanzar error ... }`.

---

## Parte 3: Las Colecciones en Acción - El Flujo de una Reserva

Veamos cómo los métodos de ambas colecciones trabajan juntos cuando un cliente reserva:
1.  **`GestionReservas`** (la interfaz) pide los datos y llama a `servicio.createReserva("C1", "H101", ...)`
2.  **`ServicioReservas`** (la lógica) recibe la petición.
3.  Usa **`containsKey("C1")`** en el `HashMap` de clientes para verificar que el cliente exista.
4.  Usa **`get("H101")`** en el `HashMap` de hoteles para obtener el objeto `Hotel` al instante.
5.  Si todo es válido, crea un objeto `nuevaReserva`.
6.  Obtiene el objeto `Cliente` con **`get("C1")`** y luego usa **`add(nuevaReserva)`** en el `ArrayList` de reservas que está *dentro* de ese cliente.
7.  ¡Misión cumplida! El servicio usa la **velocidad del `HashMap`** para encontrar las cosas, y el modelo de datos usa el **orden del `ArrayList`** para guardar el historial. ¡Cada uno haciendo lo que mejor sabe hacer!

---

## ¡Cuidado! Errores Comunes que Encontrarás

Conocer estas colecciones también implica conocer sus trampas más comunes. ¡Estar prevenido te ahorrará horas de debugging!

*   **`NullPointerException` con `HashMap`:**
    *   **¿Qué pasa?** Ocurre si intentas usar un objeto que es `null`. El error más típico es llamar a `mapa.get("clave_que_no_existe")`, que devuelve `null`, y luego intentar usar el resultado.
    *   **Ejemplo:** `Hotel hotel = hoteles.get("H999"); hotel.getNombre();` // ¡CRASH! `hotel` es `null`.
    *   **Prevención:** Siempre comprueba si el resultado de `get()` es `null` antes de usarlo: `if (hotel != null) { ... }`.

*   **`IndexOutOfBoundsException` con `ArrayList`:**
    *   **¿Qué pasa?** Ocurre cuando intentas acceder a un índice que no existe. Si una lista tiene 3 elementos (índices 0, 1, 2), intentar acceder a `lista.get(3)` causará este error.
    *   **Ejemplo:** `reservas.get(reservas.size());` // ¡CRASH! El último índice válido es `size() - 1`.
    *   **Prevención:** Asegúrate de que el índice al que accedes esté siempre entre `0` y `lista.size() - 1`.

---

## Parte 4: Preguntas para Reflexionar (¡Discusión!)

1.  **Imagina que necesitamos cancelar la reserva más reciente de un cliente.** ¿Qué métodos de `ArrayList` usarías y en qué orden? (Pista: `size()` y `remove()`).
2.  **¿Qué método de `HashMap` es el más importante para la regla de negocio "no se pueden registrar dos hoteles con el mismo ID"?**
3.  **Nuevo Requisito:** "Mostrar todos los nombres de los hoteles registrados". El `HashMap` no tiene un orden. ¿Qué harías para obtener todos los hoteles del `HashMap` y luego mostrarlos ordenados alfabéticamente? (Pista: `values()` y `Collections.sort()`).
