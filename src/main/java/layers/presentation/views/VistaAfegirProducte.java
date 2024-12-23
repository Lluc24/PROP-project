package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe VistaAfegirProducte
 *
 *   Aquesta classe te herencia de la VistaControladors
 *   Utilitza el controlador 'CtrlVistaCatalegAmbRestriccions' per interactuar amb la lògica del sistema.
 *   <p><b>Informació:</b></p>
 *   Aquesta vista permet l'usuari crear un producte donant les seves similtuds i restriccions
 *   <ul>
 *         <li><b>Afegir Similituds/Editar Similituds:</b> Permet afegir totes les similituds, i un cop donades todes editar-les</li>
 *         <li><b>Afegir Restricció:</b> Permet afegir una restriccio entre el nou producte i el seleccionat</li>
 *         <li><b>Canviar Nom:</b> Permet canviar el nom del producte</li>
 *         <li><b>Guardar:</b> Boto per afegir el producte i la seva informació al sistema</li>
 *     </ul>
 *  @see VistaControladors
 *  @see CtrlVistaCatalegAmbRestriccions
 *  @author Alejandro Lorenzo Navarro
 *
 */
public class VistaAfegirProducte extends VistaControladors {
    /**
     * Instancia del controlador de cataleg amb restriccions
     */
    private CtrlVistaCatalegAmbRestriccions controlVista;

    /**
     * Boolean que determina si el producte te nom
     */
    private Boolean te_nom = false;

    /**
     * Boolean que determina si el producte ja te TOTES les similituds
     */
    private Boolean Simis_DONE = false;

    /**
     * Array de similituds del productes, conte nombre representats per strings
     */
    private ArrayList<String> similituds;

    /**
     * Array de restriccions del producte, conte el nom del altres productes amb els quals té restricció
     */
    private ArrayList<String> restriccions;

    /**
     * Array que conté tots els productes del catàleg
     */
    private String[] productes;

    /**
     * Nom del producte, es mostra a la vista
     */
    private String nom_prod = "INSEREIX NOM";
    /**
     * Label per mostrar el nom del producte
     */
    private JLabel labelNomProd;
    /**
     * Text del boto Guardar, te valor "Guardar"
     */
    private String textBotoGuardar = "Guardar";
    /**
     * Boto Guardar, serveix per afegir el producte al catàleg
     */
    private Boto BotoGuardar;

    /**
     * Text del boto Canviar Nom, te valor "Canviar Nom"
     */
    private String textBotoCanviNom = "Canvia Nom";
    /**
     * Boto Canviar Nom, serveix per canviar el nom del producte
     */
    private Boto BotoCanviNom;

    /**
     * Boolean que determina si es la primera vegada en executar la vista
     */
    private Boolean primeraVegada = true;
    /**
     * String d'utilitat per determinar si una entrada de valors ha sigut cancelada
     */
    private String textCancelat = "Cancelat";
    /**
     * Integer per contar quina es la ultima Similitud donada
     */
    private int ultimaSimi = 0;

    /**
     * Constructura de la clase
     * @param cps Instancia del controlador de restriccions
     */
    public VistaAfegirProducte (CtrlVistaCatalegAmbRestriccions cps) {
        controlVista = cps;
    }

