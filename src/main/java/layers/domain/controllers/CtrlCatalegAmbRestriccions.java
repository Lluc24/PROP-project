package layers.domain.controllers;

import layers.domain.Producte;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import layers.domain.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Aquesta classe reescriu alguns mètodes de CtrlCataleg i permet establir o eliminar restriccions entre productes.
 *
 * @see CtrlCataleg
 *
 * @author Efrain Tito Cortés
 * @version 1,1
 *
 * <p><b>Informació:</b></p>
 * Manté una matriu de mida igual al nombre de productes al catàleg i les restriccions entre ells per determinar quins productes no poden ser consecutius en la solució.
 * Matriu noConsecutius: Si l'element de la fila 'i', columna 'j' és true, hi ha una restricció de consecutius entre el producte amb índex 'i' i aquell amb índex 'j'.
 */
public class CtrlCatalegAmbRestriccions extends CtrlCataleg {

    //Atributs
    /** Indica els productes que no poden aparèixer consecutivament a la solució.
     * Els índexs de la matriu corresponen amb els índexs dels productes.
     * La mida és sempre igual al nombre de productes al catàleg. És quadrada.
     */

    private ArrayList<ArrayList<Boolean>> noConsecutius;

    //Constructora
    /**
     * Mètode de construcció d'un conjunt de restriccions buit
     */
    public CtrlCatalegAmbRestriccions() {

        super();
        this.noConsecutius = new ArrayList<>();

    }

    //Mètodes

    /**
     * Obté la mida actual de la matriu de restriccions consecutives (nombre de productes).
     *
     * @return La mida de la matriu, que correspon al nombre de productes en la gestió de restriccions de consecutius.
     */
    public int get_mida_noConsec() { return noConsecutius.size(); }

    /**
     * Afegir un nou producte a la matriu de restriccions.
     * Quan un producte és afegit, s'afegeixen noves files i columnes a la matriu per mantenir les restriccions de consecutius.
     */
    private void producteAfegit() {

        if (noConsecutius.isEmpty()) {
            //cas inicial: cap producte encara
            ArrayList<Boolean> aux = new ArrayList<>();
            aux.add(false);
            noConsecutius.add(aux);
        } else {
            //afegir nova fila i columna per al nou producte
            ArrayList<Boolean> aux = new ArrayList<>(Collections.nCopies(noConsecutius.getFirst().size(), false));
            noConsecutius.add(aux);

            for (ArrayList<Boolean> fila : noConsecutius) {
                fila.add(false);
            }
        }
    }

    /**
     * Elimina un producte de la matriu de restriccions.
     * Aquesta operació elimina la fila i la columna corresponents al producte especificat.
     *
     * @param id L'índex del producte a eliminar.
     */
    private void producteEliminat(int id) {
        noConsecutius.remove(id);
        for(int i = 0; i < noConsecutius.size(); ++i) {
            noConsecutius.get(i).remove(id);
        }
    }

    /**
     * Estableix una restricció de consecutius entre dos productes especificats pels seus índexs.
     * Aquesta operació fa que els dos productes no puguin estar consecutius.
     *
     * @param id1 L'índex del primer producte.
     * @param id2 L'índex del segon producte.
     */
    public void setRestrConsecId(int id1, int id2) {

        if (!valida_index(id1) || !valida_index(id2)) {
            System.err.println("Algun producte no es valid");
            return; //Error;
        }

        noConsecutius.get(id1).set(id2, true);
        noConsecutius.get(id2).set(id1, true);

    }

    /**
     * Elimina una restricció de consecutivitat entre dos productes especificats pels seus índexs.
     * Aquesta operació permet que els dos productes puguin estar consecutius.
     *
     * @param id1 L'índex del primer producte.
     * @param id2 L'índex del segon producte.
     */
    public void remRestrConsecId(int id1, int id2) {

        if (!valida_index(id1) || !valida_index(id2)) {
            System.err.println("Algun producte no es valid");
            return; //Error;
        }

        noConsecutius.get(id1).set(id2, false);
        noConsecutius.get(id2).set(id1, false);

    }

