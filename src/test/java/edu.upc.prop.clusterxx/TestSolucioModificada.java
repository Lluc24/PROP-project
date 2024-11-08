package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.Solucio;
import edu.upc.prop.clusterxx.SolucioModificada;
import edu.upc.prop.clusterxx.Producte;
import edu.upc.prop.clusterxx.Algorisme;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Classe de testeig de Solucio.java
 */
public class TestSolucioModificada {
    ArrayList<Double> similituds1, similituds2, similituds3, similituds4;
    Producte p1, p2, p3, p4;
    ArrayList<Producte> productes;
    Algorisme alg;
    SolucioModificada solucioModificada;

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
        solucioModificada = new SolucioModificada(productes, alg, "Solucio1");

    }

    @Test
    public void testConstructor() {
        assertEquals("Verificar nom", "Solucio1", solucioModificada.getNom());
        assertEquals("Verificar algorisme", alg, solucioModificada.getAlgorisme());
        assertEquals("Verificar productes", productes, solucioModificada.getSolucio());
    }

    @Test
    public void testIntercanvia1() {
        solucioModificada.intercanvia(producte1, producte2);

        ArrayList<Producte> productesIntercanviats = new ArrayList<>();
        productesIntercanviats.add(p2);
        productesIntercanviats.add(p1);
        productesIntercanviats.add(p3);
        productesIntercanviats.add(p4);

        assertEquals("Verificar intercanvi", productesIntercanviats, solucioModificada.getSolucio());
    }

    @Test
    public void testIntercanvia2() {
        solucioModificada.intercanvia(producte1, producte4);

        ArrayList<Producte> productesIntercanviats = new ArrayList<>();
        productesIntercanviats.add(p4);
        productesIntercanviats.add(p2);
        productesIntercanviats.add(p3);
        productesIntercanviats.add(p1);

        assertEquals("Verificar intercanvi", productesIntercanviats, solucioModificada.getSolucio());
    }

    }
