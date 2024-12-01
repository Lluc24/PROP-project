package layers.presentation.utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class MenuItemGeneric extends MenuItem {
    public MenuItemGeneric(String text) {
        super(text);
        inicialitzarItem();
    }

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

    protected abstract void actionPerformedItem(ActionEvent event);
}
