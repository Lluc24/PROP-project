package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    public CtrlVistaSolucions(CtrlGeneric ctrlGeneric) {
        super(ctrlGeneric);
    }

    @Override
    public void executar() {
        System.out.println("Metode executar de CtrlVistaSolucions");
    }
}
