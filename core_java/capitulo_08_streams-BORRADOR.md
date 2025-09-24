# Borrador - Capítulo 8: Streams

Este capítulo introduce la API de Streams, un paradigma moderno y poderoso para procesar colecciones de datos en Java de una manera más declarativa y legible.

---

## 1. El Problema: Bucles `for` engorrosos

Imagina que tienes una lista de productos y quieres obtener los nombres de todos los productos caros (precio > 1000), en mayúsculas y ordenados alfabéticamente.

**La forma tradicional (Imperativa):**

```java
List<Producto> productos = ...;
List<String> nombresCaros = new ArrayList<>();

// 1. Filtrar
for (Producto p : productos) {
    if (p.getPrecio() > 1000) {
        nombresCaros.add(p.getNombre());
    }
}

// 2. Ordenar
Collections.sort(nombresCaros);

// 3. Transformar a mayúsculas
List<String> nombresFinales = new ArrayList<>();
for (String nombre : nombresCaros) {
    nombresFinales.add(nombre.toUpperCase());
}
```
Este código funciona, pero tiene problemas:
*   **Es verboso:** Requiere mucho código para una tarea simple.
*   **Es difícil de leer:** Tienes que leer línea por línea para entender la intención general.
*   **Crea listas intermedias:** `nombresCaros` es una lista temporal que ensucia el código.

---

## 2. La Solución: La API de Streams

*   **¿QUÉ ES?** Un Stream es una **secuencia de elementos** que puedes procesar a través de una **cadena de operaciones** (un "pipeline"). No es una estructura de datos que almacena elementos, sino una vista sobre una fuente de datos (como una `List` o un `Array`).

*   **¿POR QUÉ EXISTE?** Para permitirnos escribir código de procesamiento de datos que es:
    *   **Declarativo:** Describes **qué** quieres lograr, no **cómo** hacerlo paso a paso.
    *   **Fluido y Legible:** Las operaciones se encadenan de forma natural.
    *   **Eficiente:** Las operaciones se realizan de forma "perezosa" (lazy), evitando la creación de colecciones intermedias.

*   **Analogía:** Piensa en un Stream como una **línea de ensamblaje en una fábrica**.
    *   La **fuente** (`productos.stream()`) es el almacén de materias primas.
    *   Las **operaciones intermedias** (`filter`, `map`, `sorted`) son las estaciones de trabajo. Cada estación recibe un ítem, lo transforma y lo pasa a la siguiente.
    *   La **operación terminal** (`collect`) es la estación de empaquetado final, donde se recoge el producto terminado.

**La forma moderna (Declarativa) con Streams:**

```java
List<String> nombresFinales = productos.stream() // 1. Obtener el stream
    .filter(p -> p.getPrecio() > 1000)          // 2. Filtrar productos caros
    .sorted(Comparator.comparing(Producto::getNombre)) // 3. Ordenar por nombre
    .map(p -> p.getNombre().toUpperCase())      // 4. Mapear a nombres en mayúsculas
    .collect(Collectors.toList());             // 5. Recolectar en una nueva lista
```
Este código hace exactamente lo mismo, pero es mucho más conciso y la intención es clara de inmediato.

---

## 3. Anatomía de un Pipeline de Streams

Un pipeline de Streams tiene tres partes:

### a. La Fuente

*   **¿Qué es?** De dónde vienen los datos. Generalmente, una colección.
*   **Cómo se crea:** `miLista.stream()`, `miSet.stream()`, `Arrays.stream(miArray)`.

### b. Operaciones Intermedias (0 o más)

*   **¿Qué son?** Transformaciones que se aplican a los elementos del stream. Son **perezosas** (`lazy`), lo que significa que no se ejecutan hasta que se invoca una operación terminal. Devuelven un nuevo stream.
*   **Operaciones Comunes:**
    *   `filter(predicate)`: Descarta elementos que no cumplen una condición.
    *   `map(function)`: Transforma cada elemento en otra cosa (ej. un `Producto` a un `String`).
    *   `sorted()`: Ordena los elementos.
    *   `distinct()`: Elimina elementos duplicados.

### c. La Operación Terminal (1)

*   **¿Qué es?** La operación que inicia el procesamiento y produce un resultado final (o una acción). Cierra el stream.
*   **Operaciones Comunes:**
    *   `collect(collector)`: Recolecta los elementos en una `List`, `Set` o `Map`.
    *   `forEach(consumer)`: Aplica una acción a cada elemento (ej. imprimirlo).
    *   `count()`: Devuelve el número de elementos.
    *   `findFirst()` / `findAny()`: Devuelve el primer elemento (envuelto en un `Optional`).
    *   `anyMatch()` / `allMatch()`: Comprueba si algún/todos los elementos cumplen una condición.

---

## 4. Streams Paralelos: Procesamiento Sencillo en Múltiples Hilos

*   **¿QUÉ ES?** Una de las características más impresionantes de la API de Streams. Con un simple cambio, puedes hacer que el pipeline se ejecute en paralelo, utilizando múltiples núcleos de tu procesador.
*   **¿CÓMO SE USA?** Simplemente reemplaza `.stream()` por `.parallelStream()`.

    ```java
    List<String> nombresFinales = productos.parallelStream()
        .filter(...)
        // ... el resto del pipeline es idéntico ...
    ```
*   **¿CUÁNDO LO USO?** Cuando tienes una gran cantidad de datos y las operaciones que realizas sobre ellos son independientes y computacionalmente intensivas. No es una solución mágica para todo; para conjuntos de datos pequeños, el costo de coordinar los hilos puede hacer que sea más lento.