    /**
     * Funcio que serveix per executar la vista, si es el primer cop que es fa s'inicialicien tots els components, si no s'actualitzan els components.
     */
    public void executar() {
        if (primeraVegada) {
            primeraVegada = false;
            titolFrame = "Afegir Producte";
            ajuda = "Estas a la vista per afegir un nou producte\n" +
                    "Label: Aqui veuras el nom que li has donat al producte, recorda no pot ser el nom d'un producte existent\n" +
                    "ComboBox: Aqui veuras tots el productes dins de cataleg, pot seleccionar un per afegir una restriccio\n" +
                    "Afegir Restriccio: Despres de seleccionar un producte, clica aquest boto per afegir una restriccio\n" +
                    "Afegir Similituds: Començaras un proces per afegir una similitud, per cada producte indicat. Un cop donades, aquest boto pasa a ser Editar Similituds\n" +
                    "Editar Similituds: Un cop selecciones un producte podras editar la similitud d'aquest producte amb el que estas creant" +
                    "Canvi Nom: Permet cambiar el nom del producte\n" +
                    "Guardar: Afegiras el producte de manera definitiva, no podras fer us d'aquest boto fins que el producte tingui nom i totes les similituds estiguin donades\n" +
                    "Tornar: Et permet anar a l'anterior vista, recorda perdras la informacio del producte si no la guardas\n" +
                    "Sortir: Finalitzar l'aplicacio\n";

            super.executar();
        } else {
            frameVista.setVisible(true);
            actualitzarComponents();

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
        te_nom = false;
        Simis_DONE = false;
        ultimaSimi = 0;

        //Inicialitzar vectors
        int mida_cataleg = controlVista.getNumProd();
        productes = controlVista.getProductes();
        similituds = new ArrayList<>();
        restriccions = new ArrayList<>();

        super.inicialitzarComponents();

        add(Box.createVerticalGlue());

        labelNomProd = new JLabel("PRODUCTE "+nom_prod);
        labelNomProd.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(labelNomProd);
        add(Box.createRigidArea(new Dimension(0,10)));
        //Capuramos nombre del producto
        afegirNom();

        textEtiquetaTriar = "PRODUCTES DINS DEL CATALEG";
        etiquetaTriar.setText(textEtiquetaTriar);

        //Setear botones de vista controlador
        textBotoAfegir = "Afegir Restriccions";
        botoAfegir.setText(textBotoAfegir);

        System.err.println(Simis_DONE);
        if (!Simis_DONE) {
            textBotoMostrar = "Afegir Similituds";
            botoMostrar.setText(textBotoMostrar);
            botoMostrar.setBackground(Color.YELLOW);
        }
        else canviBotoSimilituds();

        //Boto Canviar nom
        BotoCanviNom = new Boto(textBotoCanviNom);
        BotoCanviNom.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(BotoCanviNom);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(Box.createVerticalGlue());

        //Setear boto finalitzar
        BotoGuardar = new Boto(textBotoGuardar);
        BotoGuardar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        BotoGuardar.setBackground(Color.GREEN);
        add(BotoGuardar);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());

        opcions.removeAllItems();
        for (String item : productes) {
            opcions.addItem(item);
        }
    }

    /**
     * S'acutalicen tots els components de la vista
     */
    private void actualitzarComponents() {
        te_nom = false;
        nom_prod = "INSEREIX_NOM";
        labelNomProd.setText(nom_prod);
        Simis_DONE = false;
        ultimaSimi = 0;

        //Inicialitzar vectors
        int mida_cataleg = controlVista.getNumProd();
        productes = controlVista.getProductes();
        similituds = new ArrayList<>();
        restriccions = new ArrayList<>();


        afegirNom();
        if (!Simis_DONE) {
            textBotoMostrar = "Afegir Similituds";
            botoMostrar.setText(textBotoMostrar);
            botoMostrar.setBackground(Color.YELLOW);
        }
        else canviBotoSimilituds();



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
            //Afegir Restriccio
            String seleccionat = (String) opcions.getSelectedItem();
            if (seleccionat == null || seleccionat.isEmpty()) {
                JOptionPane.showMessageDialog(frameVista,
                        "No se ha seleccionat cap producte del ComboBox",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String parts[] = seleccionat.split(" -> ");
                String producteSelecionat = parts[0];
                AfegirRestriccio(producteSelecionat);
            }

        } else if (textBoto.equals(textBotoMostrar)) {
            if (Simis_DONE) {
                String seleccionat = (String) opcions.getSelectedItem();
                if (seleccionat == null || seleccionat.isEmpty()) {
                    JOptionPane.showMessageDialog(frameVista,
                            "No se ha seleccionat cap producte del ComboBox",
                            "Error Input",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    String parts[] = seleccionat.split(" -> ");
                    String producteSelecionat = parts[0];
                    EditarSimilitud(producteSelecionat);
                }
            } else {
                //Afegir Similituds
                AfegirSimilituds();
            }
        } else if (textBoto.equals(textBotoGuardar)) {
            finalizar();
        } else if (textBoto.equals(textBotoCanviNom)) {
            canviaNom();
        } else if (textBoto.equals(textBotoTornar)) {
            AvisSortir();
        } else if (textBoto.equals(textItemSortir)) {
            AvisSortir();
            super.botoAccionat(textBoto);
        } else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Metode que afegiex una a una totes les similituds del producte, amenys que l'usuari canceli l'accio
     */
    private void AfegirSimilituds() {

        for (int i = ultimaSimi; i < productes.length; ++i) {
            boolean ok = false;
            String result = getSimilitud(productes[i]);
            if (result != null) {
                similituds.add(i, result);
            } else {
                ultimaSimi = i;
                System.out.println("VistaAfegirProducte: Ultima similitud afegida numero "+ultimaSimi);
                afegirSimisCombobox();
                return;
            }
        }
        ultimaSimi = productes.length;
        Simis_DONE = true;
        afegirSimisCombobox();
        canviBotoSimilituds();
    }

    /**
     * Edita la similitud amb el producte seleccionat
     * @param prodSeleccionat Producte seleccionat al combobox
     */
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
            if (result != null) {
                similituds.set(index,result);
            }
        } else {
            System.err.println("VistaAfegirProducte: EditarSimilitud: Error Producte no trobat");
        }

        afegirSimisCombobox();
    }

