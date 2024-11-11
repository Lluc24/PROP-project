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

        System.out.print("Cicle euleria: ");
        for (int i = 0; i < cicle.size(); ++i) System.out.print(cicle.get(i) + " ");
        System.out.println();
        simplificar(cicle);
        System.out.print("Cicle hamiltonia: ");
        for (int i = 0; i < cicle.size(); ++i) System.out.print(cicle.get(i) + " ");
        System.out.println();
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
        while (tallaCicle > n + 1) {
            int[] frequencia = new int[n];
            for (int i = 1; i < n; ++i) frequencia[i] = 0;
            frequencia[0] = 1;
            ArrayList<ParellInt> idxsRepeticio = new ArrayList<ParellInt>();
            ArrayList<Double> costRepeticio = new ArrayList<Double>();
            int idxRepeticio = -1;
            int vtxPrevi = 0;
            for (int i = 1; i < tallaCicle-1; ++i) {
                int vtxActual = cicle.get(i);
                if (frequencia[vtxActual] > 0) {
                    if (frequencia[vtxPrevi] > 1) {
                        double costAcumulat = costRepeticio.get(idxRepeticio);
                        costRepeticio.set(idxRepeticio, costAcumulat + similituds[vtxPrevi][vtxActual]);
                    }
                    else {
                        costRepeticio.add(similituds[vtxPrevi][vtxActual]);
                        idxsRepeticio.add(new ParellInt(i-1, -1));
                        ++idxRepeticio;
                    }
                }
                else if (frequencia[vtxPrevi] > 1){
                    double costAcumulat = costRepeticio.get(idxRepeticio);
                    costRepeticio.set(idxRepeticio, costAcumulat + similituds[vtxPrevi][vtxActual]);
                    idxsRepeticio.get(idxRepeticio).setSegon(i);
                }
                ++frequencia[vtxActual];
                vtxPrevi = vtxActual;
            }
            if (frequencia[vtxPrevi] > 1) {
                double costAcumulat = costRepeticio.get(idxRepeticio);
                costRepeticio.set(idxRepeticio, costAcumulat + similituds[vtxPrevi][cicle.get(tallaCicle-1)]);
                idxsRepeticio.get(idxRepeticio).setSegon(tallaCicle-1);
            }
            int millorCandidat = 0;
            ParellInt repeCicle = idxsRepeticio.getFirst();
            ParellInt repeVertexs = new ParellInt(cicle.get(repeCicle.getPrimer()), cicle.get(repeCicle.getSegon()));
            double menorDiferencia = costRepeticio.getFirst() - similituds[repeVertexs.getPrimer()][repeVertexs.getSegon()];
            for (int i = 1; i <= idxRepeticio; ++i) {
                repeCicle = idxsRepeticio.get(i);
                repeVertexs = new ParellInt(cicle.get(repeCicle.getPrimer()), cicle.get(repeCicle.getSegon()));
                double diferencia = costRepeticio.get(i) - similituds[repeVertexs.getPrimer()][repeVertexs.getSegon()];
                if (diferencia < menorDiferencia) {
                    millorCandidat = i;
                    menorDiferencia = diferencia;
                }
            }
            int idxInicial = idxsRepeticio.get(millorCandidat).getPrimer() + 1;
            int idxFinal = idxsRepeticio.get(millorCandidat).getSegon() - 1;
            tallaCicle -= idxFinal - idxInicial + 1;
            for (int i = idxInicial; i <= idxFinal; ++i) {
                cicle.remove(idxInicial);
            }
        }
    }
}