package layers.presentation.controllers;
import layers.domain.controllers.*;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import layers.presentation.views.*;

import java.util.Objects;

public class CtrlVistaCatalegAmbRestriccions extends CtrlVistaGeneric {
    public void importar(String s1, String s2) throws FormatInputNoValid {}
    public void exportar(String s1, String s2) throws FormatInputNoValid {}

    //Atributs
    private CtrlCatalegAmbRestriccions ctrl;
    private VistaPrincipalCataleg vistaPrincCat; //vista 0
    private VistaAfegirProducte vistaAfegProd; //vista 1
    private VistaInfoProducte vistaInfoProd; //vista 2
    private VistaConsultarRest vistaConsRest; //vista 3
    private String prodAct = null;
    private enum EstatVista {
        noInicialitzada,
        noVisible,
        esVisible
    }

    EstatVista[] controlVistes = {EstatVista.noInicialitzada,EstatVista.noInicialitzada,EstatVista.noInicialitzada,EstatVista.noInicialitzada};

    //Mètodes

    /** Constructora
     * @param ctrlCat Singleton del controlador de catàleg amb restriccions.
     */
    public CtrlVistaCatalegAmbRestriccions(CtrlCatalegAmbRestriccions ctrlCat) {

        this.ctrl = ctrlCat;
        vistaPrincCat = new VistaPrincipalCataleg(this);
        vistaAfegProd = new VistaAfegirProducte(this);
        vistaInfoProd = new VistaInfoProducte(this);
        vistaConsRest = new VistaConsultarRest(this);
    }

    /**
     * Canvia la vista actual a la vista indicada pel nom.
     *
     * @param nomVista El nom de la vista a la qual es vol canviar.
     */
    public void canviaVista(String nomVista) {


        if (Objects.equals(nomVista, "AfegirProductes")) {
            controlVistes(1);
            vistaAfegProd.executar();
        }

        if (Objects.equals(nomVista, "PrincipalCataleg")) {
            controlVistes(0);
            vistaPrincCat.executar();
        }

        else if (Objects.equals(nomVista, "ConsultarRestriccions")) {
            controlVistes(3);
            vistaConsRest.executar();
        }

        else {
            System.err.println("No s'ha trobat la vista amb nom: " + nomVista); //substituir per excepció després
        }
    }

    /**
     * Canvia la vista actual a la vista indicada pel nom.
     * Canvia a una vista on es requereix el nom d'un producte.
     *
     * @param nomVista El nom de la vista a la qual es vol canviar.
     * @param nomProd El nom del producte.
     */
    public void canviarVista(String nomVista, String nomProd) {

        if (Objects.equals(nomVista, "InfoProducte")) {
            this.prodAct = nomProd;
            controlVistes(2);
            vistaInfoProd.executar(nomProd);

        }
    }

