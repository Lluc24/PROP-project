package edu.upc.prop.clusterxx;
import java.util.ArrayList;

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


    /**
     * pre: l'usuari crida aquesta funcio passant-me el tipus d'algorisme (i més endevant pasarà paràmetres)
     * post: S'ha creat una instància d'Algorisme amb els paràmetres indicats
     * @param tipusAlgorisme
     */
    public void gestioAlgorisme(String tipusAlgorisme) {

        if (tipusAlgorisme == vorac){
            boolean trobat = false;
            for (Vorac v: vorac && !trobat){
                trobat = true;
                algorismeAct = v;
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
        else if (tipusAlgorime == aproximacio){
            boolean trobat = false;
            for (Aproximacio a: aproximacio && !trobat){
                trobat = true;
                algorismeAct = a;
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
            if (s.getNom() == nomSolucio) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        double[][] similituds = cataleg.getMatriuSimilituds();
        if (similituds.length() < 4) System.out.println("GestioSolucions: no hi ha suficients productes a ordenar");
        algorismeAct.resol(similituds, nomSolucio);
    }

    /**
     *
     * @param prod1
     * @param prod2
     * @param nomSolucioAnt
     * @param nomSolucioNova
     */
    public void modificarSolucio (Producte prod1, Producte prod2, String nomSolucioAnt, String nomSolucioNova){
        //busco una solucio al del conjunt de solucions que tingui nom = nomSolucio
        //si no n'hi ha cap, aviso del error
        //si la trobo, en creo una copia amb nom nomSolucioNova , cridant a la funció copia(nomSolucioNova) de la classe solucio
        //afegeixo la nova solucioModificada a la llista de solucions
        //un cop creada la copia, crido a la funcio esModificada() de la copia
        //i despres crido a la funcio intercanvia(prod1,prod2)

        for (Solucio s: solucions){
            if (s.getNom() == nomSolucioNova) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        boolean trobat = false;
        for (Solucio s: solucions){
            if (s.getNom() == nomSolucioAnt){
                trobat = true;
                SolucioModificada solMod = SolucioModificada(s);
                solucions.add(solMod);
                solmod.intercanvia(prod1, prod2);
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
    public void afegeixSolucio(ArrayList<int> solucio, Algorisme a, string nom) {
        for (Solucio s: solucions){
            if (s.getNom() == nom) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        Solucio s = new Solucio(solucio, a, nom);
        solucions.add(solucio);
    }

    // Eliminar una solució
    public void eliminarSolucio(string nomSolucio) {
        boolean trobat = false;
        for (Solucio s: solucions){
            if (s.getNom() == nomSolucio){
                solucions.remove(s);
                trobat = true;
            }
        }
        if (!trobat) System.out.println("GestioSolucions: error no existeix una solucio amb aquest nom");
    }

    // Obtenir totes les solucions
    public ArrayList<Solucio> mostrarSolucions() {
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        for (Solucio s: solucions) s.mostrarSolucio;
    }
}
