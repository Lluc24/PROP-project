
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
        assertEquals("Index Correcte", 1, P.getIndex());
        assertEquals("Nom Correcte", "Proba", P.getNom());
        assertEquals("Llista correcta", simi, P.getSimilituds());
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

    @Test
    public void TestGetNom() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        assertEquals("Nom producte correcte", P.getNom(), "NewProd");
    }

    @Test
    public void TestGetIndex() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        assertEquals("Index producte correcte", P.getIndex(), 1);
    }

    @Test
    public void TestGetSimilituds() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        assertEquals("Nom producte correcte", P.getSimilituds(), simi);
    }


    @Test
    public void TestSetNom() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        P.setNom("NomCambiat");

        assertEquals("Nom producte correcte", P.getNom(), "NomCambiat");
    }

    @Test
    public void TestSetSimilituds() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        ArrayList<Double> simi2 = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 2.0);
        }

        assertEquals("Similituds producte correcte", P.getSimilituds(), simi2);

    }

    @Test
    public void TestAddSimiProd() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        P.addSimiProd(11, 2.0);

        assertEquals("Similitud producte correcte", P.get_simil_prod(11), 2.0);

    }

    @Test
    public void TestRemSimiProd() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            simi.add(i, 1.0);
        }
        Producte P = new Producte(1, "NewProd", simi);

        P.remSimiProd(9);

        assertEquals("Mida nova correcte", P.getSimilituds().size(), 9);
    }



}