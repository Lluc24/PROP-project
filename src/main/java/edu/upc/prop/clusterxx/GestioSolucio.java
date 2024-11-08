package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Iterator;

public class GestioSolucio {
    // Llista de solucions
    private ArrayList<Solucio> solucions;//llista de solucions que tracta
    private Cataleg cataleg;// relació amb el catàleg
    private Algorisme algorismeAct; //algorisme de la solució que esta tractant
    private ArrayList<Vorac> vorac; //relació amb els algorismes de tipus voraç
    private ArrayList<Aproximacio> aproximacio; //relació amb els algorismes de tipus aproximacio


    // Constructora
    public GestioSolucio(Cataleg c){
        this.solucions = new ArrayList<Solucio>();
        this.cataleg = c;
        this.algorismeAct = null;
        this.vorac = new ArrayList<Vorac>();
        this.aproximacio = new ArrayList<Aproximacio>();
    }

    //Getters i setters

    public ArrayList<Solucio> getSolucions(){ return solucions }

    public Cataleg getCataleg(){ return cataleg }

    public Algorisme getAlgorismeAct(){return algorismeAct}

    public ArrayList<Vorac> getVorac(){return vorac}

    public ArrayList<Aproximacio> getAproximacio(){ return aproximacio }

    //Mètodes addicionals
    /**
     * pre: l'usuari crida aquesta funcio passant el tipus d'algorisme (i més endevant pasarà paràmetres)
     * post: S'ha creat una instància d'Algorisme amb els paràmetres indicats
     * @param tipusAlgorisme
     */
    public void gestioAlgorisme(String tipusAlgorisme) {

        if (tipusAlgorisme.equals("vorac")){
            boolean trobat = false;
            for (Vorac v: vorac){
                trobat = true;
                algorismeAct = v;
                break;
                /*
                if (v.getParametre() == parametre) {
                    algorismeAct = v;
                    trobat = true;
                }
                */
            }
            if (!trobat) {
                Vorac v = new Vorac();
                vorac.add(v);
                algorismeAct = v;
            }
        }
        else if (tipusAlgorisme.equals("aproximacio")){
            boolean trobat = false;
            for (Aproximacio a: aproximacio){
                trobat = true;
                algorismeAct = a;
                break;
                /*
                if (v.getParametre() == parametre) {
                    algorismeAct = v;
                    trobat = true;
                }
                */
            }
            if (!trobat) {
                Aproximacio a = new Aproximacio();
                aproximacio.add(a);
                algorismeAct = a;
            }
        }
        else {
            System.out.println("GestioSolucio: error al especificar el tipus d'algorisme");
        }
    }
    /**
     * pre: l'usuari crida a aquesta funcio quan vol crear una nova solucio
     * @param nomSolucio : nom de la nova solucio que es vol crear
     */
    public void creaSolucio(String nomSolucio){
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSolucio)) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        if (similituds.length() < 4) {
            for (Solucio s: solucions){
                if (s.getNom().equals(nom)) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
            }
            ArrayList<Producte> llistaProd = cataleg.getCataleg_Productes();
            Solucio sol = new Solucio(llistaProd, a, nom);
            solucions.add(sol);
        }
        else {
            double[][] similituds = cataleg.getMatriuSimilituds();
            algorismeAct.resol(similituds, nomSolucio);
        }
    }

    /**
     *L'usuari crida a aquesta funcio quan vol intercanviar dos productes d'una solucio
     * @param prod1
     * @param prod2
     * @param nomSolucio
     */
    public void modificarSolucio (Producte prod1, Producte prod2, String nomSolucio){
        boolean trobat = false;
        Iterator<Solucio> iterator = solucions.iterator();
        while (iterator.hasNext()){
            Solucio s = iterator.next();
            if (s.getNom().equals("nomSolucio")){
                trobat = true;
                if (s.trobarProducte(prod1) && s.trobarProducte(prod2)) {
                    SolucioModificada solMod = SolucioModificada(s);
                    iterator.remove();
                    solucions.add(solMod);
                    solMod.intercanvia(prod1, prod2);
                }
                else System.out.println("GestioSolucions: error no existeix algun dels productes en la solucio");
                break;
            }
        }
        if (!trobat)System.out.println("GestioSolucions: error no existeix una solucio amb aquest nom");
    }

    // Afegir una nova solució

    /**
     * pre: Algorisme crida aquesta funcio quan ja hagi calculat una nova solucio
     * post: s'ha creat una nova instància de solucio
     * @param solucio: conte els index dels productes ordenats per obtenir el benefici màxim de les similituds
     * @param a:instància del algorisme que ha solucionat el problema
     * @param nom: nom de la solucio a crear
     */
    public void afegeixSolucio(ArrayList<String> solucio, Algorisme a, String nom) {
        for (Solucio s: solucions){
            if (s.getNom().equals(nom)) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        ArrayList<Producte> llistaProd = new ArrayList<Productes>();
        for (String s: solucio){
            llistaProd.add(cataleg.getProducte(s));
        }
        Solucio sol = new Solucio(llistaProd, a, nom);
        solucions.add(sol);
    }

    // Eliminar una solució
    public void eliminarSolucio(String nomSolucio) {
        boolean trobat = false;
        Iterator<Solucio> iterator = solucions.iterator();
        while (iterator.hasNext()){
            Solucio s = iterator.next();
            if (s.getNom().equals(nomSolucio)){
                trobat = true;
                iterator.remove();
                break;
            }
        }
        if (!trobat)System.out.println("GestioSolucions: error no existeix una solucio amb aquest nom");
    }

    // Obtenir totes les solucions
    public void mostrarSolucions() {
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        for (Solucio s: solucions) s.mostrarSolucio;
    }
}
