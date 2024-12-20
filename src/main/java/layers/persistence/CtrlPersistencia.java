package layers.persistence;

import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.controllers.CtrlSolucions;

public class CtrlPersistencia {
    private static CtrlPersistencia instancia = null;
    private static CtrlPersistenciaSolucio ctrlPersistenciaSolucio = null;
    //private static CtrlPersistenciaCataleg ctrlPersistenciaCataleg = null;

    private CtrlPersistencia(){}

    public static CtrlPersistencia getCtrlPersistencia(CtrlSolucions cs, CtrlCatalegAmbRestriccions cg){
        if (instancia == null){
            instancia = new CtrlPersistencia();
            ctrlPersistenciaSolucio = new CtrlPersistenciaSolucio(cs);
            //ctrlPersistenciaCataleg = new CtrlPersistenciaCataleg(cg);
        }
        return instancia;
    }

    //public CtrlPersistenciaCataleg getCtrlPersistenciaCataleg() {
    //   return ctrlPersistenciaCataleg;
    //}

    public CtrlPersistenciaSolucio getCtrlPersistenciaSolucio() {
        return ctrlPersistenciaSolucio;
    }
}

