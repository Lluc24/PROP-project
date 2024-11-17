package layers.domain;

import java.util.ArrayList;

public class Solucio {
    // Atributs
    protected ArrayList<String> solucio;
    protected Algorisme algorisme;
    protected String nom;

    // Constructor
    public Solucio(ArrayList<String> s, Algorisme a, String n) {
        this.solucio = s;
        this.algorisme = a;
        this.nom = n;
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

    public Algorisme getAlgorisme() {
        return algorisme;
    }

    public ArrayList<String> getSolucio() {
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
            System.out.print(solucio.get(i));

            if (i != solucio.size() - 1) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
        if (solucio.size() == 0) System.out.println("la solucio no te cap producte");
    }

    /**
     * @Class Solucio
     * @param x: el nom d'un producte
     * @return retorna true si hi ha un producte amb nom x a la solució. Fals en cas contrari.
     * @author Eulalia Peiret Santacana
     */
    public boolean trobarProducte(String x){
        for (String p : solucio){
            if (p.equals(x)) return true;
        }
        return false;
    }
}