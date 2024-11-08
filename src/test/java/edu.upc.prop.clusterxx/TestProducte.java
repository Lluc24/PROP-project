import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

import Producte;

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
        Producte P = new (1, "Proba", simi);
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
        Producte P(1, "NewProd", simi);

        double simi = P.get_simi_prod(3);

        assertEquals("Similitud amb Producte correcte", (0.15+3), simi);
    }
}