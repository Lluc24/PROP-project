package layers;

import layers.domain.Algorisme;
import layers.domain.Producte;
import layers.domain.Solucio;
import org.junit.Test;
import java.util.ArrayList;
import org.junit.Before;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Classe de testeig de Solucio.java
 */
public class TestSolucio {

    ArrayList<Producte> productes = new ArrayList<Producte>();
    Algorisme alg;
    Solucio solucio;

    @Before
    public void Inicialitza(){

        for (int i = 0; i < 4; i++) {
            Producte producteMock = mock(Producte.class);
            when(producteMock.getIndex()).thenReturn(i);
            when(producteMock.getNom()).thenReturn("p" + i);
            productes.add(producteMock);
        }

        alg = mock(Algorisme.class);

        solucio = new Solucio(productes, alg, "Solucio1");
    }

    /**
     * Test de constructora
     * Valors estudiats: Crea una instància de SolucioModificada i comprova que els paràmetres siguin correctes.
     */
    @Test
    public void testConstructor() {
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
        assertEquals("Verificar algorisme", alg, solucio.getAlgorisme());
        assertEquals("Verificar productes", productes, solucio.getSolucio());
    }

    /**
     * Test de getNom
     * Valors estudiats: Crea una instància de Solucio i comprova que el nom sigui correcte.
     */
    @Test
    public void testgetNom() {
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
    }

    /**
     * Test de getAlgorisme
     * Valors estudiats: Crea una instància de Solucio i comprova que el algorisme sigui correcte.
     */
    @Test
    public void testgetAlgorisme() {
        assertEquals("Verificar algorisme", alg, solucio.getAlgorisme());
    }

    /**
     * Test de getSolucio
     * Valors estudiats: Crea una instància de Solucio i comprova que el paràmetre solucio sigui correcte.
     */
    @Test
    public void testgetSolucio() {
        assertEquals("Verificar llista de productes", productes, solucio.getSolucio());
    }

    /**
     * Test de producteExisteix
     * Valors estudiats: Afegim un producte i comprovem que producteExisteix el detecta.
     */
    @Test
    public void testTrobarProducteExisteix1() {
        // Testeja si el primer producte existeix
        boolean resultat = solucio.trobarProducte("p0");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }

    /**
     * Test de producteExisteix
     * Valors estudiats: Afegim un producte i comprovem que producteExisteix el detecta.
     */
    @Test
    public void testTrobarProducteExisteix2() {
        // Testeja si el segon producte existeix
        boolean resultat = solucio.trobarProducte("p1");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }

    @Test
    public void testTrobarProducteExisteix3() {
        boolean resultat = solucio.trobarProducte("p3");
        assertTrue("El producte hauria d'existir", resultat);  // Ens assegurem que retorna true
    }

    /**
     * Test de producteExisteix
     * Valors estudiats: Comprovem que producteExisteix no detecti un producte que no existeix.
     */
    @Test
    public void testTrobarProducteNoExisteix() {
        // Testeja si el producte amb nom "ProducteNoExisteix" existeix
        boolean resultat = solucio.trobarProducte("ProducteNoExisteix");
        assertFalse("El producte no hauria d'existir", resultat);  // Ens assegurem que retorna false
    }
    }
