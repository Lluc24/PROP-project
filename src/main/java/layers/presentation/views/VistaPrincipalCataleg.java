package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipalCataleg extends VistaControladors {
    private CtrlVistaCatalegAmbRestriccions controlVista;
    protected Boto botoMenuRestriccion;
    protected String textBotoMenuRestriccions = "Menu Restriccions";

    Boolean primeraVegada = true;
    
    public VistaPrincipalCataleg(CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    public void executar() {
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

    }

    @Override
    public void inicialitzarComponents() {
        super.inicialitzarComponents();
        //Etiqueta de triar
        textEtiquetaTriar = "Quina funcionalitat del cataleg vols realitzar";
        etiquetaTriar.setText(textEtiquetaTriar);

        //Boto Afegir productes
        textBotoAfegir = "Afegir Productes";
        botoAfegir.setText(textBotoAfegir);

        //Boto mostrar Producte
        textBotoMostrar = "Mostrar Producte";
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

    @Override
    public void botoAccionat (String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            canviVistaAfegirProducte();
        } else if (textBoto.equals(textBotoMostrar)) {
            String prodSeleccionat = (String) opcions.getSelectedItem();
            if (prodSeleccionat == null || prodSeleccionat.isEmpty()) {
                JOptionPane.showMessageDialog(frameVista,
                        "No se ha seleccionat cap producte del ComboBox",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                canviaVistaInfoProd(prodSeleccionat);
            }
        } else if (textBoto.equals(textBotoMenuRestriccions)) {
            canviVistaConsultarRest();
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

}

