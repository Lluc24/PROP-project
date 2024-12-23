package layers.presentation.controllers;

import layers.domain.controllers.CtrlDomini;

/**
 * @Class CtrlPresentacio
 * @Description Es el controlador principal de presentacio.
 * @author Lluc Santamaria Riba
 * @version 3.0
 *
 * @Information
 * Segueix el patro singleton.
 * Gestiona les instancies dels controladors especifics de la capa de presentacio.
 * Es comunica amb el controlador principal de domini.
 */
public class CtrlPresentacio {
    /** La instancia de la propia classe. */
    private static CtrlPresentacio instancia = null;
    /** La instancia del controlador principal de domini. */
    private static CtrlDomini ctrlDomini = null;
    /** La instancia del controlador de solucions de la capa de presentacio. */
    private static CtrlVistaSolucions ctrlVistaSolucions = null;
    /** La instancia del controlador del cataleg de la capa de presentacio. */
    private static CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions = null;

    /**
     * Constructora privatitzada per no permetre la lliure instanciacio externa.
     */
    private CtrlPresentacio(){}

    /**
     * Metode per obtenir la instancia de la propia classe
     * @return Retorna la instancia propia.
     */
    public static CtrlPresentacio getCtrlPresentacio(){
        if (instancia == null){
            instancia = new CtrlPresentacio();
            ctrlDomini = CtrlDomini.getCtrlDomini();
            ctrlVistaSolucions = new CtrlVistaSolucions(ctrlDomini.getCtrlSolucions());
            ctrlVistaCatalegAmbRestriccions = new CtrlVistaCatalegAmbRestriccions(ctrlDomini.getCtrlCatalegAmbRestriccions());
        }
        return instancia;
    }

    /**
     * Metode per obtenir la instancia del controlador de cataleg de presentacio.
     * @return Retorna la instancia del controlador de cataleg de presentacio.
     */
    public CtrlVistaGeneric getCtrlVistaCatalegAmbRestriccions() {
        return ctrlVistaCatalegAmbRestriccions;
    }

    /**
     * Metode per obtenir la instancia del controlador de solucions de presentacio.
     * @return Retorna la instancia del controlador de solucions de presentacio.
     */
    public CtrlVistaGeneric getCtrlVistaSolucions() {
        return ctrlVistaSolucions;
    }
}
