package layers.persistence;

import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.controllers.CtrlSolucions;

/**
 * Classe 'CtrlPersistencia'
 *
 * Controlador de persistència principal que gestiona les instàncies dels controladors de persistència
 * per a solucions i catàlegs. Aquesta classe actua com a punt d'entrada per a la capa de persistència,
 * permetent obtenir i gestionar els controladors específics de solucions i catàlegs.
 *
 * @see CtrlPersistenciaSolucio
 * @see CtrlPersistenciaCataleg
 * @see CtrlSolucions
 * @see CtrlCatalegAmbRestriccions
 *
 * @author Eulalia Peiret Santacana
 *
 * <p><b>Informació:</b></p>
 * Aquesta classe implementa el patró Singleton per garantir que només hi hagi una única instància
 * del controlador de persistència. Els mètodes d'aquesta classe permeten inicialitzar i obtenir
 * els controladors específics per a la gestió de solucions i catàlegs.
 *
 * <p><b>Funcionalitats principals:</b></p>
 * - Inicialització dels controladors de persistència per a solucions i catàlegs.
 * - Obtenir instàncies dels controladors específics de persistència.
 *
 */
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

