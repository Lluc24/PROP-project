package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;

public class VistaGestioAlgorisme extends VistaGenerica {
    private CtrlVistaSolucions ctrlVistaSolucions;

    protected String textEtiquetaTriar = "Quina tipus d'algorisme vols?";
    protected JLabel etiquetaTriar;

    protected String textBotoAproximacio = "Aproximacio";
    protected Boto botoAproximacio;

    protected String textBotoGreedy = "Greedy";
    protected Boto botoGreedy;

    protected String textBotoBacktracking = "Backtracking";
    protected Boto botoBacktracking;

    protected String textBotoSortir = "Sortir";
    protected Boto botoSortir;

    public void executar(CtrlVistaGeneric ctrl) {
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl;
        titolFrame = "Vista gestio algorisme";
        ajuda = "Estas a la vista de gestio d'algorismes. Des d'aquesta vista pots canviar l'algorisme actual " +
                "utilitzant els botons correstponents.\n" +
                "Aproximacio: l'algorisme actual passara a ser de tipus Aproximacio.\n" +
                "Greedy: l'algorisme actual passara a ser de tipus Greedy.\n" +
                "Backtracking: l'algorisme actual passara a ser de tipus Backtracking.\n" +
                "Sortir: Finalitza l'aplicacio.";
        super.executar();
    }

    @Override
    protected void inicialitzarComponents() {
        // Inicialitzem la superclase
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem la etiqueta descriptiva
        etiquetaTriar = new JLabel(textEtiquetaTriar);
        etiquetaTriar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaTriar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Aproximacio
        botoAproximacio = new Boto(textBotoAproximacio);
        botoAproximacio.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoAproximacio);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Gestionar solucions
        botoGreedy = new Boto(textBotoGreedy);
        botoGreedy.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoGreedy);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Gestionar restriccions
        botoBacktracking = new Boto(textBotoBacktracking);
        botoBacktracking.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoBacktracking);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Sortir
        botoSortir = new Boto(textBotoSortir);
        botoSortir.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoSortir);

        add(Box.createVerticalGlue());
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAproximacio)) {
            ctrlVistaSolucions.gestioAlgorisme("aproximacio");
        }
        else if (textBoto.equals(textBotoGreedy)) {
        }
        else if (textBoto.equals(textBotoBacktracking)) {
            ctrlVistaSolucions.gestioAlgorisme("algorismeBT");
        }
        else if (textBoto.equals(textBotoSortir)) {
            sortir();
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    @Override
    protected void itemAccionat(String textItem) {
        super.itemAccionat(textItem);
    }
}
