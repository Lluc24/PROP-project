package layers;

import static org.junit.Assert.*;
import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.excepcions.ProducteNoValid;
import org.junit.Before;
import org.junit.Test;


public class TestCtrlCatalegAmbRestriccions {

    private CtrlCatalegAmbRestriccions controlador;

    @Before
    public void setUp() {
        controlador = new CtrlCatalegAmbRestriccions();
    }

    @Test
    public void testAfegirProducteNou() {
        try {
            controlador.afegir_producte("Producte1");
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
            assertEquals("Producte1", controlador.getNomProd_index(0));
            assertEquals(1, controlador.num_prod_act());

    }

    @Test
    public void testAfegitBuit() {
        controlador.producteAfegit();
        assertEquals(1, controlador.get_mida_noConsec());
    }

    @Test
    public void testAfegitNoBuit() {
        controlador.producteAfegit();
        controlador.producteAfegit();
        assertEquals(2, controlador.get_mida_noConsec());
    }

    @Test
    public void testProducteEliminat1() {
        controlador.producteAfegit();
        controlador.producteAfegit();
        controlador.producteAfegit();
        controlador.setRestrConsecId(0, 1);
        controlador.producteEliminat(1);
        assertEquals(2, controlador.get_mida_noConsec());
        try {
            assertFalse(controlador.getRestrConsecID(0, 1));
        } catch (ProducteNoValid e) {

        }
    }

    @Test
    public void testGetRestr() {
        controlador.producteAfegit();
        controlador.producteAfegit();
        controlador.setRestrConsecId(0, 1);
        try {
            assertTrue(controlador.getRestrConsecID(0, 1));
        } catch (ProducteNoValid e) {

        }
    }

    @Test
    public void testGetRestrMateixProd() {
        controlador.producteAfegit();
        controlador.producteAfegit();
        controlador.setRestrConsecId(1, 1);
        try {
            boolean res = controlador.getRestrConsecID(1, 1);
            assertTrue(res);
        } catch (ProducteNoValid e) {

        }
    }

    @Test
    public void testRemRestrConsec() {
        controlador.producteAfegit();
        controlador.producteAfegit();
        controlador.setRestrConsecId(1, 1);
        try {
            assertTrue(controlador.getRestrConsecID(1, 1));
        } catch (ProducteNoValid e) {

        }
        controlador.remRestrConsecId(1, 1);
        try {
            assertFalse(controlador.getRestrConsecID(1, 1));
        } catch (ProducteNoValid e) {

        }
    }

    @Test
    public void testGetMatrRestrConsec() {
        controlador.producteAfegit();
        controlador.producteAfegit();

        boolean[][] matriuRestr = {
                {false, false},
                {false, false},
        };

        assertEquals(matriuRestr, controlador.getMatrRestrConsec());
    }

    @Test
    public void testGetMatrRestrConsecBuida() {

        boolean[][] matriuRestr = new boolean[0][0];

        assertEquals(matriuRestr, controlador.getMatrRestrConsec());
    }


    @Test
    public void  TestMostrarRestrConsec() {
        controlador.producteAfegit();
        controlador.producteAfegit();

        controlador.mostrarRestrConsec(0);
    }






}
