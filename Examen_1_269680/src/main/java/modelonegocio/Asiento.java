package modelonegocio;

public class Asiento {

    private int fila;
    private int columna;
    private boolean ocupado;

    public Asiento(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public boolean estaDisponible() {
        return !ocupado;
    }

    public void ocupar() {
        ocupado = true;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
