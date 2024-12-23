package layers.presentation.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *  BotoGeneric
 *  Classe abstracte per definir un comportament comu dels items.
 * @see MenuItem
 * @author Lluc Santamaria Riba
 * @version 3.0
 *
 *
 * Exten un MenuItem. Permet definir un comportament comu que seguiran tots els
 * items de les barres de menu de totes les vistes.
 */
public abstract class MenuItemGeneric extends MenuItem {

    /**
     * Constructora que especifica el text de l'item.
     * @param text: Text de l'item
     */
    public MenuItemGeneric(String text) {
        super(text);
        inicialitzarItem();
    }

    /**
     * Metode per inicialitzar la interficie de l'item i definir la logica
     * comuna de tots els items.
     */
    protected void inicialitzarItem() {
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String textItem = getLabel();
                String textMenu = ((Menu) getParent()).getLabel();
                System.out.println("S'ha clicat l'item " + textMenu + "->" + textItem);
                actionPerformedItem(event);
            }
        });
    }

    /**
     * Metode abstracte executat cada vegada que s'acciona un item.
     * @param event: L'event capturat pel listener a l'accionar l'item.
     */
    protected abstract void actionPerformedItem(ActionEvent event);
}
