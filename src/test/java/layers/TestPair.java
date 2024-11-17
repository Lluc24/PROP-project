package layers;

import static org.junit.Assert.*;

import layers.domain.utils.Pair;
import org.junit.Test;

public class TestPair {

    @Test
    public void TestPair() {

        //Proba instancia unitaria
        String a = "Proba_Pair";
        Double d = 1.0;
        Pair<String, Double> unit = new Pair<>(a, d);
        assertEquals("Proba first",a, unit.first);
        assertEquals("Proba second",d, unit.second);


        //Proba array de pair
        //Creacio array
        Pair<String, Double>[] array = new Pair[5];
        for (int i = 0; i < 5; ++i) {
            String prod = "Prod_" + i;
            Pair<String, Double> aux = new Pair<>(prod, i * 1.0);
            array[i] = aux;
        }
        //Comprovacio Array
        for (int i = 0; i < 5; ++i) {
            assertEquals("Proba vector first","Prod_"+i, array[i].first);
            Double p = i*1.0;
            assertEquals("Proba vector second",p, array[i].second);
        }
    }
}

