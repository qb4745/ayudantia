# Borrador - Capítulo 12: La API de Red

Este capítulo explora cómo las aplicaciones Java pueden comunicarse a través de la red, enfocándose en la moderna API `HttpClient` para interactuar con servicios web y APIs.

---

## 1. El Rol de Java en la Web: El Cliente

Aunque Java puede usarse para construir servidores, un caso de uso extremadamente común es escribir **clientes** que consumen datos de APIs web existentes. Por ejemplo, una aplicación de escritorio que obtiene el pronóstico del tiempo de una API, o un servicio de backend que se comunica con otro microservicio.

La API `java.net.http.HttpClient`, introducida en Java 11, es la herramienta moderna y preferida para esta tarea.

*   **Analogía:** Piensa en tu aplicación Java como un **navegador web sin interfaz gráfica**.
    *   Escribes una **URL** en el navegador.
    *   El navegador envía una **petición (Request)** al servidor.
    *   El servidor devuelve una **respuesta (Response)** con el contenido (HTML, JSON, etc.).
    *   El `HttpClient` nos permite hacer exactamente esto, pero de forma programática.

---

## 2. Los 3 Componentes Clave del `HttpClient`

El proceso de hacer una petición web se modela con tres objetos principales:

### a. `HttpRequest`: ¿Qué Quieres Pedir?

*   **¿QUÉ ES?** Un objeto que representa la petición que vas a enviar. Contiene toda la información sobre tu solicitud.
*   **Componentes Principales:**
    *   **URI (Uniform Resource Identifier):** La dirección del recurso que quieres obtener (ej. `https://api.github.com/users/octocat`).
    *   **Método HTTP:** La acción que quieres realizar. Los más comunes son:
        *   `GET`: Para obtener datos.
        *   `POST`: Para enviar datos nuevos (ej. crear un usuario).
        *   `PUT`: Para actualizar datos existentes.
        *   `DELETE`: Para borrar datos.
    *   **Cabeceras (Headers):** Metadatos sobre la petición (ej. `Accept: application/json` para indicar que esperas una respuesta en formato JSON).
    *   **Cuerpo (Body):** Los datos que envías con la petición (usado principalmente con `POST` y `PUT`).

*   **Construcción:** Se usa un patrón "Builder" fluido y legible.
    ```java
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.example.com/data"))
        .header("Accept", "application/json")
        .GET() // O .POST(body), .PUT(body), etc.
        .build();
    ```

### b. `HttpClient`: ¿Quién Envía la Petición?

*   **¿QUÉ ES?** El objeto que actúa como el "navegador" o el "mensajero". Es el cliente que toma tu `HttpRequest` y lo envía a través de la red.
*   **Buenas Prácticas:** Un `HttpClient` es un objeto pesado y está diseñado para ser **reutilizado**. La práctica recomendada es crear una única instancia y compartirla para todas las peticiones que tu aplicación necesite hacer.

*   **Construcción:**
    ```java
    HttpClient client = HttpClient.newHttpClient();
    ```

### c. `HttpResponse<T>`: ¿Qué Recibiste de Vuelta?

*   **¿QUÉ ES?** Un objeto que contiene la respuesta del servidor.
*   **Componentes Principales:**
    *   **Código de Estado (Status Code):** Un número que indica el resultado de la petición (ej. `200 OK`, `404 Not Found`, `500 Internal Server Error`).
    *   **Cabeceras (Headers):** Metadatos sobre la respuesta.
    *   **Cuerpo (Body):** El contenido de la respuesta (ej. el JSON o HTML).

---

## 3. Poniéndolo Todo Junto: Un Ejemplo Sincrónico

Vamos a hacer una petición `GET` simple para obtener información de un usuario de la API de GitHub.

*   **Sincrónico:** Significa que nuestro código se **bloqueará** y esperará hasta que la respuesta del servidor haya sido recibida por completo.

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubApiClient {
    public static void main(String[] args) throws Exception { // Simplificado para el ejemplo
        // 1. Crear el cliente (reutilizable)
        HttpClient client = HttpClient.newHttpClient();

        // 2. Construir la petición
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/users/octocat"))
            .GET()
            .build();

        // 3. Enviar la petición y obtener la respuesta
        // El segundo argumento, BodyHandlers.ofString(), le dice al cliente
        // que queremos leer el cuerpo de la respuesta como un String.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 4. Procesar la respuesta
        System.out.println("Código de Estado: " + response.statusCode());
        System.out.println("Cuerpo de la Respuesta (JSON):");
        System.out.println(response.body());
    }
}
```

---

## 4. Peticiones Asíncronas: No Bloquear el Programa

Para aplicaciones con interfaz gráfica o servidores que manejan muchas peticiones, no podemos permitirnos bloquear un hilo mientras esperamos una respuesta de la red. La API `HttpClient` también soporta un modo asíncrono.

*   **¿QUÉ ES?** En lugar de esperar, envías la petición y le adjuntas una "función de callback". Tu programa continúa ejecutando otras tareas, y cuando la respuesta llegue, el cliente ejecutará tu función de callback con el resultado.

*   **`CompletableFuture<T>`:** El modo asíncrono devuelve un `CompletableFuture`, que es una versión más avanzada de los `Future` que vimos en el capítulo de concurrencia.

```java
// El método sendAsync no bloquea
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body) // Cuando llegue, extrae el cuerpo
      .thenAccept(System.out::println) // Y luego, imprime el cuerpo
      .join(); // Espera a que todo termine (solo para este ejemplo simple)
```
El código asíncrono es más complejo, pero es fundamental para construir aplicaciones de alto rendimiento.
