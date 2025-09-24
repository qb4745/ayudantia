# Borrador - Capítulo 10: Procesamiento Concurrente

Este capítulo explora la concurrencia: cómo hacer que un programa realice múltiples tareas "al mismo tiempo" para mejorar el rendimiento y la capacidad de respuesta.

---

## 1. El "Porqué" de la Concurrencia

Los procesadores modernos tienen múltiples núcleos, pero un programa estándar solo usa uno. La concurrencia nos permite aprovechar todo el poder del hardware.

*   **Mejorar el Rendimiento:** Tareas computacionalmente intensivas (como procesar imágenes o grandes volúmenes de datos) se pueden dividir y ejecutar en paralelo en diferentes núcleos, terminando mucho más rápido.
*   **Mejorar la Capacidad de Respuesta:** En una aplicación de escritorio, si una tarea larga (como descargar un archivo) se ejecuta en el hilo principal, toda la interfaz de usuario se "congela". Ejecutarla en un hilo separado mantiene la aplicación responsiva.

*   **Analogía:** Imagina que eres un **chef cocinando una cena**.
    *   **Sin concurrencia (secuencial):** Pones a cocer la pasta. Esperas 10 minutos. Cuando está lista, empiezas a preparar la salsa. Esperas 15 minutos. Cuando está lista, la sirves.
    *   **Con concurrencia:** Pones a cocer la pasta. **Mientras** la pasta se cuece, preparas la salsa al mismo tiempo. Ambas tareas terminan mucho antes.

---

## 2. Hilos (`Threads`): Los "Trabajadores" de un Programa

*   **¿QUÉ ES?** Un hilo es la unidad de ejecución más pequeña que un sistema operativo puede gestionar. Cada programa Java se ejecuta en al menos un hilo, llamado el "hilo principal". Podemos crear hilos adicionales para que actúen como "trabajadores" independientes.

*   **¿CÓMO SE CREA (Forma Clásica)?**
    1.  Define una tarea implementando la interfaz `Runnable`.
    2.  Crea un objeto `Thread` con ese `Runnable`.
    3.  Llama a `thread.start()`.

    ```java
    // 1. La tarea a realizar
    Runnable tarea = () -> {
        System.out.println("Estoy trabajando en otro hilo...");
    };

    // 2. El trabajador que ejecutará la tarea
    Thread trabajador = new Thread(tarea);

    // 3. Iniciar el trabajo
    trabajador.start(); // NO llames a run() directamente
    ```

---

## 3. El Problema: Condiciones de Carrera (`Race Conditions`)

Cuando múltiples hilos acceden y modifican **datos compartidos** al mismo tiempo, pueden ocurrir resultados impredecibles y desastrosos.

*   **¿QUÉ ES?** Una condición de carrera ocurre cuando el resultado de una operación depende del orden incontrolado en que los hilos acceden a un recurso compartido.

*   **Analogía:** Imagina un **contador de clics compartido**.
    *   Hilo A lee el valor actual del contador (es `9`).
    *   **¡Justo en ese momento, el sistema operativo pausa al Hilo A y le da el turno al Hilo B!**
    *   Hilo B lee el valor actual (sigue siendo `9`), le suma 1 (ahora es `10`) y lo guarda.
    *   **El sistema operativo le devuelve el turno al Hilo A.**
    *   Hilo A, que todavía piensa que el valor es `9`, le suma 1 (ahora es `10`) y lo guarda.
    *   **Resultado:** Se hicieron dos incrementos, pero el valor final es `10` en lugar de `11`. Se ha perdido un clic.

---

## 4. La Solución Clásica: Bloqueos (`Locks`) y Sincronización

Para prevenir las condiciones de carrera, debemos asegurar que solo **un hilo a la vez** pueda acceder a la sección crítica del código.

*   **La palabra clave `synchronized`:** Es la forma más simple de crear un "bloqueo" (lock). Cuando un hilo entra en un método o bloque `synchronized`, adquiere un bloqueo. Ningún otro hilo puede entrar hasta que el primero haya terminado y liberado el bloqueo.

    ```java
    public class ContadorSeguro {
        private int cuenta = 0;

        // Solo un hilo puede ejecutar este método a la vez.
        public synchronized void incrementar() {
            this.cuenta++;
        }
    }
    ```
*   **Problema:** Manejar bloqueos manualmente es complejo y propenso a errores como los "deadlocks" (dos hilos esperándose mutuamente en un abrazo mortal).

---

## 5. La Solución Moderna: El `Executor Framework`

Manejar hilos manualmente es de bajo nivel y arriesgado. La forma moderna es abstraer la gestión de hilos usando "Ejecutores".

*   **¿QUÉ ES?** Un `ExecutorService` es un objeto de alto nivel que gestiona un **conjunto de hilos (thread pool)**. Tú le envías tareas (`Runnable` o `Callable`) y él se encarga de asignarlas a un hilo disponible para su ejecución.

*   **¿POR QUÉ ES MEJOR?**
    1.  **Abstracción:** No te preocupas por crear o destruir hilos.
    2.  **Eficiencia:** Reutiliza los hilos del "pool", evitando el alto costo de crear un hilo nuevo para cada tarea.
    3.  **Gestión de Resultados:** Puede manejar tareas que devuelven un resultado (usando `Callable` y `Future`).

    ```java
    // 1. Crear un "pool" con un número fijo de hilos trabajadores
    ExecutorService executor = Executors.newFixedThreadPool(4);

    // 2. Enviar tareas para su ejecución
    Runnable tarea1 = () -> System.out.println("Tarea 1 ejecutándose...");
    Runnable tarea2 = () -> System.out.println("Tarea 2 ejecutándose...");

    executor.submit(tarea1);
    executor.submit(tarea2);

    // 3. Apagar el servicio cuando ya no se necesite
    executor.shutdown();
    ```
*   **Analogía:** Un `ExecutorService` es como el **gerente de un restaurante de comida rápida**.
    *   Los **clientes** (tu código) llegan y dejan sus **pedidos** (tareas `Runnable`).
    *   El **gerente** (`ExecutorService`) no contrata un cocinero nuevo para cada pedido. Tiene un **equipo de cocineros** (`thread pool`).
    *   El gerente toma los pedidos y los asigna a los cocineros que se van desocupando. Es mucho más eficiente.
