package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;

import java.util.*;

public class SolucioModificada extends Solucio {

    /**
     * Funcio constructora de la classe.
     *
     * @param s Matriu que representa la solucio.
     * @param n Nom de la solucio.
     * @throws FormatInputNoValid Si la matriu proporcionada no es correcta, salta la excepcio.
     */
    public SolucioModificada(ArrayList<ArrayList<String>> s, String n) throws FormatInputNoValid {
        super(s, n);
        this.solucio = s;
        this.nom = n;
    }

    /**
     * Intercanvia els productes prod1 i prod2 si els dos estan en la solució
     *
     * @param prod1 nom del primer producte
     * @param prod2 nom del segon producte
     */
    public void intercanvia (String prod1, String prod2) throws IntercanviNoValid {
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
        String producte1 = solucio.get(index1i).get(index1j);
        solucio.get(index1i).set(index1j, solucio.get(index2i).get(index2j));
        solucio.get(index2i).set(index2j, producte1);
    }

    /**
     * Intercanvia els productes amb els index indicats si els dos estan en la solució
     *
     * @param index1i fila del primer poroducte
     * @param index1j columna del primer poroducte
     * @param index2i fila del segon poroducte
     * @param index2j columna del segon poroducte
     * @throws IntercanviNoValid si els productes en les posicions indicades no es poden intercanviar per algun motiu
     */
    public void intercanvia (int index1i, int index1j, int index2i,int index2j) throws IntercanviNoValid{
        if (index1i == index2i && index1j == index2j) {
            String missatge = "No pots intercanviar un producte amb ell mateix";
            throw new IntercanviNoValid(missatge);
        }
        else if(!(this.existeixPosicio(index1i,index1j)) || !(this.existeixPosicio(index2i,index2j))){
            String missatge = "No hi ha cap producte en les posicions indicades";
            throw new IntercanviNoValid(missatge);
        }
        String producte1 = solucio.get(index1i).get(index1j);
        solucio.get(index1i).set(index1j, solucio.get(index2i).get(index2j));
        solucio.get(index2i).set(index2j, producte1);
    }
}
