package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.NomSolucioNoValid;

import java.util.ArrayList;
import java.util.Collections;

public class Solucio {
    // Atributs
    protected ArrayList<ArrayList<String>> solucio;
    protected String nom;

    // Constructora
    public Solucio(ArrayList<String> s, String n, int p) throws FormatInputNoValid{
        if (p <= 0) {
            String missatge = "El numero de productes per prestatgeria ha de ser minim 1";
            throw new FormatInputNoValid(missatge);
        }
        else {
            this.nom = n;
            //Divideix la llista en prestatges
            ArrayList<ArrayList<String>> aux = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < s.size(); i++) {
                if (i % p == 0) {
                    ArrayList<String> prestatgeNou = new ArrayList<>();
                    prestatgeNou.add(s.get(i));
                    aux.add(prestatgeNou);
                } else {
                    aux.getLast().add(s.get(i));
                }
            }
            for (int i = 0; i < aux.size(); i++) {
                if (i % 2 != 0) {
                    Collections.reverse(aux.get(i));
                }
            }
            this.solucio = aux;
        }
    }

    //Constructora
    public Solucio(ArrayList<ArrayList<String>> s, String n) {
        this.nom = n;
        this.solucio = s;
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public ArrayList<ArrayList<String>> getSolucio() {
        return solucio;
    }

    //Metodes addicionals
    /**
     * @Class Solucio
     * Mostra el nom dels productes d'una solucio en ordre.
     * @author Eulalia Peiret Santacana
     */
    public void mostrarSolucio() {
        System.out.print(nom+": ");
        for (int i = 0; i < solucio.size(); i++) {
            for (int j = 0; j < solucio.get(i).size(); ++j) {
                if (j == 0) {
                    System.out.println();
                }
                System.out.print(solucio.get(i).get(j) + " ");
            }
        }
        if (solucio.size() == 0) System.out.println("la solucio no te cap producte");
        else System.out.println();
    }

    /**
     * @Class Solucio
     * @param nomProducte: el nom d'un producte
     * @return retorna true si hi ha un producte amb nom nomProducte a la solució. Fals en cas contrari.
     * @author Eulalia Peiret Santacana
     */
    public boolean trobarProducte(String nomProducte){
        for (int i = 0; i < solucio.size(); ++i) {
            for (String p : solucio.get(i)) {
                if (p.equals(nomProducte)) return true;
            }
        }
        return false;
    }
}