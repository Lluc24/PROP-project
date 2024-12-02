package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;

import javax.swing.*;
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
        teBotoTornar = true;

        // Inicialitzem la superclase
        super.inicialitzarComponents();

        modelTaula = new ModelTaula(productes);
        taulaCataleg = new JTable(modelTaula);
        scrollPane = new JScrollPane(taulaCataleg);
        add(scrollPane);

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem l'etiqueta descriptiva
        etiquetaProductes = new JLabel(textEtiquetaProductes);
        etiquetaProductes.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaProductes);
    }

    private class ModelTaula extends AbstractTableModel {
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

        public Object getValueAt(int row, int col) {
            return matriu.get(row).get(col);
        }
    }
}
