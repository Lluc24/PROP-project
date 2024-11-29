package layers.presentation.views;

import layers.presentation.controllers.CtrlPreSolucions;
import layers.presentation.controllers.CtrlVistaGeneric;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaGeneric {

    protected JFrame frameVista = new JFrame("Vista principal");
    protected JPanel panelContinguts = new JPanel();

    protected JComboBox<String> boxOpcions;
    protected JButton buttonAfegir = new JButton("Afegir");
    protected JButton button2 = new JButton("Boto 2");

    protected JMenuBar menubarVista = new JMenuBar();
    protected JMenu menuFile = new JMenu("File");
    protected JMenuItem menuitemSortir = new JMenuItem("Sortir");

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