    /**
     * Metode que canvia el text del combobox per un amb producte i similituds
     */
    private void afegirSimisCombobox() {
        String[] Prod_Simi = new String[productes.length];
        for (int i = 0; i < productes.length; ++i) {
            if ( i < ultimaSimi) Prod_Simi[i] = productes[i]+" -> "+similituds.get(i);
            else Prod_Simi[i] = productes[i];
        }

        String prodSame = nom_prod+" -> 0.0";
        opcions.removeAllItems();
        for (String item : Prod_Simi) {
            if (!item.equals(prodSame)) opcions.addItem(item);
        }

    }

    /**
     * Afegeix una restriccio amb el producte seleccionat
     * @param prodSeleccionat Producte seleccionat desde el combobox
     */
    private void AfegirRestriccio(String prodSeleccionat) {
        boolean trobat = false;
        for (String item : restriccions) {
            if (item.equals(prodSeleccionat)) trobat = true;
        }
        if (trobat) {
            String message = "Ja has afegit la restriccio entre " + nom_prod + " i " + prodSeleccionat;
            JOptionPane.showMessageDialog(frameVista, message, "Afegir Restriccio", JOptionPane.ERROR_MESSAGE);
        } else {
            restriccions.add(prodSeleccionat);
            String message = "La restriccio amb "+prodSeleccionat+ " s'ha afegit";
            JOptionPane.showMessageDialog(frameVista, message, "Restriccio afegida", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Funcio que afegeix el nom del producte
     */
    private void afegirNom() {
        String nom = null;
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
                    return;
                }
            }
        }
        nom_prod = nom;
        labelNomProd.setText("PRODUCTE: "+nom_prod);
    }

    /**
     * Funcio que canvia el nom del producte per un altre
     */
    private void canviaNom() {
        boolean done = false;
        while (!done) {
            String nom = getNomUsuari();
            if (nom != null) {
                if (!nom.equals(textCancelat)) {
                    if (!controlVista.findProd(nom)) {
                        done = true;
                        te_nom = true;
                        nom_prod = nom;
                        labelNomProd.setText("PRODUCTE: " + nom);
                    } else {
                        JOptionPane.showMessageDialog(frameVista,
                                "Aquest nom ja esta sent utilitzat per un altre producte",
                                "Error Input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    return;
                }
            }
        }
    }

    /**
     * Funcio que afegeix el producte al cataleg i tenca la vista si el producte te nom i te totes les similituds
     */
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
            String message = "No has afegit tota la informacio del producte\n" + falta_nom + "\n" + falta_Simis;
            JOptionPane.showMessageDialog(frameVista, message, "Error Input", JOptionPane.ERROR_MESSAGE);
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
            String message = "El producte ja s'ha afegit";
            JOptionPane.showMessageDialog(frameVista, message, "Producte Afegit", JOptionPane.INFORMATION_MESSAGE);
            controlVista.canviaVista("PrincipalCataleg");
        }
    }

    /**
     * Funcio que mostra al usuari un avis abans de sortir de la vista, per recordar li que ha de guardar el producte
     */
    private void AvisSortir() {
        int  result = JOptionPane.showConfirmDialog(frameVista,
                "CAUTION: Has d'afegir el producte, si surts perdras tota la informacio del producte\n" +
                        "Estas segur que vols tornar ?",
                "Estas segur?",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            controlVista.canviaVista("PrincipalCataleg");
        }

    }

    /**
     * Funcio que canvia el boto Afegir Similituds per el de Editar Similituds
     */
    private void canviBotoSimilituds() {
        textBotoMostrar = "Editar Similituds";
        botoMostrar.setText(textBotoMostrar);
        botoMostrar.setBackground(UIManager.getColor("Button.background"));
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
     * Funcio de gestio de valors, per obtenir un nom per el producte
     * @param producteSeleccionat Producte seleccionat desde el combobox
     * @return Retorna un string valid que compleixi el format
     */
    private String getSimilitud(String producteSeleccionat) {
        boolean numValid = false;
        String result = null;
        while (!numValid) {
            boolean error = false;
            double ret = -1;
            String message = "Quina sera la  similtud del producte " + nom_prod +
                    " amb el producte " + producteSeleccionat + "\n" +
                    "**RECORDA: 0 < Similitud < 100. HA DE SER UN DECIMAL**";
            result = JOptionPane.showInputDialog(frameVista, message, "Afegir Similitud", JOptionPane.QUESTION_MESSAGE);
            if (result != null && !result.isEmpty()) {
                try {
                    ret = Double.parseDouble(result);
                } catch (NumberFormatException e) {
                    error = true;
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
            } else if (!error){
                JOptionPane.showMessageDialog(frameVista,
                        "Si us plau, introdueix un numero del 1 al 100",
                        "Error Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }


}
