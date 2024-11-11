import java.util.ArrayList;

public class AlgorismeBT extends Algorisme {

    /**
     * Mètode principal per trobar la configuració òptima de la prestatgeria.
     * @param cataleg El catàleg de productes a analitzar
     * @return Un vector d'índexs de productes al catàleg que representa la millor configuració trobada
     */
    @Override
    public int[] resoldre(Cataleg cataleg) {
        ArrayList<Integer> configuracioActual = new ArrayList<>();
        boolean[] visitats = new boolean[cataleg.num_prod_act()];
        int[] millorConfiguracio = new int[cataleg.num_prod_act()];

        backtrack(cataleg, configuracioActual, visitats, 0.0, millorConfiguracio, 0.0);

        return millorConfiguracio;
    }

    /**
     * Mètode recursiu per explorar totes les configuracions possibles.
     * @param cataleg Catàleg de productes per calcular les similituds.
     * @param configuracioActual Configuració actual d'índexs de productes.
     * @param visitats Vector de seguiment per evitar repetir productes.
     * @param similitudAcumulada Similitud total acumulada fins al punt actual de la configuració.
     * @param millorConfiguracio Millor configuració trobada fins ara.
     * @param maxSimilitud La màxima similitud trobada fins ara.
     * @return El màxim de similitud trobat fins al moment per la configuració actual.
     */
    private double backtrack(Cataleg cataleg, ArrayList<Integer> configuracioActual, boolean[] visitats, double similitudAcumulada, int[] millorConfiguracio, double maxSimilitud) {
        int numProd = cataleg.num_prod_act();

        if (configuracioActual.size() == numProd) {
            //Sumem la similitud entre el primer i l'últim
            int primerIndex = configuracioActual.get(0);
            int ultimIndex = configuracioActual.get(configuracioActual.size() - 1);
            double similitudTotal = similitudAcumulada + cataleg.consultar_similitud_index(primerIndex, ultimIndex);

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

                //Si no és el primer producte, afegim la similitud amb l'anterior
                double novaSimilitudAcumulada = similitudAcumulada;
                if (configuracioActual.size() > 1) {
                    int anteriorIndex = configuracioActual.get(configuracioActual.size() - 2);
                    int actualIndex = configuracioActual.get(configuracioActual.size() - 1);
                    novaSimilitudAcumulada += cataleg.consultar_similitud_index(anteriorIndex, actualIndex);
                }

                maxSimilitud = backtrack(cataleg, configuracioActual, visitats, novaSimilitudAcumulada, millorConfiguracio, maxSimilitud);

                configuracioActual.remove(configuracioActual.size() - 1);
                visitats[i] = false;
            }
        }

        return maxSimilitud;
    }
}



