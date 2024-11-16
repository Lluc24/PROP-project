package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.Excepcions.FormatInputNoValid;

public abstract class Algorisme {
    Algorisme() {

    }

    public abstract int[] solucionar(double[][] matriuSimilituds) throws FormatInputNoValid;
}
