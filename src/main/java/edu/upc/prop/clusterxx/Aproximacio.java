package edu.upc.prop.clusterxx;

import java.util.*;

public class Aproximacio extends Algorisme {
    private double[][] similituds;
    private int n, m;

    @Override
    public int[] solucionar(double[][] matriuSimilituds) {
        n = matriuSimilituds.length;
        m = n * (n - 1) / 2;
        similituds = matriuSimilituds;
        ParellInt[] arestesOrdenades = new ParellInt[m];
        int k = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                ParellInt parellInt = new ParellInt(i, j);
                arestesOrdenades[k] = parellInt;
                ++k;
            }
        }

        ordenacioRapida(arestesOrdenades, 0, m-1);
        int[] idxArestesMST = kruskal(arestesOrdenades);

        List<Set<Integer>> grafDobleDirigit = new ArrayList<Set<Integer>>(n);
        for (int i = 0; i < n; ++i) grafDobleDirigit.add(new HashSet<Integer>());
        for (int i = 0; i < n - 1; ++i) {
            ParellInt aresta = arestesOrdenades[idxArestesMST[i]];
            grafDobleDirigit.get(aresta.getPrimer()).add(aresta.getSegon());
            grafDobleDirigit.get(aresta.getSegon()).add(aresta.getPrimer());
        }

        List<Integer> cicle = new ArrayList<Integer>();
        dfs(grafDobleDirigit, cicle, 0, -1);

        simplificar(cicle);
    }

    void ordenacioRapida(ParellInt[] arestes, int e, int d) {
        if (e < d) {
            int q = particioHoare(arestes, e, d);
            ordenacioRapida(arestes, e, q);
            ordenacioRapida(arestes, q + 1, d);
        }
    }

    int particioHoare(ParellInt[] adj, int e, int d) {
        double pivot = similituds[adj[e].getPrimer()][adj[e].getSegon()];
        int i = e;
        int j = d;
        boolean acabat = false;
        while (!acabat) {
            while (similituds[adj[i].getPrimer()][adj[i].getSegon()] < pivot) ++i;
            while (similituds[adj[j].getPrimer()][adj[j].getSegon()] > pivot) --j;
            if (i >= j) acabat = true;
            else {
                ParellInt aux = adj[i];
                adj[i] = adj[j];
                adj[j] = aux;
                ++i;
                --j;
            }
        }
        return j;
    }

    int[] kruskal(ParellInt[] arestesOrdenades) {
        MergeFindSet mfs = new MergeFindSet(n);
        int[] idxArestes = new int[n-1];
        idxArestes[0] = m-1;
        int arestesFixades = 1;
        int i = m-2;
        mfs.unir(arestesOrdenades[m-1].getPrimer(), arestesOrdenades[m-1].getSegon());

        while (mfs.getNombreConjunts() > 1) {
            if (mfs.unir(arestesOrdenades[i].getPrimer(), arestesOrdenades[i].getSegon())) {
                idxArestes[arestesFixades] = i;
                ++arestesFixades;
            }
            --i;
        }
        return idxArestes;
    }

    void dfs(List<Set<Integer>> grafDobleDirigit, List<Integer> cicle, int vtxActual, int vtxPrevi) {
        cicle.add(vtxActual);
        for (int proper : grafDobleDirigit.get(vtxActual)) {
            if (proper != vtxPrevi) {
                dfs(grafDobleDirigit, cicle, proper, vtxActual);
                cicle.add(vtxActual);
            }
        }
    }

    void simplificar(List<Integer> cicle) {
        while (cicle.size() > n) {

        }
    }

    private void imprimirArrayParellInt(ParellInt[] arestes) {
        int mida = arestes.length;
        for (int i = 0; i < mida; ++i) {
            System.out.print(arestes[i].toString() + ", ");
        }
        System.out.println();
    }
}