package layers.domain;

import layers.domain.controllers.CtrlDomini;
import layers.domain.utils.Pair;

import java.util.Scanner;

public class Driver {
    private static CtrlDomini ctrlDomini;
    private static final int numGestions = 3;
    private static final int[] numAccions = {5, 6};
    private static final String formatNoEnter = "Fomat incorrecte: \"%s\" no es un enter";
    private static final String formatNoDecimal = "Fomat incorrecte: \"%s\" no es un decimal";

    public static void main(String[] args) {
        ctrlDomini = CtrlDomini.getCtrlDomini();
        executar();
    }

    private static void executar() {
        boolean finalitzat = false;
        Scanner scanner = new Scanner(System.in);
        while (!finalitzat) {
            imprimirMenuGestio();
            int gestio = demanaInt(formatNoEnter, scanner);

            switch (gestio) {
                case 1: // Gestio productes i similituds
                    imprimirMenuAccioDeGestio1();
                    int accioGestio1 = demanaInt(formatNoEnter, scanner);
                    switch (accioGestio1) {
                        case 1: // Mostrar tots els productes
                            ctrlDomini.mostrarCataleg();
                            break;
                        case 2: // Veure un producte
                            System.out.println("Entra el nom del producte");
                            String nomProducteCase2 = scanner.next();
                            ctrlDomini.mostrarProducte(nomProducteCase2);
                            break;
                        case 3: // Crear producte
                            System.out.println("Entra el nom del producte");
                            String nomProducteCase3 = scanner.next();
                            int midaCataleg = ctrlDomini.getMidaCataleg();
                            if (midaCataleg > 0) {
                                System.out.println("Entra un per un el nom dels productes ja existents seguit per la similitud amb el producte " + nomProducteCase3);
                                Pair<String, Double>[] similituds = new Pair[midaCataleg];
                                for (int i = 0; i < midaCataleg; ++i) {
                                    String producte = scanner.next();
                                    double similitud = demanaDouble(formatNoDecimal, scanner);
                                    similituds[i] = new Pair<>(producte, similitud);
                                }
                                ctrlDomini.afegirProducte(nomProducteCase3, similituds);
                            } else {
                                ctrlDomini.afegirProducte(nomProducteCase3);
                            }
                            break;
                        case 4: // Eliminar un producte
                            System.out.println("Entra el nom del producte");
                            String nomProducteCase4 = scanner.next();
                            ctrlDomini.eliminarProducte(nomProducteCase4);
                            break;
                        case 5: // Editar una similitud
                            System.out.println("Entra el nom dels dos productes a modificar la similitud seguit del nou grau de similitud");
                            String nomProducte1 = scanner.next();
                            String nomProducte2 = scanner.next();
                            double similitud = demanaDouble(formatNoDecimal, scanner);
                            ctrlDomini.editarSimilitud(nomProducte1, nomProducte2, similitud);
                            break;
                        default:
                            System.out.println("Numero fora de rang: " + accioGestio1 + " no esta entre 1 y " + numAccions[gestio - 1]);
                            break;
                    }
                    break;

                case 2: // Gestio solcucions
                    imprimirMenuAccioDeGestio2();
                    int accioGestio2 = demanaInt(formatNoEnter, scanner);
                    switch (accioGestio2) {
                        case 1: // Veure totes les solucions
                            ctrlDomini.mostrarSolucions();
                            break;
                        case 2: // Veure una solucio
                            System.out.println("Entra el nom de la solucio");
                            String nomSolucioCase2 = scanner.next();
                            ctrlDomini.mostrarSolucio(nomSolucioCase2);
                            break;
                        case 3:  // Crear una solucio
                            System.out.println("Entra el nom de la solucio");
                            String nomSolucioCase3 = scanner.next();
                            ctrlDomini.crearSolucio(nomSolucioCase3);
                            break;
                        case 4: // Eliminar una solucio
                            System.out.println("Entra el nom de la solucio");
                            String nomSolucioCase4 = scanner.next();
                            ctrlDomini.eliminarSolucio(nomSolucioCase4);
                            break;
                        case 5: // Intercambiar dos productes en una solucio
                            System.out.println("Entra el nom de la solucio seguit dels dos productes a intercambiar en la solucio");
                            String nomSolucioCase5 = scanner.next();
                            String nomProducte1 = scanner.next();
                            String nomProducte2 = scanner.next();
                            ctrlDomini.modificarSolucio(nomSolucioCase5, nomProducte1, nomProducte2);
                            break;
                        case 6: // Canviar d'algorisme
                            System.out.println("Entra el nom del algorisme: greedy, aproximacio, algorismeBT");
                            String nomAlgorisme = scanner.next();

                            if (nomAlgorisme.equals("greedy")) {
                                System.out.println("Entra el valor del parametre producteInicial: ");
                                int producteIncial = demanaInt(formatNoEnter, scanner);
                                System.out.println("Entra el valor del parametre numIteracions: ");
                                int numIteracions = demanaInt(formatNoEnter, scanner);
                                ctrlDomini.canviarAlgorisme(nomAlgorisme, producteIncial, numIteracions);
                            }
                            else ctrlDomini.canviarAlgorisme(nomAlgorisme);
                            break;
                        default:
                            System.out.println("Numero fora de rang: " + accioGestio2 + " no esta entre 1 y " + numGestions);
                            break;
                    }
                    break;

                case 3:
                    System.out.println("Fins la proxima!");
                    finalitzat = true;
                    break;

                default:
                    System.out.println("Numero fora de rang: " + gestio + " no esta entre 1 y " + numGestions);
                    break;
            }
        }
    }

    private static void imprimirMenuGestio() {
        System.out.println("Quina gestio vols realitzar?");
        System.out.println("[1] Gestionar els productes i similituds de la prestatgeria");
        System.out.println("[2] Gestionar les solucions existents i calcular una nova");
        System.out.println("[3] Sortir");
    }

    private static void imprimirMenuAccioDeGestio1() {
        System.out.println("Selecciona l'accio:");
        System.out.println("[1] Visualitzar tots els productes");
        System.out.println("[2] Visualitzar un producte especific");
        System.out.println("[3] Crear un nou producte");
        System.out.println("[4] Eliminar un producte");
        System.out.println("[5] Editar una similitud");
    }

    private static void imprimirMenuAccioDeGestio2() {
        System.out.println("Selecciona l'accio:");
        System.out.println("[1] Visualitzar totes les solucions existents");
        System.out.println("[2] Visualitzar una solucio especifica");
        System.out.println("[3] Calcular una solucio per la prestatgeria actual");
        System.out.println("[4] Eliminar una solucio");
        System.out.println("[5] Editar una solucio");
        System.out.println("[6] Triar tipus d'algorisme per resoldre");
    }

    private static int demanaInt(String err, Scanner scanner) {
        while (!scanner.hasNextInt()) {
            String aux = String.format(err, scanner.next());
            System.out.println(aux);
        }
        return scanner.nextInt();
    }

    private static double demanaDouble(String err, Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            String aux = String.format(err, scanner.next());
            System.out.println(aux);
        }
        return scanner.nextDouble();
    }
}
