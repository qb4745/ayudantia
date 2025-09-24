# Borrador - Capítulo 5: Excepciones

Este capítulo aborda un tema crucial: ¿qué pasa cuando las cosas salen mal en nuestro programa? Aprenderemos a manejar errores de forma elegante y controlada usando el mecanismo de Excepciones de Java.

---

## 1. El Problema: El Flujo Feliz no es Suficiente

Hasta ahora, hemos escrito código que asume que todo funciona perfectamente (el "flujo feliz"). Pero, ¿qué pasa si intentamos leer un archivo que no existe? ¿O si intentamos dividir un número por cero? Sin un manejo de errores, estas situaciones harían que nuestro programa se detenga abruptamente (un "crash").

Las **Excepciones** son la solución de Java a este problema. Son un mecanismo para manejar errores en tiempo de ejecución de forma estructurada.

*   **Analogía:** Piensa en el proceso de cocinar siguiendo una receta. El "flujo feliz" es tener todos los ingredientes. Una **excepción** ocurre cuando abres la nevera y te das cuenta de que "no hay huevos". Tienes que detener el flujo normal de la receta y ejecutar un plan de contingencia: ir al supermercado.

---

## 2. El Bloque `try-catch`: El Plan de Contingencia

*   **¿QUÉ ES?** Es la estructura fundamental para manejar excepciones.
    *   El código que **podría** fallar se coloca dentro del bloque `try`.
    *   El código de "plan de contingencia" se coloca dentro del bloque `catch`.

*   **¿POR QUÉ EXISTE?** Para separar la lógica de negocio principal del código de manejo de errores. Esto hace que el programa sea mucho más limpio y legible.

*   **¿CUÁNDO LO USO?** Siempre que llames a un método que podría lanzar una excepción (como operaciones de red, de archivos, o conversiones de datos).

    ```java
    public void leerConfiguracion() {
        // Intenta ejecutar este bloque de código...
        try {
            // Este método podría lanzar una FileNotFoundException
            File archivo = new File("config.txt");
            Scanner lector = new Scanner(archivo);
            System.out.println("Archivo leído exitosamente.");
        }
        // ...pero si ocurre una FileNotFoundException, salta directamente aquí.
        catch (FileNotFoundException e) {
            System.out.println("¡Error! El archivo de configuración no existe.");
            // Aquí podríamos crear un archivo por defecto, por ejemplo.
        }
    }
    ```
    Si el archivo no existe, el programa no crashea. En su lugar, imprime un mensaje de error amigable y continúa.

---

## 3. Tipos de Excepciones: Comprobadas vs. No Comprobadas

Java clasifica las excepciones en dos categorías principales.

### a. Excepciones Comprobadas (`Checked Exceptions`)

*   **¿QUÉ SON?** Son errores que un programa bien escrito debería anticipar y de los que debería poder recuperarse. Son "problemas externos" previsibles.
*   **Ejemplos:** `FileNotFoundException` (el archivo no está), `SQLException` (la base de datos no responde).
*   **La Regla del Compilador:** Java te **obliga** a manejarlas. Si llamas a un método que declara que puede lanzar una excepción comprobada, debes rodearlo con un `try-catch` o declarar que tu propio método también la lanza (usando `throws`).
*   **Analogía:** Son como la advertencia "Suelo Mojado" en un pasillo. El cartel te **obliga** a tomar una precaución (caminar con cuidado). No puedes ignorarlo.

### b. Excepciones No Comprobadas (`Unchecked Exceptions`)

*   **¿QUÉ SON?** Son errores que casi siempre reflejan un **fallo en la lógica de programación** (un bug). No se espera que una aplicación en producción se recupere de ellos.
*   **Ejemplos:** `NullPointerException` (intentar usar un objeto que es `null`), `ArrayIndexOutOfBoundsException` (intentar acceder a un índice inválido de un array).
*   **La Regla del Compilador:** Java **no** te obliga a manejarlas. La expectativa es que corrijas el bug en tu código para que nunca ocurran.
*   **Analogía:** Son como intentar caminar a través de una pared. No pones un `try-catch` para "manejar" el choque; simplemente corriges tu camino (tu código) para no chocar contra la pared en primer lugar.

---

## 4. El Bloque `finally`: El Código que se Ejecuta Siempre

*   **¿QUÉ ES?** Un bloque opcional que se puede añadir después de un `try-catch`. El código dentro de `finally` se ejecutará **siempre**, sin importar si ocurrió una excepción o no.

*   **¿POR QUÉ EXISTE?** Para liberar recursos importantes (como cerrar archivos, conexiones de red o de bases de datos) y asegurar que no queden abiertos, incluso si ocurre un error.

*   **¿CUÁNDO LO USO?** Cuando necesitas garantizar que una acción de "limpieza" se realice.

    ```java
    Scanner lector = null;
    try {
        lector = new Scanner(new File("datos.txt"));
        // ... procesar el archivo ...
    } catch (FileNotFoundException e) {
        System.out.println("Error, el archivo no se encontró.");
    } finally {
        // Este bloque se ejecuta SIEMPRE.
        if (lector != null) {
            lector.close(); // Aseguramos que el lector se cierre.
        }
    }
    ```

---

## 5. Lanzar Excepciones: Señalando un Problema

*   **¿QUÉ ES?** En lugar de solo capturar excepciones, también podemos crearlas y "lanzarlas" nosotros mismos usando la palabra clave `throw`.

*   **¿POR QUÉ EXISTE?** Para permitir que nuestros propios métodos señalen que algo ha salido mal y que no pueden continuar con su trabajo normal.

*   **¿CUÁNDO LO USO?** Cuando un método recibe parámetros inválidos o se encuentra en un estado en el que no puede cumplir su contrato.

    ```java
    public void retirarDinero(double monto) {
        if (monto < 0) {
            // Lanzamos una excepción para señalar un argumento ilegal.
            throw new IllegalArgumentException("El monto a retirar no puede ser negativo.");
        }
        if (monto > this.saldo) {
            throw new IllegalStateException("No hay saldo suficiente.");
        }
        this.saldo -= monto;
    }
    ```
