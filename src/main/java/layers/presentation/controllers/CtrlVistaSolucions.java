package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;
import layers.presentation.views.VistaPrincipalSolucions;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    private CtrlSolucions ctrlSolucions;
    private VistaPrincipalSolucions vistaPplSols;

    public CtrlVistaSolucions(CtrlSolucions cs) {
        this.ctrlSolucions = cs;
        vistaPplSols = new VistaPrincipalSolucions(this);
    }

    @Override
    public void executar() {
        vistaPplSols.executar(this);
    }

    public String[] getSolucions(){
        System.out.println("ctrlSols->getSolucions()");
        return String[]{"solucio1", "solucio2"};
    }

    public String getAlgorismeAct(){
        System.out.println("ctrlSols->getAlgorismeAct()");
        return ctrlSolucions.getAlgorismeAct();
    }

    public void afegeixSolucio(){
        System.out.println("ctrlSols->afegrixSolucio()");
    }

    public void gestioAlgorisme(){
        System.out.println("ctrlSols->gestioAlgorisme()");
    }
    public void mostrarSolucio(String s){
        System.out.println("ctrlSols->getSolucio(" + s + ")");
    }
}
