package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;
import layers.presentation.VistaPrincipalSolucio;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    private CtrlSolucions ctrlSolucions;
    private VistaPrincipalSolucio iVistaPplSols;

    public CtrlVistaSolucions(CtrlSolucions cs) {
        this.ctrlSolucions(cs);
        iVistaPplSols = new VistaPrincipalSolucio(this);
    }

    public CtrlVistaSolucions(CtrlGeneric ctrlGeneric) {
        super(ctrlGeneric);
    }

    @Override
    public void executar() {
        iVistaPplSols.ferVisible();
    }

    public String botoCreaSolucio( String s){
        System.out.println("CtrlPreSol sap que has clickat el boto amb text: " +
                s );

        CtrlSolucions.creaSolucio("solucio",4);
        return "ok";
    }

    public String[] getSolucions(){

    }
}
