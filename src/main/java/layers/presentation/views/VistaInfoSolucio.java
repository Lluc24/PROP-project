package layers.presentation.views;

import layers.domain.utils.Pair;
import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;
import layers.presentation.utils.BotoGeneric;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  VistaInfoSolucio
 *  Es la vista que permet visualitzar i modificar solucions.
 *  VistaGenerica
 * @author Lluc Santamaria Riba
 * @version 3.7
 *
 * <p><b>Informació:</b></p>
 * Aquesta vista esta compost per una matriu on es visulitza la solucio i diversos botons que permeten:
 * <ul>
 *     <li><b>Canviar el mode:</b> Canviar entre mode nomes visualitzacio o mode edicio.</li>
 *     <li><b>Eliminar la solucio:</b> Elimina la solucio del sistema.</li>
 *     <li><b>Intercanviar productes:</b> Conjunt de botons situats a la part inferior que permeten intercanviar.</li>
 * </ul>
 */
public class VistaInfoSolucio extends VistaGenerica {

    /** Template del text que es mostra quan hi ha un error d'incoherencia en els estats. */
    private final String errEstatTemplate = "Error %s: L'%s es %s pero hauria de ser %s";
    /** Acis per intentar intercanviar un producte amb si mateix. */
    private final String avisIntercanviMateixProducte = "No es pot fer l'intercanvi.\n" +
            "Els productes a intercanviar coincideixen";

    /**
     * Enumeracio dels estats generals que segueix la vista.
     */
    private enum EstatGeneral {
        INICIAL,
        VISUALITZAR,
        EDITAR,
    }
    /** L'estat general en el qual es troba la vista. */
    private EstatGeneral estatGeneral;

    /**
     * Enumeracio de l'estat que segueix un producte en seleccio.
     */
    private enum EstatProducte {    // Nomes si estatGeneral = EDITAR
        INICIAL,
        SELECCIONAR,
        SELECCIONAT,
        CONFIRMAT,
    }
    /** L'estat de seleccio en el qual es troba el primer producte */
    private EstatProducte estatProducte1;
    /** L'estat de seleccio en el qual es troba el segon producte */
    private EstatProducte estatProducte2;

    /** Parell d'enters que indica la posicio del primer producte seleccionat. */
    private final Pair<Integer, Integer> producte1Seleccionat = new Pair<Integer, Integer>(-1, -1);
    /** Parell que indica la posicio del segon producte seleccionat. */
    private final Pair<Integer, Integer> producte2Seleccionat = new Pair<Integer, Integer>(-1, -1);

    /** Numero de files que te la matriu. */
    private int files;
    /** Numero de columnes que te la matriu. */
    private int columnes;
    /** Numero de columnes no buides que te la matriu en l'ultima fila. */
    private int columnesUltimaFila;

    /** Controlador de solucions de la capa de presentacio. */
    private final CtrlVistaSolucions ctrlVistaSolucions;

    /** Estructura de dades que gestiona la matriu. */
    protected List<List<String>> productes;

    /** Template del text que es mostra al damunt de la matriu. */
    protected String templateTextEtiquetaTitol = "Productes de la solucio %s";
    /** Nom de la solucio que la vista esta mostrant. */
    protected String nomSolucio;
    /** Etiqueta que es mostra al dammunt de la matriu */
    protected JLabel etiquetaTitol;

    /** Panel amb scrollBar horitzontal i vertical que conte la matriu. */
    protected JScrollPane scrollPane;
    /** Logica de model de la matriu. */
    protected ModelTaula modelTaula;
    /** JTable encarregat de la presentacio de la matriu. */
    protected JTable taulaCataleg;
    /** Fila de la matriu seleccionada. */
    private int filaSeleccionada = -1;
    /** Columna de la matriu seleccionada. */
    private int columnaSeleccionada = -1;

