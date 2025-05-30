import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    public static void main(String[] args) {
        String apiKey = "258bfd82ac701bc2c55e16f0";
        String baseCurrency = "MXN";
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
            System.out.println("\n=== CONVERTIDOR DE MONEDAS (MXN base) ===");
            System.out.println("Seleccione la moneda de destino:");
            System.out.println("1. USD");
            System.out.println("2. EUR");
            System.out.println("3. GBP");
            System.out.println("4. JPY");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            String destino = null;

            switch (opcion) {
                case 1:
                    destino = "USD";
                    break;
                case 2:
                    destino = "EUR";
                    break;
                case 3:
                    destino = "GBP";
                    break;
                case 4:
                    destino = "JPY";
                    break;
                case 5:
                    continuar = false;
                    System.out.println("Gracias por usar el convertidor.");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    continue;  // vuelve al inicio del while
            }

            if (!continuar || destino == null) {
                break; // sal del while
            }

            System.out.print("Ingrese la cantidad en MXN: ");
            double cantidad = scanner.nextDouble();

            if (response.conversion_rates.containsKey(destino)) {
                double tasa = response.conversion_rates.get(destino);
                double convertido = cantidad * tasa;
                System.out.printf("➤ %.2f MXN = %.2f %s%n", cantidad, convertido, destino);
                GuardarConversion c = new GuardarConversion(destino, cantidad, tasa, convertido);
                historial.add(c);
            } else {
                System.out.println("Moneda no disponible.");
            }

        }
        scanner.close();

        Gson gsonGuardar = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("historial_conversiones.json")) {
            gsonGuardar.toJson(historial, writer);
            System.out.println("✅ Historial guardado en 'historial_conversiones.json'");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el archivo: " + e.getMessage());
        }
    }


