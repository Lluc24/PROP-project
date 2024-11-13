package edu.upc.prop.clusterxx;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    Main divisioner = new Main();
    System.out.println("Dividing 10 by 2 is " + divisioner.division(10,2));
  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}