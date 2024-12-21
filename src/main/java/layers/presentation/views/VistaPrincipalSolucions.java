package layers.presentation.views;

import layers.domain.excepcions.NomSolucioNoValid;
import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class VistaPrincipalSolucions extends VistaControladors {
    private CtrlVistaSolucions ctrlVistaSolucions;

    private String textBotoAlgorisme = "Canviar l'algorisme actual";
    private Boto botoAlgorisme;

    //Constructora
    public VistaPrincipalSolucions(CtrlVistaSolucions ctrl){
        ctrlVistaSolucions = ctrl;
    }

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
        textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
        etiquetaTriar.setText(textEtiquetaTriar);
        textBotoAfegir = "Crear solucio";
        botoAfegir.setText(textBotoAfegir);
        textBotoMostrar = "Mostrar solucio";
        botoMostrar.setText(textBotoMostrar);

        //Inicialitzem el ComboBox amb totes les opcions
        ArrayList<String> solsIni = ctrlVistaSolucions.getSolucions();
        opcions.removeAllItems();
        for (int i = 0; i < solsIni.size(); ++i){
            opcions.addItem(solsIni.get(i));
        }
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            int alg = confirmacioAlgorisme();
            if (alg > 0) {
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
        }
        else if (textBoto.equals(textBotoAlgorisme)) {
            ctrlVistaSolucions.canviarAlgorisme();
        }
        else if (textBoto.equals(textBotoMostrar)) {System.out.println("1");
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
        else if (textBoto.equals(textBotoTornar)){
            ctrlVistaSolucions.tornar();
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    @Override
    protected void itemAccionat(String textItem) {
        if (textItem.equals(textItemSortir)) {
            int result = JOptionPane.showConfirmDialog(frameVista,
                    "Estas segur que vols sortir de l'aplicacio?",
                    "Sortir de l'aplicacio",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.out.println("Fins la proxima!");
                ctrlVistaSolucions.sortirAplicacio();
            }
        } else {
            super.itemAccionat(textItem);
        }
    }

    /**
     * L'usuari vol crear una nova solucio i ha d'indicar-ne el nom
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

    private int confirmacioAlgorisme() {
        String alg = ctrlVistaSolucions.getAlgorismeAct();
        int result = JOptionPane.showConfirmDialog(frameVista,
                "L'algorisme actual es de tipus "+alg+", vols continuar?",
                "Confirmacio algorisme",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            result = 1;
        }
        else result = -1;
        return result;
    }
}
