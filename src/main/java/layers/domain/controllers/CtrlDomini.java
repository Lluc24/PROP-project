package layers.domain.controllers;

import layers.persistence.CtrlPersistencia;
import layers.persistence.CtrlPersistenciaSolucio;

public class CtrlDomini{
    private static CtrlDomini instancia = null;
    private static CtrlCatalegAmbRestriccions ctrlCatalegAmbRestriccions = null;
    private static CtrlSolucions ctrlSolucions = null;
    private static CtrlPersistencia ctrlPersistencia = null;

    private CtrlDomini(){}

    public static CtrlDomini getCtrlDomini(){
        if (instancia == null){
            instancia = new CtrlDomini();
            ctrlCatalegAmbRestriccions = new CtrlCatalegAmbRestriccions();
            ctrlSolucions = new CtrlSolucions(ctrlCatalegAmbRestriccions);
            ctrlPersistencia = CtrlPersistencia.getCtrlPersistencia(ctrlSolucions, ctrlCatalegAmbRestriccions);
            CtrlPersistenciaSolucio cps = ctrlPersistencia.getCtrlPersistenciaSolucio();
            ctrlSolucions.setCtrlPersistenciaSolucio(cps);
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