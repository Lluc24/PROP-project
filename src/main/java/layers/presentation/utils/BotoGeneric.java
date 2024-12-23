package layers.presentation.utils;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Class BotoGeneric
 * @Description Classe abstracte per definir un comportament comu dels botons.
 * @see JButton
 * @author Lluc Santamaria Riba
 * @version 3.0
 *
 * @Information
 * Exten un JButton. Permet definir un comportament comu que seguiran tots els
 * botons de totes les vistes.
 */
public abstract class BotoGeneric extends JButton {
    /** Amplada en pixels del boto. */
    protected int width = 100;
    /** Alcada en pixels del boto. */
    protected int height = 20;

    /**
     * Constructora per defecte.
     */
    public BotoGeneric() {
        super();
        inicialitzarBoto();
    }

    /**
     * Constructora que especifica el text del boto.
     * @param text: Text que es mostrara en el boto.
     */
    public BotoGeneric(String text) {
        super(text);
        inicialitzarBoto();
    }

    /**
     * Constructora que especifica el text i el tamanny del boto.
     * @param width: Amplada en pixels del boto.
     * @param height: Alcada en pixels del boto.
     * @param text: Text que es mostrara en el boto.
     */
    public BotoGeneric(int width, int height, String text) {
        super(text);
        this.width = width;
        this.height = height;
        inicialitzarBoto();
    }

    /**
     * Metode per inicialitzar la interficie del boto i definir la logica
     * comuna de tots els botons.
     */
    protected void inicialitzarBoto() {
        setSize(width, height);
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("S'ha clicat el boto " + getText());
                actionPerformedBoto(event);
            }
        });
    }

    /**
     * Metode abstracte executat cada vegada que s'acciona un boto.
     * @param event: L'event capturat pel listener a l'accionar el boto.
     */
    protected abstract void actionPerformedBoto(ActionEvent event);
}
