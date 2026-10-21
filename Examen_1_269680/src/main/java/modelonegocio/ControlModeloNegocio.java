package modelonegocio;

import java.util.ArrayList;
import java.util.List;
import utilerias.SujetoObservado;

public class ControlModeloNegocio extends SujetoObservado {

    private final ContenedorNegocio contenedor;

    public ControlModeloNegocio(List<Pelicula> peliculas) {
        this.contenedor = new ContenedorNegocio(peliculas);
    }

    public List<Pelicula> getPeliculas() {
        return contenedor.getPeliculas();
    }

    public Pelicula getPeliculaSeleccionada() {
        return contenedor.getPeliculaSeleccionada();
    }

    public List<Funcion> getHorariosDisponibles() {
        return contenedor.getHorariosDisponibles();
    }

    public Funcion getFuncionSeleccionada() {
        return contenedor.getFuncionSeleccionada();
    }

    public List<Asiento> getAsientos() {
        return contenedor.getAsientos();
    }

    public List<Asiento> getAsientosSeleccionados() {
        return contenedor.getAsientosSeleccionados();
    }

    public double getImporteTotal() {
        return contenedor.getImporteTotal();
    }

    public MetodoPago getDatosTarjeta() {
        return contenedor.getTarjeta();
    }

    public List<Boleto> getBoletos() {
        return contenedor.getBoletos();
    }

    public boolean seleccionarPelicula(Pelicula pelicula) {
        if (bloqueado() || pelicula == null || !contenedor.contienePelicula(pelicula)) {
            return false;
        }
        Funcion anterior = contenedor.getFuncionSeleccionada();
        if (anterior != null) {
            anterior.limpiarSeleccion();
        }
        contenedor.setPeliculaSeleccionada(pelicula);
        contenedor.setFuncionSeleccionada(null);
        notificarObservadores();
        return true;
    }

    public boolean seleccionarHorario(Funcion funcion) {
        if (bloqueado() || contenedor.getPeliculaSeleccionada() == null || !contenedor.getHorariosDisponibles().contains(funcion)) {
            return false;
        }
        Funcion anterior = contenedor.getFuncionSeleccionada();
        if (anterior != null) {
            anterior.limpiarSeleccion();
        }
        contenedor.setFuncionSeleccionada(funcion);
        notificarObservadores();
        return true;
    }

    public boolean seleccionarAsiento(int fila, int columna) {
        Funcion funcion = contenedor.getFuncionSeleccionada();
        if (bloqueado() || funcion == null) {
            return false;
        }
        if (!funcion.seleccionarAsiento(fila, columna)) {
            return false;
        }
        funcion.calcularImporteTotal();
        notificarObservadores();
        return true;
    }

    public boolean realizarPago(String numeroTarjeta) {
        if (contenedor.hayBoletos() || contenedor.getAsientosSeleccionados().isEmpty()) {
            return false;
        }
        contenedor.setTarjeta(new MetodoPago(numeroTarjeta));
        notificarObservadores();
        return true;
    }

    public boolean confirmarPago() {
        if (!contenedor.hayTarjeta() || contenedor.hayBoletos()) {
            return false;
        }
        Funcion funcion = contenedor.getFuncionSeleccionada();
        funcion.ocuparAsientosSeleccionados();
        contenedor.agregarBoleto(new Boleto(
                funcion.getPelicula().getTitulo(),
                funcion.getFecha(),
                funcion.getHorario(),
                funcion.getSala(),
                new ArrayList<>(funcion.getAsientosSeleccionados()),
                funcion.getImporteTotal()));
        notificarObservadores();
        return true;
    }

    private boolean bloqueado() {
        return contenedor.hayTarjeta() || contenedor.hayBoletos();
    }

}