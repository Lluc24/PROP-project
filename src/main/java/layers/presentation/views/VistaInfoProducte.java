package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Classe VistaInfoProducte
 *   Aquesta classe te herencia de la VistaControladors
 *   Utilitza el controlador 'CtrlVistaCatalegAmbRestriccions' per interactuar amb la lògica del sistema.
 *   <p><b>Informació:</b></p>
 *   Aquesta vista mostra tota la informacio sobre un producte
 *   <ul>
 *         <li><b>Editar Similitud:</b> Permet editar la similitud del produte de la vista amb el seleccionat</li>
 *         <li><b>Canviar Nom:</b> Permet canviar el nom del producte de la vista</li>
 *         <li><b>Eliminar producte</b> Permet eliminar el producte de la vista</li>
 *     </ul>
 *  @see VistaControladors
 *  @see CtrlVistaCatalegAmbRestriccions
 *  @author Alejandro Lorenzo Navarro
 *
 */
public class VistaInfoProducte extends VistaControladors {
    /**
     * Instancia del controlador de cataleg amb restriccions
     */
    private CtrlVistaCatalegAmbRestriccions controlVista;

    /**
     * String que conte el nom del producte que es mostra
     */
    private String nom_prod;

    /**
     * Boolean que determina si es la primera vegada en executar la vista
     */
    private Boolean primeraVegada = true;

    /**
     * Label per mostrar el nom del producte
     */
    private JLabel labelNom_prod;
    /**
     * Boto Canviar Nom, permet canviar el nom del producte
     */
    private Boto BotoCanviarNom;

    /**
     * Text del boto Canviar Nom
     */
    private String textBotoCanviarNom = "ELIMINAR PRODUCTE";

    /**
     * String d'utilitat per determinar si una entrada de valors ha sigut cancelada
     */
    private String textCancelat = "CANCELADO";

    /**
     * Constructura de la clase
     * @param cps Instancia del controlador de restriccions
     */
    public VistaInfoProducte(CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    /**
     * Funcio que serveix per executar la vista, si es el primer cop que es fa s'inicialicien tots els components, si no s'actualitzan els components.
     */
    public void executar(String nom) {
        if (primeraVegada) {
            this.nom_prod = nom;
            titolFrame = "Vista Info producte";
            ajuda = "Estas a la vista de informacio del producte "+nom_prod+" aqui pots" +
                    "consultar les seves similituds amb la resta de productes" +
                    ", editar alguna d'aquestes similituds i eliminar el producte\n" +
                    "Eliminar Producte: Elimina aquest producte\n" +
                    "Canviar Nom: Et permet canviar el nom del producte" +
                    "Editar Similitud: Et permet editar la similitud amb el producte seleccionat del comboBox\n" +
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
        //Label nom producte

        super.inicialitzarComponents();

        labelNom_prod = new JLabel("PRODUCTE: "+nom_prod);
        labelNom_prod.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(labelNom_prod);
        add(Box.createRigidArea(new Dimension(0,10)));

        //Boto editar
        textBotoMostrar = "Editar Similitud";
        botoMostrar.setText(textBotoMostrar);

        //Boto eliminar
        textBotoAfegir = "Canviar Nom";
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

    /**
     * S'acutalicen tots els components de la vista
     */
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

    /**
     * Metode que tracta les accions dels botons en ser premuts
     * @param textBoto Text que indica quin boto ha sigut premut, es igual al text d'aquest boto
     */
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
        } else if (textBoto.equals(textBotoTornar)) {
            controlVista.canviaVista("PrincipalCataleg");
        } else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Metode que permet obtenir la nova similitud amb el producte seleccionat
     * @param producteSeleccionat Producte seleccionat del combobox
     * @return String amb valor de la nova similitud
     */
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

    /**
     * Metode que permet editar la similitud d'un producte
     * @param producteSeleccionat
     */
    private void EditarSimi(String producteSeleccionat) {
        String result = Input_Similitud(producteSeleccionat);
        if (result != null) {
            controlVista.editarSimilitud(nom_prod, producteSeleccionat, result);
            actualitzarComponents();
        }
    }

    /**
     * Metodo que permet eliminar el producte del sistema
     */
    private void Eliminar() {
        int result = JOptionPane.showConfirmDialog(frameVista,
                        "Estas segur que vols eliminar el producte "+nom_prod,
                        "Eliminar Producte", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            controlVista.eliminarProducte(nom_prod);
            controlVista.canviaVista("PrincipalCataleg");
        }
    }

    /**
     * Metode que permet canviar el nom al producte
     */
    private void canviaNom() {
        String nou_nom = afegirNom();
        if (nou_nom != null) {
            if (!controlVista.findProd(nou_nom)) {
                controlVista.canviarNom(nom_prod, nou_nom);
                nom_prod = nou_nom;
            } else {
                JOptionPane.showMessageDialog(frameVista,
                        "Aquest nom ja esta sent utilitzat per un altre producte",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
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
