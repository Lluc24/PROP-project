package layers.domain.utils;


import layers.domain.Algorisme;

/**
 * MergeFindSet
 * Estructura de dades que permet gestionar una particio com a multiconjunt
 * <p><b>Informació:</b></p>
 * Aquesta classe s'utilitza com a estructura de dades auxiliar per l'algorisme de
 * 2-Aproximacio. Permet de forma dinamica mantenir una particio en un conjunt de
 * nombres enters seguint una relacio d'equvalencia. S'utiliza per construir
 * l'arbre d'expansio maxim de forma dinamica. Segueix les instruccions amb totes
 * les millores opcionals del document d'informacio adicional del professorat de
 * PROP
 * @author Lluc Santamaria Riba
 * @version 3.1
 *
 * @see layers.domain.Aproximacio
 */
public class MergeFindSet {

    /** Atribut per gestionar el multiconjunt a partir d'indexs. */
    private int[] MFS;
    /** Numero de conjunts de la particio. */
    int n_conjunts;

    /**
     * Constructora de l'estructura de dades on cada element pertany al seu propi conjunt.
     * @param n: Nombre d'elements en el multiconjunt.
     */
    public MergeFindSet(int n) {
        MFS = new int[n];
        for (int i = 0; i < n; ++i) MFS[i] = -1;
        n_conjunts = n;
    }

    /**
     * Metode per consultar el conjunt al qual pertanyen els elements.
     * Implementa la compressio de camins.
     * @param u: Element a consultar
     * @return Retorna a quin conjunt pertany l'element u
     */
    public int buscar(int u) {
        if (MFS[u] < 0) return u;
        else {
            int cjt_u = buscar(MFS[u]);
            MFS[u] = cjt_u;
            return cjt_u;
        }
    }

    /**
     * Metode per unir dos conjunts. Implementa la unio per talles
     * @param u: Element del qual el seu conjunt es vol unir amb l'altre
     * @param v: Element del qual el seu conjunt es vol unir amb l'altre
     * @return Retorna cert si i nomes si u i v pertanyen a conjunts diferents.
     */
    public boolean unir(int u, int v) {
        int cjt_u = buscar(u);
        int cjt_v = buscar(v);
        if (cjt_u == cjt_v) return false;
        else {
            if (MFS[cjt_u] <= MFS[cjt_v]) {
                MFS[cjt_u] += MFS[cjt_v];
                MFS[cjt_v] = cjt_u;
            } else {
                MFS[cjt_v] += MFS[cjt_u];
                MFS[cjt_u] = cjt_v;
            }
            n_conjunts--;
            return true;
        }
    }

    /**
     * Consultora del nombre de conjunts del multiconjunt.
     * @return Retorna el nombre de subconjunts.
     */
    public int getNombreConjunts() {
        return n_conjunts;
    }
}
