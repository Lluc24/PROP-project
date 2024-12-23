package layers.domain.controllers;

import layers.domain.*;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;
import layers.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe 'CtrlSolucions'
 *
 * Controlador principal per a la gestió de solucions dins del sistema. Aquesta classe permet crear, modificar, eliminar,
 * i gestionar solucions, així com interactuar amb el catàleg i els algorismes utilitzats per generar-les.
 * També proporciona funcionalitats per a carregar i guardar solucions des d'arxius.
 *
 * @see CtrlCatalegAmbRestriccions
 * @see Algorisme
 * @see Solucio
 * @see SolucioModificada
 *
 * @author Eulalia Peiret Santacana
 * @version 1.0
 *
 * <p><b>Funcionalitats principals:</b></p>
 * - <b>Crear solucions:</b> Genera noves solucions utilitzant un algorisme específic i un conjunt de restriccions.
 * - <b>Modificar solucions:</b> Permet intercanviar productes dins d'una solució o modificar-ne les posicions.
 * - <b>Eliminar solucions:</b> Elimina una solució específica del sistema.
 * - <b>Persistència:</b> Carrega i guarda solucions en fitxers externs.
 * - <b>Consulta:</b> Permet consultar si una solució existeix, obtenir-ne els detalls, i llistar totes les solucions disponibles.
 * - <b>Gestió d'algorismes:</b> Triar entre tres tipus (aproximació, backtracking, greedy) per resoldre problemes.
 *
 * <p><b>Excepcions:</b></p>
 * - <b>NomSolucioNoValid:</b> S'activa quan es treballa amb noms de solucions que no existeixen o no són vàlids.
 * - <b>FormatInputNoValid:</b> S'activa quan el format d'entrada no és vàlid, com paràmetres d'algorisme incorrectes.
 * - <b>IntercanviNoValid:</b> S'activa quan es produeix un error en intentar intercanviar productes en una solució.
 */

public class CtrlSolucions extends CtrlGeneric {
    // Llista de solucions
    private ArrayList<Solucio> solucions;//llista de solucions que tracta
    private CtrlCatalegAmbRestriccions cataleg;// relació amb el catàleg
    private Algorisme algorismeAct; //algorisme de la solució que esta tractant
    private CtrlPersistenciaSolucio ctrlPersistenciaSolucio;

    /**
     * Funcio contructora del controlador CtrlSolucions.
     *
     * @param c Instancia del controlador del cataleg.
     */
    public CtrlSolucions(CtrlCatalegAmbRestriccions c){
        this.solucions = new ArrayList<Solucio>();
        this.cataleg = c;
        this.algorismeAct = new Aproximacio(); //per defecte, el algorismeAct és d'aproximació
    }

    //Getters i setters

    /**
     *
     * @return Retorna la llista de Solucions del sistema.
     */
    public ArrayList<Solucio> getSolucions(){ return solucions; }

    /**
     *
     * @return Retorna una instancia del cataleg del sistema
     */
    public CtrlCataleg getCataleg(){ return cataleg; }

    /**
     *
     * @return Retorna un string que indica el tipus d'algorisme configurat actualment.
     */
    public String getAlgorismeAct(){
        if (algorismeAct instanceof AlgorismeBT)  return "backtracking";
        if (algorismeAct instanceof AlgorismeGreedy) return "greedy";
        return "aproximacio";
    }

    /**
     * A l'algorisme greedy se li poden especificar parametres.
     *
     * @param param1 Index del primer producte.
     * @param param2 Numero d'iteracions.
     */
    public void setParametres(int param1, int param2) throws FormatInputNoValid{
        algorismeAct = new AlgorismeGreedy(param1, param2);
    }

    /**
     * El controlador de domini crida a aquesta funcio per proporcionar el controlador de persistencia.
     *
     * @param cps Instancia del controlador de persistencia de solucions
     */
    public void setCtrlPersistenciaSolucio(CtrlPersistenciaSolucio cps){
        ctrlPersistenciaSolucio = cps;
    }

