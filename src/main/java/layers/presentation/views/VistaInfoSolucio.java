package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;

import javax.swing.*;
import java.util.List;

public class VistaInfoSolucio extends VistaGenerica {
    protected CtrlVistaSolucions ctrlVistaSolucions;

    protected String textEtiquetaProductes = "Productes de la solucio";
    protected JLabel etiquetaProductes;

    //Constructora
    public VistaInfoSolucio(CtrlVistaSolucions ctrl){
        ctrlVistaSolucions = ctrl;
    }

    protected void executar(List<List<String>> solList) {
        titolFrame = "Informacio de la solucio";
        ajuda = "No hi ha";

        super.executar();
    }

    protected void inicialitzarComponents() {
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
    }
}