    // Els components del panel de classe (this). Segueixen els estats de EstatGeneral
    /** Text de l'etiqueta que es mostra quan l'estat es visualitzar. */
    protected String textEtiquetaEstatGeneralEnVisualitzar = "Actualment estas en l'estat Visualitzar";
    /** Text de l'etiqueta que es mostra quan l'estat es editar. */
    protected String textEtiquetaEstatGeneralEnEditar = "Actualment estas en l'estat Editar";
    /** Etiqueta que mostra l'estat general de la vista. */
    protected JLabel etiquetaEstatGeneral;
    /** Text del boto de canvi d'estat quan l'estat general es visualitzar. */
    protected String textBotoCanviEstatGeneralEnVisualitzar = "Mode Editar";
    /** Text del boto de canvi d'estat quan l'estat general es editar. */
    protected String textBotoCanviEstatGeneralEnEditar = "Mode Visualitzar";
    /** Boto de canvi d'estat general. */
    protected Boto botoCanviEstatGeneral;
    /** Text del boto d'eliminar la solucio. */
    protected String textBotoEliminarSolucio = "Eliminar i Sortir";
    /** Boto d'eliminar la solucio. */
    protected Boto botoEliminarSolucio;

    /** Panel que conte els elements necessaris per poder fer els intercanvis. */
    protected JPanel panelEdicio;

    // Els components del panelEdicio. Segueixen els estats de EstatProducte
    /** Text de l'etiqueta del producte en seleccio quan l'estat es inicial. */
    protected String textEtiquetaProducteEnInicial = "Prem el boto per poder seleccionar el producte";
    /** Text de l'etiqueta del producte en seleccio quan l'estat es seleccionar. */
    protected String textEtiquetaProducteEnSeleccionar = "Clica un producte de la taula";

    /** Panel que conte els elements que permeten seleccionar en la matriu el primer producte. */
    protected JPanel panelSeleccioPrimerProducte;
    /** Etiqueta del producte1 que s'esta seleccionant. */
    protected JLabel etiquetaPrimerProducte;
    /** Text del boto del producte1 quan l'estat es inicial. */
    protected String textBotoPrimerProducteEnInicial = "Triar producte1";
    /** Text del boto del producte1 quan l'estat es seleccionat. */
    protected String textBotoPrimerProducteEnSeleccionat = "Confirmar producte1";
    /** Boto del producte1. */
    protected Boto botoPrimerProducte;

    /**  Text del boto per realizar l'intercanvi dels productes ja seleccionats. */
    protected String textBotoIntercanviar = "Intercanviar";
    /**  Boto per realizar l'intercanvi dels productes ja seleccionats. */
    protected Boto botoIntercanviar;

    /** Panel que conte els elements que permeten seleccionar en la matriu el segon producte. */
    protected JPanel panelSeleccioSegonProducte;
    /** Etiqueta del producte2 que s'esta seleccionant. */
    protected JLabel etiquetaSegonProducte;
    /** Text del boto del producte2 quan l'estat es inicial. */
    protected String textBotoSegonProducteEnInicial = "Triar producte2";
    /** Text del boto del producte2 quan l'estat es seleccionat. */
    protected String textBotoSegonProducteEnSeleccionat = "Confirmar producte2";
    /** Boto del producte2. */
    protected Boto botoSegonProducte;

    /**
     * Constructora de la classe.
     * @param ctrl: Controladora de solucions de la capa de presentacio.
     */
    public VistaInfoSolucio(CtrlVistaSolucions ctrl) {
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl;
    }

