package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;

/**
 * Classe 'Algorisme'
 *
 * Serveix com a superclasse per la resta d'algorismes.
 * Tracta d'emmagatzemar mètodes i atributs comuns.
 *
 * @see AlgorismeBT
 * @see Aproximacio
 * @see AlgorismeGreedy
 *
 * @author Efrain Tito Cortés
 * @version 2,0
 *
 * <p><b>Informació:</b></p>
 * La classe Algorisme accepta un sistema de restriccions per calcular les solucions.
 * En el cas de AlgorismeBT, es prenen com a restriccions i pot haver-hi configuracions sense solució.
 * En el cas de Aproximacio i AlgorismeGreedy, es prenen com a prioritats i poden arribar a solucions que no les compleixin.
 */
public abstract class Algorisme {

    //Constructora
    /** Constructora */
    Algorisme() {

    }

    //Mètodes
    /**
     * Mètode abstracte per trobar la configuració de la prestatgeria.
     *
     * @param matriuSimilituds Matriu de similituds entre productes.
    */
    public int[] solucionar(double[][] matriuSimilituds) throws FormatInputNoValid {

        int n = matriuSimilituds.length;
        boolean[][] matriuRestrConsec = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) matriuRestrConsec[i][j] = false;
        }
        return solucionar(matriuSimilituds, matriuRestrConsec);

    }

    /**
     * Mètode abstracte per trobar la configuració de la prestatgeria, tenint en compte les restriccions o prioritats de consecutius.
     *
     * @param matriuSimilituds Matriu de similituds entre productes.
     * @param matriuRestrConsec Matriu de restriccions consecutives, on matriuRestrConsec[i][j] indica si els productes i i j no poden ser consecutius.
     * @return Un vector d'índexs de productes al catàleg que representa la millor configuració trobada.
     * @throws FormatInputNoValid Si amb la matriuRestrConsec no es pot trobar cap configuració vàlida.
     */
    public abstract int[] solucionar(double[][] matriuSimilituds, boolean[][] matriuRestrConsec) throws FormatInputNoValid;
}

