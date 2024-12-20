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
 * Controlador abstracte de persistencia carregar i validar arxius.
 *
 * @author Eulalia Peiret
 */

public class CtrlPersistenciaGeneric {

    /**
     * Metode per verificar si l'arxiu existeix i si es accesible.
     *
     * @param path  Ruta del arxiu.
     * @param nombre Nom del arxiu.
     * @return true si l'arxiu es valid; false en cas contrari.
     */
    protected boolean esArxiuValid(String path, String nombre) {
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
    protected String carregarArxiu(String path, String nombre) throws IOException ,FormatInputNoValid {
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
    }

    /**
     * L'usuari vol guardar les dades del sistema a un arxiu.
     *
     * @param contingut totes les dades en el format correcte
     * @param path lloc on es vol guardar
     * @param nomArxiu nom del arxiu on es vol guardar
     */
    public void guardar(String contingut, String path, String nomArxiu) throws FormatInputNoValid {
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

    /**
     * Es vol saber si un directori es valid.
     *
     * @param path path del directori.
     * @return true si el directori es valid i s'hi pot escriure, false altrament.
     */
    protected boolean directoriCorrecte(String path) {
        File directori = new File(path);
        // Comprovar si el directori existeix, es valid i es pot escriure
        return (directori.exists() && directori.isDirectory() && directori.canWrite());
    }
}
