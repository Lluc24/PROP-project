package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Iterator;

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
        ArrayList<Integer>[] grafDobleDirigit = new ArrayList[n];
        for (int i = 0; i < n - 1; ++i) {
            grafDobleDirigit[arestesOrdenades[idxArestesMST[i]].getPrimer()].add(arestesOrdenades[idxArestesMST[i]].getSegon());
            grafDobleDirigit[arestesOrdenades[idxArestesMST[i]].getSegon()].add(arestesOrdenades[idxArestesMST[i]].getPrimer());
        }
        for (int i = 0; i < n; ++i) {
            System.out.print(i + ": ");
            Iterator<Integer> it = grafDobleDirigit[i].iterator();
            while(it.hasNext()) {
                System.out.print(it.next() + ", ");
            }
        }

        //int[] cicleEuleria = cercarCicleEuleria(grafDobleDirigit);
        //for (int i = 0; i < cicleEuleria.length; ++i) System.out.print(cicleEuleria[i] + ", ");
        //System.out.println();
        return new int[1];
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

    int[] cercarCicleEuleria(ArrayList<Integer>[] grafDobleDirigit) {
        int[] cicle = new int[2 * (n - 1)];
        dfs(grafDobleDirigit, cicle, 0, 0, -1);
        return cicle;
    }

    int dfs(ArrayList<Integer>[] grafDobleDirigit, int[] cicle, int idxActual, int vtxActual, int vtxPrevi) {
        cicle[idxActual] = vtxActual;
        Iterator<Integer> it = grafDobleDirigit[vtxActual].iterator();
        int idx = -1;
        while (it.hasNext()) {
            int proper = it.next();
            if (proper != vtxPrevi) {
                idx = dfs(grafDobleDirigit, cicle, idxActual+1, proper, vtxActual);
            }
        }
        if (idx != -1) {
            cicle[idx] = vtxActual;
            return idx+1;
        }
        else return idxActual+1;
    }

    private void imprimirArrayParellInt(ParellInt[] arestes) {
        int mida = arestes.length;
        for (int i = 0; i < mida; ++i) {
            System.out.print(arestes[i].toString() + ", ");
        }
        System.out.println();
    }
}