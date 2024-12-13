package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import java.awt.*;

public class VistaConsultarRest extends VistaControladors {
    private CtrlVistaCatalegAmbRestriccions ctrl;
    protected Boto botoEliminarRest;
    protected String textBotoEliminarRest = "Eliminar Per Nom";

    Boolean primeraVegada = true;

    public VistaConsultarRest(CtrlVistaCatalegAmbRestriccions controlador) {
        this.ctrl = controlador;
    }

    public void executar() {
        titolFrame = "Gestionar Restriccions";
        ajuda = "Estas a la vista de gestio de restriccions. Des d'aquesta vista pots gestionar les restriccions disponibles " +
                "utilitzant els botons corresponents.\n" +
                "Afegir Restriccio: Permet afegir una nova restriccio introduint dos productes.\n" +
                "Eliminar Seleccionada: Elimina la restriccio actualment seleccionada del llistat.\n" +
                "Eliminar Per Nom: Permet eliminar una restriccio indicant manualment els noms dels productes afectats.\n" +
                "Sortir: Finalitza l'aplicacio.";

        primeraVegada = false;
        super.executar();

    }

    @Override
    public void inicialitzarComponents() {
        super.inicialitzarComponents();

        textEtiquetaTriar = "Afegeix una restriccio o elimina la seleccionada";
        etiquetaTriar.setText(textEtiquetaTriar);

        //botó eliminar seleccionada
        textBotoMostrar = "Eliminar Seleccionada";
        botoMostrar.setText(textBotoMostrar);


        //botó afegir
        textBotoAfegir = "Afegir Restriccio";
        botoAfegir.setText(textBotoAfegir);

        //botó eliminar per nom
        botoEliminarRest = new Boto(textBotoEliminarRest);
        botoEliminarRest.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoEliminarRest);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(Box.createVerticalGlue());

        //Selecció
        String[] restriccions = ctrl.getAllRestriccions();
        opcions.removeAllItems();
        for (String item : restriccions) {
            opcions.addItem(item);
        }

    }

    @Override
    public void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            afegirRestriccio();
        } else if (textBoto.equals(textBotoMostrar)) {
            String seleccio = (String) opcions.getSelectedItem();
            if (seleccio == null || seleccio.isEmpty()) {
                JOptionPane.showMessageDialog(frameVista,
                        "No s'ha seleccionat cap restriccio",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                eliminaRestr(seleccio);
            }
        } else if (textBoto.equals(textBotoEliminarRest)) {
            eliminar_restr_nom();
        } else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Elimina una restricció seleccionada després d'una confirmació per part de l'usuari.
     *
     * @param seleccio String que representa la restricció a eliminar.
     */
    private void eliminaRestr(String seleccio) {

        //confirmació
        int result = JOptionPane.showConfirmDialog(frameVista,
                "Vols eliminar la restriccio?",
                "Confirmacio eliminar",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            ctrl.eliminarRestriccio(seleccio);
        }

    }

    /**
     * Afegeix una nova restricció entre dos productes introduïts per l'usuari.
     * Els productes es validen abans d'afegir la restricció.
     */
    private void afegirRestriccio() {

        String prod1 = getNomProducte1();

        if (prod1 != null) {
            String prod2 = getNomProducte2();

            if (prod2 != null) ctrl.afegirRestriccio(prod1, prod2);
        }

    }

    /**
     * Demana a l'usuari que introdueixi el nom del primer producte i valida la seva existència.
     *
     * @return El nom del producte 1, o null si no és vàlid o no existeix.
     */
    private String getNomProducte1() {
        String message = "Introdueix el nom d'un dels productes";
        String result = JOptionPane.showInputDialog(frameVista, message, "Introduir nom", JOptionPane.QUESTION_MESSAGE);
        if (result == null || result.isEmpty()) {
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un nom",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!ctrl.valida_nom(result)) {
            JOptionPane.showMessageDialog(frameVista,
                    "El producte no existeix",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }

    /**
     * Demana a l'usuari que introdueixi el nom del segon producte i valida la seva existència.
     *
     * @return El nom del producte 2, o null si no és vàlid o no existeix.
     */
    private String getNomProducte2() {
        String message = "Introdueix el nom de l'altre producte";
        String result = JOptionPane.showInputDialog(frameVista, message, "Introduir nom", JOptionPane.QUESTION_MESSAGE);
        if (result == null || result.isEmpty()) {
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un nom",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (!ctrl.valida_nom(result)) {
            JOptionPane.showMessageDialog(frameVista,
                    "El producte no existeix",
                    "Error Input",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }

    /**
     * Elimina una restricció entre dos productes especificats per l'usuari.
     * Els noms dels productes són introduïts per l'usuari i validats abans de procedir a l'eliminació.
     */
    private void eliminar_restr_nom() {

        String prod1 = getNomProducte1();

        if (prod1 != null) {
            String prod2 = getNomProducte2();

            if (prod2 != null) {

                int result = JOptionPane.showConfirmDialog(frameVista,
                        "Vols eliminar la restriccio?",
                        "Confirmacio eliminar",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) ctrl.eliminarRestriccio(prod1, prod2);
            }
        }
    }

}
