package layers;

import static org.junit.Assert.*;
import layers.domain.ControladorCatalegAmbRestriccions;
import layers.domain.controllers.CtrlCataleg;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import org.junit.Before;
import org.junit.Test;


public class TestControladorCatalegAmbRestriccions {

    private ControladorCatalegAmbRestriccions controlador;

    @Before
    public void setUp() {
        controlador = new ControladorCatalegAmbRestriccions();
    }


}
