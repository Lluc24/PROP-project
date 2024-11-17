package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.ProducteNoValid;
import layers.domain.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;

public class ControladorCatalegAmbRestriccions extends Cataleg {

    //Atributs
    /** Indica els productes que no poden aparèixer consecutivament a la solució
     * Els índexs de la matriu corresponen amb els índexs dels productes.
     */

    private ArrayList<ArrayList<Boolean>> noConsecutius;

    //Constructora
    /**
     * Mètode de construcció d'un conjunt de restriccions buit
     */
    public ControladorCatalegAmbRestriccions() {

        super();
        this.noConsecutius = new ArrayList<ArrayList<Boolean>>();

    }

    //Mètodes
    private void producteAfegit() {

        ArrayList<Boolean> aux = new ArrayList<>(Collections.nCopies(noConsecutius.getFirst().size(), false));
        noConsecutius.add(aux);

        for(int i = 0; i < noConsecutius.size(); ++i) {
            noConsecutius.get(i).add(false);
        }
    }

    private void producteEliminat(int id) {
        noConsecutius.remove(id);
        for(int i = 0; i < noConsecutius.getFirst().size(); ++i) {
            noConsecutius.get(i).remove(id);
        }
    }

    public void setRestrConsecId(int id1, int id2) {

        if (!valida_index(id1) || !valida_index(id2)) {
            System.err.println("SetRestrConsec: Producte no valid");
            return; //Error;
        }

        noConsecutius.get(id1).set(id2, true);
        noConsecutius.get(id2).set(id1, true);

    }

    public void remRestrConsecId(int id1, int id2) {

        if (!valida_index(id1) || !valida_index(id2)) {
            System.err.println("SetRestrConsec: Producte no valid");
            return; //Error;
        }

        noConsecutius.get(id1).set(id2, false);
        noConsecutius.get(id2).set(id1, false);

    }

    public void setRestrConsecNom(String nom1, String nom2) {
        setRestrConsecId(get_index_prod(nom1), get_index_prod(nom2));
    }

    public void remRestrConsecNom(String nom1, String nom2) {
        remRestrConsecId(get_index_prod(nom1), get_index_prod(nom2));
    }

    public boolean getRestrConsecID(int id1, int id2) throws ProducteNoValid {

        if (!valida_index(id1) || !valida_index(id2)) {
            throw new ProducteNoValid("getRestrConsec: Producte no existeix");
        }
        return noConsecutius.get(id1).get(id2);
    }

    public boolean getRestrConsecNom(String nom1, String nom2) throws ProducteNoValid {
        return getRestrConsecID(get_index_prod(nom1), get_index_prod(nom2));
    }

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

    private void mostrarRestrConsec(int index){

        boolean hihaRestr = false;
        for (int i = 0; i < noConsecutius.size(); ++i) {
            if (noConsecutius.get(index).get(i)) {
                if (!hihaRestr) {
                    System.out.print("Restrictivament, no pot estar consecutiu a: ");
                }
                hihaRestr = true;
                System.out.print(getNomProd_index(i) + ", ");
            }
        }

        if (!hihaRestr) {
            System.out.print("Pot estar consecutiu a qualsevol altre producte ");
        }

        System.out.println(" ");
    }

    @Override
    public void afegir_producte(String new_nom, Pair<String, Double>[] llista_simi) throws ProducteNoValid, FormatInputNoValid {

        //Existeix un producte amb new_nom
        if (find_prod(new_nom)) {
            throw new ProducteNoValid("Afegir producte: El producte ja es troba a el cataleg");
        }

        Pair<Integer, Double>[] simi_index = new Pair[llista_simi.length];
        for (int i = 0; i < llista_simi.length; ++i) {
            int index_in = get_index_prod(llista_simi[i].first);
            if (!valida_index(index_in)) {
                throw new ProducteNoValid("Afegir producte: Producte per similtud no esta a cataleg");
            } else {
                if (llista_simi[i].second >= 0 && llista_simi[i].second <= 100) {
                    Pair<Integer, Double> p = new Pair<>(i, llista_simi[i].second);
                    simi_index[i] = p;
                } else {
                    throw new FormatInputNoValid("El valors de la similitud per producte" +llista_simi[i].first+"no son valid");
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

    @Override
    public void afegir_producte(String new_nom) throws ProducteNoValid {
        //Existe producto con new_nom
        if (find_prod(new_nom)) {
            throw new ProducteNoValid("Afegir producte: El producte ja es troba a el cataleg");

        }

        if (num_prod_act() != 0) {
            System.err.println("Afegir_producte: Cataleg ja te productes");
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

    public void eliminar_producte_index(int index_out) {
        if (!valida_index(index_out)) {
            System.err.println("Eliminar producte: Index de producte no valid");
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

    @Override
    public void mostrarProducte(int index) {
        if (!valida_index(index)) {
            System.err.println("mostrarProducte: Index no es valid");
        }

        Producte prod = Cataleg_Productes.get(index);
        System.out.print("Producte " +index+ ": "+prod.getNom()+". Les seves similituds son: ");
        ArrayList<Double>  Simi = prod.getSimilituds();
        for (int j = 0; j < Simi.size(); ++j) {
            if (j !=  index) {
                if (j < Simi.size() - 1) {
                    System.out.print(getNomProd_index(j) + " -> " + Simi.get(j) + " , ");
                } else System.out.print(getNomProd_index(j) + " -> " + Simi.get(j) + " ");
            }
        }
        System.out.println(" ");

        mostrarRestrConsec(index);
    }

}

