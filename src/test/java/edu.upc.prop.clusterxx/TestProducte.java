
import static org.junit.Assert.*;

import edu.upc.prop.clusterxx.Producte;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @Author Alejandro Lorenzo Navarro
 */
public class TestProducte {

    @Test
    public void TestProducte() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            simi.add(i, 0.33+i);
        }
        Producte P = new Producte(1, "Proba", simi);
        assertEquals("Index Correcte", 1, P.index);
        assertEquals("Nom Correcte", "Proba", P.nom);
        assertEquals("Llista correcta", simi, P.llista_Similituds);
    }


    @Test
    public void TestGet_simil_prod() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 0.15+i);
        }
        Producte P = new Producte(1, "NewProd", simi);

        double simid = P.get_simil_prod(3);

        assertEquals("Similitud amb Producte correcte", (0.15+3), simid);
    }
}