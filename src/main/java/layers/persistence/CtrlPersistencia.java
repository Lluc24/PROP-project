package layers.persistence;

import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.controllers.CtrlSolucions;

public class CtrlPersistencia {
    private static CtrlPersistencia instancia = null;
    private static CtrlPersistenciaSolucio ctrlPersistenciaSolucio = null;
    private static CtrlPersistenciaCataleg ctrlPersistenciaCataleg = null;

    private CtrlPersistencia(){}

    /**
     * Constructora de la classe
     *
     * @param cs Controlador de solucions de la capa de domini.
     * @param cg Controlador de cataleg de la capa de domini.
     */
    public static CtrlPersistencia getCtrlPersistencia(CtrlSolucions cs, CtrlCatalegAmbRestriccions cg){
        if (instancia == null){
            instancia = new CtrlPersistencia();
            ctrlPersistenciaSolucio = new CtrlPersistenciaSolucio(cs);
            ctrlPersistenciaCataleg = new CtrlPersistenciaCataleg(cg);
        }
        return instancia;
    }

    /**
     * La capa de domini crida a aquesta funcio per obtenir la instancia del controlador de cataleg
     *
     * @return Retorna el controlador de cataleg de la capa de persistencia
     */
    public CtrlPersistenciaCataleg getCtrlPersistenciaCataleg() {
       return ctrlPersistenciaCataleg;
    }

    /**
     * La capa de domini crida a aquesta funcio per obtenir la instancia del controlador de solucions
     *
     * @return Retorna el controlador de solucions de la capa de persistencia
     */
    public CtrlPersistenciaSolucio getCtrlPersistenciaSolucio() {
        return ctrlPersistenciaSolucio;
    }
}

