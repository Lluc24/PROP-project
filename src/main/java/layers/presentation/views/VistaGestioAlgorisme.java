package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;

public class VistaGestioAlgorisme extends VistaGenerica {
    private CtrlVistaSolucions ctrlVistaSolucions;

    protected String textEtiquetaInfo = "L'algorisme actual es de tipus ";
    protected JLabel etiquetaInfo;
    protected String textEtiquetaTriar = "Vols canviarlo? Selecciona el que prefereixis:";
    protected JLabel etiquetaTriar;

    protected String textBotoAproximacio = "Aproximacio";
    protected Boto botoAproximacio;

    protected String textBotoGreedy = "Greedy";
    protected Boto botoGreedy;

    protected String textBotoBacktracking = "Backtracking";
    protected Boto botoBacktracking;

    Boolean primeraVegada = true;

    public VistaGestioAlgorisme(CtrlVistaSolucions ctrl) {
        ctrlVistaSolucions = ctrl;
    }

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

    @Override
    protected void inicialitzarComponents() {
        // Inicialitzem la superclase
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem la etiqueta descriptiva
        String alg = ctrlVistaSolucions.getAlgorismeAct();
        textEtiquetaInfo = "L'algorisme actual és de tipus " + alg;
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
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAproximacio)) {
            ctrlVistaSolucions.gestioAlgorisme("aproximacio");
            textEtiquetaInfo = "L'algorisme actual és de tipus aproximacio";
            etiquetaInfo.setText(textEtiquetaInfo);
        } else if (textBoto.equals(textBotoGreedy)) {
            int idx = insertaIdx();
            int iteracions = insertaIteracions();
            if (idx >= 0 && iteracions >= 0) {
                ctrlVistaSolucions.gestioAlgorisme("greedy");
                ctrlVistaSolucions.setParametres(idx, iteracions);
                textEtiquetaInfo = "L'algorisme actual és de tipus greedy (idx = " + idx + ", iteracions = " + iteracions + ")";
                etiquetaInfo.setText(textEtiquetaInfo);
            }

        } else if (textBoto.equals(textBotoBacktracking)) {
            ctrlVistaSolucions.gestioAlgorisme("algorismeBT");
            textEtiquetaInfo = "L'algorisme actual és de tipus backtracking";
            etiquetaInfo.setText(textEtiquetaInfo);
        } else {
            super.botoAccionat(textBoto);
        }
    }

    @Override
    protected void itemAccionat(String textItem) {
        super.itemAccionat(textItem);
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
}