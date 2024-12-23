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
 * @version 4,1
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
     * @throws FormatInputNoValid Si algun dels paràmetres no és correcte segons la precondició.
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
     * @throws FormatInputNoValid Si algun dels paràmetres no és correcte segons la precondició.
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
     * @throws FormatInputNoValid Si algun dels paràmetres no és correcte segons la precondició.
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

        ArrayList<Integer> millorConfiguracioArrList = new ArrayList<>();
        int[] millorConfiguracio = new int[matriuSimilituds.length];

        if (numProd <= 0) {
            return millorConfiguracio;
        }

        if (producteInicial < 0 || producteInicial >= numProd) {
            throw new FormatInputNoValid("L'index del producte no pot ser negatiu o superior a la quantitat de productes al cataleg");
        }

        double maxSimilitudTotal = -1.0;
        boolean millorSolucioBruta = true;
        int indexInicial = producteInicial;
        int ites = numIteracions;
        if (ites > numProd) ites = numProd;

        //bucle per realitzar les diferents iteracions
        for (int iteracio = 0; iteracio < ites; iteracio++) {

            ArrayList<Integer> configuracioActual = new ArrayList<>();
            double similitudTotal = 0.0;
            boolean[] visitats = new boolean[numProd];

            configuracioActual.add(indexInicial);
            visitats[indexInicial] = true;
            boolean solucioBruta = false;


            //bucle per executar l'algorisme voraç i seleccionar el següent producte
            int actual = indexInicial;
            boolean continuar = true;
            int i = 1;

            while (i < numProd && continuar) {

                double maxSimilitudPref = -1.0;
                int properPref = -1;

                double maxSimilitudRest = -1.0;
                int properRest = -1;

                //Variables pel procés de selecció
                int numProdFase1 = Math.max(1, (int) Math.floor((numProd - configuracioActual.size()) / Math.E));

                //Fase 1
                int j = 0;
                int k = 0;
                while (k < numProdFase1) {
                    if (!visitats[j]) {

                        double similitud = matriuSimilituds[actual][j];

                        //no hi ha restricció
                        if (!matriuRestrConsec[actual][j]) {
                            if (similitud > maxSimilitudPref) {
                                properPref = j;
                                maxSimilitudPref = similitud;
                            }


                        } else { //hi ha restricció
                            if (similitud > maxSimilitudRest) {
                                properRest = j;
                                maxSimilitudRest = similitud;
                            }
                        }
                        ++k;
                    }
                    ++j;
                }

                //Fase 2
                boolean trobat = false;

                while (j < numProd && !trobat) {
                    if (!visitats[j]) {

                        double similitud = matriuSimilituds[actual][j];

                        if (!matriuRestrConsec[actual][j]) {
                            if (similitud > maxSimilitudPref) {

                                configuracioActual.add(j);
                                properPref = j;
                                visitats[j] = true;
                                similitudTotal += similitud;
                                actual = j;
                                trobat = true;
                            }

                        } else {
                            if (similitud > maxSimilitudRest) {
                                properRest = j;
                                maxSimilitudRest = similitud;
                            }
                        }
                    }
                    ++j;
                }

                //cap element preferit de la fase 2 és elegible
                if (!trobat) {

                    //hi ha element preferit a la fase 1
                    if (properPref != -1) {

                        configuracioActual.add(properPref);
                        visitats[properPref] = true;
                        similitudTotal += maxSimilitudPref;
                        actual = properPref;

                    } else { //no hi ha cap element preferit

                        configuracioActual.add(properRest);
                        visitats[properRest] = true;
                        similitudTotal += maxSimilitudRest;
                        actual = properRest;
                        solucioBruta = true;

                    }

                }

                //càlcul del valor màxim possible a partir del punt actual
                int productesRestants = numProd - configuracioActual.size();
                double maxPossible = similitudTotal + (productesRestants + 1) * 100.0;

                //Poda: si el màxim possible no supera la millor similitud trobada, deixem d'executar el cos
                if ((!solucioBruta && !millorSolucioBruta && maxPossible <= maxSimilitudTotal) || (solucioBruta && millorSolucioBruta && maxPossible <= maxSimilitudTotal) || (solucioBruta && !millorSolucioBruta)) {
                    continuar = false;
                }

                ++i;

            }



            //comparar iteració actual amb l'òptima fins al moment si està completa
            if (configuracioActual.size() == numProd) {

                //Relacionar l'últim element amb el primer
                similitudTotal += matriuSimilituds[configuracioActual.getFirst()][configuracioActual.getLast()];
                if (matriuRestrConsec[configuracioActual.getFirst()][configuracioActual.getLast()]) solucioBruta = true;


                if (!solucioBruta && millorSolucioBruta) {
                    maxSimilitudTotal = similitudTotal;
                    millorConfiguracioArrList = configuracioActual;
                    millorSolucioBruta = false;
                }

                else if (similitudTotal > maxSimilitudTotal && !(solucioBruta && !millorSolucioBruta)) {
                    maxSimilitudTotal = similitudTotal;
                    millorConfiguracioArrList = configuracioActual;
                }
            }

            //actualitzem el producte inicial per a la següent iteració
            indexInicial = (indexInicial + 1) % numProd;
        }

        for (int j = 0; j < millorConfiguracioArrList.size(); j++) {
            millorConfiguracio[j] = millorConfiguracioArrList.get(j);
        }

        return millorConfiguracio;
    }
}




