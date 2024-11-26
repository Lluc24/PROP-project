package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;

import java.util.*;

public class SolucioModificada extends Solucio {

    // Constructor
    public SolucioModificada(ArrayList<String> s, String n, int p) throws FormatInputNoValid {
        super(s, n, validateProducts(p));
        this.solucio = s;
        this.nom = n;
        this.prodPrestatge = p;
    }

    private static int validateProducts(int p) throws FormatInputNoValid {
        if (p <= 0) {
            String missatge = "El numero de productes per prestatgeria ha de ser minim 1";
            throw new FormatInputNoValid(missatge);
        }
        return p;
    }

    /**
     *
     * @Class SolucioModificada
     * Intercanvia els productes prod1 i prod2 si els dos estan en la solució
     * @param prod1
     * @param prod2
     * @author Eulalia Peiret Santacana
     */
    public void intercanvia (String prod1, String prod2) throws IntercanviNoValid{
        if (prod1.equals(prod2)) {
            String missatge = "No pots intercanviar un producte amb ell mateix";
            throw new IntercanviNoValid(missatge);
        }
        int index1 = -1;
        int index2 = -1;
        // Busca els idx dels prod a solucio
        for (int i = 0; i < solucio.size() && (index1 == -1 || index2 == -1); i++) {
            if (solucio.get(i).equals(prod1)) {
                index1 = i;
            } else if (solucio.get(i).equals(prod2)) {
                index2 = i;
            }
        }

        // Verifica que hagi trobat els 2 productes
        if (index1 != -1 && index2 != -1) {
            String aux = solucio.get(index1);
            solucio.set(index1, solucio.get(index2));
            solucio.set(index2, aux);
        }
        else {
            String missatge = "No existeix algun dels dos productes en la solucio";
            throw new IntercanviNoValid(missatge);
        }
    }
}
