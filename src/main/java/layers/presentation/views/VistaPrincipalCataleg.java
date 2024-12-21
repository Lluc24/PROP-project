package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipalCataleg extends VistaControladors {
    private CtrlVistaCatalegAmbRestriccions controlVista;
    protected Boto botoMenuRestriccion;
    protected String textBotoMenuRestriccions = "Menu Restriccions";

    private Boolean primeraVegada = true;

    private String textCancelat = "CANCELAT";
    
    public VistaPrincipalCataleg(CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    public void executar() {
        if (primeraVegada) {
            titolFrame = "Vista Principal del Cataleg";
            ajuda = "Estas a la vista principal del cataleg. Des d'aquesta vista " +
                    "podras veure els productes del cataleg, seleccionar-los, o anar el menu per afegir-ne mes" +
                    "com tambe anar a menu de restriccions\n " +
                    "Afegir Productes: Aniras a la vista per afegir un producte\n" +
                    "Consultar Producte: Aniras a la vista per veure la informacio del producte seleccionat\n" +
                    "Consultar Restriccions: Aniras al menu de restriccions, per consultarlas i editar les\n" +
                    "ComboBox: Et mostra tots el productes del cataleg, pot seleccionar un\n" +
                    "Enrere: Et permet anar a l'anterior vista\n" +
                    "Sortir: Finalitzar l'aplicacio\n";
            primeraVegada = false;
            super.executar();
        } else {
            actualitzarComponents();
            frameVista.setVisible(true);
        }

    }

    @Override
    public void sortirSistema() {
        controlVista.sortirSistema();
    }

    @Override
    public void inicialitzarComponents() {
        super.inicialitzarComponents();
        //Etiqueta de triar
        textEtiquetaTriar = "Quina funcionalitat del cataleg vols realitzar";
        etiquetaTriar.setText(textEtiquetaTriar);

        //Boto Afegir productes
        textBotoAfegir = "Afegir Producte";
        botoAfegir.setText(textBotoAfegir);

        //Boto mostrar Producte
        textBotoMostrar = "Mostrar Productes";
        botoMostrar.setText(textBotoMostrar);

        //Boto Consultar Restriccions
        botoMenuRestriccion = new Boto(textBotoMenuRestriccions);
        botoMenuRestriccion.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoMenuRestriccion);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(Box.createVerticalGlue());

        //Combo box
        String[] productes = controlVista.getProductes();
        opcions.removeAllItems();
        for (String item : productes) {
            opcions.addItem(item);
        }

    }

    private void actualitzarComponents() {
        String[] productes = controlVista.getProductes();
        opcions.removeAllItems();
        for (String item : productes) {
            opcions.addItem(item);
        }
    }

    @Override
    public void botoAccionat (String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            int mida = controlVista.getNumProd();
            if (mida == 0) {
                //Pedir nombre
                String nom = afegirNom();
                if (nom != null) {
                    String[] buit = {};
                    //AñadirProducto
                    controlVista.afegirProducte(nom, buit, buit);
                    String message = "El producte ja s'ha afegit\nAl ser el primer producte no necesitem mes informacio";
                    JOptionPane.showMessageDialog(frameVista, message, "Producte Afegit", JOptionPane.INFORMATION_MESSAGE);
                    actualitzarComponents();
                }
            } else {
                canviVistaAfegirProducte();
                actualitzarComponents();
            }
        } else if (textBoto.equals(textBotoMostrar)) {
            String prodSeleccionat = (String) opcions.getSelectedItem();
            if (prodSeleccionat == null || prodSeleccionat.isEmpty()) {
                JOptionPane.showMessageDialog(frameVista,
                        "No se ha seleccionat cap producte del ComboBox",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                canviaVistaInfoProd(prodSeleccionat);
                actualitzarComponents();
            }
        } else if (textBoto.equals(textBotoMenuRestriccions)) {
            canviVistaConsultarRest();
            actualitzarComponents();
        } else if (textBoto.equals(textBotoTornar)) {
            controlVista.canviaVista("VistaPrincipal");
        } else {
            super.botoAccionat(textBoto);
        }
    }

    private void canviVistaAfegirProducte() {
        controlVista.canviaVista("AfegirProductes");
    }

    public void canviVistaConsultarRest() {
        controlVista.canviaVista("ConsultarRestriccions");
    }

    public void canviaVistaInfoProd(String prod) {
        controlVista.canviarVista("InfoProducte", prod);
    }

    private String getNomUsuari() {
        String message = "Afegeix el nom del producte que vols afegir\nCAUTION: Nomes ha de incloure lletre o numeros";
        String result = JOptionPane.showInputDialog(frameVista, message, "Afegir Nom", JOptionPane.QUESTION_MESSAGE);
        if (result == null) {
            result = textCancelat;
        } else if (result.isEmpty()) {
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un nombre per el producte",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        } else if (!result.matches("[a-zA-Z0-9]+")){
            JOptionPane.showMessageDialog(frameVista,
                    "El nom nomes pot estar format per lletres o numeros",
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

