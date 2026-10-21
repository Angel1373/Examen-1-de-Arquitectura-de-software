package vista;

import controlador.Controlador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import modelonegocio.Funcion;
import modelonegocio.IConsultaModeloNegocio;
import modelonegocio.Pelicula;

public class PanelDetalle extends JPanel {

    private static final Color AZUL = new Color(0x255BC7);
    private static final Color BORDE = new Color(0xE2E5EA);

    private Controlador controlador;
    private IConsultaModeloNegocio consultaModelo;

    public PanelDetalle() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(16, 18, 16, 18)));
        setVisible(false);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setModeloNegocio(IConsultaModeloNegocio consultaModelo) {
        this.consultaModelo = consultaModelo;
    }

    public void mostrarDetallePelicula(Pelicula pelicula, List<Funcion> horarios) {
        removeAll();
        if (pelicula == null) {
            setVisible(false);
            revalidate();
            repaint();
            return;
        }
        JLabel titulo = new JLabel("Detalle de la película");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        JLabel nombre = new JLabel(pelicula.getTituloCompleto());
        nombre.setFont(nombre.getFont().deriveFont(Font.BOLD, 22f));
        JLabel sinopsis = new JLabel("<html><div style='width:430px;color:#6b7280'>" + pelicula.getSinopsis() + "</div></html>");
        JLabel datos = new JLabel("Clasificación " + pelicula.getClasificacion() + " · Duración: " + pelicula.getDuracion() + " minutos");
        add(izq(titulo));
        add(Box.createVerticalStrut(10));
        add(izq(nombre));
        add(Box.createVerticalStrut(6));
        add(izq(sinopsis));
        add(Box.createVerticalStrut(6));
        add(izq(datos));
        if (horarios != null && !horarios.isEmpty()) {
            String fecha = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "MX")).format(horarios.get(0).getFecha());
            add(Box.createVerticalStrut(12));
            add(izq(new JLabel(Character.toUpperCase(fecha.charAt(0)) + fecha.substring(1))));
            add(Box.createVerticalStrut(6));
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            fila.setOpaque(false);
            Funcion seleccionada = consultaModelo.getFuncionSeleccionada();
            for (Funcion funcion : horarios) {
                JButton boton = new JButton(funcion.getHorario());
                boolean activa = funcion == seleccionada;
                boton.setFocusPainted(false);
                boton.setOpaque(true);
                boton.setBackground(activa ? AZUL : Color.WHITE);
                boton.setForeground(activa ? Color.WHITE : Color.BLACK);
                boton.setBorder(BorderFactory.createCompoundBorder(new LineBorder(activa ? AZUL : BORDE), new EmptyBorder(8, 18, 8, 18)));
                boton.addActionListener(e -> controlador.seleccionarHorario(funcion));
                fila.add(boton);
            }
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            add(izq(fila));
        }
        setVisible(true);
        revalidate();
        repaint();
    }

    private JComponent izq(JComponent componente) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        return componente;
    }
}

