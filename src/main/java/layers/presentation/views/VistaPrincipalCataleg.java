package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import java.awt.*;
/**
 * Classe VistaPrincipalCataleg
 *   Aquesta classe te herencia de la VistaControladors
 *   Utilitza el controlador 'CtrlVistaCatalegAmbRestriccions' per interactuar amb la lògica del sistema.
 *   <p><b>Informació:</b></p>
 *   Aquesta vista es un menu per entrar a la resta de vistes de gestio de cataleg
 *   <ul>
 *         <li><b>Mostrar Producte:</b> Perment anar a la vista per veure la informacio del producte seleccionat</li>
 *         <li><b>Afegir Producte:</b> Permet anar a la vista per afegir un producte nou</li>
 *         <li><b>Menu Restriccions</b> Permet anar a la vista del menu de restriccions</li>
 *     </ul>
 *  @see VistaControladors
 *  @see CtrlVistaCatalegAmbRestriccions
 *  @author Alejandro Lorenzo Navarro
 *
 */
public class VistaPrincipalCataleg extends VistaControladors {
    /**
     * Instancia del controlador de cataleg amb restriccions
     */
    private CtrlVistaCatalegAmbRestriccions controlVista;
    /**
     * Boto de Menu de restriccions, permet anar al menu de restriccions
     */
    protected Boto botoMenuRestriccion;
    /**
     * Text del boto de menu de restricicons
     */
    protected String textBotoMenuRestriccions = "Menu Restriccions";
    /**
     * Boolean que determina si es la primera vegada en executar la vista
     */
    private Boolean primeraVegada = true;
    /**
     * String d'utilitat per determinar si una entrada de valors ha sigut cancelada
     */
    private String textCancelat = "CANCELAT";

    /**
     * Constructura de la clase
     * @param cps Instancia del controlador de restriccions
     */
    public VistaPrincipalCataleg(CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    /**
     * Funcio que serveix per executar la vista, si es el primer cop que es fa s'inicialicien tots els components, si no s'actualitzan els components.
     */
    public void executar() {
        if (primeraVegada) {
            titolFrame = "Vista Principal del Cataleg";
            ajuda = "Estas a la vista principal del cataleg. Des d'aquesta vista " +
                    "podras veure els productes del cataleg, seleccionar-los, o anar el menu per afegir-ne mes" +
                    "com tambe anar a menu de restriccions\n " +
                    "Afegir Productes: Aniras a la vista per afegir un producte\n" +
                    "Mostrar Producte: Aniras a la vista per veure la informacio del producte seleccionat\n" +
                    "Menu Restriccions: Aniras al menu de restriccions, per consultarlas i editar les\n" +
                    "ComboBox: Et mostra tots el productes del cataleg, pot seleccionar un\n" +
                    "Tornar: Et permet anar a l'anterior vista\n" +
                    "Sortir: Finalitzar l'aplicacio\n";
            primeraVegada = false;
            super.executar();
        } else {
            actualitzarComponents();
            frameVista.setVisible(true);
        }

    }

    /**
     * Funcio per sortir del sistema
     */
    @Override
    public void sortirSistema() {
        controlVista.sortirSistema();
    }

    /**
     * Funcio heredada de la clase VistaGenerica, s'inicialicen tots el components de la vista
     */
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

    /**
     * S'acutalicen tots els components de la vista
     */
    private void actualitzarComponents() {
        String[] productes = controlVista.getProductes();
        opcions.removeAllItems();
        for (String item : productes) {
            opcions.addItem(item);
        }
    }

    /**
     * Metode que tracta les accions dels botons en ser premuts
     * @param textBoto Text que indica quin boto ha sigut premut, es igual al text d'aquest boto
     */
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

    /**
     * Metode que canvia a la vista d'Afegir producte, si s'ha premut el boto Afegir Producte
     */
    private void canviVistaAfegirProducte() {
        controlVista.canviaVista("AfegirProductes");
    }

    /**
     * Metode que canvia a la vista de Menu restriccions, si s'ha premut el boto Menu Restriccions
     */
    public void canviVistaConsultarRest() {
        controlVista.canviaVista("ConsultarRestriccions");
    }

    /**
     * Metode que canvia a la vista d'Afegir producte, si s'ha premut el boto Afegir Producte
     */
    public void canviaVistaInfoProd(String prod) {
        controlVista.canviarVista("InfoProducte", prod);
    }

    /**
     * Funcio de gestio de valors, per obtenir un nom per el producte
     * @return Retorna un string valid que compleixi el format
     */
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

    /**
     * Funcio que obte un nom valid dins del cataleg per el producte
     * @return Un string que te el valor del nom de producte a afegir
     */
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

