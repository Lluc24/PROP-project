package edu.upc.prop.clusterxx;
import static org.junit.Assert.*;

//import edu.upc.prop.clusterxx.Algorisme;
//import edu.upc.prop.clusterxx.AlgorismeBT;

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
        int[] resultat = algorismeBT.solucionar(matriuSimilituds);
        assertEquals("Solució correcta", 0, resultat.length); //el resultat ha de ser un array buit
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

        int[] configuracio = algorismeBT.solucionar(matriuSimilituds);

        assertArrayEquals("Ordre correcte", new int[] {0}, configuracio);
    }

    /**
     * Test pel mètode 'solucionar' que espera que l'algorisme retorni l'ordre dels índexs [0, 2, 1, 3].
     */
    @Test
    public void testSolucionarAmbQuatreProductes() {

        double[][] matriuSimilituds = {
                {0.0, 0.4, 0.7, 0.4},
                {0.4, 0.0, 0.8, 0.2},
                {0.7, 0.8, 0.0, 0.9},
                {0.4, 0.2, 0.9, 0.0}
        };

        int[] configuracio = algorismeBT.solucionar(matriuSimilituds);
        int[] configuracioEsperada = {0, 2, 1, 3}; //l'ordre correcte segons aquesta matriu

        assertArrayEquals("Ordre correcte", configuracioEsperada, configuracio);
    }

}




