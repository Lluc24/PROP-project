package edu.upc.prop.clusterxx;

import java.util.ArrayList;


/**
 * @Class Producte
 * @Description Representa un producte del sistema, amb nom, llista de similituts
 * i index que representa la seva posicio al Catalge
 * @see Cataleg
 * @author Alejandro Lorenzo Navarro
 * @version 2.2
 */
public class Producte {

    /** Index que concideix amb index on es troba el producte dins de cataleg*/
    private int index;
    /** String que representa el nom unic que se li ha donat al producte */
    private String nom;
    /** ArrayList que representa les similituds de aquest producte amb la resta
     * cada similitud amb un producte es troba a index corresponent al index*/
    private ArrayList<Double> llista_Similituds;

    /**
     * @param index El index a Cataleg_Productes de la classe Cataleg on es troba el producte
     * @param nom El nom de producte a crear
     * @param llista Una llista amb totes les similituds del productes
     * Descripció: Es crea una nova instancia de productes
     */
    public Producte(int index, String nom, ArrayList<Double> llista) {
        this.index = index;
        this.nom = nom;
        this.llista_Similituds = llista;
    }

    /**
     * 
     * @param index_prod El index d'un producte al cual es vol la similitud amb el producte que crida la funcio
     * @return Retorna el valor de la similitud This.Producte = Producte.index=index_prod
     */
    public double get_simil_prod(int index_prod) {
        return llista_Similituds.get(index_prod);
    }

    public String getNom() {return nom;}
    public int getIndex() {return index;}
    public ArrayList<Double> getSimilituds() {return llista_Similituds;}
    public void setNom(String nom) {this.nom = nom;}
    public void setIndex(int index) {this.index = index;}
    public void setSimilituds(ArrayList<Double> similituds) {this.llista_Similituds = similituds;}

    /**
     *
     * @param index Index de producte dins de llista_similituds
     * @param simi Similitud nova
     */
    public void addSimiProd(int index, Double simi) {
        llista_Similituds.add(index, simi);
    }

    /**
     *
     * @param index_out Index de la similitud a eliminar
     */
    public void remSimiProd(int index_out) {
        llista_Similituds.remove(index_out);
    }

    /**
     *
     * @param index Index de producte dins de llista_similituds
     * @param simi Similitud nova
     */
    public void setSimiProd(int index, Double simi) {
        llista_Similituds.set(index, simi);
    }

}
