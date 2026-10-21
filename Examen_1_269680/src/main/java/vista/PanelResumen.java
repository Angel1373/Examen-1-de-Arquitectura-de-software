package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import modelonegocio.Asiento;
import modelonegocio.Funcion;

public class PanelResumen extends JPanel {

    private static final Color AZUL = new Color(0x255BC7);
    private static final Color BORDE = new Color(0xE2E5EA);
    private static final Color GRIS = new Color(0x6B7280);

    private Controlador controlador;
    private Runnable accionPago;

    public PanelResumen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(16, 18, 16, 18)));
        setVisible(false);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setAccionPago(Runnable accionPago) {
        this.accionPago = accionPago;
    }

    public void mostrarResumenCompra(Funcion funcion, int paso) {
        removeAll();
        if (funcion == null) {
            setVisible(false);
            revalidate();
            repaint();
            return;
        }
        List<Asiento> seleccionados = new ArrayList<>(funcion.getAsientosSeleccionados());
        seleccionados.sort(Comparator.comparingInt(Asiento::getFila).thenComparingInt(Asiento::getColumna));
        StringBuilder asientos = new StringBuilder();
        for (Asiento asiento : seleccionados) {
            if (asientos.length() > 0) {
                asientos.append(", ");
            }
            asientos.append((char) ('A' + asiento.getFila())).append(asiento.getColumna() + 1);
        }
        JLabel titulo = new JLabel("Resumen de compra");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        JLabel pelicula = new JLabel(funcion.getPelicula().getTitulo());
        pelicula.setFont(pelicula.getFont().deriveFont(Font.BOLD, 18f));
        add(izq(titulo));
        add(Box.createVerticalStrut(10));
        add(izq(pelicula));
        add(Box.createVerticalStrut(6));
        add(izq(fila("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(funcion.getFecha()), false)));
        add(izq(fila("Horario", funcion.getHorario(), false)));
        add(izq(fila("Sala", String.valueOf(funcion.getSala()), false)));
        add(izq(fila("Asientos", asientos.toString(), false)));
        add(izq(fila("Boletos", String.valueOf(seleccionados.size()), false)));
        add(izq(fila("Precio por boleto", String.format(Locale.US, "$%.2f MXN", funcion.getPrecioBoleto()), false)));
        add(Box.createVerticalStrut(8));
        add(izq(new JSeparator()));
        add(Box.createVerticalStrut(8));
        add(izq(fila(paso == 4 ? "Monto pagado" : "Total", String.format(Locale.US, "$%.2f MXN", funcion.getImporteTotal()), true)));
        add(Box.createVerticalStrut(10));
        if (paso == 1) {
            JButton pagar = new JButton("Realizar pago");
            pagar.setFocusPainted(false);
            pagar.setOpaque(true);
            pagar.setBackground(AZUL);
            pagar.setForeground(Color.WHITE);
            pagar.setBorder(new EmptyBorder(10, 10, 10, 10));
            pagar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
            pagar.addActionListener(e -> {
                if (accionPago != null) {
                    accionPago.run();
                }
            });
            add(izq(pagar));
        } else if (paso == 3) {
            JLabel nota = new JLabel("Confirma el pago en el panel de tarjeta.");
            nota.setForeground(GRIS);
            add(izq(nota));
        } else if (paso == 4) {
            JLabel nota = new JLabel("Pago confirmado");
            nota.setForeground(AZUL);
            add(izq(nota));
        }
        setVisible(true);
        revalidate();
        repaint();
    }

    private JPanel fila(String etiqueta, String valor, boolean destacada) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        JLabel izquierda = new JLabel(etiqueta);
        JLabel derecha = new JLabel(valor);
        if (destacada) {
            izquierda.setFont(izquierda.getFont().deriveFont(Font.BOLD));
            derecha.setFont(derecha.getFont().deriveFont(Font.BOLD, 20f));
        } else {
            izquierda.setForeground(GRIS);
        }
        fila.add(izquierda, BorderLayout.WEST);
        fila.add(derecha, BorderLayout.EAST);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, destacada ? 34 : 26));
        return fila;
    }

    private JComponent izq(JComponent componente) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        return componente;
    }
}

