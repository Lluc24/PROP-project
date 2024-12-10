package layers.presentation.controllers;
import layers.domain.controllers.*;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import layers.domain.utils.Pair;
import layers.presentation.views.*;

import java.util.Objects;

public class CtrlVistaCatalegAmbRestriccions extends CtrlVistaGeneric {


    //Atributs
    private CtrlCatalegAmbRestriccions ctrl;
    private VistaPrincipalCataleg vistaPrincCat;
    private VistaAfegirProducte vistaAfegProd;
    private VistaInfoProducte vistaInfoProd;
    private VistaConsultarRest vistaConsRest;
    private String prodAct = null;


    //Mètodes

    /*
    public CtrlVistaCatalegAmbRestriccions(CtrlGeneric ctrlGeneric) {
        super(ctrlGeneric);

        ctrl = (CtrlCatalegAmbRestriccions) ctrlGeneric;
        vistaPrincCat = new VistaPrincipalCataleg(this);
        vistaAfegProd = new VistaAfegirProducte(this);
        vistaInfoProd = new VistaInfoProducte(this);
        vistaConsRest = new VistaConsultarRest(this);
    }
    */

    /** Constructora
     * @param ctrlCat Singleton del controlador de catàleg amb restriccions
     */
    public CtrlVistaCatalegAmbRestriccions(CtrlCatalegAmbRestriccions ctrlCat) {

        this.ctrl = ctrlCat;
        vistaPrincCat = new VistaPrincipalCataleg(this);
        vistaAfegProd = new VistaAfegirProducte(this);
        //vistaInfoProd = new VistaInfoProducte(this);
        vistaConsRest = new VistaConsultarRest(this);
    }

    /**
     * Canvia la vista actual a la vista indicada pel nom.
     * Si ja existeix una vista, aquesta es tanca abans de mostrar la nova.
     *
     * @param nomVista El nom de la vista a la qual es vol canviar.
     */
    public void canviaVista(String nomVista) {


        if (Objects.equals(nomVista, "AfegirProductes")) {
            vistaAfegProd.executar();
        }

        else if (Objects.equals(nomVista, "ConsultarRestriccions")) {
            vistaConsRest.executar();
        }

        else {
            System.err.println("No s'ha trobat la vista amb nom: " + nomVista); //substituir per excepció després
        }
    }

    public void canviarVista(String nomVista, String nomProd) {

        if (Objects.equals(nomVista, "InfoProducte")) {
            this.vistaInfoProd = new VistaInfoProducte(this, nomProd);
            this.prodAct = nomProd;
            vistaInfoProd.executar();

        }
    }

    public void afegirProducte(String nomProd, String[] similituds, String[] restriccionsArray) {

        if (getNumProd() == 0) {
            try {
                ctrl.afegir_producte(nomProd);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        else {
            try {
                ctrl.afegir_producte(nomProd, stringV_a_pairV(similituds));
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            } catch (FormatInputNoValid e) {
                System.out.println(e.getMessage());
            }

            for (String s : restriccionsArray) {

                try {
                    ctrl.setRestrConsecNom(nomProd, s);
                } catch (ProducteNoValid e) {
                    System.out.println(e.getMessage());
                }
            }

        }

    }

    /**
     * Elimina un producte del catàleg.
     *
     * @param nomProd El nom del producte que es vol eliminar.
     */
    public void eliminarProducte(String nomProd) {

        try {
            ctrl.eliminar_producte_nom(nomProd);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

    }


    public void editarSimilitud(String nomProd, String nomProd2, String simil) {

        double similDouble = Double.parseDouble(simil);
        try {
            ctrl.editar_similitud(nomProd, nomProd2, similDouble);
        } catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

    }


    public void afegirRestriccio(String prod1, String prod2) {

        try {
            ctrl.setRestrConsecNom(prod1, prod2);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }


    public void eliminarRestriccio(String prod1, String prod2) {

        try {
            ctrl.remRestrConsecNom(prod1, prod2);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void executar() {
        vistaPrincCat.executar();
    }



    public String[] getSimilituds() {
        return ctrl.getSimilituds_array(prodAct);
    }

    public String[] getProductes() {
        return ctrl.getProductes_array();
    }

    public int getNumProd() {
        return ctrl.num_prod_act();
    }

    public boolean findProd(String nomProd) {
        return ctrl.find_prod(nomProd);
    }


    private Pair<String, Double>[] stringV_a_pairV(String[] similituds) {

        //int numProd = getNumProd();
        int numProd = similituds.length;
        Pair<String, Double>[] llistaSim = new Pair[numProd];

        for (int i = 0; i < numProd; ++i) {
            llistaSim[i].first = ctrl.getNomProd_index(i);
            llistaSim[i].second = Double.parseDouble(similituds[i]);
        }

        return llistaSim;
    }




    //Legacy

    /*
    public void editarProducte(String str) {

        //Opció 1: nova funció de catàleg
        //ctrlCataleg.editar_prod(str);

        //Opció 2: eliminar i afegir un nou amb les modificacions


        String nomProd = decodificar_producte(str);

        try {
            ctrl.eliminar_producte_nom(nomProd);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

        if (ctrl.num_prod_act() == 0) {
            try {
                ctrl.afegir_producte(nomProd);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        else {

            //decodificació de paràmetres

            try {
                ctrl.afegir_producte(nomProd); //afegir paràmetres
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }




    }


    public String[][] infoCataleg() {

        return decodificar_cataleg(ctrl.cataleg_a_String());

    }


    public String infoProducte(String nomProd) {

        try {
            return ctrl.producte_a_String(nomProd);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    public void afegirProducte(String str) {

        String[] infoProd = decodificar_producte(str);

        if (ctrl.num_prod_act() == 0) {
            try {
                ctrl.afegir_producte(infoProd[0]);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        else {

            //importar pair a presentacio
            Pair<String, Double>[] similituds = new Pair[(infoProd.length - 1)/2];

            int j = 0;
            for (int i = 1; i + 1 < infoProd.length; i += 2) {

                Double d = Double.valueOf(infoProd[i+1]);
                Pair<String, Double> pair_sim = new Pair<>(infoProd[i], d);
                similituds[j] = pair_sim;

                ++j;
            }

            try {
                ctrl.afegir_producte(infoProd[0], similituds); //afegir paràmetres
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            } catch (FormatInputNoValid e2) {
                System.out.println(e2.getMessage());
            }
        }

    }


    public String veureRestriccions() {

        return ctrl.restr_a_String(); //editar per retornar un string que es decodifiqui a la vista
    }

    public void afegirRestriccio(String str) {

        String[] nomProds = decodificar_producte(str);

        try {
            ctrl.setRestrConsecNom(nomProds[0], nomProds[1]);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }


    public void eliminarRestriccio(String str) {

        String[] nomProds = decodificar_producte(str);

        try {
            ctrl.remRestrConsecNom(nomProds[0], nomProds[1]);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    //Decodificacions

    private String[][] decodificar_cataleg(String str){

        String[] decodificacio_primaria = str.split(";");

        String[][] decodificacio = new String[(decodificacio_primaria.length + 1)/2][decodificacio_primaria.length];

        //veure cas catàleg buit
        for (int i = 0; i < decodificacio_primaria.length; ++i) {

            decodificacio[i] = decodificacio_primaria[i].split(";");
        }

        return decodificacio;
    }

    private String[] decodificar_producte(String str) {

        return str.split(",");

    }

    */

}

