# Borrador - Capítulo 9: Entrada y Salida

Este capítulo explora cómo los programas Java leen y escriben datos, una capacidad esencial para interactuar con archivos y otras fuentes de información. Nos centraremos en la API moderna y más sencilla, conocida como NIO.2 (`java.nio.file`).

---

## 1. El Objeto Clave: `Path` - La Representación de una Ruta

*   **¿QUÉ ES?** Un objeto `Path` representa la ruta a un archivo o directorio en el sistema de archivos. **No es el archivo en sí**, sino su ubicación.

*   **¿POR QUÉ EXISTE?** Para proporcionar una representación independiente del sistema operativo. Un `Path` maneja correctamente las diferencias entre las rutas de Windows (`C:\Users\Ana`) y las de Linux/macOS (`/home/ana`).

*   **¿CÓMO SE CREA?** Usando el método estático `Paths.get()`.

    ```java
    // Creando una ruta a un archivo
    Path rutaArchivo = Paths.get("datos", "config.txt"); // Relativa al proyecto

    // Creando una ruta a un directorio
    Path rutaDirectorio = Paths.get("/home/usuario/documentos"); // Absoluta
    ```

*   **Analogía:** Un `Path` es como una **dirección postal escrita en un sobre**. La dirección no es la casa, pero te dice exactamente dónde encontrarla.

---

## 2. La Clase de Utilidad: `Files` - El Cartero que Actúa

*   **¿QUÉ ES?** La clase `Files` es una navaja suiza llena de métodos estáticos para operar sobre los `Path`s. Es el "trabajador" que usa la dirección (`Path`) para hacer cosas con el archivo o directorio real.

*   **¿POR QUÉ EXISTE?** Para simplificar enormemente las operaciones comunes con archivos, que antes eran muy complejas.

*   **¿CUÁNDO LA USO?** Para casi todas las interacciones básicas con el sistema de archivos.

### Operaciones Comunes con `Files`:

*   **Comprobar si algo existe:**
    ```java
    if (Files.exists(rutaArchivo)) { ... }
    ```
*   **Crear un directorio:**
    ```java
    Files.createDirectory(rutaDirectorio);
    ```
*   **Copiar un archivo:**
    ```java
    Files.copy(rutaOrigen, rutaDestino);
    ```
*   **Mover o renombrar un archivo:**
    ```java
    Files.move(rutaOrigen, rutaDestino);
    ```
*   **Eliminar un archivo:**
    ```java
    Files.delete(rutaArchivo);
    ```

---

## 3. Leyendo y Escribiendo Archivos Pequeños

Para archivos que caben cómodamente en la memoria (como archivos de configuración o documentos pequeños), la clase `Files` ofrece métodos increíblemente simples.

*   **Leer todo un archivo a un `String`:**
    ```java
    String contenido = Files.readString(rutaArchivo);
    ```
*   **Leer todo un archivo a una lista de líneas (`List<String>`):**
    ```java
    List<String> lineas = Files.readAllLines(rutaArchivo);
    ```
*   **Escribir un `String` a un archivo:**
    ```java
    Files.writeString(rutaArchivo, "Hola Mundo");
    ```
*   **Escribir una lista de líneas a un archivo:**
    ```java
    List<String> lineas = List.of("Primera línea", "Segunda línea");
    Files.write(rutaArchivo, lineas);
    ```

---

## 4. Manejando Archivos Grandes: `Streams` de I/O

Cuando trabajas con archivos muy grandes (gigabytes), no puedes cargarlos enteros en la memoria. Para esto, usamos `Streams` de I/O, que procesan los datos en pequeños trozos.

*   **¿QUÉ SON?** Son canales que nos permiten leer o escribir datos de forma secuencial, byte a byte o carácter a carácter.

*   **Analogía:** Piensa en leer un archivo grande como **beber agua de un río con una pajita**. No metes todo el río en un vaso (la memoria); en su lugar, bebes pequeños sorbos (bytes) a través de la pajita (el `InputStream`).

### El Patrón `try-with-resources` es Esencial

Como los streams de I/O son recursos que deben cerrarse, **siempre** deben manejarse con un bloque `try-with-resources` para garantizar que se cierren automáticamente, incluso si ocurre un error.

*   **Leyendo un archivo grande línea por línea:**
    ```java
    try (Stream<String> lineasStream = Files.lines(rutaArchivo)) {
        // Usamos la API de Streams del Capítulo 8 para procesar las líneas
        lineasStream
            .filter(linea -> linea.contains("ERROR"))
            .forEach(System.out::println);
    }
    ```
    `Files.lines()` es "perezoso" (`lazy`), lo que significa que no lee todo el archivo de una vez, sino que va entregando las líneas a medida que el stream las procesa.

*   **Lectura y escritura de bajo nivel (Bytes):**
    Para copiar un archivo grande, se usan `InputStream` y `OutputStream`.

    ```java
    try (InputStream in = Files.newInputStream(rutaOrigen);
         OutputStream out = Files.newOutputStream(rutaDestino)) {
        
        // Copia los datos en trozos (buffer)
        in.transferTo(out);
    }
    ```

---

## 5. Navegando Directorios

La API de Streams también facilita el recorrido de árboles de directorios.

*   **`Files.walk(rutaDirectorio)`:** Devuelve un `Stream<Path>` que te permite "caminar" por todos los archivos y subdirectorios a partir de una ruta inicial.

    ```java
    // Ejemplo: Encontrar todos los archivos .java en un proyecto
    try (Stream<Path> paths = Files.walk(Paths.get("."))) {
        paths
            .filter(path -> path.toString().endsWith(".java"))
            .forEach(System.out::println);
    }
    ```
