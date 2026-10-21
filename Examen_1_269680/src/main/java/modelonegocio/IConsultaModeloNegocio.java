package modelonegocio;

import java.util.List;

public interface IConsultaModeloNegocio {

    List<Pelicula> getPeliculas();

    Pelicula getPeliculaSeleccionada();

    List<Funcion> getHorariosDisponibles();

    Funcion getFuncionSeleccionada();

    List<Asiento> getAsientos();

    List<Asiento> getAsientosSeleccionados();

    double getImporteTotal();

    MetodoPago getDatosTarjeta();

    List<Boleto> getBoletos();
}