    /**
     * Afegeix un producte al catàleg, juntament amb les seves similituds i restriccions.
     * Si no hi ha cap producte existent al catàleg, només s'afegeix el nom del producte.
     * En cas contrari, es defineixen les similituds i les restriccions proporcionades.
     *
     * @param nomProd El nom del producte a afegir.
     * @param similituds Un array de valors que representen les similituds del nou producte amb els productes existents. Els valors han d'estar ordenats segons l'ordre dels productes al catàleg.
     * @param restriccionsArray Un array de noms de productes amb els quals el nou producte té restriccions de consecutivitat.
     */
    public void afegirProducte(String nomProd, String[] similituds, String[] restriccionsArray) {

        if (getNumProd() == 0) {
            try {
                ctrl.afegir_producte(nomProd);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        else {
            ctrl.afegir_producte_aux(nomProd, similituds);

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

    /**
     * Modifica la similitud entre dos productes.
     *
     * @param nomProd El nom del producte del que es vol modificar la similitud.
     * @param nomProd2 El nom del producte amb el qual es vol modificar la similitud.
     * @param simil La nova similitud.
     */
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

    /**
     * Afegeix una restricció de consecutivitat entre dos productes al catàleg.
     *
     * @param prod1 El nom del primer producte implicat en la restricció.
     * @param prod2 El nom del segon producte implicat en la restricció.
     */
    public void afegirRestriccio(String prod1, String prod2) {

        try {
            ctrl.setRestrConsecNom(prod1, prod2);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina una restricció de consecutivitat entre dos productes al catàleg.
     *
     * @param prod1 El nom del primer producte implicat en la restricció.
     * @param prod2 El nom del segon producte implicat en la restricció.
     */
    public void eliminarRestriccio(String prod1, String prod2) {

        try {
            ctrl.remRestrConsecNom(prod1, prod2);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void executar() {
        controlVistes(0);
        vistaPrincCat.executar();
    }


    /**
     * Obté les similituds del producte actual amb la resta de productes del catàleg.
     *
     * @return Un array de strings que representa les similituds del producte actual amb altres productes. Si no hi ha un producte actiu, el resultat pot ser buit.
     */
    public String[] getSimilituds() {
        return ctrl.getSimilituds_array(prodAct);
    }

    /**
     * Obté tots els noms dels productes existents al catàleg.
     *
     * @return Un array de strings que conté els noms de tots els productes actualment registrats al catàleg.
     */
    public String[] getProductes() {
        return ctrl.getProductes_array();
    }

    /**
     * Obté el nombre total de productes actualment registrats al catàleg.
     *
     * @return El nombre de productes presents al catàleg.
     */
    public int getNumProd() {
        return ctrl.num_prod_act();
    }

    /**
     * Verifica si un producte amb un nom específic existeix al catàleg.
     *
     * @param nomProd El nom del producte a buscar.
     * @return True si el producte existeix al catàleg, false en cas contrari.
     */
    public boolean findProd(String nomProd) {
        return ctrl.find_prod(nomProd);
    }

    /**
     * Obté totes les restriccions disponibles en format de cadena de text.
     *
     * @return Un array de strings amb les restriccions.
     */
    public String[] getAllRestriccions() {
        return ctrl.restr_a_String();
    }

    /**
     * Elimina una restricció especificada per una string de text que conté els noms dels productes.
     *
     * @param str String que representa la restricció (format: "producte1;producte2").
     */
    public void eliminarRestriccio(String str) {

        String[] nomProds = decodificar_producte(str);

        try {
            ctrl.remRestrConsecNom(nomProds[0], nomProds[1]);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Decodifica una cadena de text que conté dos noms de productes separats per un punt i coma.
     *
     * @param str Cadena amb el format "producte1;producte2".
     * @return Un array amb els dos noms dels productes.
     */
    private String[] decodificar_producte(String str) {

        return str.split(" ; ");
    }

    /**
     * Valida si el nom del producte especificat existeix al sistema.
     *
     * @param nomProd Nom del producte a validar.
     * @return True si el producte existeix, false en cas contrari.
     */
    public boolean valida_nom(String nomProd) {
        return ctrl.find_prod(nomProd);
    }

    /**
     * Canvia el nom a un producte.
     *
     * @param nomAnterior Nom anterior del producte.
     * @param nomNou Nou nom del producte.
     */
    public void canviarNom(String nomAnterior, String nomNou) {
        ctrl.canviar_nom(nomAnterior, nomNou);
        prodAct = nomNou;
    }

    /**
     * Mostra la vista corresponent.
     *
     * @param numVista Identificador de la vista a mostrar.
     */
    private void mostrarVista(int numVista){
        if (numVista == 0){
            vistaPrincCat.mostrar();
        }
        else if (numVista == 1){
            vistaAfegProd.mostrar();
        }
        else if (numVista == 2){
            vistaInfoProd.mostrar();
        }
        else if (numVista == 3){
            vistaConsRest.mostrar();
        }
    }

    /**
     * Oculta la vista corresponent.
     *
     * @param numVista Identificador de la vista a ocultar.
     */
    private void ocultarVista(int numVista){
        if (numVista == 0){
            vistaPrincCat.ocultar();
        }
        else if (numVista == 1){
            vistaAfegProd.ocultar();
        }
        else if (numVista == 2){
            vistaInfoProd.ocultar();
        }
        else if (numVista == 3){
            vistaConsRest.ocultar();
        }
    }

    /**
     * Mostra la vista corresponent i amaga la resta.
     *
     * @param numVista Identificador de la vista a mostrar.
     */
    private void controlVistes(int numVista) {
        for(int i = 0; i < 4; ++i){
            if (controlVistes[i] == EstatVista.noInicialitzada){
                if (i == numVista) controlVistes[i] = EstatVista.esVisible;
            }
            else {
                if (i == numVista) {
                    mostrarVista(numVista);
                    controlVistes[i] = EstatVista.esVisible;
                }
                else {
                    ocultarVista(i);
                    controlVistes[i] = EstatVista.noVisible;
                }
            }
        }

    }

    /**
     * L'usuari vol guardar el catàleg actual del sistema en un fitxer.
     *
     * @param path lloc on està el fitxer
     * @param nomArxiu nom del fitxer on es vol guardar
     * @throws FormatInputNoValid si algun dels paràmetres passats no és vàlid, es llença l'excepció
     */
    public void guardarCataleg(String path, String nomArxiu) throws FormatInputNoValid {
        ctrl.guardarCataleg(path, nomArxiu);
    }

    /**
     * L'usuari vol carregar el catàleg des d'un fitxer al sistema.
     *
     * @param path lloc on està el fitxer
     * @param nomArxiu nom del fitxer on es vol guardar
     * @throws FormatInputNoValid si algun dels paràmetres passats no és vàlid, es llença l'excepció
     */
    public void carregarCataleg(String path, String nomArxiu) throws FormatInputNoValid{
        ctrl.carregarCataleg(path,nomArxiu);
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

    */


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

}

