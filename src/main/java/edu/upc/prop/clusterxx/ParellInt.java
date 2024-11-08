package edu.upc.prop.clusterxx;

public class ParellInt {
    private int primer, segon;

    public ParellInt(int e1, int e2) {
        primer = e1;
        segon = e2;
    }

    public int getPrimer() {
        return primer;
    }
    public int getSegon() {
        return segon;
    }
    public void setPrimer(int e1) {
        primer = e1;
    }
    public void setSegon(int e2) {
        segon = e2;
    }

    public String toString() {
        return "{" + primer + ", " + segon + "}";
    }
}
