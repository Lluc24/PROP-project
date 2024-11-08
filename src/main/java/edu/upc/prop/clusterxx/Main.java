package edu.upc.prop.clusterxx;

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

      Algorisme a = new Aproximacio();
      a.solucionar(similituds);
  }
}