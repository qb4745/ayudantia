# Borrador - Capítulo 7: Colecciones

Este capítulo explora el Framework de Colecciones de Java, un conjunto de herramientas esenciales para almacenar y manipular grupos de objetos de manera eficiente.

---

## 1. ¿Qué es el Framework de Colecciones?

Más que clases individuales, es un **sistema de interfaces y sus implementaciones**. Piensa en él como una caja de herramientas con diferentes tipos de "contenedores" para objetos. Cada tipo de contenedor sirve para un propósito específico.

Las interfaces principales definen los **contratos** (qué puede hacer un contenedor), y las clases son las **implementaciones** (cómo lo hace).

*   **Analogía:** Imagina que necesitas organizar tus herramientas.
    *   Una **`List`** es como un **cinturón de herramientas** con bolsillos numerados. Las herramientas están en un orden específico y puedes tener duplicados.
    *   A **`Set`** es como una **caja con compartimentos únicos**. No puedes tener dos martillos idénticos y el orden no importa.
    *   A **`Map`** es como un **panel de pared con ganchos etiquetados**. Cada herramienta cuelga de un gancho con un nombre único.

---

## 2. La Interfaz `List`: Contenedores Ordenados

*   **¿QUÉ ES?** Una colección ordenada que permite elementos duplicados. Mantiene el orden de inserción.
*   **¿CUÁNDO LA USO?** Cuando el orden de los elementos es importante y necesitas acceder a ellos por su posición (índice).

### Implementaciones Comunes:

*   **`ArrayList` (La Elección por Defecto):**
    *   **Cómo funciona:** Usa un array de tamaño dinámico por debajo.
    *   **Fortalezas:** Acceso súper rápido a elementos por su índice (`get(i)`).
    *   **Debilidades:** Lento para añadir o quitar elementos en medio de la lista, porque requiere desplazar todos los elementos posteriores.
    *   **Cuándo usarla:** En el 90% de los casos. Es la lista de propósito general más común.

*   **`LinkedList`:**
    *   **Cómo funciona:** Cada elemento es un "nodo" que conoce al elemento anterior y al siguiente.
    *   **Fortalezas:** Muy rápido para añadir o quitar elementos al principio, al final o en medio de la lista.
    *   **Debilidades:** Lento para acceder a un elemento por su índice, porque tiene que recorrer la cadena de nodos desde el principio.
    *   **Cuándo usarla:** En casos específicos donde realizas muchísimas inserciones y eliminaciones en medio de la lista.

---

## 3. La Interfaz `Set`: Contenedores de Elementos Únicos

*   **¿QUÉ ES?** Una colección que **no permite** elementos duplicados.
*   **¿CUÁNDO LA USO?** Cuando necesitas asegurar que cada elemento en tu colección sea único. Por ejemplo, para almacenar una lista de correos electrónicos sin repetidos.

### Implementaciones Comunes:

*   **`HashSet` (La Elección por Defecto):**
    *   **Cómo funciona:** Usa una tabla hash por debajo. No garantiza ningún orden.
    *   **Fortalezas:** Extremadamente rápido para añadir, quitar y comprobar si un elemento existe (`contains()`).
    *   **Cuándo usarla:** Cuando solo te importa la unicidad y la velocidad, y el orden no es un requisito.

*   **`TreeSet`:**
    *   **Cómo funciona:** Usa una estructura de árbol. Mantiene los elementos en **orden natural** (alfabético, numérico).
    *   **Fortalezas:** Los elementos siempre están ordenados.
    *   **Debilidades:** Más lento que `HashSet` para añadir y quitar elementos.
    *   **Cuándo usarla:** Cuando necesitas unicidad Y que la colección se mantenga ordenada automáticamente.

---

## 4. La Interfaz `Map`: Contenedores de Pares Clave-Valor

*   **¿QUÉ ES?** Una colección que almacena pares de `(clave, valor)`. Las claves deben ser únicas. No es técnicamente una `Collection`, pero es parte del framework.
*   **¿CUÁNDO LA USO?** Cuando necesitas buscar un valor a través de un identificador único (la clave). Por ejemplo, para buscar un `Usuario` por su `rut`.

### Implementaciones Comunes:

*   **`HashMap` (La Elección por Defecto):**
    *   **Cómo funciona:** Usa una tabla hash. No garantiza el orden de las claves.
    *   **Fortalezas:** Búsqueda, inserción y eliminación de pares casi instantánea si tienes la clave.
    *   **Cuándo usarla:** En la mayoría de los casos donde necesites una búsqueda rápida por clave.

*   **`TreeMap`:**
    *   **Cómo funciona:** Usa una estructura de árbol. Mantiene las claves en **orden natural**.
    *   **Fortalezas:** Las claves siempre están ordenadas.
    *   **Debilidades:** Más lento que `HashMap`.
    *   **Cuándo usarla:** Cuando necesitas buscar por clave Y necesitas que las claves se mantengan ordenadas.

---

## 5. La Interfaz `Queue`: Contenedores para Procesamiento

*   **¿QUÉ ES?** Una colección diseñada para mantener elementos antes de procesarlos. Típicamente operan en modo FIFO (First-In, First-Out).
*   **¿CUÁNDO LA USO?** Para gestionar colas de tareas, como una lista de trabajos de impresión o peticiones de red pendientes.
    *   **Analogía:** Una **fila en un supermercado**. El primero que llega (`add`/`offer`) es el primero en ser atendido (`remove`/`poll`).
