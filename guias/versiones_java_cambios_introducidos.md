# Guía de Estudio: Los Grandes Cambios en Java (Versiones 8 y Posteriores)

¡Hola! Desde 2014, Java ha evolucionado a un ritmo mucho más rápido. Ya no esperamos años por nuevas características. Esta guía resume los cambios más transformadores que se han introducido desde la versión 8, que marcó un antes y un después en la historia del lenguaje.

---

## Java 8 (2014): La Revolución Funcional

Java 8 es posiblemente la actualización más importante en la historia de Java. Introdujo conceptos de programación funcional que cambiaron para siempre cómo escribimos código.

### 1. Expresiones Lambda (`->`)
*   **¿Qué es?** Una sintaxis corta y anónima para escribir funciones.
*   **¿Por qué es importante?** Redujo drásticamente el "boilerplate" (código repetitivo) de las clases anónimas. Hizo que el código fuera más conciso y expresivo.
*   **Antes (Clase Anónima):**
    ```java
    new Thread(new Runnable() {
        public void run() {
            System.out.println("Hola desde un hilo!");
        }
    }).start();
    ```
*   **Después (Lambda):**
    ```java
    new Thread(() -> System.out.println("Hola desde un hilo!")).start();
    ```

### 2. API de Streams
*   **¿Qué es?** Una nueva forma declarativa de procesar colecciones de datos.
*   **¿Por qué es importante?** Permitió escribir código de manipulación de datos mucho más legible y fluido, encadenando operaciones como `filter`, `map` y `collect`. También simplificó enormemente el procesamiento en paralelo (`.parallelStream()`).
*   **Ejemplo:**
    ```java
    List<String> nombresFiltrados = listaNombres.stream()
        .filter(nombre -> nombre.startsWith("A"))
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    ```

### 3. Nueva API de Fecha y Hora (`java.time`)
*   **¿Qué es?** Un reemplazo completo para las antiguas y problemáticas clases `Date` y `Calendar`.
*   **¿Por qué es importante?** Introdujo clases inmutables y mucho más intuitivas como `LocalDate`, `LocalTime` y `ZonedDateTime`, resolviendo décadas de problemas y confusiones en el manejo de fechas.

### 4. Métodos `default` en Interfaces
*   **¿Qué es?** La capacidad de añadir métodos con implementación a las interfaces.
*   **¿Por qué es importante?** Permitió que las interfaces evolucionaran sin romper el código existente. Fue clave para añadir métodos como `forEach` a la interfaz `Collection`.

---

## Java 9 (2017): La Modularidad

### El Sistema de Módulos de la Plataforma Java (JPMS)
*   **¿Qué es?** La capacidad de dividir una aplicación grande en "módulos" más pequeños, cada uno con dependencias explícitas.
*   **¿Por qué es importante?** Permitió crear aplicaciones más ligeras y mantenibles, mejorando la encapsulación y la seguridad al evitar que el código acceda a partes internas de una librería que no debería. Es la respuesta de Java a los problemas del "infierno de los JARs".

---

## Java 10 (2018): Conveniencia para el Desarrollador

### Inferencia de Tipos de Variables Locales (`var`)
*   **¿Qué es?** La capacidad de declarar variables locales sin escribir explícitamente su tipo, dejando que el compilador lo infiera.
*   **¿Por qué es importante?** Reduce el código repetitivo y mejora la legibilidad, especialmente con tipos genéricos complejos.
*   **Antes:** `Map<String, List<Cliente>> clientesPorRegion = new HashMap<>();`
*   **Después:** `var clientesPorRegion = new HashMap<String, List<Cliente>>();`

---

## Java 11 (2018): Consolidación y un Nuevo `HttpClient`

Java 11 es una versión de **Soporte a Largo Plazo (LTS)**, lo que la convierte en un estándar de la industria durante muchos años.

### Nueva API `HttpClient`
*   **¿Qué es?** Un cliente HTTP moderno, flexible y fácil de usar que soporta HTTP/2 y WebSockets.
*   **¿Por qué es importante?** Reemplazó a la antigua y engorrosa clase `HttpURLConnection`. Simplificó enormemente la forma en que las aplicaciones Java se comunican con APIs y servicios web, soportando tanto operaciones sincrónicas como asíncronas.

---

## Java 12 y 13 (2019): Mejoras Incrementales

### Expresiones `switch` Mejoradas
*   **¿Qué es?** Una nueva sintaxis para `switch` que es más concisa, menos propensa a errores (no necesita `break`) y puede usarse como una expresión que devuelve un valor.
*   **¿Por qué es importante?** Modernizó una de las estructuras de control más antiguas de Java, haciéndola más segura y poderosa.
*   **Antes:**
    ```java
    int numLetras;
    switch (dia) {
        case LUNES:
        case VIERNES:
            numLetras = 5;
            break;
        // ... etc ...
    }
    ```
*   **Después:**
    ```java
    int numLetras = switch (dia) {
        case LUNES, VIERNES -> 5;
        case MARTES -> 6;
        // ... etc ...
        default -> 0;
    };
    ```

---

## Java 14 (2020): Simplificando los Datos

