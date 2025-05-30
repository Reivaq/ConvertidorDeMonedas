public class ExchangeRateClient  {
    private String apikey = "258bfd82ac701bc2c55e16f0";

    public ExchangeRateClient(String apikey){
        this.apikey = apikey;
    }

    public String getApikey() {
        return apikey;
    }
}
