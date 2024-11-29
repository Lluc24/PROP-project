package layers.presentation;

import javax.swing.*;
import layers.domain.*;
import layers.presentation.controllers.CtrlVistaSolucions;
import layers.presentation.views.VistaGeneric;

import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.event.*;

public class VistaPrincipalSolucio extends VistaGeneric {
    private CtrlVistaSolucions iCtrlVistaSols;
    private JLabel labelAlgorismeAct = new JLabel("Panel Informacion 1");

    public VistaPrincipalSolucio (CtrlVistaSolucions cps) {
        iCtrlVistaSols = cps;
        inicialitzarComponents();
    }

    public void ferVisible() {
        frameVista.setVisible(true);
    }

    void inicialitzarComponents(){
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frameVista = new JFrame("Vista inicial solucions");

        buttonAfegir = new JButton("Crear nova solucio");
        button2 = new JButton("Canviar l'algorisme actual");
        String[] solucions = iCtrlVistaSols.getSolucions();
        boxOpcions = new JComboBox<>(solucions);
        String algInfo = iCtrlVistaSols.getAlgorismeAct();
        labelAlgorismeAct = new JLabel(algInfo);

        panelContinguts = (JPanel)frameVista.getContentPane();
        panelContinguts.setLayout(new BoxLayout.Y_AXIS);
        panelContinguts.add(button2);
        panelContinguts.add(buttonAfegir);
        panelContinguts.add(labelAlgorismeAct);
        panelContinguts.add(boxOpcions);

        frameVista.add(panelContinguts);

        menuFile.add(menuitemSortir);
        menubarVista.add(menuFile);
        frameVista.setJMenuBar(menubarVista);

        menuitemSortir.addActionListener(e -> System.exit(0));

        buttonAfegir.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickat el boto amb text: " +
                                texto);
                        actionPerformed_buttonCreaSolucio(event);
                    }
                });

        button2.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickat el boto amb text: " +
                                texto);
                        actionPerformed_button2(event);
                    }
                });

    }

    public void actionPerformed_buttonCreaSolucio (ActionEvent event) {
        iCtrlVistaSols.botoCreaSolucio(event.getSource()).getText());

        //actualitza les solucions
        String[] solucions = CtrlVistaSolucions.getSolucions();
        boxOpcions = new JComboBox<>(solucions);
    }

    public void actionPerformed_button2 (ActionEvent event) {
        iCtrlVistaSols.botoCreaSolucio(event.getSource()).getText());

        //actualitza label
        String algInfo = iCtrlVistaSols.getAlgorismeAct();
        labelAlgorismeAct = new JLabel(algInfo);
    }
}