package layers.presentation;
import layers.presentation.views.VistaPrincipal;
import layers.presentation.controllers.CtrlPresentacio;
import layers.presentation.controllers.CtrlVistaGeneric;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater (
            new Runnable() {
                public void run() {
                    CtrlPresentacio ctrlPresentacio = CtrlPresentacio.getCtrlPresentacio();
                    CtrlVistaGeneric ctrlVistaSolucions = ctrlPresentacio.getCtrlVistaSolucions();
                    CtrlVistaGeneric ctrlVistaCatalegAmbRestriccions = ctrlPresentacio.getCtrlVistaCatalegAmbRestriccions();

                    VistaPrincipal vistaPrincipal = new VistaPrincipal(ctrlVistaCatalegAmbRestriccions, ctrlVistaSolucions);
                    vistaPrincipal.executar();
                }
            }
        );
    }
}
