package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Iterator;

public class GestioSolucio {
    // Llista de solucions
    private ArrayList<Solucio> solucions;//llista de solucions que tracta
    private Cataleg cataleg;// relació amb el catàleg
    private Algorisme algorismeAct; //algorisme de la solució que esta tractant

    // Constructora
    public GestioSolucio(Cataleg c){
        this.solucions = new ArrayList<Solucio>();
        this.cataleg = c;
        this.algorismeAct = new Aproximacio(); //per defecte, el algorismeAct és d'aproximació
    }

    //Getters i setters

    public ArrayList<Solucio> getSolucions(){ return solucions; }

    public Cataleg getCataleg(){ return cataleg; }

    public Algorisme getAlgorismeAct(){ return algorismeAct;}


    //Mètodes addicionals
    /**
     * pre: l'usuari crida aquesta funcio passant el tipus d'algorisme (i més endevant pasarà paràmetres)
     * post: S'ha creat una instància d'Algorisme amb els paràmetres indicats
     * @param tipusAlgorisme
     */
    public void gestioAlgorisme(String tipusAlgorisme) {

        if (tipusAlgorisme.equals("greedy")){
            algorismeAct =  new AlgorismeGreedy();
        }
        else if (tipusAlgorisme.equals("aproximacio")){
            algorismeAct = new Aproximacio();;
        }
        else if (tipusAlgorisme.equals("algorismeBT")){
            algorismeAct = new AlgorismeBT();;
        }
        else {
            System.out.println("GestioSolucio: error al especificar el tipus d'algorisme");
        }
    }
    /**
     * pre: l'usuari crida a aquesta funcio quan vol crear una nova solucio
     * post: s'ha creat una nova instància de solucio resolta amb algorismeAct
     * @param nomSolucio : nom de la nova solucio que es vol crear
     */
    public void creaSolucio(String nomSolucio){
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSolucio)) {
                System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
                return;
            }
        }
        double[][] similituds = cataleg.getMatriuSimilituds();
        int[] solucio = algorismeAct.solucionar(similituds);

        ArrayList<Producte> llistaProd = new ArrayList<Producte>();
        for (int i: solucio){
            llistaProd.add(cataleg.getProd_index(i));
        }
        Solucio sol = new Solucio(llistaProd, algorismeAct, nomSolucio);
        solucions.add(sol);
    }

    /**
     *L'usuari crida a aquesta funcio quan vol intercanviar dos productes d'una solucio
     * @param prod1
     * @param prod2
     * @param nomSolucio
     */
    public void modificarSolucio (String prod1, String prod2, String nomSolucio){
        boolean trobat = false;
        //ArrayList<Solucio> solucionsTemp = new ArrayList<>(solucions);
        Iterator<Solucio> iterator = solucions.iterator();
        SolucioModificada solMod = null;
        while (iterator.hasNext()){
            Solucio s = iterator.next();
            if (s.getNom().equals(nomSolucio)){
                trobat = true;
                if (s.trobarProducte(prod1) && s.trobarProducte(prod2)) {
                    solMod = new SolucioModificada(s.getSolucio(), s.getAlgorisme(), nomSolucio);
                    solMod.intercanvia(prod1, prod2);
                    iterator.remove();
                    break;
                }
                else {
                    System.out.println("GestioSolucions: error no existeix algun dels productes en la solucio");
                    return;
                }

            }
        }
        if (!trobat)System.out.println("GestioSolucions: error no existeix una solucio amb aquest nom");
        else {
            solucions.add(solMod);
        }
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
        if (solucions.isEmpty()) {
            System.out.println("No hi ha solucions a mostrar");
        }
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        else {
            for (Solucio s: solucions) s.mostrarSolucio();
        }

    }

    // Obtenir una solucio especifica
    public void mostrarSolucio(String nomSol) {
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        boolean trobat = false;
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSol)) {
                s.mostrarSolucio();
                trobat = true;
                return;
            }
        }
        if (!trobat) System.err.println("No exiteix cap solucio amb aquest nom");
    }
}
