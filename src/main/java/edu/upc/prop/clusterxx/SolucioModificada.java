package edu.upc.prop.clusterxx;

import java.util.*;

public class SolucioModificada extends Solucio {

    // Constructor
    public SolucioModificada(ArrayList<Producte> s, Algorisme a, String n) {
        super(s, a, n);
        this.solucio = s;
        this.algorisme = a;
        this.nom = n;
    }

    /**
     *
     * @Class SolucioModificada
     * Intercanvia els productes prod1 i prod2 si els dos estan en la solució
     * @param prod1
     * @param prod2
     * @author Eulalia Peiret Santacana
     */
    public void intercanvia(String prod1, String prod2){
        if (prod1.equals(prod2)) {
            System.out.println("SolucioModificada: error no pots intercanviar dos productes iguals");
            return;
        }
        int index1 = -1;
        int index2 = -1;
        // Busca els idx dels prod a solucio
        for (int i = 0; i < solucio.size() && (index1 == -1 || index2 == -1); i++) {
            if (solucio.get(i).getNom().equals(prod1)) {
                index1 = i;
            } else if (solucio.get(i).getNom().equals(prod2)) {
                index2 = i;
            }
        }

        // Verifica que hagi trobat els 2 productes
        if (index1 != -1 && index2 != -1) {
            Producte aux = solucio.get(index1);
            solucio.set(index1, solucio.get(index2));
            solucio.set(index2, aux);
        }
        else System.out.println("SolucioModificada: error no existeix algun dels dos productes");
    }
}
