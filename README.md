# 💱 Convertidor de Monedas

Este es un proyecto en Java que permite convertir monedas utilizando una API de tasas de cambio en tiempo real. El programa se ejecuta en consola, consulta la tasa de cambio actual desde [ExchangeRate API](https://www.exchangerate-api.com/) y realiza la conversión según la opción elegida por el usuario.

---

## 🚀 Funcionalidades

- Conversión de moneda entre:
  - Dólar (USD) ⇄ Peso Argentino (ARS)
  - Dólar (USD) ⇄ Real Brasileño (BRL)
  - Dólar (USD) ⇄ Peso Colombiano (COP)
- Registro de cada conversión realizada.
- Guardado del historial de conversiones en un archivo `historial_conversiones.json`.

---

## 🛠️ Tecnologías Usadas

- Java 11
- Librería `Gson` de Google para manejar JSON
- API de ExchangeRate para obtener las tasas de cambio
- Cliente HTTP `java.net.http.HttpClient`

---

## 📦 Estructura del Proyecto

```
ConvertidorDeMonedas/
├── src/
│   ├── Main.java
│   ├── ExchangeRateClient.java
│   ├── ApiResponse.java
│   └── GuardarConversion.java
├── historial_conversiones.json
└── README.md
```

---

## ⚙️ Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Reivaq/ConvertidorDeMonedas.git
cd ConvertidorDeMonedas
```

### 2. Agregar tu propia API Key

Regístrate en [ExchangeRate API](https://www.exchangerate-api.com/) y obtén tu API key gratuita.  
Luego, reemplaza la línea del archivo `Main.java`:

```java
String apiKey = "TU_API_KEY_AQUI";
```

### 3. Compilar y ejecutar

Asegúrate de tener Java 11 o superior instalado.

```bash
javac src/*.java
java src/Main
```

---

## 📝 Uso del programa

Al ejecutarlo, verás un menú en consola como el siguiente:

```
*******************************************************
Sea bienvenido/a al Conversor de Moneda =]

1) Dólar =>> Peso argentino
2) Peso argentino =>> Dólar
3) Dólar =>> Real brasileño
4) Real brasileño =>> Dólar
5) Dólar =>> Peso colombiano
6) Peso colombiano =>> Dólar
7) Salir

Elija una opción válida:
*******************************************************
```

Selecciona una opción, ingresa el monto a convertir, y el programa mostrará el resultado. Al finalizar, el historial de conversiones se guardará automáticamente en un archivo JSON.

---

## 🗂️ Archivo de historial

Todas las conversiones se almacenan en el archivo `historial_conversiones.json`, incluyendo:

- Moneda destino
- Cantidad original
- Tasa de conversión utilizada
- Resultado final

Ejemplo:

```json
[
  {
    "monedaDestino": "ARS",
    "cantidad": 100,
    "tasaConversion": 882.5,
    "resultado": 88250
  }
]
```

---

## 📌 Notas

- El programa requiere conexión a internet para acceder a la API.
- La clave de API tiene un límite de consultas gratuitas por día según el plan.

---

## 🧑‍💻 Autor

**Reivaj**  
GitHub: [@Reivaq](https://github.com/Reivaq)

---

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT. Puedes modificarlo y usarlo libremente para fines educativos o personales.

## 🧠 Estructura del Código y Funcionamiento Interno

El proyecto está compuesto por varias clases con responsabilidades bien definidas:

- `Main`:  
  Es el punto de entrada del programa. Muestra el menú, gestiona la entrada del usuario, realiza las conversiones de moneda y guarda el historial en un archivo `.json`.

- `ExchangeRateClient`:  
  Se encarga de conectarse a la API pública [ExchangeRate API](https://www.exchangerate-api.com/) para obtener las tasas de cambio. Utiliza `HttpClient` para realizar la petición GET y devuelve la respuesta como JSON.

- `ApiResponse`:  
  Clase modelo para deserializar la respuesta de la API. Contiene el resultado (`success` o error) y un `Map<String, Double>` con las tasas de conversión.

- `GuardarConversion`:  
  Clase modelo que representa una conversión realizada. Contiene los campos de moneda destino, cantidad, tasa de conversión y resultado. Se utiliza para guardar el historial de conversiones en un archivo `.json` usando Gson.
