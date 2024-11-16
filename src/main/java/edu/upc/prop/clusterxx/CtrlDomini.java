package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.Excepcions.*;

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
        try {
            cataleg.afegir_producte(nom);
        } catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * afegeix un producte amb el nom i les similituds especificades
     */
    public void afegirProducte(String nom, Pair<String, Double>[] simi){
        try {
            cataleg.afegir_producte(nom, simi);
        }catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
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
        try {
            cataleg.eliminar_producte_nom(nom);
        }catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        }
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
        try {
            cataleg.editar_similitud(prod1, prod2, sim);
        }catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * mostrar un producte amb nom nomProd
     */
    public void mostrarProducte(String nomProd){
        try {
            cataleg.mostrarProducte(nomProd);
        }catch (ProducteNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * mostren totes les solucions del moment
     */
    public void mostrarSolucions() {
        gs.mostrarSolucions();
    }

    /**
     * mostra la solucio especificada si existeix
     */
    public void mostrarSolucio(String sol){
        try {
            gs.mostrarSolucio(sol);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * crea una nova solucio amb tots els productes de cataleg en aquell moment i e
     */
    public void crearSolucio(String nomSol){
       try {
           gs.creaSolucio(nomSol);
       }catch (NomSolucioNoValid e) {
           System.out.println(e.getMessage());
       }
    }

    /**
     * elimina la solucio amb nomSol
     */
   public void eliminarSolucio(String nomSol){
       try {
           gs.eliminarSolucio(nomSol);
       }catch (NomSolucioNoValid e) {
           System.out.println(e.getMessage());
       }
   }

    /**
     * modifca la solucio nomSol i intercanvia prod1 i prod2
     */
   public void modificarSolucio(String nomSol, String prod1, String prod2){
       try {
           gs.modificarSolucio(prod1, prod2, nomSol);
       }catch (NomSolucioNoValid e) {
           System.out.println(e.getMessage());
       }catch (IntercanviNoValid e) {
           System.out.println(e.getMessage());
       }
    }

    /**
     * estableix el algorisme amb nom nomAlg el algorisme per solucionar
     */
    public void canviarAlgorisme(String nomAlg){
        try {
            gs.gestioAlgorisme(nomAlg);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * estableix el algorisme amb nom nomAlg el algorisme per solucionar
     */
    public void canviarAlgorisme(String nomAlg, int param1, int param2){
        try {
            gs.gestioAlgorisme(nomAlg);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
        /*
        try {
            gs.setParametres(param1, param2);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }

         */
    }
}