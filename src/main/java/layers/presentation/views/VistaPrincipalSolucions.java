package layers.presentation.views;

import layers.domain.excepcions.NomSolucioNoValid;
import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;

public class VistaPrincipalSolucions extends VistaControladors {
    private CtrlVistaSolucions ctrlVistaSolucions;

    protected String textBotoAlgorisme = "Canviar l'algorisme actual";
    protected Boto botoAlgorisme;

    public VistaPrincipalSolucions(){
    }

    public void executar(CtrlVistaSolucions ctrl) {
        if (primeraVegada) {
            ctrlVistaSolucions = ctrl;
            titolFrame = "Vista Principal Solucions";
            ajuda = "Estas a la vista principal de solucions. Des d'aquesta vista pots provar totes les funcionalitats relacionades amb  les solucions " +
                    "utilitzant els items correstponents.\n" +
                    "ComboBox d'opcions: Permet veure totes les solucions existents.\n" +
                    "Mostra solucio: Mostra la solucio seleccionada al ComboBox i permet eliminar-la i modificar-la.\n" +
                    "Crear solucio: Permet crear una nova solucio amb els productes ja existents al cataleg.\n" +
                    "Gestionar algorisme: Permet crear una nova solucio.\n";
        }
        super.executar();
    }

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
        String alg = ctrlVistaSolucions.getAlgorismeAct();
        textEtiquetaTriar = "L'algorisme actual és de tipus " + alg;
        etiquetaTriar.setText(textEtiquetaTriar);
        textBotoAfegir = "Crear solucio";
        botoAfegir.setText(textBotoAfegir);
        botoMostrar.setText("Mostrar solucio");
        String[] solsIni = ctrlVistaSolucions.getSolucions();
        opcions.removeAllItems();
        for (int i = 0; i < solsIni.length; ++i){
            opcions.addItem(solsIni[i]);
        }
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            String nom = insertaNom();
            if (!nom.equals(null)) {
                int prodPrestatge = insertaProdPrestatge();
                if (prodPrestatge > 0) {
                    ctrlVistaSolucions.afegeixSolucio(nom, prodPrestatge);
                    opcions.addItem(nom);
                    opcions.setSelectedItem(nom);
                }
            }
        }
        else if (textBoto.equals(textBotoAlgorisme)) {
            ctrlVistaSolucions.gestioAlgorisme();
        }
        else if (textBoto.equals(textBotoMostrar)) {
            String solucioSeleccionada = (String) opcions.getSelectedItem();
            ctrlVistaSolucions.mostrarSolucio(solucioSeleccionada);
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    private String insertaNom(){
        String input = JOptionPane.showInputDialog(frameVista,
                "Escriu el nom de la nova solucio:",
                "Insertar nom",
                JOptionPane.QUESTION_MESSAGE);

        //L'usuari ha abortat
        if (input == null) {
            return input;
        }

        // Comprobar si el usuario ha escrit correctament
        try {
            ctrlVistaSolucions.existeixSolucio(input);
        } catch (NomSolucioNoValid e) {
            // Si la entrada no es un numero valid, missatge d'error
            JOptionPane.showMessageDialog(frameVista,
                    "Ja existeix una solucio amb aquest nom al sistema.",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaNom();
        }
        return input;
    }

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
                    "Si us plau, introdueix un numero enter positiu valid .",
                    "Error d'entrada",
                    JOptionPane.ERROR_MESSAGE);
            return insertaProdPrestatge();
        }

        return inputNumber;
    }
}
