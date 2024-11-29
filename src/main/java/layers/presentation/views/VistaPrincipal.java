package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;

public class VistaPrincipal extends VistaGenerica {
    private CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions;
    private CtrlVistaSolucions ctrlVistaSolucions;

    private JFrame frameVista;
    private JPanel panelContingut;
    private JButton botoCataleg;
    private JButton botoSolucions = new JButton("Gestionar Solucions");

    VistaPrincipal() {

    }

    public void executar(CtrlVistaGeneric ctrl1, CtrlVistaGeneric ctrl2) {
        ctrlVistaCatalegAmbRestriccions = (CtrlVistaCatalegAmbRestriccions)ctrl1;
        ctrlVistaSolucions = (CtrlVistaSolucions)ctrl2;

        inicialitzarComponents();
    }

    private void inicialitzarComponents() {

        // Inicialitzem el frame
        frameVista = new JFrame("Vista Principal");
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Inicialitzem el panel
        panelContingut = (JPanel) frameVista.getContentPane();
        panel.setLayout(new FlowLayout());

        // Inicialitzem el boto Gestionar Cataleg
        botoCataleg = new JButton("Gestionar Cataleg");
        botoCataleg.setSize(100, 20);
        botoCataleg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("S'ha clicat el boto 'Gestionar Cataleg'");
                actionPerformedBotoCataleg(event);
            }
        });

        // Inicialitzem el boto Gestionar Solucions
        botoCataleg = new JButton("Gestionar Solucions");
        botoCataleg.setSize(100, 20);
        botoCataleg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("S'ha clicat el boto 'Gestionar Solucions'");
                actionPerformedBotoSolucions(event);
            }
        });
    }

    private void actionPerformedBotoCataleg(ActionEvent event) {
        ctrlVistaCatalegAmbRestriccions.executar();
    }

    private void actionPerformedBotoSolucions(ActionEvent event) {
        ctrlVistaSolucions.executar();
    }
}
