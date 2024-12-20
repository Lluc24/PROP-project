package layers.persistence;

import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Controlador de persistencia carregar i validar arxius amb solucions.
 * Permet gestionar arxius i carregar dades de manera segura.
 *
 * true
 * Solucio 1
 * huevo arroz
 * carne
 *
 * false
 * Solucio 2
 * leche pan
 * queso
 *
 * @author Eulalia Peiret
 */

public class CtrlPersistenciaSolucio extends CtrlPersistenciaGeneric {
    private static CtrlSolucions ctrlSolucions = null;
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
            contenido = carregarArxiu(path, nombre);
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
                continue;
            }

            // Convertim la linea a boolean
            boolean modificada = Boolean.parseBoolean(lineas[index].trim());
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
