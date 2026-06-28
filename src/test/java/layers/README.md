# Tests

Two complementary layers of testing back this project.

## Unit tests — JUnit 4 (+ Mockito)

**105 tests across 11 classes.** Every algorithm, controller, and hand-built data structure
has dedicated coverage; Mockito isolates controllers from their collaborators.

| Test class | Under test |
|------------|-----------|
| `TestAlgorismeBT` | Backtracking solver (exact optimum + constraints) |
| `TestAlgorismeGreedy` | Greedy heuristic solver |
| `TestAproximacio` | 2-approximation solver (MST → Euler → Hamiltonian) |
| `TestCtrlCataleg` | Catalog controller |
| `TestCtrlCatalegAmbRestriccions` | Catalog controller with adjacency restrictions |
| `TestCtrlSolucions` | Solution controller |
| `TestMergeFindSet` | Union-Find data structure |
| `TestPair` | Generic pair |
| `TestProducte` | Product model |
| `TestSolucio` | Solution model |
| `TestSolucioModificada` | User-edited solution model |

Run them all:

```bash
./gradlew test
```

## Functional test cases — *Jocs de Prova*

`JocsDeProva/` holds **13 end-to-end scenarios** that drive the system through complete
user workflows (add products, build solutions, swap items, import/export…). Each case is
three files:

| File | Contents |
|------|----------|
| `JocN.txt` | Human-readable description of the functionality being exercised |
| `JocNinput.txt` | The exact input stream fed to the program |
| `JocNoutput.txt` | The expected output for that input |

`ImportarSolucions.txt` is a sample data file for exercising the solution-import path.
</content>
