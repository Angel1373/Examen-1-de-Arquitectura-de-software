package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PanelDialogoTarjeta extends JDialog {

    private static final Color AZUL = new Color(0x255BC7);
    private static final Color BORDE = new Color(0xE2E5EA);
    private static final Color GRIS = new Color(0x6B7280);

    private JLabel descripcion = new JLabel();
    private JTextField campo = new JTextField();
    private String resultado;

    public PanelDialogoTarjeta(JFrame propietario) {
        super(propietario, "Ingresa tu tarjeta", true);
        JLabel titulo = new JLabel("Ingresa tu tarjeta");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        descripcion.setForeground(GRIS);
        JLabel etiqueta = new JLabel("Número de tarjeta");
        campo.setPreferredSize(new Dimension(380, 36));
        JLabel nota = new JLabel("Datos ficticios para este ejemplo.");
        nota.setForeground(GRIS);
        nota.setFont(nota.getFont().deriveFont(11f));
        JButton cancelar = new JButton("Cancelar");
        cancelar.setFocusPainted(false);
        cancelar.setOpaque(true);
        cancelar.setBackground(Color.WHITE);
        cancelar.setBorder(BorderFactory.createCompoundBorder(new LineBorder(BORDE), new EmptyBorder(10, 10, 10, 10)));
        cancelar.addActionListener(e -> {
            resultado = null;
            setVisible(false);
        });
        JButton continuar = new JButton("Continuar");
        continuar.setFocusPainted(false);
        continuar.setOpaque(true);
        continuar.setBackground(AZUL);
        continuar.setForeground(Color.WHITE);
        continuar.setBorder(new EmptyBorder(10, 10, 10, 10));
        continuar.addActionListener(e -> {
            resultado = campo.getText();
            setVisible(false);
        });
        JPanel textos = new JPanel(new GridLayout(5, 1, 0, 6));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(descripcion);
        textos.add(etiqueta);
        textos.add(campo);
        textos.add(nota);
        JPanel botones = new JPanel(new GridLayout(1, 2, 12, 0));
        botones.setOpaque(false);
        botones.add(cancelar);
        botones.add(continuar);
        JPanel contenido = new JPanel(new BorderLayout(0, 16));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(new EmptyBorder(20, 24, 20, 24));
        contenido.add(textos, BorderLayout.CENTER);
        contenido.add(botones, BorderLayout.SOUTH);
        setContentPane(contenido);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
    }

    public String solicitarNumeroTarjeta(double importe) {
        descripcion.setText(String.format(Locale.US, "Ingresa el número de tarjeta para continuar con el pago de $%.2f MXN.", importe));
        campo.setText("4242 4242 4242 4242");
        resultado = null;
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
        return resultado;
    }
}