### `Records`
*   **¿Qué es?** Una nueva sintaxis ultra-compacta para crear clases que son simples "contenedores de datos" inmutables.
*   **¿Por qué es importante?** Eliminó la necesidad de escribir manualmente constructores, `getters`, `equals()`, `hashCode()` y `toString()` para clases de datos simples (POJOs/DTOs).
*   **Antes (decenas de líneas):**
    ```java
    public class Punto {
        private final int x;
        private final int y;
        // ... constructor, getters, equals, hashCode, toString ...
    }
    ```
*   **Después (una sola línea):**
    ```java
    public record Punto(int x, int y) {}
    ```

---

## Java 15 y 16 (2020-2021): Más Patrones y Datos

### `Sealed Classes` (Clases Selladas)
*   **¿Qué es?** Permite a una clase o interfaz restringir qué otras clases pueden heredar de ella o implementarla.
*   **¿Por qué es importante?** Da más control al diseñador de una API sobre su jerarquía de clases, lo que es especialmente útil en combinación con las expresiones `switch` para que el compilador pueda verificar que se han cubierto todos los casos posibles.

### `Pattern Matching` para `instanceof`
*   **¿Qué es?** Una sintaxis más inteligente para `instanceof` que realiza el casting automáticamente.
*   **Antes:**
    ```java
    if (obj instanceof String) {
        String s = (String) obj;
        System.out.println(s.toUpperCase());
    }
    ```
*   **Después:**
    ```java
    if (obj instanceof String s) {
        System.out.println(s.toUpperCase());
    }
    ```

---

## Java 17 (2021): El Nuevo Estándar LTS

Java 17 es la siguiente versión de **Soporte a Largo Plazo (LTS)** después de la 11. Consolida todas las características anteriores (Records, Sealed Classes, Pattern Matching) como finales y listas para producción a gran escala. Es el nuevo punto de referencia para las aplicaciones empresariales modernas.

---

## Java 18 (2022): Utilidades y Vistas Previas

### Servidor Web Simple (`jwebserver`)
*   **¿Qué es?** Una herramienta de línea de comandos para iniciar un servidor web estático mínimo.
*   **¿Por qué es importante?** No es para producción, sino una utilidad fantástica para desarrollo, prototipado y enseñanza. Permite levantar rápidamente un servidor para servir archivos locales sin necesidad de configurar un servidor completo como Tomcat o Nginx.

---

## Java 19 y 20 (2022-2023): El Futuro de la Concurrencia (En Vista Previa)

Estas versiones se centraron en incubar y refinar una de las características más esperadas de la historia reciente de Java: **Project Loom**.

### Hilos Virtuales (`Virtual Threads`)
*   **¿Qué es?** Una implementación de hilos extremadamente ligeros gestionados por la JVM, en lugar de directamente por el sistema operativo.
*   **¿Por qué es importante?** Es una revolución para la concurrencia. Permite escribir código de red de alto rendimiento en el estilo simple y bloqueante de "un hilo por petición", pero con la capacidad de manejar millones de tareas concurrentes. Busca simplificar drásticamente la programación concurrente sin sacrificar el rendimiento.

### Concurrencia Estructurada (`Structured Concurrency`)
*   **¿Qué es?** Un nuevo paradigma de API que trata las tareas concurrentes en diferentes hilos como una sola unidad de trabajo.
*   **¿Por qué es importante?** Simplifica el manejo de errores y la cancelación en flujos de trabajo complejos con múltiples hilos. Si una subtarea falla, el grupo entero puede ser cancelado de forma fiable.

---

## Java 21 (2023): La LTS Actual y la Revolución Concurrente

Java 21 es la **LTS más reciente** y es una de las actualizaciones más significativas desde la 8 y la 17. Trae como características finales las promesas de Project Loom.

### Hilos Virtuales (`Virtual Threads`) - Finalizado
*   La característica estrella. Los hilos virtuales están listos para producción, permitiendo a las aplicaciones manejar un número masivo de conexiones concurrentes con un código mucho más simple.

### `Pattern Matching` para `switch` - Finalizado
*   **¿Qué es?** La capacidad de usar `switch` no solo con valores, sino con patrones de tipos, incluyendo condiciones adicionales.
*   **¿Por qué es importante?** Hace que el código que maneja jerarquías de objetos sea mucho más seguro y expresivo, eliminando cadenas de `if-else-if` con `instanceof`.
*   **Ejemplo:**
    ```java
    Object obj = ...;
    String formatted = switch (obj) {
        case Integer i -> String.format("Es un entero: %d", i);
        case String s && s.length() > 5 -> String.format("Es un String largo: %s", s);
        case String s -> String.format("Es un String corto: %s", s);
        default -> "Es otra cosa";
    };
    ```

### Colecciones Secuenciadas (`Sequenced Collections`)
*   **¿Qué es?** Nuevas interfaces que unifican el acceso al primer y último elemento de colecciones que tienen un orden definido (como `List` y `LinkedHashSet`).
*   **¿Por qué es importante?** Simplifica el código al proporcionar una API común (`getFirst()`, `getLast()`, `reversed()`) para todas las colecciones ordenadas.

---

## Java 22 y Posteriores (El Futuro)

El desarrollo de Java continúa a gran velocidad, con proyectos como **Project Valhalla** (para mejorar el rendimiento de los objetos en memoria) y **Project Panama** (para mejorar la interoperabilidad con código nativo C/C++) que prometen seguir evolucionando el lenguaje y la plataforma.
