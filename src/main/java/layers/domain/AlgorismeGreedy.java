package layers.domain;
import java.util.ArrayList;
import layers.domain.excepcions.FormatInputNoValid;

/**
 * Classe 'AlgorismeGreedy'
 *
 * Algorisme voraç que troba una solució aproximada, seleccionant de manera iterativa els productes més similars entre ells.
 *
 * @see Algorisme
 *
 * @author Efrain Tito Cortés
 * @version 3,0
 *
 * <p><b>Informació:</b></p>
 * Per al mètode 'solucionar', cal passar com a paràmetre una matriu de similituds, on l'element [i][j]
 * representa la similitud entre el producte amb índex de catàleg 'i' i el producte amb índex de catàleg 'j'.
 * Els atributs de la classe indiquen per quin producte iniciar l'algorisme i les vegades que cal fer-ho, començant
 * les posteriors pels productes amb índexs consecutius.
 *
 * Nota: L'algorisme tracta de trobar una solució que compleixi les restriccions, però no té per què retornar sempre una solució que les compleixi.
 * Nota: Per tal d'optimitzar, l'algorisme assumeix que la similitud màxima entre dos productes és 100,0.
 */
public class AlgorismeGreedy extends Algorisme {

    //Atributs
    /** Índex del producte inicial des del qual comença l'algorisme */
    private int producteInicial;

    /** Nombre d'iteracions que ha de realitzar l'algorisme */
    private int numIteracions;

    //Mètodes

    /**
     * Constructor de la classe AlgorismeGreedy
     * @param producteInicial L'índex del producte amb el qual comença l'algorisme. No pot ser negatiu, o superior a la quantitat de productes al catàleg, com a precondició.
     * @param numIteracions El nombre d'iteracions que realitzarà l'algorisme. No pot ser negatiu o 0, com a precondició.
     */
    public AlgorismeGreedy(int producteInicial, int numIteracions) throws FormatInputNoValid {
        if (producteInicial < 0) {
            throw new FormatInputNoValid("L'index del producte inicial no pot ser negatiu");
        }
        if (numIteracions <= 0) {
            throw new FormatInputNoValid("El nombre d'iteracions ha de ser positiu i no zero");
        }
        this.producteInicial = producteInicial;
        this.numIteracions = numIteracions;
    }

    /**
     * Constructor per defecte de la classe AlgorismeGreedy, on producteInicial = 0 i numIteracions = 1.
     */
    public AlgorismeGreedy() {
        this.producteInicial = 0;
        this.numIteracions = 1;
    }

    /**
     * Retorna l'índex del producte inicial amb el qual comença l'algorisme.
     * @return El valor de l'atribut producteInicial, que representa l'índex del producte inicial.
     */
    public int getProducteInicial() {
        return producteInicial;
    }

    /**
     * Estableix l'índex del producte inicial amb el qual comença l'algorisme.
     * @param producteInicial L'índex del producte inicial que no pot ser negatiu ni superior a la quantitat de productes al catàleg. Aquesta és una precondició.
     */
    public void setProducteInicial(int producteInicial) throws FormatInputNoValid {

        if (producteInicial < 0) {
            throw new FormatInputNoValid("L'index del producte inicial no pot ser negatiu");
        }
        this.producteInicial = producteInicial;
    }

    /**
     * Retorna el nombre d'iteracions que l'algorisme ha de realitzar.
     * @return El valor de l'atribut numIteracions, que representa el nombre d'iteracions.
     */
    public int getNumIteracions() {
        return numIteracions;
    }

    /**
     * Estableix el nombre d'iteracions que l'algorisme ha de realitzar.
     * @param numIteracions El nombre d'iteracions que ha de ser un valor positiu no zero. Aquesta és una precondició.
     */
    public void setNumIteracions(int numIteracions) throws FormatInputNoValid {

        if (numIteracions <= 0) {
            throw new FormatInputNoValid("El nombre d'iteracions ha de ser positiu i no zero");
        }
        this.numIteracions = numIteracions;
    }

