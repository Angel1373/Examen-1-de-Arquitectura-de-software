package modelonegocio;

import utilerias.Observador;

import java.util.List;

public class FachadaModeloNegocio implements IConsultaModeloNegocio, IOperacionesModeloNegocio {

    private ControlModeloNegocio control;

    public FachadaModeloNegocio(List<Pelicula> peliculas) {
        this.control = new ControlModeloNegocio(peliculas);
    }

    public void agregarObservador(Observador observador) {
        control.agregarObservador(observador);
    }

    @Override
    public List<Pelicula> getPeliculas() {
        return control.getPeliculas();
    }

    @Override
    public Pelicula getPeliculaSeleccionada() {
        return control.getPeliculaSeleccionada();
    }

    @Override
    public List<Funcion> getHorariosDisponibles() {
        return control.getHorariosDisponibles();
    }

    @Override
    public Funcion getFuncionSeleccionada() {
        return control.getFuncionSeleccionada();
    }

    @Override
    public List<Asiento> getAsientos() {
        return control.getAsientos();
    }

    @Override
    public List<Asiento> getAsientosSeleccionados() {
        return control.getAsientosSeleccionados();
    }

    @Override
    public double getImporteTotal() {
        return control.getImporteTotal();
    }

    @Override
    public MetodoPago getDatosTarjeta() {
        return control.getDatosTarjeta();
    }

    @Override
    public List<Boleto> getBoletos() {
        return control.getBoletos();
    }

    @Override
    public boolean seleccionarPelicula(Pelicula pelicula) {
        return control.seleccionarPelicula(pelicula);
    }

    @Override
    public boolean seleccionarHorario(Funcion funcion) {
        return control.seleccionarHorario(funcion);
    }

    @Override
    public boolean seleccionarAsiento(int fila, int columna) {
        return control.seleccionarAsiento(fila, columna);
    }

    @Override
    public boolean realizarPago(String numeroTarjeta) {
        return control.realizarPago(numeroTarjeta);
    }

    @Override
    public boolean confirmarPago() {
        return control.confirmarPago();
    }
}