    /**
     * Metode que dona inici a l'execucio de la vista.
     * @param productes: Estructura de dades que gestiona la matriu de productes.
     * @param nomSolcuio: Nom de la solucio que es mostrara.
     */
    public void executar(List<List<String>> productes, String nomSolcuio) {
        System.out.println("La matriu rebuda es:");
        imprimir(productes);
        this.nomSolucio = nomSolcuio;
        this.productes = productes;
        titolFrame = "Vista Informacio de la Solucio";
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

    /**
     * Metode que inicialitza els components i els insereix a la vista.
     */
    @Override
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

    /**
     * Metode per canviar l'estat general a visualitzar
     */
    protected void canviEstatGeneralAVisualitzar() {
        String estatGeneralDesitjat = EstatGeneral.INICIAL + " o " + EstatGeneral.EDITAR;
        String err = String.format(errEstatTemplate, "canviEstatGeneralAVisualitzar", "estatGeneral", estatGeneral, estatGeneralDesitjat);
        if (estatGeneral != EstatGeneral.INICIAL && estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        estatGeneral = EstatGeneral.VISUALITZAR;

        etiquetaEstatGeneral.setText(textEtiquetaEstatGeneralEnVisualitzar);
        botoCanviEstatGeneral.setText(textBotoCanviEstatGeneralEnVisualitzar);

        panelEdicio.setVisible(false);
    }

    /**
     * Metode per canviar l'estat general a editar
     */
    protected void canviEstatGeneralAEditar() {
        String err = String.format(errEstatTemplate, "canviEstatGeneralAEditar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.VISUALITZAR) {
            mostrarOptionPane(err, true);
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

    /**
     * Metode per canviar l'estat del primer producte a inicial
     */
    protected void canviEstatProducte1AInicial() {
        estatProducte1 = EstatProducte.INICIAL;
        etiquetaPrimerProducte.setText(textEtiquetaProducteEnInicial);
        botoPrimerProducte.setText(textBotoPrimerProducteEnInicial);
        botoPrimerProducte.setVisible(true);
    }

    /**
     * Metode per canviar l'estat del primer producte a seleccionar
     */
    protected void canviEstatProducte1ASeleccionar() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatProducte1", estatProducte1, EstatProducte.INICIAL);
        if (estatProducte1 != EstatProducte.INICIAL) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionar", "estatProducte2", estatProducte2, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err + "\nCal acabar la seleccio del segon producte.", false);
            return;
        }

        estatProducte1 = EstatProducte.SELECCIONAR;
        botoPrimerProducte.setVisible(false);
        etiquetaPrimerProducte.setText(textEtiquetaProducteEnSeleccionar);
    }

    /**
     * Metode per canviar l'estat del primer producte a seleccionat
     */
    protected void canviEstatProducte1ASeleccionat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionat", "estatProducte1", estatProducte1, EstatProducte.SELECCIONAR);
        if (estatProducte1 != EstatProducte.SELECCIONAR) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1ASeleccionat", "estatProducte2", estatProducte1, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err, true);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            mostrarOptionPane("Casella fantasma en canviEstatProducte1ASeleccionat", true);
            return;
        }

        estatProducte1 = EstatProducte.SELECCIONAT;
        actualitzaProducte1Seleccionat();
        botoPrimerProducte.setText(textBotoPrimerProducteEnSeleccionat);
        botoPrimerProducte.setVisible(true);
    }

