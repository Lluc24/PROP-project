package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Classe de testeig de Solucio.java
 */
public class TestGestioSolucio {
    GestioSolucio gs;
    Cataleg c;

    @Before
   public void Inicialitza() {
        c = mock(Cataleg.class);
        gs = new GestioSolucio(c);
    }

    /**
     * Test de la creadora
     * Crea una instància de GestioSolucio i comprova que sigui correcte
     */
    @Test
    public void crearGestioSolucioTest() {
        ArrayList<Solucio> solucions = new ArrayList<Solucio>();
        Algorisme algorismeAct = mock(Aproximacio.class);

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
        Aproximacio aprox = mock(Aproximacio.class);

        gs.gestioAlgorisme("aproximacio");

        assertEquals("Verificar algorismeAct", aprox, gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeGreedy i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme2() {
        AlgorismeGreedy vor = mock(AlgorismeGreedy.class);

        gs.gestioAlgorisme("greedy");

        assertEquals("Verificar algorismeAct", vor, gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeBT i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme3() {
        AlgorismeBT vor = mock(AlgorismeBT.class);

        gs.gestioAlgorisme("algorismeBT");

        assertEquals("Verificar algorismeAct", vor, gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte Aproximacio i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme4() {
        Aproximacio aprox = mock(Aproximacio.class);

        gs.gestioAlgorisme("aproximacio");
        gs.gestioAlgorisme("NoExisteix");

        assertEquals("Verificar algorismeAct", aprox, gs.getAlgorismeAct());
    }

    /**
     * Test de crearSolucio
     * Valors estudiats: Es crea un nou objecte Solucio amb algorismeAct de tipus Aproximacio i s'hagi creat una Solucio igual.
     */
    @Test
    public void testCrearSolucio1() {
        Algorisme a = mock(Aproximacio.class);
        Solucio sol = mock(Solucio.class);
        when(sol.getNom()).thenReturn("Solucio1");
        when(sol.getSolucio()).thenReturn(c.getCataleg());
        when(sol.getAlgorisme()).thenReturn(a);

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
        Algorisme a = mock(AlgorismeGreedy.class);
        Solucio sol = mock(Solucio.class);
        when(sol.getNom()).thenReturn("Solucio1");
        when(sol.getSolucio()).thenReturn(c.getCataleg());
        when(sol.getAlgorisme()).thenReturn(a);

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol);
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar llista solucions", llistaSol, gs.getSolucions());
    }

        /**
         * Test de crearSolucio
         * Valors estudiats: Intenta crear dues solucions amb el mateix nom i ha de comprovar que no s'hagi permés.
         */
    @Test
    public void testCrearSolucio3() {
        Algorisme a = mock(AlgorismeGreedy.class);
        Solucio sol1 = mock(Solucio.class);
        when(sol1.getNom()).thenReturn("Solucio1");
        when(sol1.getSolucio()).thenReturn(c.getCataleg());
        when(sol1.getAlgorisme()).thenReturn(a);

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol1);
        gs.gestioAlgorisme("greedy");
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
        Algorisme a = mock(AlgorismeGreedy.class);
        Solucio sol1 = mock(Solucio.class);
        when(sol1.getNom()).thenReturn("Solucio1");
        when(sol1.getSolucio()).thenReturn(c.getCataleg());
        when(sol1.getAlgorisme()).thenReturn(a);

        ArrayList<Solucio> llistaSol = new ArrayList<Solucio>();
        llistaSol.add(sol1);

        Solucio sol2 = mock(Solucio.class);
        when(sol2.getNom()).thenReturn("Solucio2");
        when(sol2.getSolucio()).thenReturn(c.getCataleg());
        when(sol2.getAlgorisme()).thenReturn(a);

        gs.gestioAlgorisme("greedy");
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
        ArrayList<Producte> productes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            when(producteMock.getNom()).thenReturn("p"+i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);
        when(c.num_prod_act()).thenReturn(3);

        //enviar a solucionar
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviar
        gs.modificarSolucio(productes.get(0), productes.get(1), "Solucio1");
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
        ArrayList<Producte> productes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            when(producteMock.getNom()).thenReturn("p"+i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);
        when(c.num_prod_act()).thenReturn(3);

        //enviar a solucionar
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviarproducte
        gs.modificarSolucio(productes.get(0), productes.get(1), "Solucio2");
        ArrayList<Solucio> solsDespres = gs.getSolucions();

        //comprovar que ha saltat error i no he fet res
        assertEquals("Verificar llista solucions", solsDespres, solsAbans);
    }
/**
 * Test de afegirSolucio
 * Valors estudiats: Afegeix una solucio i ha de comprovar que s'hagi permés.

    @Test
    public void testAfegeixSolucio1() {
        //crear dos productes
        ArrayList<Producte> productes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            when(producteMock.getNom()).thenReturn("p"+i);
            productes.add(producteMock);
        }
        when(c.consultar_cataleg()).thenReturn(productes);
        when(c.num_prod_act()).thenReturn(3);

        //enviar a solucionar
        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");

        //mirar quin ordre els ha solucionat
        ArrayList<Solucio> solsAbans = gs.getSolucions();

        //intercanviar
        gs.modificarSolucio(p1, p2, "Solucio1");
        ArrayList<Solucio> solsDespres = gs.getSolucions();

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

        Algorisme alg = mock(Aproximacio.class);
        Solucio sol = mock(Solucio.class);
        when(sol.getNom()).thenReturn("Solucio1");
        when(sol.getSolucio()).thenReturn(c.getCataleg());
        when(sol.getAlgorisme()).thenReturn(alg);

        ArrayList<Solucio> llistaSol = new ArrayList<>();

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

        Algorisme alg = mock(Aproximacio.class);
        Solucio sol = mock(Solucio.class);
        when(sol.getNom()).thenReturn("Solucio1");
        when(sol.getSolucio()).thenReturn(c.getCataleg());
        when(sol.getAlgorisme()).thenReturn(alg);

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