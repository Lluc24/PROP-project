package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.*;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;



/**
 * Classe de testeig de Solucio.java
 */
public class TestGestioSolucio {
    GestioSolucio gs;
    Cataleg c;
    ArrayList<Double> similituds1, similituds2, similituds3, similituds4;
    Producte p1, p2, p3, p4;
    ArrayList<Producte> productes;
    Algorisme alg;
    Solucio solucio;

    @before
    {
        c = new Cataleg();
        gs = new GestioSolucio(c);
/*
        c.afegir_producte("Producte1");
        c.afegir_producte("Producte1");
        c.afegir_producte("Producte1");
        c.afegir_producte("Producte1");
        c.afegir_similituds(,0)

 */

    }

    @Test
    public void testGestioAlgorisme1() {
        Aproximacio aprox = new Aproximacio();
        ArrayList<Aproximacio> llistaAprox = new ArrayList<Aproximacio>();
        llistaAprox.add(aprox);

        gs.gestioAlgorisme("aproximacio");

        assertEquals("Verificar llista aproximacio", llistaAprox, gs.getAproximacio());
        assertEquals("Verificar algorismeAct", aprox, gs.getAlgorismeAct());
    }

    @Test
    public void testGestioAlgorisme2() {
        Vorac vor = new Vorac();
        ArrayList<Vorac> llistaVorac = new ArrayList<Vorac>();
        llistaAprox.add(vor);

        gs.gestioAlgorisme("vorac");

        assertEquals("Verificar llista vorac", llistaVorac, gs.getVorac());
        assertEquals("Verificar algorismeAct", vor, gs.getAlgorismeAct());
    }

    @Test
    public void testGestioAlgorisme3() {
        ArrayList<Vorac> llistaVorac = new ArrayList<Vorac>();
        ArrayList<Aproximacio> llistaAprox = new ArrayList<Aproximacio>();
        Algorsime alg = new Algorisme();

        gs.gestioAlgorisme("noExiste");

        assertEquals("Verificar llista aproximacio", llistaAprox, gs.getAproximacio());
        assertEquals("Verificar llista vorac", llistaVorac, gs.getVorac());
        assertEquals("Verificar algorismeAct", alg, gs.getAlgorismeAct());
    }

    @Test
    public void testCrearSolucio1() {
        c.getCataleg_Productes();
        Algorisme a = new Aproximacio();
        Solucio sol = new Solucio(c.getCataleg_Productes(), a, "Solucio1");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);
        gs.gestioAlgorisme("Aproximacio");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }

    @Test
    public void testCrearSolucio2() {
        c.getCataleg_Productes();
        Algorisme a = new Aproximacio();
        Solucio sol = new Solucio(c.getCataleg_Productes(), a, "Solucio1");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);
        gs.gestioAlgorisme("Aproximacio");
        gs.creaSolucio("Solucio1");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }

    public void crearGestioSolucioTest() {
        ArrayList<Solucio> solucions = new ArrayList<Solucio>();
        Algorisme algorismeAct = null;
        ArrayList<Vorac> vorac = new ArrayList<Vorac>();
        ArrayList<Aproximacio> aproximacio = new ArrayList<Aproximacio>();


        assertEquals("Verificar solucions", solucions, gs.getSolucions());
        assertEquals("Verificar cataleg", c, gs.getCataleg());
        assertEquals("Verificar algorismeAct", algorismeAct, gs.getAlgorismeAct());
        assertEquals("Verificar vorac", vorac, gs.getVorac());
        assertEquals("Verificar aproximacio", aproximacio, gs.getAproximacio());
    }











}