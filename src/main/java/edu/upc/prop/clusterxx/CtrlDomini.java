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

    /**
     * afegeix el primer producte de cataleg
     */
    public void afegirProducte(String nom){
        cataleg.afegir_producte(nom);
    }

    /**
     * afegeix un producte amb el nom i les similituds especificades
     */
    public void afegirProducte(String nom, Pair<String, Double>[] simi){
        cataleg.afegir_producte(nom, simi);
    }

    /**
     * torna el numero de productes que hi ha ara mateix al catàleg
     */
    public int getMidaCataleg(){
        return cataleg.num_prod_act();
    }

    /**
     * eliminar el producte amb nom 'nom' del cataleg
     */
    public void eliminarProducte(String nom){
        cataleg.eliminar_producte_nom(nom);
    }

    /**
     * mostrar cataleg actual
     */
    public void mostrarCataleg(){
        cataleg.mostrarCataleg();
    }

    /**
     * editar similitud de dos productes del cataleg
     */
    public void editarSimilitud(String prod1, String prod2, Double sim){
        cataleg.editar_similitud(prod1,prod2,sim);
    }

    /**
     * mostrar un producte amb nom nomProd
     */
    public void mostrarProducte(String nomProd){
        cataleg.mostrarProducte(nomProd);
    }

    /**
     * mostren totes les solucions del moment
     */
    public void mostrarSolucions(){
        gs.mostrarSolucions();
    }

    /**
     * mostra la solucio especificada si existeix
     */
    public void mostrarSolucio(String sol){
        gs.mostrarSolucio(sol);
    }

    /**
     * crea una nova solucio amb tots els productes de cataleg en aquell moment i e
     */
    public void crearSolucio(String nomSol){
        gs.creaSolucio(nomSol);
    }

    /**
     * elimina la solucio amb nomSol
     */
   public void eliminarSolucio(String nomSol){
        gs.eliminarSolucio(nomSol);
   }

    /**
     * modifca la solucio nomSol i intercanvia prod1 i prod2
     */
   public void modificarSolucio(String prod1, String prod2, String nomSol){
        gs.modificarSolucio(prod1,prod2,nomSol);
    }

    /**
     * estableix el algorisme amb nom nomAlg el algorisme per solucionar
     */
    public void canviarAlgorisme(String nomAlg){
        gs.gestioAlgorisme(nomAlg);
    }
}