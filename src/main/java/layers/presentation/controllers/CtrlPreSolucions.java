package layers.presentation.controllers;

import layers.domain.controllers.*;
import layers.domain.excepcions.*;
import java.util.ArrayList;
import javax.swing.*;
import layers.domain.*;

public class CtrlPreSolucions {
    private CtrlSolucions ctrlSolucions;
    private VistaInicialSolucio vistaInicialSolucio;
    private VistaInicial vistaInicial;

    public CtrlPreSolucions(VistaInicial vi, CtrlSolucions cs) {
        this.ctrlSolucions(cs);
        this.vistaInicial = vi;
        vistaInicialSolucio = new VistaInicialSolucio(this);
    }

    public void inicialitzarPresentacio(){
        vistaInicialSolucio.ferVisible();
    }

    public String botoCreaSolucio( String s){
        System.out.println("CtrlPreSol sap que has clickat el boto amb text: " +
                s );

        CtrlSolucions.creaSolucio("solucio",4);
        return "ok";
    }
}
