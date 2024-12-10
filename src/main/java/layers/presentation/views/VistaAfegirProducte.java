package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaAfegirProducte extends VistaControladors {
    private CtrlVistaCatalegAmbRestriccions controlVista;

    private Boolean te_nom = false;
    private Boolean Simis_DONE = false;

    private ArrayList<String> similituds;
    private ArrayList<String> restriccions;
    private String[] productes;
    private int index_restriccions = 0;
    private String nom_prod = "INSEREIX NOM";

    private JLabel labelNomProd;

    private String textBotoFinalitzar = "Afegir Canvis";
    private Boto BotoFinalitzar;

    private String textBotoCanviNom = "Canvia Nom";
    private Boto BotoCanviNom;

    public VistaAfegirProducte (CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    public void executar() {
        primeraVegada = false;
        titolFrame = "Afegir Producte";
        ajuda = "Estas a la vista per afegir un nou producte\n" +
                "Label: Aqui veuras el nom que li has donat al producte, recorda no pot ser el nom d'un producte existent\n" +
                "ComboBox: Aqui veuras tots el productes dins de cataleg, pot seleccionar un per afegir una restriccio\n" +
                "Afegir Restriccio: Despres de seleccionar un producte, clica aquest boto per afegir una restriccio\n" +
                "Afegir Similituds: Començaras un proces per afegir una similitud, per cada producte indicat. Un cop donades, amb es podra editar la similitud amb el producte indicat\n" +
                "Canvi Nom: Permet cambiar el nom del producte\n" +
                "Afegir producte: Afegiras el producte de manera definitiva, no podras fer us d'aquest boto fins que el producte tingui nom i totes les similituds estiguin donades\n";

        super.executar();

    }

    @Override
    public void inicialitzarComponents() {
        super.inicialitzarComponents();

        te_nom = false;
        Simis_DONE = false;

        //Inicialitzar vectors
        int mida_cataleg = controlVista.getNumProd();
        productes = controlVista.getProductes();
        similituds = new ArrayList<>();
        restriccions = new ArrayList<>();

        labelNomProd = new JLabel(nom_prod);
        labelNomProd.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(labelNomProd);
        add(Box.createRigidArea(new Dimension(0,10)));
        //Capuramos nombre del producto
        afegirNom();

        if (mida_cataleg == 0) {
            Simis_DONE = true;
            botoAccionat(textBotoFinalitzar);
        }

        textEtiquetaTriar = "Quin producte vols seleccionar";
        etiquetaTriar.setText(textEtiquetaTriar);

        //Setear botones de vista controlador
        textBotoAfegir = "Afegir Restriccions";
        botoAfegir.setText(textBotoAfegir);

        if (!Simis_DONE) textBotoMostrar = "Afegir Similituds";
        else textBotoMostrar = "Editar Similitud";
        botoMostrar.setText(textBotoMostrar);

        //Setear boto finalitzar
        BotoFinalitzar = new Boto(textBotoFinalitzar);
        BotoFinalitzar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(BotoFinalitzar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        //Boto Canviar nom
        BotoCanviNom = new Boto(textBotoCanviNom);
        BotoCanviNom.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(BotoCanviNom);
        add(Box.createRigidArea(new Dimension(0,10)));

        opcions.removeAllItems();
        for (String item : productes) {
            opcions.addItem(item);
        }
    }

    @Override
    public void botoAccionat (String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            String seleccionat = (String) opcions.getSelectedItem();
            if (seleccionat != null && !seleccionat.isEmpty()) {
                AfegirRestriccio(seleccionat);
            }
        } else if (textBoto.equals(textBotoMostrar)) {
            if (Simis_DONE) {
                String seleccionat = (String) opcions.getSelectedItem();
                if (seleccionat != null && !seleccionat.isEmpty() ) {
                    EditarSimilitud(seleccionat);
                }
            } else {
                AfegirSimilituds();
            }
        } else if (textBoto.equals(textBotoFinalitzar)) {
            finalizar();
        } else if (textBoto.equals(textBotoCanviNom)) {
            canviaNom();
        } else if (textBoto.equals(textBotoTornar)) {
            AvisSortir();
            super.botoAccionat(textBoto);
        } else if (textBoto.equals(textItemSortir)) {
            AvisSortir();
            super.botoAccionat(textBoto);
        } else {
            super.botoAccionat(textBoto);
        }
    }

    private void AfegirSimilituds() {
        for (int i = 0; i < productes.length; ++i) {
            boolean ok = false;
            String result = getSimilitud(productes[i]);
            similituds.add(i, result);
        }
        Simis_DONE = true;
        textBotoMostrar = "Editar Similitud";
        botoMostrar.setText(textBotoMostrar);
    }

    private void EditarSimilitud(String prodSeleccionat) {
        int index = 0;
        boolean trobat = false;
        while(!trobat && index < productes.length) {
            if (productes[index].equals(prodSeleccionat)) trobat = true;
            else ++index;
        }

        //Canviem la similitud
        if (trobat) {
            String result = getSimilitud(productes[index]);
            similituds.set(index,result);
        }
    }

    private void AfegirRestriccio(String prodSeleccionat) {
        restriccions.add(prodSeleccionat);
        ++index_restriccions;
    }

    private void afegirNom() {
        String nom = null;
        while (!te_nom) {
            nom = getNomUsuari();
            if (nom != null) {
                if (!controlVista.findProd(nom)) {
                    te_nom = true;
                } else {
                    JOptionPane.showMessageDialog(frameVista,
                            "Aquest nom ja esta sent utilitzat per un altre producte",
                            "Error Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        nom_prod = nom;
        labelNomProd.setText(nom_prod);
    }

    private void canviaNom() {
        String nom = getNomUsuari();
        if (nom != null) {
            if (!controlVista.findProd(nom)) {
                te_nom = true;
            } else {
                JOptionPane.showMessageDialog(frameVista,
                        "Aquest nom ja esta sent utilitzat per un altre producte",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        nom_prod = nom;
        labelNomProd.setText(nom);
    }

    private void finalizar() {
        if (!te_nom || !Simis_DONE) {
            String falta_nom = "";
            String falta_Simis = "";
            if (!te_nom) {
                falta_nom = "No has afegit un nom al producte";
            }
            if (!Simis_DONE) {
                falta_Simis = "No has afegit totes les similituds";
            }
            String message = "No has afegit totes les informacio del producte\n" + falta_nom + "\n" + falta_Simis;
            JOptionPane.showMessageDialog(frameVista, message, "Error Input", JOptionPane.ERROR_MESSAGE);
        } else if (controlVista.getNumProd() == 0) {
            String[] buit ={};
            controlVista.afegirProducte(nom_prod,buit, buit);
            String message = "El producte ja s'afegit\n Ja que es el primer producte no necesitem mes informacio";
            JOptionPane.showMessageDialog(frameVista, message, "Producte Afegit", JOptionPane.INFORMATION_MESSAGE);
            frameVista.dispose();
        } else {
            String[] restriccionsArray = new String[restriccions.size()];
            for (int i = 0; i < restriccions.size(); ++i){
                restriccionsArray[i] = restriccions.get(i);
            }
            String[] similitudsArray = new String[similituds.size()];
            for (int i = 0; i < similituds.size(); ++i){
                similitudsArray[i] = similituds.get(i);
            }
            controlVista.afegirProducte(nom_prod, similitudsArray, restriccionsArray); //Retorna String, String[], String[]
            String message = "El producte ja s'afegit";
            JOptionPane.showMessageDialog(frameVista, message, "Producte Afegit", JOptionPane.INFORMATION_MESSAGE);
            frameVista.dispose();
        }
    }

    private void AvisSortir() {
        if (!te_nom || !Simis_DONE) {
            JOptionPane.showMessageDialog(frameVista,
                    "CAUTION: No has afegit el producte si surts perdras tota la informacio del producte ",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private String getNomUsuari() {
        String message = "Afegeix el nom del producte que vols afegir";
        String result = JOptionPane.showInputDialog(frameVista, message, "Afegir Nom", JOptionPane.QUESTION_MESSAGE);
        if (result == null || result.isEmpty()) {
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un nombre per el producte",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }

    private String getSimilitud(String producteSeleccionat) {
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


}
