package layers;
import static org.junit.Assert.*;

import layers.domain.AlgorismeGreedy;
import layers.domain.excepcions.FormatInputNoValid;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de testeig de AlgorismeGreedy
 * @author Efrain Tito Cortés
 */
public class TestAlgorismeGreedy {

    private AlgorismeGreedy algorisme;

    /**
     * Inicialitza una instància de la classe AlgorismeGreedy abans de cada test.
     */
    @Before
    public void setUp() {
        try {
            algorisme = new AlgorismeGreedy(0, 1);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
    }

    /**
     * Testa el constructor amb valors vàlids.
     */
    @Test
    public void testConstructor() {

        AlgorismeGreedy alg = null;

        try {
            alg = new AlgorismeGreedy(1, 5);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertEquals("Constructor correcte (producteInicial)", 1, alg.getProducteInicial());
        assertEquals("Constructor correcte (numIteracions)", 5, alg.getNumIteracions());
    }

    /**
     * Testa el constructor amb un valor invàlid per al producteInicial.
     */
    @Test
    public void testConsInvalidProducteInicial() {
        try {
            new AlgorismeGreedy(-1, 5);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "L'índex del producte inicial no pot ser negatiu.", e.getMessage());
        }
    }

    /**
     * Testa el constructor amb un valor invàlid per a numIteracions.
     */
    @Test
    public void testConsInvalidNumIteracions() {
        try {
            new AlgorismeGreedy(1, -5);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "El nombre d'iteracions ha de ser positiu i no zero.", e.getMessage());
        }

        try {
            new AlgorismeGreedy(1, 0);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "El nombre d'iteracions ha de ser positiu i no zero.", e.getMessage());
        }
    }

    /**
     * Testa el setter per a producteInicial amb un valor vàlid.
     */
    @Test
    public void testSetProducteInicialValid() {

        try {
            algorisme.setProducteInicial(2);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertEquals("Setter correcte", 2, algorisme.getProducteInicial());
    }

    /**
     * Testa el setter per a producteInicial amb un valor invàlid.
     */
    @Test
    public void testSetProducteInicialInvalid() {
        try {
            algorisme.setProducteInicial(-1);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "L'índex del producte inicial no pot ser negatiu.", e.getMessage());
        }
    }

    /**
     * Testa el setter per a numIteracions amb un valor vàlid.
     */
    @Test
    public void testSetNumIteracionsValid() {

        try {
            algorisme.setNumIteracions(3);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        assertEquals("Setter correcte", 3, algorisme.getNumIteracions());
    }

    /**
     * Testa el setter per a numIteracions amb un valor invàlid.
     */
    @Test
    public void testSetNumIteracionsInvalid() {
        try {
            algorisme.setNumIteracions(0);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "El nombre d'iteracions ha de ser positiu i no zero.", e.getMessage());
        }

        try {
            algorisme.setNumIteracions(-3);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "El nombre d'iteracions ha de ser positiu i no zero.", e.getMessage());
        }
    }

    /**
     * Testa que es llança una excepció quan el producteInicial és invàlid al mètode 'solucionar'.
     */
    @Test
    public void testSolucionarInvalidProducteInicial() {
        double[][] matriuSimilituds = {
                {0.0, 0.9, 0.8},
                {0.9, 0.0, 0.85},
                {0.8, 0.85, 0.0}
        };

        try {
            algorisme.setProducteInicial(4);  // Establim un producte inicial invàlid
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        try {
            algorisme.solucionar(matriuSimilituds);
            fail("S'hauria d'haver llançat una excepció");
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "L'índex del producte no pot ser negatiu o superior a la quantitat de productes al catàleg.", e.getMessage());
        }
    }

    /**
     * Test per al mètode 'solucionar' amb una matriu de similituds buida.
     */
    @Test
    public void testSolucionarMatriuBuida() {
        double[][] matriuSimilituds = new double[0][0];
        try {
            int[] resultat = algorisme.solucionar(matriuSimilituds);
        } catch (FormatInputNoValid e) {
            assertEquals("El missatge d'error és correcte", "No hi ha productes: La matriu de similituds és buida.", e.getMessage());
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
            configuracio = algorisme.solucionar(matriuSimilituds);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }

        assertArrayEquals("Ordre correcte", new int[] {0}, configuracio);
    }

    /**
     * Test pel mètode 'solucionar' que espera que l'algorisme retorni l'ordre dels índexs [0, 2, 3, 1].
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

        try {
            configuracio = algorisme.solucionar(matriuSimilituds);
        } catch (FormatInputNoValid e) {
            fail("Excepció de FormatInputNoValid inesperada.");
        }
        int[] configuracioEsperada = {0, 2, 3, 1}; //l'ordre correcte segons aquesta matriu

        assertArrayEquals("Ordre correcte", configuracioEsperada, configuracio);
    }

}

