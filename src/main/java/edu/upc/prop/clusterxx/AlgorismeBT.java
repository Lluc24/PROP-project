package edu.upc.prop.clusterxx;
import java.util.ArrayList;

/**
 * Classe 'AlgorismeBT'
 *
 * Algorisme de força bruta que troba la solució òptima mitjançant una estratègia de backtracking.
 * Aquesta classe utilitza una matriu de similituds per determinar les relacions entre els productes.
 *
 * @see Algorisme
 *
 * @author Efrain Tito Cortés
 * @version 2,0
 *
 * <p><b>Informació:</b></p>
 * Per al mètode 'solucionar', cal passar com a paràmetre una matriu de similituds, on l'element
 * [i][j] representa la similitud entre el producte amb índex de catàleg 'i' i el producte amb índex de catàleg 'j'.
 */
public class AlgorismeBT extends Algorisme {

    /**
     * Mètode principal per trobar la configuració òptima de la prestatgeria.
     * @param matriuSimilituds Matriu de similituds entre productes, on matriuSimilituds[i][j] és la similitud entre els productes i i j.
     * @return Un vector d'índexs de productes al catàleg que representa la millor configuració trobada.
     */
    @Override
    public int[] solucionar(double[][] matriuSimilituds) {

        ArrayList<Integer> configuracioActual = new ArrayList<>();
        boolean[] visitats = new boolean[matriuSimilituds.length];
        int[] millorConfiguracio = new int[matriuSimilituds.length];

        backtrack(matriuSimilituds, configuracioActual, visitats, 0.0, millorConfiguracio, 0.0);

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
     * @return El màxim de similitud trobat fins al moment per la configuració actual.
     */
    private double backtrack(double[][] matriuSimilituds, ArrayList<Integer> configuracioActual, boolean[] visitats, double similitudAcumulada, int[] millorConfiguracio, double maxSimilitud) {
        int numProd = matriuSimilituds.length;

        //comprovem si la configuració actual està completa
        if (configuracioActual.size() == numProd) {
            //sumem la similitud entre el primer i l'últim producte
            int primerIndex = configuracioActual.getFirst();
            int ultimIndex = configuracioActual.getLast();
            double similitudTotal = similitudAcumulada + matriuSimilituds[primerIndex][ultimIndex];

            if (similitudTotal > maxSimilitud) {
                maxSimilitud = similitudTotal;
                for (int i = 0; i < numProd; i++) {
                    millorConfiguracio[i] = configuracioActual.get(i);
                }
            }
            return maxSimilitud;
        }

        for (int i = 0; i < numProd; i++) {
            if (!visitats[i]) {
                visitats[i] = true;
                configuracioActual.add(i);

                //si no és el primer, afegim la similitud amb l'anterior
                double novaSimilitudAcumulada = similitudAcumulada;
                if (configuracioActual.size() > 1) {
                    int anteriorIndex = configuracioActual.get(configuracioActual.size() - 2);
                    int actualIndex = configuracioActual.getLast();
                    novaSimilitudAcumulada += matriuSimilituds[anteriorIndex][actualIndex];
                }

                maxSimilitud = backtrack(matriuSimilituds, configuracioActual, visitats, novaSimilitudAcumulada, millorConfiguracio, maxSimilitud);

                configuracioActual.removeLast();
                visitats[i] = false;
            }
        }

        return maxSimilitud;
    }
}




