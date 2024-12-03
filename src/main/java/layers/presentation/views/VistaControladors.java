package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaSolucions;

import javax.swing.*;
import java.awt.*;

public class VistaControladors extends VistaGenerica {
    protected String textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
    protected JLabel etiquetaTriar;

    protected String textBotoAfegir = "Afegir";
    protected Boto botoAfegir;

    protected String textBotoMostrar = "Mostrar";
    protected Boto botoMostrar;

    protected JComboBox<String> opcions;

    Boolean primeraVegada = true;

    @Override
    public void executar() {
        if (!primeraVegada) frameVista.setVisible(true);
        else {
            primeraVegada = false;
            super.executar();
        }
    }

    public VistaControladors() {
        super();
        this.titolFrame = "Vista Controladors";
    }

    @Override
    protected void inicialitzarComponents() {
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem la etiqueta descriptiva
        etiquetaTriar = new JLabel(textEtiquetaTriar);
        etiquetaTriar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaTriar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el ComboBox
        opcions = new JComboBox<>(new String[]{});
        opcions.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(opcions);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Mostrar
        botoMostrar = new Boto(textBotoMostrar);
        botoMostrar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoMostrar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Afegir
        botoAfegir = new Boto(textBotoAfegir);
        botoAfegir.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoAfegir);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) System.out.println("S'ha clickat 'Afegir' a la VistaControladors");
        else super.botoAccionat(textBoto);
    }
    @Override
    protected void itemAccionat(String textItem) {
        if (textItem.equals(textItemSortir)) {
            super.tornar();
        }
        else super.itemAccionat(textItem);
    }
}
