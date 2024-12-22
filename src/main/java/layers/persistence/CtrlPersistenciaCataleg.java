package layers.persistence;

import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.excepcions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe 'CtrlPersistenciaCataleg'
 *
 * Controlador de persistència per al catàleg que gestiona la càrrega i processament de dades des d'un arxiu.
 * Aquesta classe hereta de 'CtrlPersistenciaGeneric' i utilitza el controlador 'CtrlCatalegAmbRestriccions'
 * per a carregar les dades de productes, similituds i restriccions. L'algorisme de processament divideix
 * el contingut de l'arxiu en diferents fases: productes, similituds i restriccions.
 *
 * @see CtrlPersistenciaGeneric
 * @see CtrlCatalegAmbRestriccions
 *
 * @author Efrain Tito Cortés
 * @version 2,2
 *
 * <p><b>Informació:</b></p>
 * El mètode 'processarDadesArxiu' carrega un arxiu que conté dades sobre productes, la matriu de similituds
 * i la matriu de restriccions. El format de l'arxiu ha de ser el següent:
 * - La primera secció conté els noms dels productes (un per línia).
 * - La segona secció conté la matriu de similituds entre els productes.
 * - La tercera secció conté la matriu de restriccions (valors 0 o 1).
 *
 * El mètode també valida que les matrius siguin quadrades i que els valors de similitud estiguin dins el rang [0, 100].
 *
 * <p><b>Excepcions:</b></p>
 * - <b>NomSolucioNoValid</b>: L'arxiu no té el format adequat o no es poden carregar les dades.
 * - <b>FormatInputNoValid</b>: Els valors a l'arxiu no són vàlids, com matrius no quadrades o similituds fora de rang.
 */
public class CtrlPersistenciaCataleg extends CtrlPersistenciaGeneric {

    //Atributs
    /** Instància del controlador de catàleg amb restriccions. */
    private static CtrlCatalegAmbRestriccions ctrlCat = null;

    //Mètodes
    /** Contructora de CtrlPersistenciaCataleg.
     *
     * @param cs Instància del controlador de catàleg amb restriccions.
     */
    public CtrlPersistenciaCataleg(CtrlCatalegAmbRestriccions cs){
        ctrlCat = cs;
    }

    /**
     * Metode per obtenir les dades procesades d'un arxiu.
     *
     * @param path  Ruta de l'arxiu.
     * @param nombre Nom de  l'arxiu.
     * @throws NomSolucioNoValid Heretada, pero no es llença.
     * @throws FormatInputNoValid Amb el missatge d'error corresponent si hi ha alguna dada incorrecta a l'arxiu.
     */
    @Override
    public void processarDadesArxiu(String path, String nombre) throws NomSolucioNoValid, FormatInputNoValid {
        super.processarDadesArxiu(path, nombre);

        String contingut = null;
        String[] productes = null;
        double[][] similituds = null;
        int[][] restriccions = null;

        try {
            contingut = importar(path, nombre);
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
                    if (!prod.matches("[a-zA-Z0-9]+")) throw new FormatInputNoValid("Els noms dels productes contenen caracters que no son lletres o numeros. Linia: " + (index + 1));
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
                        try {
                            double valor = Double.parseDouble(valors[j]);
                            if (valor < 0 || valor > 100) {
                                throw new FormatInputNoValid("El valor '" + valors[j] + "' de la matriu de similituds no esta entre 0 i 100. Linia: " + (index + 1));
                            }
                            similituds[i][j] = valor;
                        } catch (NumberFormatException e) {
                            throw new FormatInputNoValid("El valor '" + valors[j] + "' no es pot convertir a un numero. Linia: " + (index + 1));
                        }
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
                        throw new FormatInputNoValid("La matriu de restriccions no es quadrada.");
                    }

                    for (int j = 0; j < numProductes; j++) {
                        try {
                            int valor = Integer.parseInt(valors[j]);
                            if (valor != 0 && valor != 1) {
                                throw new FormatInputNoValid("El valor '" + valors[j] + "' de la matriu de restriccions no es 0 o 1. Linia: " + (index + 1));
                            }
                            restriccions[i][j] = valor;
                        } catch (NumberFormatException e) {
                            throw new FormatInputNoValid("El valor '" + valors[j] + "' no es pot convertir a un enter. Linia " + (index + 1));
                        }
                    }
                    index++;
                }
            }
        }

        if (productes != null) {
            System.out.println("Productes: " + Arrays.toString(productes));
            System.out.println("Matriu de similituds:");
            for (double[] fila : similituds) {
                System.out.println(Arrays.toString(fila));
            }
            System.out.println("Matriu de restriccions:");
            for (int[] fila : restriccions) {
                System.out.println(Arrays.toString(fila));
            }
            ctrlCat.carregaCataleg(productes, similituds, restriccions);
        } else {
            System.out.println("Sense dades.");
        }

    }
}
