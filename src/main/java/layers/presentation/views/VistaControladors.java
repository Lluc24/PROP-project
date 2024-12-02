package layers.presentation.views;

import javax.swing.*;
import java.awt.*;

public class VistaControladors extends VistaGenerica {
    protected String textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
    protected JLabel etiquetaTriar;

    protected String textBotoAfegir = "Afegir";
    protected Boto botoAfegir;

    protected String textBotoMostrar = "Mostrar";
    protected Boto botoMostrar;

    protected String textBoto2 = "Utilitat";
    protected Boto boto2;

    protected JComboBox<String> opcions;

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
        opcions = new JComboBox<>(new String[]{"Opción 1", "Opción 2", "Opción 3"});
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

        // Inicialitzem el boto 2
        boto2 = new Boto(textBoto2);
        boto2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(boto2);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) System.out.println("S'ha clickat 'Afegir' a la VistaControladors");
        if (textBoto.equals(textBoto2)) System.out.println("S'ha clickat 'Boto2' a la VistaControladors");
        else super.botoAccionat(textBoto);
    }
}
