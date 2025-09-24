# Borrador - Capítulo 3: Interfaces y Expresiones Lambda

Este capítulo explora cómo Java define "contratos" de comportamiento con Interfaces y cómo simplifica el código con Expresiones Lambda.

---

## 1. Interfaces: Los Contratos de Comportamiento

*   **¿QUÉ ES?** Una interfaz es una colección de métodos abstractos (sin implementación). Es un **contrato** que una clase puede prometer cumplir. Si una clase `implementa` una interfaz, está obligada a proporcionar una implementación para todos los métodos definidos en ese contrato.

*   **¿POR QUÉ EXISTE?** Para definir un conjunto de capacidades que diferentes clases pueden compartir, sin importar cómo están construidas internamente. Permite que nuestro código sea más flexible y desacoplado.

*   **¿CUÁNDO LO USO?** Cuando quieres que un grupo de clases no relacionadas ofrezcan una funcionalidad común.
    *   **Analogía:** Piensa en la interfaz `Enchufable`. Un `CargadorDeTelefono`, una `Lampara` y una `Aspiradora` son clases muy diferentes, pero todas pueden `implementar` la interfaz `Enchufable`. Esto significa que todas prometen tener un método `conectarACorriente()`. A la toma de corriente no le importa qué dispositivo es, solo le importa que sea `Enchufable`.

    ```java
    // El Contrato
    public interface Enchufable {
        void conectarACorriente(); // Todas las clases que implementen esto DEBEN tener este método.
    }

    // Una clase que cumple el contrato
    public class Lampara implements Enchufable {
        @Override
        public void conectarACorriente() {
            System.out.println("Lámpara encendida.");
        }
    }
    ```

---

## 2. El Poder de Programar para la Interfaz

El verdadero poder de las interfaces es que podemos escribir código que dependa del **contrato**, no de la **implementación específica**.

*   **¿QUÉ SIGNIFICA?** Significa que podemos tener una variable del tipo de la interfaz y asignarle cualquier objeto de una clase que cumpla ese contrato.

*   **Analogía:** Puedes tener una caja de herramientas (`ArrayList<Enchufable>`) donde guardas todos tus aparatos. No te importa si son lámparas o aspiradoras; lo único que sabes es que a cualquier cosa que saques de esa caja le puedes pedir que se `conecteACorriente()`.

    ```java
    Enchufable miAparato = new Lampara(); // Válido, porque una Lampara ES Enchufable.
    miAparato.conectarACorriente();

    // Más adelante, podrías cambiarlo por otro objeto que también cumple el contrato.
    // miAparato = new Aspiradora();
    // miAparato.conectarACorriente();
    ```
Esto hace que nuestro código sea increíblemente flexible. Podemos añadir nuevos tipos de aparatos `Enchufables` en el futuro sin tener que cambiar el código que los utiliza.

---

## 3. Expresiones Lambda: Atajos para Acciones

*   **¿QUÉ ES?** Una expresión lambda es una forma corta y anónima de escribir una función. Es básicamente un bloque de código que puedes pasar como si fuera una variable.

*   **¿POR QUÉ EXISTE?** Para reducir el "ruido" (boilerplate) del código, especialmente cuando se trabaja con interfaces que tienen un solo método (llamadas interfaces funcionales).

*   **¿CUÁNDO LO USO?** Cuando necesites pasar un pequeño bloque de código (una acción) como argumento a otro método. Son extremadamente comunes en el manejo de eventos, el procesamiento de colecciones y la programación asíncrona.

### De Clase Anónima a Lambda

Imagina que tenemos un botón y queremos definir qué pasa cuando se hace clic. La forma "antigua" era crear una clase anónima:

```java
// Forma Antigua: verbosa y difícil de leer
boton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent event) {
        System.out.println("¡Botón pulsado!");
    }
});
```

Con una lambda, nos centramos solo en la **acción**:

```java
// Forma Moderna: concisa y clara
boton.addActionListener(event -> System.out.println("¡Botón pulsado!"));
```

*   **Anatomía de la Lambda:**
    *   `event`: Los parámetros de la función (si no hay, se usan `()`).
    *   `->`: La flecha que separa los parámetros del cuerpo.
    *   `System.out.println(...)`: El cuerpo de la función (la acción a realizar).

*   **Analogía:** Piensa en las lambdas como una **nota Post-it con una instrucción**. En lugar de escribir un manual formal y detallado (la clase anónima) para una tarea simple, simplemente escribes la instrucción en un Post-it (`lambda`) y se la pasas a quien la necesite ejecutar.

---

## 4. Métodos `default` en Interfaces

*   **¿QUÉ ES?** Un método `default` es un método en una interfaz que **sí tiene una implementación**.
*   **¿POR QUÉ EXISTE?** Para permitir añadir nueva funcionalidad a interfaces existentes sin romper todas las clases que ya la implementan. Si se añade un nuevo método abstracto a una interfaz, todas las clases que la implementan se romperían porque les faltaría ese método. Si se añade como `default`, las clases existentes simplemente heredan la implementación por defecto y no se rompen.
*   **¿CUÁNDO LO USO?** Cuando eres el diseñador de una librería o API y necesitas evolucionar una interfaz sin causar una catástrofe de compilación para tus usuarios.
