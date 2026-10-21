package ensamblador;

import controlador.Controlador;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import modelonegocio.Asiento;
import modelonegocio.FachadaModeloNegocio;
import modelonegocio.Funcion;
import modelonegocio.Pelicula;
import vista.CatalogoPeliculas;

public class Ensamblador {

    public void ensamblar() {
        FachadaModeloNegocio modelo = new FachadaModeloNegocio(crearPeliculas());
        Controlador controlador = new Controlador();
        controlador.setModelo(modelo);
        CatalogoPeliculas vista = new CatalogoPeliculas();
        vista.setControlador(controlador);
        vista.setModeloNegocio(modelo);
        modelo.agregarObservador(vista);
        vista.setVisible(true);
    }

    private List<Pelicula> crearPeliculas() {
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(new Pelicula("El último viaje", "El último viaje",
                "Tras encontrar una carta de su padre, Elena emprende un viaje en tren para reencontrarse con su familia y resolver una promesa pendiente.", "B", 112));
        peliculas.add(new Pelicula("Un verano en casa", "Un verano en casa",
                "Tres hermanos regresan a la casa de su infancia para pasar juntos unas vacaciones que cambiarán la forma en que se ven.", "A", 98));
        peliculas.add(new Pelicula("La señal", "La señal",
                "Un técnico de radio recibe un mensaje misterioso desde una estación abandonada y decide descubrir quién lo envía.", "B15", 106));
        peliculas.add(new Pelicula("Más allá del cielo", "Más allá del cielo",
                "Una joven piloto se une a una expedición para cruzar una cordillera que nadie ha logrado sobrevolar.", "A", 120));
        int[] salas = {3, 1, 2, 4};
        String[] horarios = {"15:00", "18:30", "21:00"};
        Date fecha = new GregorianCalendar(2026, Calendar.OCTOBER, 20).getTime();
        for (int i = 0; i < peliculas.size(); i++) {
            for (int j = 0; j < horarios.length; j++) {
                Funcion funcion = new Funcion(peliculas.get(i), salas[i], fecha, horarios[j], 85.0, 6, 8);
                for (Asiento asiento : funcion.obtenerAsientos()) {
                    if ((asiento.getFila() * 3 + asiento.getColumna() * 5 + i + j) % 4 == 0) {
                        asiento.ocupar();
                    }
                }
            }
        }
        return peliculas;
    }
}