    /**
     * Metode per canviar l'estat del primer producte a confirmat.
     */
    protected void canviEstatProducte1AConfirmat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatProducte1", estatProducte1, EstatProducte.SELECCIONAT);
        if (estatProducte1 != EstatProducte.SELECCIONAT) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte1AConfirmat", "estatProducte2", estatProducte1, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err, true);
            return;
        }

        if (estatProducte2 == EstatProducte.CONFIRMAT &&
                producte2Seleccionat.first == filaSeleccionada && producte2Seleccionat.second == columnaSeleccionada) {
            mostrarOptionPane(avisIntercanviMateixProducte, false);
            return;
        }

        estatProducte1 = EstatProducte.CONFIRMAT;
        botoPrimerProducte.setVisible(false);

        if (estatProducte2 == EstatProducte.CONFIRMAT) {
            botoIntercanviar.setVisible(true);
        }
    }

    /**
     * Metode per actualitzar el producte que ha sigut seleccionat com a primer producte.
     */
    protected void actualitzaProducte1Seleccionat() {
        String err = String.format(errEstatTemplate, "actualitzaProducte1Seleccionat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "actualitzaProducte1Seleccionat", "estatProducte1", estatProducte1, EstatProducte.SELECCIONAT);
        if (estatProducte1 != EstatProducte.SELECCIONAT) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte2Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "actualitzaProducte1Seleccionat", "estatProducte2", estatProducte2, estatProducte2Desitjat);
        if (estatProducte2 != EstatProducte.INICIAL && estatProducte2 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err, true);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            mostrarOptionPane("Casella fantasma en actualitzaProducte1Seleccionat", true);
            return;
        }

        producte1Seleccionat.first = filaSeleccionada;
        producte1Seleccionat.second = columnaSeleccionada;
        String producte = (String) taulaCataleg.getValueAt(filaSeleccionada, columnaSeleccionada);
        etiquetaPrimerProducte.setText(producte);
    }

    /**
     * Metode per canviar l'estat del segon producte a inicial
     */
    protected void canviEstatProducte2AInicial() {
        estatProducte2 = EstatProducte.INICIAL;
        etiquetaSegonProducte.setText(textEtiquetaProducteEnInicial);
        botoSegonProducte.setText(textBotoSegonProducteEnInicial);
        botoSegonProducte.setVisible(true);
    }

    /**
     * Metode per canviar l'estat del segon producte a seleccionar.
     */
    protected void canviEstatProducte2ASeleccionar() {
        String err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionar", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionar", "estatProducte2", estatProducte2, EstatProducte.INICIAL);
        if (estatProducte2 != EstatProducte.INICIAL) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte1Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionar", "estatProducte1", estatProducte1, estatProducte1Desitjat);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err + "\nCal acabar la seleccio del primer producte.", false);
            return;
        }

        estatProducte2 = EstatProducte.SELECCIONAR;
        botoSegonProducte.setVisible(false);
        etiquetaSegonProducte.setText(textEtiquetaProducteEnSeleccionar);
    }

    /**
     * Metode per canviar l'estat del segon producte a seleccionat.
     */
    protected void canviEstatProducte2ASeleccionat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionat", "estatProducte2", estatProducte2, EstatProducte.SELECCIONAR);
        if (estatProducte2 != EstatProducte.SELECCIONAR) {
            mostrarOptionPane(err, true);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            mostrarOptionPane("Casella fantasma en canviEstatProducte2ASeleccionat", true);
            return;
        }

        String estatProducte1Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte2ASeleccionat", "estatProducte1", estatProducte1, estatProducte1Desitjat);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err, true);
            return;
        }

        estatProducte2 = EstatProducte.SELECCIONAT;
        actualitzaProducte2Seleccionat();
        botoSegonProducte.setText(textBotoSegonProducteEnSeleccionat);
        botoSegonProducte.setVisible(true);
    }

    /**
     * Metode per canviar l'estat del segon producte a confirmat.
     */
    protected void canviEstatProducte2AConfirmat() {
        String err = String.format(errEstatTemplate, "canviEstatProducte2AConfirmat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "canviEstatProducte2AConfirmat", "estatProducte2", estatProducte2, EstatProducte.SELECCIONAT);
        if (estatProducte2 != EstatProducte.SELECCIONAT) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte1Desitjats = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "canviEstatProducte2AConfirmat", "estatProducte1", estatProducte1, estatProducte1Desitjats);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err, true);
            return;
        }

        if (estatProducte1 == EstatProducte.CONFIRMAT &&
                producte1Seleccionat.first == filaSeleccionada && producte1Seleccionat.second == columnaSeleccionada) {
            mostrarOptionPane(avisIntercanviMateixProducte, false);
            return;
        }

        estatProducte2 = EstatProducte.CONFIRMAT;
        botoSegonProducte.setVisible(false);

        if (estatProducte1 == EstatProducte.CONFIRMAT) {
            botoIntercanviar.setVisible(true);
        }
    }

     /**
     * Metode per actualitzar el producte que ha sigut seleccionat com a segon producte.
     */
    protected void actualitzaProducte2Seleccionat() {
        String err = String.format(errEstatTemplate, "actualitzaProducte2Seleccionat", "estatGeneral", estatGeneral, EstatGeneral.EDITAR);
        if (estatGeneral != EstatGeneral.EDITAR) {
            mostrarOptionPane(err, true);
            return;
        }

        err = String.format(errEstatTemplate, "actualitzaProducte2Seleccionat", "estatProducte2", estatProducte2, EstatProducte.SELECCIONAT);
        if (estatProducte2 != EstatProducte.SELECCIONAT) {
            mostrarOptionPane(err, true);
            return;
        }

        String estatProducte1Desitjat = EstatProducte.INICIAL + " o " + EstatProducte.CONFIRMAT;
        err = String.format(errEstatTemplate, "actualitzaProducte2Seleccionat", "estatProducte1", estatProducte1, estatProducte1Desitjat);
        if (estatProducte1 != EstatProducte.INICIAL && estatProducte1 != EstatProducte.CONFIRMAT) {
            mostrarOptionPane(err, true);
            return;
        }

        if (celaFantasma(filaSeleccionada, columnaSeleccionada)) {
            mostrarOptionPane("Casella fantasma en actualitzaProducte2Seleccionat", true);
            return;
        }

        producte2Seleccionat.first = filaSeleccionada;
        producte2Seleccionat.second = columnaSeleccionada;
        String producte = (String) taulaCataleg.getValueAt(filaSeleccionada, columnaSeleccionada);
        etiquetaSegonProducte.setText(producte);
    }

    /**
     * Metode que indica si algun producte esta en seleccio.
     * @return Retorna true si i nomes si algun producte esta en l'estat seleccionar o seleccionat
     */
    private boolean algunProducteEnSeleccio() {
        if (estatProducte1 == EstatProducte.SELECCIONAR) return true;
        if (estatProducte1 == EstatProducte.SELECCIONAT) return true;
        if (estatProducte2 == EstatProducte.SELECCIONAR) return true;
        return estatProducte2 == EstatProducte.SELECCIONAT;
    }

    /**
     * Metode que indica si la posicio pasada com a parametres es buida (fantasma, no conte cap producte).
     * @param fila: La fila del producte en la matriu.
     * @param columna: La columna del producte en la matriu.
     * @return Retorna true si i nomes si la casella no conte cap producte (es fantasma).
     */
    private boolean celaFantasma(int fila, int columna) {
        if (fila == -1 || columna == -1) {
            String err = "celaFantasma en la casella (" + fila + ", " + columna + ") no funciona";
            mostrarOptionPane(err, true);
            return false;
        }

        if (fila == files - 1) return columna >= columnesUltimaFila;
        else return false;
    }

    /**
     * Imprimeix per consola l'estructura de dades passada com a parametre.
     * @param matriu: Llista de llistes de strings que s'imprimira.
     */
    private void imprimir(List<List<String>> matriu) {
        Iterator<List<String>> it = matriu.iterator();
        while (it.hasNext()) {
            Iterator<String> it2 = it.next().iterator();
            while (it2.hasNext()) {
                System.out.print(it2.next() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Metode per mostrar una finestra d'avis o error.
     * @param missatge: El missatge que es mostrara en la finestra.
     * @param esError: Indica si ha de ser missatge d'error o no.
     */
    protected void mostrarOptionPane(String missatge, boolean esError) {
        JOptionPane.showMessageDialog(frameVista,
                missatge,
                esError ? "Error" : "Accio incorrecta",
                esError ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Indica al controlador que es vol tornar a la vista previa.
     */
    @Override
    public void tornar() {
        ctrlVistaSolucions.controlVistes(0);
    }

    /**
     * Indica al controlador que es vol sortir del sistema.
     */
    @Override
    protected void sortirSistema() {
        ctrlVistaSolucions.sortirSistema();
    }

    /**
     * Metode botoAccionat executat cada vegada que s'acciona un boto de la vista.
     * El boto de la vista que s'ha accionat es diferencia pel text que hi ha en aquest.
     * @param textBoto: El text que hi ha en el boto que s'ha accionat.
     */
    @Override
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
            tornar();
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Metode executat cada vegada que s'ha accionat una casella de la matriu.
     * Gestiona la coherencia en la logica d'estats de seleccio dels productes.
     */
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

    /**
     * Metode executat quan s'ha accionat una fila (a partir de la cassella seleccionada) de la matriu.
     * @param fila: Fila de la matriu que s'ha accionat.
     */
    protected void setFilaSeleccionada(int fila) {
        if (fila != filaSeleccionada) {
            filaSeleccionada = fila;
            actualitzatCelaSeleccionada();
        }
    }

    /**
     * Metode executat quan s'ha accionat una columna (a partir de la cassella seleccionada) de la matriu.
     * @param columna: Columna de la matriu que s'ha accionat.
     */
    protected void setColumnaSeleccionada(int columna) {
        if (columna != columnaSeleccionada) {
            columnaSeleccionada = columna;
            actualitzatCelaSeleccionada();
        }
    }

    /**
     *  ModelTaula
     *  Defineix un la logica de model darrere la matriu.
     * @see AbstractTableModel
     * @author Lluc Santamaria Riba
     * @version 3.1
     *
     * <p><b>Informació:</b></p>
     * Es la classe que s'utilitzara per definir la logica de model que
     * utilitza la JTable per mostrar la matriu. Permet fer us de l'estructura
     * de dades que gestiona VistaInfoSolucio.
     */
    protected class ModelTaula extends AbstractTableModel {

        /**
         * Constructora per defecte
         */
        public ModelTaula() {
        }

        /**
         * Indica el numero de columnes.
         * @return Retorna el numero de columnes que te la matriu.
         */
        public int getColumnCount() {
            return columnes;
        }

        /**
         * Indica el numero de files.
         * @return Retorna el numero de files que te la matriu.
         */
        public int getRowCount() {
            return files;
        }

        /**
         * Indica els noms de les columnes.
         * @param col: Columna la qual es vol obtenir el nom.
         * @return Retorna el String que s'utilitzara per la columna passada com a parametre.
         */
        public String getColumnName(int col) {
            return "Col" + col;
        }

        /**
         * Indica l'objecte d'una posicio concreta.
         * @param row: La fila de la posicio.
         * @param col: La columna de la posicio.
         * @return Retorna l'objecta que es troba en la posicio indicada pels parametres.
         */
        public Object getValueAt(int row, int col) {
            if (celaFantasma(row, col)) return "";
            return productes.get(row).get(col);
        }

        /**
         * Estableix l'objecte d'una posicio concreta.
         * @param value: El valor nou que s'establira en la posicio.
         * @param row: La fila de la posicio.
         * @param col: La columna de la posicio.
         */
        public void setValueAt(Object value, int row, int col) {
            productes.get(row).set(col, (String) value);
            fireTableCellUpdated(row, col);
        }
    }

    /**
     *  SharedRowSelectionHandler
     *  Implementa un listener de files.
     * @see ListSelectionListener
     * @author Lluc Santamaria Riba
     * @version 3.1
     *
     * <p><b>Informació:</b></p>
     * Implementa la interficie ListSelectionListener per definir un listener que compartiran totes
     * les files de la matriu.
     */
    protected class SharedRowSelectionHandler implements ListSelectionListener {

        /**
         * Metode executat quan es canvia la posicio que esta seleccionada de la matriu.
         * @param e: L'event que caracteritza el canvi.
         */
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                int leadSelection = lsm.getLeadSelectionIndex();
                System.out.println("Fila clicada: " + leadSelection);
                setFilaSeleccionada(leadSelection);
            }
        }
    }

    /**
     *  SharedColumnSelectionHandler
     *  Implementa un listener de columnes.
     * @see ListSelectionListener
     * @author Lluc Santamaria Riba
     * @version 3.1
     *
     * <p><b>Informació:</b></p>
     * Implementa la interficie ListSelectionListener per definir un listener que compartiran totes
     * les columnes de la matriu.
     */
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
