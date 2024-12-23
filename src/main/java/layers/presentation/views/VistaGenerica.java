package layers.presentation.views;

import layers.presentation.utils.BotoGeneric;
import layers.presentation.utils.MenuItemGeneric;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

/**
 * VistaGenerica
 * Es la vista en el nivell mes superior de l'herencia.
 * @see JPanel
 * @author Lluc Santamaria Riba
 * @version 3.1
 *
 * <p><b>Informació:</b></p>
 * Es la vista de la qual totes les altres hereten directament o indirectament.
 * Defineix un compartment i un esquema comu per totes les subclasses.
 * Es una classe abstracte.
 * Aquesta vista permet realitzar les seguents accions amb botons o items
 * <ul>
 *     <li><b>Tornar:</b> Boto de tornar (opcional) per anar a la vista previa.</li>
 *     <li><b>Sortir:</b> Item per sortir del sistema.</li>
 *     <li><b>Respecte a:</b> Item per obtenir informacio dels autors.</li>
 *     <li><b>Ajuda:</b> Item per obtenir ajuda. Cada vista hereu ha de posar la corresponent.</li>
 * </ul>
 */
public abstract class VistaGenerica extends JPanel {

    /** L'amplada de la vista. */
    protected int width = 800;
    /** L'alcada de la vista. */
    protected int height = 600;

    /** Boolea que indica si el boto de tornar es present o no. */
    protected boolean teBotoTornar = true;
    /** Text del boto de tornar */
    protected String textBotoTornar = "Tornar";
    /** Boto de tornar. */
    protected Boto botoTornar;

    /** Titol de la vista. */
    protected String titolFrame = "Vista Generica";

    /** Text d'ajuda particular de cada vista. */
    protected String ajuda = "Lorem Ipsum";

    /** Text de la informacio dels autors. */
    private final String respecteA =
            "Lluc Santamaria Riba\n" +
            "lluc.santamaria@estudiantat.upc.edu\n" +
            "Eulalia Peiret Santacana\n" +
            "eulalia.peiret@estudiantat.upc.edu\n" +
            "Efrain Tito Cortes\n" +
            "efrain.tito@estudiantat.upc.edu\n" +
            "Alejandro Lorenzo Navarro\n" +
            "alejandro.lorenzo@estudiantat.upc.edu\n";


    /** Frame principal de la vista. */
    protected JFrame frameVista;
    /** Panel principal de continguts de la vista. */
    protected JPanel panelContinguts;
    /** Panel inferior on es mostra el boto de tornar. */
    protected JPanel panelPageEnd;

    /** Barra de menu de la vista. */
    protected MenuBar menuBar;

    /** Text del submenu per gestions. */
    protected String textMenuFitxer = "Fitxer";
    /** Submenu per gestions. */
    protected Menu menuFitxer;
    /** Text de l'item per sortir. */
    protected String textItemSortir = "Sortir";
    /** Item per sortir. */
    protected Item menuItemSortir;

    /** Text del submenu per consultes. */
    protected String textMenuAjuda = "Ajuda";
    /** Submenu per consultes. */
    protected Menu menuAjuda;
    /** Text de l'item per obtenir d'ajuda. */
    protected String textItemAjuda = "? Ajuda";
    /** Item per obtenir ajuda. */
    protected Item menuItemAjuda;
    /** Text del l'item per obtenir informacio dels autors. */
    protected String textItemRespecteA = "Respecte a";
    /** Item per obtenir informacio dels autors. */
    protected Item menuItemRespecteA;

    /**
     * Constuctora per defecte de la classe.
     */
    protected VistaGenerica() {
        super();
    }

    /**
     * Metode per iniciar l'execucio de la vista.
     * Inicialitza els components afegint-los al lloc corresponent i mostra la vista.
     */
    protected void executar() {
        inicialitzarComponents();
        frameVista.setVisible(true);
    }

