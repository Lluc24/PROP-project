package layers.presentation;

import javax.swing.*;
import layers.domain.*;
import layers.presentation.controllers.CtrlPreSolucions;

import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.event.*;

public class VistaInicialSolucio {
    private CtrlPreSolucions iCtrlPreSol;
    private JFrame frameVista = new JFrame("Vista Inicial Solucio");
    private JPanel panelContenidos = new JPanel();

    private JComboBox<String> boxSolucions;
    private JButton buttonCrearSolucio = new JButton("Crear nova solucio");
    private JButton buttonGestionarAlgorisme = new JButton("Canviar algorisme actual");

    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemSortir = new JMenuItem("Sortir");

    public VistaInicialSolucio (CtrlPreSolucions cps) {
        iCtrlPreSol = cps;
        inicialitzarComponents();
    }

    public void ferVisible() {
        frameVista.setVisible(true);
    }

    void inicialitzarComponents(){
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[] solucions = CtrlPreSolucions.getSolucions();
        boxSolucions = new JComboBox<>(solucions);

        panelContenidos = (JPanel)frameVista.getContentPane();
        panelContenidos.setLayout(new BoxLayout.Y_AXIS);
        panelContenidos.add(buttonGestionarAlgorisme);
        panelContenidos.add(buttonCrearSolucio);
        panelContenidos.add(boxSolucions);

        frameVista.add(panelContenidos);

        menuFile.add(menuitemSortir);
        menubarVista.add(menuFile);
        frameVista.setJMenuBar(menubarVista);

        menuitemSortir.addActionListener(e -> System.exit(0));

        buttonCrearSolucio.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickat el boto amb text: " +
                                texto);
                        actionPerformed_buttonCreaSolucio(event);
                    }
                });

    }

    public void actionPerformed_buttonCreaSolucio (ActionEvent event) {
        String resulDominio =
                iCtrlPreSol.botoCreaSolucio(event.getSource()).getText());
    }
}