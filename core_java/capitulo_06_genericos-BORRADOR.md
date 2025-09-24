# Borrador - Capítulo 6: Genéricos

Este capítulo introduce los Genéricos, una poderosa característica de Java que nos permite escribir clases y métodos más flexibles y seguros en cuanto a tipos.

---

## 1. El Problema: Clases que solo funcionan con un tipo

Imagina que creamos una clase `CajaDeStrings` para guardar un `String`.

```java
public class CajaDeStrings {
    private String contenido;
    public void guardar(String cosa) { this.contenido = cosa; }
    public String sacar() { return this.contenido; }
}
```
Funciona bien, pero ¿qué pasa si ahora queremos una caja para guardar un `Integer`? ¿Y otra para un `Cliente`? Tendríamos que copiar y pegar la clase, cambiando solo el tipo de dato. Esto es repetitivo y propenso a errores.

La solución "antigua" era usar el tipo `Object`, la superclase de todos los objetos en Java.

```java
public class CajaDeObject {
    private Object contenido;
    public void guardar(Object cosa) { this.contenido = cosa; }
    public Object sacar() { return this.contenido; }
}
```
Esto es flexible, pero tiene dos grandes problemas:
1.  **Pérdida de Seguridad de Tipos:** Puedes guardar un `Integer` y luego intentar sacarlo como si fuera un `String`, lo que causará un `ClassCastException` en tiempo de ejecución.
2.  **Necesidad de Castings:** Cada vez que sacas algo de la caja, tienes que hacer un "casting" manual para decirle al compilador de qué tipo es, lo cual es verboso y peligroso. `String miString = (String) caja.sacar();`

---

## 2. La Solución: Genéricos (`Generics`)

*   **¿QUÉ SON?** Los genéricos son una forma de parametrizar clases, interfaces y métodos con un **tipo de dato comodín**. Este tipo se especifica cuando se usa la clase o el método.

*   **¿POR QUÉ EXISTEN?** Para proporcionar **seguridad de tipos en tiempo de compilación** y eliminar la necesidad de castings manuales.

*   **¿CUÁNDO LOS USO?** Siempre que quieras crear una clase o método que pueda operar con diferentes tipos de objetos de manera segura. El ejemplo más famoso es el `ArrayList`.

### Creando una Clase Genérica

Vamos a refactorizar nuestra clase `Caja` para que sea genérica.

*   **Analogía:** Piensa en los genéricos como crear una **plantilla para una caja**. En lugar de construir una "Caja para Zapatos" o una "Caja para Sombreros", construyes una "Caja para <_CUALQUIER_COSA_>" (`Caja<T>`). Cuando necesitas una caja, especificas para qué la vas a usar: `Caja<Zapato>` o `Caja<Sombrero>`.

```java
// Se declara un parámetro de tipo <T> (la 'T' es una convención para 'Tipo')
public class Caja<T> {
    private T contenido; // El tipo del campo es ahora 'T'

    public void guardar(T cosa) {
        this.contenido = cosa;
    }

    public T sacar() {
        return this.contenido;
    }
}
```

### Usando la Clase Genérica

Ahora, cuando usamos nuestra clase `Caja`, le decimos al compilador qué tipo de `T` vamos a usar.

```java
// Creamos una caja específicamente para Strings.
Caja<String> cajaDeNombres = new Caja<>();
cajaDeNombres.guardar("Ana");
String nombre = cajaDeNombres.sacar(); // No se necesita casting.

// El compilador nos protege de errores:
// cajaDeNombres.guardar(123); // ¡Error de compilación! Esta caja solo acepta Strings.

// Creamos otra caja, esta vez para Integers.
Caja<Integer> cajaDeEdades = new Caja<>();
cajaDeEdades.guardar(25);
int edad = cajaDeEdades.sacar(); // No se necesita casting.
```
Con una sola clase `Caja<T>`, hemos logrado flexibilidad total con máxima seguridad.

---

## 3. Métodos Genéricos

También podemos crear métodos individuales que son genéricos, sin que toda la clase lo sea.

*   **¿CUÁNDO LOS USO?** Cuando tienes una función de utilidad que realiza la misma lógica para diferentes tipos de datos.

```java
public class Utilidades {
    // Este método puede imprimir cualquier tipo de array.
    public static <E> void imprimirArray(E[] array) {
        for (E elemento : array) {
            System.out.printf("%s ", elemento);
        }
        System.out.println();
    }
}

// Uso:
Integer[] numeros = {1, 2, 3};
String[] palabras = {"Hola", "Mundo"};
Utilidades.imprimirArray(numeros);
Utilidades.imprimirArray(palabras);
```

---

## 4. Comodines (`Wildcards`): Flexibilidad Avanzada

A veces, no nos importa el tipo exacto, solo que cumpla ciertas condiciones. Para eso usamos comodines (`?`).

*   **`List<?>` (Comodín sin límites):** Significa "una lista de cualquier tipo desconocido". Es útil cuando solo necesitas hacer operaciones que no dependen del tipo, como `list.size()` o `list.clear()`.

*   **`List<? extends ClasePadre>` (Límite Superior):** Significa "una lista de cualquier tipo que sea `ClasePadre` o una de sus hijas".
    *   **Analogía:** `List<? extends Animal>` puede contener una `List<Perro>` o una `List<Gato>`, pero no una `List<Planta>`. Es útil para cuando necesitas **leer** de una estructura genérica.

*   **`List<? super ClaseHija>` (Límite Inferior):** Significa "una lista de cualquier tipo que sea `ClaseHija` o uno de sus padres". Es un caso de uso más raro, útil para cuando necesitas **escribir** en una estructura genérica.