    /**
     * Mètode que resol el problema utilitzant l'algorisme voraç
     * @param matriuSimilituds Matriu de similituds entre productes, on matriuSimilituds[i][j] és la similitud entre els productes i i j.
     * @param matriuRestrConsec Matriu de restriccions de consecutius, on matriuRestrConsec[i][j] indica si els productes i i j no haurien de ser consecutius a la solució, si és possible.
     * @return Un vector d'índexs de productes al catàleg que representa la millor configuració trobada.
     */
    @Override
    public int[] solucionar(double[][] matriuSimilituds, boolean[][] matriuRestrConsec) throws FormatInputNoValid {

        int numProd = matriuSimilituds.length;

        int[] millorConfiguracio = new int[matriuSimilituds.length];

        if (numProd <= 0) {
            return millorConfiguracio;
        }

        if (producteInicial < 0 || producteInicial >= numProd) {
            throw new FormatInputNoValid("L'index del producte no pot ser negatiu o superior a la quantitat de productes al cataleg");
        }

        double maxSimilitudTotal = -1.0;
        //Diu si les
        boolean millorSolucioBruta = true;
        boolean solucioBruta = false;

        //bucle per realitzar les diferents iteracions
        for (int iteracio = 0; iteracio < numIteracions % numProd; iteracio++) {
            int indexInicial = producteInicial;
            ArrayList<Integer> configuracioActual = new ArrayList<>();
            double similitudTotal = 0.0;
            boolean[] visitats = new boolean[numProd];

            configuracioActual.add(indexInicial);
            visitats[indexInicial] = true;

            //bucle per executar l'algorisme voraç i seleccionar el següent producte
            int actual = indexInicial;
            boolean continuar = true;
            int i = 1;

            while (i < numProd && continuar) {

                double maxSimilitud = -1.0;
                int proper = -1;

                if (!solucioBruta) {
                    for (int j = 0; j < numProd; j++) {
                        if (!visitats[j] && !matriuRestrConsec[actual][j]) {

                            double similitud = matriuSimilituds[actual][j];
                            if (similitud > maxSimilitud) {
                                maxSimilitud = similitud;
                                proper = j;
                            }
                        }
                    }
                }

                else {
                    for (int j = 0; j < numProd; j++) {
                        if (!visitats[j]) {

                            double similitud = matriuSimilituds[actual][j];
                            if (similitud > maxSimilitud) {
                                maxSimilitud = similitud;
                                proper = j;
                            }
                        }
                    }
                }

                if (proper != -1) {
                    configuracioActual.add(proper);
                    visitats[proper] = true;
                    similitudTotal += maxSimilitud;
                    actual = proper;
                }

                else {
                    solucioBruta = true;
                    --i;
                }

                i++;
                //càlcul del valor màxim possible a partir del punt actual
                int productesRestants = numProd - configuracioActual.size();
                double maxPossible = similitudTotal + (productesRestants + 1) * 100.0;

                //Poda: si el màxim possible no supera la millor similitud trobada, deixem d'executar el cos
                if ((!solucioBruta && !millorSolucioBruta && maxPossible <= maxSimilitudTotal) || (solucioBruta && millorSolucioBruta && maxPossible <= maxSimilitudTotal) || (solucioBruta && !millorSolucioBruta)) {
                    continuar = false;
                }
            }

            if ((!solucioBruta && millorSolucioBruta) || (similitudTotal > maxSimilitudTotal)) {
                maxSimilitudTotal = similitudTotal;
                for (int j = 0; j < configuracioActual.size(); j++) {
                    millorConfiguracio[j] = configuracioActual.get(j);
                }
                if (!solucioBruta && millorSolucioBruta) millorSolucioBruta = false;
            }

            //actualitzem el producte inicial per a la següent iteració
            producteInicial = (indexInicial + 1) % numProd;
        }

        return millorConfiguracio;
    }
}




