package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.excepcions.FormatInputNoValid;

import javax.swing.*;

/**
 *  CtrlVistaGeneric
 *  Es la superclasse dels controladors de la capa de presentacio.
 * @author Lluc Santamaria Riba
 * @version 3.1
 *
 * <p><b>Informació:</b></p>
 * Tots els controladors de la capa de presentacio hereden d'aquest.
 * Permet definir comportament i funcions comunes.
 * Redueix l'acoblament del projecte.
 */
public abstract class CtrlVistaGeneric {

    /** Controlador del domini amb el qual treballa. */
    protected CtrlGeneric ctrl = null;

    /**
     * Constructora de la classe.
     * @param ctrlGeneric: Controlador de la capa de domini amb el qual treballara.
     */
    protected CtrlVistaGeneric(CtrlGeneric ctrlGeneric) {
        this.ctrl = ctrlGeneric;
    }

    /**
     * Constructora per defecte.
     */
    protected CtrlVistaGeneric() {
    }

    /**
     * Metode executar que inicia els casos d'us que gestionen els controladors
     * que hereten d'aquesta classe.
     */
    public void executar() {
        System.out.println("Metode executar de CtrlVistaGeneric");
    }

    /**
     * Metode abstracte per importar un fitxer de disc.
     * @param path: La ruta fins al directori que conte el fitxer.
     * @param nomFitxer: El nom del fitxer
     * @throws FormatInputNoValid Excepcio llencada en cas de que el fitxer no sigui valid
     */
    public abstract void importar(String path, String nomFitxer) throws FormatInputNoValid;

    /**
     * Metode abstracte per exportar un fitxer de disc.
     * @param path: La ruta fins al directori on es guardara el fitxer.
     * @param nomFitxer: El nom del fitxer
     * @throws FormatInputNoValid Excepcio llencada en cas de que el directori o nomFitxer no permeting guardar-lo
     */
    public abstract void exportar(String path, String nomFitxer) throws FormatInputNoValid;

    /**
     * Metode executat per acabar l'execucio del sistema.
     */
    public void sortirSistema() {
        System.exit(0);
    }
}