    /**
     * Estableix una restricció de consecutivitat entre dos productes identificats pels seus noms.
     * Aquesta operació fa que els productes amb els noms especificats no puguin estar consecutius.
     *
     * @param nom1 El nom del primer producte.
     * @param nom2 El nom del segon producte.
     */
    public void setRestrConsecNom(String nom1, String nom2) throws ProducteNoValid{
        boolean noms_valids = true;
        int index1 = get_index_prod(nom1);
        if (index1 == -1) noms_valids = false;
        int index2 = get_index_prod(nom2);
        if (index2 == -1) noms_valids = false;

        if (!noms_valids) throw new ProducteNoValid("Algun producte no es valid");

        setRestrConsecId(index1, index2);

    }

    /**
     * Elimina una restricció de consecutivitat entre dos productes identificats pels seus noms.
     * Aquesta operació permet que els productes amb els noms especificats puguin estar consecutius.
     *
     * @param nom1 El nom del primer producte.
     * @param nom2 El nom del segon producte.
     */
    public void remRestrConsecNom(String nom1, String nom2) throws ProducteNoValid {
        boolean noms_valids = true;
        int index1 = get_index_prod(nom1);
        if (index1 == -1) noms_valids = false;
        int index2 = get_index_prod(nom2);
        if (index2 == -1) noms_valids = false;

        if (!noms_valids) throw new ProducteNoValid("Algun producte no es valid");

        if (!getRestrConsecID(index1, index2)) {
            System.out.println("No existeix aquesta restricció entre "+ nom1+" i "+nom2);
            return;
        } else {
            remRestrConsecId(index1, index2);
        }
    }

    /**
     * Obté si hi ha una restricció de consecutivitat entre dos productes especificats pels seus índexs.
     *
     * @param id1 L'índex del primer producte.
     * @param id2 L'índex del segon producte.
     * @return True si els productes no poden estar consecutius, false en cas contrari.
     * @throws ProducteNoValid Si algun dels productes no és vàlid (el seu índex no es troba al catàleg).
     */
    public boolean getRestrConsecID(int id1, int id2) throws ProducteNoValid {

        if (!valida_index(id1) || !valida_index(id2)) {
            throw new ProducteNoValid("Algun producte no existeix");
        }
        return noConsecutius.get(id1).get(id2);
    }

    /**
     * Obté si hi ha una restricció de consecutivitat entre dos productes identificats pels seus noms.
     *
     * @param nom1 El nom del primer producte.
     * @param nom2 El nom del segon producte.
     * @return True si els productes no poden estar consecutius, false en cas contrari.
     * @throws ProducteNoValid Si algun dels productes no és vàlid (el seu nom no es troba al catàleg).
     */
    public boolean getRestrConsecNom(String nom1, String nom2) throws ProducteNoValid {
        return getRestrConsecID(get_index_prod(nom1), get_index_prod(nom2));
    }

    /**
     * Obté la matriu completa de restriccions de consecutivitat entre els productes.
     * Aquesta matriu és una representació booleana en què true indica que els productes no poden ser consecutius.
     *
     * @return La matriu de restriccions de consecutivitat.
     */
    public boolean[][] getMatrRestrConsec() {

        int costat = noConsecutius.size();
        boolean[][] matriu = new boolean[costat][costat];

        for (int i = 0; i < costat; i++) {
            for (int j = 0; j < costat; j++) {
                matriu[i][j] = noConsecutius.get(i).get(j);
            }
        }

        return matriu;

    }

    /**
     * Mostra les restriccions de consecutivitat per a un producte especificat pel seu índex.
     * Imprimeix els productes amb els quals el producte actual no pot estar consecutiu.
     *
     * @param index L'índex del producte per al qual mostrar les restriccions.
     */
    private void mostrarRestrConsec(int index) {

        boolean hihaRestr = false;
        for (int i = 0; i < noConsecutius.size(); ++i) {
            if (noConsecutius.get(index).get(i)) {
                if (!hihaRestr) {
                    System.out.print("Restrictivament, no pot estar consecutiu a: ");
                    System.out.print(getNomProd_index(i));
                    hihaRestr = true;
                }
                else System.out.print(" , " + getNomProd_index(i));
            }
        }

        if (!hihaRestr) {
            System.out.print("Pot estar consecutiu a qualsevol altre producte ");
        }

        System.out.println(" ");
    }

