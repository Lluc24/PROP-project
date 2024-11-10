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
        int[] cicleHam = new int[n];
        for (int i = 0; i < n; ++i) cicleHam[i] = cicle.get(i);
        return cicleHam;
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
        int tallaCicle = cicle.size();
        while (tallaCicle > n) {
            boolean[] visitat = new boolean[n];
            for (int i = 1; i < n; ++i) visitat[i] = false;
            visitat[0] = true;
            ArrayList<ParellInt> idxsRepeticio = new ArrayList<ParellInt>();
            ArrayList<Double> costRepeticio = new ArrayList<Double>();
            int nRepeticions = 0;
            int vtxPrevi = 0;
            for (int i = 1; i < tallaCicle-1; ++i) {
                int vtxActual = cicle.get(i);
                if (visitat[vtxActual]) {
                    if (visitat[vtxPrevi]) {
                        Double costAcumulat = costRepeticio.get(nRepeticions);
                        costRepeticio.set(nRepeticions, costAcumulat + similituds[vtxPrevi][vtxActual]);
                    }
                    else {
                        costRepeticio.add(similituds[vtxPrevi][vtxActual]);
                        idxsRepeticio.add(new ParellInt(i-1, -1));
                        ++nRepeticions;
                    }
                }
                else {
                    if (nRepeticions > 0 && idxsRepeticio.get(nRepeticions).getSegon() == -1) {
                        Double costAcumulat = costRepeticio.get(nRepeticions);
                        costRepeticio.set(nRepeticions, costAcumulat + similituds[vtxPrevi][vtxActual]);
                        idxsRepeticio.get(nRepeticions).setSegon(i);
                    }
                    visitat[vtxActual] = true;
                }
            }
            if (nRepeticions > 0 && idxsRepeticio.get(nRepeticions).getSegon() == -1) {
                Double costAcumulat = costRepeticio.get(nRepeticions);
                costRepeticio.set(nRepeticions, costAcumulat + similituds[vtxPrevi][cicle.get(tallaCicle-1)]);
                idxsRepeticio.get(nRepeticions).setSegon(tallaCicle-1);
            }
            for (int i = 0; i < cicle.size(); ++i) {
                System.out.print("Cicle: " + cicle.get(i));
            }
            System.out.println();
            for (int i = 0; i < nRepeticions; ++i) {
                System.out.println("Shortcut de " + idxsRepeticio.get(i).getPrimer() + " a " + idxsRepeticio.get(i).getSegon());
                double costEstalviat = costRepeticio.get(i) - similituds[idxsRepeticio.get(i).getPrimer()][idxsRepeticio.get(i).getSegon()];
                System.out.print("Cost estalviat: " + costEstalviat);
            }
            tallaCicle = 0;
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