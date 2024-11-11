package edu.upc.prop.clusterxx;

import java.util.ArrayList;

public class Solucio {
    // Atributs
    private ArrayList<Producte> solucio;
    private Algorisme algorisme;
    private String nom;

    // Constructor
    public Solucio(ArrayList<Producte> s, Algorisme a, String n) {
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

    public ArrayList<Producte> getSolucio() {
        return solucio;
    }


    //Metodes addicionals
    /**
     * @Class Solucio
     * Mostra el nom dels productes d'una solucio en ordre.
     * @author Eulalia Peiret Santacana
     */
    public void mostrarSolucio() {
        for (int i = 0; i < solucio.size(); i++) {
            Producte p = solucio.get(i);  // Obtener el producto en la posición i
            System.out.print(p.getNom());

            if (i != solucio.size() - 1) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    /**
     * @Class Solucio
     * @param x: el nom d'un producte
     * @return retorna true si hi ha un producte amb nom x a la solució. Fals en cas contrari.
     * @author Eulalia Peiret Santacana
     */
    public boolean trobarProducte(String x){
        for (Producte p : solucio){
            if (p.getNom().equals(x)) return true;
        }
        return false;
    }

}