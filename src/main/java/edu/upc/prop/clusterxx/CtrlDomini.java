package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CtrlDomini{
    private static CtrlDomini instancia = null;
    private static Cataleg cataleg = null;
    private static GestioSolucio gs = null;
    private Scanner in;

    private CtrlDomini(){}

    public static CtrlDomini getCtrlDomini(){
        if (instancia == null){
            instancia = new CtrlDomini();
            cataleg = new Cataleg();
            gs = new GestioSolucio(cataleg);
        }
        return instancia;
    }

    // per defecte--->crear un cataleg
    //afegir producte
    /**
     * crea una nova solucio amb tots els productes de cataleg en aquell moment i e
     */
    public void afegirProducte(String nom, Pair<String, Double>[] simi){
        System.out.println("Introdueix nom del producte que vols afegir i les seves similituds:");
        System.out.println("Si és el primer producte, només el nom: p.e.: galetes ");
        System.out.println("Si és el segon producte, només el nom: p.e.: aigua galetes 0.5");
        cataleg.afegir_producte(nom, );
    }

    public int getMidaCataleg(){ return cataleg.num_prod_act();}

    //eliminar producte
    //editar similituds

    /**
     * crea una nova solucio amb tots els productes de cataleg en aquell moment i e
     */
    public void gestioAlgorisme(){
        if (cataleg.num_prod_act() <= 0) {
            System.err.println("Primer s'ha d'afegir almenys un producte");
            return;
        }
        System.out.println("Introdueix nom del algorisme que vols utilitzar: greedy, aproximacio, algorismeBT");
        String alg = in.nextLine();
        gs.gestioAlgorisme(alg);
        System.out.println("Introdueix nom de la solució que vols crear:");
        String sol = in.nextLine();
        gs.creaSolucio(sol);
    }

    //gestio solucio : mostrar + modificar
    /**
     * mostra totes les solucions i et permet modificar-ne una.
     */
    public void gestioSolucions(){
        if (gs.getSolucions().isEmpty()) {
            System.err.println("No hi ha cap solucio al sistema");
            return;
        }
        gs.mostrarSolucions();
        System.out.println("Vols modificar alguna solucio? Introdueix SI/NO");
        String res = in.nextLine();
        if (res.equals("SI")){
            System.out.println("Introdueix els noms dels productes que vols intercanviar i en quina solució: nomProd1 nomProd2 nomSol");

            String input = in.nextLine();
            String[] parts = input.split("\\s+"); // Divideix per espais en blanc

            if (parts.length != 3) {
                System.err.println("Error: Has d'introduir exactament tres paraules (nomProd1 nomProd2 nomSol).");
                return;
            }

            String prod1 = parts[0];
            String prod2 = parts[1];
            String sol = parts[2];

            gs.modificarSolucio(prod1, prod2, sol);
            System.out.println("Solucio modificada correctament");
        }
    }
}