package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    public CtrlVistaSolucions(CtrlGeneric ctrlGeneric) {
        super(ctrlGeneric);
    }

    @Override
    public void executar() {
        System.out.println("Metode executar de CtrlVistaSolucions");
    }

    private CtrlSolucions ctrlSolucions;
    private VistaInicialSolucio vistaInicialSolucio;
    private VistaInicial vistaInicial;

    public CtrlPreSolucions(VistaInicial vi, CtrlSolucions cs) {
        this.ctrlSolucions(cs);
        this.vistaInicial = vi;
        vistaInicialSolucio = new VistaInicialSolucio(this);
    }

    public void inicialitzarPresentacio(){
        vistaInicialSolucio.ferVisible();
    }

    public String botoCreaSolucio( String s){
        System.out.println("CtrlPreSol sap que has clickat el boto amb text: " +
                s );

        CtrlSolucions.creaSolucio("solucio",4);
        return "ok";
    }
}
