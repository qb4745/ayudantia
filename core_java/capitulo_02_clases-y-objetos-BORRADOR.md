# Borrador - Capítulo 2: Clases y Objetos

Esta síntesis se enfoca en el pilar de Java: la Programación Orientada a Objetos. Aprenderemos a crear nuestros propios "tipos de datos" complejos.

---

## 1. El Salto Conceptual: De Variables a Objetos

En el capítulo anterior, usamos variables para guardar datos simples (`int`, `String`). Pero el mundo real es más complejo. ¿Cómo representamos un "Cliente"? Un cliente no es solo un nombre, tiene también un RUT, un saldo, etc.

La Programación Orientada a Objetos (POO) nos permite agrupar datos y las acciones relacionadas con esos datos en una sola entidad: un **objeto**.

---

## 2. Clases: Los Planos de Construcción

*   **¿QUÉ ES?** Una clase es un **plano** o una **plantilla** que define cómo serán los objetos de un cierto tipo. No es el objeto en sí, sino el diseño del objeto.

*   **¿POR QUÉ EXISTE?** Para crear nuevos tipos de datos personalizados y reutilizables. En lugar de manejar el nombre y el saldo de un cliente como variables separadas por todo nuestro código, creamos una clase `Cliente` que los agrupa.

*   **¿CUÁNDO LO USO?** Siempre que necesites representar un concepto del mundo real (un `Producto`, una `Factura`, un `Usuario`). Las clases son la forma en que organizamos la complejidad en Java.
    *   **Analogía:** Piensa en un **plano para construir una casa**. El plano (`la clase`) define que todas las casas de ese diseño tendrán 2 baños, 3 dormitorios y un método `encenderLuces()`. El plano no es una casa real, solo la especificación.

---

## 3. Objetos: Las Casas Construidas

*   **¿QUÉ ES?** Un objeto es una **instancia** de una clase. Es la "cosa" real que construimos a partir del plano.

*   **¿POR QUÉ EXISTE?** Para que podamos trabajar con entidades concretas en nuestro programa.

*   **¿CUÁNDO LO USO?** Cuando necesites representar una entidad específica. Puedes tener múltiples objetos de la misma clase.
    *   **Analogía:** Usando el plano de la casa (`Clase Casa`), puedes construir **muchas casas reales** (`objetos`). `miCasa`, `tuCasa`, `laCasaBlanca` son tres objetos diferentes, pero todos construidos a partir del mismo plano. Cada uno tiene sus propias características (el color de `miCasa` es azul, el de `tuCasa` es verde), pero todos comparten la misma estructura.

---

## 4. Anatomía de una Clase: Campos, Constructores y Métodos

Una clase (el plano) tiene tres componentes principales:

### a. Campos (o Atributos): Los Datos

*   **¿QUÉ SON?** Son las **variables** que pertenecen a un objeto. Definen las características o el "estado" del objeto.
*   **Ejemplo:** En una clase `Cliente`, los campos serían `String nombre;` e `int saldo;`. Cada objeto `Cliente` tendrá su propia copia de estas variables.

### b. Constructores: El Proceso de Construcción

*   **¿QUÉ ES?** Un bloque de código especial que se ejecuta **una sola vez** cuando se crea un nuevo objeto. Su trabajo es inicializar los campos.
*   **¿POR QUÉ EXISTE?** Para asegurar que un objeto nazca en un estado válido y consistente.
*   **Ejemplo:**
    ```java
    public class Cliente {
        String nombre;
        // Este es el constructor. Recibe un nombre y lo asigna al campo.
        public Cliente(String nombreInicial) {
            this.nombre = nombreInicial;
        }
    }
    // Para crear un objeto: new Cliente("Ana");
    ```

### c. Métodos: Las Acciones

*   **¿QUÉ SON?** Son las **funciones** que pertenecen a un objeto. Definen lo que el objeto puede "hacer".
*   **¿POR QUÉ EXISTEN?** Para encapsular el comportamiento junto con los datos. En lugar de tener una función externa que modifique el saldo de un cliente, el propio objeto `Cliente` tiene un método para hacerlo.
*   **Ejemplo:**
    ```java
    public class Cliente {
        // ... campos y constructor ...
        // Este es un método.
        public void depositar(int monto) {
            this.saldo += monto;
        }
    }
    // Para usarlo: miCliente.depositar(100);
    ```

---

## 5. Encapsulamiento: Protegiendo los Datos

*   **¿QUÉ ES?** Es el principio de mantener los campos de un objeto como **`private`** y solo permitir su modificación a través de métodos **`public`**.
*   **¿POR QUÉ EXISTE?** Para proteger la integridad de los datos de un objeto. Evita que código externo pueda corromper el estado del objeto asignándole valores inválidos.
*   **¿CUÁNDO LO USO?** Siempre. Es una de las "mejores prácticas" fundamentales de la POO.
    *   **Analogía:** Piensa en el **motor de un coche**. No puedes simplemente meter la mano y cambiar la posición de un pistón (`campo private`). En su lugar, usas los pedales y la palanca de cambios (`métodos public`) que el coche te ofrece para interactuar con el motor de forma segura.

    ```java
    public class CuentaBancaria {
        private double saldo; // Privado: nadie fuera de esta clase puede tocarlo.

        // Público: la única forma de cambiar el saldo es a través de este método.
        public void depositar(double monto) {
            if (monto > 0) { // Podemos añadir lógica de validación.
                this.saldo += monto;
            }
        }
    }
    ```
