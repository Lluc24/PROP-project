package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;
import layers.presentation.VistaPrincipalSolucio;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    private CtrlSolucions iCtrlSolucions;
    private VistaPrincipalSolucio iVistaPplSols;

    public CtrlVistaSolucions(CtrlSolucions cs) {
        this.iCtrlSolucions = cs;
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

    public String getAlgorismeAct(){
        return iCtrlSolucions.getAlgorismeAct();
    }
}
