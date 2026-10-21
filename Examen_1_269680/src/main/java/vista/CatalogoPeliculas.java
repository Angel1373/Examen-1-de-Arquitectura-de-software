package vista;

import controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modelonegocio.Funcion;
import modelonegocio.IConsultaModeloNegocio;
import utilerias.Observador;

public class CatalogoPeliculas extends JFrame implements Observador {

    private static final String[] PASOS = {"", "1. Selección", "2. Datos de tarjeta", "3. Revisión del pago", "4. Compra finalizada"};
    private static final String[] SUBTITULOS = {
            "",
            "Elige una película, un horario y tus asientos.",
            "Elige una película, un horario y tus asientos.",
            "Revisa tu selección y los datos de la tarjeta antes de confirmar.",
            "Tu pago se realizó correctamente. Estos son tus boletos electrónicos."
    };

    private Controlador controlador;
    private IConsultaModeloNegocio consultaModelo;
    private PanelPeliculas panelPeliculas = new PanelPeliculas();
    private PanelDetalle panelDetalle = new PanelDetalle();
    private PanelAsientos panelAsientos = new PanelAsientos();
    private PanelResumen panelResumen = new PanelResumen();
    private PanelTarjeta panelTarjeta = new PanelTarjeta();
    private PanelDialogoTarjeta panelDialogoTarjeta;
    private JLabel etiquetaPaso = new JLabel();
    private JLabel etiquetaSubtitulo = new JLabel();

    public CatalogoPeliculas() {
        super("Compra de boletos");
        panelDialogoTarjeta = new PanelDialogoTarjeta(this);
        panelResumen.setAccionPago(this::solicitarTarjeta);
        Color fondo = new Color(0xF5F6F8);
        JLabel titulo = new JLabel("Compra de boletos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        etiquetaPaso.setForeground(new Color(0x6B7280));
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        encabezado.setBorder(new EmptyBorder(16, 24, 16, 24));
        encabezado.add(titulo, BorderLayout.WEST);
        encabezado.add(etiquetaPaso, BorderLayout.EAST);
        etiquetaSubtitulo.setForeground(new Color(0x6B7280));
        etiquetaSubtitulo.setBorder(new EmptyBorder(0, 0, 12, 0));
        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setBackground(fondo);
        cuerpo.setBorder(new EmptyBorder(16, 24, 16, 24));
        agregarColumna(cuerpo, columna(panelPeliculas), 0, 240, 0);
        agregarColumna(cuerpo, columna(panelDetalle, panelAsientos), 1, 560, 1);
        agregarColumna(cuerpo, columna(panelResumen, panelTarjeta), 2, 340, 0);
        JPanel central = new JPanel(new BorderLayout());
        central.setBackground(fondo);
        central.setBorder(new EmptyBorder(12, 24, 0, 24));
        central.add(etiquetaSubtitulo, BorderLayout.NORTH);
        central.add(cuerpo, BorderLayout.CENTER);
        cuerpo.setBorder(new EmptyBorder(0, 0, 16, 0));
        setLayout(new BorderLayout());
        add(encabezado, BorderLayout.NORTH);
        add(central, BorderLayout.CENTER);
        getContentPane().setBackground(fondo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 900);
        setLocationRelativeTo(null);
        mostrarPaso(1);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        panelPeliculas.setControlador(controlador);
        panelDetalle.setControlador(controlador);
        panelAsientos.setControlador(controlador);
        panelResumen.setControlador(controlador);
        panelTarjeta.setControlador(controlador);
    }

    public void setModeloNegocio(IConsultaModeloNegocio consulta) {
        this.consultaModelo = consulta;
        panelPeliculas.setModeloNegocio(consulta);
        panelDetalle.setModeloNegocio(consulta);
        panelPeliculas.mostrarListaPeliculas(consulta.getPeliculas());
        mostrarPaso(calcularPaso());
    }

    @Override
    public void actualizar() {
        Funcion funcion = consultaModelo.getFuncionSeleccionada();
        int paso = calcularPaso();
        panelPeliculas.mostrarListaPeliculas(consultaModelo.getPeliculas());
        panelDetalle.mostrarDetallePelicula(consultaModelo.getPeliculaSeleccionada(), consultaModelo.getHorariosDisponibles());
        panelAsientos.mostrarDistribucionAsientos(funcion, funcion == null ? null : consultaModelo.getAsientos());
        boolean hayAsientos = funcion != null && !consultaModelo.getAsientosSeleccionados().isEmpty();
        panelResumen.mostrarResumenCompra(hayAsientos ? funcion : null, paso);
        if (paso == 4) {
            panelTarjeta.mostrarBoletos(consultaModelo.getBoletos());
        } else {
            panelTarjeta.mostrarDatosTarjeta(consultaModelo.getDatosTarjeta(), consultaModelo.getImporteTotal());
        }
        mostrarPaso(paso);
        revalidate();
        repaint();
    }

    private void solicitarTarjeta() {
        mostrarPaso(2);
        String numero = panelDialogoTarjeta.solicitarNumeroTarjeta(consultaModelo.getImporteTotal());
        mostrarPaso(calcularPaso());
        if (numero == null) {
            return;
        }
        controlador.realizarPago(numero);
    }

    private int calcularPaso() {
        if (consultaModelo == null) {
            return 1;
        }
        if (!consultaModelo.getBoletos().isEmpty()) {
            return 4;
        }
        if (consultaModelo.getDatosTarjeta() != null) {
            return 3;
        }
        return 1;
    }

    private void mostrarPaso(int paso) {
        etiquetaPaso.setText(PASOS[paso]);
        etiquetaSubtitulo.setText(SUBTITULOS[paso]);
    }

    private JPanel columna(JComponent... componentes) {
        JPanel columna = new JPanel(new GridBagLayout());
        columna.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 12, 0);
        c.anchor = GridBagConstraints.NORTH;
        int fila = 0;
        for (JComponent componente : componentes) {
            c.gridy = fila++;
            columna.add(componente, c);
        }
        GridBagConstraints relleno = new GridBagConstraints();
        relleno.gridx = 0;
        relleno.gridy = fila;
        relleno.weighty = 1;
        columna.add(new JPanel() {{ setOpaque(false); }}, relleno);
        return columna;
    }

    private void agregarColumna(JPanel cuerpo, JPanel columna, int posicion, int ancho, double pesoX) {
        columna.setPreferredSize(new Dimension(ancho, 600));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = posicion;
        c.gridy = 0;
        c.weightx = pesoX;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, posicion == 0 ? 0 : 16, 0, 0);
        cuerpo.add(columna, c);
    }
}

