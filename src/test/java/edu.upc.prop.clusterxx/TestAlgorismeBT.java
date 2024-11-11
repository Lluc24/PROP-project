package test;

import main.domain.classes.Cataleg;
import main.domain.classes.Producte;
import main.domain.classes.AlgorismeBT;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Classe de testeig de AlgorismeBT
 */
public class AlgorismeBTTest {

    private Cataleg catalegMock;
    private ArrayList<Producte> productes;
    private AlgorismeBT algorismeBT;

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

        algorismeBT = new AlgorismeBT();
    }

    /**
     * Prova l'algorisme de backtracking amb una configuració de 4 productes.
     */
    @Test
    public void testResoldreQuatreProductes() {

        when(catalegMock.consultar_similitud_index(0, 1)).thenReturn(0.8);
        when(catalegMock.consultar_similitud_index(0, 2)).thenReturn(0.3);
        when(catalegMock.consultar_similitud_index(0, 3)).thenReturn(0.4);

        when(catalegMock.consultar_similitud_index(1, 0)).thenReturn(0.8);
        when(catalegMock.consultar_similitud_index(1, 2)).thenReturn(0.9);
        when(catalegMock.consultar_similitud_index(1, 3)).thenReturn(0.2);

        when(catalegMock.consultar_similitud_index(2, 0)).thenReturn(0.3);
        when(catalegMock.consultar_similitud_index(2, 1)).thenReturn(0.9);
        when(catalegMock.consultar_similitud_index(2, 3)).thenReturn(0.5);

        when(catalegMock.consultar_similitud_index(3, 0)).thenReturn(0.4);
        when(catalegMock.consultar_similitud_index(3, 1)).thenReturn(0.2);
        when(catalegMock.consultar_similitud_index(3, 2)).thenReturn(0.5);

        int[] resultat = algorismeBT.resoldre(catalegMock);
        int[] esperat = {0, 1, 2, 3};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova amb un sol producte en el catàleg.
     */
    @Test
    public void testResoldreUnProducte() {
        when(catalegMock.num_prod_act()).thenReturn(1);
        Producte prod = mock(Producte.class);
        when(prod.getIndex()).thenReturn(0);
        productes.clear();
        productes.add(prod);

        int[] resultat = algorismeBT.resoldre(catalegMock);
        int[] esperat = {0};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova amb dos productes en el catàleg.
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

        int[] resultat = algorismeBT.resoldre(catalegMock);
        int[] esperat = {0, 1};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova amb similituds zero entre tots els productes.
     */
    @Test
    public void testResoldreSimilitudZero() {

        when(catalegMock.consultar_similitud_index(anyInt(), anyInt())).thenReturn(0.0);

        int[] resultat = algorismeBT.resoldre(catalegMock);
        int[] esperat = {0, 1, 2, 3};
        assertArrayEquals(esperat, resultat);
    }

    /**
     * Prova amb similituds idèntiques entre tots els productes.
     */
    @Test
    public void testResoldreSimilitudUniforme() {

        when(catalegMock.consultar_similitud_index(anyInt(), anyInt())).thenReturn(0.5);

        int[] resultat = algorismeBT.resoldre(catalegMock);
        int[] esperat = {0, 1, 2, 3};
        assertArrayEquals(esperat, resultat);
    }
}




