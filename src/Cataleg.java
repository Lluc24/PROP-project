
import Producte;
import pair;
import java.util.ArrayList;

public class Cataleg {
    //Classe Cataleg
     //Atributs

     private int Current_numProd;
     private ArrayList<Producte> Cataleg_Productes;
     private ArrayList<ArrayList<Int>> Cataleg_Similituds; // Matriu de numProd x NumProds, les similituds de tots els productes amb tots el productes

     // Constructor
     /**
          Descripció: Es crea un objecte cataleg buit, sense cap producte
      */
     public Cataleg() {
          //creacio Cataleg Productes
          ArrayList<Producte> aux1 = new ArrayList<>();
          aux1 = this.Cataleg_Productes;
          find_prod(nou)ilituds
          ArrayList<ArrayList<Int>> aux2 = new ArrayList<>()();
          aux2 = this.Cataleg_Similituds;

          this.Current_numProd = 0;

     }

     //Metodes
     /**
          Descripció: S'afegeix un producte si aquest no es troba al cataleg, també afegira 
          Parametres: Rep un produte nou, i un llista de similituds, donada per un producte i la similitud corresponent amb el producte
          Pre: Els valors del producte son correctes
     */
     public void afegir_producte(Producte nou, pair<String, int>[] lista_simi) {
          
          //Si es troba el producte a cataleg es llença error
          if (Cataleg_Productes.contains(nou)) return; //error
          
          Cataleg_Productes.add(nou);

          //Afegim el index al nou producte
          int nou_index = Cataleg_Productes.size-1;
          Cataleg_Productes(nou_index).index = nou_index;

          //Guardem les seves similituds
          ArrayList<int> new_simi = new ArrayList<>();
          for (int i = 0; i < lista_simi.lenght; ++i) {
               Producte aux = find_prod(lista_simi[i]); 
               if (aux != null) {
                    int index_aux = Cataleg_Productes.indexof(aux);
                    Cataleg_Similituds(nou_index).add(index_aux, lista_simi.second);
               } else {
                    //error el producte no es troba al cataleg
               }
          }
          Cataleg_Similituds(nou_index).add(nou_index, 0); 
          ++Current_numProd;
          

     }

     /**
          Descripció: Elimina el producte si existeix dins del cataleg
     */
     public void eliminar_producte(Producte eliminat) {
          
          //Si no es troba el producte a cataleg es llença error
          if (!Cataleg_Productes.contains(nou)) return; //error
          
          int index_out = eliminat.index;
          //Eliminem el producte...
          Cataleg_Productes.remove(index_out);
          //...I les seves similituds
          for (int i = 0; i < Cataleg_Similituds(index_out).size(); ++i) {
               Cataleg_Similituds(index_out).remove(i);     
          }


          --Current_numProd;
     }

     /**
          Descripció:
     */
     public void editar_similitud(Producte prod1, Producte prod2, int new_simi) {
          if (!Cataleg_Productes.contains(prod1) && !Cataleg_Productes.contains(prod2)) return; //error
          int index_1 = prod1.index;
          int index_2 = prod2.index;

          Cataleg_Similituds(index_1)(index_2) = new_simi;
     }

     //Consultores
     /**
          Descripció: Retornem el nombre actual de productes dins del cataleg
      */
     public void num_Prod_act() {
          return Current_numProd;
     }

     /**
          Descripció: Retorna el producte amb nom igual al string donat com a parametre
     */
     public producte consultar_producte(String nom) {
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               if (Cataleg_Productes(i).nom = nom) return Cataleg_Productes(i);
          }
          return null; //Que retornem si no hi ha res

     }void

     /**
          Parametres: Dos Productes prod1, prod2
          Descripció: Donat dos productes es retorna la seva similitud si ambdos productes estan dins de cataleg, si no es aixi es retorna -1
     */ 
     public int consultar_similitud(Producte prod1, Producte prod2){
          if (!Cataleg_Productes.contains(prod1) && !Cataleg_Productes.contains(prod2)) return -1; //error
          int index_1 = prod1.index;
          int index_2 = prod2.index;

          return Cataleg_Similituds(index_1)(index_2);
     }

     /**
          Descripció: Retorna el cataleg de productes
     */
    public ArrayList<Producte> consultar_cataleg(){
          return Cataleg_Productes;

    }

    /**
          Descripció: Retorna el cataleg de similituds;
     */
    public ArrayList<ArrayList<int>> consultar_cataleg_similitud(){
          return Cataleg_Similituds;

    }

}