    /** Override de afegir_producte(String new_nom, Pair<String, Double>[] llista_simi) throws ProducteNoValid, FormatInputNoValid */
    @Override
    public void afegir_producte(String new_nom, Pair<String, Double>[] llista_simi) throws ProducteNoValid, FormatInputNoValid {

        //Existeix un producte amb new_nom
        if (find_prod(new_nom)) {
            throw new ProducteNoValid("El producte ja es troba a el cataleg");
        }

        Pair<Integer, Double>[] simi_index = new Pair[llista_simi.length];
        for (int i = 0; i < llista_simi.length; ++i) {
            int index_in = get_index_prod(llista_simi[i].first);
            if (!valida_index(index_in)) {
                throw new ProducteNoValid("Producte per similtud no esta a cataleg");
            } else {
                if (llista_simi[i].second >= 0 && llista_simi[i].second <= 100) {
                    Pair<Integer, Double> p = new Pair<>(i, llista_simi[i].second);
                    simi_index[i] = p;
                } else {
                    throw new FormatInputNoValid("El valors de la similitud per producte " +llista_simi[i].first+" no son valid");
                }
            }

        }

        //Nova instància producte
        int new_index = Cataleg_Productes.size();
        ArrayList<Double> new_simi = new ArrayList<>();

        if (num_prod_act()==0) {
            new_simi.add(new_index,0.0);
            Producte new_prod = new Producte(new_index, new_nom, new_simi);
            Cataleg_Productes.add(new_index, new_prod);
            producteAfegit();
            return;
        }

        //Afegim les similituds a la llista
        for (int i = 0; i < simi_index.length; ++i) {
            int index_in = simi_index[i].first;
            //Condicional, per asegurar el funcionament si el valors de simi no estan ordenats amb ordre de cataleg
            if (new_simi.size() < index_in) {
                //Afegim els espais necesaris fins arribar a index_in
                for (int x = new_simi.size(); x <= index_in; ++x) {
                    double simi_value = -1.0;
                    if (x == index_in) simi_value = simi_index[i].second;
                    else simi_value = -1.0;
                    new_simi.add(x, simi_value);
                }
            } else if (new_simi.size() == index_in) {
                new_simi.add(index_in, simi_index[i].second);
            } else if (new_simi.size() > index_in) {
                new_simi.set(index_in, simi_index[i].second);
            }
            Cataleg_Productes.get(index_in).addSimiProd(new_index, simi_index[i].second); //La similitud del producte preexisten amb el nou
        }

        new_simi.add(new_index,0.0);
        Producte new_prod = new Producte(new_index, new_nom, new_simi);
        Cataleg_Productes.add(new_index, new_prod);

        producteAfegit();

    }

    /** Override de afegir_producte(String new_nom) throws ProducteNoValid */
    @Override
    public void afegir_producte(String new_nom) throws ProducteNoValid {
        //Existe producto con new_nom
        if (find_prod(new_nom)) {
            throw new ProducteNoValid("El producte ja es troba a el cataleg");

        }

        if (num_prod_act() != 0) {
            System.err.println("Cataleg ja te productes");
            return;
        }

        //Nova instancia producte
        int new_index = Cataleg_Productes.size();
        ArrayList<Double> new_simi = new ArrayList<>();

        new_simi.add(new_index,0.0);
        Producte new_prod = new Producte(new_index, new_nom, new_simi);
        Cataleg_Productes.add(new_index, new_prod);

        producteAfegit();
    }

    /** Override de public void eliminar_producte_index(int index_out) */
    @Override
    public void eliminar_producte_index(int index_out) {
        if (!valida_index(index_out)) {
            System.err.println("Index de producte no valid");
            return; //Error;
        }

        Cataleg_Productes.remove(index_out);

        //Eliminem totes les similituds associades al producte
        for (int i = 0; i < Cataleg_Productes.size(); ++i) {
            Cataleg_Productes.get(i).remSimiProd(index_out);
        }

        //Actualitzem tots els index del productes
        for (int i = 0; i < Cataleg_Productes.size(); ++i) {
            Cataleg_Productes.get(i).setIndex(i);
        }

        producteEliminat(index_out);
    }

