package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import modelonegocio.IConsultaModeloNegocio;
import modelonegocio.Pelicula;

public class PanelPeliculas extends JPanel {

    private static final Color AZUL = new Color(0x255BC7);
    private static final Color BORDE = new Color(0xE2E5EA);

    private Controlador controlador;
    private IConsultaModeloNegocio consultaModelo;
    private JPanel lista = new JPanel();

    public PanelPeliculas() {
        setLayout(new BorderLayout(0, 12));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(16, 18, 16, 18)));
        JLabel titulo = new JLabel("Películas");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setOpaque(false);
        add(titulo, BorderLayout.NORTH);
        add(lista, BorderLayout.CENTER);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setModeloNegocio(IConsultaModeloNegocio consultaModelo) {
        this.consultaModelo = consultaModelo;
    }

    public void mostrarListaPeliculas(List<Pelicula> peliculas) {
        lista.removeAll();
        Pelicula seleccionada = consultaModelo.getPeliculaSeleccionada();
        for (Pelicula pelicula : peliculas) {
            JButton boton = new JButton("<html><b>" + pelicula.getTitulo() + "</b><br><small>"
                    + pelicula.getClasificacion() + " · " + pelicula.getDuracion() + " min</small></html>");
            boolean activa = pelicula == seleccionada;
            boton.setHorizontalAlignment(SwingConstants.LEFT);
            boton.setFocusPainted(false);
            boton.setOpaque(true);
            boton.setBackground(activa ? new Color(0xEEF3FD) : Color.WHITE);
            boton.setBorder(BorderFactory.createCompoundBorder(new LineBorder(activa ? AZUL : BORDE), new EmptyBorder(8, 10, 8, 10)));
            boton.setAlignmentX(Component.LEFT_ALIGNMENT);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
            boton.addActionListener(e -> controlador.seleccionarPelicula(pelicula));
            lista.add(boton);
            lista.add(Box.createVerticalStrut(8));
        }
        revalidate();
        repaint();
    }
}

