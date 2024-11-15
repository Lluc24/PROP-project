package edu.upc.prop.clusterxx;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testeig de Solucio.java
 */
public class TestGestioSolucio {
    private GestioSolucio gs;
    private Cataleg c;
    private ArrayList<Double> similituds0 = new ArrayList<>();
    private ArrayList<Double> similituds1 = new ArrayList<>();
    private ArrayList<Producte> productes = new ArrayList<Producte>();
    private double[][] matriuSim = {{0.0, 0.5},{0.5, 0.0}};

    @Before
   public void Inicialitza() {
        c = mock(Cataleg.class);
        gs = new GestioSolucio(c);
        similituds0.add(0.0);
        similituds1.add(0.5);
        similituds1.add(0.0);
    }

    /**
     * Test de la creadora
     * Crea una instància de GestioSolucio i comprova que sigui correcte
     */
    @Test
    public void crearGestioSolucioTest() {
        ArrayList<Solucio> solucions = new ArrayList<Solucio>();

        assertEquals("Verificar solucions", solucions, gs.getSolucions());
        assertEquals("Verificar cataleg", c, gs.getCataleg());
        assertTrue("Verificar algorismeAct", gs.getAlgorismeAct() instanceof Aproximacio);
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte Aproximacio i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme1() {
        gs.gestioAlgorisme("aproximacio");

        assertTrue("Verificar algorismeAct", gs.getAlgorismeAct() instanceof Aproximacio);
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeGreedy i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme2() {

        gs.gestioAlgorisme("greedy");

        assertTrue("Verificar algorismeAct", gs.getAlgorismeAct() instanceof AlgorismeGreedy);
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeBT i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme3() {
        AlgorismeBT vor = mock(AlgorismeBT.class);

        gs.gestioAlgorisme("algorismeBT");

        assertTrue("Verificar algorismeAct", gs.getAlgorismeAct() instanceof AlgorismeBT);
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte Aproximacio i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme4() {

        gs.gestioAlgorisme("NoExisteix");

        assertTrue("Verificar algorismeAct", gs.getAlgorismeAct() instanceof Aproximacio);
    }

    /**
     * Test de crearSolucio
     * Valors estudiats: Es crea un nou objecte Solucio amb algorismeAct de tipus Aproximacio i s'hagi creat una Solucio igual.
     */
    @Test
    public void testCrearSolucio1() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);

        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        gs.creaSolucio("Solucio1");
        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
         assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
         assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof Aproximacio);
    }

    /**
     * Test de crearSolucio
     * Valors estudiats: Es crea un nou objecte Solucio amb algorismeAct de tipus AlgorismeGreedy i s'hagi creat una Solucio igual.
     */
    @Test
    public void testCrearSolucio2() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);

        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        gs.gestioAlgorisme("greedy");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof AlgorismeGreedy);
    }

        /**
         * Test de crearSolucio
         * Valors estudiats: Intenta crear dues solucions amb el mateix nom i ha de comprovar que no s'hagi permés.
         */
    @Test
    public void testCrearSolucio3() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);

        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        gs.creaSolucio("Solucio1");
        gs.creaSolucio("Solucio1");
        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof Aproximacio);
    }
/**
 * Test de crearSolucio
 * Valors estudiats: Intenta crear dues solucions amb diferent nom i ha de comprovar que ho hagi permés.
 */
    @Test
    public void testCrearSolucio4() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);

        when(c.getMatriuSimilituds()).thenReturn(matriuSim);


        when(c.getProd_index(0)).thenReturn(productes.get(0));
        when(c.getProd_index(1)).thenReturn(productes.get(1));

        gs.creaSolucio("Solucio1");
        gs.creaSolucio("Solucio2");

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar nom","Solucio2", gs.getSolucions().get(1).getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida Solucio","p0", gs.getSolucions().getFirst().getSolucio().getFirst().getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().get(1).getSolucio().size());
        assertEquals("Verificar mida solucions",2, gs.getSolucions().size());
        assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof Aproximacio);
        assertTrue("Verificar algorisme", gs.getSolucions().get(1).getAlgorisme() instanceof Aproximacio);
    }

        /**
         * Test de modificarSolucio
         * Valors estudiats: Crea una solucio i intercanvia els seus productes.
         */
    @Test
    public void testModificarSolucio1() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        when(c.getProd_index(0)).thenReturn(productes.get(0));
        when(c.getProd_index(1)).thenReturn(productes.get(1));

        gs.creaSolucio("Solucio1");

        gs.modificarSolucio(productes.get(0).getNom(),productes.get(1).getNom(),"Solucio1");
        Solucio solFi = gs.getSolucions().getFirst();

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof Aproximacio);
        assertEquals("Verificar primer intercanvi", "p1", solFi.getSolucio().get(0).getNom());
        assertEquals("Verificar segon intercanvi", "p0", solFi.getSolucio().get(1).getNom());

    }
        /**
         * Test de modificarSolucio
         * Valors estudiats: Intenta modificar una solucio que no existeix i ha de comprovar que no s'hagi permés.
         */
    @Test
    public void testModificarSolucio2() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        when(c.getProd_index(0)).thenReturn(productes.get(0));
        when(c.getProd_index(1)).thenReturn(productes.get(1));

        gs.creaSolucio("Solucio1");

        gs.modificarSolucio(productes.get(0).getNom(),productes.get(1).getNom(),"SolucioNoExisteix");
        Solucio solFi = gs.getSolucions().getFirst();

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof Aproximacio);
        assertEquals("Verificar primer intercanvi", "p0", solFi.getSolucio().get(0).getNom());
        assertEquals("Verificar segon intercanvi", "p1", solFi.getSolucio().get(1).getNom());
    }

        /**
         * Test de eliminarSolucio
         * Valors estudiats: Afegeix una solucio i la elimina. Ha de comprovar que s'hagi permés.
         */
    @Test
    public void testEliminarSolucio1() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);

        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        gs.creaSolucio("Solucio1");
        gs.eliminarSolucio("Solucio1");
        assertTrue("Verificar que s'ha eliminat", gs.getSolucions().isEmpty());
    }

/**
 * Test de eliminarSolucio
 * Valors estudiats: Intenta eliminar una solucio que no existeix. Ha de comprovar que no hagi fet res.
 */
    @Test
    public void testEliminarSolucio2() {
        for (int i = 0; i < 2; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            if (i == 0) when(producteMock.getSimilituds()).thenReturn(similituds0);
            if (i == 1) when(producteMock.getSimilituds()).thenReturn(similituds1);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productes);

        when(c.getMatriuSimilituds()).thenReturn(matriuSim);

        gs.creaSolucio("Solucio1");
        gs.eliminarSolucio("SolucioNoExisteix");
        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar mida Solucio",2, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertTrue("Verificar algorisme", gs.getSolucions().getFirst().getAlgorisme() instanceof Aproximacio);

    }
}