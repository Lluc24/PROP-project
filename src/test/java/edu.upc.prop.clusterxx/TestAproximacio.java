package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;

public class TestAproximacio {
    static final double[][] mat1 = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
    };

    /*static final double[][] mat2 = {
            {0, 10, 10, 10, 0, 0, 0, 10, 10, 0},
            {10, 0, 0, 0, 0, 0, 0, 0, 0},
            {10, 0, 0, 10,}
    };*/

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
    public void testMatriuSimilitudsBuida() {
        double[][] matriu = new double[0][0];
        assertEquals("Test: Solucionar amb una matriu buida", 0, algorisme.solucionar(matriu).length);
    }

}
