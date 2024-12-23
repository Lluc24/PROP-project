package layers.presentation.views;

import javax.swing.*;
import java.awt.*;

/**
 * Classe 'VistaControladors'
 *
 * Vista abstracta per gestionar operacions genèriques de les vistes dels controladors. Aquesta classe proporciona
 * una estructura bàsica amb botons, etiquetes i un ComboBox per interactuar amb diferents funcionalitats.
 * És la base per altres vistes que necessiten gestionar controladors específics.
 *
 * @see VistaGenerica
 *
 * @author Eulalia Peiret Santacana
 *
 * <p><b>Components principals:</b></p>
 * <ul>
 *     <li><b>Etiqueta:</b> `etiquetaTriar` descriu les funcionalitats disponibles a l'usuari.</li>
 *     <li><b>ComboBox:</b> `opcions` permet seleccionar una opció d'entre les disponibles.</li>
 *     <li><b>Botons:</b>
 *         <ul>
 *             <li><b>Afegir:</b> Botó per executar accions d'afegir.</li>
 *             <li><b>Mostrar:</b> Botó per executar accions de mostrar informació.</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 */
public abstract class VistaControladors extends VistaGenerica {
    protected String textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
    protected JLabel etiquetaTriar;

    protected String textBotoAfegir = "Afegir";
    protected Boto botoAfegir;

    protected String textBotoMostrar = "Mostrar";
    protected Boto botoMostrar;

    protected JComboBox<String> opcions;

    protected Boolean primeraVegada = true;

    /**
     * Funcio constructora de la classe
     */
    public VistaControladors() {
        super();
        this.titolFrame = "Vista Controladors";
    }

    /**
     * Si es la primera vegada que s'executa la classe, inicialitza tots els components.
     * Si ja s'han inicialitzat anteriorment, només cal fer la classe visible.
     */
    @Override
    public void executar() {
        if (!primeraVegada) frameVista.setVisible(true);
        else {
            primeraVegada = false;
            super.executar();
        }
    }

    /**
     * Inicialitza els components necessaris per visualitzar la vista correctament
     */
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
        opcions.setMinimumSize(new java.awt.Dimension(250, 20));
        opcions.setMaximumSize(new java.awt.Dimension(250, 20));
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

}
