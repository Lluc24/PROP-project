package test;

import main.domain.classes.Solucio;
import main.domain.classes.Producte;
import main.domain.classes.Algorisme;

import org.junit.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
/**
 * Classe de testeig de Solucio.java
 */
public class TestSolucio {

    public Solucio crearSolucioTest(){
        ArrayList<Double> similituds1 = new ArrayList<>();
        similituds1.add(0.0);
        similituds1.add(0.7);
        similituds1.add(0.8);
        similituds1.add(0.1);

        ArrayList<Double> similituds2 = new ArrayList<>();
        similituds2.add(0.7);
        similituds2.add(0.0);
        similituds2.add(0.9);
        similituds2.add(0.3);

        ArrayList<Double> similituds3 = new ArrayList<>();
        similituds3.add(0.8);
        similituds3.add(0.9);
        similituds3.add(0.0);
        similituds3.add(0.4);

        ArrayList<Double> similituds4 = new ArrayList<>();
        similituds4.add(0.1);
        similituds4.add(0.3);
        similituds4.add(0.4);
        similituds4.add(0.0);

        // Crear instancias de Producte
        Producte p1 = new Producte(1, "Producte1", similituds1);
        Producte p2 = new Producte(2, "Producte2", similituds2);
        Producte p3 = new Producte(1, "Producte3", similituds3);
        Producte p4 = new Producte(2, "Producte4", similituds4);

        ArrayList<Producte> productes = new ArrayList<>();
        productes.add(p1);
        productes.add(p2);
        productes.add(p3);
        productes.add(p4);

        // Crear la instancia de Algorisme (suponiendo que tiene un constructor simple)
        Algorisme alg = new Algorisme("Algorisme1");

        // Crear la instancia de Solucio
        Solucio solucio = new Solucio(productes, alg, "Solucio1");

        return solucio;
    }

    @Test
    public void testConstructor() {
        ArrayList<Double> similituds1 = new ArrayList<>();
        similituds1.add(0.0);
        similituds1.add(0.7);
        similituds1.add(0.8);
        similituds1.add(0.1);

        ArrayList<Double> similituds2 = new ArrayList<>();
        similituds2.add(0.7);
        similituds2.add(0.0);
        similituds2.add(0.9);
        similituds2.add(0.3);

        // Crear instancias de Producte
        Producte p1 = new Producte(1, "Producte1", similituds1);
        Producte p2 = new Producte(2, "Producte2", similituds2);

        ArrayList<Producte> productes = new ArrayList<>();
        productes.add(p1);
        productes.add(p2);

        // Crear la instancia de Algorisme (suponiendo que tiene un constructor simple)
        Algorisme alg = new Algorisme("Algorisme1");

        // Crear la instancia de Solucio
        Solucio solucio = new Solucio(productes, alg, "Solucio1");

         // Verificar que la informació de Solucio es correcta
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
        assertEquals("Verificar algorisme", alg, solucio.getAlgorisme());
        assertEquals("Verificar productes", productes, solucio.getSolucio());
    }

    @Test
    public void testgetNom() {
        Solucio solucio = crearSolucioTest();
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
    }

    @Test
    public void testgetAlgorisme() {
        Solucio solucio = crearSolucioTest();
        Algorisme alg = new Algorisme("Algorisme1");
        assertEquals("Verificar algorisme", alg, solucio.getAlgorisme());
    }

    @Test
    public void testgetSolucio() {
        Solucio solucio = crearSolucioTest();

        ArrayList<Double> similituds1 = new ArrayList<>();
        similituds1.add(0.0);
        similituds1.add(0.7);
        similituds1.add(0.8);
        similituds1.add(0.1);

        ArrayList<Double> similituds2 = new ArrayList<>();
        similituds2.add(0.7);
        similituds2.add(0.0);
        similituds2.add(0.9);
        similituds2.add(0.3);

        ArrayList<Double> similituds3 = new ArrayList<>();
        similituds3.add(0.8);
        similituds3.add(0.9);
        similituds3.add(0.0);
        similituds3.add(0.4);

        ArrayList<Double> similituds4 = new ArrayList<>();
        similituds4.add(0.1);
        similituds4.add(0.3);
        similituds4.add(0.4);
        similituds4.add(0.0);

        // Crear instancias de Producte
        Producte p1 = new Producte(1, "Producte1", similituds1);
        Producte p2 = new Producte(2, "Producte2", similituds2);
        Producte p3 = new Producte(1, "Producte3", similituds3);
        Producte p4 = new Producte(2, "Producte4", similituds4);

        ArrayList<Producte> productes = new ArrayList<>();
        productes.add(p1);
        productes.add(p2);
        productes.add(p3);
        productes.add(p4);

        assertEquals("Verificar llista de productes", productes, solucio.getSolucio());
    }

    @Test
    public void testTrobarProducteExisteix() {
        Solucio solucio = crearSolucioTest();
        // Testeja si el producte amb nom "Producte2" existeix
        boolean resultat = solucio.trobarProducte("Producte2");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }

    @Test
    public void testTrobarProducteNoExisteix() {
        Solucio solucio = crearSolucioTest();
        // Testeja si el producte amb nom "ProducteNoExisteix" existeix
        boolean resultat = solucio.trobarProducte("ProducteNoExisteix");
        assertFalse("El producte no hauria d'existir", resultat);  // Ens assegurem que retorna false
    }

    }
