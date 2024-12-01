package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaInfoProducte extends VistaGeneric {
    private CtrlVistaCatalegAmbRestriccions controlVista;
    private JLabel labelNom_prod = new JLabel("Nom del producte");
    private JLabel labelSimis = new JLabel("Productes amb similituds");
    private String nom_prod;

    private JList<String> llista_simi;

    public VistaInfoProducte(CtrlVistaCatalegAmbRestriccions cps, String nom_prod) {
        controlVista = cps;
        this.nom_prod = nom_prod;
        inicialitzarComponents();
    }

    public void ferVisible() {
        frameVista.setVisible(true);
    }

    public void inicialitzarComponents() {
        frameVista.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panelContinguts = (JPanel) frameVista.getContentPane();
        panelContinguts.add(buttonAfegir);
        panelContinguts.add(labelNom_prod);

        //Label Producte
        labelNom_prod.setText(nom_prod);
        labelNom_prod.setVerticalAlignment(SwingConstants.TOP); //Alineat adalt

        //Button delete
        buttonAfegir = new JButton("ELIMINAR PRODUCTE");
        buttonAfegir.setBackground(Color.RED);
        buttonAfegir.setForeground(Color.WHITE);
        buttonAfegir.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("S'ha cliclat el boto per eliminar producte");
                        Eliminar();
                    }
                });
        //Label similituds
        labelNom_prod.setText("Producte amb similituds");
        labelNom_prod.setVerticalAlignment(SwingConstants.TOP); //Alineat adalt

        //List productes + similituds
        String[] productes = controlVista.getProductes();
        int[] simis = controlVista.getSimilituds();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < productes.length; ++i) {
            listModel.addElement(productes[i]+ " -> "+simis[i]);
        }
        llista_simi = new JList<>(listModel);
        llista_simi.addListSelectionListener
                (new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String seleccionat = llista_simi.getSelectedValue();
                        if (seleccionat != null) {
                            String[] parts = seleccionat.split(" -> ");
                            String nom_editar = parts[0];
                            System.out.println("S'ha seleccionat "+ nom_editar);
                            EditarSimi(nom_editar);
                        }
                    }
                });
        panelContinguts.add(llista_simi);
        JScrollPane scroll = new JScrollPane(llista_simi);



    }

    private void EditarSimi(String nom_prod) {
        //
    }

    public void Eliminar() {
        int result = JOptionPane.showConfirmDialog(frameVista,
                        "Estas segur que vols eliminar el producte "+nom_prod,
                        "Eliminar Producte", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            controlVista.eliminarProducte(nom_prod);
            frameVista.dispose();
        }
    }
}
