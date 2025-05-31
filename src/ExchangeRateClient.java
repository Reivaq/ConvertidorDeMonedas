import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateClient {

    private final String apiKey;
    private final HttpClient client;

    public ExchangeRateClient(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient(); // Crear una sola instancia reutilizable
    }

    public String getExchangeRates(String baseCurrency) {
        String apiUrl = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, baseCurrency);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                return response.body();
            } else {
                System.err.println("❌ Error: Código de respuesta HTTP " + statusCode);
            }

        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la API: " + e.getMessage());
        }

        return null;
    }
}
