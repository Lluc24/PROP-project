package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private CtrlDomini ctrlDomini;

    Driver() {
        ctrlDomini = CtrlDomini.getCtrlDomini();
    }

    public void executar() {
        boolean finalitzat = false;
        while (!finalitzat) {
            System.out.println("Quina gestio vols realitzar?");
            System.out.println("[1] Gestionar els productes i similituds de la prestatgeria");
            System.out.println("[2] Gestionar les distribucions existents i calcular una nova");
            System.out.println("[3] Sortir");
            Scanner scanner = new Scanner(System.in);
            int gestio = scanner.nextInt();

            System.out.println("Selecciona la accio:");
            if (gestio == 1) {
                System.out.println("[1] Visualitzar tots els productes");
                System.out.println("[2] Visualitzar un producte especific pel seu id");
                System.out.println("[3] Crear un nou producte");
                System.out.println("[4] Eliminar un producte");
                System.out.println("[5] Editar una similitud");

                int accio = scanner.nextInt();
                if (accio == 1) {
                    ctrlDomini.mostrarCataleg();
                } else if (accio == 2) {
                    String idProducte = scanner.next();
                    System.out.println("Entra el id del producte");
                    ctrlDomini.mostrarProducte(idProducte);
                } else if (accio == 3) {
                    System.out.println("Entra el nom del producte");
                    String nomProducte = scanner.next();
                    int midaCataleg = ctrlDomini.getMidaCataleg();
                    if (midaCataleg > 0) {
                        System.out.println("Entra un per un el nom dels productes ja existents seguit per la similitud amb el producte " + nomProducte);
                        Pair<String, Double>[] similituds = new Pair[midaCataleg];
                        for (int i = 0; i < midaCataleg; ++i) {
                            similituds[i] = new Pair<>(scanner.next(), Double.parseDouble(scanner.next()));
                        }
                        ctrlDomini.afegirProducte(nomProducte, similituds);
                    }
                    else {
                        ctrlDomini.afegirProducte(nomProducte);
                    }
                } else if (accio == 4) {
                    System.out.println("Entra el nom del producte");
                    String nomProducte = scanner.next();
                    ctrlDomini.eliminarProducte(nomProducte);
                } else if (accio == 5) {
                    System.out.println("Entra el nom dels dos productes a modificar la similitud seguit del nou grau de similitud");
                    ctrlDomini.editarSimilitud(scanner.next(), scanner.next(), Double.parseDouble(scanner.next()));
                }
            } else if (gestio == 2) {
                System.out.println("[1] Visualitzar totes les distribucions existents");
                System.out.println("[2] Visualitzar una distribucio especifica");
                System.out.println("[3] Calcular una distribucio per la prestatgeria actual");
                System.out.println("[4] Eliminar una distribucio");
                System.out.println("[5] Editar una distribucio");
                System.out.println("[6] Triar tipus d'Algorisme per resoldre");

                int accio = scanner.nextInt();
                if (accio == 1) {
                    ctrlDomini.mostrarSolucions();
                } else if (accio == 2) {
                    System.out.println("Entra el nom de la solucio");
                    String nomSolucio = scanner.next();
                    ctrlDomini.mostrarSolucio(nomSolucio);
                } else if (accio == 3) {
                    System.out.println("Entra el nom de la solucio");
                    String nomSolucio = scanner.next();
                    ctrlDomini.crearSolucio(nomSolucio);
                } else if (accio == 4) {
                    System.out.println("Entra el nom de la solucio");
                    String nomSolucio = scanner.next();
                    ctrlDomini.eliminarSolucio(nomSolucio);
                } else if (accio == 5) {
                    System.out.println("Entra el nom de la solucio seguit dels dos productes a intercambiar en la solucio");
                    ctrlDomini.modificarSolucio(scanner.next(), scanner.next(), scanner.next());
                } else if (accio == 6) {
                    System.out.println("Entra el nom del algorisme: greedy, aproximacio, algorismeBT");
                    String nomAlgorisme = scanner.next();
                    ctrlDomini.canviarAlgorisme(nomAlgorisme);
                }
            }
            else if (gestio == 3) {
                System.out.println("Fins la proxima!");
                finalitzat = true;
            }
        }
    }
}
