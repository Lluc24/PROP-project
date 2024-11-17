package layers;


import layers.domain.utils.MergeFindSet;
import org.junit.*;
import static org.junit.Assert.*;

public class TestMergeFindSet {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Inici test unitari MergeFindSet");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Fi test unitari MergeFindSet");
    }

    @Test
    public void testGetNombreConjunts() {
        MergeFindSet mfs = new MergeFindSet(0);
        assertEquals("Test: Nombre de subconjunts inicials amb zero vertexs", 0, mfs.getNombreConjunts());
        mfs = new MergeFindSet(13);
        assertEquals("Test: Nombre de subconjunts inicials amb diversos vertexs", 13, mfs.getNombreConjunts());
        mfs.unir(4, 11);
        assertEquals("Test: Nombre de subconjunts despres d'unir dos subconjunts", 12, mfs.getNombreConjunts());
        mfs.unir(4, 11);
        assertEquals("Test: Nombre de subconjunts despres d'intentar unir dos vertexs del mateix subconjunt", 12, mfs.getNombreConjunts());
    }

    @Test
    public void testBuscar() {
        MergeFindSet mfs = new MergeFindSet(5);
        for (int i = 0; i < 5; ++i) {
            assertEquals("Test: buscar el pare del vertex quan esta recent inicialitzat", i, mfs.buscar(i));
        }
        mfs.unir(0, 1);
        boolean expr = (mfs.buscar(0) == mfs.buscar(1)) && ((mfs.buscar(0) == 0) || mfs.buscar(0) == 1);
        assertTrue("Test: Buscar el pare de dos vertexs units", expr);
        assertEquals("Test: Buscar el pare de d'un vertex no unit", 2, mfs.buscar(2));
        mfs.unir(1, 2);
        int pare = mfs.buscar(0);
        expr = (pare == mfs.buscar(1)) && (pare == mfs.buscar(2));
        assertTrue("Test: Buscar el pare de dos conjunts units retorna el pare que previament tenia un subconjunt major", expr);
    }

    @Test
    public void testUnir() {
        MergeFindSet mfs = new MergeFindSet(17);
        assertTrue("Test: Unir dos conjunts diferents", mfs.unir(1, 7));
        assertFalse("Test: Unir dos vertexs del mateix conjunt", mfs.unir(1, 7));
    }
}
