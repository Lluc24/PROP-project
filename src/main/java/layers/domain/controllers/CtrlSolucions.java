package layers.domain.controllers;

import layers.domain.*;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;

import java.util.ArrayList;
import java.util.Iterator;

public class CtrlSolucions extends CtrlGeneric {
    // Llista de solucions
    private ArrayList<Solucio> solucions;//llista de solucions que tracta
    private CtrlCataleg cataleg;// relació amb el catàleg
    private Algorisme algorismeAct; //algorisme de la solució que esta tractant

    // Constructora
    public CtrlSolucions(CtrlCataleg c){
        this.solucions = new ArrayList<Solucio>();
        this.cataleg = c;
        this.algorismeAct = new Aproximacio(); //per defecte, el algorismeAct és d'aproximació
    }

    //Getters i setters

    public ArrayList<Solucio> getSolucions(){ return solucions; }

    public CtrlCataleg getCataleg(){ return cataleg; }

    public String getAlgorismeAct(){
        if (algorismeAct instanceof AlgorismeBT)  return "backtracking";
        else if (algorismeAct instanceof AlgorismeGreedy) return "greedy";
        else return "aproximacio";
    }

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
            algorismeAct = new AlgorismeBT();
        }
        else {
            String missatge = "El tipus d'algorisme '" +tipusAlgorisme+ "' no existeix al sistema";
            throw new FormatInputNoValid(missatge);
        }
    }

    /**
     * L'usuari vol saber si existeix una solucio amb un nom determinat
     * @param nomSolucio nom de la solucio
     * @return retorna true si la solucio existeix, false altrament
     */
    public Boolean existeixSolucio(String nomSolucio){
        for (Solucio sol : solucions){
            if(sol.getNom().equals(nomSolucio)) return true;
        }
        return false;
    }

    /**
     * pre: l'usuari crida a aquesta funcio quan vol crear una nova solucio
     * post: s'ha creat una nova instància de solucio resolta amb algorismeAct
     * @param nomSolucio : nom de la nova solucio que es vol crear
     */
    public void creaSolucio(String nomSolucio, int prodPrestatge) throws NomSolucioNoValid, FormatInputNoValid {
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSolucio)) {
                String missatge = "Ja existeix una solucio amb nom '" +nomSolucio+ "'";
                throw new NomSolucioNoValid(missatge);
            }
        }
        double[][] similituds = cataleg.getMatriuSimilituds();
        int[] solucio = algorismeAct.solucionar(similituds);

        ArrayList<String> llistaProd = new ArrayList<String>();
        for (int i: solucio){
            llistaProd.add(cataleg.getNomProd_index(i));
        }
        Solucio sol = new Solucio(llistaProd, nomSolucio, prodPrestatge);
        solucions.add(sol);
    }

    /**
     *L'usuari crida a aquesta funcio quan vol intercanviar dos productes d'una solucio
     * @param prod1
     * @param prod2
     * @param nomSolucio
     */
    public void modificarSolucio (String prod1, String prod2, String nomSolucio) throws IntercanviNoValid, NomSolucioNoValid, FormatInputNoValid {
        boolean trobat = false;
        //ArrayList<Solucio> solucionsTemp = new ArrayList<>(solucions);
        Iterator<Solucio> iterator = solucions.iterator();
        SolucioModificada solMod = null;
        while (iterator.hasNext()){
            Solucio s = iterator.next();
            if (s.getNom().equals(nomSolucio)){
                trobat = true;
                if (s.trobarProducte(prod1) && s.trobarProducte(prod2)) {
                    solMod = new SolucioModificada(s.getSolucio(), nomSolucio);
                    solMod.intercanvia(prod1, prod2);
                    iterator.remove();
                    break;
                }
                else {
                    String missatge = "No existeix cap dels productes a la solucio amb nom '" +nomSolucio+ "'";;
                    if (s.trobarProducte(prod2)) missatge = "No existeix " +prod1+ " a la solucio amb nom '" +nomSolucio+ "'";
                    else if (s.trobarProducte(prod1)) missatge = "No existeix " +prod2+ " a la solucio '" +nomSolucio+ "'";
                    throw new NomSolucioNoValid(missatge);
                }

            }
        }
        if (!trobat){
            String missatge = "No existeix una solucio amb nom '" +nomSolucio+ "'";
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
            String missatge = "No existeix una solucio amb nom '" +nomSolucio+ "'";
            throw new NomSolucioNoValid(missatge);
        }
    }

    /**
     * pre: l'usuari crida aquesta funcio
     * post: Retorna un vector amb els noms de totes les solucions
     */
    public String[] getSolucionsNom(){
        String[] result = new String[solucions.size()];
        int i = 0;
        for (Solucio s: solucions){
            result[i] = s.getNom();
            ++i;
        }
        return result;
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
            String missatge = "No existeix una solucio amb nom '" +nomSol+ "'";
            throw new NomSolucioNoValid(missatge);
        }
    }
}
