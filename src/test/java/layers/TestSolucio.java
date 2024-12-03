package layers;

import layers.domain.Algorisme;
import layers.domain.Producte;
import layers.domain.Solucio;
import layers.domain.excepcions.FormatInputNoValid;
import org.junit.Test;
import java.util.ArrayList;
import org.junit.Before;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Classe de testeig de Solucio.java
 */
public class TestSolucio {
    ArrayList<String> productesLlista = new ArrayList<String>();
    ArrayList<ArrayList<String>> productesMatriu = new ArrayList<ArrayList<String>>();
    Solucio solucio;
    int prodPrestatge;

    @Before
    public void Inicialitza(){
        prodPrestatge = 4;

        for (int i = 0; i < 4; i++) {
            productesLlista.add("p" + i);
        }

        productesMatriu.add(productesLlista);

        try {
            solucio = new Solucio(productesLlista, "Solucio1", prodPrestatge);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test de constructora
     * Valors estudiats: Crea una instància de SolucioModificada i comprova que els paràmetres siguin correctes.
     */
    @Test
    public void testConstructor() {
        assertEquals("Verificar nom", "Solucio1", solucio.getNom());
        assertEquals("Verificar productes", productesMatriu, solucio.getSolucio());
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
     * Test de getSolucio
     * Valors estudiats: Crea una instància de Solucio i comprova que el paràmetre solucio sigui correcte.
     */
    @Test
    public void testgetSolucio() {
        assertEquals("Verificar llista de productes", productesMatriu, solucio.getSolucio());
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
