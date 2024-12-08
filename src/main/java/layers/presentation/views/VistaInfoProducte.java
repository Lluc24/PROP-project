package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaInfoProducte extends VistaControladors {
    private CtrlVistaCatalegAmbRestriccions controlVista;
    private String nom_prod;

    private Boolean primeraVegada = true;

    private JLabel labelNom_prod;


    public VistaInfoProducte(CtrlVistaCatalegAmbRestriccions cps, String nom_prod) {
        controlVista = cps;
        this.nom_prod = nom_prod;
    }

    public void executar() {
        if (primeraVegada) {
            titolFrame = "Vista Info producte: " +nom_prod;
            ajuda = "Estas a la vista de informacio del producte "+nom_prod+" aqui pots" +
                    "consultar les seves similituds amb la resta de productes" +
                    ", editar alguna d'aquestes similituds i eliminar el producte\n" +
                    "Delete: Elimina aquest producte\n" +
                    "Editar: Et permet editar la similitud amb el producte seleccionat del comboBox\n" +
                    "ComboBox: Et mostra les similituds amb la resta de productes, en pot seleccionar una\n" +
                    "Enrere: Et permet anar a l'anterior vista\n" +
                    "Sortir: Finalitzar l'aplicacio\n";
            frameVista.setVisible(true);
            super.executar();
        } else {
            frameVista.setVisible(true);
        }
    }

    @Override
    public void inicialitzarComponents() {
        super.inicialitzarComponents();

        //Label nom producte
        labelNom_prod = new JLabel("PRODUCTE: "+nom_prod);
        labelNom_prod.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(labelNom_prod);
        add(Box.createRigidArea(new Dimension(0,10)));

        //Boto eliminar
        textBotoAfegir = "ELIMINAR PRODUCTE";
        botoAfegir.setText(textBotoAfegir);
        botoAfegir.setBackground(Color.RED);
        botoAfegir.setForeground(Color.WHITE);

        //Boto editar
        textBotoMostrar = "EDITAR PRODUCTE";
        botoMostrar.setText(textBotoMostrar);

        //ComboBox
        String[] productes = controlVista.getProductes();
        String[] similituds = controlVista.getSimilituds();
        String[] Prod_Simi = new String[productes.length];
        for (int i = 0; i < productes.length; ++i) {
            Prod_Simi[i] = productes[i]+" -> "+similituds[i];
        }

        opcions.removeAllItems();
        for (String item : Prod_Simi) {
            opcions.addItem(item);
        }

    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            Eliminar();
        } else if (textBoto.equals(textBotoMostrar)) {
            String seleccionat = (String) opcions.getSelectedItem();
            if (seleccionat == null || seleccionat.isEmpty()) {
                JOptionPane.showMessageDialog(frameVista,
                        "No se ha seleccionat cap producte del ComboBox",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String parts[] = seleccionat.split(" -> ");
                String producteSelecionat = parts[0];
                EditarSimi(producteSelecionat);
            }
        } else {
            super.botoAccionat(textBoto);
        }
    }

    private double Input_Similitud(String producteSeleccionat, String result) {
        double ret = -1;
        String message = "Quina sera la nova similtud del producte "+nom_prod+
                " amb el producte "+producteSeleccionat+"\n" +
                "**RECORDA: 0 < Similitud < 100. HA DE SER UN DECIMAL**";
        result = JOptionPane.showInputDialog(frameVista, message, "Editar Similitud", JOptionPane.QUESTION_MESSAGE);
        if (!result.equals("NO_INPUT")) {
            try {
                ret = Double.parseDouble(result);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frameVista,
                            "Si us plau, introdueix un numero del 1 al 100",
                            "Error Input",
                            JOptionPane.ERROR_MESSAGE);
                return -1.0;
            }
        }
        return ret;

    }

    private void EditarSimi(String producteSeleccionat) {
        boolean ok = false;
        String result = "NO_INPUT";
        while(!ok) {
           double d = Input_Similitud(producteSeleccionat, result);
           if (d > 0 && d <= 100) {
               ok = true;
           } else {
               JOptionPane.showMessageDialog(frameVista,
                       "Si us plau, introdueix un numero del 1 al 100",
                       "Error Input",
                       JOptionPane.ERROR_MESSAGE);
           }
        }
        controlVista.editarSimilitud(nom_prod, producteSeleccionat, result);
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
