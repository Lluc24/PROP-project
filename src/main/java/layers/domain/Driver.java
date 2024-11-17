package layers.domain;

import layers.domain.controllers.CtrlCataleg;
import layers.domain.controllers.CtrlSolucions;
import layers.domain.controllers.CtrlDomini;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;
import layers.domain.excepcions.ProducteNoValid;
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
                    CtrlCataleg ctrlCataleg = ctrlDomini.getCtrlCataleg();
                    switch (accioGestio1) {
                        case 1: // Mostrar tots els productes
                            ctrlCataleg.mostrarCataleg();
                            break;
                        case 2: // Veure un producte
                            System.out.println("Entra el nom del producte");
                            String nomProducteCase2 = scanner.next();
                            try {
                                ctrlCataleg.mostrarProducte(nomProducteCase2);
                            }catch (ProducteNoValid e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 3: // Crear producte
                            System.out.println("Entra el nom del producte");
                            String nomProducteCase3 = scanner.next();
                            int midaCataleg = ctrlCataleg.num_prod_act();
                            if (midaCataleg > 0) {
                                System.out.println("Entra un per un el nom dels productes ja existents seguit per la similitud amb el producte " + nomProducteCase3);
                                Pair<String, Double>[] similituds = new Pair[midaCataleg];
                                for (int i = 0; i < midaCataleg; ++i) {
                                    String producte = scanner.next();
                                    double similitud = demanaDouble(formatNoDecimal, scanner);
                                    similituds[i] = new Pair<>(producte, similitud);
                                }
                                try {
                                    ctrlCataleg.afegir_producte(nomProducteCase3, similituds);
                                }catch (ProducteNoValid e){
                                    System.out.println(e.getMessage());
                                }catch (FormatInputNoValid e){
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                try {
                                    ctrlCataleg.afegir_producte(nomProducteCase3);
                                } catch (ProducteNoValid e){
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                        case 4: // Eliminar un producte
                            System.out.println("Entra el nom del producte");
                            String nomProducteCase4 = scanner.next();
                            try {
                                ctrlCataleg.eliminar_producte_nom(nomProducteCase4);
                            }catch (ProducteNoValid e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 5: // Editar una similitud
                            System.out.println("Entra el nom dels dos productes a modificar la similitud seguit del nou grau de similitud");
                            String nomProducte1 = scanner.next();
                            String nomProducte2 = scanner.next();
                            double similitud = demanaDouble(formatNoDecimal, scanner);
                            try {
                                ctrlCataleg.editar_similitud(nomProducte1, nomProducte2, similitud);
                            }catch (ProducteNoValid e){
                                System.out.println(e.getMessage());
                            }catch (FormatInputNoValid e){
                                System.out.println(e.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Numero fora de rang: " + accioGestio1 + " no esta entre 1 y " + numAccions[gestio - 1]);
                            break;
                    }
                    break;

                case 2: // Gestio solcucions
                    imprimirMenuAccioDeGestio2();
                    int accioGestio2 = demanaInt(formatNoEnter, scanner);
                    CtrlSolucions ctrlSolucions = ctrlDomini.getCtrlSolucions();
                    switch (accioGestio2) {
                        case 1: // Veure totes les solucions
                            ctrlSolucions.mostrarSolucions();
                            break;
                        case 2: // Veure una solucio
                            System.out.println("Entra el nom de la solucio");
                            String nomSolucioCase2 = scanner.next();
                            try {
                                ctrlSolucions.mostrarSolucio(nomSolucioCase2);
                            }catch (NomSolucioNoValid e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 3:  // Crear una solucio
                            System.out.println("Entra el nom de la solucio");
                            String nomSolucioCase3 = scanner.next();
                            try {
                                ctrlSolucions.creaSolucio(nomSolucioCase3);
                            }catch (NomSolucioNoValid e) {
                                System.out.println(e.getMessage());
                            }catch (FormatInputNoValid e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 4: // Eliminar una solucio
                            System.out.println("Entra el nom de la solucio");
                            String nomSolucioCase4 = scanner.next();
                            try {
                                ctrlSolucions.eliminarSolucio(nomSolucioCase4);
                            }catch (NomSolucioNoValid e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 5: // Intercambiar dos productes en una solucio
                            System.out.println("Entra el nom de la solucio seguit dels dos productes a intercambiar en la solucio");
                            String nomSolucioCase5 = scanner.next();
                            String nomProducte1 = scanner.next();
                            String nomProducte2 = scanner.next();
                            try {
                                ctrlSolucions.modificarSolucio(nomProducte1, nomProducte2, nomSolucioCase5);
                            }catch (NomSolucioNoValid e) {
                                System.out.println(e.getMessage());
                            }catch (IntercanviNoValid e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 6: // Canviar d'algorisme
                            System.out.println("Entra el nom del algorisme: greedy, aproximacio, algorismeBT");
                            String nomAlgorisme = scanner.next();
                            try {
                                ctrlSolucions.gestioAlgorisme(nomAlgorisme);
                            }catch (FormatInputNoValid e){
                                System.out.println(e.getMessage());
                            }

                            if (nomAlgorisme.equals("greedy")) {
                                System.out.println("Entra el valor del parametre producteInicial: ");
                                int producteIncial = demanaInt(formatNoEnter, scanner);
                                System.out.println("Entra el valor del parametre numIteracions: ");
                                int numIteracions = demanaInt(formatNoEnter, scanner);

                                try {
                                    ctrlSolucions.setParametres(producteIncial, numIteracions);
                                }catch (FormatInputNoValid e){
                                    System.out.println(e.getMessage());
                                }
                            }
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
