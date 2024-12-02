qpackage layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;

import javax.swing.*;

public abstract class VistaControladors extends VistaGenerica {

    protected JFrame frameVista = new JFrame("Vista controladors");
    protected JPanel panelContinguts = new JPanel();

    protected JComboBox<String> boxOpcions;
    protected JButton buttonAfegir = new JButton("Afegir");
    protected JButton button2 = new JButton("Boto 2");

    protected JMenuBar menubarVista = new JMenuBar();
    protected JMenu menuFile = new JMenu("File");
    protected JMenuItem menuitemSortir = new JMenuItem("Sortir");

    public abstract class VistaGeneric() {

    }

    public void executar(CtrlVistaGeneric ctrlVista) {
        System.out.println("Metode executar de VistaControladors");
    }
    public void executar(CtrlVistaGeneric ctrlVista1, CtrlVistaGeneric ctrlVista2) {
        System.out.println("Metode executar de VistaControladors");
    }

    public abstract void ferVisible();

    public abstract void inicialitzarComponents();
}
