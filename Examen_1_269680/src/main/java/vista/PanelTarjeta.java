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
import modelonegocio.Boleto;
import modelonegocio.MetodoPago;

public class PanelTarjeta extends JPanel {

    private static final Color AZUL = new Color(0x255BC7);
    private static final Color BORDE = new Color(0xE2E5EA);
    private static final Color GRIS = new Color(0x6B7280);

    private Controlador controlador;

    public PanelTarjeta() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(16, 18, 16, 18)));
        setVisible(false);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void mostrarDatosTarjeta(MetodoPago tarjeta, double importe) {
        removeAll();
        if (tarjeta == null) {
            setVisible(false);
            revalidate();
            repaint();
            return;
        }
        JLabel titulo = new JLabel("Tarjeta para el pago");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        add(izq(titulo));
        add(Box.createVerticalStrut(10));
        add(izq(fila("Banco emisor", tarjeta.getBancoEmisor())));
        add(izq(fila("Tarjeta", "•••• " + tarjeta.getUltimosCuatroDigitos())));
        add(izq(fila("Tipo", tarjeta.getTipoTarjeta())));
        add(Box.createVerticalStrut(8));
        add(izq(new JSeparator()));
        add(Box.createVerticalStrut(8));
        JLabel nota = new JLabel(String.format(Locale.US, "Se cargarán $%.2f MXN a esta tarjeta.", importe));
        nota.setForeground(GRIS);
        add(izq(nota));
        add(Box.createVerticalStrut(10));
        JButton confirmar = new JButton("Confirmar pago");
        confirmar.setFocusPainted(false);
        confirmar.setOpaque(true);
        confirmar.setBackground(AZUL);
        confirmar.setForeground(Color.WHITE);
        confirmar.setBorder(new EmptyBorder(10, 10, 10, 10));
        confirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        confirmar.addActionListener(e -> controlador.confirmarPago());
        add(izq(confirmar));
        setVisible(true);
        revalidate();
        repaint();
    }

    public void mostrarBoletos(List<Boleto> boletos) {
        removeAll();
        if (boletos == null || boletos.isEmpty()) {
            setVisible(false);
            revalidate();
            repaint();
            return;
        }
        JLabel titulo = new JLabel("Boletos electrónicos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        JLabel nota = new JLabel("Presenta estos boletos al entrar a la sala.");
        nota.setForeground(GRIS);
        add(izq(titulo));
        add(Box.createVerticalStrut(4));
        add(izq(nota));
        add(Box.createVerticalStrut(10));
        for (Boleto boleto : boletos) {
            List<Asiento> ordenados = new ArrayList<>(boleto.getAsientos());
            ordenados.sort(Comparator.comparingInt(Asiento::getFila).thenComparingInt(Asiento::getColumna));
            StringBuilder asientos = new StringBuilder();
            for (Asiento asiento : ordenados) {
                if (asientos.length() > 0) {
                    asientos.append(", ");
                }
                asientos.append((char) ('A' + asiento.getFila())).append(asiento.getColumna() + 1);
            }
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
            tarjeta.setOpaque(false);
            tarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(10, 12, 10, 12)));
            JLabel pelicula = new JLabel(boleto.getPelicula());
            pelicula.setFont(pelicula.getFont().deriveFont(Font.BOLD, 14f));
            tarjeta.add(izq(pelicula));
            tarjeta.add(izq(new JLabel(new SimpleDateFormat("dd/MM/yyyy").format(boleto.getFecha()) + " · " + boleto.getHorario())));
            tarjeta.add(izq(new JLabel("Sala " + boleto.getSala())));
            tarjeta.add(izq(new JLabel("Asientos: " + asientos)));
            tarjeta.add(izq(fila("Monto pagado", String.format(Locale.US, "$%.2f MXN", boleto.getMontoPagado()))));
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
            add(izq(tarjeta));
            add(Box.createVerticalStrut(8));
        }
        setVisible(true);
        revalidate();
        repaint();
    }

    private JPanel fila(String etiqueta, String valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        JLabel izquierda = new JLabel(etiqueta);
        izquierda.setForeground(GRIS);
        fila.add(izquierda, BorderLayout.WEST);
        fila.add(new JLabel(valor), BorderLayout.EAST);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        return fila;
    }

    private JComponent izq(JComponent componente) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        return componente;
    }
}

