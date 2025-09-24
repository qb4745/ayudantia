# Borrador - Capítulo 1: Estructuras Fundamentales de Programación

Esta es una síntesis pedagógica de los conceptos clave presentados en el Capítulo 1, diseñada para construir una base sólida en la programación con Java.

---

## 1. Variables: Las Cajas para Guardar Cosas

*   **¿QUÉ ES?** Una variable es un espacio en la memoria del computador al que le damos un nombre y donde guardamos un valor. Cada variable tiene un **tipo**, que define qué clase de "cosas" puede guardar.

*   **¿POR QUÉ EXISTE?** Para poder recordar y manipular información. Sin variables, un programa no podría hacer nada útil, ya que no podría almacenar datos, como la puntuación de un jugador o el nombre de un usuario.

*   **¿CUÁNDO LO USO?** Siempre. Las variables son el pilar de cualquier programa. Se usan para todo: desde contadores en bucles hasta para almacenar objetos complejos.
    *   **Analogía:** Piensa en las variables como **cajas de almacenamiento etiquetadas**. La etiqueta es el nombre de la variable (ej. `edad`) y el tipo de la caja te dice qué puedes meter dentro (ej. "Solo Números"). El contenido es el valor (ej. `25`).

---

## 2. Tipos de Datos: Las Reglas de las Cajas

Java tiene dos grandes categorías de tipos de datos.

### a. Tipos Primitivos: Los Bloques de Construcción

*   **¿QUÉ SON?** Son los tipos de datos más básicos y eficientes. No son objetos.
    *   `int`: Para números enteros (ej. `10`, `-5`).
    *   `double`: Para números con decimales (ej. `3.14`, `-0.01`).
    *   `boolean`: Para valores de verdadero o falso (`true` o `false`).
    *   `char`: Para un único carácter (ej. `'A'`).

*   **¿POR QUÉ EXISTEN?** Por eficiencia. Ocupan una cantidad de memoria predecible y son muy rápidos de procesar por el computador.

*   **¿CUÁNDO LOS USO?** Cuando necesites manejar datos simples y el rendimiento sea importante. Son ideales para contadores, cálculos matemáticos, y para representar estados simples (sí/no).

### b. Tipos de Objeto: Las Herramientas Sofisticadas

*   **¿QUÉ SON?** Son instancias de clases. A diferencia de los primitivos, los objetos no solo guardan datos, sino que también pueden **realizar acciones** a través de sus métodos.
    *   `String`: Para secuencias de caracteres (texto).
    *   `LocalDate`: Para representar fechas.

*   **¿POR QUÉ EXISTEN?** Para modelar conceptos del mundo real que son más complejos que un simple número. Un objeto `Cliente` puede tener nombre, RUT y la capacidad de `comprar()`.

*   **¿CUÁNDO LOS USO?** Para todo lo que no sea un dato primitivo simple. Cuando necesites agrupar datos y comportamientos, usas un objeto.

---

## 3. Operadores: Las Acciones Básicas

*   **¿QUÉ SON?** Símbolos que realizan operaciones sobre variables y valores.
    *   **Aritméticos:** `+`, `-`, `*`, `/` (suma, resta, etc.).
    *   **Relacionales:** `==`, `!=`, `<`, `>` (igual a, diferente de, menor que, etc.).

*   **¿POR QUÉ EXISTEN?** Para poder realizar cálculos y comparaciones, que son la base de la lógica de cualquier programa.

*   **¿CUÁNDO LOS USO?** Constantemente. En cálculos matemáticos, en las condiciones de los `if` y en las terminaciones de los bucles `while`.

---

## 4. Flujos de Control: Tomando Decisiones y Repitiendo Tareas

### a. Condicionales (`if`/`else`)

*   **¿QUÉ ES?** Una estructura que permite ejecutar un bloque de código solo si se cumple una condición.
*   **¿POR QUÉ EXISTE?** Para que los programas puedan tomar decisiones y reaccionar a diferentes situaciones.
*   **¿CUÁNDO LO USO?** Cuando necesites que tu programa se comporte de manera diferente según los datos. Ejemplo: "Si la edad del usuario es mayor a 18, dale acceso. Si no, muéstrale un mensaje de error".

### b. Bucles (`while`, `for`)

*   **¿QUÉ SON?** Estructuras que permiten repetir un bloque de código múltiples veces.
*   **¿POR QUÉ EXISTEN?** Para automatizar tareas repetitivas.
*   **¿CUÁNDO LOS USO?** Cuando necesites procesar una colección de elementos (como un `ArrayList`) o repetir una acción hasta que se cumpla una condición. Ejemplo: "Para cada estudiante en la lista, imprime su nombre".

---

## 5. Arrays: Listas de Tamaño Fijo

*   **¿QUÉ ES?** Un objeto que puede contener una cantidad fija de elementos del mismo tipo.
*   **¿POR QUÉ EXISTE?** Es la forma más básica y eficiente en memoria de almacenar una secuencia de datos.
*   **¿CUÁNDO LO USO?** Cuando sepas de antemano exactamente cuántos elementos necesitas guardar y ese número no vaya a cambiar. Por ejemplo, para guardar los 7 días de la semana.
    *   **Analogía:** Un **cartón de huevos**. Tiene un número fijo de espacios (12) y solo puedes poner huevos (elementos del mismo tipo) en él. No puedes añadir un espacio número 13.
