/*package layers.persistence;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.NomSolucioNoValid;

import java.io.IOException;

public class TestCtrlPersistenciaSolucio {
    public static void main(String[] args) {
        // Crear una instància de la classe CtrlPersistenciaSolucio
        CtrlPersistenciaSolucio ctrl = new CtrlPersistenciaSolucio();

        // Ruta i nom d'un arxiu de prova
        String path = "/home/lali/Escritorio/q5/prop/"; // Substitueix amb el directori correcte
        String nombre = "test1.txt";    // Substitueix amb el nom correcte

        try {
            // Provar si el fitxer és vàlid
            boolean esValido = ctrl.esArchivoValido(path, nombre);
            System.out.println("El fitxer és vàlid: " + esValido);

            if (esValido) {
                // Carregar el contingut del fitxer
                String contenido = ctrl.cargarArchivo(path, nombre);
                System.out.println("Contingut del fitxer:\n" + contenido);

                // Processar les dades del fitxer
                try {
                    ctrl.procesarDatosArchivo(path, nombre);
                } catch (NomSolucioNoValid e) {
                    System.err.println(e.getMessage());
                } catch (FormatInputNoValid e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("S'ha produït un error: " + e.getMessage());
        }
    }
}*/
