package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.FormatInputNoValid;
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
        return ctrlSolucions.getSolucionsNom();
    }


    public String getAlgorismeAct(){
        return ctrlSolucions.getAlgorismeAct();
    }

    public void afegeixSolucio(String nom, int prodPrest){
        try{
            ctrlSolucions.creaSolucio(nom, prodPrest);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    public void gestioAlgorisme(){

    }
    public void mostrarSolucio(String s){
        //ctrlSolucions.mostrarSolucio(s);
        System.out.println("ctrlSols->getSolucio(" + s + ")");
    }

    public Boolean existeixSolucio(String nom) throws NomSolucioNoValid{
        return false;
    }
}
