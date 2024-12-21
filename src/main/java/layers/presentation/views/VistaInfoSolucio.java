package layers.presentation.views;

import layers.domain.utils.Pair;
import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VistaInfoSolucio extends VistaGenerica {

    private String errEstatTemplate = "Error %s: L'%s es %s pero hauria de ser %s";

    private enum EstatGeneral {
        INICIAL,
        VISUALITZAR,
        EDITAR,
    }
    private EstatGeneral estatGeneral;

    private enum EstatProducte {    // Nomes si estatGeneral = EDITAR
        INICIAL,
        SELECCIONAR,
        SELECCIONAT,
        CONFIRMAT,
    }
    private EstatProducte estatProducte1;
    private EstatProducte estatProducte2;

    private Pair<Integer, Integer> producte1Seleccionat = new Pair<Integer, Integer>(-1, -1);
    private Pair<Integer, Integer> producte2Seleccionat = new Pair<Integer, Integer>(-1, -1);

    private int files;
    private int columnes;
    private int columnesUltimaFila;

    private final CtrlVistaSolucions ctrlVistaSolucions;

    protected List<List<String>> productes;

    protected String templateTextEtiquetaTitol = "Productes de la solucio %s";
    protected String nomSolucio;
    protected JLabel etiquetaTitol;

    protected JScrollPane scrollPane;
    protected ModelTaula modelTaula;
    protected JTable taulaCataleg;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    // Els components del panel de classe (this). Segueixen els estats de EstatGeneral
    protected String textEtiquetaEstatGeneralEnVisualitzar = "Actualment estas en l'estat Visualitzar";
    protected String textEtiquetaEstatGeneralEnEditar = "Actualment estas en l'estat Editar";
    protected JLabel etiquetaEstatGeneral;
    protected String textBotoCanviEstatGeneralEnVisualitzar = "Mode Editar";
    protected String textBotoCanviEstatGeneralEnEditar = "Mode Visualitzar";
    protected Boto botoCanviEstatGeneral;
    protected String textBotoEliminarSolucio = "Eliminar i Sortir";
    protected Boto botoEliminarSolucio;

    protected JPanel panelEdicio;

    // Els components del panelEdicio. Segueixen els estats de EstatProducte
    protected String textEtiquetaProducteEnInicial = "Prem el boto per poder seleccionar el producte";
    protected String textEtiquetaProducteEnSeleccionar = "Clica un producte de la taula";

    protected JPanel panelSeleccioPrimerProducte;
    protected JLabel etiquetaPrimerProducte;
    protected String textBotoPrimerProducteEnInicial = "Triar producte1";
    protected String textBotoPrimerProducteEnSeleccionat = "Confirmar producte1";
    protected Boto botoPrimerProducte;

    protected String textBotoIntercanviar = "Intercanviar";
    protected Boto botoIntercanviar;

    protected JPanel panelSeleccioSegonProducte;
    protected JLabel etiquetaSegonProducte;
    protected String textBotoSegonProducteEnInicial = "Triar producte2";
    protected String textBotoSegonProducteEnSeleccionat = "Confirmar producte2";
    protected Boto botoSegonProducte;

    public VistaInfoSolucio(CtrlVistaSolucions ctrl) {
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl;
    }

    public void executar(List<List<String>> productes, String nomSolcuio) {
        this.nomSolucio = nomSolcuio;
        this.productes = productes;
        titolFrame = "Informacio de la solucio";
        ajuda = "Estas a la vista on es mostra una solucio. Des d'aquesta vista pots provar qualsevol de les quatre \n" +
                "funcionalitats utilitzant els botons corresponents i clicant els productes sobre la taula.\n" +
                "Veure la solucio: Amb l'estat de visualitzar pots veure la distribucio de la prestatgeria.\n" +
                "Editar la solucio: Permet intercanviar productes de la distribucio de la prestatgeria.\n" +
                "Eliminar la solucio: Permet eliminar la distribucio.\n" +
                "Tornar: Permet tornar a la vista principal.\n" +
                "Sortir: Finalitza l'aplicacio.";

        files = this.productes.size();
        if (files > 0) {
            columnes = this.productes.getFirst().size();
            columnesUltimaFila = this.productes.getLast().size();
        }
        else {
            columnes = 0;
            columnesUltimaFila = 0;
        }

        super.executar();
    }

    protected void inicialitzarComponents() {

        width = 1000;
        height = 600;
        teBotoTornar = true;

        // Inicialitzem la superclase
        super.inicialitzarComponents();

        estatGeneral = EstatGeneral.INICIAL;

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem l'etiqueta descriptiva
        String textEtiquetaTitol = String.format(templateTextEtiquetaTitol, nomSolucio);
        etiquetaTitol = new JLabel(textEtiquetaTitol);
        etiquetaTitol.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaTitol);

        // Inicialitzem la taula amb els productes
        modelTaula = new ModelTaula(); // Model que gestiona l'ED de la taula
        taulaCataleg = new JTable(modelTaula);

        taulaCataleg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Perque el width no s'ajusti automaticament
        taulaCataleg.getTableHeader().setReorderingAllowed(false); // Per prohibir moure les columnes
        taulaCataleg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Per fer seleccio unica
        taulaCataleg.setCellSelectionEnabled(true); // Per fer seleccio en caselles individuals

        // Afegim els Listeners de la taula
        taulaCataleg.getSelectionModel().addListSelectionListener(new SharedRowSelectionHandler());
        taulaCataleg.getColumnModel().getSelectionModel().addListSelectionListener(new SharedColumnSelectionHandler());

        // Fiquem la taula dins un JScrollPane
        scrollPane = new JScrollPane(taulaCataleg);
        add(scrollPane);

        // Inicialitzem etiqueta i boto de canvi d'estat
        etiquetaEstatGeneral = new JLabel();
        etiquetaEstatGeneral.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaEstatGeneral);
        botoCanviEstatGeneral = new Boto();
        botoCanviEstatGeneral.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoCanviEstatGeneral);

        // Inicialitzem el boto d'eliminar
        add(Box.createRigidArea(new Dimension(0, 10)));
        botoEliminarSolucio = new Boto(textBotoEliminarSolucio);
        botoEliminarSolucio.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoEliminarSolucio);

        // A partir d'aqui vindra el panel d'edicio (visible sii estatEditar = true). Afegim una separacio
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(Box.createVerticalGlue());

        // Inicialitzem el panel d'edicio que permet intercanviar productes
        // Estara compost horitzontalment per:
        // El panel de seleccionar el primer producte
        // Un boto d'intercanvi
        // El panel de seleccionar el segon producte
        panelEdicio = new JPanel();
        panelEdicio.setLayout(new BoxLayout(panelEdicio, BoxLayout.LINE_AXIS));

        // Inicialitzem el panel per seleccionar el primer producte. Te una etiqueta i un boto
        panelSeleccioPrimerProducte = new JPanel();
        panelSeleccioPrimerProducte.setLayout(new BoxLayout(panelSeleccioPrimerProducte, BoxLayout.PAGE_AXIS));
        panelSeleccioPrimerProducte.add(Box.createVerticalGlue());
        etiquetaPrimerProducte = new JLabel();
        etiquetaPrimerProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioPrimerProducte.add(etiquetaPrimerProducte);
        botoPrimerProducte = new Boto();
        botoPrimerProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioPrimerProducte.add(botoPrimerProducte);

        panelEdicio.add(panelSeleccioPrimerProducte);
        panelEdicio.add(Box.createRigidArea(new Dimension(15, 0)));
        panelEdicio.add(Box.createHorizontalGlue());

        // Inicialitzem el boto d'intercanviar
        botoIntercanviar = new Boto(textBotoIntercanviar);
        botoIntercanviar.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        panelEdicio.add(botoIntercanviar);

        panelEdicio.add(Box.createHorizontalGlue());
        panelEdicio.add(Box.createRigidArea(new Dimension(15, 0)));

        // Inicialitzem el panel per seleccionar el segon producte. Te una etiqueta i un boto
        panelSeleccioSegonProducte = new JPanel();
        panelSeleccioSegonProducte.setLayout(new BoxLayout(panelSeleccioSegonProducte, BoxLayout.PAGE_AXIS));
        panelSeleccioSegonProducte.add(Box.createVerticalGlue());
        etiquetaSegonProducte = new JLabel();
        etiquetaSegonProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioSegonProducte.add(etiquetaSegonProducte);
        botoSegonProducte = new Boto();
        botoSegonProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioSegonProducte.add(botoSegonProducte);

        panelEdicio.add(panelSeleccioSegonProducte);
        canviEstatGeneralAVisualitzar();

        add(panelEdicio);
    }

    protected void canviEstatGeneralAVisualitzar() {
        String estatGeneralDesitjat = EstatGeneral.INICIAL + " o " + EstatGeneral.EDITAR;
        String err = String.format(errEstatTemplate, "canviEstatGeneralAVisualitzar", "estatGeneral", estatGeneral, estatGeneralDesitjat);
        if (estatGeneral != EstatGeneral.INICIAL && estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        estatGeneral = EstatGeneral.VISUALITZAR;

        etiquetaEstatGeneral.setText(textEtiquetaEstatGeneralEnVisualitzar);
        botoCanviEstatGeneral.setText(textBotoCanviEstatGeneralEnVisualitzar);

        panelEdicio.setVisible(false);
    }

    protected void canviEstatGeneralAEditar() {
        String err = String.format(errEstatTemplate, "canviEstatGeneralAEditar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.VISUALITZAR) {
            System.err.println(err);
            return;
        }

        estatGeneral = EstatGeneral.EDITAR;

        etiquetaEstatGeneral.setText(textEtiquetaEstatGeneralEnEditar);
        botoCanviEstatGeneral.setText(textBotoCanviEstatGeneralEnEditar);

        canviEstatProducte1AInicial();
        canviEstatProducte2AInicial();

        botoIntercanviar.setVisible(false);

        panelEdicio.setVisible(true);
    }

    protected void canviEstatProducte1AInicial() {
        estatProducte1 = EstatProducte.INICIAL;
        etiquetaPrimerProducte.setText(textEtiquetaProducteEnInicial);
        botoPrimerProducte.setText(textBotoPrimerProducteEnInicial);
        botoPrimerProducte.setVisible(true);
    }

    protected void canviEstatProducte2AInicial() {
        estatProducte2 = EstatProducte.INICIAL;
        etiquetaSegonProducte.setText(textEtiquetaProducteEnInicial);
        botoSegonProducte.setText(textBotoSegonProducteEnInicial);
        botoSegonProducte.setVisible(true);
    }

    protected void canviEstatProducte1ASeleccionar() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatProducte1", estatProducte1, EstatProducte.INICIAL);
        if (estatProducte1 != EstatProducte.INICIAL) {
            System.err.println(err);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatProducte2", estatProducte2, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        estatProducte1 = EstatProducte.SELECCIONAR;
        botoPrimerProducte.setVisible(false);
        etiquetaPrimerProducte.setText(textEtiquetaProducteEnSeleccionar);
    }

    protected void canviEstatProducte1ASeleccionat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatProducte1", estatProducte1, EstatProducte.SELECCIONAR);
        if (estatProducte1 != EstatProducte.SELECCIONAR) {
            System.err.println(err);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatProducte2", estatProducte1, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        if (celaFantasma(filaSeleccionada, filaSeleccionada)) {
            System.err.println("Casella fantasma");
            return;
        }

        estatProducte1 = EstatProducte.SELECCIONAT;
        actualitzaProducte1Seleccionat();
        botoPrimerProducte.setText(textBotoPrimerProducteEnSeleccionat);
        botoPrimerProducte.setVisible(true);
    }

    protected void canviEstatProducte1AConfirmat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatProducte1", estatProducte1, EstatProducte.SELECCIONAT);
        if (estatProducte1 != EstatProducte.SELECCIONAT) {
            System.err.println(err);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatProducte2", estatProducte1, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        estatProducte1 = EstatProducte.CONFIRMAT;
        botoPrimerProducte.setVisible(false);

        if (estatProducte2 == EstatProducte.CONFIRMAT) {
            botoIntercanviar.setVisible(true);
        }
    }

    protected void actualitzaProducte1Seleccionat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatProducte1", estatProducte1, EstatProducte.SELECCIONAT);
        if (estatProducte1 != EstatProducte.SELECCIONAT) {
            System.err.println(err);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatProducte2", estatProducte2, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            System.err.println("Casella fantasma");
            return;
        }

        producte1Seleccionat.first = filaSeleccionada;
        producte1Seleccionat.second = columnaSeleccionada;
        String producte = (String) taulaCataleg.getValueAt(filaSeleccionada, columnaSeleccionada);
        etiquetaPrimerProducte.setText(producte);
    }

    protected void canviEstatProducte2ASeleccionar() {
        String err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionar", "estatProducte2", estatProducte2, EstatProducte.INICIAL);
        if (estatProducte2 != EstatProducte.INICIAL) {
            System.err.println(err);
            return;
        }

        String estatProducte1Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionar", "estatProducte1", estatProducte1, estatProducte1Desitjat);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        estatProducte2 = EstatProducte.SELECCIONAR;
        botoSegonProducte.setVisible(false);
        etiquetaSegonProducte.setText(textEtiquetaProducteEnSeleccionar);
    }

    protected void canviEstatProducte2ASeleccionat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionat", "estatProducte2", estatProducte2, EstatProducte.SELECCIONAR);
        if (estatProducte2 != EstatProducte.SELECCIONAR) {
            System.err.println(err);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            System.err.println("Casella fantasma");
            return;
        }

        String estatProducte1Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionat", "estatProducte1", estatProducte1, estatProducte1Desitjat);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        estatProducte2 = EstatProducte.SELECCIONAT;
        actualitzaProducte2Seleccionat();
        botoSegonProducte.setText(textBotoSegonProducteEnSeleccionat);
        botoSegonProducte.setVisible(true);
    }

    protected void canviEstatProducte2AConfirmat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte2AConfirmat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte2AConfirmat", "estatProducte2", estatProducte2, EstatProducte.SELECCIONAT);
        if (estatProducte2 != EstatProducte.SELECCIONAT) {
            System.err.println(err);
            return;
        }

        String estatProducte1Desitjats = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte2AConfirmat", "estatProducte1", estatProducte1, estatProducte1Desitjats);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        estatProducte2 = EstatProducte.CONFIRMAT;
        botoSegonProducte.setVisible(false);

        if (estatProducte1 == EstatProducte.CONFIRMAT) {
            botoIntercanviar.setVisible(true);
        }
    }

    protected void actualitzaProducte2Seleccionat() {
        String err = String.format(errEstatTemplate, "actualitzaProducte2Seleccionat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            System.err.println(err);
            return;
        }

        err = String.format(errEstatTemplate, "actualitzaProducte2Seleccionat", "estatProducte2", estatProducte2, EstatProducte.SELECCIONAT);
        if (estatProducte2 != EstatProducte.SELECCIONAT) {
            System.err.println(err);
            return;
        }

        String estatProducte1Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "actualitzaProducte2Seleccionat", "estatProducte1", estatProducte1, estatProducte1Desitjat);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            System.err.println(err);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            System.err.println("Casella fantasma");
            return;
        }

        producte2Seleccionat.first = filaSeleccionada;
        producte2Seleccionat.second = columnaSeleccionada;
        String producte = (String) taulaCataleg.getValueAt(filaSeleccionada, columnaSeleccionada);
        etiquetaSegonProducte.setText(producte);
    }

    private boolean algunProducteEnSeleccio() {
        if (estatProducte1 == EstatProducte.SELECCIONAR) return true;
        if (estatProducte1 == EstatProducte.SELECCIONAT) return true;
        if (estatProducte2 == EstatProducte.SELECCIONAR) return true;
        return estatProducte2 == EstatProducte.SELECCIONAT;
    }

    private boolean celaFantasma(int fila, int columna) {
        if (fila == -1 || columna == -1) {
            String errTemplate = "celaFantasma en la casella (%d, %d) no funciona%n";
            System.err.printf(errTemplate, fila, columna);
            return false;
        }

        if (fila == files - 1) return columna >= columnesUltimaFila;
        else return false;
    }

    public void tornar() {
        super.tornar();
        ctrlVistaSolucions.controlVistes(0);
    }

    protected void sortirSistema() {
        ctrlVistaSolucions.sortirSistema();
    }

    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoCanviEstatGeneralEnVisualitzar)) {
            canviEstatGeneralAEditar();
        }
        else if (textBoto.equals(textBotoCanviEstatGeneralEnEditar)) {
            canviEstatGeneralAVisualitzar();
        }
        else if (textBoto.equals(textBotoPrimerProducteEnInicial)) {
            canviEstatProducte1ASeleccionar();
        }
        else if (textBoto.equals(textBotoPrimerProducteEnSeleccionat)) {
            canviEstatProducte1AConfirmat();
        }
        else if (textBoto.equals(textBotoSegonProducteEnInicial)) {
            canviEstatProducte2ASeleccionar();
        }
        else if (textBoto.equals(textBotoSegonProducteEnSeleccionat)) {
            canviEstatProducte2AConfirmat();
        }
        else if (textBoto.equals(textBotoIntercanviar)) {
            ctrlVistaSolucions.intercanviarProductes(producte1Seleccionat.first, producte1Seleccionat.second,
                    producte2Seleccionat.first, producte2Seleccionat.second);
            taulaCataleg.repaint();
            canviEstatGeneralAVisualitzar();
        }
        else if (textBoto.equals(textBotoEliminarSolucio)) {
            ctrlVistaSolucions.eliminarSolucio();
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    protected void actualitzatCelaSeleccionada() {
        if (filaSeleccionada != -1 && columnaSeleccionada != -1) {
            System.out.println("Casella seleccionada: (" + filaSeleccionada + ", " + columnaSeleccionada + ")");

            if (algunProducteEnSeleccio() && celaFantasma(filaSeleccionada, columnaSeleccionada)) {
                JOptionPane.showMessageDialog(frameVista,
                        "Has clicat una casella buida",
                        "Casella invalida",
                        JOptionPane.WARNING_MESSAGE);
                if (estatProducte1 == EstatProducte.SELECCIONAR || estatProducte1 == EstatProducte.SELECCIONAT) {
                    canviEstatProducte1AInicial();
                }
                if (estatProducte2 == EstatProducte.SELECCIONAR || estatProducte2 == EstatProducte.SELECCIONAT) {
                    canviEstatProducte2AInicial();
                }

            }

            if (estatProducte1 == EstatProducte.SELECCIONAR) {
                canviEstatProducte1ASeleccionat();
            }
            else if (estatProducte1 == EstatProducte.SELECCIONAT) {
                actualitzaProducte1Seleccionat();
            }

            if (estatProducte2 == EstatProducte.SELECCIONAR) {
                canviEstatProducte2ASeleccionat();
            }
            else if (estatProducte2 == EstatProducte.SELECCIONAT) {
                actualitzaProducte2Seleccionat();
            }
        }
    }

    protected void setFilaSeleccionada(int fila) {
        if (fila != filaSeleccionada) {
            filaSeleccionada = fila;
            actualitzatCelaSeleccionada();
        }
    }

    protected void setColumnaSeleccionada(int columna) {
        if (columna != columnaSeleccionada) {
            columnaSeleccionada = columna;
            actualitzatCelaSeleccionada();
        }
    }

    protected class ModelTaula extends AbstractTableModel {

        public ModelTaula() {
        }

        public int getColumnCount() {
            return columnes;
        }

        public int getRowCount() {
            return files;
        }

        public String getColumnName(int col) {
            return "Col" + col;
        }

        public Object getValueAt(int row, int col) {
            if (celaFantasma(row, col)) return "";
            return productes.get(row).get(col);
        }

        public void setValueAt(Object value, int row, int col) {
            productes.get(row).set(col, (String) value);
            fireTableCellUpdated(row, col);
        }
    }

    protected class SharedRowSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                int leadSelection = lsm.getLeadSelectionIndex();
                System.out.println("Fila clicada: " + leadSelection);
                setFilaSeleccionada(leadSelection);
            }
        }
    }

    protected class SharedColumnSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                int leadSelection = lsm.getLeadSelectionIndex();
                System.out.println("Columna clicada: " + leadSelection);
                setColumnaSeleccionada(leadSelection);
            }
        }
    }
}