    /**
     * Metode que inicialitza els components i els insereix al seu lloc corresponent de la vista.
     */
    protected void inicialitzarComponents() {
        // En cas que la vista ja fos inicialitzada, netegem els components
        super.removeAll();
        if (frameVista != null) frameVista.dispose();

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

    /**
     * Metode per acabar l'execucio del sistema demanant confirmacio.
     */
    protected void sortir() {
        int result = JOptionPane.showConfirmDialog(frameVista,
                "Estas segur que vols sortir de l'aplicacio?",
                "Sortir de l'aplicacio",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            System.out.println("Fins la proxima!");
            sortirSistema();
        }
    }

    /**
     * Metode per mostrar l'ajuda.
     */
    protected void mostraAjuda() {
        JOptionPane.showMessageDialog(frameVista, ajuda, textItemAjuda, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Metode per mostrar la informacio dels autors.
     */
    protected void mostraRespecteA() {
        JOptionPane.showMessageDialog(frameVista, respecteA, textItemRespecteA, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Metode abstracte per finalitzar l'execucio del sistema.
     */
    protected abstract void sortirSistema();

    /**
     * Metode per tornar a la vista anterior.
     */
    public void tornar() {
        frameVista.dispose();
    }

    /**
     * Metode per ocultar (finalitzar) l'execucio de la vista.
     */
    public void ocultar() {
        frameVista.dispose();
    }

    /**
     * Metode per mostrar la vista en cas de ser invisible.
     */
    public void mostrar() {
        frameVista.setVisible(true);
    }

    /**
     * Metode botoAccionat executat cada vegada que s'acciona un boto de la vista.
     * El boto de la vista que s'ha accionat es diferencia pel text que hi ha en aquest.
     * @param textBoto: El text que hi ha en el boto que s'ha accionat.
     */
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoTornar)) tornar();
    }

    /**
     * Metode itemAccionat executat cada vegada que s'acciona un item de la barra de menu de la vista.
     * L'item de la vista que s'ha accionat es diferencia pel text que hi ha en aquest.
     * @param textItem: Text que hi ha en l'item que s'ha accionat.
     */
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

    /**
     *  Boto
     *  Defineix un esquema de comportament comu per tots els botons.
     * @see BotoGeneric
     * @author Lluc Santamaria Riba
     * @version 3.0
     *
     * <p><b>Informació:</b></p>
     * Es la classe que s'utilitzara per definir botons en totes les vistes.
     * Defineix un comportament comu i facil d'extendre. Quan es clica un boto
     * s'executa el metode botoAccionat amb el text del boto que s'ha accionat
     * com a parametre
     */
    protected class Boto extends BotoGeneric {
        /**
         * Constructora per defecte
         */
        public Boto() {
            super();
        }

        /**
         * Constructora que especifica el text del boto.
         * @param text: Text que es mostrara en el boto.
         */
        public Boto(String text) {
            super(text);
        }

        /**
         * Constructora que especifica el text i el tamanny del boto.
         * @param width: Amplada en pixels del boto.
         * @param height: Alcada en pixels del boto.
         * @param text: Text que es mostrara en el boto.
         */
        public Boto(int width, int height, String text) {
            super(width, height, text);
        }

        /**
         * Metode que s'executa cada vegada que s'acciona un boto
         * @param event: Event que ha disparat el listener
         */
        protected void actionPerformedBoto(ActionEvent event) {
            String textBoto = ((JButton) event.getSource()).getText();
            botoAccionat(textBoto);
        }
    }

    /**
     * Item
     *  Defineix un esquema de comportament comu per tots els items.
     * @see MenuItemGeneric
     * @author Lluc Santamaria Riba
     * @version 3.0
     *
     * <p><b>Informació:</b></p>
     * Es la classe que s'utilitzara per definir items de la barra de menu en totes les vistes.
     * Defineix un comportament comu i facil d'extendre. Quan es clica un item
     * s'executa el metode itemAccionat amb el text de l'item que s'ha accionat
     * com a parametre.
     */
    protected class Item extends MenuItemGeneric {
        /**
         * Constructora que especifica el text de l'item.
         * @param text: Text de l'item
         */
        public Item(String text) {
            super(text);
        }

        /**
         * Metode que s'executa cada vegada que s'acciona un item.
         * @param event: Event que ha disparat el listener.
         */
        protected void actionPerformedItem(ActionEvent event) {
            String textItem = ((MenuItem) event.getSource()).getLabel();
            itemAccionat(textItem);
        }
    }
}
