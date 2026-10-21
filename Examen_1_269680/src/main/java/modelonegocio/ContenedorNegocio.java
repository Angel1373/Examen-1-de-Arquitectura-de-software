package modelonegocio;

import java.util.ArrayList;
import java.util.List;

class ContenedorNegocio {

    private List<Pelicula> peliculas;
    private Pelicula peliculaSeleccionada;
    private Funcion funcionSeleccionada;
    private MetodoPago tarjeta;
    private List<Boleto> boletos = new ArrayList<>();

    ContenedorNegocio(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    List<Pelicula> getPeliculas() {
        return peliculas;
    }

    boolean contienePelicula(Pelicula pelicula) {
        return peliculas.contains(pelicula);
    }

    Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }

    void setPeliculaSeleccionada(Pelicula pelicula) {
        this.peliculaSeleccionada = pelicula;
    }

    Funcion getFuncionSeleccionada() {
        return funcionSeleccionada;
    }

    void setFuncionSeleccionada(Funcion funcion) {
        this.funcionSeleccionada = funcion;
    }

    List<Funcion> getHorariosDisponibles() {
        return peliculaSeleccionada == null ? new ArrayList<>() : peliculaSeleccionada.obtenerHorariosDisponibles();
    }

    List<Asiento> getAsientos() {
        return funcionSeleccionada == null ? new ArrayList<>() : funcionSeleccionada.obtenerAsientos();
    }

    List<Asiento> getAsientosSeleccionados() {
        return funcionSeleccionada == null ? new ArrayList<>() : funcionSeleccionada.getAsientosSeleccionados();
    }

    double getImporteTotal() {
        return funcionSeleccionada == null ? 0 : funcionSeleccionada.getImporteTotal();
    }

    MetodoPago getTarjeta() {
        return tarjeta;
    }

    void setTarjeta(MetodoPago tarjeta) {
        this.tarjeta = tarjeta;
    }

    boolean hayTarjeta() {
        return tarjeta != null;
    }

    List<Boleto> getBoletos() {
        return boletos;
    }

    void agregarBoleto(Boleto boleto) {
        boletos.add(boleto);
    }

    boolean hayBoletos() {
        return !boletos.isEmpty();
    }
}
