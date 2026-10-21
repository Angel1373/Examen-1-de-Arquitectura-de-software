package controlador;

import modelonegocio.Funcion;
import modelonegocio.IOperacionesModeloNegocio;
import modelonegocio.Pelicula;

public class Controlador {

    private IOperacionesModeloNegocio modelo;

    public void setModelo(IOperacionesModeloNegocio modelo) {
        this.modelo = modelo;
    }

    public void seleccionarPelicula(Pelicula pelicula) {
        modelo.seleccionarPelicula(pelicula);
    }

    public void seleccionarHorario(Funcion funcion) {
        modelo.seleccionarHorario(funcion);
    }

    public void seleccionarAsiento(int fila, int columna) {
        modelo.seleccionarAsiento(fila, columna);
    }

    public void realizarPago(String numeroTarjeta) {
        modelo.realizarPago(numeroTarjeta);
    }

    public void confirmarPago() {
        modelo.confirmarPago();
    }
}
