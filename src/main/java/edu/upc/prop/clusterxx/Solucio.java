package ?;
import ???;

public class Solucio {
    // Atributs
    private vector<Producte> solucio;
    private string algorisme;
    private string nom;

    // Constructor
    public Solucio(vector<Producte> s, string a, string n) {
        this.solucio = s;
        this.algorisme = a;
        this.nom = n;
    }

    // Getters i Setters
    public string getNom() {
        return nom;
    }

    //Metodes addicionals
    public void mostrarSolucio() {

        for (Producte p : solucio){
            System.out.println(p.getNom() + ", ");
        }
        System.out.println(); // Solo realiza un salto de línea

    }

}
