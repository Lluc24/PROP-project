package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.Solucio;
import edu.upc.prop.clusterxx.GestioSolucio;
import edu.upc.prop.clusterxx.Cataleg;
import edu.upc.prop.clusterxx.Producte;
import edu.upc.prop.clusterxx.Algorisme;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Classe de testeig de Solucio.java
 */
public class TestSolucio {

    public Solucio crearGestioSolucioTest() {
        Cataleg c = new Cataleg();
        GestioSolucio gs = new GestioSolucio(c);

        ArrayList<Solucio> solucions = new ArrayList<Solucio>();
        Algorisme algorismeAct = null;
        ArrayList<Vorac> vorac = new ArrayList<Vorac>();
        ArrayList<Aproximacio> aproximacio = new ArrayList<Aproximacio>();

        assertEquals("Verificar solucions", solucions, gs.getSolucions());
        assertEquals("Verificar cataleg", c, gs.getCataleg());
        assertEquals("Verificar algorismeAct", algorismeAct, gs.getAlgorismeAct());
        assertEquals("Verificar vorac", vorac, gs.getVorac());
        assertEquals("Verificar aproximacio", aproximacio, gs.getAproximacio());
    }





}