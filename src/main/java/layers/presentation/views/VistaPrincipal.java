package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends VistaGenerica {
    private CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions;
    private CtrlVistaSolucions ctrlVistaSolucions;

    protected String textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
    protected JLabel etiquetaTriar;

    protected String textBotoCataleg = "Gestionar cataleg";
    protected Boto botoCataleg;

    protected String textBotoSolucions = "Gestionar solucions";
    protected Boto botoSolucions;

    protected String textBotoRestriccions = "Gestionar restriccions";
    protected Boto botoRestriccions;

    protected String textBotoSortir = "Sortir";
    protected Boto botoSortir;

    public void executar(CtrlVistaGeneric ctrl1, CtrlVistaGeneric ctrl2) {
        ctrlVistaCatalegAmbRestriccions = (CtrlVistaCatalegAmbRestriccions)ctrl1;
        ctrlVistaSolucions = (CtrlVistaSolucions)ctrl2;
        titolFrame = "Vista Principal";
        ajuda = "Estas a la vista principal. Des d'aquesta vista pots provar qualsevol de les quatre funcionalitats " +
                "utilitzant els botons correstponents.\n" +
                "Gestionar cataleg: Permet gestionar els productes i similituds de la prestatgeria.\n" +
                "Gestionar solucions: Permet gestionar les solucions existents i calcular una nova.\n" +
                "Gestionar restriccions: Permet gestionar les restriccions entre productes.\n" +
                "Sortir: Finalitza l'aplicacio.";
        super.executar();
    }

    @Override
    protected void inicialitzarComponents() {
        teBotoTornar = false;

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

        // Inicialitzem el boto Gestionar cataleg
        botoCataleg = new Boto(textBotoCataleg);
        botoCataleg.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoCataleg);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Gestionar solucions
        botoSolucions = new Boto(textBotoSolucions);
        botoSolucions.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoSolucions);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Gestionar restriccions
        botoRestriccions = new Boto(textBotoRestriccions);
        botoRestriccions.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoRestriccions);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Sortir
        botoSortir = new Boto(textBotoSortir);
        botoSortir.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoSortir);

        add(Box.createVerticalGlue());
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoCataleg)) {
            ctrlVistaCatalegAmbRestriccions.executar();
        }
        else if (textBoto.equals(textBotoSolucions)) {
            ctrlVistaSolucions.executar();
        }
        else if (textBoto.equals(textBotoRestriccions)) {
            ctrlVistaCatalegAmbRestriccions.executar();
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
