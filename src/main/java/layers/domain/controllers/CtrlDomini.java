package layers.domain.controllers;

public class CtrlDomini{
    private static CtrlDomini instancia = null;
    private static CtrlCatalegAmbRestriccions ctrlCatalegAmbRestriccions = null;
    private static CtrlSolucions ctrlSolucions = null;

    private CtrlDomini(){}

    public static CtrlDomini getCtrlDomini(){
        if (instancia == null){
            instancia = new CtrlDomini();
            ctrlCatalegAmbRestriccions = new CtrlCatalegAmbRestriccions();
            ctrlSolucions = new CtrlSolucions(ctrlCatalegAmbRestriccions);
        }
        return instancia;
    }

    public CtrlCatalegAmbRestriccions getCtrlCatalegAmbRestriccions() {
        return ctrlCatalegAmbRestriccions;
    }

    public CtrlSolucions getCtrlSolucions() {
        return ctrlSolucions;
    }
}