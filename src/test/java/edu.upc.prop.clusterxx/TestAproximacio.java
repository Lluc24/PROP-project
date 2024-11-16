package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class TestAproximacio {
    Algorisme algorisme;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Inici test unitari algorisme aproximacio");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Fi test unitari algorisme aproximacio");
    }

    @Before
    public void before() {
        algorisme = new Aproximacio();
    }

    @After
    public void after() {

    }

    @Test
    public void testSolucionarAmbZeroElements() {
        double[][] matriu = new double[0][0];
        int[] esperat = {};
        int[] obtingut = algorisme.solucionar(matriu);
        assertArrayEquals("Test: Solucionar amb una matriu buida", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbUnElement() {
        double[][] matriu = {{0.0}};
        int[] esperat = {0};
        int[] obtingut = algorisme.solucionar(matriu);
        assertArrayEquals("Test: Solucionar amb una matriu d'un element", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbDosElements() {
        double[][] matriu = {
                {0.0, 1.0},
                {1.0, 0.0}
        };
        int[] esperat = {0, 1};
        int[] obtingut = algorisme.solucionar(matriu);
        assertArrayEquals("Test: Solucionar amb una matriu de quatre elements", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbDiversosElements() {
        double[][] matriu = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };
        int[] esperat = {0, 3, 2, 1};
        int[] obtingut = algorisme.solucionar(matriu);
        assertArrayEquals("Test: Solucionar amb una matriu de quatre elements", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbBastantsElements() {
        double[][] matriu = {
            {0, 29, 82, 46, 68, 52, 72, 42, 51},
            {29, 0, 55, 46, 42, 43, 43, 23, 23},
            {82, 55, 0, 68, 46, 55, 23, 43, 41},
            {46, 46, 68, 0, 82, 15, 72, 31, 62},
            {68, 42, 46, 82, 0, 74, 23, 52, 21},
            {52, 43, 55, 15, 74, 0, 61, 23, 55},
            {72, 43, 23, 72, 23, 61, 0, 42, 23},
            {42, 23, 43, 31, 52, 23, 42, 0, 33},
            {51, 23, 41, 62, 21, 55, 23, 33, 0}
        };

        int[] esperat = {0, 2, 1, 6, 3, 4, 5, 7, 8};
        int[] obtingut = algorisme.solucionar(matriu);
        assertArrayEquals("Test: Solucionar amb una matriu de nou elements", esperat, obtingut);
    }
}