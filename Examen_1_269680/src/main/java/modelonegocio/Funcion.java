package modelonegocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Funcion {

    private Pelicula pelicula;
    private int sala;
    private Date fecha;
    private String horario;
    private double precioBoleto;
    private List<Asiento> asientos = new ArrayList<>();
    private List<Asiento> asientosSeleccionados = new ArrayList<>();
    private double importeTotal;

    public Funcion(Pelicula pelicula, int sala, Date fecha, String horario, double precioBoleto, int filas, int columnas) {
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.horario = horario;
        this.precioBoleto = precioBoleto;
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                asientos.add(new Asiento(fila, columna));
            }
        }
        pelicula.agregarFuncion(this);
    }

    public List<Asiento> obtenerAsientos() {
        return asientos;
    }

    public boolean seleccionarAsiento(int fila, int columna) {
        for (Asiento asiento : asientos) {
            if (asiento.getFila() == fila && asiento.getColumna() == columna) {
                if (asientosSeleccionados.contains(asiento)) {
                    asientosSeleccionados.remove(asiento);
                    calcularImporteTotal();
                    return true;
                }
                if (!asiento.estaDisponible()) {
                    return false;
                }
                asientosSeleccionados.add(asiento);
                calcularImporteTotal();
                return true;
            }
        }
        return false;
    }

    public double calcularImporteTotal() {
        importeTotal = precioBoleto * asientosSeleccionados.size();
        return importeTotal;
    }

    public void ocuparAsientosSeleccionados() {
        for (Asiento asiento : asientosSeleccionados) {
            asiento.ocupar();
        }
    }

    public void limpiarSeleccion() {
        asientosSeleccionados.clear();
        importeTotal = 0;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public int getSala() {
        return sala;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getHorario() {
        return horario;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public List<Asiento> getAsientosSeleccionados() {
        return asientosSeleccionados;
    }

    public double getImporteTotal() {
        return importeTotal;
    }
}