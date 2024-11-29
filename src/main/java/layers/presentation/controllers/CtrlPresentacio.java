package layers.presentation.controllers;

import layers.domain.controllers.CtrlDomini;
import layers.domain.controllers.CtrlSolucions;
import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;

public class CtrlPresentacio {
    private static CtrlPresentacio instancia = null;
    private static CtrlDomini ctrlDomini = null;
    private static CtrlVistaSolucions ctrlVistaSolucions = null;
    private static CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions = null;

    private CtrlPresentacio(){}

    public static CtrlPresentacio getCtrlPresentacio(){
        if (instancia == null){
            instancia = new CtrlPresentacio();
            ctrlDomini = CtrlDomini.getCtrlDomini();
            ctrlVistaSolucions = new CtrlVistaSolucions(ctrlDomini.getCtrlSolucions());
            ctrlVistaCatalegAmbRestriccions = new CtrlVistaCatalegAmbRestriccions(ctrlDomini.getCtrlCatalegAmbRestriccions());
        }
        return instancia;
    }

    public CtrlVistaGeneric getCtrlVistaCatalegAmbRestriccions() {
        return ctrlVistaCatalegAmbRestriccions;
    }

    public CtrlVistaGeneric getCtrlVistaSolucions() {
        return ctrlVistaSolucions;
    }
}
