package layers.presentation;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;

public abstract class Vista {
    protected CtrlVistaCatalegAmbRestriccions controlador;

    public Vista(CtrlVistaCatalegAmbRestriccions controlador) {
        this.controlador = controlador;
    }

    public abstract void mostrar();
    public abstract void ocultar();
}
