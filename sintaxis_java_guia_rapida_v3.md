# Guía Conceptual de Sintaxis y Ecosistema Moderno de Java (v3)

Esta guía está diseñada para desarrolladores que regresan a Java. No solo refresca la sintaxis, sino que profundiza en los conceptos y el "porqué" detrás de las herramientas y prácticas del desarrollo moderno, sirviendo como una guía de estudio conceptual.

---

## 1. Conceptos Fundamentales de Java

### 1.1. Anatomía de una Clase: El Plano Maestro
En Java, todo vive dentro de una clase. Una clase es el plano o plantilla que define a un tipo de objeto. Su propósito es agrupar el **estado** (los datos que un objeto conoce) y el **comportamiento** (las acciones que un objeto puede realizar) en una unidad lógica y cohesiva.

```java
// `public` es un modificador de acceso que significa que esta clase es visible
// desde cualquier otro paquete en la aplicación.
public class Employee {
    // 1. CAMPOS (Estado): Son las variables que pertenecen a cada instancia de la clase.
    // Se declaran `private` para cumplir con el principio de encapsulamiento.
    private String name;
    private double salary;

    // 2. CONSTRUCTOR: Un método especial que se llama cuando se crea un nuevo objeto
    // con la palabra clave `new`. Su función es inicializar los campos y asegurar
    // que el objeto nazca en un estado válido.
    public Employee(String name, double startingSalary) {
        this.name = name;
        this.salary = startingSalary;
    }

    // 3. MÉTODOS (Comportamiento): Definen las acciones que el objeto puede realizar.
    // `public`: Expone una capacidad al mundo exterior.
    // `void`: Indica que este método realiza una acción pero no devuelve ningún valor.
    public void raiseSalary(double byPercent) {
        double raise = this.salary * byPercent / 100;
        this.salary += raise;
    }

    // Este método devuelve un valor de tipo String.
    public String getName() {
        return this.name;
    }
}
```

### 1.2. Los 3 Pilares de la Programación Orientada a Objetos (POO)
Estos principios son la filosofía que guía el diseño de software en Java.

| Pilar | Concepto Clave | Propósito ("Porqué") | Analogía |
| :--- | :--- | :--- | :--- |
| **Encapsulamiento** | Proteger los datos (`private`) y exponer solo métodos (`public`). | Controlar el acceso, ocultar la complejidad interna y garantizar la integridad de los datos del objeto. | **Una Caja Negra** |
| **Herencia** | Una clase (hija) `extends` de otra (padre) para heredar su comportamiento. | Reutilizar código y establecer una jerarquía lógica donde una clase "es un tipo de" otra. | **Jerarquía de Clasificación** |
| **Polimorfismo** | Tratar objetos de diferentes tipos a través de una interfaz o superclase común. | Escribir código genérico y flexible que se adapta al tipo real del objeto en tiempo de ejecución. | **Un Método, Muchas Formas** |

### 1.3. Entendiendo la Memoria: Stack vs. Heap
Comprender esto es crucial para evitar errores sutiles con los objetos.

*   **Stack (Pila):** Una memoria pequeña, ordenada y muy rápida. Aquí se almacenan:
    1.  **Variables Primitivas:** El valor de `int`, `double`, `boolean`, etc., vive directamente aquí.
    2.  **Referencias a Objetos:** No el objeto en sí, sino la "dirección" o el "puntero" que nos dice dónde encontrar el objeto.

*   **Heap (Montón):** Una gran área de memoria, menos organizada. Aquí es donde viven todos los **objetos** creados con la palabra clave `new`.

*   **Implicación Clave (Paso por Valor):** Cuando llamas a un método, Java **siempre** copia el valor de la variable que le pasas.
    *   Si pasas un `int`, se copia el valor del número.
    *   Si pasas un objeto, se copia el valor de la **referencia (la dirección)**. Esto significa que tanto la variable original como el parámetro del método apuntan al **mismo objeto** en el Heap. Por lo tanto, el método puede modificar el estado del objeto original.

### 1.4. Diseño Flexible: La Importancia de las Interfaces
*   **¿Qué es una Interfaz?** Un contrato 100% abstracto. Es una colección de firmas de métodos que una clase promete implementar. No contiene código, solo la especificación del "qué".
*   **Principio Clave:** "Programa para una interfaz, no para una implementación".
*   **¿Por qué?** Escribir código que depende de una interfaz (`List`) en lugar de una clase concreta (`ArrayList`) lo hace increíblemente flexible. Te permite cambiar la implementación subyacente (de `ArrayList` a `LinkedList`, por ejemplo) sin tener que cambiar el código que la utiliza. Esto se llama **desacoplamiento**.

