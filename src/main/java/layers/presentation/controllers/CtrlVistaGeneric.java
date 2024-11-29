package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;

public abstract class CtrlVistaGeneric {
    CtrlGeneric ctrl = null;

    public CtrlVistaGeneric(CtrlGeneric ctrlGeneric) {
        this.ctrl = ctrlGeneric;
    }

    public CtrlVistaGeneric() {
    }

    public void executar() {
        System.out.println("Metode executar de CtrlVistaGeneric");
    }

    public void netejar() {
        return;
    }
}
