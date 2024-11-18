package layers;
import static org.junit.Assert.*;

import layers.domain.AlgorismeBT;
import layers.domain.excepcions.FormatInputNoValid;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de testeig de AlgorismeBT
 * @author Efrain Tito Cortés
 */
public class TestAlgorismeBT {

    private AlgorismeBT algorismeBT;

    @Before
    public void setUp() {
        // Inicialitzem l'algorisme abans de cada test
        algorismeBT = new AlgorismeBT();
    }

    /**
     * Test per al mètode 'solucionar' amb una matriu de similituds buida.
     */
    @Test
    public void testSolucionarMatriuBuida() {
        double[][] matriuSimilituds = new double[0][0];

        try {
            int[] resultat = algorismeBT.solucionar(matriuSimilituds);
            assertArrayEquals("Ordre correcte", new int[] {}, resultat);

        } catch (FormatInputNoValid e) {

        }
    }

    /**
     * Test pel mètode 'solucionar' quan hi ha només un producte.
     * L'algorisme ha de retornar l'únic índex disponible, que és 0.
     */
    @Test
    public void testSolucionarAmbUnProducte() {

        double[][] matriuSimilituds = {
                {0.0}
        };

        int[] configuracio = null;
        try {
            configuracio = algorismeBT.solucionar(matriuSimilituds);

        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }

        assertArrayEquals("Ordre correcte", new int[] {0}, configuracio);
    }

    /**
     * Test pel mètode 'solucionar' que espera que l'algorisme retorni l'ordre dels índexs [0, 1, 2, 3].
     */
    @Test
    public void testSolucionarAmbQuatreProductes() {

        double[][] matriuSimilituds = {
                {0.0, 0.4, 0.7, 0.4},
                {0.4, 0.0, 0.8, 0.2},
                {0.7, 0.8, 0.0, 0.9},
                {0.4, 0.2, 0.9, 0.0}
        };

        int[] configuracio = null;
        int[] configuracioEsperada = {0, 1, 2, 3};

        try {
            configuracio = algorismeBT.solucionar(matriuSimilituds);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertArrayEquals("Ordre correcte", configuracioEsperada, configuracio);
    }

    /**
     * Test pel mètode 'solucionar' que espera que l'algorisme retorni l'ordre dels índexs [0, 1, 3, 2].
     * Amb restriccions.
     */
    @Test
    public void testSolucionarAmbQuatreProductesRestriccio() {

        double[][] matriuSimilituds = {
                {0.0, 0.4, 0.7, 0.4},
                {0.4, 0.0, 0.8, 0.2},
                {0.7, 0.8, 0.0, 0.9},
                {0.4, 0.2, 0.9, 0.0}
        };

        boolean[][] matriuRestr = {
                {false, false, false, false},
                {false, false, true, false},
                {false, true, false, false},
                {false, false, false, false}
        };

        int[] configuracio = null;
        int[] configuracioEsperada = {0, 1, 3, 2};

        try {
            configuracio = algorismeBT.solucionar(matriuSimilituds, matriuRestr);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertArrayEquals("Ordre correcte", configuracioEsperada, configuracio);
    }

    /**
     * Test pel mètode 'solucionar' que espera que l'algorisme retorni l'ordre dels índexs [0, 1, 3, 2].
     * Amb restriccions.
     */
    @Test
    public void testSolucionarAmbQuatreProductesImpossible() {

        double[][] matriuSimilituds = {
                {0.0, 0.4, 0.7, 0.4},
                {0.4, 0.0, 0.8, 0.2},
                {0.7, 0.8, 0.0, 0.9},
                {0.4, 0.2, 0.9, 0.0}
        };

        boolean[][] matriuRestr = {
                {false, true, true, true},
                {true, false, true, true},
                {true, true, false, true},
                {true, true, true, false}
        };

        try {
            algorismeBT.solucionar(matriuSimilituds, matriuRestr);
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "No hi ha una solucio valida amb les restriccions actuals", e.getMessage());
        }

    }

    @Test
    public void testSolucionarAmbQuatreProductesImpossible2() {

        double[][] matriuSimilituds = {
                {0.0, 0.4, 0.7, 0.4},
                {0.4, 0.0, 0.8, 0.2},
                {0.7, 0.8, 0.0, 0.9},
                {0.4, 0.2, 0.9, 0.0}
        };

        boolean[][] matriuRestr = {
                {false, false, true, true},
                {false, false, true, true},
                {true, true, false, false},
                {true, true, false, false}
        };

        try {
            algorismeBT.solucionar(matriuSimilituds, matriuRestr);
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "No hi ha una solucio valida amb les restriccions actuals", e.getMessage());
        }

    }

    @Test
    public void testSolucionarAmbDosProductesImpossible() {

        double[][] matriuSimilituds = {
                {0.0, 0.4},
                {0.4, 0.0}
        };

        boolean[][] matriuRestr = {
                {false, true},
                {true, false}

        };


        try {
            algorismeBT.solucionar(matriuSimilituds, matriuRestr);
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "No hi ha una solucio valida amb les restriccions actuals", e.getMessage());
        }
    }

    @Test
    public void testSolucionarAmbDosProductesPossible() {

        double[][] matriuSimilituds = {
                {0.0, 0.4},
                {0.4, 0.0}
        };

        boolean[][] matriuRestr = {
                {false, false},
                {false, false}

        };

        int[] configuracio = null;
        int[] configuracioEsperada = {0, 1};

        try {
            configuracio = algorismeBT.solucionar(matriuSimilituds, matriuRestr);
        } catch (FormatInputNoValid e) {
            assertNotEquals("El missatge d'error és inesperat", "No hi ha una solucio valida amb les restriccions actuals", e.getMessage());
        }

        assertArrayEquals("Ordre correcte", configuracioEsperada, configuracio);
    }

}




