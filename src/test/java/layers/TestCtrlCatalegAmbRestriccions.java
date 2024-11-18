package layers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import layers.domain.Producte;
import layers.domain.controllers.CtrlCataleg;
import layers.domain.controllers.CtrlCatalegAmbRestriccions;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import layers.domain.utils.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;


public class TestCtrlCatalegAmbRestriccions {

    private CtrlCatalegAmbRestriccions controlador;


    @Before
    public void setUp() {
        controlador = new CtrlCatalegAmbRestriccions();
    }


    @Test
    public void testAfegirProducteNou() {
        try {
            controlador.afegir_producte("Producte1");
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
            assertEquals("Producte1", controlador.getNomProd_index(0));
            assertEquals(1, controlador.num_prod_act());
            assertEquals(controlador.num_prod_act(), controlador.get_mida_noConsec());

    }

    /*
    @Ignore
    @Test
    public void testAfegitBuit() {
        controlador.producteAfegit();
        assertEquals(1, controlador.get_mida_noConsec());
    }

    @Ignore
    @Test
    public void testAfegitNoBuit() {
        controlador.producteAfegit();
        controlador.producteAfegit();
        assertEquals(2, controlador.get_mida_noConsec());
    }
    */

    @Test
    public void testProducteEliminat1() {

        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        assertEquals(10, controlador.num_prod_act());

        controlador.setRestrConsecId(0, 1);
        try {
            assertTrue(controlador.getRestrConsecID(0, 1));
        } catch (ProducteNoValid e) {
            fail("Error inesperat");
        }

        controlador.eliminar_producte_index(1);

        assertEquals(controlador.num_prod_act(), controlador.get_mida_noConsec());
        try {
            assertFalse(controlador.getRestrConsecID(0, 1));
        } catch (ProducteNoValid e) {
            fail("Error inesperat");
        }
    }


    @Test
    public void testGetRestr() {
        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        assertEquals(10, controlador.num_prod_act());

        controlador.setRestrConsecId(0, 1);
        try {
            assertTrue(controlador.getRestrConsecID(0, 1));
        } catch (ProducteNoValid e) {
            fail("Error inesperat");
        }
    }

    @Test
    public void testGetRestrMateixProd() {
        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        assertEquals(10, controlador.num_prod_act());

        controlador.setRestrConsecId(1, 1);

        try {
            assertTrue(controlador.getRestrConsecID(1, 1)); //no és un problema pels algorismes
        } catch (ProducteNoValid e) {
            fail("Error inesperat");
        }
    }

    @Test
    public void testRemRestrConsec() {
        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        assertEquals(10, controlador.num_prod_act());

        controlador.setRestrConsecId(1, 1);

        try {
            assertTrue(controlador.getRestrConsecID(1, 1)); //no és un problema pels algorismes
        } catch (ProducteNoValid e) {
            fail("Error inesperat");
        }

        controlador.remRestrConsecId(1, 1);
        try {
            assertFalse(controlador.getRestrConsecID(1, 1));
        } catch (ProducteNoValid e) {

        }
    }

    @Test
    public void testGetMatrRestrConsec() {
        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

        for (int index_prod = 0; index_prod < 2; ++index_prod) {
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

        this.controlador = aux_cat;

        assertEquals(2, controlador.num_prod_act());

        controlador.setRestrConsecId(1, 1);

        try {
            assertTrue(controlador.getRestrConsecID(1, 1)); //no és un problema pels algorismes
        } catch (ProducteNoValid e) {
            fail("Error inesperat");
        }

        boolean[][] matriuRestr = {
                {false, false},
                {false, true},
        };

        assertEquals(matriuRestr, controlador.getMatrRestrConsec());
    }

    @Test
    public void testGetMatrRestrConsecBuida() {

        boolean[][] matriuRestr = new boolean[0][0];

        assertEquals(matriuRestr, controlador.getMatrRestrConsec());
    }

    @Test
    public void TestAfegir_producte() {

        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        //Mockito
        Producte producteMock = mock(Producte.class);
        for (int i = 0; i < 11; ++i) {
            when(producteMock.getNom()).thenReturn("Prod_" + i);
        }
        //Afegim un nou producte
        int size_ini = controlador.num_prod_act();
        String new_prod = "Prod_"+size_ini;
        Pair<String,Double>[] simi = new Pair[size_ini];
        for (int i = 0; i < simi.length; ++i) {
            double d_simi = size_ini*1.0;
            Pair<String, Double> p = new Pair<>("Prod_"+i, d_simi);
            simi[i] = p;
        }
        try {
            controlador.afegir_producte(new_prod, simi);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        //Comprovació si ha funcionat correctament
        int size2 = controlador.num_prod_act();
        assertEquals("Mida correcte", size_ini+1, size2);
        int new_index = controlador.get_index_prod(new_prod);
        assertEquals("Index correcte", new_index, size_ini);
        assertEquals(size2, controlador.get_mida_noConsec());

        for (int i = 0; i < size2; ++i) {
            assertEquals("Producte existeix", "Prod_"+i, controlador.getProd_index(i).getNom());
            for (int j = 0; j < size2; ++j) {
                if (i == j) assertEquals("Similituds(i==j) correcte", controlador.getSimilitud_index(i, j), 0.0, 0.0);
                else if (j < i) assertEquals("Similituds(j<i) correcte", controlador.getSimilitud_index(i, j), i, 0.0);
                else if (j > i) assertEquals("Similituds(j>i) correcte", controlador.getSimilitud_index(i, j), j, 0.0);
            }
        }


    }

    @Test
    public void TestEliminarProducte() {

        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        int size_ini = controlador.num_prod_act();
        String[] noms_eliminats = new String[3];
        int[] index_eliminats = {0,5,size_ini-3}; //El primer, un del mig, l'ultim, elements

        for (int i = 0; i < 3; ++i) {
            String nom_out = controlador.getNomProd_index(index_eliminats[i]);
            noms_eliminats[i] = nom_out;
            try {
                controlador.eliminar_producte_nom(nom_out);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        assertEquals("Nova mida del cataleg", size_ini-3, controlador.num_prod_act());
        assertEquals(controlador.num_prod_act(), controlador.get_mida_noConsec());

        //Indexos ha variat correctament
        for (int i = 0; i < controlador.num_prod_act(); ++i) {
            assertEquals("Index"+i +" Correctes", i, controlador.getProd_index(i).getIndex());
        }

        //Produtes han sigut eliminats
        for (int i = 0; i < 3; ++i) {
            assertFalse("Producte ha sigut eliminat", controlador.find_prod(noms_eliminats[i]));
        }

    }

    @Test
    public void TestAfegir_producte_Inicialitzar() {

        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

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
        assertEquals(controlador.num_prod_act(), controlador.get_mida_noConsec());
    }

    @Test
    public void TestAfegir_Producte_Incorrecte() {

        CtrlCatalegAmbRestriccions aux_cat = new CtrlCatalegAmbRestriccions();

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

        this.controlador = aux_cat;

        Producte producteMock = mock(Producte.class);
        for (int i = 0; i < 11; ++i) {
            when(producteMock.getNom()).thenReturn("Prod_" + i);
        }
        //Afegim un nou producte
        int size_ini = controlador.num_prod_act();
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
            controlador.afegir_producte(new_prod, simi);
        } catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
        //Comprovació si ha funcionat correctament
        int size2 = controlador.num_prod_act();
        assertEquals("Mida correcte", size_ini, size2);
        assertEquals(controlador.num_prod_act(), controlador.get_mida_noConsec());

        for (int i = 0; i < size2; ++i) {
            assertEquals("Producte existeix", "Prod_"+i, controlador.getProd_index(i).getNom());
            for (int j = 0; j < size2; ++j) {
                if (i == j) assertEquals("Similituds(i==j) correcte", controlador.getSimilitud_index(i, j), 0.0, 0.0);
                else if (j < i) assertEquals("Similituds(j<i) correcte", controlador.getSimilitud_index(i, j), i, 0.0);
                else if (j > i) assertEquals("Similituds(j>i) correcte", controlador.getSimilitud_index(i, j), j, 0.0);
            }
        }
    }

}
