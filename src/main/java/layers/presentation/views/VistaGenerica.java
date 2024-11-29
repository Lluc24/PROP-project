package layers.presentation.views;


import layers.presentation.controllers.CtrlPreSolucions;
import layers.presentation.controllers.CtrlVistaGeneric;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class VistaGenerica {

    protected JFrame frameVista = new JFrame("Vista Incial");
    protected JPanel panelContinguts = new JPanel();

    protected JMenuBar menubarVista = new JMenuBar();
    protected JMenu menuFile = new JMenu("File");
    protected JButton menuitemSortir = new JButton("Sortir");

    public abstract void ferVisible();
    public abstract void inicialitzarComponents();
}
