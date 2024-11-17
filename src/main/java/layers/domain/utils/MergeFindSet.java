package layers.domain.utils;

public class MergeFindSet {
    private int[] MFS;
    int n_conjunts;

    public MergeFindSet(int n) {
        MFS = new int[n];
        for (int i = 0; i < n; ++i) MFS[i] = -1;
        n_conjunts = n;
    }

    public int buscar(int u) {
        if (MFS[u] < 0) return u;
        else {
            int cjt_u = buscar(MFS[u]);
            MFS[u] = cjt_u;
            return cjt_u;
        }
    }

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

    public int getNombreConjunts() {
        return n_conjunts;
    }
}
