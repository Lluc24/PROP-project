package layers.presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class VistaGenerica {

    boolean teBotoTornar = true;

    protected String titolFrame = "Vista Generica";
    protected String ajuda = "Lorem Ipsum";
    private final String respecteA =
            "Lluc Santamaria Riba\n" +
            "lluc.santamaria@estudiantat.upc.edu\n" +
            "Afegiu-vos aqui";


    protected JFrame frameVista;
    protected JPanel panelContinguts;

    protected MenuBar menuBar;

    protected Menu menuFitxer;
    protected MenuItem menuItemSortir;

    protected Menu menuAjuda;
    protected MenuItem menuItemAjuda;
    protected MenuItem menuItemRespecteA;

    protected JButton botoTornar;

    protected VistaGenerica() {
        inicialitzarComponents();
    }

    protected void inicialitzarComponents() {

        // Inicialitzem el frame
        frameVista = new JFrame(titolFrame);
        frameVista.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Accio de sortir clicant X realitzada");
                sortir();
            }
        });
        frameVista.setSize(1000, 800);

        // Inicialitzem el panel
        panelContinguts = (JPanel) frameVista.getContentPane();
        panelContinguts.setLayout(new FlowLayout());

        // Inicialitzem el menuBar amb els seus actionListeners
        menuBar = new MenuBar();

        menuFitxer = new Menu("Fitxer"); // Menu Fitxer
        menuItemSortir = new MenuItem("Sortir"); // Item Sortir
        menuItemSortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Accio realitzada en Menu->Fitxer->Sortir");
                sortir();
            }
        });
        menuFitxer.add(menuItemSortir);
        menuBar.add(menuFitxer);

        menuAjuda = new Menu("Ajuda"); // Menu Ajuda
        menuItemAjuda = new MenuItem("? Ajuda"); // Item ? Ajuda
        menuItemAjuda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Accio realitzada en Menu->Ajuda->? Ajuda");
                mostraAjuda();
            }
        });
        menuAjuda.add(menuItemAjuda);
        menuItemRespecteA = new MenuItem("Respecte a"); // Item Respecte de
        menuItemRespecteA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Accio realitzada en Menu->Ajuda->Respecte a");
                mostraRespecteA();
            }
        });
        menuAjuda.add(menuItemRespecteA);

        menuBar.add(menuAjuda);
        frameVista.setMenuBar(menuBar);

        // Inicialitzem el boto de Tornar
        if (teBotoTornar) {
            botoTornar = new JButton("Tornar");
            botoTornar.setSize(30, 20);
            botoTornar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    System.out.println("S'ha clicat el boto 'Tornar'");
                    tornar();
                }
            });
            panelContinguts.add(botoTornar);
        }

    }

    public void setVisible(boolean visibilitat) {
        frameVista.setVisible(visibilitat);
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
        JOptionPane.showMessageDialog(frameVista, ajuda, "Ajuda", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void mostraRespecteA() {
        JOptionPane.showMessageDialog(frameVista, respecteA, "Respecte a", JOptionPane.INFORMATION_MESSAGE);
    }


    protected void tornar() {
        frameVista.dispose();
    }
}
