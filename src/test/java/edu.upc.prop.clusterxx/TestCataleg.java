import org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

import edu.upc.prop.clusterxx;

import java.util.Random;


/**
 * @Author Alejandro Lorenzo Navarro
 */
public class TestCataleg {

    public Cataleg CatalegTest;

    @Test
    public void TestCataleg() { }

    @Before
    public void OmplirCataleg {

        Cataleg aux_cat = new Cataleg();
        this.CatalegTest = aux_cat;

        for (int i = 0; i < 10; ++i) {
            String new_prod= "Prod_" + i;
            CatalegTest.afegir_producte(new_prod);
        }

        for (int i = 0; i < 10; ++i) {
            pair<String, Double> llista_Simi[] = new pair<String, Double>[10];
            for (int j = 0; j < 10; ++j) {
                if (j != i) {
                    String new_simi = "Prod_" + j;
                    double simi = 1.15 + j;
                    pair<String, Double> aux(new_simi, simi);
                    llista_Simi[j] =  aux;
                }
            }
            TestCataleg.afegir_similituds(i, llista_Simi, 0);
        }

    }


    @Test
    public void TestAfegir_Inici() {
        int size = CatalegTest.num_prod_act();
        assertEquals("Mida del cataleg correcta", size, 10);

        for (int i = 0; i < size; ++i) {
            String nomProd = "Prod_" +i;
            assertEquals("Nom Productes de catàleg", nomProd, CatalegTest.Cataleg_Productes.get(i).nom);

            for (int j = 0; j < size; ++j) {
                if (i == j) {
                    assertEquals("Validació Similitud", 0, CatalegTest.get.get_simil_prod(j));
                } else {
                    double new_simi = 1.15+j;
                    assertEquals("Validació Similitud", new_simi, CatalegTest.Cataleg_Productes.get(i).get_simil_prod(j));
                }
            }
        }
    }

    @Test
    public void TestAfegir_NouProducte() {

        String nouProd = "NouProd";
        int size = CatalegTest.num_prod_act();

        pair<String, Double>[] NovaLlista = new pair<String, Double>[size];
        for (int i = 0; i < size; ++i) {
            String aux_nom = "Prod_" + i;
            double simi = 10.5;
            pair<String, Double> aux = new pair<>(aux_nom, simi);
            NovaLlista[i] = aux;
        }

        CatalegTest.afegir_producte(nouProd);
        int index = CatalegTest.get_index_prod(nouProd);
        CatalegTest.afegir_similituds(index, NovaLlista, 1);
        int size2 = CatalegTest.num_prod_act();
        assertEquals("Mida del cataleg correcta", size2, size +1);

        String nom = CatalegTest.getNomProd_index(index);
        assertEquals("Comprovacio nom producte", nouProd, nom);

        for (int p = 0; p < size2; ++p) {
            double nova_simi = 10.5;
            if (p != index) {
                assertequals("Comprovacio nova similitud", CatalegTest.getProd_index(p).get_simil_prod(index), nova_simi);

            } else {
                for (int i = 0; i < size2;++i) {
                    if ( i != index) assertEquals("Comprovacio similitud nou producte", CatalegTest.getProd_index(p).get_simil_prod(i), nova_simi);
                    else assertEquals("Comprovacio similitud nou producte", CatalegTest.getProd_index(p).get_simil_prod(i), 0);

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

        for (int i = 0; i < CatalegTest.num_prod_act(); ++i) {
            assertEquals("Index"+i +" Correctes", i, CatalegTest.getProd_index(i).index);
        }

        for (int i = 0; i < 3; ++i) {
            assertFalse("Producte ha sigut eliminat", CatalegTest.find_prod(noms_eliminats[i]));
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

        asserEquals("Similitud producte editat", CatalegTest.getProd_index(index1).get_simil_prod(index2), 99.99);
        asserEquals("Similitud producte editat", CatalegTest.getProd_index(index2).get_simil_prod(index1), 99.99);

    }

    @Test
    public void TestGetters() {

    }


}