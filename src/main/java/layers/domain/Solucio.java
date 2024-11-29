package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.NomSolucioNoValid;

import java.util.ArrayList;

public class Solucio {
    // Atributs
    protected ArrayList<String> solucio;
    protected String nom;
    protected int prodPrestatge;

    // Constructor
    public Solucio(ArrayList<String> s, String n, int p) throws FormatInputNoValid{
        if (p <= 0) {
            String missatge = "El numero de productes per prestatgeria ha de ser minim 1";
            throw new FormatInputNoValid(missatge);
        }
        else {
            this.solucio = s;
            this.nom = n;
            this.prodPrestatge = p;
        }
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }


    public ArrayList<String> getSolucio() {
        return solucio;
    }

    public int getProdPrestatge() {
        return prodPrestatge;
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
            if (i%prodPrestatge == 0) {
                System.out.println();
                System.out.println("------------------------------------------");
                System.out.print("| ");
            }
            System.out.print(solucio.get(i));

            if (i != solucio.size() - 1) {
                System.out.print(" | ");
            } else {
                System.out.println(" |");
                System.out.println("------------------------------------------");
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