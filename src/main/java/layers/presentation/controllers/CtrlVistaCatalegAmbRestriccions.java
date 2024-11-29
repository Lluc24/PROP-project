package layers.presentation.controllers;
import layers.domain.controllers.*;
import layers.domain.excepcions.ProducteNoValid;
import layers.presentation.Vista;

import java.util.HashMap;
import java.util.Map;

public class CtrlVistaCatalegAmbRestriccions extends CtrlVistaGeneric {

    //Atributs
    private Map<String, Vista> vistesCataleg; //vistes en un map on la Key és el nom de la vista
    private Vista vistaActual;
    private static CtrlCatalegAmbRestriccions ctrlCataleg = null;

    //Mètodes

    /** Constructora
     * @param SingCtrlCatalegAmbRestriccions Singleton del controlador de catàleg amb restriccions
     */

    public CtrlVistaCatalegAmbRestriccions(CtrlGeneric ctrlGeneric, CtrlCatalegAmbRestriccions SingCtrlCatalegAmbRestriccions) {
        super(ctrlGeneric);

        ctrlCataleg = SingCtrlCatalegAmbRestriccions; //singleton

        this.vistesCataleg = new HashMap<>();

        vistesCataleg.put("VistaInicialCataleg", new VistaInicialCataleg(this));
        vistesCataleg.put("VistaInfoProducte", new VistaInfoProducte(this));
        vistesCataleg.put("VistaEditarProducte", new VistaEditarProducte(this));
        vistesCataleg.put("VistaAfegirProducte", new VistaAfegirProducte(this));
        vistesCataleg.put("VistaEliminarProducte", new VistaEliminarProducte(this));
        vistesCataleg.put("VistaVeureRestriccions", new VistaVeureRestriccions(this));
        vistesCataleg.put("VistaAfegirRestriccio", new VistaAfegirRestriccio(this));
        vistesCataleg.put("VistaEliminarRestriccio", new VistaEliminarRestriccio(this));
        vistesCataleg.put("VistaEditarSimilitud", new VistaEditarSimilitud(this));

        this.vistaActual = null; //substituir per vista inicial, si hi ha

    }

    /**
     * Canvia la vista actual a la vista indicada pel nom.
     * Si ja existeix una vista, aquesta es tanca abans de mostrar la nova.
     *
     * @param nomVista El nom de la vista a la qual es vol canviar.
     */
    public void canviaVista(String nomVista) {
        if (vistaActual != null) {
            vistaActual.ocultar();
        }

        Vista novaVista = vistesCataleg.get(nomVista);
        if (novaVista != null) {
            vistaActual = novaVista;
            vistaActual.mostrar();
        } else {
            System.err.println("No s'ha trobat la vista amb nom: " + nomVista); //substituir per excepció després
        }
    }

    //Incloure mètodes per rellançar les viste un cop actualitzada la informació

    /**
     * Obté la informació del catàleg i la retorna com a cadena de text.
     * Aquesta cadena serà utilitzada per mostrar la informació a la vista.
     *
     * @return Una cadena amb la informació del catàleg.
     */
    public String infoCataleg() {

        return ctrlCataleg.mostrarCataleg(); //editar per retornar un string que es decodifiqui a la vista

    }

    /**
     * Obté la informació d'un producte específic.
     *
     * @param nomProd El nom del producte per obtenir la seva informació.
     * @return Una cadena amb la informació del producte, o null si el producte no és vàlid (no hauria de passar).
     */
    public String infoProducte(String nomProd) {

        try {
            return ctrlCataleg.mostrarProducte(nomProd); //editar per retornar un string que es decodifiqui a la vista
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Afegeix un producte al catàleg.
     *
     * @param str Les dades del producte que es vol afegir, codificat en un string.
     */
    public void afegirProducte(String str) {

        String nomProd = decodificar_nom(str);

        if (ctrlCataleg.num_prod_act() == 0) {
            try {
                ctrlCataleg.afegir_producte(nomProd);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        else {

            //decodificació de paràmetres

            try {
                ctrlCataleg.afegir_producte(nomProd); //afegir paràmetres
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Elimina un producte del catàleg.
     *
     * @param str El nom del producte que es vol eliminar.
     */
    public void eliminarProducte(String str) {

        //substituir per nomProd com a paràmetre
        String nomProd = decodificar_nom(str);

        try {
            ctrlCataleg.eliminar_producte_nom(nomProd);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Edita les dades d'un producte existent al catàleg.
     *
     * @param str Les noves dades del producte que es vol editar, codificat en un string.
     */
    public void editarProducte(String str) {

        //Opció 1: nova funció de catàleg
        //ctrlCataleg.editar_prod(str);

        //Opció 2: eliminar i afegir un nou amb les modificacions
        String nomProd = decodificar_nom(str);

        try {
            ctrlCataleg.eliminar_producte_nom(nomProd);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }

        if (ctrlCataleg.num_prod_act() == 0) {
            try {
                ctrlCataleg.afegir_producte(nomProd);
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

        else {

            //decodificació de paràmetres

            try {
                ctrlCataleg.afegir_producte(nomProd); //afegir paràmetres
            } catch (ProducteNoValid e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     *
     * @param str
     */
    public void editarSimilitud(String str) {

        //determinar flux

    }

    /**
     * Mostra les restriccions associades als productes.
     * Retorna la informació en forma de cadena per ser mostrada a la vista.
     *
     * @return Una cadena amb la informació de les restriccions.
     */
    public String veureRestriccions() {

        return ctrlCataleg.mostrarRestrConsec(); //editar per retornar un string que es decodifiqui a la vista
    }

    /**
     * Afegeix una restricció entre dos productes.
     *
     * @param str Un string que conté els noms dels dos productes als quals es vol afegir la restricció.
     */
    public void afegirRestriccio(String str) {

        String nomProd1 = decodificar_nom(str);
        String nomProd2 = decodificar_nom(str);

        try {
            ctrlCataleg.setRestrConsecNom(nomProd1, nomProd2);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina una restricció entre dos productes.
     *
     * @param str Un string que conté els noms dels dos productes dels quals es vol eliminar la restricció.
     */
    public void eliminarRestriccio(String str) {

        String nomProd1 = decodificar_nom(str);
        String nomProd2 = decodificar_nom(str);

        try {
            ctrlCataleg.remRestrConsecNom(nomProd1, nomProd2);
        } catch (ProducteNoValid e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void executar() {
        System.out.println("Metode executar de CtrlVistaCatalegAmbRestriccions");
    }

    private String decodificar_nom(String str) {

        //decodificació
        return null;
    }
}

