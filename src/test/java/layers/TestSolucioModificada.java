package layers;

import layers.domain.Algorisme;
import layers.domain.excepcions.FormatInputNoValid;
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
    ArrayList<String> productesLlista= new ArrayList<String>();
    ArrayList<ArrayList<String>> productesMatriu = new ArrayList<ArrayList<String>>();
    SolucioModificada solucioModificada;
    int prodPrestatge;

    @Before
    public void Inicialitza(){
        for (int i = 0; i < 4; i++) {
            productesLlista.add("p"+i);
        }

        productesMatriu.add(productesLlista);
        prodPrestatge = 4;

        try{
            solucioModificada = new SolucioModificada(productesMatriu, "Solucio1");
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
        assertEquals("Verificar nom", "Solucio1", solucioModificada.getNom());
        assertEquals("Verificar productes", productesMatriu, solucioModificada.getSolucio());
    }

    /**
     * Test de intercanvia
     * Valors estudiats: Intercanvia el primer i el segon producte de la solucio.
     */
    @Test
    public void testIntercanvia1() {
        try {
            solucioModificada.intercanvia(productesLlista.get(0), productesLlista.get(1));
        }catch (IntercanviNoValid e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> productesIntercanviats = productesLlista;
        productesIntercanviats.add(0, productesLlista.get(1));
        productesIntercanviats.add(1, productesLlista.get(0));
        productesMatriu.set(0,productesIntercanviats);

        assertEquals("Verificar intercanvi", productesMatriu, solucioModificada.getSolucio());
    }

    /**
     * Test de intercanvia
     * Valors estudiats: Intercanvia el primer i el últim producte de la solucio.
     */
    @Test
    public void testIntercanvia2() {
        try {
            solucioModificada.intercanvia(productesLlista.get(0), productesLlista.get(3));
        }catch (IntercanviNoValid e){
            System.out.println(e.getMessage());
        }

        ArrayList<String> productesIntercanviats = productesLlista;
        productesIntercanviats.add(0, productesLlista.get(3));
        productesIntercanviats.add(3, productesLlista.get(0));
        productesMatriu.set(0,productesIntercanviats);

        assertEquals("Verificar intercanvi", productesMatriu, solucioModificada.getSolucio());
    }

    /**
     * Test de intercanvia
     * Valors estudiats: Intenta intercanviar el mateix producte amb ell mateix i no ha d'afectar a la solució.
     */
    @Test
    public void testIntercanvia3() {
        try {
            solucioModificada.intercanvia(productesLlista.getFirst(), productesLlista.getFirst());
        }catch (IntercanviNoValid e){
        System.out.println(e.getMessage());
        }

        assertEquals("Verificar intercanvi", productesMatriu, solucioModificada.getSolucio());
    }

    }
