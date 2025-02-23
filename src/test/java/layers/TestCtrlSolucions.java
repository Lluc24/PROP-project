package layers;

import layers.domain.*;
import layers.domain.controllers.CtrlCataleg;
import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;
import layers.persistence.CtrlPersistenciaGeneric;
import layers.persistence.CtrlPersistenciaSolucio;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testeig de CtrlSolucions.java
 */
public class TestCtrlSolucions {
    private CtrlSolucions gs;
    private CtrlCatalegAmbRestriccions c;
    private CtrlPersistenciaSolucio cps;
    private ArrayList<Double> similituds0 = new ArrayList<>();
    private ArrayList<Double> similituds1 = new ArrayList<>();
    private ArrayList<Producte> productesLit = new ArrayList<Producte>();
    private double[][] matriuSim = {{0.0, 0.5},{0.5, 0.0}};
    private boolean[][] matriuRestr = {{false, false},{false, false}};
    @Before
    public void Inicialitza() {
        c = mock(CtrlCatalegAmbRestriccions.class);
        gs = new CtrlSolucions(c);
        similituds0.add(0.0);
        similituds1.add(0.5);
        similituds1.add(0.0);
    }

    /**
     * Test de getAlgorismeAct
     * Comprova que retorni el algorisme corresponent
     */
    @Test
    public void getAlgorismeActTest() {
        assertEquals("Verificar algorismeAct", "aproximacio", gs.getAlgorismeAct());
    }

    /**
     * Test de getSolucions
     * Comprova que retorni el vector de solucions
     */
    @Test
    public void getSolucionsTest() {
        ArrayList<Solucio> solucions = new ArrayList<Solucio>();
        assertEquals("Verificar solucions", solucions, gs.getSolucions());
    }

    /**
     * Test de getCatalegAct
     * Comprova que retorni el cataleg corresponent
     */
    @Test
    public void getCatalegTest() {
        assertEquals("Verificar cataleg", c, gs.getCataleg());
    }

    /**
     * Test de setParametres
     * Comprova que s'hagi creat un algorisme Greedy amb els parametres corresponents
     */
    @Test
    public void setParametresTest() {
        try {
            gs.setParametres(3, 4);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar algorismeAct", "greedy", gs.getAlgorismeAct());
    }

    /**
     * Test de la creadora
     * Crea una instància de CtrlSolucions i comprova que sigui correcte
     */
    @Test
    public void crearGestioSolucioTest() {
        ArrayList<Solucio> solucions = new ArrayList<Solucio>();

        assertEquals("Verificar solucions", solucions, gs.getSolucions());
        assertEquals("Verificar cataleg", c, gs.getCataleg());
        assertEquals("Verificar algorismeAct", "aproximacio", gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte Aproximacio i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme1() {
        try {
        gs.gestioAlgorisme("aproximacio");
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar algorismeAct", "aproximacio", gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeGreedy i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme2() {
        try{
        gs.gestioAlgorisme("greedy");
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar algorismeAct", "greedy", gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte AlgorismeBT i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme3() {
        AlgorismeBT vor = mock(AlgorismeBT.class);

        try{
        gs.gestioAlgorisme("algorismeBT");
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar algorismeAct","backtracking", gs.getAlgorismeAct());
    }

    /**
     * Test de gestioAlgorisme
     * Valors estudiats: Es crea un nou objecte Aproximacio i es comprova que el algorismeAct sigui el mateix.
     */
    @Test
    public void testGestioAlgorisme4() {
        try{
        gs.gestioAlgorisme("NoExisteix");
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar algorismeAct", "aproximacio", gs.getAlgorismeAct());
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
            productesLit.add(producteMock);
        }
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);

        try{
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");

        try {
        gs.gestioAlgorisme("greedy");
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        try {
        gs.creaSolucio("Solucio1",4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
    }

    /**
     *  Test de crearSolucio
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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");

        try {
        gs.creaSolucio("Solucio1",4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        try{
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");

        try{
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        try{
        gs.creaSolucio("Solucio2", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar nom","Solucio2", gs.getSolucions().get(1).getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar els primer elements de les solucions",gs.getSolucions().get(1).getSolucio().getFirst().getFirst(), gs.getSolucions().getFirst().getSolucio().getFirst().getFirst());
        assertEquals("Verificar els segons elements de les solucions",gs.getSolucions().get(1).getSolucio().getFirst().get(1), gs.getSolucions().getFirst().getSolucio().getFirst().get(1));
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().get(1).getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().get(1).getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",2, gs.getSolucions().size());
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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");

        try{
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        try {
            gs.modificarSolucio("p1", "p0", "Solucio1");
        }catch (IntercanviNoValid e){
        System.out.println(e.getMessage());
        }catch (NomSolucioNoValid e) {
        System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
        System.out.println(e.getMessage());
        }

        ArrayList<ArrayList<String>> solFi = gs.getSolucions().getFirst().getSolucio();

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertEquals("Verificar primer intercanvi", "p1", solFi.getFirst().get(0));
        assertEquals("Verificar segon intercanvi", "p0", solFi.getFirst().get(1));
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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");

        try{
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        try{
        gs.modificarSolucio(productesLit.get(0).getNom(),productesLit.get(1).getNom(),"SolucioNoExisteix");
        }catch (IntercanviNoValid e){
            System.out.println(e.getMessage());
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }

        Solucio solFi = gs.getSolucions().getFirst();

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
        assertEquals("Verificar primer intercanvi", "p0", solFi.getSolucio().getFirst().get(0));
        assertEquals("Verificar segon intercanvi", "p1", solFi.getSolucio().getFirst().get(1));
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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);

        try{
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        try {
        gs.eliminarSolucio("Solucio1");
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }

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
            productesLit.add(producteMock);
        }
        when(c.getCataleg()).thenReturn(productesLit);
        when(c.getMatriuSimilituds()).thenReturn(matriuSim);
        when(c.getMatrRestrConsec()).thenReturn(matriuRestr);
        when(c.getNomProd_index(0)).thenReturn("p0");
        when(c.getNomProd_index(1)).thenReturn("p1");

        try {
        gs.creaSolucio("Solucio1", 4);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        try{
        gs.eliminarSolucio("SolucioNoExisteix");
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }

        assertEquals("Verificar nom","Solucio1", gs.getSolucions().getFirst().getNom());
        assertEquals("Verificar numero de prestatges",1, gs.getSolucions().getFirst().getSolucio().size());
        assertEquals("Verificar numero de productes",2, gs.getSolucions().getFirst().getSolucio().getFirst().size());
        assertEquals("Verificar mida solucions",1, gs.getSolucions().size());
    }
}