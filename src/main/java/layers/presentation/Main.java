package layers.presentation;
import layers.presentation.views.VistaPrincipal;
import layers.presentation.controllers.CtrlPresentacio;
import layers.presentation.controllers.CtrlVistaGeneric;

public class Main {
    public static void main(String[] args) {
        CtrlPresentacio ctrlPresentacio = CtrlPresentacio.getCtrlPresentacio();
        CtrlVistaGeneric ctrlVistaSolucions = ctrlPresentacio.getCtrlVistaSolucions();
        CtrlVistaGeneric ctrlVistaCatalegAmbRestriccions = ctrlPresentacio.getCtrlVistaCatalegAmbRestriccions();

        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        vistaPrincipal.executar(ctrlVistaCatalegAmbRestriccions, ctrlVistaSolucions);
    }
}
