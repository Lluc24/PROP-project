package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;

public abstract class Algorisme {
    Algorisme() {

    }

    public abstract int[] solucionar(double[][] matriuSimilituds) throws FormatInputNoValid;

    public abstract int[] solucionar(double[][] matriuSimilituds, boolean[][] matriuRestrConsec) throws FormatInputNoValid;
}
