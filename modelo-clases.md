# Análisis del Diagrama de Clases del Sistema de Reservas

Este documento describe las relaciones entre las clases del modelo de datos, tal como se representan en el diagrama UML del proyecto. El diagrama muestra una arquitectura clara y bien estructurada que es fundamental para el funcionamiento del sistema.

---

## 1. Relación entre `Hotel` y `Habitacion`

![Relación Hotel-Habitacion](https://i.imgur.com/your_image_link_here.png) 
*(Nota: Se puede reemplazar con una imagen recortada de la relación si se desea)*

*   **Tipo de Relación:** Agregación.
*   **Representación en el Diagrama:** La conexión entre `Hotel` y `Habitacion` se realiza mediante una línea sólida con un **rombo hueco (◇)** en el extremo de la clase `Hotel`.
*   **Interpretación:** Esta relación se interpreta como "un `Hotel` **tiene** `Habitacion`es". Es una relación de "todo-parte", donde el hotel es el "todo" y las habitaciones son las "partes". El rombo hueco especifica que es una **agregación**, lo que implica que una habitación podría, conceptualmente, existir sin estar asociada a un hotel.
*   **Multiplicidad (Cardinalidad):**
    *   El **`1`** en el lado del `Hotel` indica que una `Habitacion` pertenece exactamente a **un** `Hotel`.
    *   El **`*`** (asterisco) en el lado de la `Habitacion` indica que un `Hotel` puede tener **muchas** (`0` o más) `Habitacion`es.
*   **Justificación en el Código:** Esta relación se materializa en la clase `Hotel` a través del siguiente atributo:
    ```java
    private ArrayList<Habitacion> habitaciones;
    ```

---

## 2. Relación entre `Cliente` y `Reserva`

*   **Tipo de Relación:** Agregación.
*   **Representación en el Diagrama:** Al igual que en el caso anterior, una línea con un **rombo hueco (◇)** en el extremo de `Cliente` conecta ambas clases.
*   **Interpretación:** La relación se lee como "un `Cliente` **tiene** `Reserva`s". Representa el historial de reservas que pertenece a un cliente.
*   **Multiplicidad (Cardinalidad):**
    *   El **`1`** en el lado del `Cliente` (etiquetado como `1cliente`) significa que una `Reserva` es realizada por exactamente **un** `Cliente`.
    *   El **`*`** en el lado de la `Reserva` significa que un `Cliente` puede realizar **muchas** (`0` o más) `Reserva`s.
*   **Justificación en el Código:** Esta relación se implementa en la clase `Cliente` con el atributo:
    ```java
    private ArrayList<Reserva> reservas;
    ```

---

## 3. Relaciones de la Clase `Reserva`

La clase `Reserva` es el núcleo del modelo, ya que conecta a las demás entidades para formar un registro completo.

*   **Tipo de Relación:** Asociación.
*   **Representación en el Diagrama:** Se muestran líneas sólidas y directas desde `Reserva` hacia `Hotel`, `Habitacion` y `Cliente`. La ausencia de rombos indica una asociación estándar.
*   **Interpretación:** Se lee como "una `Reserva` **está asociada con** un `Hotel`, una `Habitacion` y un `Cliente`". Esto significa que una reserva es un evento que no puede existir si le falta alguna de estas tres piezas de información. Su propósito es vincular a las otras entidades.
*   **Multiplicidad (Cardinalidad):**
    *   El **`1`** cerca de `Hotel` indica que una `Reserva` corresponde a exactamente **un** `Hotel`.
    *   El **`1`** cerca de `Habitacion` indica que una `Reserva` es para exactamente **una** `Habitacion`.
    *   El **`1`** cerca de `Cliente` indica que una `Reserva` pertenece a exactamente **un** `Cliente`.
*   **Justificación en el Código:** La clase `Reserva` contiene atributos que son instancias de las otras clases, estableciendo así los enlaces:
    ```java
    private Hotel hotel;
    private Habitacion habitacion;
    private Cliente cliente;
    ```

---

### Resumen General

El diagrama de clases nos muestra una arquitectura de datos robusta y lógica:

*   Las **agregaciones** definen claramente las colecciones que posee cada entidad principal (`Hotel` y `Cliente`).
*   La clase `Reserva` actúa como una **clase asociativa**, siendo indispensable para registrar la interacción entre un `Cliente`, una `Habitacion` y un `Hotel`.

Visualmente, el diagrama comunica de manera efectiva el modelo de negocio: "Un sistema donde los Clientes pueden acumular un historial de Reservas. Cada Reserva es un registro único que vincula a ese Cliente con una Habitación específica que, a su vez, pertenece a un Hotel concreto".
