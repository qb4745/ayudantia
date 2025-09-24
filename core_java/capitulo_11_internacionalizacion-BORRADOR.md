# Borrador - Capítulo 11: Internacionalización

Este capítulo explora cómo escribir aplicaciones que puedan soportar múltiples idiomas y formatos regionales, un proceso conocido como Internacionalización (i18n).

---

## 1. El Problema: Código "Duro" en un Idioma

Imagina una aplicación que saluda al usuario:

```java
System.out.println("Hello, World!");
double price = 1234.56;
System.out.println("Price: " + price);
```
Este código tiene varios problemas para una audiencia global:
1.  **Idioma Fijo:** El texto "Hello, World!" y "Price:" está "hardcodeado" en inglés. Para traducirlo al español, tendríamos que cambiar el código fuente.
2.  **Formato de Número Fijo:** En Estados Unidos, `1,234.56` es correcto. En Alemania, el formato correcto es `1.234,56`.
3.  **Formato de Fecha Fijo:** Las fechas también varían (`MM/DD/YYYY` vs. `DD/MM/YYYY`).

La internacionalización resuelve esto separando el código de los datos específicos de una región.

---

## 2. `Locale`: La Identidad de la Región del Usuario

*   **¿QUÉ ES?** Un objeto `Locale` es un identificador que representa una combinación específica de idioma y región geográfica.

*   **¿POR QUÉ EXISTE?** Para que nuestro programa sepa qué idioma y qué formatos (de números, fechas, moneda) debe usar para el usuario actual.

*   **¿CÓMO SE CREA?**
    *   Usando constantes predefinidas: `Locale.US`, `Locale.GERMANY`, `Locale.JAPAN`.
    *   Especificando un idioma (código ISO 639) y, opcionalmente, un país (código ISO 3166): `new Locale("es", "ES")` para español de España.

    ```java
    // Obtener el Locale por defecto del sistema operativo del usuario
    Locale localeUsuario = Locale.getDefault();

    // Definir un Locale específico
    Locale localeFrances = Locale.FRANCE; // fr_FR
    ```
*   **Analogía:** Un `Locale` es como la **configuración de "Idioma y Región" de tu teléfono**. Le dice a todas las aplicaciones cómo deben mostrarte la información.

---

## 3. `ResourceBundle`: Externalizando el Texto

*   **¿QUÉ ES?** Un `ResourceBundle` es un conjunto de archivos de propiedades (`.properties`) que contienen el texto de nuestra aplicación traducido a diferentes idiomas.

*   **¿POR QUÉ EXISTE?** Para sacar todo el texto visible por el usuario fuera del código fuente. Esto permite que los traductores puedan trabajar en los archivos de texto sin tocar (y potencialmente romper) el código Java.

*   **¿CÓMO FUNCIONA?**
    1.  Creas una familia de archivos con un nombre base, seguido del código de idioma/región.
        *   `messages.properties` (El archivo por defecto, usualmente en inglés)
        *   `messages_es.properties` (Español)
        *   `messages_fr.properties` (Francés)

    2.  Dentro de cada archivo, defines pares `clave=valor`.
        *   **`messages.properties`:** `greeting=Hello`
        *   **`messages_es.properties`:** `greeting=Hola`

    3.  En tu código, cargas el `ResourceBundle` para un `Locale` específico y pides el texto por su clave.

    ```java
    // Cargar el bundle para un Locale español
    Locale localeES = new Locale("es");
    ResourceBundle messages = ResourceBundle.getBundle("messages", localeES);

    // Obtener el texto usando la clave
    String saludo = messages.getString("greeting"); // Devuelve "Hola"
    System.out.println(saludo);
    ```
    Java es lo suficientemente inteligente como para buscar el archivo más específico (`messages_es.properties`) y, si no lo encuentra, usar el archivo por defecto (`messages.properties`).

---

## 4. Formateo de Números, Monedas y Fechas

Una vez que tenemos un `Locale`, podemos usarlo para formatear datos sensibles a la región.

### a. Números y Monedas

*   La clase `NumberFormat` se encarga de esto.

    ```java
    double numero = 12345.67;
    Locale localeUS = Locale.US; // en_US
    Locale localeDE = Locale.GERMANY; // de_DE

    // Formateo de números
    NumberFormat nfUS = NumberFormat.getNumberInstance(localeUS);
    NumberFormat nfDE = NumberFormat.getNumberInstance(localeDE);
    System.out.println("US: " + nfUS.format(numero)); // Salida: US: 12,345.67
    System.out.println("DE: " + nfDE.format(numero)); // Salida: DE: 12.345,67

    // Formateo de monedas
    NumberFormat cfUS = NumberFormat.getCurrencyInstance(localeUS);
    NumberFormat cfDE = NumberFormat.getCurrencyInstance(localeDE);
    System.out.println("US: " + cfUS.format(numero)); // Salida: US: $12,345.67
    System.out.println("DE: " + cfDE.format(numero)); // Salida: DE: 12.345,67 €
    ```

### b. Fechas y Horas

*   La clase `DateTimeFormatter` (de la API moderna `java.time`) se encarga de esto.

    ```java
    LocalDate hoy = LocalDate.now();
    Locale localeUS = Locale.US;
    Locale localeIT = Locale.ITALY;

    // Crear formateadores con estilos predefinidos para cada Locale
    DateTimeFormatter dtfUS = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(localeUS);
    DateTimeFormatter dtfIT = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(localeIT);

    System.out.println("US: " + hoy.format(dtfUS)); // Salida: US: September 24, 2025
    System.out.println("IT: " + hoy.format(dtfIT)); // Salida: IT: 24 settembre 2025
    ```
