package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VistaInfoSolucio extends VistaGenerica {

    protected CtrlVistaSolucions ctrlVistaSolucions;

    protected List<List<String>> productes;

    protected String textEtiquetaProductes = "Productes de la solucio";
    protected JLabel etiquetaProductes;

    protected JScrollPane scrollPane;
    protected ModelTaula modelTaula;
    protected JTable taulaCataleg;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;

    private boolean estatEditar = false;

    protected String textEtiquetaEstatVisualitzar = "Actualment estas en l'estat Visualitzar";
    protected String textEtiquetaEstatEditar = "Actualment estas en l'estat Editar";
    protected JLabel etiquetaEstat;
    protected String textBotoCanviEstatAVisualitzar = "Mode Visualitzar";
    protected String textBotoCanviEstatAEditar = "Mode Editar";
    protected Boto botoCanviEstat;

    protected JPanel panelEdicio;

    protected String textEtiquetaEstatInicial = "Prem el boto per poder seleccionar el producte";
    protected String textEtiquetaEstatSeleccionar = "Clica un producte de la taula";

    protected JPanel panelSeleccioPrimerProducte;
    protected JLabel etiquetaPrimerProducte;
    protected String textBotoPrimerProducteEstatTriar = "Triar producte1";
    protected String textBotoPrimerProducteEstatConfirmar = "Confirmar producte1";
    protected Boto botoPrimerProducte;

    protected String textBotoIntercanviar = "Intercanviar";
    protected Boto botoIntercanviar;

    protected JPanel panelSeleccioSegonProducte;
    protected JLabel etiquetaSegonProducte;
    protected String textBotoSegonProducteEstatTriar = "Triar producte2";
    protected String textBotoSegonProducteEstatConfirmar = "Confirmar producte2";
    protected Boto botoSegonProducte;


    public void executar(CtrlVistaGeneric ctrl, List<List<String>> productes) {
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl;
        //this.productes = productes;
        this.productes = new ArrayList<List<String>>();
        List<String> l1 = new ArrayList<String>();
        l1.add("ous"); l1.add("llet"); l1.add("peix");
        List<String> l2 = new ArrayList<String>();
        l2.add("pa"); l2.add("farina"); l2.add("oli");
        this.productes.add(l1); this.productes.add(l2);
        titolFrame = "Informacio de la solucio";
        ajuda = "No hi ha";

        super.executar();
    }

    protected void inicialitzarComponents() {
        width = 1600;
        height = 1300;
        teBotoTornar = true;

        // Inicialitzem la superclase
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem l'etiqueta descriptiva
        etiquetaProductes = new JLabel(textEtiquetaProductes);
        etiquetaProductes.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaProductes);

        // Inicialitzem la taula amb els productes
        modelTaula = new ModelTaula(productes); // Model que gestiona l'ED de la taula
        taulaCataleg = new JTable(modelTaula);

        taulaCataleg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Per que el width no s'ajusti automaticament
        taulaCataleg.getTableHeader().setReorderingAllowed(false); // Per prohibir moure les columnes
        taulaCataleg.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Per fer seleccio unica
        taulaCataleg.setCellSelectionEnabled(true); // Per fer seleccio en cel.les individuals

        // Afegim els Listeners de la taula
        taulaCataleg.getSelectionModel().addListSelectionListener(new SharedRowSelectionHandler());
        taulaCataleg.getColumnModel().getSelectionModel().addListSelectionListener(new SharedColumnSelectionHandler());

        // Fiquem la taula dins un JScrollPane
        scrollPane = new JScrollPane(taulaCataleg);
        add(scrollPane);

        // Inicialitzem etiqueta i boto de canvi d'estat
        estatEditar = false;
        etiquetaEstat = new JLabel(textEtiquetaEstatVisualitzar);
        etiquetaEstat.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaEstat);
        botoCanviEstat = new Boto(textBotoCanviEstatAEditar);
        botoCanviEstat.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoCanviEstat);

        // Inicialitzem el panel que permet intercanviar productes
        inicialitzarPanelEdicio();

        add(Box.createVerticalGlue());
        add(panelEdicio);
    }

    protected void inicialitzarPanelEdicio() {
        panelEdicio = new JPanel();
        panelEdicio.setLayout(new BoxLayout(panelEdicio, BoxLayout.LINE_AXIS));

        panelSeleccioPrimerProducte = new JPanel();
        panelSeleccioPrimerProducte.setLayout(new BoxLayout(panelSeleccioPrimerProducte, BoxLayout.PAGE_AXIS));
        etiquetaPrimerProducte = new JLabel(textEtiquetaEstatInicial);
        etiquetaPrimerProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioPrimerProducte.add(etiquetaPrimerProducte);
        botoPrimerProducte = new Boto(textBotoPrimerProducteEstatTriar);
        botoPrimerProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioPrimerProducte.add(botoPrimerProducte);

        panelEdicio.add(panelSeleccioPrimerProducte);

        panelEdicio.add(Box.createHorizontalGlue());

        botoIntercanviar = new Boto(textBotoIntercanviar);
        botoIntercanviar.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        panelEdicio.add(botoIntercanviar);

        panelEdicio.add(Box.createHorizontalGlue());

        panelSeleccioSegonProducte = new JPanel();
        panelSeleccioSegonProducte.setLayout(new BoxLayout(panelSeleccioSegonProducte, BoxLayout.PAGE_AXIS));
        etiquetaSegonProducte = new JLabel(textEtiquetaEstatInicial);
        etiquetaSegonProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioSegonProducte.add(etiquetaSegonProducte);
        botoSegonProducte = new Boto(textBotoSegonProducteEstatTriar);
        botoSegonProducte.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        panelSeleccioSegonProducte.add(botoSegonProducte);

        panelEdicio.add(panelSeleccioSegonProducte);
        panelEdicio.setVisible(estatEditar);
    }

    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoCanviEstatAEditar)) {
            estatEditar = true;
            inicialitzarPanelEdicio();
        }
        else if (textBoto.equals(textBotoCanviEstatAVisualitzar)) {
            estatEditar = false;
            panelEdicio.setVisible(false);
        }
    }

    protected void actualitzatCelaSeleccionada() {
        if (filaSeleccionada != -1 && columnaSeleccionada != -1) {
            System.out.println("Casella seleccionada: (" + filaSeleccionada + ", " + columnaSeleccionada + ")");
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
        private final List<List<String>> matriu;
        private final int files;
        private final int columnes;

        ModelTaula(List<List<String>> mat) {
            matriu = mat;
            files = mat.size();
            columnes = files > 0 ? mat.getFirst().size() : 0;
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
            return matriu.get(row).get(col);
        }

        public void setValueAt(Object value, int row, int col) {
            matriu.get(row).set(col, (String) value);
            fireTableCellUpdated(row, col);
        }
    }

    protected class SharedRowSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                int leadSelection = lsm.getLeadSelectionIndex();
                setFilaSeleccionada(leadSelection);
            }
        }
    }

    protected class SharedColumnSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                int leadSelection = lsm.getLeadSelectionIndex();
                setColumnaSeleccionada(leadSelection);
            }
        }
    }
}
