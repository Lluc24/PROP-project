package layers.presentation.views;

import layers.presentation.controllers.CtrlPreSolucions;
import layers.presentation.controllers.CtrlVistaGeneric;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaGeneric {

    protected JFrame frameVista = new JFrame("Vista principal");
    private JPanel panelContinguts = new JPanel();

    private JComboBox<String> boxOpcions;
    private JButton buttonAfegir = new JButton("Afegir");
    private JButton button2 = new JButton("Boto 2");

    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemSortir = new JMenuItem("Sortir");

    public abstract VistaGeneric() {
    }

    public void executar(CtrlVistaGeneric ctrlVista) {
        System.out.println("Metode executar de VistaGeneric");
    }
    public void executar(CtrlVistaGeneric ctrlVista1, CtrlVistaGeneric ctrlVista2) {
        System.out.println("Metode executar de VistaGeneric");
    }

    public abstract void ferVisible() { }

}
