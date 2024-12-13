package layers.presentation.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BotoGeneric extends JButton {
    protected int width = 100;
    protected int height = 20;

    public BotoGeneric() {
        super();
        inicialitzarBoto();
    }

    public BotoGeneric(String text) {
        super(text);
        inicialitzarBoto();
    }

    public BotoGeneric(int width, int height, String text) {
        super(text);
        this.width = width;
        this.height = height;
        inicialitzarBoto();
    }

    protected void inicialitzarBoto() {
        setSize(width, height);
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("S'ha clicat el boto " + getText());
                actionPerformedBoto(event);
            }
        });
    }

    protected abstract void actionPerformedBoto(ActionEvent event);
}
