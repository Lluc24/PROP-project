package layers.domain;
import java.util.ArrayList;
import layers.domain.excepcions.FormatInputNoValid;

/**
 * Classe 'AlgorismeBT'
 *
 * Algorisme de força bruta que troba la solució òptima mitjançant una estratègia de backtracking.
 * Aquesta classe utilitza una matriu de similituds per determinar les relacions entre els productes.
 *
 * @see Algorisme
 *
 * @author Efrain Tito Cortés
 * @version 3,0
 *
 * <p><b>Informació:</b></p>
 * Per al mètode 'solucionar', cal passar com a paràmetre una matriu de similituds, on l'element
 * [i][j] representa la similitud entre el producte amb índex de catàleg 'i' i el producte amb índex de catàleg 'j'.
 * Nota: Per tal d'optimitzar, l'algorisme assumeix que la similitud màxima entre dos productes és 100,0.
 */
public class AlgorismeBT extends Algorisme {

    /**
     * Constructor de la classe AlgorismeBT
     */
    public AlgorismeBT() {

    }

    /**
     * Comprova si la configuració no està plena.
     *
     * @param millorConfiguracio Representa la millor configuració trobada.
     * @return True si encara hi ha espais no ocupats a la configuració (és a dir, és una solució incompleta), false en cas contrari.
     */
    private boolean noPle(int[] millorConfiguracio) {

        if (millorConfiguracio[millorConfiguracio.length - 1] != 0) return false;

        if (millorConfiguracio.length == 1) return false;

        for (int i = millorConfiguracio.length - 2; i >= 0; --i) {
            if (millorConfiguracio[i] == 0) return true;
        }

        return false;
    }

    /**
     * Mètode principal per trobar la configuració òptima de la prestatgeria.
     * @param matriuSimilituds Matriu de similituds entre productes, on matriuSimilituds[i][j] és la similitud entre els productes i i j.
     * @param matriuRestrConsec Matriu de restriccions de consecutius, on matriuRestrConsec[i][j] indica si els productes i i j no poden ser consecutius a la solució.
     * @return Un vector d'índexs de productes al catàleg que representa la millor configuració trobada.
     */
    @Override
    public int[] solucionar(double[][] matriuSimilituds, boolean[][] matriuRestrConsec) throws FormatInputNoValid {

        int[] millorConfiguracio = new int[matriuSimilituds.length];

        if (matriuSimilituds.length <= 0) {
            return millorConfiguracio;
        }

        ArrayList<Integer> configuracioActual = new ArrayList<>();
        boolean[] visitats = new boolean[matriuSimilituds.length];

        backtrack(matriuSimilituds, configuracioActual, visitats, 0.0, millorConfiguracio, 0.0, matriuRestrConsec);


        if (noPle(millorConfiguracio) || matriuRestrConsec[millorConfiguracio[0]][millorConfiguracio[millorConfiguracio.length - 1]]) {
            throw new FormatInputNoValid("No hi ha una solucio valida amb les restriccions actuals");
        }

        return millorConfiguracio;
    }

    /**
     * Mètode recursiu per calcular totes les configuracions possibles.
     * @param matriuSimilituds Matriu de similituds entre productes.
     * @param configuracioActual Configuració actual d'índexs de productes.
     * @param visitats Vector de seguiment per evitar repetir productes.
     * @param similitudAcumulada Similitud total acumulada fins al punt actual de la configuració.
     * @param millorConfiguracio Millor configuració trobada fins ara.
     * @param maxSimilitud La màxima similitud trobada fins ara.
     * @param matriuRestrConsec Matriu de restriccions de consecutius, on matriuRestrConsec[i][j] indica si els productes i i j no poden ser consecutius a la solució.
     * @return El màxim de similitud trobat fins al moment per la configuració actual.
     */
    private double backtrack(double[][] matriuSimilituds, ArrayList<Integer> configuracioActual, boolean[] visitats, double similitudAcumulada, int[] millorConfiguracio, double maxSimilitud, boolean[][] matriuRestrConsec) {
        int numProd = matriuSimilituds.length;

        //comprovem si la configuració actual està completa
        if (configuracioActual.size() == numProd) {
            //sumem la similitud entre el primer i l'últim producte
            int primerIndex = configuracioActual.getFirst();
            int ultimIndex = configuracioActual.getLast();
            double similitudTotal = similitudAcumulada + matriuSimilituds[primerIndex][ultimIndex];

            if (matriuRestrConsec[configuracioActual.getLast()][configuracioActual.getFirst()]) {
                similitudTotal = -1.0;
            }

            if (similitudTotal > maxSimilitud) {
                maxSimilitud = similitudTotal;
                for (int i = 0; i < numProd; i++) {
                    millorConfiguracio[i] = configuracioActual.get(i);
                }
            }
            return maxSimilitud;
        }

        //Poda per optimalitat: calculem el màxim possible des del punt actual
        int productesRestants = numProd - configuracioActual.size();
        double maxPossible = similitudAcumulada + (productesRestants + 1) * 100.0; //+1, donat que l'últim es relaciona amb el primer
        if (maxPossible <= maxSimilitud) {
            return maxSimilitud; //podem descartar aquesta branca
        }

        for (int i = 0; i < numProd; i++) {
            if (!visitats[i]) {

                double novaSimilitudAcumulada = similitudAcumulada;

                //configuracioActual no és buida
                if (!configuracioActual.isEmpty()) {
                    int anteriorIndex = configuracioActual.getLast();
                    if (!matriuRestrConsec[anteriorIndex][i]) {
                        novaSimilitudAcumulada += matriuSimilituds[anteriorIndex][i];
                        visitats[i] = true;
                        configuracioActual.add(i);

                        maxSimilitud = backtrack(matriuSimilituds, configuracioActual, visitats, novaSimilitudAcumulada, millorConfiguracio, maxSimilitud, matriuRestrConsec);

                        configuracioActual.removeLast();
                        visitats[i] = false;

                    }


                //configuracioActual és buida
                } else {

                    visitats[i] = true;
                    configuracioActual.add(i);

                    maxSimilitud = backtrack(matriuSimilituds, configuracioActual, visitats, novaSimilitudAcumulada, millorConfiguracio, maxSimilitud, matriuRestrConsec);

                    configuracioActual.removeLast();
                    visitats[i] = false;

                }
            }

        }

        return maxSimilitud;
    }
}




