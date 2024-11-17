package layers.domain.controllers;

public class CtrlDomini{
    private static CtrlDomini instancia = null;
    private static CtrlCataleg ctrlCataleg = null;
    private static CtrlSolucions ctrlSolucions = null;

    private CtrlDomini(){}

    public static CtrlDomini getCtrlDomini(){
        if (instancia == null){
            instancia = new CtrlDomini();
            ctrlCataleg = new CtrlCataleg();
            ctrlSolucions = new CtrlSolucions(ctrlCataleg);
        }
        return instancia;
    }

    public CtrlCataleg getCtrlCataleg() {
        return ctrlCataleg;
    }

    public CtrlSolucions getCtrlSolucions() {
        return ctrlSolucions;
    }
}