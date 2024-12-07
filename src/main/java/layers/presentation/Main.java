package layers.presentation;
import layers.presentation.views.VistaInfoSolucio;
import layers.presentation.views.VistaPrincipal;
import layers.presentation.controllers.CtrlPresentacio;
import layers.presentation.controllers.CtrlVistaGeneric;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {
                        CtrlPresentacio ctrlPresentacio = CtrlPresentacio.getCtrlPresentacio();
                        CtrlVistaGeneric ctrlVistaSolucions = ctrlPresentacio.getCtrlVistaSolucions();
                        CtrlVistaGeneric ctrlVistaCatalegAmbRestriccions = ctrlPresentacio.getCtrlVistaCatalegAmbRestriccions();

                        //VistaPrincipal vistaPrincipal = new VistaPrincipal();
                        //vistaPrincipal.executar(ctrlVistaCatalegAmbRestriccions, ctrlVistaSolucions);
                        new VistaInfoSolucio().executar(ctrlVistaSolucions, new ArrayList<>());
                    }
                }

        );
    }
}
