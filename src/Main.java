import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String apiKey = "258bfd82ac701bc2c55e16f0";
        String baseCurrency = "USD";
        List<GuardarConversion> historial = new ArrayList<>();

        ExchangeRateClient client = new ExchangeRateClient(apiKey);
        String jsonResponse = client.getExchangeRates(baseCurrency);

        if (jsonResponse == null) {
            System.out.println("Error al obtener datos de la API.");
            return;
        }

        Gson gson = new Gson();
        ApiResponse response = gson.fromJson(jsonResponse, ApiResponse.class);

        if (!"success".equalsIgnoreCase(response.result)) {
            System.out.println("Error en la respuesta: " + response.result);
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("""
                    *******************************************************
                    Sea bienvenido/a al Conversor de Moneda =)

                    1) Dólar => Peso argentino
                    2) Peso argentino => Dólar
                    3) Dólar => Real brasileño
                    4) Real brasileño => Dólar
                    5) Dólar => Peso colombiano
                    6) Peso colombiano => Dólar
                    7) Salir

                    Elija una opción :
                   
                    """);

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Entrada inválida. Por favor ingrese un número.");
                scanner.nextLine(); // limpiar entrada
                continue;
            }

            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar el buffer

            switch (opcion) {
                case 1:
                    convertirMoneda(scanner, response, historial, "USD", "ARS", false);
                    break;
                case 2:
                    convertirMoneda(scanner, response, historial, "ARS", "USD", true);
                    break;
                case 3:
                    convertirMoneda(scanner, response, historial, "USD", "BRL", false);
                    break;
                case 4:
                    convertirMoneda(scanner, response, historial, "BRL", "USD", true);
                    break;
                case 5:
                    convertirMoneda(scanner, response, historial, "USD", "COP", false);
                    break;
                case 6:
                    convertirMoneda(scanner, response, historial, "COP", "USD", true);
                    break;
                case 7:
                    continuar = false;
                    System.out.println("Gracias por usar el convertidor.");
                    break;
                default:
                    System.out.println("❌ Opción inválida.");
            }
        }

        scanner.close();

        // Guardar historial
        Gson gsonGuardar = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("historial_conversiones.json")) {
            gsonGuardar.toJson(historial, writer);
            System.out.println("✅ Historial guardado en 'historial_conversiones.json'");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el archivo: " + e.getMessage());
        }
    }

    private static void convertirMoneda(
            Scanner scanner,
            ApiResponse response,
            List<GuardarConversion> historial,
            String origen,
            String destino,
            boolean esInversion
    ) {
        if (!response.conversion_rates.containsKey(destino)) {
            System.out.println("❌ Moneda de destino no disponible.");
            return;
        }

        System.out.printf("Ingrese cantidad en %s: ", origen);
        while (!scanner.hasNextDouble()) {
            System.out.println("❌ Entrada inválida. Por favor ingrese un número válido.");
            scanner.nextLine(); // limpiar entrada
        }
        double cantidad = scanner.nextDouble();
        scanner.nextLine(); // limpiar buffer

        double tasa = response.conversion_rates.get(destino);
        double resultado = esInversion ? cantidad / tasa : cantidad * tasa;

        System.out.printf("➤ %.2f %s = %.2f %s%n", cantidad, origen, resultado, destino);

        GuardarConversion conversion = new GuardarConversion(destino, cantidad, tasa, resultado);
        historial.add(conversion);
    }
}