**Mal (Rígido y Acoplado):** `ArrayList<String> names = new ArrayList<>();`
**Bien (Flexible y Desacoplado):** `List<String> names = new ArrayList<>();`

---

## 2. Sintaxis Moderna y Atajos

*   **`var` (Java 10+):** Permite al compilador inferir el tipo de una variable local. `var message = "Hello!";`. Úsalo cuando el tipo sea obvio por el contexto para reducir el ruido.
*   **`Records` (Java 14+):** Una sintaxis ultra-concisa para clases inmutables que son simples contenedores de datos. `public record Point(int x, int y) {}`.
*   **`switch` Mejorado (Java 14+):** Más seguro (sin `break` accidentales) y puede usarse como una expresión que devuelve un valor.
*   **Text Blocks (Java 15+):** Permite escribir strings de múltiples líneas de forma natural usando `"""`.
*   **Atajos de IDE:** `psvm` (main), `sout` (println), `fori` (bucle for), `iter` (bucle for-each).

---

## 3. Librerías y Prácticas Esenciales de la JDK

### Colecciones (`java.util`)
El framework para almacenar grupos de objetos. La elección correcta depende de tus necesidades.

| Interfaz | Implementación Común | Propósito Principal |
| :--- | :--- | :--- |
| `List<E>` | `ArrayList<E>` | Colección ordenada, acceso rápido por índice, permite duplicados. |
| `Set<E>` | `HashSet<E>` | Colección sin duplicados, sin orden, comprobación de existencia muy rápida. |
| `Map<K, V>` | `HashMap<K, V>` | Pares de clave-valor únicos, búsqueda casi instantánea por clave. |

### Streams (`java.util.stream`)
La forma moderna y declarativa de procesar colecciones. En lugar de escribir bucles `for` (el "cómo"), describes una cadena de transformaciones (el "qué").

```java
var longNames = employees.stream() // 1. Obtener un flujo de datos
    .filter(e -> e.getName().length() > 10) // 2. Filtrar
    .map(Employee::getName) // 3. Transformar
    .collect(Collectors.toList()); // 4. Recolectar el resultado
```

### Excepciones y Entrada/Salida
*   **Excepciones:** El mecanismo para manejar errores. Usa `try-catch-finally` para el control de errores y `try-with-resources` para la gestión automática y segura de recursos (como archivos o conexiones).
*   **I/O (`java.nio.file`):** La API moderna para archivos, centrada en `Path` (la ruta) y `Files` (las operaciones).
    ```java
    Path path = Paths.get("data/info.txt");
    try {
        String content = Files.readString(path);
        Files.writeString(path, content.toUpperCase());
    } catch (IOException e) {
        // Manejar el error de forma robusta
        e.printStackTrace();
    }
    ```

---

## 4. El Ecosistema Moderno

### Build Tools: Ant, Maven y Gradle
Son herramientas que automatizan el ciclo de vida de un proyecto: compilación, pruebas, empaquetado y gestión de dependencias.

| Característica | Ant | Maven | Gradle |
| :--- | :--- | :--- | :--- |
| **Enfoque** | Procedural (le dices *cómo*) | Declarativo (le dices *qué*) | Híbrido (declarativo con flexibilidad) |
| **Gestión de Dependencias** | No (Manual) | Sí (Automática) | Sí (Automática y Avanzada) |
| **Configuración** | `build.xml` (XML) | `pom.xml` (XML) | `build.gradle` (Groovy/Kotlin DSL) |
| **Uso Moderno** | Legado / Proyectos específicos | Muy extendido (estándar de facto) | Creciente (estándar en Android) |

### Testing: JUnit 5
El framework estándar para escribir **pruebas unitarias**. Es una práctica no negociable en el desarrollo profesional para garantizar la calidad y la mantenibilidad del código.

---

## 5. Escribiendo Código Limpio: Convenciones de Nombrado

Seguir las convenciones estándar es crucial para la legibilidad y la colaboración.

| Elemento | Convención | Ejemplo |
| :--- | :--- | :--- |
| **Clases, Interfaces, Records** | `PascalCase` | `Employee`, `Comparable` |
| **Métodos y Variables** | `camelCase` | `raiseSalary()`, `employeeName` |
| **Constantes** | `ALL_CAPS_SNAKE_CASE` | `MAX_RETRIES`, `PI` |
| **Paquetes** | `lowercase.con.puntos` | `com.horstmann.corejava` |
