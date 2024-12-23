package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;

public class VistaGestioAlgorisme extends VistaGenerica {
    private CtrlVistaSolucions ctrlVistaSolucions;

    private String textEtiquetaInfo = "L'algorisme actual es de tipus ";
    private JLabel etiquetaInfo;
    private String textEtiquetaTriar = "Vols canviar-lo? Selecciona el que prefereixis:";
    private JLabel etiquetaTriar;

    private String textBotoAproximacio = "Aproximacio";
    private Boto botoAproximacio;

    private String textBotoGreedy = "Greedy";
    private Boto botoGreedy;

    private String textBotoBacktracking = "Backtracking";
    private Boto botoBacktracking;

    protected String textItemAproximacio = "Canviar a Aproximacio";
    protected MenuItem menuItemAproximacio;

    protected String textItemGreedy = "Canviar a Greedy";
    protected MenuItem menuItemGreedy;

    protected String textItemBacktracking = "Canviar a Backtracking";
    protected MenuItem menuItemBacktracking;

    private Boolean primeraVegada = true;

    /**
     * Funcio constructora de la vista
     *
     * @param ctrl Controlador de la vista
     */
    public VistaGestioAlgorisme(CtrlVistaSolucions ctrl) {
        ctrlVistaSolucions = ctrl;
    }

    /**
     * Cada vegada que el controlador vol mostrar la vista, crida aquesta funcio.
     */
    public void executar() {
        if (primeraVegada) {
            titolFrame = "Vista gestio algorisme";
            ajuda = "Estas a la vista de gestio d'algorismes. Des d'aquesta vista pots canviar l'algorisme actual " +
                    "utilitzant els botons correstponents.\n" +
                    "Aproximacio: l'algorisme actual passara a ser de tipus Aproximacio.\n" +
                    "Greedy: l'algorisme actual passara a ser de tipus Greedy.\n" +
                    "Backtracking: l'algorisme actual passara a ser de tipus Backtracking.\n" +
                    "Sortir: Finalitza l'aplicacio.";
            super.executar();
            primeraVegada = false;
        } else frameVista.setVisible(true);

    }

