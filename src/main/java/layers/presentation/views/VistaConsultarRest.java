package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class VistaConsultarRest extends VistaControladors {
    private CtrlVistaCatalegAmbRestriccions ctrl;

    protected Boto botoConsultarNom;
    protected String textBotoConsultRest = "Consultar Per Nom";

    protected String textEtiquetaInfo = "";
    protected JLabel etiquetaInfo;

    private ArrayList<String> Producte1;
    private ArrayList<String> Producte2;
    private ArrayList<String> ultOperacio;

    protected String textItemDesfer = "Desfer";
    protected Item menuItemDesfer;

    Boolean primeraVegada = true;

    public VistaConsultarRest(CtrlVistaCatalegAmbRestriccions controlador) {
        this.ctrl = controlador;
    }

    public void executar() {
        if(primeraVegada) {
            titolFrame = "Gestionar Restriccions";
            ajuda = "Estas a la vista de gestio de restriccions. Des d'aquesta vista pots gestionar les restriccions disponibles " +
                    "utilitzant els botons corresponents.\n" +
                    "Afegir Restriccio: Permet afegir una nova restriccio introduint dos productes.\n" +
                    "Eliminar Seleccionada: Elimina la restriccio actualment seleccionada del llistat.\n" +
                    "Eliminar Per Nom: Permet eliminar una restriccio indicant manualment els noms dels productes afectats.\n" +
                    "Sortir: Finalitza l'aplicacio.";

            primeraVegada = false;
            Producte1 = new ArrayList<>();
            Producte2 = new ArrayList<>();
            ultOperacio = new ArrayList<>();
            super.executar();
        } else {
            frameVista.setVisible(true);
            actualitzarComponents();
        }

    }

    @Override
    public void inicialitzarComponents() {

        super.inicialitzarComponents();

        textEtiquetaTriar = "Consulta o afegeix una restriccio o elimina la seleccionada";
        etiquetaTriar.setText(textEtiquetaTriar);

        //botó eliminar seleccionada
        textBotoMostrar = "Eliminar Seleccionada";
        botoMostrar.setText(textBotoMostrar);

        //botó afegir
        textBotoAfegir = "Afegir Restriccio";
        botoAfegir.setText(textBotoAfegir);

        //botó consultar per nom
        botoConsultarNom = new Boto(textBotoConsultRest);
        botoConsultarNom.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoConsultarNom);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(Box.createVerticalGlue());

        if (ultOperacio.isEmpty())  {
            textEtiquetaInfo = "";
        }
        else {
            textEtiquetaInfo = "Ultima operacio enregistrada: " + ultOperacio.getLast() + " restriccio entre " + Producte1.getLast() + " i " + Producte2.getLast();

            //item desfer
            menuItemDesfer = new Item(textItemDesfer);
            menuFitxer.add(menuItemDesfer);
        }
        etiquetaInfo = new JLabel(textEtiquetaInfo);
        etiquetaInfo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaInfo);

        //Selecció
        String[] restriccions = ctrl.getAllRestriccions();
        opcions.removeAllItems();
        for (String item : restriccions) {
            opcions.addItem(item);
        }

    }

    @Override
    public void itemAccionat(String textItem) {
        if (textItem.equals(textItemDesfer)) {
            desfer();
        }
        else {
            super.itemAccionat(textItem);
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
                        "No s'ha seleccionat cap restriccio.",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                eliminaRestr(seleccio);
            }
        } else if (textBoto.equals(textBotoConsultRest)) {
            eliminar_restr_nom();
        } else if (textBoto.equals(textBotoTornar)) {
            ctrl.canviaVista("PrincipalCataleg");
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

        String[] prods = decodificar_producte(seleccio);

        //confirmació
        int result = JOptionPane.showConfirmDialog(frameVista,
                "Vols eliminar la restriccio entre " + prods[0] + " i " + prods[1] + "?",
                "Confirmacio eliminar",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            ctrl.eliminarRestriccio(prods[0], prods[1]);
            ultOperacio.add("Eliminar");
            Producte1.add(prods[0]);
            Producte2.add(prods[1]);
            actualitzarComponents();
        }

    }

    /**
     * Afegeix una nova restricció entre dos productes introduïts per l'usuari.
     * Els productes es validen abans d'afegir la restricció.
     */
    private void afegirRestriccio() {

        if (ctrl.getNumProd() < 2) {
            JOptionPane.showMessageDialog(frameVista,
                    "Hi ha menys de dos productes al cataleg.",
                    "Productes insuficients",
                    JOptionPane.ERROR_MESSAGE);
        }

        else {

            String prod1 = getNomProducte(null, true);

            if (prod1 != null) {
                String prod2 = getNomProducte(prod1, true);

                if (prod2 != null) {

                    int ok = JOptionPane.showConfirmDialog(frameVista,
                            "Vols afegir la restriccio entre " + prod1 + " i " + prod2 + "?",
                            "Confirmacio afegir",
                            JOptionPane.YES_NO_OPTION);
                    if (ok == JOptionPane.YES_OPTION) {
                        ctrl.afegirRestriccio(prod1, prod2);
                        ultOperacio.add("Afegir");
                        Producte1.add(prod1);
                        Producte2.add(prod2);
                        actualitzarComponents();
                    }

                }
            }
        }

    }

    /**
     * Demana a l'usuari que introdueixi el nom del producte i valida la seva existència.
     *
     * @param nomProd null si és el primer cop que es demana, 1 si és el segon.
     * @return El nom del producte, o null si no és vàlid o no existeix.
     */
    private String getNomProducte(String nomProd, boolean afegir) {
        String message;
        JComboBox<String> comboBox2;
        JTextField textField = new JTextField(15); // Camp de text

        if (nomProd == null) {
            message = "Introdueix el nom d'un dels productes";
            comboBox2 = new JComboBox<>(ctrl.getProductes());
        } else {

            String[] prods;
            if (afegir) {
                prods = ctrl.getProdNoRestrConsec(nomProd);
                message = "Introdueix el nom de l'altre producte";
                if (prods.length == 0) {
                    JOptionPane.showMessageDialog(
                            frameVista,
                            "Aquest producte ja te restriccions amb tots els altres",
                            "No elegible",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return null;
                }
            }
            else {
                prods = ctrl.getProdRestrConsec(nomProd);
                message = "Es mostren els productes restringits";
                if (prods.length == 0) {
                    JOptionPane.showMessageDialog(
                            frameVista,
                            "Aquest producte no te restriccions amb cap altre",
                            "No elegible",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return null;
                }
            }

            comboBox2 = new JComboBox<>(prods);
        }

        //sincronitzar el JTextField amb el JComboBox
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) comboBox2.getSelectedItem();
                textField.setText(selectedItem);
            }
        });

        //ajustar l'alçada del JTextField
        Dimension textFieldSize = new Dimension(250, 30); // Amplada i alçada personalitzada
        textField.setPreferredSize(textFieldSize);
        textField.setMaximumSize(textFieldSize);
        textField.setMinimumSize(textFieldSize);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(message);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox2.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(comboBox2);

        //mostrar el JOptionPane
        String titol = "Seleccionar Producte";
        String[] options = {"Seleccionar", "Sortir"};
        if (nomProd != null) {
            if (afegir) {
                titol = "Afegir Restriccio amb " + nomProd;
                options[0] = "Afegir";
            } else {
                options[0] = "Eliminar";
                titol = "Restriccions amb " + nomProd;
            }
        }

        int ok = JOptionPane.showOptionDialog(
                frameVista,
                panel,
                titol,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (ok == 0) {
            String result = textField.getText().trim();
            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(frameVista,
                        "No s'ha introduit cap nom",
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

            System.out.println(result);
            return result;
        }

        return null;
    }

    /**
     * Elimina una restricció entre dos productes especificats per l'usuari.
     * Els noms dels productes són introduïts per l'usuari i validats abans de procedir a l'eliminació.
     */
    private void eliminar_restr_nom() {

        if (ctrl.getAllRestriccions().length == 0) {
            JOptionPane.showMessageDialog(frameVista,
                    "No hi ha restriccions al cataleg",
                    "Restriccions insuficients",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {

            String prod1 = getNomProducte(null, false);

            if (prod1 != null) {
                String prod2 = getNomProducte(prod1, false);

                if (prod2 != null) {

                    int result = JOptionPane.showConfirmDialog(frameVista,
                            "Vols eliminar la restriccio entre " + prod1 + " i " + prod2 + "?",
                            "Confirmacio eliminar",
                            JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        ctrl.eliminarRestriccio(prod1, prod2);
                        ultOperacio.add("Eliminar");
                        Producte1.add(prod1);
                        Producte2.add(prod2);
                        actualitzarComponents();
                    }
                }

            }
        }
    }

    /**
     * Torna a carregar els elements de la vista.
     */
    private void actualitzarComponents() {

        String[] restriccions = ctrl.getAllRestriccions();
        opcions.removeAllItems();
        for (String item : restriccions) {
            opcions.addItem(item);
        }

        if (ultOperacio.isEmpty()) {
            menuFitxer.remove(menuItemDesfer);
            textEtiquetaInfo = "";
            etiquetaInfo.setText(textEtiquetaInfo);
        }
        else {
            if (!menuTeItem(menuFitxer, textItemDesfer)) {
                menuItemDesfer = new Item(textItemDesfer);
                menuFitxer.add(menuItemDesfer);
            }
            textEtiquetaInfo = "Ultima operacio enregistrada: " + ultOperacio.getLast() + " restriccio entre " + Producte1.getLast() + " i " + Producte2.getLast();
            etiquetaInfo.setText(textEtiquetaInfo);
        }

    }

    /**
     * Decodifica una cadena de text que conté dos noms de productes separats per un punt i coma.
     *
     * @param str Cadena amb el format "producte1 ; producte2".
     * @return Un array amb els dos noms dels productes.
     */
    private String[] decodificar_producte(String str) {

        return str.split(" ; ");
    }

    /**
     * Tanca l'aplicació.
     */
    @Override
    public void sortirSistema() {
        ctrl.sortirSistema();
    }

    /**
     * Desfà l'última operació, sigui eliminació o afegit.
     */
    private void desfer() {
        if (!ultOperacio.isEmpty()) {
            if (Objects.equals(ultOperacio.getLast(), "Afegir")) {
                ctrl.eliminarRestriccio(Producte1.getLast(), Producte2.getLast());

            } else if (Objects.equals(ultOperacio.getLast(), "Eliminar")) {
                ctrl.afegirRestriccio(Producte1.getLast(), Producte2.getLast());
            }
            ultOperacio.removeLast();
            Producte1.removeLast();
            Producte2.removeLast();
            actualitzarComponents();
        }

    }

    /**
     * Comprova si l'item està al menu.
     *
     * @param menu menu on es cerca.
     * @param nomItem nom del item.
     * @return True si està, false en cas contrari.
     */
    private boolean menuTeItem(Menu menu, String nomItem) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            MenuItem item = menu.getItem(i);
            if (item != null && nomItem.equals(item.getLabel())) {
                return true;
            }
        }
        return false;
    }

}
