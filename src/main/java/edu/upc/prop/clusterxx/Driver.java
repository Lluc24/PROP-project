package edu.upc.prop.clusterxx;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private CtrlDomini ctrlDomini;

    Driver() {
        ctrlDomini = new CtrlDomini();
    }

    public void executar() {
        System.out.println("Quina gestio vols realitzar?");
        System.out.println("[1] Gestionar els productes i similituds de la prestatgeria");
        System.out.println("[2] Gestionar les distribucions existents i calcular una nova");
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
            }
            else if (accio == 2) {
                String idProducte = scanner.nextLine();
                System.out.println("Entra l'id del producte");
                ctrlDomini.mostrarProducte(idProducte);
            }
            else if (accio == 3) {
                int midaCataleg = ctrlDomini.getMidaCataleg();
                System.out.println("Entra el nom del producte");
                String nomProducte = scanner.nextLine();
                System.out.println("Entra un per un el nom dels productes ja existents seguit per la similitud amb el producte " + nomProducte);
                Pair<String, Double>[] similituds = new Pair[midaCataleg];
                for (int i = 0; i < midaCataleg; ++i) {
                    String[] data = scanner.nextLine().split(" ");
                    similituds[i].first = data[0];
                    similituds[i].second = Double.parseDouble(data[0]);
                }
                ctrlDomini.afegirProducte(nomProducte, similituds);
            }
            else if (accio == 4) {
                System.out.println("Entra el nom del producte");
                String nomProducte = scanner.nextLine();
                ctrlDomini.eliminarProducte(nomProducte);
            }
            else if (accio == 5) {
                System.out.println("Entra el nom dels dos productes a modificar la similitud seguit del nou grau de similitud");
                String[] data = scanner.nextLine().split(" ");
                ctrlDomini.editarSimilitud(data[0], data[1], Double.parseDouble(data[2]));
            }
        }
        else if (gestio == 2) {
            System.out.println("[1] Visualitzar totes les distribucions existents");
            System.out.println("[2] Visualitzar una distribucio especifica");
            System.out.println("[3] Calcular una distribucio per la prestatgeria actual");
            System.out.println("[4] Eliminar una distribucio");
            System.out.println("[5] Editar una distribucio");

            int accio = scanner.nextInt();
            if (accio == 1) {
                ctrlDomini.mostrarSolucions();
            }
            else if (accio == 2) {
                System.out.println("Entra el nom de la solucio");
                String nomSolucio = scanner.nextLine();
                ctrlDomini.mostrarSolucio(nomSolucio);
            }
            else if (accio == 3) {
                ctrlDomini.crearSolucio();
            }
            else if (accio == 4) {
                System.out.println("Entra el nom de la solucio");
                String nomSolucio = scanner.nextLine();
                ctrlDomini.eliminarSolucio(nomSolucio);
            }
            else if (accio == 5) {
                System.out.println("Entra el nom de la solucio seguit dels dos productes a intercambiar en la solucio");
                String[] data = scanner.nextLine().split(" ");
                ctrlDomini.modificarSolucio(data[0], data[1], data[2]);
            }
        }
    }
}
