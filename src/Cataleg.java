
import Producte

public class Cataleg {
    //Classe Cataleg
     //Atributs

     private int numProd_Total;
     public Producte[] Cataleg_Productes;
     public Producte[][] Cataleg_Similituds;

     // Constructor
     /**
          Descripció: Es crea un objecte cataleg buit, sense cap producte
      */
     public Cataleg(int numProd_Total) {
          //creacio Cataleg Productes
          Producte[] aux1 = new Producte[numProd_Total];
          aux1 = this.Cataleg_Productes;

          //Creacio Cataleg_Similituds
          Producte[][] aux2 = new Producte[numProd_Total][numProd_Total];
          aux2 = this.Cataleg_Similituds;


     }

     //Metodes
     /**
          Descripció: S'afegeix un producte si aquest no es troba al cataleg
     */
     public void afegir_producte(Producte nou) {
          for (int i = 0; )
     }

     /**
          Descripció:
     */
     public void eliminar_producte() {

     }

     /**
          Descripció:
     */
     public void editar_similitud() {

     }

     //Consultores
     /**
          Descripció:
     */
     public void consultar_productes() {

     }

     /**
          Descripció:
     */
     public void consultar_similitud(){

     }

}