    //Mètodes addicionals
    /**
     * pre: l'usuari crida aquesta funcio passant el tipus d'algorisme (i més endevant pasarà paràmetres)
     * post: S'ha creat una instància d'Algorisme amb els paràmetres indicats
     * @param tipusAlgorisme tipus del algorisme
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
     *
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
     * Retorna la matriu de productes d'una solucio concreta
     *
     * @param nomSolucio nom de la solucio que es vol obtenir
     */
    public ArrayList<ArrayList<String>> getSolucio(String nomSolucio) throws NomSolucioNoValid{
        if (this.existeixSolucio(nomSolucio)){
            for (Solucio sol : solucions){
                if(sol.getNom().equals(nomSolucio)) return sol.getSolucio();
            }
        }
        else {
            String missatge = "Ja existeix una solucio amb nom '" +nomSolucio+ "'";
            throw new NomSolucioNoValid(missatge);
        }
        return new ArrayList<ArrayList<String>>();
    }

    /**
     * pre: l'usuari crida a aquesta funcio quan vol crear una nova solucio
     * post: s'ha creat una nova instància de solucio resolta amb algorismeAct
     * @param nomSolucio : nom de la nova solucio que es vol crear
     */
    public void creaSolucio(String nomSolucio, int prodPrestatge) throws NomSolucioNoValid, FormatInputNoValid {
        System.out.println("Estic a crear solucio!!!"+ nomSolucio + " " + prodPrestatge);
        for (Solucio s: solucions){
            if (s.getNom().equals(nomSolucio)) {
                String missatge = "Ja existeix una solucio amb nom '" +nomSolucio+ "'";
                throw new NomSolucioNoValid(missatge);
            }
        }

        double[][] similituds = cataleg.getMatriuSimilituds();
        boolean[][] matriuRestriccions = cataleg.getMatrRestrConsec();
        int[] solucio = algorismeAct.solucionar(similituds, matriuRestriccions);
        System.out.println("Ja ha creat la solucio!!!"+ nomSolucio + " " + prodPrestatge);

        ArrayList<String> llistaProd = new ArrayList<String>();
        for (int i : solucio) {
            llistaProd.add(cataleg.getNomProd_index(i));
        }

        Solucio sol = new Solucio(llistaProd, nomSolucio, prodPrestatge);
        solucions.add(sol);
    }

