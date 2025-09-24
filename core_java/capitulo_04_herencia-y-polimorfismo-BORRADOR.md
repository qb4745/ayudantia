# Borrador - Capítulo 4: Herencia y Polimorfismo

Este capítulo cubre dos de los conceptos más poderosos y a menudo confusos de la Programación Orientada a Objetos: cómo las clases pueden heredar características de otras y cómo podemos tratar a objetos de diferentes clases de la misma manera.

---

## 1. Herencia: Construyendo sobre lo que ya Existe

*   **¿QUÉ ES?** La herencia es un mecanismo que permite a una clase (llamada **subclase** o clase hija) adquirir los campos y métodos de otra clase (llamada **superclase** o clase padre).

*   **¿POR QUÉ EXISTE?** Para la **reutilización de código** y para crear una jerarquía lógica entre clases. Si tenemos varias clases que comparten características comunes, podemos poner esas características en una superclase y hacer que las otras hereden de ella.

*   **¿CUÁNDO LO USO?** Cuando puedes decir que una clase "es un tipo de" otra clase.
    *   **Analogía:** Piensa en vehículos. Podemos tener una superclase `Vehiculo` con campos como `velocidad` y métodos como `acelerar()`. Luego, podemos tener subclases como `Coche` y `Bicicleta`.
        *   Un `Coche` **es un tipo de** `Vehiculo`.
        *   Una `Bicicleta` **es un tipo de** `Vehiculo`.
    *   Tanto `Coche` como `Bicicleta` heredarán automáticamente `velocidad` y `acelerar()` sin tener que reescribir ese código. Además, pueden añadir sus propias características (`Coche` puede tener `numeroDePuertas`, `Bicicleta` puede tener `tipoDeManillar`).

    ```java
    // La Superclase (Padre)
    public class Vehiculo {
        protected int velocidad; // 'protected' permite acceso a las clases hijas

        public void acelerar(int aumento) {
            this.velocidad += aumento;
        }
    }

    // La Subclase (Hija)
    public class Coche extends Vehiculo { // La palabra 'extends' establece la herencia
        private int numeroDePuertas; // Un campo específico de Coche
    }
    ```

---

## 2. Sobrescritura de Métodos: Especializando el Comportamiento

*   **¿QUÉ ES?** Una subclase puede proporcionar su propia implementación de un método que ya existe en su superclase. Esto se llama sobrescritura (`@Override`).

*   **¿POR QUÉ EXISTE?** Para permitir que una subclase modifique o especialice un comportamiento heredado.

*   **¿CUÁNDO LO USO?** Cuando un método heredado no hace exactamente lo que la subclase necesita.
    *   **Analogía:** La superclase `Ave` tiene un método `moverse()`. Para un `Pinguino` (que es un `Ave`), el movimiento es "caminar". Para un `Aguila` (que también es un `Ave`), el movimiento es "volar". Ambas subclases sobrescriben `moverse()` para adaptarlo a su comportamiento específico.

    ```java
    public class Ave {
        public void moverse() {
            System.out.println("El ave se mueve.");
        }
    }

    public class Pinguino extends Ave {
        @Override // Buena práctica para indicar que estamos sobrescribiendo
        public void moverse() {
            System.out.println("El pingüino camina torpemente.");
        }
    }
    ```

---

## 3. Polimorfismo: Un Nombre, Muchas Formas

*   **¿QUÉ ES?** El polimorfismo es la capacidad de tratar a objetos de diferentes clases de manera uniforme, siempre que compartan una superclase o interfaz común.

*   **¿POR QUÉ EXISTE?** Para escribir código más genérico y flexible. Nos permite manejar una colección de objetos heterogéneos sin tener que saber el tipo exacto de cada uno.

*   **¿CUÁNDO LO USO?** Cuando tienes una colección de objetos que son de diferentes tipos pero comparten una jerarquía de herencia.
    *   **Analogía:** Imagina que tienes un **zoológico** (`ArrayList<Animal>`). En esta lista, guardas un `Leon`, un `Pinguino` y una `Serpiente`. No te importa el tipo exacto de cada uno. Simplemente puedes recorrer la lista y decirle a cada `animal` en ella que `hagaSonido()`.
        *   El león rugirá.
        *   El pingüino graznará.
        *   La serpiente siseará.
    *   El polimorfismo se encarga de que se llame al método `hacerSonido()` correcto para cada objeto en tiempo de ejecución. Tú solo necesitas escribir un único bucle genérico.

    ```java
    // Tenemos una lista de la superclase
    ArrayList<Animal> zoologico = new ArrayList<>();
    zoologico.add(new Leon());
    zoologico.add(new Pinguino());

    // Gracias al polimorfismo, podemos tratar a todos por igual
    for (Animal animal : zoologico) {
        // Java sabe qué método 'hacerSonido()' llamar para cada objeto
        animal.hacerSonido();
    }
    ```

---

## 4. Clases Abstractas: Superclases que no se pueden Instanciar

*   **¿QUÉ ES?** Una clase declarada con la palabra clave `abstract`. Puede tener tanto métodos abstractos (sin implementación) como métodos concretos (con implementación). No se pueden crear objetos (`new`) de una clase abstracta.

*   **¿POR QUÉ EXISTE?** Para ser usada exclusivamente como una superclase. Sirve como una base común para una jerarquía de clases, forzando a las subclases a implementar ciertos métodos, pero también proporcionando funcionalidad compartida.

*   **¿CUÁNDO LO USO?** Cuando tienes un concepto que es demasiado genérico para existir por sí solo, pero que sirve como una excelente plantilla para otras clases.
    *   **Analogía:** `FiguraGeometrica` es una excelente clase abstracta. No tiene sentido crear un objeto de "figura geométrica" (¿qué forma tendría?), pero sí tiene sentido que `Circulo`, `Cuadrado` y `Triangulo` hereden de ella. La clase `FiguraGeometrica` podría tener un método abstracto `calcularArea()` (forzando a cada hija a implementarlo) y un método concreto `getColor()`.
