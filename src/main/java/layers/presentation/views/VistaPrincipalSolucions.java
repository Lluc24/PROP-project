package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class VistaPrincipalSolucions extends VistaControladors {
    private CtrlVistaSolucions ctrlVistaSolucions;

    private String textBotoAlgorisme = "Canviar l'algorisme actual";
    private Boto botoAlgorisme;

    protected String textItemCrear = "Crear solucio";
    protected MenuItem menuItemCrear;

    protected String textItemAlgorisme = "Canviar algorisme actual";
    protected MenuItem menuItemAlgorisme;

    /**
     * Funcio constructora de la vista.
     *
     * @param ctrl Controlador de solucions que gestiona la vista.
     */
    public VistaPrincipalSolucions(CtrlVistaSolucions ctrl){
        ctrlVistaSolucions = ctrl;
    }

    /**
     * El controlador crida aquesta funció cada vegada que vol mostrar la vista.
     */
    public void executar() {
        //Si es la primera vegada que s'executa, inicialitzar
        if (primeraVegada) {
            titolFrame = "Vista Principal Solucions";
            ajuda = "Estas a la vista principal de solucions. Des d'aquesta vista pots provar totes les funcionalitats relacionades amb  les solucions " +
                    "utilitzant els items correstponents.\n" +
                    "ComboBox d'opcions: Permet veure totes les solucions existents.\n" +
                    "Mostra solucio: Mostra la solucio seleccionada al ComboBox i permet eliminar-la i modificar-la.\n" +
                    "Crear solucio: Permet crear una nova solucio amb els productes ja existents al cataleg.\n" +
                    "Gestionar algorisme: Permet crear una nova solucio.\n";
        }
        super.executar();
        ArrayList<String> solsIni = ctrlVistaSolucions.getSolucions();
        opcions.removeAllItems();
        for (int i = 0; i < solsIni.size(); ++i){
            opcions.addItem(solsIni.get(i));
        }
    }

    /**
     * Inicialitza els components necessaris de la vista.
     */
    @Override
    protected void inicialitzarComponents() {
        super.inicialitzarComponents();

        // Inicialitzem el boto 2
        botoAlgorisme = new Boto(textBotoAlgorisme);
        botoAlgorisme.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoAlgorisme);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());

        //Inicialitzem el text dels botons
        textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
        etiquetaTriar.setText(textEtiquetaTriar);
        textBotoAfegir = "Crear solucio";
        botoAfegir.setText(textBotoAfegir);
        textBotoMostrar = "Mostrar solucio";
        botoMostrar.setText(textBotoMostrar);

        //Inicialitzem els items del menu
        menuItemCrear = new Item(textItemCrear); // Item Respecte a
        menuFitxer.add(menuItemCrear);
        menuItemAlgorisme = new Item(textItemAlgorisme); // Item ? Ajuda
        menuFitxer.add(menuItemAlgorisme);

        //Inicialitzem el ComboBox amb totes les opcions
        ArrayList<String> solsIni = ctrlVistaSolucions.getSolucions();
        opcions.removeAllItems();
        for (int i = 0; i < solsIni.size(); ++i){
            opcions.addItem(solsIni.get(i));
        }
    }

    /**
     * Indica al controlador que l'usuari ha clickat el boto "Tornar".
     */
    @Override
    public void tornar(){
        ctrlVistaSolucions.tornar();
    }

    /**
     * Detecta que s'ha premut un boto i gestiona que fa el sistema segons el seu text associat.
     *
     * @param textBoto Text associat al boto.
     */
    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            int alg = confirmacioAlgorisme();
            if (alg > 0) {
                String nom = insertaNom();
                if (!nom.equals(null)) {
                    int prodPrestatge = insertaProdPrestatge();
                    if (prodPrestatge > 0) {
                        if (ctrlVistaSolucions.afegeixSolucio(nom, prodPrestatge)) {
                            panelInformatiu("La solucio '" + nom + "'s'ha afegit correctament");
                            ctrlVistaSolucions.afegeixSolucio(nom, prodPrestatge);
                            opcions.addItem(nom);
                            opcions.setSelectedItem(nom);
                        }
                        else panelInformatiu("L'algorisme no ha pogut solucionar amb els productes actuals.");
                    }
                }
            }
        }
        else if (textBoto.equals(textBotoAlgorisme)) {
            ctrlVistaSolucions.canviarAlgorisme();
        }
        else if (textBoto.equals(textBotoMostrar)) {
            String solucioSeleccionada = (String) opcions.getSelectedItem();
            if (solucioSeleccionada == null) {
                JOptionPane.showMessageDialog(frameVista,
                        "No hi ha cap solucio al sistema.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                ctrlVistaSolucions.mostrarSolucio(solucioSeleccionada);
                opcions.removeAllItems();
                ArrayList<String> noms = ctrlVistaSolucions.getSolucions();
                for (String nom : noms) {
                    opcions.addItem(nom);
                }
            }
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Aquesta funcio es crida quan s'ha clickat un item de la vista.
     *
     * @param textItem text associat al item.
     */
    @Override
    protected void itemAccionat(String textItem) {
        if (textItem.equals(textItemCrear)) {
            botoAccionat(textBotoAfegir);
        }
        else if (textItem.equals(textItemAlgorisme)) {
            botoAccionat(textBotoAlgorisme);
        }
        else {
            super.itemAccionat(textItem);
        }
    }

    /**
     * L'usuari vol sortir del sistema, aquesta funcio informa d'aixo al controlador.
     */
    @Override
    protected void sortirSistema(){
        ctrlVistaSolucions.sortirSistema();
    }

    /**
     * L'usuari vol crear una nova solucio i ha d'indicar-ne el nom
     *
     * @return Retorna el nom que ha escrit l'usuari. Si ha abortat durant la operacio, retorna null
     */
    private String insertaNom(){
        String input = JOptionPane.showInputDialog(frameVista,
                "Escriu el nom de la nova solucio:",
                "Insertar nom",
                JOptionPane.QUESTION_MESSAGE);

        //L'usuari ha abortat
        if (input == null) {
            return input;
        }
        if(ctrlVistaSolucions.existeixSolucio(input)) {
            JOptionPane.showMessageDialog(frameVista,
                    "Ja existeix una solucio amb aquest nom al sistema.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaNom();
        }
        else if (input.isBlank()) {
            JOptionPane.showMessageDialog(null,
                    "El nom de la solucio no pot estar buit o contenir nomes espais.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaNom();
        }

        if (input.contains(" ")) {
            JOptionPane.showMessageDialog(null,
                    "El nom de la solucio no pot tenir espais.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaNom();
        }
        return input;
    }

    /**
     * L'usuari vol crear una nova solucio i ha d'indicar el numero de productes per prestatge
     *
     * @return Retorna el numero que ha escrit l'usuari. Si ha abortat durant la operacio, retorna null
     */
    private int insertaProdPrestatge(){
        // Mostrar un cuadre d'entrada on l'usuario pugui escriure un texto
        String input = JOptionPane.showInputDialog(frameVista,
                "Escriu el numero de productes en cada prestatgeria:",
                "Insertar productes per prestatgeria",
                JOptionPane.QUESTION_MESSAGE);

        //L'usuari ha abortat
        if (input == null) {
            return -1;
        }

        // Comprovar si L'usuari ha escrit correctament
        int inputNumber = -1;
        try {
            inputNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Si la entrada no es un numero valid, missatge d'error
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un numero enter positiu valid.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaProdPrestatge();
        }

        if (inputNumber < 1) {
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un numero enter positiu valid.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaProdPrestatge();
        }

        return inputNumber;
    }

    /**
     * El sistema demana confirmacio al usuari de que vol resoldre una solucio amb l'algorisme actual.
     */
    private int confirmacioAlgorisme() {
        String alg = ctrlVistaSolucions.getAlgorismeAct();
        String mssg = " ";
        if (alg.equals("backtracking")) mssg = "L'algorisme actual es de tipus Backtacking. \nTingues en compte que si hi ha mes de 10 productes al cataleg pot trigar molt en solucionar, vols continuar?";
        else mssg = "L'algorisme actual es de tipus " +alg+ ", vols continuar?";
        int result = JOptionPane.showConfirmDialog(frameVista,
                mssg,
                "Confirmacio algorisme",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            result = 1;
        }
        else result = -1;
        return result;
    }

    /**
     * El sistema mostra un panel informatiu
     *
     * @param text el text del panel
     */
    public void panelInformatiu(String text){
        JOptionPane.showMessageDialog(frameVista,
                text,
                "Confirmacio de l'accio",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
