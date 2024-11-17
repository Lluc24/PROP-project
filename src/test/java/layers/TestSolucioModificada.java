package layers;

import layers.domain.Algorisme;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.Producte;
import layers.domain.SolucioModificada;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Classe de testeig de SolucioModificada.java
 */
public class TestSolucioModificada {
    ArrayList<String> productes = new ArrayList<String>();
    Algorisme alg;
    SolucioModificada solucioModificada;

    @Before
    public void Inicialitza(){
        for (int i = 0; i < 4; i++) {
            productes.add("p"+i);
        }
        alg = mock(Algorisme.class);

        solucioModificada = new SolucioModificada(productes, alg, "Solucio1");
    }

    /**
     * Test de constructora
     * Valors estudiats: Crea una instància de SolucioModificada i comprova que els paràmetres siguin correctes.
     */
    @Test
    public void testConstructor() {
        assertEquals("Verificar nom", "Solucio1", solucioModificada.getNom());
        assertEquals("Verificar algorisme", alg, solucioModificada.getAlgorisme());
        assertEquals("Verificar productes", productes, solucioModificada.getSolucio());
    }

    /**
     * Test de intercanvia
     * Valors estudiats: Intercanvia el primer i el segon producte de la solucio.
     */
    @Test
    public void testIntercanvia1() {
        try {
            solucioModificada.intercanvia(productes.get(0), productes.get(1));
        }catch (IntercanviNoValid e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> productesIntercanviats = productes;
        productesIntercanviats.add(0, productes.get(1));
        productesIntercanviats.add(1, productes.get(0));

        assertEquals("Verificar intercanvi", productesIntercanviats, solucioModificada.getSolucio());
    }

    /**
     * Test de intercanvia
     * Valors estudiats: Intercanvia el primer i el últim producte de la solucio.
     */
    @Test
    public void testIntercanvia2() {
        try {
            solucioModificada.intercanvia(productes.get(0), productes.get(3));
        }catch (IntercanviNoValid e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> productesIntercanviats = productes;
        productesIntercanviats.add(0, productes.get(3));
        productesIntercanviats.add(3, productes.get(0));

        assertEquals("Verificar intercanvi", productesIntercanviats, solucioModificada.getSolucio());
    }

    /**
     * Test de intercanvia
     * Valors estudiats: Intenta intercanviar el mateix producte amb ell mateix i no ha d'afectar a la solució.
     */
    @Test
    public void testIntercanvia3() {
        try {
            solucioModificada.intercanvia(productes.getFirst(), productes.getFirst());
        }catch (IntercanviNoValid e){
        System.out.println(e.getMessage());
        }

        assertEquals("Verificar intercanvi", productes, solucioModificada.getSolucio());
    }

    }
