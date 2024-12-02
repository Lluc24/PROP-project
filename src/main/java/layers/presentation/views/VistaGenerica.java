package layers.presentation.views;

import layers.presentation.utils.BotoGeneric;
import layers.presentation.utils.MenuItemGeneric;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public abstract class VistaGenerica extends JPanel {
    protected int width = 800;
    protected int height = 600;

    protected boolean teBotoTornar = true;
    protected String textBotoTornar = "Tornar";
    protected Boto botoTornar;

    protected String titolFrame = "Vista Generica";
    protected String ajuda = "Lorem Ipsum";
    private final String respecteA =
            "Lluc Santamaria Riba\n" +
            "lluc.santamaria@estudiantat.upc.edu\n" +
            "Eulalia Peiret Santacana\n" +
            "eulalia.peiret@estudiantat.upc.edu\n" +
            "Afegiu-vos aqui";


    protected JFrame frameVista;
    protected JPanel panelContinguts;
    protected JPanel panelPageEnd;

    protected MenuBar menuBar;

    protected String textMenuFitxer = "Fitxer";
    protected Menu menuFitxer;
    protected String textItemSortir = "Sortir";
    protected Item menuItemSortir;

    protected String textMenuAjuda = "Ajuda";
    protected Menu menuAjuda;
    protected String textItemAjuda = "? Ajuda";
    protected Item menuItemAjuda;
    protected String textItemRespecteA = "Respecte a";
    protected Item menuItemRespecteA;

    protected VistaGenerica() {
        super();
    }

    protected void executar() {
        inicialitzarComponents();
        frameVista.setVisible(true);
    }

    protected void inicialitzarComponents() {

        // Inicialitzem el frame principal
        frameVista = new JFrame(titolFrame);
        frameVista.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Accio de sortir clicant X realitzada");
                sortir();
            }
        });
        frameVista.setSize(width, height);


        // Inicialitzem el panel central (herencia)
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Inicialitzem el panelPageEnd
        panelPageEnd = new JPanel();
        panelPageEnd.setLayout(new BoxLayout(panelPageEnd, BoxLayout.LINE_AXIS));
        panelPageEnd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPageEnd.add(Box.createHorizontalGlue());


        // Si teBotoTornar, inicialitzem el botoTornar i l'afegim al panelPageEnd
        if (teBotoTornar) {
            botoTornar = new Boto(30, 20, textBotoTornar);
            panelPageEnd.add(botoTornar);
        }


        // Inicialitzem el content pane
        panelContinguts = (JPanel) frameVista.getContentPane();
        panelContinguts.setLayout(new BorderLayout());
        panelContinguts.add(this, BorderLayout.CENTER);
        panelContinguts.add(panelPageEnd, BorderLayout.PAGE_END);


        // Inicialitzem el menuBar
        menuBar = new MenuBar();

        menuFitxer = new Menu(textMenuFitxer); // Menu Fitxer
        menuItemSortir = new Item(textItemSortir); // Item Sortir
        menuFitxer.add(menuItemSortir);
        menuBar.add(menuFitxer);

        menuAjuda = new Menu(textMenuAjuda); // Menu Ajuda
        menuItemAjuda = new Item(textItemAjuda); // Item ? Ajuda
        menuAjuda.add(menuItemAjuda);
        menuItemRespecteA = new Item(textItemRespecteA); // Item Respecte a
        menuAjuda.add(menuItemRespecteA);
        menuBar.add(menuAjuda);

        frameVista.setMenuBar(menuBar);
    }

    protected void sortir() {
        int result = JOptionPane.showConfirmDialog(frameVista,
                "Estas segur que vols sortir de l'aplicacio?",
                "Sortir de l'aplicacio",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            System.out.println("Fins la proxima!");
            frameVista.dispose();
        }
    }

    protected void mostraAjuda() {
        JOptionPane.showMessageDialog(frameVista, ajuda, textItemAjuda, JOptionPane.INFORMATION_MESSAGE);
    }

    protected void mostraRespecteA() {
        JOptionPane.showMessageDialog(frameVista, respecteA, textItemRespecteA, JOptionPane.INFORMATION_MESSAGE);
    }

    protected void tornar() {
        frameVista.dispose();
    }

    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoTornar)) tornar();
    }

    protected void itemAccionat(String textItem) {
        if (textItem.equals(textItemSortir)) {
            sortir();
        }
        else if (textItem.equals(textItemAjuda)) {
            mostraAjuda();
        }
        else if (textItem.equals(textItemRespecteA)) {
            mostraRespecteA();
        }
    }

    protected class Boto extends BotoGeneric {
        public Boto(String text) {
            super(text);
        }

        public Boto(int width, int height, String text) {
            super(width, height, text);
        }

        protected void actionPerformedBoto(ActionEvent event) {
            String textBoto = ((JButton) event.getSource()).getText();
            botoAccionat(textBoto);
        }
    }

    protected class Item extends MenuItemGeneric {
        public Item(String text) {
            super(text);
        }

        protected void actionPerformedItem(ActionEvent event) {
            String textItem = ((MenuItem) event.getSource()).getLabel();
            itemAccionat(textItem);
        }
    }
}
