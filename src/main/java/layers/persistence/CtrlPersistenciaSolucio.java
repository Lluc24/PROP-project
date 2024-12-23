package layers.persistence;

import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Classe 'CtrlPersistenciaSolucio'
 *
 * Controlador de persistència per a la gestió de solucions. Aquesta classe hereta de 'CtrlPersistenciaGeneric'
 * i utilitza el controlador 'CtrlSolucions' per a carregar i validar dades de solucions des d'un arxiu.
 * L'algorisme de processament divideix el contingut de l'arxiu en diferents fases: estat de modificació,
 * nom de la solució i estructura de dades de la solució.
 *
 * @see CtrlPersistenciaGeneric
 * @see CtrlSolucions
 *
 * @author Eulalia Peiret Santacana
 *
 * <p><b>Informació:</b></p>
 * El mètode 'processarDadesArxiu' carrega un arxiu que conté informació sobre diverses solucions. El format de l'arxiu ha de ser el seguent:
 * - La primera línia indica si la solució està modificada ('true' o 'false').
 * - La segona línia conté el nom de la solució.
 * - Les línies següents descriuen l'estructura de la solució, amb elements separats per espais.
 * - Cada solució està separada per una línia buida.
 *
 * El mètode valida que les dades tinguin el format correcte i crida al controlador de domini per carregar la informació.
 *
 * <p><b>Excepcions:</b></p>
 * - <b>NomSolucioNoValid</b>: L'arxiu no té el format adequat o no es poden carregar les dades.
 * - <b>FormatInputNoValid</b>: Els valors a l'arxiu no són vàlids, com un format incorrecte o línies invàlides.
 */
public class CtrlPersistenciaSolucio extends CtrlPersistenciaGeneric {
    private static CtrlSolucions ctrlSolucions = null;

    /**
     * Funcio conctructora de la classe.
     *
     * @param cs Controlador de la capa de domini.
     */
    public CtrlPersistenciaSolucio(CtrlSolucions cs){
        ctrlSolucions = cs;
    }

    /**
     * Metode per obtenir les dades procesades d'un arxiu.
     *
     * @param path  Ruta del arxiu.
     * @param nombre Nom del arxiu.
     */
    @Override
    public void processarDadesArxiu(String path, String nombre) throws NomSolucioNoValid, FormatInputNoValid {
        super.processarDadesArxiu(path, nombre);

        String contenido = null;
        try {
            contenido = importar(path, nombre);
        }catch (IOException e){
            throw new FormatInputNoValid("L'arxiu '" +nombre+ "' falla.");
        }

        // Dividim el contingut en lineas
        String[] lineas = contenido.split("\\n");
        int index = 0;

        while (index < lineas.length) {
            // Si hi ha mes d'una nova linea buida entre solucio
            if (lineas[index].isEmpty()) {
                index++;
            }

            // Convertim la linea a boolean
            String valor = lineas[index].trim();
            if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false")) {
                throw new FormatInputNoValid("L'arxiu '" +nombre+ "' falla.");
            }
            boolean modificada = Boolean.parseBoolean(valor);
            index++;

            // Llegir la segona linea com nomSolucio
            String nomSolucio = lineas[index].trim();
            index++;

            // Llegir les seguents linies fins trobar una linea buida
            ArrayList<ArrayList<String>> sol = new ArrayList<>();

            while (index < lineas.length && !lineas[index].isEmpty()) {
                // Dividir los elementos de la línea en una lista
                ArrayList<String> a = new ArrayList<>(Arrays.asList(lineas[index].trim().split(" ")));
                sol.add(a);
                index++;
            }

            // Crida a carregaSolucio
            ctrlSolucions.carregaSolucio(modificada, nomSolucio, sol) ;

            // Saltar línea vacía
            index++;
        }
    }
}
