package modelonegocio;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {

    private String titulo;
    private String tituloCompleto;
    private String sinopsis;
    private String clasificacion;
    private int duracion;
    private List<Funcion> funciones = new ArrayList<>();

    public Pelicula(String titulo, String tituloCompleto, String sinopsis, String clasificacion, int duracion) {
        this.titulo = titulo;
        this.tituloCompleto = tituloCompleto;
        this.sinopsis = sinopsis;
        this.clasificacion = clasificacion;
        this.duracion = duracion;
    }

    void agregarFuncion(Funcion funcion) {
        funciones.add(funcion);
    }

    public List<Funcion> obtenerHorariosDisponibles() {
        return funciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTituloCompleto() {
        return tituloCompleto;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public int getDuracion() {
        return duracion;
    }
}

