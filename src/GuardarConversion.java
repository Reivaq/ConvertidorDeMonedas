public class GuardarConversion {
    private final String monedaDestino;
    private final double cantidad;
    private final double tasaConversion;
    private final double resultado;

    public GuardarConversion(String monedaDestino, double cantidad, double tasaConversion, double resultado) {
        this.monedaDestino = monedaDestino;
        this.cantidad = cantidad;
        this.tasaConversion = tasaConversion;
        this.resultado = resultado;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getTasaConversion() {
        return tasaConversion;
    }

    public double getResultado() {
        return resultado;
    }

    @Override
    public String toString() {
        return String.format("Conversión a %s: %.2f * %.4f = %.2f",
                monedaDestino, cantidad, tasaConversion, resultado);
    }
}
