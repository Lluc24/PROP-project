package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;

public abstract class Algorisme {
    Algorisme() {

    }

    public int[] solucionar(double[][] matriuSimilituds) throws FormatInputNoValid {

        int n = matriuSimilituds.length;
        boolean[][] matriuRestrConsec = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) matriuRestrConsec[i][j] = false;
        }
        return solucionar(matriuSimilituds, matriuRestrConsec);

    }

    public abstract int[] solucionar(double[][] matriuSimilituds, boolean[][] matriuRestrConsec) throws FormatInputNoValid;
}

