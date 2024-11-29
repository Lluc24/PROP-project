package layers.presentation.views;


import layers.presentation.controllers.CtrlPreSolucions;
import layers.presentation.controllers.CtrlVistaGeneric;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class VistaGenerica {

    boolean teBotoTornar = true;

    protected String titolFrame = "Vista Generica";
    protected String ajuda = "Lorem Ipsum";
    private final String respecteDe =
            "Lluc Santamaria Riba\n" +
            "lluc.santamaria@estudiantat.upc.edu\n" +
            "Afegiu-vos aqui";


    protected JFrame frameVista;
    protected JPanel panelContinguts;

    protected JMenuBar menuBar;

    protected JMenu menuFitxer;
    protected JMenuItem menuItemSortir;

    protected JMenu menuAjuda;
    protected JMenuItem menuItemAjuda;
    protected JMenuItem menuItemRespecteA;

    protected JButtonTornar botoTornar;

    protected VistaGenerica() {
        inicialitzarComponents();
    }

    protected void inicialitzarComponents() {

        // Inicialitzem el frame
        frameVista = new JFrame(titolFrame);
        frameVista.setDefaultCloseOperation(WindowConstants.DO_NOTHIING_ON_CLOSE);
        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Accio de sortir clicant X realitzada");
                sortir();
            }
        });

        // Inicialitzem el panel
        panelContinguts = (JPanel) frameVista.getContentPane();
        panelContinguts.setLayout(new FlowLayout());

        // Inicialitzem el menuBar amb els seus actionListeners
        menuBar = new JMenuBar();

        menuFitxer = new JMenu("Fitxer"); // Menu Fitxer
        menuItemSortir = new JMenuItem("Sortir"); // Item Sortir
        menuItemSortir.addActionListener(new ActionListener() {
            public void ActionPerformed(ActionEvent event) {
                System.out.println("Accio realitzada en Menu->Fitxer->Sortir");
                sortir();
            }
        });
        menuFitxer.add(menuItemSortir);
        menuBar.add(menuFitxer);

        menuAjuda = new JMenu("Ajuda"); // Menu Ajuda
        menuItemAjuda = new JMenuItem("? Ajuda"); // Item ? Ajuda
        menuItemAjuda.addActionListener(new ActionListener() {
            public void ActionPerformed(ActionEvent event) {
                System.out.println("Accio realitzada en Menu->Ajuda->? Ajuda")
                mostraAjuda();
            }
        }):
        menuAjuda.add(menuItemAjuda);
        menuItemRespecteA = new JMenuItem("Respecte a"); // Item Respecte de
        menuItemRespecteA.addActionListener(new ActionListener() {
            public void ActionPerformed(ActionEvent event) {
                System.out.println("Accio realitzada en Menu->Ajuda->Respecte a")
                mostraRespecteA();
            }
        });
        menuAjuda.add(menuItemRespecteA);

        menuBar.add(menuAjuda);
        panelContinguts.setMenuBar(menuBar);

        // Inicialitzem el boto de Tornar
        if (teBotoTornar) {
            botoTornar = new JButton("Tornar");
            botoSortir.setSize(30, 20);
            botoCataleg.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    System.out.println("S'ha clicat el boto 'Tornar'");
                    tornar();
                }
            });
            panelContinguts.add(botoSortir);
        }

    }

    public void ferVisible() {
        frameVista.setVisible(true);
    }

    protected void sortir() {
        int result = JOptionPane.showConfirmDialog(frameVista,
                "Estas segur que vols sortir de l'aplicacio?",
                "Sortir de l'aplicacio",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            System.out.println("Sortim de l'aplicacio");
            frameVista.dispose();
        }
    }

    protected void mostraAjuda() {

    }

    protected void mostraRespecteA() {

    }


    protected void tornar() {
        frameVista.dispose();
    }
}
