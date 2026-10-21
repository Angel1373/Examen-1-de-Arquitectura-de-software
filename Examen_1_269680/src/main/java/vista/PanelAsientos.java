package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import modelonegocio.Asiento;
import modelonegocio.Funcion;

public class PanelAsientos extends JPanel {

    private static final Color AZUL = new Color(0x255BC7);
    private static final Color BORDE = new Color(0xE2E5EA);
    private static final Color OCUPADO = new Color(0xDADDE3);

    private Controlador controlador;

    public PanelAsientos() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(16, 18, 16, 18)));
        setVisible(false);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void mostrarDistribucionAsientos(Funcion funcion, List<Asiento> asientos) {
        removeAll();
        if (funcion == null || asientos == null || asientos.isEmpty()) {
            setVisible(false);
            revalidate();
            repaint();
            return;
        }
        JLabel titulo = new JLabel("Selecciona tus asientos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);
        info.add(new JLabel("Sala " + funcion.getSala() + " · " + funcion.getHorario()), BorderLayout.WEST);
        info.add(new JLabel(String.format(Locale.US, "$%.2f MXN por boleto", funcion.getPrecioBoleto())), BorderLayout.EAST);
        info.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        JLabel pantalla = new JLabel("PANTALLA", SwingConstants.CENTER);
        pantalla.setPreferredSize(new Dimension(380, 30));
        pantalla.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, OCUPADO));
        JPanel filaPantalla = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaPantalla.setOpaque(false);
        filaPantalla.add(pantalla);

        int filas = 0;
        int columnas = 0;
        for (Asiento asiento : asientos) {
            filas = Math.max(filas, asiento.getFila() + 1);
            columnas = Math.max(columnas, asiento.getColumna() + 1);
        }
        JPanel cuadricula = new JPanel(new GridLayout(filas, columnas + 1, 6, 6));
        cuadricula.setOpaque(false);
        for (int fila = 0; fila < filas; fila++) {
            JLabel letra = new JLabel(String.valueOf((char) ('A' + fila)), SwingConstants.CENTER);
            letra.setPreferredSize(new Dimension(30, 32));
            cuadricula.add(letra);
            for (int columna = 0; columna < columnas; columna++) {
                Asiento asiento = asientos.get(fila * columnas + columna);
                JButton boton = new JButton(etiqueta(asiento));
                boolean seleccionado = funcion.getAsientosSeleccionados().contains(asiento);
                boton.setPreferredSize(new Dimension(42, 32));
                boton.setMargin(new java.awt.Insets(0, 0, 0, 0));
                boton.setFocusPainted(false);
                boton.setOpaque(true);
                boton.setFont(boton.getFont().deriveFont(11f));
                if (seleccionado) {
                    boton.setBackground(AZUL);
                    boton.setForeground(Color.WHITE);
                    boton.setBorder(new LineBorder(AZUL));
                } else if (!asiento.estaDisponible()) {
                    boton.setBackground(OCUPADO);
                    boton.setBorder(new LineBorder(OCUPADO));
                    boton.setEnabled(false);
                } else {
                    boton.setBackground(Color.WHITE);
                    boton.setBorder(new LineBorder(BORDE));
                }
                boton.addActionListener(e -> controlador.seleccionarAsiento(asiento.getFila(), asiento.getColumna()));
                cuadricula.add(boton);
            }
        }
        JPanel filaCuadricula = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaCuadricula.setOpaque(false);
        filaCuadricula.add(cuadricula);
        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        leyenda.setOpaque(false);
        leyenda.add(chip(Color.WHITE, BORDE, "Disponible"));
        leyenda.add(chip(OCUPADO, OCUPADO, "Ocupado"));
        leyenda.add(chip(AZUL, AZUL, "Seleccionado"));

        add(izq(titulo));
        add(Box.createVerticalStrut(8));
        add(izq(info));
        add(Box.createVerticalStrut(8));
        add(izq(filaPantalla));
        add(izq(filaCuadricula));
        add(Box.createVerticalStrut(8));
        add(izq(leyenda));
        setVisible(true);
        revalidate();
        repaint();
    }

    private JPanel chip(Color relleno, Color borde, String texto) {
        JPanel cuadro = new JPanel();
        cuadro.setPreferredSize(new Dimension(14, 14));
        cuadro.setBackground(relleno);
        cuadro.setBorder(new LineBorder(borde));
        JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        chip.setOpaque(false);
        chip.add(cuadro);
        chip.add(new JLabel(texto));
        return chip;
    }

    private String etiqueta(Asiento asiento) {
        return String.valueOf((char) ('A' + asiento.getFila())) + (asiento.getColumna() + 1);
    }

    private JComponent izq(JComponent componente) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        return componente;
    }
}
