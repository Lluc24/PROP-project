import java.util.ArrayList;


/**
 * Representa un producte del sistema, amb nom, llista de similituts 
 * i index que representa la seva posicio al Catalge (@see Cataleg)
 * @author Alejandro Lorenzo Navarro
 * @version 2.0
 */
public class Producte {

    public int index;
    public String nom; 
    public ArrayList<Int> llista_Similituds;

    /**
     * @param inde El index a Cataleg_Productes de la classe Cataleg on es troba el producte 
     * @param nom El nom de producte a crear
     * @param llista Una llista amb totes les similituds del productes
     * Descripció: Es crea una nova instancia de productes
     */
    public Producte(int index, String nom, ArrayList<Int> llista) {
        this.index = index;
        this.nom = nom;
        this.llista_Similituds = llista;
    }

    /**
     * 
     * @param index_prod El index d'un producte al cual es vol la similitud amb el producte que crida la funcio
     * @return Retorna el valor de la simiitud This.Producte = Producte.index=index_prod
     */
    public int simil_prod(int index_prod) {
        return llista_Similituds(index_prod);
    }

}
