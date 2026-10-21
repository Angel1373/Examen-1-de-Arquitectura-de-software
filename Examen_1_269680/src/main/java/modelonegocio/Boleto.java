package modelonegocio;

import java.util.Date;
import java.util.List;

public class Boleto {

    private String pelicula;
    private Date fecha;
    private String horario;
    private int sala;
    private List<Asiento> asientos;
    private double montoPagado;

    public Boleto(String pelicula, Date fecha, String horario, int sala, List<Asiento> asientos, double montoPagado) {
        this.pelicula = pelicula;
        this.fecha = fecha;
        this.horario = horario;
        this.sala = sala;
        this.asientos = asientos;
        this.montoPagado = montoPagado;
    }

    public String getPelicula() {
        return pelicula;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getHorario() {
        return horario;
    }

    public int getSala() {
        return sala;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public double getMontoPagado() {
        return montoPagado;
    }
}