    /**
     * L'usuari crida a aquesta funcio quan vol intercanviar dos productes d'una solucio
     *
     * @param prod1 nom d'un producte a intercanviar
     * @param prod2 nom d'un producte a intercanviar
     * @param nomSolucio nom de la solucio a modificar
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
                    throw new IntercanviNoValid(missatge);
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

    /**
     *L'usuari crida a aquesta funcio quan vol intercanviar dos productes d'una solucio. proporciona els index dels productes dins la solucio
     *
     * @param nomSolucio nom de la solucio a modificar
     */
    public void modificarSolucio (int index1i, int index1j, int index2i,int index2j, String nomSolucio) throws IntercanviNoValid, NomSolucioNoValid, FormatInputNoValid {
        boolean trobat = false;
        //ArrayList<Solucio> solucionsTemp = new ArrayList<>(solucions);
        Iterator<Solucio> iterator = solucions.iterator();
        SolucioModificada solMod = null;
        while (iterator.hasNext()){
            Solucio s = iterator.next();
            if (s.getNom().equals(nomSolucio)){
                trobat = true;
                if (s.existeixPosicio(index1i,index1j) && s.existeixPosicio(index2i,index2j)) {
                    solMod = new SolucioModificada(s.getSolucio(), nomSolucio);
                    solMod.intercanvia(index1i, index1j,index2i,index2j);
                    iterator.remove();
                    break;
                }
                else {
                    String missatge = "No existeix algun dels productes a la solucio amb nom '" +nomSolucio+ "'";
                    throw new IntercanviNoValid(missatge);
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

    /**
     * L'usuari vol eliminar una solucio del sistema
     *
     * @param nomSolucio el nom de la solucio que es vol eliminar
     * @throws NomSolucioNoValid si no existeix cap solucio amb el nom indicat
     */
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
     * Retorna un vector amb els noms de totes les solucions
     */
    public ArrayList<String> getSolucionsNom(){
        ArrayList<String> result = new ArrayList<String>();
        for (Solucio s: solucions){
            result.add(s.getNom());
        }
        return result;
    }

    /**
     * Mostra per terminal totes les solucions actuals del sistema
     */
    public void mostrarSolucions() {
        if (solucions.isEmpty()) {
            System.out.println("No hi ha solucions a mostrar");
        }
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        else {
            for (Solucio s: solucions) s.mostrarSolucio();
        }
    }


    /**
     * Mostra per terminal una solucio especifica
     *
     * @param nomSol nom de la solucio que es vol mostrar
     * @throws NomSolucioNoValid si no hi ha cap solucio amb el nom indicat
     */
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

    /**
     * L'usuari vol carregar solucions al del sistema des d'un fitxer
     *
     * @param path on hi ha el fitxer
     * @param nomArxiu nom del fitxer amb les solucions
     */
    public void carregaSolucions(String path, String nomArxiu) throws FormatInputNoValid{
        String s = null;
        try {
            solucions.clear();
            ctrlPersistenciaSolucio.processarDadesArxiu(path, nomArxiu);
        }catch (FormatInputNoValid e){
            s = e.getMessage();
        }catch (NomSolucioNoValid e){
            s = e.getMessage();
        }
        if (s != null){
            solucions.clear();
            throw new FormatInputNoValid(s);
        }
    }

    /**
     * El sistema te informacio sobre una solucio i vol carregar-la al sistema
     *
     * @param modificada indica si la solucio esta modificada
     * @param nomSolucio indica el nom de la solucio
     * @param sol distribucio dels productes de la solucio
     * @throws NomSolucioNoValid
     * @throws FormatInputNoValid
     */
    public void carregaSolucio(boolean modificada, String nomSolucio, ArrayList<ArrayList<String>> sol) throws NomSolucioNoValid, FormatInputNoValid{
        Solucio s = null;
        if (modificada){
            s = new SolucioModificada(sol, nomSolucio);
        }
        else {
            s = new Solucio(sol, nomSolucio);
        }
        if (existeixSolucio(s.getNom())){
            String missatge = "El contingut del arxiu no es compatible amb el programa";
            throw new NomSolucioNoValid(missatge);
        }
        solucions.add(s);
        System.out.println("solucio afegida: "+ nomSolucio);
    }

    /**
     * L'usuari vol guardar totes les solucions del sistema en un fitxer
     *
     * @param path on es trova el fitxer
     * @param nomArxiu nom del fitxer amb les solucions
     */
    public void guardaSolucio(String path, String nomArxiu) throws FormatInputNoValid{
        StringBuilder contenido = new StringBuilder();

        for (Solucio solucio : solucions) {
            // Escriure si la solucio ha sigut modificada
            boolean modificada = solucio instanceof SolucioModificada;
            contenido.append(modificada).append("\n");

            // Escriure el nom de la solucio
            contenido.append(solucio.getNom()).append("\n");

            // Escriure cada linia de la solucio
            for (ArrayList<String> linea : solucio.getSolucio()) {
                contenido.append(String.join(" ", linea)).append("\n");
            }

            // Afegir linia buida entre solucions
            contenido.append("\n");
        }
        String c = contenido.toString();
        ctrlPersistenciaSolucio.exportar(c, path, nomArxiu);
    }

    /**
     * @param nomSolucio nom d'una solucio del sistema
     * @return si la solucio ha estat modificada, retorna true. False en cas contrari.
     */
    public boolean esModificada(String nomSolucio){
        for (Solucio sol : solucions){
            if(sol.getNom().equals(nomSolucio)) return sol instanceof SolucioModificada;
        }
        return false;
    }
}
