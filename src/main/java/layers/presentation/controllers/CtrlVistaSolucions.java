package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.NomSolucioNoValid;
import layers.presentation.views.VistaPrincipalSolucions;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    private CtrlSolucions ctrlSolucions = null;
    private VistaPrincipalSolucions vistaPplSols;

    public CtrlVistaSolucions(CtrlSolucions cs) {
        this.ctrlSolucions = cs;
        vistaPplSols = new VistaPrincipalSolucions();

    }

    @Override
    public void executar() {
        vistaPplSols.executar(this);
    }

    public String[] getSolucions() {
        System.out.println("ctrlSols->getSolucions()");
        return new String[]{"solucio1", "solucio2"};
    }

    public String getAlgorismeAct(){
        return ctrlSolucions.getAlgorismeAct();
    }

    public void afegeixSolucio(String nom, int prodPrest){
        System.out.println("ctrlSols->afegrixSolucio()");
    }

    public void gestioAlgorisme(){
        System.out.println("ctrlSols->gestioAlgorisme()");
    }
    public void mostrarSolucio(String s){
        System.out.println("ctrlSols->getSolucio(" + s + ")");
    }

    public Boolean existeixSolucio(String nom) throws NomSolucioNoValid{
        return false;
    }
}
