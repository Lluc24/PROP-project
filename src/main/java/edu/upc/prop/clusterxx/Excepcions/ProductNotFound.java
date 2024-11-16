package edu.upc.prop.clusterxx.Excepcions;

public class ProductNotFound extends Exception {

    public ProductNotFound(String s) {
        super("Error en producte" + s + ": producte no ha sigut trobat a cataleg");
    }


}
