# Domain layer — the optimization engine

This is the heart of the project: the data model, the three shelf-layout solvers, and the
hand-built data structures that power them. It has **no dependency on the GUI or the file
system** — it can be driven from unit tests or the CLI `Driver` alone.

## Data model

| Class | Role |
|-------|------|
| `Producte` | A product: an index, a name, and its row of pairwise similarity scores. |
| `Solucio` | A computed layout — products grouped into shelves (a matrix of product names). Validates uniqueness and structural consistency. |
| `SolucioModificada` | A solution the user has edited by hand (swaps), tracked separately from the algorithm's original output. |

## The optimization problem

Given `n` products and a symmetric `n × n` **similarity matrix** (entry `[i][j]` = how
similar products `i` and `j` are, `0–100`, zero diagonal), find the ordering of products
that **maximizes the total similarity between consecutive positions**. This is the
**Maximum-Weight Hamiltonian Path** problem — NP-hard. Optionally, a boolean
**constraint matrix** forbids specific pairs from being placed next to each other.

All solvers share the abstract base `Algorisme`, which exposes:

```java
int[] solucionar(double[][] similarities)                          // unconstrained
int[] solucionar(double[][] similarities, boolean[][] forbidden)   // with constraints
```

and return a permutation of product indices — the optimized order.

## The three algorithms

### `AlgorismeBT` — Backtracking (exact)
Exhaustive depth-first search over permutations with pruning, returning the **provably
optimal** layout. It treats the constraint matrix as **hard restrictions**, so a
configuration may legitimately have *no* valid solution. Exponential time → intended for
small catalogs.

### `AlgorismeGreedy` — Greedy heuristic (fast)
Starts from a chosen product and repeatedly appends the most-similar still-unplaced
product. Configurable **start product** and **iteration count** for a multi-start search
that keeps the best run. Treats constraints as soft **priorities** — fast, but no
optimality guarantee. Roughly `O(n²)`.

### `Aproximacio` — 2-Approximation (the showcase)
The classic **metric-TSP 2-approximation**, guaranteeing a result **no worse than twice
the optimum** in **polynomial time**. Pipeline:

1. **Collect edges** — all unrestricted pairs from the similarity matrix (with full
   validation: symmetry, zero diagonal, solvability).
2. **Sort by weight** — a hand-written **Hoare-partition quicksort** (`ordenacioRapida` /
   `particioHoare`).
3. **Maximum spanning tree** — **Kruskal's algorithm** (`kruskal`) backed by a
   **Union-Find** structure for cycle detection.
4. **Double the tree** — turn each tree edge into two opposite arcs (a multigraph where
   every vertex has even degree).
5. **Eulerian traversal** — a DFS (`dfs`) walks the doubled graph.
6. **Shortcut to Hamiltonian** — `simplificar` removes already-visited vertices from the
   Euler walk, yielding a valid product ordering.

With constraints, it tries to respect them but cannot guarantee it (doing so would forfeit
the polynomial-time bound).

## Hand-built data structures — `utils/`

No `java.util` shortcuts for the core algorithmics:

- **`MergeFindSet`** — a Union-Find / disjoint-set structure (with path compression) that
  makes Kruskal's cycle checks near-constant-time.
- **`Pair<A, B>`** — a generic immutable pair, used to represent graph edges.

## Custom exceptions — `excepcions/`

Typed exceptions make invalid input explicit and self-documenting:

| Exception | Thrown when |
|-----------|-------------|
| `FormatInputNoValid` | An input value is out of range or malformed (e.g. a bad similarity, an unsolvable constraint matrix). |
| `IntercanviNoValid` | An invalid product swap inside a solution (product missing, swapping a product with itself…). |
| `NomSolucioNoValid` | A solution name is invalid. |
| `ProducteNoValid` | A product name is invalid (missing, duplicate…). |

## Controllers — `controllers/`

The domain's public surface, behind a Singleton facade:

- **`CtrlDomini`** — main Singleton facade; wires the catalog and solution controllers and
  talks to the persistence layer.
- **`CtrlCataleg` / `CtrlCatalegAmbRestriccions`** — manage the product catalog and its
  adjacency restrictions.
- **`CtrlSolucions`** — create, store, edit, and delete computed solutions.
- **`CtrlGeneric`** — shared controller behavior.
</content>
