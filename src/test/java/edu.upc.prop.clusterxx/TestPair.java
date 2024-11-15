import edu.upc.prop.clusterxx.*;
import static org.junit.Assert.*;

import edu.upc.prop.clusterxx.pair;
import edu.upc.prop.clusterxx.Producte;
import edu.upc.prop.clusterxx.Cataleg;

import org.junit.Test;

public class TestPair {

    @Test
    public void TestPair() {

        //Proba instancia unitaria
        String a = "Proba_Pair";
        Double d = 1.0;
        pair<String, Double> unit = new pair<>(a, d);
        assertEquals("Proba first",a, unit.first);
        assertEquals("Proba second",d, unit.second);


        //Proba array de pair
        //Creacio array
        pair<String, Double>[] array = new pair[5];
        for (int i = 0; i < 5; ++i) {
            String prod = "Prod_" + i;
            pair<String, Double> aux = new pair<>(prod, i * 1.0);
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

