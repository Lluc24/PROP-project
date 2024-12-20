package layers.domain;

import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.utils.MergeFindSet;
import layers.domain.utils.Pair;

import java.util.*;

public class Aproximacio extends Algorisme {
    private double[][] similituds;
    private int n, m;

    @Override
    public int[] solucionar(double[][] matriuSimilituds, boolean[][] matriuRestrConsec) throws FormatInputNoValid {
        n = matriuSimilituds.length;
        similituds = matriuSimilituds;

        // Cas base:
        if (n == 0) return new int[0];
        if (n == 1) return new int[]{0};

        // Cas extens:

        // Construim un array amb totes les arestes del graf que no tenen restriccio
        List<Pair<Integer, Integer>> arestesOrdenades = new ArrayList<Pair<Integer, Integer>>();
        int k = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (similituds[i][i] != 0) {
                    throw new FormatInputNoValid("La diagonal de la matriu d'adjacencia no es nul.la");
                }
                if (similituds[i][j] != similituds[j][i]) {
                    throw new FormatInputNoValid("La matriu d'adjacencia no es simetrica");
                }
                if (matriuRestrConsec[i][i]) {
                    throw new FormatInputNoValid("La diagonal de la matriu de restriccions no es false");
                }
                if (matriuRestrConsec[i][j] != matriuRestrConsec[j][i]) {
                    throw new FormatInputNoValid("La matriu de restriccions no es simetrica");
                }
                if (!matriuRestrConsec[i][j]) { // No hi ha restriccio, podem afegir l'aresta
                    arestesOrdenades.add(new Pair<>(i, j));
                    ++k;
                }
            }
        }

        m = k;

        // Ordenem les arestes per pes creixent utilitzant QUICKSORT
        ordenacioRapida(arestesOrdenades, 0, m-1);

        // Obtenim l'arbre d'expansio maxim MST. En total te n-1 arestes
        int[] idxArestesMST = kruskal(arestesOrdenades);

        // Construim el digraf on cada aresta no dirigida passa a ser dos arcs en sentits oposats
        // Per representar el digraf utilitzem llistes d'adjacencia
        List<Set<Integer>> grafDobleDirigit = new ArrayList<Set<Integer>>(n);
        for (int i = 0; i < n; ++i) grafDobleDirigit.add(new HashSet<Integer>());
        for (int i = 0; i < n - 1; ++i) { // Recorrem les arestes del MST
            Pair<Integer, Integer> aresta = arestesOrdenades.get(idxArestesMST[i]); // aresta = (u, v)
            grafDobleDirigit.get(aresta.first).add(aresta.second); // Afegim l'arc u -> v
            grafDobleDirigit.get(aresta.second).add(aresta.first); // Afegim l'arc v -> u
        }

        // Trobem un cicle euleria a partir de recorrer el digraf amb un depth-first search
        List<Integer> cicleEuleria = new ArrayList<Integer>();
        dfs(grafDobleDirigit, cicleEuleria, 0, -1);

        // Simplifiquem el cicle euleria per transformar-lo en cicle hamiltonia
        List<Integer> cicleHamiltonia = new ArrayList<Integer>(cicleEuleria);
        simplificar(cicleHamiltonia);

        // Reconstruim el array que sera el cami hamiltonia solucio
        int[] camiHamiltonia = new int[n];
        for (int i = 0; i < n; ++i) camiHamiltonia[i] = cicleHamiltonia.get(i);
        return camiHamiltonia;
    }

    @Override
    public int[] solucionar(double[][] matriuSimilituds) throws FormatInputNoValid {
        n = matriuSimilituds.length;
        boolean[][] matriuRestrConsec = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) matriuRestrConsec[i][j] = false;
        }
        return solucionar(matriuSimilituds, matriuRestrConsec);
    }

    void ordenacioRapida(List<Pair<Integer, Integer>> arestes, int e, int d) {
        if (e < d) {
            int q = particioHoare(arestes, e, d);   // q es la posicio que deixa a la seva esquerra les arestes de menor
                                                    // pes que l'aresta q i la resta a la dreta
            // Apliquem recursivament l'ordenacio en l'esquerra i la dreta obtenint arestes[e..d] ordenades per pes
            ordenacioRapida(arestes, e, q);
            ordenacioRapida(arestes, q + 1, d);
        }
    }

    int particioHoare(List<Pair<Integer, Integer>> arestes, int e, int d) {
        double pivot = similituds[arestes.get(e).first][arestes.get(e).second]; // Utilitzem com a pivot la primera aresta
        int i = e;
        int j = d;
        boolean acabat = false;
        while (!acabat) {
            // Obtenim les posicions i, j tals que:
            // Tota aresta a l'esquerra d'i te menor pes que l'aresta de la posicio pivot
            // Tota aresta a la dreta de j te major pes que l'aresta de la posicio pivot
            while (similituds[arestes.get(i).first][arestes.get(i).second] < pivot) ++i;
            while (similituds[arestes.get(j).first][arestes.get(j).second] > pivot) --j;
            if (i >= j) acabat = true;
            else {
                // Intercanviem les posicions i, j
                Pair<Integer, Integer> aux = arestes.get(i);
                arestes.set(i, arestes.get(j));
                arestes.set(j, aux);
                ++i;
                --j;
                // Es compleix que:
                // arestes[e..i-1] tenen pes menor o igual a l'aresta pivot
                // arestes[j+1..d] tenen pes major o igual a l'aresta pivot
            }
        }
        // Retornem la posicio on hi ha una aresta amb el mateix pes que l'aresta pivot
        return j;
    }

    int[] kruskal(List<Pair<Integer, Integer>> arestesOrdenades) throws FormatInputNoValid {
        MergeFindSet mfs = new MergeFindSet(n);
        int[] idxArestes = new int[n-1]; // Hem de trobar els indexos de les n - 1 arestes que construeixen el MST
        idxArestes[0] = m-1; // La primera aresta sera la de major pes, amb una aresta no fem cap cicle
        int arestesFixades = 1;
        int i = m-2;
        mfs.unir(arestesOrdenades.get(m-1).first, arestesOrdenades.get(m-1).second);

        while (mfs.getNombreConjunts() > 1) { // Mentre no sigui connex
            if (mfs.unir(arestesOrdenades.get(i).first, arestesOrdenades.get(i).second)) {
                // Hem trobat l'aresta i de maxim pes que no fa un cicle
                idxArestes[arestesFixades] = i;
                ++arestesFixades;
            }
            --i;
            if (i < 0) throw new FormatInputNoValid("No es pot solucionar la prestatgeria (massa restriccions)");
        }
        return idxArestes;
    }

    void dfs(List<Set<Integer>> grafDobleDirigit, List<Integer> cicle, int vtxActual, int vtxPrevi) {
        // Cada vertex que es visita s'afegeix al cicle, sigui per expansio de branques o per backtracking
        cicle.add(vtxActual); // Vertex afegir per expansio
        for (int proper : grafDobleDirigit.get(vtxActual)) {
            if (proper != vtxPrevi) {
                dfs(grafDobleDirigit, cicle, proper, vtxActual);
                cicle.add(vtxActual); // Vertex afegit per backtracking
            }
        }
    }

    void simplificar(List<Integer> cicle) {
        int tallaCicle = cicle.size();
        while (tallaCicle > n + 1) {
            int[] frequencia = new int[n]; // frequencia indica quantes vegades apareix un vertex en el cicle
            for (int i = 1; i < n; ++i) frequencia[i] = 0;
            frequencia[0] = 1;

            List<Pair<Pair<Integer, Integer>, Double>> repeticions = new ArrayList<Pair<Pair<Integer, Integer>, Double>>();
            // Una llista de la forma {((u, v), w), ...}
            // Cada element es una repeticio (u, v), es a dir, els vertexs del cicle en les posicions u, v salten
            // els vertexs repetits i w es el pes que correspon a la diferencia de pes en cas d'eliminar la repeticio
            int idxRepeticio = -1;
            int vtxPrevi = 0;

            // El vertex inicial (posicio 0) i final (posicio tallaCicle-1) no pertanyen a repeticions per definicio
            for (int i = 1; i < tallaCicle-1; ++i) {
                int vtxActual = cicle.get(i);
                // Per poder estudiar les repeticions, considerarem tres casos principals:
                if (frequencia[vtxActual] > 0) {    // Cas 1: El vertex actual ja ha sigut visitat previament.
                                                    // Es a dir, pertany a una repeticio
                    // Distingin dos subcasos:
                    if (frequencia[vtxPrevi] > 1) { // Subcas 1: El vertex previ tambe havia sigut visitat previament,
                                                    // tenim una repeticio oberta que li hem d'afegir cost extra
                                                    // corresponent a l'aresta (vtxPrevi, vtxActual)
                        repeticions.get(idxRepeticio).second += similituds[vtxPrevi][vtxActual];
                    }
                    else {  // Subcas 2: El vertex previ s'havia visitat per primera vegada, hem d'iniciar una nova
                            // repeticio de vertexs
                        repeticions.add(new Pair(new Pair(i-1, -1), similituds[vtxPrevi][vtxActual]));
                        ++idxRepeticio;
                    }
                }
                else if (frequencia[vtxPrevi] > 1) {    // Cas 2: El vertex actual es visitat per primer cop i el vertex
                                                        // previ pertany a una repeticio (havia sigut visitat previament).
                                                        // Hem d'afegir el pes de (vtxPrevi, vtxActual), treure el pes
                                                        // de (vertex que inicia la repeticio, vtxActual) i establir
                                                        // vtxActual com a fi de la repeticio. Aixi haurem tancat la
                                                        // repeticio
                    Pair<Pair<Integer, Integer>, Double> p = repeticions.get(idxRepeticio);
                    p.first.second = i;
                    p.second += similituds[vtxPrevi][vtxActual];
                    p.second -= similituds[cicle.get(p.first.first)][vtxActual];
                }
                else {  // Cas 3: El vertex acual i el previ son visitats per primer cop. No cal fer res

                }
                ++frequencia[vtxActual];
                vtxPrevi = vtxActual;
            }
            if (frequencia[vtxPrevi] > 1) { // Estem en el vertex que tanca el cicle, pot ser que el vtxPrevi pertany
                                            // a una repeticio que no ha sigut tancada. Cal reconsiderar el Cas 2
                Pair<Pair<Integer, Integer>, Double> p = repeticions.get(idxRepeticio);
                p.first.second = tallaCicle-1;
                int vtxActual = cicle.get(tallaCicle-1);
                p.second += similituds[vtxPrevi][vtxActual];
                p.second -= similituds[cicle.get(p.first.first)][vtxActual];
            }

            // Tenim totes les repeticions del cicle actual, cal trobar la que tingui una menor diferencia per tal que
            // el cost total es mantingui el mes gran possible
            int millorCandidat = 0;
            double menorDiferencia = repeticions.get(millorCandidat).second;
            for (int i = 1; i <= idxRepeticio; ++i) {
                double diferencia = repeticions.get(i).second;
                if (diferencia < menorDiferencia) { // Hem trobat una repeticio amb menor cost
                    millorCandidat = i;
                    menorDiferencia = diferencia;
                }
            }

            // Tenim la millor repeticio ((u, v), w) a eliminar. Cal eliminar els vertexs que estiguin entre u i v
            Pair<Integer, Integer> millorParell = repeticions.get(millorCandidat).first;
            int idxInicial = millorParell.first + 1;
            int idxFinal = millorParell.second - 1;
            tallaCicle -= idxFinal - idxInicial + 1;    // Nova talla del cicle un cop eliminants els vertexs entre u i v
            for (int i = idxInicial; i <= idxFinal; ++i) {
                cicle.remove(idxInicial);
            }
        }
    }
}