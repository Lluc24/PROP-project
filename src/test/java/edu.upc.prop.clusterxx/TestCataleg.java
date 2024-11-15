
import static org.junit.Assert.*;
import org.junit.Before;

import edu.upc.prop.clusterxx.Pair;
import edu.upc.prop.clusterxx.Cataleg;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Random;


/**
 * @Author Alejandro Lorenzo Navarro
 */
public class TestCataleg {

    public Cataleg CatalegTest;

    @Test
    public void TestCataleg() { }

    @Before
    public void OmplirCataleg() {

        Cataleg aux_cat = new Cataleg();

        for (int index_prod = 0; index_prod < 10; ++index_prod) {
            String nom_prod = "Prod_"+index_prod;
            Pair<String, Double>[] llista_simi = new Pair[index_prod];
            for (int p = 0; p < index_prod; ++p) {

                String simi_prod = "Prod_" + p;
                double simi = index_prod;
                Pair<String, Double> pair_simi = new Pair<>(simi_prod, simi);
                llista_simi[p] = pair_simi;
            }
            aux_cat.afegir_producte(nom_prod, llista_simi);
        }

        this.CatalegTest = aux_cat;

    }

    @Test
    public void TestAfegir_producte() {
        //Comprovació funció omplir productes funciona
        int size_ini = CatalegTest.num_prod_act();
        for (int i = 0; i < size_ini; ++i) {
            assertEquals("Producte existeix", "Prod_1", CatalegTest.getProd_index(i).getNom());
            for (int j = 0; j < size_ini; ++j) {
                if (i == j) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), 0);
                else if (j < i) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), i);
                else if (j > i) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), j);
            }
        }

        //Afegim un nou producte
        String new_prod = "Nou Producte";
        Pair<String,Double>[] simi = new Pair[size_ini];
        for (int i = 0; i < simi.length; ++i) {
            Pair<String, Double> p = new Pair<>(new_prod, 99.0);
            simi[i] = p;
        }
        CatalegTest.afegir_producte(new_prod, simi);

        //Comprovació si ha funcionat correctament
        int size2 = CatalegTest.num_prod_act();
        assertEquals("Mida correcte", size_ini+1, size2);
        int new_index = CatalegTest.get_index_prod(new_prod);
        assertEquals("Index correcte", new_index, size_ini);

        for (int i = 0; i < size2; ++i) {
            assertEquals("Producte existeix", "Prod_1", CatalegTest.getProd_index(i).getNom());
            if (i == new_index) {
                for (int x = 0; x < size2; ++i) {
                    if (i == x) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, x), 0);
                    else assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, x), 99.0);
                }
            } else {
                for (int j = 0; j < size2; ++j) {
                    if (j == new_index) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), 99.0);
                    if (i == j) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), 0);
                    else if (j < i) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), i);
                    else if (j > i) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), j);

                }
            }
        }
    }

    @Test
    public void TestEliminarProducte() {
        int size_ini = CatalegTest.num_prod_act();
        Random rand = new Random();
        String[] noms_eliminats = new String[3];

        for (int i = 0; i < 3; ++i) {
            int index = rand.nextInt(size_ini);
            String nom_out = CatalegTest.getNomProd_index(index);
            noms_eliminats[i] = nom_out;
            CatalegTest.eliminar_producte_nom(nom_out);
        }

        assertEquals("Nova mida del cataleg", size_ini-3, CatalegTest.num_prod_act());

        //Indexos ha variat correctament
        for (int i = 0; i < CatalegTest.num_prod_act(); ++i) {
            assertEquals("Index"+i +" Correctes", i, CatalegTest.getProd_index(i).getIndex());
        }

        //Produtes han sigut eliminats
        for (int i = 0; i < 3; ++i) {
            assertFalse("Producte ha sigut eliminat", CatalegTest.find_prod(noms_eliminats[i]));
        }

        //Similituds eliminades
        int size_ini2 = CatalegTest.num_prod_act();
        for (int i = 0; i < size_ini2; ++i) {
            assertEquals("Mida similituds correcte", size_ini2, CatalegTest.getProd_index(i).getSimilituds().size());
            for (int j = 0; j < size_ini2; ++j) {
                if (i == j) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), 0);
                else if (j < i) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), i);
                else if (j > i) assertEquals("Similituds correcte", CatalegTest.getSimilitud_index(i, j), j);
            }
        }
    }

    @Test
    public void TestEditarSimilitud() {
        int size_ini = CatalegTest.num_prod_act();
        Random rand = new Random();
        String[] noms_editats = new String[2];

        int index1 = rand.nextInt(size_ini);
        int index2 = rand.nextInt(size_ini);

        CatalegTest.getNomProd_index(index1);
        CatalegTest.getNomProd_index(index2);

            CatalegTest.editar_similitud(noms_editats[0], noms_editats[1], 99.99);

        assertEquals("Similitud producte editat", CatalegTest.getProd_index(index1).get_simil_prod(index2), 99.99);
        assertEquals("Similitud producte editat", CatalegTest.getProd_index(index2).get_simil_prod(index1), 99.99);

    }

    @Test
    public void TestFind_prod() {
        assertTrue("Producte trobat correcte", CatalegTest.find_prod("Prod_1"));
        assertFalse("Producte no trobat correcte", CatalegTest.find_prod("NoEsTrobaACataleg"));
    }

    @Test
    public void TestValida_index() {
        Random rand = new Random();
        int rand_index = rand.nextInt(CatalegTest.num_prod_act());
        assertTrue("Index valid correcte", CatalegTest.valida_index(rand_index));
        assertTrue("Index valid correcte", CatalegTest.valida_index(0));
        assertFalse("Index no valid correcte", CatalegTest.valida_index(CatalegTest.num_prod_act()));
        assertFalse("Index no valid correcte", CatalegTest.valida_index(CatalegTest.num_prod_act()+10));
        assertFalse("Index no valid correcte", CatalegTest.valida_index(-5));


    }

    @Test
    public void TestIndex_prod() {
        assertEquals("Index correcte", CatalegTest.get_index_prod("Prod_1"), 1);
        assertEquals("Index correcte", CatalegTest.get_index_prod("No es troba a cataleg"), -1);
        assertNotEquals("Index incorrecte", CatalegTest.get_index_prod("Prod_3"),1);
    }

    @Test
    public void TestGetNomProd_index() {
        assertEquals("Nom correcte", CatalegTest.getNomProd_index(1), "Prod_1");
        assertEquals("Nom correcte", CatalegTest.getNomProd_index(-1), null);
        assertNotEquals("Nom incorrecte", CatalegTest.getNomProd_index(1),"Prod_3");
    }

    @Test
    public void TestGetProd_index() {
        assertEquals("Producte correcte", CatalegTest.getProd_index(1).getNom(), "Prod_1");
        assertEquals("Producte correcte", CatalegTest.getProd_index(-1), null);
        assertNotEquals("Producte incorrecte", CatalegTest.getProd_index(1).getNom(),"Prod_3");
    }

    @Test
    public void TestGetProd_nom() {
        assertEquals("Producte correcte", CatalegTest.getProd_nom("Prod_1").getNom(), "Prod_1");
        assertEquals("Producte correcte", CatalegTest.getProd_nom("NoEsTrobaCataleg"), null);
        assertNotEquals("Producte incorrecte", CatalegTest.getProd_nom("Prod_1").getNom(),"Prod_3");
    }

    @Test
    public void TestGetSimilitud_nom() {
        assertEquals("Similitud correcte", CatalegTest.getSimilitud_nom("Prod_1", "Prod_2"), 2);
        assertEquals("Similitud correcte", CatalegTest.getSimilitud_nom("Prod_1", "Prod_1"), 0);
        assertNotEquals("Similitud correcte", CatalegTest.getSimilitud_nom("Prod_1", "NoExisteix"), 2);
        assertNotEquals("Similitud correcte", CatalegTest.getSimilitud_nom("Prod_1", "Prod_3"), 2);
    }

    @Test
    public void TestNum_prod_act() {
        assertEquals("Mida correcte", 10, CatalegTest.num_prod_act());
    }

    @Test
    public void TestGetAllSimilitudsProd_nom() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < CatalegTest.num_prod_act(); ++i) {
            simi.add(i*1.0);
        }

        assertEquals("Totes les similituds correctes", simi, CatalegTest.getAllSimilitudsProd_nom("Prod_0"));
    }

    @Test
    public void TestGetCataleg() {
        assertEquals("Cataleg correcte", CatalegTest.getCataleg(), CatalegTest);
    }

    @Test
    public void TestGetMatriuSimilituds() {

        int size = CatalegTest.num_prod_act();
        double[][] mat = CatalegTest.getMatriuSimilituds();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i == j) assertEquals("Similituds correcte", mat[i][j], 0);
                else if (j < i) assertEquals("Similituds correcte", mat[i][j], i);
                else if (j > i) assertEquals("Similituds correcte", mat[i][j], j);
            }
        }
    }


}