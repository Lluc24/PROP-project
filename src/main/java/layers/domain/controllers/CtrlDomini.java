package layers.domain.controllers;

import layers.persistence.*;

/**
 * @Class CtrlDomini
 * @Description Es el controlador principal de domini.
 * @author Lluc Santamaria Riba
 * @version 3.0
 *
 * @Information
 * Segueix el patro singleton.
 * Gestiona les instancies dels controladors especifics de la capa de domini.
 * Es comunica amb el controlador principal de persistencia.
 */
public class CtrlDomini{
    /** La instancia de la propia classe. */
    private static CtrlDomini instancia = null;
    /** La instancia del controlador del cataleg de la capa de domini. */
    private static CtrlCatalegAmbRestriccions ctrlCatalegAmbRestriccions = null;
    /** La instancia del controlador de solucions de la capa de domini. */
    private static CtrlSolucions ctrlSolucions = null;
    /** La instancia del controlador principal de persistencia. */
    private static CtrlPersistencia ctrlPersistencia = null;

    /**
     * Constructora privatitzada per no permetre la lliure instanciacio externa.
     */
    private CtrlDomini(){}

    /**
     * Metode per obtenir la instancia de la propia classe
     * @return Retorna la instancia propia.
     */
    public static CtrlDomini getCtrlDomini() {
        if (instancia == null){
            instancia = new CtrlDomini();
            ctrlCatalegAmbRestriccions = new CtrlCatalegAmbRestriccions();
            ctrlSolucions = new CtrlSolucions(ctrlCatalegAmbRestriccions);
            ctrlPersistencia = CtrlPersistencia.getCtrlPersistencia(ctrlSolucions, ctrlCatalegAmbRestriccions);
            CtrlPersistenciaSolucio cps = ctrlPersistencia.getCtrlPersistenciaSolucio();
            ctrlSolucions.setCtrlPersistenciaSolucio(cps);
            CtrlPersistenciaCataleg cpc = ctrlPersistencia.getCtrlPersistenciaCataleg();
            ctrlCatalegAmbRestriccions.setCtrlPersistenciaCataleg(cpc);
        }
        return instancia;
    }

    /**
     * Metode per obtenir la instancia del controlador de cataleg de domini.
     * @return Retorna la instancia del controlador de cataleg de domini.
     */
    public CtrlCatalegAmbRestriccions getCtrlCatalegAmbRestriccions() {
        return ctrlCatalegAmbRestriccions;
    }

    /**
     * Metode per obtenir la instancia del controlador de solucions de domini.
     * @return Retorna la instancia del controlador de solucions de domini.
     */
    public CtrlSolucions getCtrlSolucions() {
        return ctrlSolucions;
    }
}