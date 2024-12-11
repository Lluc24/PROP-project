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

public class CtrlPersistenciaSolucio {
    CtrlSolucions ctrlSolucions = null;
    public CtrlPersistenciaSolucio(CtrlSolucions cs){
        ctrlSolucions = cs;
    }

    /**
     * Metode per verificar si l'arxiu existeix i si es accesible.
     *
     * @param path  Ruta del arxiu.
     * @param nombre Nom del arxiu.
     * @return true si l'arxiu es valid; false en cas contrari.
     */
    public boolean esArxiuValid(String path, String nombre) {
        File archivo = new File(path, nombre);
        return archivo.exists() && archivo.isFile() && archivo.canRead();
    }

    /**
     * Metode per carregar dades des d'un arxiu si l'arxiu es valid.
     *
     * @param path  Ruta del arxiu.
     * @param nombre Nom del arxiu.
     * @return Contingut del arxiu com cadena de text.
     * @throws IOException si hi ha error al llegir l'arxiu.
     */
    public String cargarArchivo(String path, String nombre) throws IOException {
        if (!esArxiuValid(path, nombre)) {
            throw new IOException("L'arxiu no es valid o no es pot llegir.");
        }

        // Llegir contingut del arxiu
        String rutaCompleta = Paths.get(path, nombre).toString();
        return new String(Files.readAllBytes(Paths.get(rutaCompleta)));
    }

    /**
     * Metode per obtenir les dades procesades d'un arxiu.
     *
     * @param path  Ruta del arxiu.
     * @param nombre Nom del arxiu.
     */
    public void procesarDatosArchivo(String path, String nombre) throws IOException, NomSolucioNoValid, FormatInputNoValid {
        String contenido = cargarArchivo(path, nombre);
        // Dividimo el contingut en lineas
        String[] lineas = contenido.split("\\n");
        int index = 0;

        while (index < lineas.length) {
            // Si hi ha linea buida vol dir nova solucio
            if (lineas[index].isEmpty()) {
                index++;
                continue;
            }

            // Convertimos la línea a boolean usando Boolean.parseBoolean
            boolean modificada = Boolean.parseBoolean(lineas[index].trim());
            index++;

            // Leer la segunda línea como nomSolucio
            String nomSolucio = lineas[index].trim();
            index++;

            // Leer las siguientes líneas hasta encontrar una línea vacía
            ArrayList<ArrayList<String>> sol = new ArrayList<>();

            while (index < lineas.length && !lineas[index].isEmpty()) {
                // Dividir los elementos de la línea en una lista
                ArrayList<String> a = new ArrayList<>(Arrays.asList(lineas[index].trim().split(" ")));
                sol.add(a);
                index++;
            }

            // Llamar a carregaSolucio
            ctrlSolucions.carregaSolucio(modificada, nomSolucio, sol) ;
            //carregaSolucio(modificada, nomSolucio, sol);
            // Saltar línea vacía
            index++;
        }
    }

    public void guardarSolucio(String contingut, String path, String nomArxiu) {
        File archivo = new File(path, nomArxiu);
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(contingut);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
