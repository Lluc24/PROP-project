package layers.presentation;
import layers.domain.Producte;
import layers.presentation.views.VistaPrincipal;
import layers.presentation.controllers.CtrlPresentacio;
import layers.presentation.controllers.CtrlVistaGeneric;

/**
 *  Main
 *  Es la classe que inicia l'execucio del projecte de la tercera entrega
 * @author Lluc Santamaria Riba
 * @version 3.1
 *
 * <p><b>Informació:</b></p>
 * Conte el metode main utilitzat pel gradle per donar inici a l'execucio.
 */
public class Main {

    /**
     * Metode main que inicia l'execucio del projecte final (interficie grafica).
     *
     * @param args: Els arguments passats en l'execucio del programa. Son ignorats
     */
    public static void main(String[] args) {

        // Executem la vista principal en un AWT event-dispatching thread per no perjudicar el thread principal
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
