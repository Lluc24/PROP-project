package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;

import java.util.*;

public class SolucioModificada extends Solucio {

    // Constructor
    public SolucioModificada(ArrayList<ArrayList<String>> s, String n) throws FormatInputNoValid {
        super(s, n);
        this.solucio = s;
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
    public void intercanvia (String prod1, String prod2) throws IntercanviNoValid{
        if (prod1.equals(prod2)) {
            String missatge = "No pots intercanviar un producte amb ell mateix";
            throw new IntercanviNoValid(missatge);
        }
        int index1i = -1;
        int index2i = -1;
        int index2j = -1;
        int index1j = -1;
        // Busca els idx dels prod a solucio
        for (int i = 0; i < solucio.size() && (index1i == -1 || index2i == -1); i++) {
            for (int j = 0; j < solucio.get(i).size(); ++j) {
                if (solucio.get(i).get(j).equals(prod1)) {
                    index1i = i;
                    index1j = j;
                } else if (solucio.get(i).get(j).equals(prod2)) {
                    index2i = i;
                    index2j = j;
                }
            }
        }

        // Verifica que hagi trobat els 2 productes
        if (index1i != -1 && index2i != -1) {
            String producte1 = solucio.get(index1i).get(index1j);
            solucio.get(index1i).set(index1j, solucio.get(index2i).get(index2j));
            solucio.get(index2i).set(index2j, producte1);
        }
        else {
            String missatge = "No existeix algun dels dos productes en la solucio";
            throw new IntercanviNoValid(missatge);
        }
    }
}
