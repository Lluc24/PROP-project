import java.util.ArrayList;

public class AlgorismeGreedy extends Algorisme {

    //Índex del producte inicial des del qual comença l'algorisme
    private int producteInicial;
    //Nombre d'iteracions que ha de realitzar l'algorisme
    private int numIteracions;

    /**
     * Constructor de la classe AlgorismeGreedy
     * @param producteInicial L'índex del producte amb el qual comença l'algorisme. No pot ser negatiu, 0 o superior a la quantitat de productes del catàleg, com a precondició.
     * @param numIteracions El nombre d'iteracions que realitzarà l'algorisme. No pot ser negatiu, 0 o superior a la quantitat de productes del catàleg, com a precondició.
     */
    public AlgorismeGreedy(int producteInicial, int numIteracions) {
        this.producteInicial = producteInicial;
        this.numIteracions = numIteracions;
    }

    //Getters i setters per a l'atribut producteInicial
    public int getProducteInicial() {
        return producteInicial;
    }

    public void setProducteInicial(int producteInicial) {
        this.producteInicial = producteInicial;
    }

    //Getters i setters per a l'atribut numIteracions
    public int getNumIteracions() {
        return numIteracions;
    }

    public void setNumIteracions(int numIteracions) {
        this.numIteracions = numIteracions;
    }

    /**
     * Mètode que resol el problema utilitzant l'algorisme voraç
     * @param cataleg El catàleg de productes a analitzar
     * @return Un vector d'índexs de productes al catàleg que representa la millor configuració trobada
     */
    @Override
    public int[] resoldre(Cataleg cataleg) {
        int[] millorConfiguracio = new int[cataleg.num_prod_act()];
        double maxSimilitudTotal = -1.0;

        ArrayList<Producte> productes = cataleg.consultar_cataleg();

        //Bucle per realitzar les diferents iteracions
        for (int iteracio = 0; iteracio < numIteracions; iteracio++) {
            int indexInicial = producteInicial;
            ArrayList<Integer> configuracioActual = new ArrayList<>();
            double similitudTotal = 0.0;
            boolean[] visitats = new boolean[productes.size()];

            configuracioActual.add(indexInicial);
            visitats[indexInicial] = true;

            //Bucle per executar l'algorisme voraç i seleccionar el següent producte
            int actual = indexInicial;
            for (int i = 1; i < productes.size(); i++) {
                double maxSimilitud = -1.0;
                int següent = -1;

                // Busquem el següent producte amb la major similitud amb el producte actual
                for (int j = 0; j < productes.size(); j++) {
                    if (!visitats[j]) {
                        double similitud = cataleg.consultar_similitud_index(actual, j);
                        if (similitud > maxSimilitud) {
                            maxSimilitud = similitud;
                            següent = j;
                        }
                    }
                }

                //Afegim el producte següent si trobat
                if (següent != -1) {
                    configuracioActual.add(següent);
                    visitats[següent] = true;
                    similitudTotal += maxSimilitud;
                    actual = següent;
                }
            }

            if (similitudTotal > maxSimilitudTotal) {
                maxSimilitudTotal = similitudTotal;
                for (int i = 0; i < configuracioActual.size(); i++) {
                    millorConfiguracio[i] = configuracioActual.get(i);
                }
            }

            producteInicial = (indexInicial + 1) % productes.size();
        }

        return millorConfiguracio;
    }
}



