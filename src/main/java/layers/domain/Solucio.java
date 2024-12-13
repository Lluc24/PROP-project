package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Solucio {
    // Atributs
    protected ArrayList<ArrayList<String>> solucio;
    protected String nom;

    // Constructores
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


    public Solucio(ArrayList<ArrayList<String>> s, String n) throws FormatInputNoValid {
        if (!matriuValida(s)) {
            String missatge = "La matriu de productes no es valida";
            throw new FormatInputNoValid(missatge);
        }
        this.nom = n;
        this.solucio = s;
    }

    // Getters i Setters
    public String getNom() {
        return nom;
    }

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