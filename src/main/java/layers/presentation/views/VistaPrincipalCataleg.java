package layers.presentation.views;

import javax.swing.*;
import layers.domain.*;
import layers.presentation.controllers.CtrlPreSolucions;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.views.VistaGeneric;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

import layers.presentation.controllers.CtrlVistaGeneric;

public class VistaPrincipalCataleg extends VistaGeneric {
    private CtrlVistaCatalegAmbRestriccions controlVista;
    
    public VistaPrincipalCataleg(CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
        inicialitzarComponents();
    }

    @Override
    public void ferVisible() {
        frameVista.setVisible(true);
    }

    @Override
    public void inicialitzarComponents() {
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        boxOpcions = new JComboBox<String>();
        //menubarVista = new JMenuBar();
        //menuFile = new JMenu("File");

        panelContinguts = (JPanel)frameVista.getContentPane();
        panelContinguts.setLayout(new BoxLayout.Y_AXIS);
        panelContinguts.add(boxOpcions);
        panelContinguts.add(button2);
        panelContinguts.add(buttonAfegir);

        frameVista.add(panelContinguts);


        //Boto afegir
        buttonAfegir = new JButton("Afegir productes");
        buttonAfegir.setSize(100, 80);
        buttonAfegir.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("S'ha clicat al boto per afegir productes");
                        canviVistaAfegirProducte();
                    }
                });
        button2 = new JButton("Consultar Restriccions");
        button2.setSize(100, 80);
        button2.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("S'ha cliclat al boto per consultar restriccions");
                        canviVistaConsultarRest();
                    }
                });

        String[] productes = controlVista.getProductes();
        boxOpcions = new JComboBox<>(productes);
        boxOpcions.setPreferredSize(new Dimension(200, 50));
        boxOpcions.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String seleccionat = (String) boxOpcions.getSelectedItem();
                        if (seleccionat != null) {
                            System.out.println("Seleccionat producte amb nom: " + seleccionat);
                            canviaVistaInfoProd(seleccionat);
                        } else {
                            System.out.println("No se ha seleccionado nada");
                        }
                    }
                });

    }

    private void canviVistaAfegirProducte() {
        frameVista.dispose(); // AIXO ESTA BE????
        controlVista.canviaVista("AfegirProductes");
    }

    public void canviVistaConsultarRest() {
        frameVista.dispose();
        controlVista.canviarVista("ConsultarRestriccions");
    }

    public void canviaVistaInfoProd(String prod) {
        frameVista.dispose();
        controlVista.canviarVista("InfoProducte", prod);
    }

}

