package edu.upc.prop.clusterxx;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Classe de testeig de Solucio.java
 */
public class TestGestioSolucio {
    GestioSolucio gs;
    Cataleg c;

    @before
    {
        c = new Cataleg();
        gs = new GestioSolucio(c);
    }

    /**
     * Test dela creadora
     * Crea una instància de GestioSolucio i comprova que sigui correcte
     */
    @Test
    public void crearGestioSolucioTest() {
        ArrayList<Solucio> solucions = new ArrayList<Solucio>();
        Algorisme algorismeAct = new Aproximacio();

        assertEquals("Verificar solucions", solucions, gs.getSolucions());
        assertEquals("Verificar cataleg", c, gs.getCataleg());
        assertEquals("Verificar algorismeAct", algorismeAct, gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte Aproximacio i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme1() {
        Aproximacio aprox = new Aproximacio();

        gs.gestioAlgorisme("aproximacio");

        assertEquals("Verificar algorismeAct", aprox, gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeGreedy i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme2() {
        AlgorismeGreedy vor = new AlgorismeGreedy();

        gs.gestioAlgorisme("greedy");

        assertEquals("Verificar algorismeAct", vor, gs.getAlgorismeAct());
    }

    /**
     * Test de crearSolucio
     * Valors estudiats: Es crea un nou objecte Solucio amb algorismeAct de tipus Aproximacio i s'hagi creat una Solucio igual.
     */
    @Test
    public void testCrearSolucio1() {
        Algorisme a = new Aproximacio();
        Solucio sol = new Solucio(c.getCataleg(), a, "Solucio1");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);
        gs.gestioAlgorisme("aproximacio");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }

    /**
     * Test de crearSolucio
     * Valors estudiats: Es crea un nou objecte Solucio amb algorismeAct de tipus AlgorismeGreedy i s'hagi creat una Solucio igual.
     */
    @Test
    public void testCrearSolucio2() {
        Algorisme a = new AlgorismeGreedy();
        Solucio sol = new Solucio(c.getCataleg(), a, "Solucio1");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());

        /**
         * Test de crearSolucio
         * Valors estudiats: Intenta crear dues solucions amb el mateix nom i ha de comprovar que no s'hagi permés.
         */
    @Test
    public void testCrearSolucio3() {
        c.getCataleg();
        Algorisme a = new Aproximacio();
        Solucio sol = new Solucio(c.getCataleg(), a, "Solucio1");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);
        gs.gestioAlgorisme("Aproximacio");
        gs.creaSolucio("Solucio1");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }
/**
 * Test de crearSolucio
 * Valors estudiats: Intenta crear dues solucions amb diferent nom i ha de comprovar que ho hagi permés.
 */
    @Test
    public void testCrearSolucio4() {
        c.getCataleg();
        Algorisme a = new Aproximacio();
        Solucio sol1 = new Solucio(c.getCataleg(), a, "Solucio1");
        Solucio sol2 = new Solucio(c.getCataleg(), a, "Solucio2");
        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol1);
        llistaSol.add(sol2);
        gs.gestioAlgorisme("Aproximacio");
        gs.creaSolucio("Solucio1");
        gs.creaSolucio("Solucio2");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }

        /**
         * Test de modificarSolucio
         * Valors estudiats: Crea una solucio i intercanvia els seus productes.
         */
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
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviar
        gs.modificarSolucio(p1, p2, "Solucio1");
        ArrayList<Solucio> solsDespres = gs.getSolucions();

        //comprovar que son el ordre invertit
        assertEquals("Verificar llista solucions mida", solsDespres.size(), solsAbans.size());
        assertEquals("Verificar llista solucions", ((solsDespres.getFirst()).getSolucio()).get(0), ((solsAbans.getFirst()).getSolucio()).get(1));
        assertEquals("Verificar llista solucions", ((solsDespres.getFirst()).getSolucio()).get(1), ((solsAbans.getFirst()).getSolucio()).get(0));
    }
        /**
         * Test de modificarSolucio
         * Valors estudiats: Intenta modificar una solucio que no existeix i ha de comprovar que no s'hagi permés.
         */
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
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviar
        gs.modificarSolucio(p1, p2, "Solucio2");
        ArrayList<Solucio> solsDespres = gs.getSolucions();

        //comprovar que ha saltat error i no he fet res
        assertEquals("Verificar llista solucions", solsDespres, solsAbans);
    }
/**
 * Test de afegirSolucio
 * Valors estudiats: Afegeix una solucio i ha de comprovar que s'hagi permés.
 */
    @Test
    public void testAfegeixSolucio1() {
        ArrayList<Producte> productes = new ArrayList<Producte>();

        Algorisme alg = new Aproximacio();
        Solucio sol =  new Solucio(productes, alg, "Solucio1");

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);

        //enviar a solucionar
        gs.gestioAlgorisme("aproximacio");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> sols = gs.getSolucions();

        //comprovar que s'ha afegit
        assertEquals("Verificar llista solucions", sols, llistaSol);
    }

        /**
         * Test de eliminarSolucio
         * Valors estudiats: Afegeix una solucio i la elimina. Ha de comprovar que s'hagi permés.
         */
    @Test
    public void testEliminarSolucio1() {
        ArrayList<Producte> productes = new ArrayList<Producte>();

        Algorisme alg = new Aproximacio();
        Solucio sol =  new Solucio(productes, alg, "Solucio1");

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();

        ArrayList<String> llistaString = new ArrayList<String>();

        //enviar a solucionar
        gs.gestioAlgorisme("aproximacio");
        gs.creaSolucio("Solucio1");
        gs.eliminarSolucio("Solucio1");
        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> sols = gs.getSolucions();

        //comprovar que s'ha eliminat
        assertEquals("Verificar llista solucions", sols, llistaSol);
    }

/**
 * Test de eliminarSolucio
 * Valors estudiats: Intenta eliminar una solucio que no existeix. Ha de comprovar que no hagi fet res.
 */
    @Test
    public void testEliminarSolucio2() {
        ArrayList<Producte> productes = new ArrayList<Producte>();

        Algorisme alg = new Aproximacio();
        Solucio sol =  new Solucio(productes, alg, "Solucio1");

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);

        ArrayList<String> llistaString = new ArrayList<String>();

        //enviar a solucionar
        gs.gestioAlgorisme("aproximacio");
        gs.creaSolucio("Solucio1");
        gs.eliminarSolucio("Solucio2");
        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> sols = gs.getSolucions();

        //comprovar que no s'ha eliminat
        assertEquals("Verificar llista solucions", sols, llistaSol);
    }
}