    /** Override de public void mostrarProducte(int index) */
    @Override
    public void mostrarProducte(int index) {
        if (!valida_index(index)) {
            System.err.println("Index no es valid");
        }

        boolean first_print = true ;

        Producte prod = Cataleg_Productes.get(index);
        System.out.print("Producte " +index+ ": "+prod.getNom()+". Les seves similituds son: ");
        ArrayList<Double>  Simi = prod.getSimilituds();
        for (int j = 0; j < Simi.size(); ++j) {
            if (j != index) {
                if (first_print) {
                    System.out.print(getNomProd_index(j) + " -> " + Simi.get(j));
                    first_print = false;
                } else {
                    System.out.print(" , "+getNomProd_index(j) + " -> " + Simi.get(j));
                }
            }
        }
        System.out.println("");

        mostrarRestrConsec(index);
    }

    /**
     * Mostra totes les restriccions de consecutivitat entre els productes.
     * Aquest mètode recorre la matriu de restriccions i imprimeix quins productes tenen restriccions de consecutivitat amb altres productes.
     * Si un producte té alguna restricció, es mostrarà el seu nom i el dels productes amb els quals té restriccions.
     * En cas que no hi hagi cap restricció, es mostrarà un missatge indicant-ho.
     */
    public void mostrarRestrConsec() {
        Iterator<ArrayList<Boolean>> it1 = noConsecutius.iterator();
        boolean hihaRestr = false;
        int i = 0;
        while (it1.hasNext()) {
            Iterator<Boolean> it2 = it1.next().iterator();
            boolean teRestr = false;
            int j = 0;
            while (it2.hasNext()) {
                if (it2.next()) {
                    if (!teRestr) {
                        System.out.print("El producte " + Cataleg_Productes.get(i).getNom() + " te restriccions amb: ");
                    }
                    System.out.print(Cataleg_Productes.get(j).getNom() + " ");
                    teRestr = true;
                }
                ++j;
                if (teRestr) {
                    System.out.println();
                    hihaRestr = true;
                }
            }
            ++i;
        }
        if (!hihaRestr) System.out.println("No hi ha restriccions");
    }

    /**
     * Converteix les restriccions en una llista de strings de text.
     * Cada string representa una parella de productes amb format "producte1;producte2".
     *
     * @return Un array de strings amb les restriccions.
     */
    public String[] restr_a_String() {

        ArrayList<Pair<Integer, Integer>> pairs = getArrayRestr();

        ArrayList<Pair<String, String>> pairsString = getStringId(pairs);

        String[] restr = new String[pairsString.size()];

        for (int i = 0; i < pairsString.size(); i++) {
            Pair<String, String> pair = pairsString.get(i);
            restr[i] = pair.first + ";" + pair.second;
        }

        return restr;
    }

    /**
     * Converteix una llista de parelles d'índexs de productes a parelles amb els seus noms.
     *
     * @param pairs Llista de parelles d'índexs.
     * @return Llista de parelles amb els noms dels productes.
     */
    private ArrayList<Pair<String, String>> getStringId(ArrayList<Pair<Integer, Integer>> pairs) {

        ArrayList<Pair<String, String>> pairsString = new ArrayList<>();

        for (Pair<Integer, Integer> pair : pairs) {
            String firstString = getNomProd_index(pair.first);
            String secondString = getNomProd_index(pair.second);
            pairsString.add(new Pair<>(firstString, secondString));
        }

        return pairsString;

    }

    /**
     * Obté les restriccions com a llista de parelles d'índexs de productes que no poden ser consecutius.
     *
     * @return Llista de parelles d'índexs de productes.
     */
    private ArrayList<Pair<Integer, Integer>> getArrayRestr() {
        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();

        for (int i = 0; i < noConsecutius.size(); i++) {
            for (int j = i + 1; j < noConsecutius.get(i).size(); j++) {
                if (noConsecutius.get(i).get(j)) {
                    pairs.add(new Pair<>(i, j));
                }
            }
        }

        return pairs;
    }

}