    /**
     * Aquesta funcio inicialitza els components especifics de la vista.
     */
    @Override
    protected void inicialitzarComponents() {
        // Inicialitzem la superclase
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem la etiqueta descriptiva
        String alg = ctrlVistaSolucions.getAlgorismeAct();
        textEtiquetaInfo = "L'algorisme actual és de tipus Aproximacio.";
        etiquetaInfo = new JLabel(textEtiquetaInfo);
        etiquetaInfo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaInfo);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem la etiqueta descriptiva
        etiquetaTriar = new JLabel(textEtiquetaTriar);
        etiquetaTriar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaTriar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Aproximacio
        botoAproximacio = new Boto(textBotoAproximacio);
        botoAproximacio.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoAproximacio);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Greedy
        botoGreedy = new Boto(textBotoGreedy);
        botoGreedy.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoGreedy);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Backtracking
        botoBacktracking = new Boto(textBotoBacktracking);
        botoBacktracking.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoBacktracking);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(Box.createVerticalGlue());

        //Inicialitzem els items del menu
        menuItemAproximacio = new Item(textItemAproximacio); // Item Respecte a
        menuFitxer.add(menuItemAproximacio);
        menuItemGreedy = new Item(textItemGreedy); // Item ? Ajuda
        menuFitxer.add(menuItemGreedy);
        menuItemBacktracking = new Item(textItemBacktracking); // Item ? Ajuda
        menuFitxer.add(menuItemBacktracking);
    }

    /**
     * Si l'usuari prem el boto "Tornar", aquesta funcio informa al controlador.
     */
    @Override
    public void tornar(){
        ctrlVistaSolucions.controlVistes(0);
    }

    /**
     * Aquesta funcio es crida quan l'usuari prem un boto de la vista.
     *
     * @param textBoto text associat al boto clicat.
     */
    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAproximacio)) {
            if (textEtiquetaInfo.equals("L'algorisme actual es de tipus Aproximacio.")){
                panelInformatiu("L'algorisme ja es de tipus Aproximacio.");
            }
            else {
                ctrlVistaSolucions.gestioAlgorisme("aproximacio");
                textEtiquetaInfo = "L'algorisme actual es de tipus Aproximacio.";
                etiquetaInfo.setText(textEtiquetaInfo);
                panelInformatiu("L'algorisme actual ara es de tipus Aproximacio.");
            }
        } else if (textBoto.equals(textBotoGreedy)) {
            int idx = insertaIdx();
            if (idx >= 0) {
                int iteracions = insertaIteracions();
                if (iteracions >= 0) {
                    ctrlVistaSolucions.gestioAlgorisme("greedy");
                    ctrlVistaSolucions.setParametres(idx, iteracions);
                    textEtiquetaInfo = "L'algorisme actual es de tipus Greedy (idx = " + idx + ", iteracions = " + iteracions + ").";
                    etiquetaInfo.setText(textEtiquetaInfo);
                    panelInformatiu("L'algorisme actual ara es de tipus Greedy (idx = " + idx + ", iteracions = " + iteracions + ").");
                }
            }

        } else if (textBoto.equals(textBotoBacktracking)) {
            if (textEtiquetaInfo.equals("L'algorisme actual es de tipus Backtracking.")){
                panelInformatiu("L'algorisme ja es de tipus Backtracking.");
            }
            else {
                ctrlVistaSolucions.gestioAlgorisme("algorismeBT");
                textEtiquetaInfo = "L'algorisme actual es de tipus Backtracking.";
                etiquetaInfo.setText(textEtiquetaInfo);
                panelInformatiu("L'algorisme actual ara es de tipus Backtracking.");
            }
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Aquesta funcio es crida quan l'usuari prem un item de la vista.
     *
     * @param textItem Text associat al item.
     */
    @Override
    protected void itemAccionat(String textItem) {
        if (textItem.equals(textItemAproximacio)) {
            botoAccionat(textBotoAproximacio);
        }
        else if (textItem.equals(textItemGreedy)) {
            botoAccionat(textBotoGreedy);
        }
        else if (textItem.equals(textItemBacktracking)) {
            botoAccionat(textBotoBacktracking);
        }
        else {
            super.itemAccionat(textItem);
        }
    }

    /**
     * Aquesta funcio informa al controlador que l'usuari vol tancar el sistema.
     */
    @Override
    protected void sortirSistema() {
        ctrlVistaSolucions.sortirSistema();
    }

    /**
     * L'usuari vol que l'algorisme sigui de tipus greedy i ha d'indicar el index del producte
     * pel que es vol començar a calcular la solucio.
     *
     * @return Retorna el numero que ha escrit l'usuari. Si ha abortat durant la operacio, retorna null.
     */
    private int insertaIdx() {
        // Mostrar un cuadre d'entrada on l'usuari pugui escriure un text
        String input = JOptionPane.showInputDialog(frameVista,
                "Escriu l'index del producte pel que vols que l'algorisme comenci:",
                "Insertar parametre 'index'",
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
            return insertaIdx();
        }
        if (inputNumber < 0){
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un numero enter positiu valid.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaIteracions();
        }
        return inputNumber;
    }

    /**
     * L'usuari vol que l'algorisme sigui de tipus greedy i ha d'indicar numero d'iteracions que vol que faci
     * l'algrisme abans de tornar una solucio.
     *
     * @return Retorna el numero que ha escrit l'usuari. Si ha abortat durant la operacio, retorna null.
     */
    private int insertaIteracions() {
        // Mostrar un cuadre d'entrada on l'usuari pugui escriure un text
        String input = JOptionPane.showInputDialog(frameVista,
                "Escriu el numero d'iteracions que vols que faci l'algorisme:",
                "Insertar parametre 'iteracions'",
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
                    "Si us plau, introdueix un numero major o igual que 0.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaIteracions();
        }
        if (inputNumber <= 0){
            JOptionPane.showMessageDialog(frameVista,
                    "Si us plau, introdueix un numero major o igual que 0.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaIteracions();
        }
        return inputNumber;
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