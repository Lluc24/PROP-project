package layers.presentation.controllers;
import layers.domain.controllers.CtrlGeneric;

public class CtrlVistaCatalegAmbRestriccions extends CtrlVistaGeneric {
    public CtrlVistaCatalegAmbRestriccions(CtrlGeneric ctrlGeneric) {
        super(ctrlGeneric);
    }

    @Override
    public void executar() {
        System.out.println("Metode executar de CtrlVistaCatalegAmbRestriccions");
    }
}
