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
    public String carregarArxiu(String path, String nombre) throws IOException ,FormatInputNoValid {
        if (!esArxiuValid(path, nombre)) {
            throw new FormatInputNoValid("L'arxiu '" +nombre+ "' no es valid o no es pot llegir.");
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
    public void processarDadesArxiu(String path, String nombre) throws NomSolucioNoValid, FormatInputNoValid {
        if(!directoriCorrecte(path)){
            String missatge = "El path '" +path+ "' no es correcte";
            throw new FormatInputNoValid(missatge);
        }
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
            //carregaSolucio(modificada, nomSolucio, sol);
            // Saltar línea vacía
            index++;
        }
    }

    /**
     * L'usuari vol guardar les solucions del sistema a un arxiu
     * @param contingut totes les dades de les solucions en el format correcte
     * @param path lloc on es vol guardar
     * @param nomArxiu nom del arxiu on es vol guardar
     */
    public void guardarSolucio(String contingut, String path, String nomArxiu) throws FormatInputNoValid {
        if(!directoriCorrecte(path)){
            String missatge = "El path '" +path+ "' no es correcte";
            throw new FormatInputNoValid(missatge);
        }
        File archivo = new File(path, nomArxiu);
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(contingut);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public boolean directoriCorrecte(String path) {
        File directori = new File(path);
        // Comprovar si el directori existeix, es valid i es pot escriure
        return (directori.exists() && directori.isDirectory() && directori.canWrite());
    }

}
