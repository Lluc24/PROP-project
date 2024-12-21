package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.excepcions.FormatInputNoValid;

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

    public abstract void importar(String path, String nomFitxer) throws FormatInputNoValid;

    public abstract void exportar(String path, String nomFitxer) throws FormatInputNoValid;

    public void sortirSistema() {
        System.exit(0);
    }
}
