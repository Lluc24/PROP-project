package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.Aproximacio;


import org.junit.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;

@RunWith(value=Parameterized.class)
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

    private int[] esperat;
    private double[][] matriuAdjacencia;

    @BeforeClass
    public static void beforeClass() {

    }

    @AfterClass
    public static void afterClass() {

    }

    @Before
    public void before() {
        System.out.println("Inici test unitari en Aproximacio");
    }

    @After
    public void after() {
        System.out.println("Fi test unitari en Aproximacio");
    }


}
