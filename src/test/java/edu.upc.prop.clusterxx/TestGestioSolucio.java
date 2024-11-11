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

    @Test
    public void testCrearSolucio2() {
        c.getCataleg_Productes();
        Algorisme a = new Aproximacio();
        Solucio sol1 = new Solucio(c.getCataleg_Productes(), a, "Solucio1");
        Solucio sol2 = new Solucio(c.getCataleg_Productes(), a, "Solucio2");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol1);
        llistaSol.add(sol2);
        gs.gestioAlgorisme("Aproximacio");
        gs.creaSolucio("Solucio1");
        gs.creaSolucio("Solucio2");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }

    @Test
    public void testModificarSolucio1() {
        //crear dos productes
        ArrayList<double> similituds1 = new ArrayList<double>();
        similituds1.add(0.0);
        similituds1.add(0.7);

        ArrayList<double> similituds2 = new ArrayList<double>();
        similituds2.add(0.7);
        similituds2.add(0.0);

        Producte p1 = new Producte(0, "Producte1", similituds1); //preguntar al alejandro com assigna els índexs si falla
        Producte p2 = new Producte(1, "Producte2", similituds2);

        //afegir 2 prod al cataleg
        ArrayList<Pair> llistaSimiCataleg = new ArrayList<Pair>(); //preguntar al alejandro si aqui va llista buida o llista amb pair2 = new Pair("Producte2", 0.7);
        c.afegir_producte("Producte1", llistaSimiCataleg);
        Pair pair1 = new Pair("Producte1", 0.7);
        llistaSimiCataleg.add(pair1);
        c.afegir_producte("Producte2", llistaSimiCataleg);

        //enviar a solucionar
        gs.gestioAlgorisme("vorac");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviar
        gs.intercanvia(p1, p2, "Solucio1");
        ArrayList<Solucio> solsDespres = gs.getSolucions();

        //comprovar que son el ordre invertit
        assertEquals("Verificar llista solucions mida", solsDespres.length(), solsAbans.length());
        assertEquals("Verificar llista solucions", ((solsDespres.getFirst()).getSolucio()).get(0), ((solsAbans.getFirst()).getSolucio()).get(1));
        assertEquals("Verificar llista solucions", ((solsDespres.getFirst()).getSolucio()).get(1), ((solsAbans.getFirst()).getSolucio()).get(0));
    }

    @Test
    public void testModificarSolucio2() {
        //crear dos productes
        ArrayList<double> similituds1 = new ArrayList<double>();
        similituds1.add(0.0);
        similituds1.add(0.7);

        ArrayList<double> similituds2 = new ArrayList<double>();
        similituds2.add(0.7);
        similituds2.add(0.0);

        Producte p1 = new Producte(0, "Producte1", similituds1); //preguntar al alejandro com assigna els índexs si falla
        Producte p2 = new Producte(1, "Producte2", similituds2);

        //afegir 2 prod al cataleg
        ArrayList<Pair> llistaSimiCataleg = new ArrayList<Pair>(); //preguntar al alejandro si aqui va llista buida o llista amb pair2 = new Pair("Producte2", 0.7);
        c.afegir_producte("Producte1", llistaSimiCataleg);
        Pair pair1 = new Pair("Producte1", 0.7);
        llistaSimiCataleg.add(pair1);
        c.afegir_producte("Producte2", llistaSimiCataleg);

        //enviar a solucionar
        gs.gestioAlgorisme("vorac");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviar
        gs.intercanvia(p1, p2, "Solucio2");
        ArrayList<Solucio> solsDespres = gs.getSolucions();

        //comprovar que ha saltat error i no he fet res
        assertEquals("Verificar llista solucions", solsDespres, solsAbans);
    }

    @Test
    public void testAfegeixSolucio1() {
        ArrayList<Producte> productes = new ArrayList<Productes>();

        Algorisme alg = new Aproximacio();
        Solucio sol =  new Solucio(productes, alg, "Solucio1");

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);

        ArrayList<String> llistaString = new ArrayList<String>();

        //enviar a solucionar
        gs.gestioAlgorisme("aproximacio");
        gs.agefeixSolucio(llistaString, alg, "Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> sols = gs.getSolucions();

        //comprovar que s'ha afegit
        assertEquals("Verificar llista solucions", sols, llistaSol);
    }

    @Test
    public void testEliminarSolucio1() {
        ArrayList<Producte> productes = new ArrayList<Productes>();

        Algorisme alg = new Aproximacio();
        Solucio sol =  new Solucio(productes, alg, "Solucio1");

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();

        ArrayList<String> llistaString = new ArrayList<String>();

        //enviar a solucionar
        gs.gestioAlgorisme("aproximacio");
        gs.agefeixSolucio(llistaString, alg, "Solucio1");
        gs.eliminarSolucio("Solucio1");
        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> sols = gs.getSolucions();

        //comprovar que s'ha eliminat
        assertEquals("Verificar llista solucions", sols, llistaSol);
    }

    @Test
    public void testEliminarSolucio2() {
        ArrayList<Producte> productes = new ArrayList<Productes>();

        Algorisme alg = new Aproximacio();
        Solucio sol =  new Solucio(productes, alg, "Solucio1");

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);

        ArrayList<String> llistaString = new ArrayList<String>();

        //enviar a solucionar
        gs.gestioAlgorisme("aproximacio");
        gs.agefeixSolucio(llistaString, alg, "Solucio1");
        gs.eliminarSolucio("Solucio2");
        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> sols = gs.getSolucions();

        //comprovar que no s'ha eliminat
        assertEquals("Verificar llista solucions", sols, llistaSol);
    }

    @Test
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