package edu.upc.prop.clusterxx;

import edu.upc.prop.clusterxx.Excepcions.FormatInputNoValid;
import edu.upc.prop.clusterxx.Excepcions.IntercanviNoValid;
import edu.upc.prop.clusterxx.Excepcions.NomSolucioNoValid;

import java.text.Normalizer;
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

    public void setParametres(int param1, int param2) throws FormatInputNoValid{
        algorismeAct = new AlgorismeGreedy(param1, param2);
    }

    //Mètodes addicionals
    /**
     * pre: l'usuari crida aquesta funcio passant el tipus d'algorisme (i més endevant pasarà paràmetres)
     * post: S'ha creat una instància d'Algorisme amb els paràmetres indicats
     * @param tipusAlgorisme
     */
    public void gestioAlgorisme(String tipusAlgorisme) throws FormatInputNoValid {

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
            String missatge = "Error al especificar el tipus d'algorisme";
            throw new FormatInputNoValid(missatge);
        }
    }
    /**
     * pre: l'usuari crida a aquesta funcio quan vol crear una nova solucio
     * post: s'ha creat una nova instància de solucio resolta amb algorismeAct
     * @param nomSolucio : nom de la nova solucio que es vol crear
     */
    public void creaSolucio(String nomSolucio) throws NomSolucioNoValid, FormatInputNoValid {
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSolucio)) {
                String missatge = "Ja existeix una solucio amb aquest nom";
                throw new NomSolucioNoValid(missatge);
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
    public void modificarSolucio (String prod1, String prod2, String nomSolucio) throws IntercanviNoValid, NomSolucioNoValid {
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
                    String missatge = "Error no existeix algun dels productes en la solucio";
                    throw new NomSolucioNoValid(missatge);
                }

            }
        }
        if (!trobat){
            String missatge = "No existeix una solucio amb aquest nom";
            throw new NomSolucioNoValid(missatge);
        }
        else {
            solucions.add(solMod);
        }
    }

    // Eliminar una solució
    public void eliminarSolucio(String nomSolucio) throws NomSolucioNoValid {
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
        if (!trobat){
            String missatge = "No existeix una solucio amb aquest nom";
            throw new NomSolucioNoValid(missatge);
        }
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
    public void mostrarSolucio(String nomSol) throws NomSolucioNoValid {
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        boolean trobat = false;
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSol)) {
                s.mostrarSolucio();
                trobat = true;
                return;
            }
        }
        if (!trobat) {
            String missatge = "No existeix una solucio amb aquest nom";
            throw new NomSolucioNoValid(missatge);
        }
    }
}
