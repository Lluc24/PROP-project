package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;

public abstract class CtrlVistaGeneric {
    protected CtrlGeneric ctrl = null;

    protected CtrlVistaGeneric(CtrlGeneric ctrlGeneric) {
        this.ctrl = ctrlGeneric;
    }

    protected CtrlVistaGeneric() {
    }

    public void executar() {
        System.out.println("Metode executar de CtrlVistaGeneric");
    }

    public void netejar() {
        return;
    }

    public abstract void importar(String path, String nomFitxer);

    public abstract void exportar(String path, String nomFitxer);
}
