package edu.upc.prop.clusterxx;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
      double[][] similituds = new double[4][4];
      similituds[0][1] = 10;
      similituds[0][2] = 15;
      similituds[0][3] = 20;
      similituds[1][2] = 35;
      similituds[1][3] = 25;
      similituds[2][3] = 30;
      for (int i = 0; i < 4; ++i) similituds[i][i] = 0;

      double[][] similituds2 = new double[9][9];
      for (int i = 0; i < 9; ++i) similituds2[i][i] = 0;
      similituds2[0][1] = 10;
      similituds2[0][2] = 10;
      similituds2[0][3] = 10;
      similituds2[0][7] = 10;
      similituds2[0][8] = 10;
      similituds2[3][4] = 10;
      similituds2[3][6] = 10;
      similituds2[4][5] = 10;

      Algorisme a = new Aproximacio();
      a.solucionar(similituds2);

      CtrlDomini ctrlDomini = CtrlDomini.getCtrlDomini();
      Scanner scanner = new Scanner(System.in);
      boolean sortir = false;

      while (!sortir) {
          System.out.println("\n===== MENU PRINCIPAL =====");
          System.out.println("1. Crear un nou cataleg i afegir productes");
          System.out.println("2. Eliminar un producte del cataleg");
          System.out.println("3. Editar similituds");
          System.out.println("4. Crear una nova solució (gestio d'algorismes)");
          System.out.println("5. Gestionar solucions (mostrar i modificar)");
          System.out.println("6. Sortir");

          int opcio = scanner.nextInt();
          scanner.nextLine(); // Consumir el salt de línea

          switch (opcio) {
              case 1:
                  /*
                  System.out.println("Introdueix el nom del producte que vols afegir:");
                  String nomProducte = scanner.nextLine();
                  ctrlDomini.afegirProducte(nomProducte);

                   */
                  System.out.println("Aquesta funcionalitat encara no està implementada.");
                  break;

              case 2:
                  /*
                  System.out.println("Introdueix el nom del producte que vols eliminar:");
                  String nomEliminar = scanner.nextLine();
                  ctrlDomini.eliminarProducte(nomEliminar);

                   */
                  System.out.println("Aquesta funcionalitat encara no està implementada.");
                  break;

              case 3:
                  System.out.println("Aquesta funcionalitat encara no està implementada.");
                  break;

              case 4:
                  ctrlDomini.gestioAlgorisme();
                  break;

              case 5:
                  ctrlDomini.gestioSolucions();
                  break;

              case 6:
                  System.out.println("Sortint del programa...");
                  sortir = true;
                  break;

              default:
                  System.err.println("Opció no vàlida. Torna-ho a intentar.");
          }
      }
      scanner.close();
  }
}