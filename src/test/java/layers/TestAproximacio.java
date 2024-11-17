package layers;

import layers.domain.Algorisme;
import layers.domain.Aproximacio;
import layers.domain.excepcions.FormatInputNoValid;
import org.junit.*;
import static org.junit.Assert.*;

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
    public void testSolucionarMatriuBuida() {
        double[][] matriuSimilituds = new double[0][0];

        try {
            algorisme.solucionar(matriuSimilituds);
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "No hi ha productes: La matriu de similituds és buida.", e.getMessage());
        }
    }

    @Test
    public void testSolucionarAmbZeroElements() {
        double[][] matriu = new double[0][0];
        int[] esperat = {};
        int[] obtingut = null;
        try {
            obtingut = algorisme.solucionar(matriu);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertArrayEquals("Test: Solucionar amb una matriu buida", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbUnElement() {
        double[][] matriu = {{0.0}};
        int[] esperat = {0};
        int[] obtingut = null;
        try {
            obtingut = algorisme.solucionar(matriu);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertArrayEquals("Test: Solucionar amb una matriu d'un element", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbDosElements() {
        double[][] matriu = {
                {0.0, 1.0},
                {1.0, 0.0}
        };
        int[] esperat = {0, 1};
        int[] obtingut = null;
        try {
            obtingut = algorisme.solucionar(matriu);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
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
        int[] obtingut = null;
        try {
            obtingut = algorisme.solucionar(matriu);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
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
        int[] obtingut = null;
        try {
            obtingut = algorisme.solucionar(matriu);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertArrayEquals("Test: Solucionar amb una matriu de nou elements", esperat, obtingut);
    }

    @Test
    public void testSolucionarAmbMoltsElements() {
        double[][] matriu = {
                {0, 29, 82, 46, 68, 52, 72, 42, 51, 55, 29, 74, 23, 72, 46},
                {29, 0, 55, 46, 42, 43, 43, 23, 23, 31, 41, 51, 11, 52, 21},
                {82, 55, 0, 68, 46, 55, 23, 43, 41, 29, 79, 21, 64, 31, 51},
                {46, 46, 68, 0, 82, 15, 72, 31, 62, 42, 21, 51, 51, 43, 64},
                {68, 42, 46, 82, 0, 74, 23, 52, 21, 46, 82, 58, 46, 65, 23},
                {52, 43, 55, 15, 74, 0, 61, 23, 55, 31, 33, 37, 51, 29, 59},
                {72, 43, 23, 72, 23, 61, 0, 42, 23, 31, 77, 37, 51, 46, 33},
                {42, 23, 43, 31, 52, 23, 42, 0, 33, 15, 37, 33, 33, 31, 37},
                {51, 23, 41, 62, 21, 55, 23, 33, 0, 29, 62, 46, 29, 51, 11},
                {55, 31, 29, 42, 46, 31, 31, 15, 29, 0, 51, 21, 41, 23, 37},
                {29, 41, 79, 21, 82, 33, 77, 37, 62, 51, 0, 65, 42, 59, 61},
                {74, 51, 21, 51, 58, 37, 37, 33, 46, 21, 65, 0, 61, 11, 55},
                {23, 11, 64, 51, 46, 51, 51, 33, 29, 41, 42, 61, 0, 62, 23},
                {72, 52, 31, 43, 65, 29, 46, 31, 51, 23, 59, 11, 62, 0, 59},
                {46, 21, 51, 64, 23, 59, 33, 37, 11, 37, 61, 55, 23, 59, 0}
        };

        int[] esperat = {0, 2, 1, 10, 4, 3, 8, 14, 5, 7, 6, 12, 9, 11, 13};
        int[] obtingut = null;
        try {
            obtingut = algorisme.solucionar(matriu);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertArrayEquals("Test: Solucionar amb una matriu de quinze elements", esperat, obtingut);
    }

    static String[] productes = {/*0*/"Fairy", /*1*/"Cocacola", /*2*/"Lays_onduladas", /*3*/"Ruffles_jamom", /*4*/"Pelotazos_Cheetos", /*5*/"Champu_HS", /*6*/"Pepsi", /*7*/"Toallitas_Dodot", /*8*/"Cuchilla_Guillette", /*9*/"Pastillas_Ariel", /*10*/"Pasta_dientes_OralB", /*11*/"Powerade", /*12*/"Agua_Evian", /*13*/"Mtn_dew", /*14*/"Gatorade"};
}