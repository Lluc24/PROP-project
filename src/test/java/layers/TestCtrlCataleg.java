package layers;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import layers.domain.controllers.CtrlCataleg;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import layers.domain.utils.Pair;
import layers.domain.Producte;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author Alejandro Lorenzo Navarro
 */
public class TestCtrlCataleg {

    public CtrlCataleg ctrlCtrlCatalegTest;

    @Test
    public void TestCataleg() { }

    @Before
    public void OmplirCataleg() {

        CtrlCataleg aux_cat = new CtrlCataleg();

        for (int index_prod = 0; index_prod < 10; ++index_prod) {
            String nom_prod = "Prod_"+index_prod;
            Pair<String, Double>[] llista_simi = new Pair[index_prod];
            for (int p = 0; p < index_prod; ++p) {

                String simi_prod = "Prod_" + p;
                double simi = index_prod;
                Pair<String, Double> pair_simi = new Pair<>(simi_prod, simi);
                llista_simi[p] = pair_simi;
            }
            try {
                aux_cat.afegir_producte(nom_prod, llista_simi);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            } catch (FormatInputNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        this.ctrlCtrlCatalegTest = aux_cat;

    }

    @Test
    public void TestOmplir_Producte() {
        //Comprovació funció omplir productes funciona
        //Mockito
        Producte producteMock = mock(Producte.class);
        for (int i = 0; i < 10; ++i) {
            when(producteMock.getNom()).thenReturn("Prod_" + i);
        }

        int size_ini = ctrlCtrlCatalegTest.num_prod_act();
        for (int i = 0; i < size_ini; ++i) {
            assertEquals("Producte existeix", "Prod_" + i, ctrlCtrlCatalegTest.getProd_index(i).getNom());
            for (int j = 0; j < size_ini; ++j) {
                if (i == j) assertEquals("Similituds correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), 0.0, 0.0);
                else if (j < i) assertEquals("Similituds correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), i, 0.01);
                else if (j > i) assertEquals("Similituds correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), j, 0.01);
            }
        }
    }

    @Test
    public void TestAfegir_producte() {
        //Mockito
        Producte producteMock = mock(Producte.class);
        for (int i = 0; i < 11; ++i) {
            when(producteMock.getNom()).thenReturn("Prod_" + i);
        }
        //Afegim un nou producte
        int size_ini = ctrlCtrlCatalegTest.num_prod_act();
        String new_prod = "Prod_"+size_ini;
        Pair<String,Double>[] simi = new Pair[size_ini];
        for (int i = 0; i < simi.length; ++i) {
            double d_simi = size_ini*1.0;
            Pair<String, Double> p = new Pair<>("Prod_"+i, d_simi);
            simi[i] = p;
        }
        try {
            ctrlCtrlCatalegTest.afegir_producte(new_prod, simi);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        //Comprovació si ha funcionat correctament
        int size2 = ctrlCtrlCatalegTest.num_prod_act();
        assertEquals("Mida correcte", size_ini+1, size2);
        int new_index = ctrlCtrlCatalegTest.get_index_prod(new_prod);
        assertEquals("Index correcte", new_index, size_ini);

        for (int i = 0; i < size2; ++i) {
            assertEquals("Producte existeix", "Prod_"+i, ctrlCtrlCatalegTest.getProd_index(i).getNom());
            for (int j = 0; j < size2; ++j) {
                if (i == j) assertEquals("Similituds(i==j) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), 0.0, 0.0);
                else if (j < i) assertEquals("Similituds(j<i) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), i, 0.0);
                else if (j > i) assertEquals("Similituds(j>i) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), j, 0.0);
            }
        }
    }

    @Test
    public void TestAfegir_producte_OrdreInvers() {
        //Mockito
        Producte producteMock = mock(Producte.class);
        for (int i = 0; i < 11; ++i) {
            when(producteMock.getNom()).thenReturn("Prod_" + i);
        }
        //Afegim un nou producte
        int size_ini = ctrlCtrlCatalegTest.num_prod_act();
        String new_prod = "Prod_"+size_ini;

        //Aquesta llista es fara en ordre invers al ordre de cataleg
        Pair<String,Double>[] simi = new Pair[size_ini];
        for (int i = simi.length-1; i >= 0; --i) {
            double d_simi = size_ini*1.0;
            Pair<String, Double> p = new Pair<>("Prod_"+i, d_simi);
            simi[i] = p;
        }
        try {
            ctrlCtrlCatalegTest.afegir_producte(new_prod, simi);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        //Comprovació si ha funcionat correctament
        int size2 = ctrlCtrlCatalegTest.num_prod_act();
        assertEquals("Mida correcte", size_ini+1, size2);
        int new_index = ctrlCtrlCatalegTest.get_index_prod(new_prod);
        assertEquals("Index correcte", new_index, size_ini);

        for (int i = 0; i < size2; ++i) {
            assertEquals("Producte existeix", "Prod_"+i, ctrlCtrlCatalegTest.getProd_index(i).getNom());
            for (int j = 0; j < size2; ++j) {
                if (i == j) assertEquals("Similituds(i==j) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), 0.0, 0.0);
                else if (j < i) assertEquals("Similituds(j<i) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), i, 0.0);
                else if (j > i) assertEquals("Similituds(j>i) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), j, 0.0);
            }
        }
    }

    /**
     * Test del metode Afegir_productes, per vuere si al no trobar cert producte per afegir la similitud
     * El cataleg de productes no queda modificat.
     */
    @Test
    public void TestAfegir_Producte_Incorrecte() {
        Producte producteMock = mock(Producte.class);
        for (int i = 0; i < 11; ++i) {
            when(producteMock.getNom()).thenReturn("Prod_" + i);
        }
        //Afegim un nou producte
        int size_ini = ctrlCtrlCatalegTest.num_prod_act();
        String new_prod = "Prod_"+size_ini;
        Pair<String,Double>[] simi = new Pair[size_ini];
        for (int i = 0; i < simi.length; ++i) {
            double d_simi = size_ini*1.0;
            Pair<String, Double> p;
            if (i == 5) p = new Pair<>("Aborta", d_simi);
            else p = new Pair<>("Prod_"+i, d_simi);
            simi[i] = p;
        }
        try {
            ctrlCtrlCatalegTest.afegir_producte(new_prod, simi);
        } catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        //Comprovació si ha funcionat correctament
        int size2 = ctrlCtrlCatalegTest.num_prod_act();
        assertEquals("Mida correcte", size_ini, size2);

        for (int i = 0; i < size2; ++i) {
            assertEquals("Producte existeix", "Prod_"+i, ctrlCtrlCatalegTest.getProd_index(i).getNom());
            for (int j = 0; j < size2; ++j) {
                if (i == j) assertEquals("Similituds(i==j) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), 0.0, 0.0);
                else if (j < i) assertEquals("Similituds(j<i) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), i, 0.0);
                else if (j > i) assertEquals("Similituds(j>i) correcte", ctrlCtrlCatalegTest.getSimilitud_index(i, j), j, 0.0);
            }
        }
    }

    @Test
    public void TestAfegir_producte_Inicialitzar() {
        CtrlCataleg aux = new CtrlCataleg();
        String prod = "Primer";
        try {
            aux.afegir_producte(prod);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
        assertEquals("La mida es correcta", 1, aux.num_prod_act());
        assertEquals("Nom es correcte", "Primer", aux.getNomProd_index(0));
        ArrayList<Double> simis = new ArrayList<>();
        simis.add(0.0);
        assertEquals("Similituds correctes", simis, aux.getAllSimilitudsProd_nom("Primer"));
    }


    @Test
    public void TestEliminarProducte() {
        int size_ini = ctrlCtrlCatalegTest.num_prod_act();
        String[] noms_eliminats = new String[3];
        int[] index_eliminats = {0,5,size_ini-3}; //El primer, un del mig, l'ultim, elements

        for (int i = 0; i < 3; ++i) {
            String nom_out = ctrlCtrlCatalegTest.getNomProd_index(index_eliminats[i]);
            noms_eliminats[i] = nom_out;
            try {
                ctrlCtrlCatalegTest.eliminar_producte_nom(nom_out);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals("Nova mida del cataleg", size_ini-3, ctrlCtrlCatalegTest.num_prod_act());

        //Indexos ha variat correctament
        for (int i = 0; i < ctrlCtrlCatalegTest.num_prod_act(); ++i) {
            assertEquals("Index"+i +" Correctes", i, ctrlCtrlCatalegTest.getProd_index(i).getIndex());
        }

        //Produtes han sigut eliminats
        for (int i = 0; i < 3; ++i) {
            assertFalse("Producte ha sigut eliminat", ctrlCtrlCatalegTest.find_prod(noms_eliminats[i]));
        }

        //Similituds eliminades, (queda inventar una forma de veure que les similituds estan be, que ho estan).
        /*
        int size_ini2 = CatalegTest.num_prod_act();
        for (int i = 0; i < size_ini2; ++i) {
            assertEquals("Mida similituds correcte", size_ini2, CatalegTest.getProd_index(i).getSimilituds().size());
            for (int j = 0; j < size_ini2; ++j) {
                if (i == j) assertEquals("Similituds(i==j), correcte", CatalegTest.getSimilitud_index(i, j), 0,0.0);
                else if (j < i) assertEquals("Similituds(j<i) correcte", CatalegTest.getSimilitud_index(i, j), i,0.0);
                else if (j > i) assertEquals("Similituds("+j+">"+i+") correcte", CatalegTest.getSimilitud_index(i, j), j,0.0);
            }
        }
         */
    }

    @Test
    public void TestEditarSimilitud() {
        //Valors del Mig
        int index1 = 3;
        int index2 = 7;
        try {
            ctrlCtrlCatalegTest.editar_similitud(ctrlCtrlCatalegTest.getNomProd_index(index1), ctrlCtrlCatalegTest.getNomProd_index(index2), 99.99);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Similitud producte editat", ctrlCtrlCatalegTest.getProd_index(index1).get_simil_prod(index2), 99.99, 0.0);
        assertEquals("Similitud producte editat", ctrlCtrlCatalegTest.getProd_index(index2).get_simil_prod(index1), 99.99, 0.0);

        //Valor iguals
        index1 = 3;
        index2 = 3;
        try {
            ctrlCtrlCatalegTest.editar_similitud(ctrlCtrlCatalegTest.getNomProd_index(index1), ctrlCtrlCatalegTest.getNomProd_index(index2), 99.99);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
        catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        assertEquals("Similitud producte editat", ctrlCtrlCatalegTest.getProd_index(index1).get_simil_prod(index2), 0.0, 0.0);
        assertEquals("Similitud producte editat", ctrlCtrlCatalegTest.getProd_index(index2).get_simil_prod(index1), 0.0, 0.0);

    }

    @Test
    public void TestFind_prod() {
        assertTrue("Producte trobat correcte", ctrlCtrlCatalegTest.find_prod("Prod_1"));
        assertFalse("Producte no trobat correcte", ctrlCtrlCatalegTest.find_prod("NoEsTrobaACataleg"));
    }

    @Test
    public void TestValida_index() {
        Random rand = new Random();
        int rand_index = rand.nextInt(ctrlCtrlCatalegTest.num_prod_act());
        assertTrue("Index valid correcte", ctrlCtrlCatalegTest.valida_index(rand_index));
        assertTrue("Index valid correcte", ctrlCtrlCatalegTest.valida_index(0));
        assertFalse("Index no valid correcte", ctrlCtrlCatalegTest.valida_index(ctrlCtrlCatalegTest.num_prod_act()));
        assertFalse("Index no valid correcte", ctrlCtrlCatalegTest.valida_index(ctrlCtrlCatalegTest.num_prod_act()+10));
        assertFalse("Index no valid correcte", ctrlCtrlCatalegTest.valida_index(-5));


    }

    @Test
    public void TestIndex_prod() {
        assertEquals("Index correcte", ctrlCtrlCatalegTest.get_index_prod("Prod_1"), 1);
        assertEquals("Index correcte", ctrlCtrlCatalegTest.get_index_prod("No es troba a cataleg"), -1);
        assertNotEquals("Index incorrecte", ctrlCtrlCatalegTest.get_index_prod("Prod_3"),1);
        int last = ctrlCtrlCatalegTest.num_prod_act() -1;
        assertEquals("Index final correcte", ctrlCtrlCatalegTest.get_index_prod("Prod_"+last), last);
    }

    @Test
    public void TestGetNomProd_index() {
        assertEquals("Nom correcte", ctrlCtrlCatalegTest.getNomProd_index(1), "Prod_1");
        assertEquals("Nom correcte", ctrlCtrlCatalegTest.getNomProd_index(-1), null);
        assertNotEquals("Nom incorrecte", ctrlCtrlCatalegTest.getNomProd_index(1),"Prod_3");
    }

    @Test
    public void TestGetProd_index() {
        assertEquals("Producte correcte", ctrlCtrlCatalegTest.getProd_index(1).getNom(), "Prod_1");
        assertEquals("Producte correcte", ctrlCtrlCatalegTest.getProd_index(-1), null);
        assertNotEquals("Producte incorrecte", ctrlCtrlCatalegTest.getProd_index(1).getNom(),"Prod_3");
    }

    @Test
    public void TestGetProd_nom() {
        assertEquals("Producte correcte", ctrlCtrlCatalegTest.getProd_nom("Prod_1").getNom(), "Prod_1");
        assertEquals("Producte correcte", ctrlCtrlCatalegTest.getProd_nom("NoEsTrobaCataleg"), null);
        assertNotEquals("Producte incorrecte", ctrlCtrlCatalegTest.getProd_nom("Prod_1").getNom(),"Prod_3");
    }

    @Test
    public void TestGetSimilitud_nom() {
        assertEquals("Similitud correcte", ctrlCtrlCatalegTest.getSimilitud_nom("Prod_1", "Prod_2"), 2, 0.0);
        assertEquals("Similitud correcte", ctrlCtrlCatalegTest.getSimilitud_nom("Prod_1", "Prod_1"), 0, 0.0);
        assertNotEquals("Similitud correcte", ctrlCtrlCatalegTest.getSimilitud_nom("Prod_1", "NoExisteix"), 2, 0.0);
        assertNotEquals("Similitud correcte", ctrlCtrlCatalegTest.getSimilitud_nom("Prod_1", "Prod_3"), 2, 0.0);
    }

    @Test
    public void TestNum_prod_act() {
        assertEquals("Mida correcte", 10, ctrlCtrlCatalegTest.num_prod_act());
    }

    @Test
    public void TestGetAllSimilitudsProd_nom() {
        ArrayList<Double> simi = new ArrayList<>();
        for (int i = 0; i < ctrlCtrlCatalegTest.num_prod_act(); ++i) {
            simi.add(i*1.0);
        }

        assertEquals("Totes les similituds correctes", simi, ctrlCtrlCatalegTest.getAllSimilitudsProd_nom("Prod_0"));
    }

    @Test
    public void TestGetCataleg() {
        ArrayList<Producte> Array_Prod = ctrlCtrlCatalegTest.getCataleg();

        for (int i = 0; i < ctrlCtrlCatalegTest.num_prod_act(); ++i) {
            assertEquals("Es producte"+i+" correcte", Array_Prod.get(i), ctrlCtrlCatalegTest.getProd_index(i));
        }
    }

    @Test
    public void TestGetMatriuSimilituds() {

        int size = ctrlCtrlCatalegTest.num_prod_act();
        double[][] mat = ctrlCtrlCatalegTest.getMatriuSimilituds();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i == j) assertEquals("Similituds correcte", mat[i][j], 0, 0.0);
                else if (j < i) assertEquals("Similituds correcte", mat[i][j], i, 0.0);
                else if (j > i) assertEquals("Similituds correcte", mat[i][j], j, 0.0);
            }
        }
    }

    @Ignore
    @Test
    public void TestMostratCataleg() {
        ctrlCtrlCatalegTest.mostrarCataleg();
    }

    @Ignore
    @Test
    public void TestMostraProducte() {
        ctrlCtrlCatalegTest.mostrarProducte(0);
    }


}