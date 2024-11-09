package edu.upc.prop.clusterxx;
import edu.upc.prop.clusterxx.Cataleg;
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
    public int index;
    /** String que representa el nom unic que se li ha donat al producte */
    public String nom;
    /** ArrayList que representa les similituds de aquest producte amb la resta
     * cada similitud amb un producte es troba a index corresponent al index*/
    public ArrayList<Double> llista_Similituds;

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

}
