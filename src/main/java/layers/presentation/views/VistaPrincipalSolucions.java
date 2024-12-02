package layers.presentation.views;

import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.*;
import java.awt.*;

public class VistaPrincipalSolucions extends VistaControladors {
    private CtrlVistaSolucions ctrlVistaSolucions;

    public void executar(CtrlVistaGeneric ctrl) {
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl;
        titolFrame = "Vista Principal Solucions";
        ajuda = "Estas a la vista principal de solucions. Des d'aquesta vista pots provar totes les funcionalitats relacionades amb  les solucions " +
                "utilitzant els items correstponents.\n" +
                "ComboBox d'opcions: Permet veure totes les solucions existents.\n" +
                "Mostra solucio: Mostra la solucio seleccionada al ComboBox i permet eliminar-la i modificar-la.\n";
                "Crear solucio: Permet crear una nova solucio amb els productes ja existents al cataleg.\n" +
                "Gestionar algorisme: Permet crear una nova solucio.\n";
        super.executar();
    }

    @Override
    protected void inicialitzarComponents() {
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        //Inicialitzem el text dels botons
        String alg = ctrlVistaSolucions.getAlgorismeAct();
        textEtiquetaTriar = "L'algorisme actual és de tipus" + alg;
        etiquetaTriar.setText(textEtiquetaTriar);
        botoAfegir.setText("Crear solucio");
        boto2.setText("Gestio algorisme");
        botoMostrar.setText("Mostrar algorisme");
        String[] sols = ctrlVistaSolucions.getSolucions();
        opcions = new JComboBox<>(sols);
    }

    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoAfegir)) {
            ctrlVistaSolucions.afegeixSolucio();
        }
        else if (textBoto.equals(textBoto2)) {
            ctrlVistaSolucions.gestioAlgorisme();
        }
        else if (textBoto.equals(textBotoMostrar)) {
            String solucioSeleccionada = opcions.getSelectedItem();
            ctrlVistaSolucions.mostrarSolucio(solucioSeleccionada);
        }
        else {
            super.botoAccionat(textBoto);
        }
    }
}
