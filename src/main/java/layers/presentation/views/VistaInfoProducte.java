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

    private Boto BotoCanviarNom;
    private String textBotoCanviarNom = "ELIMINAR PRODUCTE";

    private String textCancelat = "CANCELADO";


    public VistaInfoProducte(CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    public void executar(String nom) {
        if (primeraVegada) {
            this.nom_prod = nom;
            titolFrame = "Vista Info producte";
            ajuda = "Estas a la vista de informacio del producte "+nom_prod+" aqui pots" +
                    "consultar les seves similituds amb la resta de productes" +
                    ", editar alguna d'aquestes similituds i eliminar el producte\n" +
                    "Delete: Elimina aquest producte\n" +
                    "Editar: Et permet editar la similitud amb el producte seleccionat del comboBox\n" +
                    "ComboBox: Et mostra les similituds amb la resta de productes, en pot seleccionar una\n" +
                    "Enrere: Et permet anar a l'anterior vista\n" +
                    "Sortir: Finalitzar l'aplicacio\n";
            super.executar();
            actualitzarComponents();
            frameVista.setVisible(true);
        } else {
                actualitzarComponents();
            frameVista.setVisible(true);
        }
    }

    @Override
    public void inicialitzarComponents() {
        //Label nom producte
        labelNom_prod = new JLabel("PRODUCTE: "+nom_prod);
        labelNom_prod.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(labelNom_prod);
        add(Box.createRigidArea(new Dimension(0,10)));

        super.inicialitzarComponents();

        //Boto editar
        textBotoMostrar = "EDITAR PRODUCTE";
        botoMostrar.setText(textBotoMostrar);

        //Boto eliminar
        textBotoAfegir = "CANVIAR NOM";
        botoAfegir.setText(textBotoAfegir);


        BotoCanviarNom = new Boto(textBotoCanviarNom);
        BotoCanviarNom.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(BotoCanviarNom);
        BotoCanviarNom.setBackground(Color.RED);
        BotoCanviarNom.setForeground(Color.WHITE);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(Box.createVerticalGlue());


        //ComboBox
        String[] productes = controlVista.getProductes();
        String[] similituds = controlVista.getSimilituds();
        String[] Prod_Simi = new String[productes.length];
        for (int i = 0; i < productes.length; ++i) {
            Prod_Simi[i] = productes[i]+" -> "+similituds[i];
        }

        String prodSame = nom_prod+" -> 0.0";
        opcions.removeAllItems();
        for (String item : Prod_Simi) {
            if (!item.equals(prodSame)) opcions.addItem(item);
        }


    }

    public void actualitzarComponents() {

        labelNom_prod.setText("PRODUCTE: "+nom_prod);

        String[] productes = controlVista.getProductes();
        String[] similituds = controlVista.getSimilituds();
        String[] Prod_Simi = new String[productes.length];
        for (int i = 0; i < productes.length; ++i) {
            Prod_Simi[i] = productes[i]+" -> "+similituds[i];
        }

        String prodSame = nom_prod+" -> 0.0";
        opcions.removeAllItems();
        for (String item : Prod_Simi) {
            if (!item.equals(prodSame)) opcions.addItem(item);
        }
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoCanviarNom)) {
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
            actualitzarComponents();
        } else if (textBoto.equals(textBotoAfegir)) {
            canviaNom();
            actualitzarComponents();
        } else {
            super.botoAccionat(textBoto);
        }
    }

    private String Input_Similitud(String producteSeleccionat) {
        Boolean numValid = false;
        String result = null;
        while (!numValid) {
            double ret = -1;
            String message = "Quina sera la  similtud del producte " + nom_prod +
                    " amb el producte " + producteSeleccionat + "\n" +
                    "**RECORDA: 0 < Similitud < 100. HA DE SER UN DECIMAL**";
            result = JOptionPane.showInputDialog(frameVista, message, "Afegir Similitud", JOptionPane.QUESTION_MESSAGE);
            if (result != null && !result.isEmpty()) {
                try {
                    ret = Double.parseDouble(result);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frameVista,
                            "Entrada no valida\n Si us plau, introdueix un numero del 1 al 100",
                            "Error Input",
                            JOptionPane.ERROR_MESSAGE);

                }
            } else if (result == null) {
                return null;
            }

            if (ret > 0 && ret <= 100) {
                numValid = true;
            } else {
                JOptionPane.showMessageDialog(frameVista,
                        "Si us plau, introdueix un numero del 1 al 100",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;

    }

    private void EditarSimi(String producteSeleccionat) {
        String result = Input_Similitud(producteSeleccionat);
        if (result != null) {
            controlVista.editarSimilitud(nom_prod, producteSeleccionat, result);
            actualitzarComponents();
        }
    }

    private void Eliminar() {
        int result = JOptionPane.showConfirmDialog(frameVista,
                        "Estas segur que vols eliminar el producte "+nom_prod,
                        "Eliminar Producte", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            controlVista.eliminarProducte(nom_prod);
            frameVista.dispose();
        }
    }

    private void canviaNom() {
        String nou_nom = afegirNom();
        if (nou_nom != null ) {
            controlVista.canviarNom(nom_prod, nou_nom);
            nom_prod = nou_nom;
        }
    }

    private String getNomUsuari() {
        String message = "Afegeix el nom del producte que vols afegir";
        String result = JOptionPane.showInputDialog(frameVista, message, "Afegir Nom", JOptionPane.QUESTION_MESSAGE);
        if (result == null) {
            result = textCancelat;
        } else if (result.isEmpty()) {
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un nombre per el producte",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }

    private String afegirNom() {
        String nom = null;
        boolean te_nom = false;
        while (!te_nom) {
            nom = getNomUsuari();
            if (nom != null) {
                if (nom != textCancelat) {
                    if (!controlVista.findProd(nom)) {
                        te_nom = true;
                    } else {
                        JOptionPane.showMessageDialog(frameVista,
                                "Aquest nom ja esta sent utilitzat per un altre producte",
                                "Error Input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    return null;
                }
            }
        }
        return nom;
    }
}
