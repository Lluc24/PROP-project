package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;

public class VistaPrincipal extends VistaGeneric {
    private CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions;
    private CtrlVistaSolucions ctrlVistaSolucions;

    private JFrame frameVista = new JFrame("Vista Principal");
    private JPanel panelContingut;
    private JButton botoCataleg = new JButton("Gestionar  cataleg");
    private JButton botoSolucions = new JButton("Gestionar Solucions");

    public void executar(CtrlVistaGeneric ctrl1, CtrlVistaGeneric ctrl2) {
        ctrlVistaCatalegAmbRestriccions = (CtrlVistaCatalegAmbRestriccions)ctrl1;
        ctrlVistaSolucions = (CtrlVistaSolucions)ctrl2;

        inicialitzarComponents();
    }

    private void inicialitzarComponents() {
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panelContingut = (JPanel) frameVista.getContentPane();
        panelContingut.add(botoCataleg);
        panelContingut.add(botoSolucions);
        frameVista.add(panelContingut);
    }
}
