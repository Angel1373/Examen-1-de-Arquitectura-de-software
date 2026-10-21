package modelonegocio;

public class MetodoPago {

    private String numeroTarjeta;
    private String bancoEmisor;
    private String ultimosCuatroDigitos;
    private String tipoTarjeta;

    public MetodoPago(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta.replaceAll("\\s", "");
        obtenerDatosTarjeta();
    }

    public void obtenerDatosTarjeta() {
        int longitud = numeroTarjeta.length();
        ultimosCuatroDigitos = longitud >= 4 ? numeroTarjeta.substring(longitud - 4) : numeroTarjeta;
        String prefijo = longitud >= 4 ? numeroTarjeta.substring(0, 4) : "";
        bancoEmisor = switch (prefijo) {
            case "4242" -> "BBVA";
            case "4111" -> "Banorte";
            case "5200" -> "Santander";
            case "5555" -> "HSBC";
            case "3782" -> "American Express";
            default -> "Banco desconocido";
        };
        char primero = longitud > 0 ? numeroTarjeta.charAt(0) : ' ';
        String red = primero == '4' ? "Visa" : primero == '5' ? "Mastercard" : primero == '3' ? "American Express" : "Tarjeta";
        boolean par = longitud > 0 && (numeroTarjeta.charAt(longitud - 1) - '0') % 2 == 0;
        tipoTarjeta = red + (par ? " débito" : " crédito");
    }

    public String getBancoEmisor() {
        return bancoEmisor;
    }

    public String getUltimosCuatroDigitos() {
        return ultimosCuatroDigitos;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }
}
