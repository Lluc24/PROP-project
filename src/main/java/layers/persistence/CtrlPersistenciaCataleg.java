package layers.persistence;

import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.excepcions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CtrlPersistenciaCataleg extends CtrlPersistenciaGeneric {
    private static CtrlCatalegAmbRestriccions ctrlCat = null;
    public CtrlPersistenciaCataleg(CtrlCatalegAmbRestriccions cs){
        ctrlCat = cs;
    }

    /**
     * Metode per obtenir les dades procesades d'un arxiu.
     *
     * @param path  Ruta de l'arxiu.
     * @param nombre Nom de  l'arxiu.
     */
    @Override
    public void processarDadesArxiu(String path, String nombre) throws NomSolucioNoValid, FormatInputNoValid {
        super.processarDadesArxiu(path, nombre);

        String contingut = null;
        String[] productes = null;
        double[][] similituds = null;
        int[][] restriccions = null;

        try {
            contingut = carregarArxiu(path, nombre);
        } catch (IOException e) {
            throw new FormatInputNoValid("L'arxiu '" + nombre + "' falla.");
        }

        //dividim el contingut en linies
        String[] linies = contingut.split("\\n");
        int index = 0;

        int fase = 1; // 1: productes, 2: similituds, 3: restriccions
        int numProductes = 0;

        while (index < linies.length) {
            if (linies[index].trim().isEmpty()) {
                index++;
                continue;
            }

            if (fase == 1) {

                ArrayList<String> producteList = new ArrayList<>();
                while (index < linies.length && !linies[index].trim().isEmpty()) {
                    String prod = linies[index].trim();
                    if (!prod.matches("[a-zA-Z0-9]+")) throw new FormatInputNoValid("Els noms dels productes contenen caracters que no son lletres o numeros.");
                    producteList.add(prod);
                    index++;
                }

                //de llista a vector
                numProductes = producteList.size();
                productes = producteList.toArray(new String[0]);

                fase = 2; //canviem a similituds

            } else if (fase == 2) {
                //matriu de similituds
                similituds = new double[numProductes][numProductes];

                for (int i = 0; i < numProductes; i++) {
                    if (index >= linies.length || linies[index].trim().isEmpty()) {
                        throw new FormatInputNoValid("Les dimensions de la matriu de similituds fallen.");
                    }
                    String[] valors = linies[index].trim().split("\\s+");
                    if (valors.length != numProductes) {
                        throw new FormatInputNoValid("La matriu de similituds no es quadrada.");
                    }

                    for (int j = 0; j < numProductes; j++) {
                        double valor = Double.parseDouble(valors[j]);
                        if (valor < 0 || valor > 100) {
                            throw new FormatInputNoValid("El valor de la matriu de similituds no esta entre 0 i 100.");
                        }
                        similituds[i][j] = valor;
                    }
                    index++;
                }

                fase = 3; //canviem a restriccions

            } else if (fase == 3) {
                //matriu de restriccions
                restriccions = new int[numProductes][numProductes];

                for (int i = 0; i < numProductes; i++) {
                    if (index >= linies.length || linies[index].trim().isEmpty()) {
                        throw new FormatInputNoValid("Les dimensions de la matriu de similituds fallen.");
                    }
                    String[] valors = linies[index].trim().split("\\s+");
                    if (valors.length != numProductes) {
                        throw new FormatInputNoValid("La matriu de restriccions no és quadrada.");
                    }

                    for (int j = 0; j < numProductes; j++) {
                        int valor = Integer.parseInt(valors[j]);
                        if (valor != 0 && valor != 1) {
                            throw new FormatInputNoValid("Els valors de la matriu de restriccions nomes poden ser 0 o 1.");
                        }
                        restriccions[i][j] = valor;
                    }
                    index++;
                }
            }
        }

        ctrlCat.carregaCataleg(productes, similituds, restriccions);
    }
}
