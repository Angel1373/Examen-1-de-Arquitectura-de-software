package modelonegocio;

public interface IOperacionesModeloNegocio {

    boolean seleccionarPelicula(Pelicula pelicula);

    boolean seleccionarHorario(Funcion funcion);

    boolean seleccionarAsiento(int fila, int columna);

    boolean realizarPago(String numeroTarjeta);

    boolean confirmarPago();
}
