package test;

import main.domain.classes.Cataleg;
import main.domain.classes.Producte;
import main.domain.classes.AlgorismeGreedy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testeig de AlgorismeGreedy
 */
public class AlgorismeGreedyTest {

    private Cataleg catalegMock;
    private ArrayList<Producte> productes;
    private AlgorismeGreedy algorismeGreedy;

    @Before
    public void setUp() {
        //Mock de la classe Cataleg
        catalegMock = mock(Cataleg.class);

        productes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Producte prod = mock(Producte.class);
            when(prod.getIndex()).thenReturn(i);
            productes.add(prod);
        }

        when(catalegMock.consultar_cataleg()).thenReturn(productes);
        when(catalegMock.num_prod_act()).thenReturn(4);
    }

    /**
     * Verifica l'algorisme greedy amb una configuració de 4 productes.
     */
    @Test
    public void testResoldreQuatreProductes() {

        when(catalegMock.consultar_similitud_index(0, 1)).thenReturn(0.8);
        when(catalegMock.consultar_similitud_index(0, 2)).thenReturn(0.2);
        when(catalegMock.consultar_similitud_index(0, 3)).thenReturn(0.6);

        when(catalegMock.consultar_similitud_index(1, 0)).thenReturn(0.8);
        when(catalegMock.consultar_similitud_index(1, 2)).thenReturn(0.5);
        when(catalegMock.consultar_similitud_index(1, 3)).thenReturn(0.9);

        when(catalegMock.consultar_similitud_index(2, 0)).thenReturn(0.2);
        when(catalegMock.consultar_similitud_index(2, 1)).thenReturn(0.5);
        when(catalegMock.consultar_similitud_index(2, 3)).thenReturn(0.3);

        when(catalegMock.consultar_similitud_index(3, 0)).thenReturn(0.6);
        when(catalegMock.consultar_similitud_index(3, 1)).thenReturn(0.9);
        when(catalegMock.consultar_similitud_index(3, 2)).thenReturn(0.3);

        algorismeGreedy = new AlgorismeGreedy(0, 1);

        int[] resultat = algorismeGreedy.resoldre(catalegMock);
        int[] esperat = {0, 1, 3, 2};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Verifica que el punt d'inici canvia cíclicament a cada iteració.
     */
    @Test
    public void testResoldreCanviInici() {

        when(catalegMock.consultar_similitud_index(anyInt(), anyInt())).thenReturn(0.5);

        algorismeGreedy = new AlgorismeGreedy(2, 4);  //Prova amb 4 iteracions

        int[] resultat = algorismeGreedy.resoldre(catalegMock);
        int[] esperat = {0, 1, 2, 3};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova un cas amb un únic producte.
     */
    @Test
    public void testResoldreUnProducte() {

        when(catalegMock.num_prod_act()).thenReturn(1);
        Producte prod = mock(Producte.class);
        when(prod.getIndex()).thenReturn(0);
        productes.clear();
        productes.add(prod);

        algorismeGreedy = new AlgorismeGreedy(0, 1);
        int[] resultat = algorismeGreedy.resoldre(catalegMock);
        int[] esperat = {0};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova amb dos productes.
     */
    @Test
    public void testResoldreDosProductes() {

        when(catalegMock.num_prod_act()).thenReturn(2);
        Producte prod1 = mock(Producte.class);
        Producte prod2 = mock(Producte.class);
        when(prod1.getIndex()).thenReturn(0);
        when(prod2.getIndex()).thenReturn(1);

        productes.clear();
        productes.add(prod1);
        productes.add(prod2);


        when(catalegMock.consultar_similitud_index(0, 1)).thenReturn(0.7);
        when(catalegMock.consultar_similitud_index(1, 0)).thenReturn(0.7);

        algorismeGreedy = new AlgorismeGreedy(0, 1);
        int[] resultat = algorismeGreedy.resoldre(catalegMock);
        int[] esperat = {0, 1};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova els getters i setters de producteInicial.
     */
    @Test
    public void testGettersSettersProducteInicial() {
        algorismeGreedy = new AlgorismeGreedy(0, 1);

        algorismeGreedy.setProducteInicial(2);
        assertEquals(2, algorismeGreedy.getProducteInicial());
    }

    /**
     * Prova els getters i setters de numIteracions.
     */
    @Test
    public void testGettersSettersNumIteracions() {
        algorismeGreedy = new AlgorismeGreedy(0, 1);

        algorismeGreedy.setNumIteracions(3);
        assertEquals(3, algorismeGreedy.getNumIteracions());
    }
}

