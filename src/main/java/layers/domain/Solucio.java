package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Classe 'Solucio'
 *
 * Representa una solució amb un conjunt de productes organitzats en prestatges. Aquesta classe permet gestionar
 * i validar les dades de la solució, incloent la seva estructura i la unicitat dels productes.
 * També ofereix funcionalitats per consultar i modificar les dades de la solució.
 *
 * @author Eulalia Peiret Santacana
 *
 * <p><b>Informació:</b></p>
 * Aquesta classe disposa de dos constructors:
 * - Un constructor que rep una llista plana de productes i la divideix en prestatges segons el nombre de productes per prestatge.
 * - Un constructor que rep directament una matriu de productes.
 *
 * Els productes es validen per assegurar que no hi hagi duplicats i que l'estructura de la matriu sigui consistent.
 * Les files de la matriu representen prestatges, i es pot consultar o modificar cada prestatge individualment.
 *
 * <p><b>Funcionalitats principals:</b></p>
 * - Validació de la matriu de productes per evitar duplicats i inconsistències.
 * - Consulta del nom de la solució i de la seva estructura.
 * - Cerca d'un producte dins de la solució.
 * - Verificació de l'existència d'una posició específica dins de la matriu.
 *
 * <p><b>Excepcions:</b></p>
 * - <b>FormatInputNoValid</b>: Els paràmetres d'entrada no són vàlids, com un nombre de productes per prestatge incorrecte
 *   o una matriu amb productes repetits o estructura no vàlida.
 *
 */
public class Solucio {
    // Atributs
    protected ArrayList<ArrayList<String>> solucio;
    protected String nom;

    /**
     * Funcio constructora de la calsse.
     *
     * @param s La llista de productes en ordre.
     * @param n El nom de la solucio.
     * @param p El numero de productes per prestatge.
     * @throws FormatInputNoValid Si algun dels parametres d'entrada no es correcte, surt la excepcio.
     */
    public Solucio(ArrayList<String> s, String n, int p) throws FormatInputNoValid{
        if (p <= 0) {
            String missatge = "El numero de productes per prestatgeria ha de ser minim 1";
            throw new FormatInputNoValid(missatge);
        }
        else {
            this.nom = n;
            //Divideix la llista en prestatges
            ArrayList<ArrayList<String>> aux = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < s.size(); i++) {
                if (i % p == 0) {
                    ArrayList<String> prestatgeNou = new ArrayList<>();
                    prestatgeNou.add(s.get(i));
                    aux.add(prestatgeNou);
                } else {
                    aux.getLast().add(s.get(i));
                }
            }
            for (int i = 0; i < aux.size(); i++) {
                if (i % 2 != 0) {
                    Collections.reverse(aux.get(i));
                }
            }
            if (!matriuValida(aux)) {
                String missatge = "La matriu de productes no es valida";
                throw new FormatInputNoValid(missatge);
            }
            this.solucio = aux;
        }
    }

    /**
     * Funcio constructora de la classe.
     *
     * @param s Matriu amb els productes ordenats.
     * @param n Nom de la solucio.
     * @throws FormatInputNoValid Si alguns dels parametres d'entrada no son valids, surt l'excepcio.
     */
    public Solucio(ArrayList<ArrayList<String>> s, String n) throws FormatInputNoValid {
        if (!matriuValida(s)) {
            String missatge = "La matriu de productes no es valida";
            throw new FormatInputNoValid(missatge);
        }
        this.nom = n;
        this.solucio = s;
    }

    /**
     *
     * @return Retorna el nom de la solucio.
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return Retorna la matriu de la solucio.
     */
    public ArrayList<ArrayList<String>> getSolucio() {
        return solucio;
    }

    //Metodes addicionals
    /**
     * Mostra el nom dels productes d'una solucio en ordre.
     */
    public void mostrarSolucio() {
        System.out.print(nom+": ");
        for (int i = 0; i < solucio.size(); i++) {
            for (int j = 0; j < solucio.get(i).size(); ++j) {
                if (j == 0) {
                    System.out.println();
                }
                System.out.print(solucio.get(i).get(j) + " ");
            }
        }
        if (solucio.size() == 0) System.out.println("la solucio no te cap producte");
        else System.out.println();
    }

    /**
     * Comprova si un producte forma part de la solucio
     *
     * @param nomProducte: el nom d'un producte
     * @return retorna true si hi ha un producte amb nom nomProducte a la solució. Fals en cas contrari.
     */
    public boolean trobarProducte(String nomProducte){
        for (int i = 0; i < solucio.size(); ++i) {
            for (String p : solucio.get(i)) {
                if (p.equals(nomProducte)) return true;
            }
        }
        return false;
    }

    /**
     * Retorna cert si existeix un producte en la posicio indicada
     *
     * @param i fila de la matriu
     * @param j columne de la matriu
     * @return cert si existeix producte en solucio[i][j], fals en cas contrari
     */
    public boolean existeixPosicio(int i, int j){
        if(i >= solucio.size()){
            return false;
        }
        else if(i+1 == solucio.size() && j >= solucio.getLast().size()){
           return false;
        }
        else if (j >= solucio.getFirst().size()) {
            return false;
        }
        return true;
    }

    /**
     * Comprova que la matriu de productes no tingui productes repetits i tingui la estructura correcte
     */
    private boolean matriuValida(ArrayList<ArrayList<String>> matriu){
        // Crear un HashSet perque torni fals si intentes afegir un element que ja existeix
        HashSet<String> valoresUnicos = new HashSet<>();

        //mira si hi ha un prestatge mes curt que l'altre i no es l'ultim
        if (!matriu.isEmpty()){
            for (int i = 0; i < matriu.size(); i++) {
                if (i < matriu.size() - 2){
                   if (matriu.get(i).size() !=  matriu.get(i+1).size()) return false;
                }
                else if (i == matriu.size() - 2){
                    if (matriu.get(i).size() <  matriu.get(i+1).size()) return false;
                }

                for (String valor : matriu.get(i)) {
                    // Si el valor ya está en el conjunt, torna fals
                    if (!valoresUnicos.add(valor)) {
                        return false;
                    }
                }


            }
        }
        return true;
    }
}