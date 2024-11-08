package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.*;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;



/**
 * Classe de testeig de Solucio.java
 */
public class TestSolucio {
    ArrayList<Double> similituds1, similituds2, similituds3, similituds4;
    Producte p1, p2, p3, p4;
    ArrayList<Producte> productes;
    Algorisme alg;
    Solucio solucio;
    @before
    {
        similituds1 = new ArrayList<>();
        similituds1.add(0.0);
        similituds1.add(0.7);
        similituds1.add(0.8);
        similituds1.add(0.1);

        similituds2 = new ArrayList<>();
        similituds2.add(0.7);
        similituds2.add(0.0);
        similituds2.add(0.9);
        similituds2.add(0.3);

        similituds3 = new ArrayList<>();
        similituds3.add(0.8);
        similituds3.add(0.9);
        similituds3.add(0.0);
        similituds3.add(0.4);

        similituds4 = new ArrayList<>();
        similituds4.add(0.1);
        similituds4.add(0.3);
        similituds4.add(0.4);
        similituds4.add(0.0);

        // Crear instancias de Producte
        p1 = new Producte(0, "Producte1", similituds1);
        p2 = new Producte(1, "Producte2", similituds2);
        p3 = new Producte(2, "Producte3", similituds3);
        p4 = new Producte(3, "Producte4", similituds4);

        productes = new ArrayList<>();
        productes.add(p1);
        productes.add(p2);
        productes.add(p3);
        productes.add(p4);

        // Crear la instancia de Algorisme (suponiendo que tiene un constructor simple)
        alg = new Aproximacio();

        // Crear la instancia de Solucio
        solucio = new Solucio(productes, alg, "Solucio1");

    }

    @Test
    public void testConstructor() {
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
        assertEquals("Verificar algorisme", alg, solucio.getAlgorisme());
        assertEquals("Verificar productes", productes, solucio.getSolucio());
    }

    @Test
    public void testgetNom() {
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
    }

    @Test
    public void testgetAlgorisme() {
        assertEquals("Verificar algorisme", alg, solucio.getAlgorisme());
    }

    @Test
    public void testgetSolucio() {
        assertEquals("Verificar llista de productes", productes, solucio.getSolucio());

    }

    @Test
    public void testTrobarProducteExisteix1() {
        // Testeja si el producte amb nom "Producte2" existeix
        boolean resultat = solucio.trobarProducte("Producte1");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }

    @Test
    public void testTrobarProducteExisteix2() {
        // Testeja si el producte amb nom "Producte2" existeix
        boolean resultat = solucio.trobarProducte("Producte2");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }
    @Test
    public void testTrobarProducteExisteix3() {
        // Testeja si el producte amb nom "Producte2" existeix
        boolean resultat = solucio.trobarProducte("Producte4");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }

    @Test
    public void testTrobarProducteNoExisteix() {
        // Testeja si el producte amb nom "ProducteNoExisteix" existeix
        boolean resultat = solucio.trobarProducte("ProducteNoExisteix");
        assertFalse("El producte no hauria d'existir", resultat);  // Ens assegurem que retorna false
    }

    }
