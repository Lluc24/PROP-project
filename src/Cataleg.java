
import Producte;
import pair;
import java.util.ArrayList;
/**
 * @Class Cataleg
 * Cataleg representa el conjunt de productes (@see Classe Productes) que es troben dins del sistema
 * @author Alejandro Lorenzo Navarro
 * @version 2.0
 * 
 * Tots el productes estan dins de un Arraylist. 
 * Es poden identificar tant per nom o per index.
 * Basat en el funcionament de ArrayList no hi ha espais buits entre productes.
 * Tots el index gerenats per aquesta classe son valits sempre que no s'elimini nigun element de la llista, 
 * afegir-ne no causa problemas
 * 
 * Alguns metodes tenen dos versions, que es diferencien per el parametres que reben, el nom del producte o el seu index.
 * Es rellevant saber que els metodes que reben un index tenen un cost menor, ja que no es fan busquedes dels productes
 * es pren com a condicio que el dos indexos representen productes dins de la classe.
 * 
 * El primer index sempre tindra valor '0', el metode num_prod_actual sempre retorna la mida correcte del cataleg, 
 * es a dir el index seguent al ultim producte.
 */
public class Cataleg {
    //Classe Cataleg
     //Atributs

     /** Un Array List Representa el conjunt de productes es troben din del sistema */
     private ArrayList<Producte> Cataleg_Productes;

     // Constructor

    /**
     * Metode de construccio d'un cataleg buit, sense cap producte
     */
    public Cataleg() {
          //creacio Cataleg Productes
          ArrayList<Producte> aux = new ArrayList<Producte>();
          this.Cataleg_Productes = aux;
     }

     //Metodes
     /**
      * Descripció: S'afegeix un producte al final del cataleg si no es troba al cataleg un amb el mateix nom, 
      * tambe es confiquren les similituds associades amb el nou producte.
      * Aquests metode se ha de fer servir per la creacio i de productes dins del sistema.
      * @param new_nom Nom del producte a afegir
      * @param llista_simi Similituds dels productes, < Nom_Productes, Similitud>
      */
     public void afegir_producte(String new_nom, pair<String, Double>[] llista_simi) {
          //Existe producto con new_nom
          if (Cataleg_Productes.find_prod(new_nom)) return; //Error

          //Nova instancia producta
          int new_index = Cataleg_Productes.size();
          ArrayList<Double> new_simi = new ArrayList<Double>(new_index+1);
          
               //Afegim les similituds a la llista
               for (int i = 0; i < llista_simi.size(); ++i) {
                    int index_in = index_prod(llista_simi(i).first);
                    new_simi.add(index_in, llista_simi(i).second); //Afegim a la nova llista de similituds
                    Cataleg_Productes(index_in).llista_Similituds.add(new_index, llista_simi(i).second); //La similitud del producte preexisten amb el nou
               }

          Producte new_prod = new Producte(new_index, new_nom, new_simi);
          Cataleg_Productes.add(new_index, new_prod);

     }

     /**
      * Descripció: S'elimina un producte, i totes les similituds associades determinat.
      * Warning: Tots el indexos inicialitzats localment abans de l'execucio de la funció seran erronis
      * @param index_out Index del producte a eliminar
      */
     public void eliminar_producte_index(int index_out) {
          Cataleg_Productes.remove(index_out);

          //Eliminem totes les similituds associades al producte
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               Cataleg_Productes(i).llista_Similituds.remove(index_out); 
          }

          //Actualitzem tots els index del productes
          for (int i = index_out; i < Cataleg_Productes.size(); ++i) {
               Cataleg_Productes(i).index = i;
          }
     }

     /**
      * Descripció: S'elimina un producte, i totes les similituds associades determinat si aquest es troba dins del cataleg
      * Warning: Tots el indexos inicialitzats localment abans de l'execucio de la funció seran erronis
      * @param nom_out Nom del producte que es vol eliminar
      * @see eliminar_producte_index(int index_out)
      */
     public void eliminar_producte_nom(String nom_out) {
          
          //Busquem l'index del producte a eliminar
          int index_out = Cataleg_Productes.index_prod(nom_out);
          
          //Si no es troba el producte a cataleg es llença error
          if (index_out == -1) return; //error

          eliminar_producte_index((index_out));
     }


     /**
      *  Descripció: Modifica la similituds entre el dos productes donats tal que es igual a la similitud donada
      * @param index_prod1 Index del primer producte
      * @param index_prod2 Index del segon producte
      * @param new_simi Nova similitud
      */
     public void editar_similitud_index(int index_prod1, int index_prod2, double new_simi) {
          //Modifiquem les similituds
          Cataleg_Productes(index1).llista_Similituds.set(index2, new_simi);
          Cataleg_Productes(index2).llista_Similituds.set(index1, new_simi);
     }

     /**
      *  Descripció: Modifica la similituds entre el dos productes donats tal que es igual a la similitud donada
      *  Si algun dels productes no es troba al cataleg es es dona error
      * @param nom_prod1 String que representa el nom del primer producte
      * @param nom_prod2 String que representa el nom del segon producte
      * @param new_simi Nova similitud entre el dos productes
      */
     public void editar_similitud(String nom_prod1, String nom_prod2, double new_simi) {
          int index1 =Cataleg_Productes.index_prod(nom_prod1);
          if (index1 == -1) return; //error


          int index2 = Cataleg_Productes.index_prod(nom_prod2);
          if (index2 == -1) return; //error

          //Modifiquem les similituds
          Cataleg_Productes(index1).llista_Similituds.set(index2, new_simi);
          Cataleg_Productes(index2).llista_Similituds.set(index1, new_simi);

     }

     //Consultores
     /**
      * 
      * @param nom Nom del producte a buscar
      * @return True si es troba dins del cataleg, si no FALSE
      */
     public boolean find_prod(String nom_in){
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               if (Cataleg_Productes(i).nom == nom_in) return true;
          }
          return false;

     }

     /**
      * Valida si el index rebut es valid, i per tant es pot fer servir per accedir al cataleg
      * @param index_in Index a validar
      * @return TRUE si es valid, FALSE si no
      */
     public boolean valida_index(int index_in) {
          return (index_in < Cataleg_Productes.size()); 
     }

     /**
      * 
      * @param nom Nom del producte a buscar
      * @return Retorna el index del producte amb el nom =  nom_in, si no es troba el producte retorna -1
      */
     public int index_prod(String nom_in){
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               if (Cataleg_Productes(i).nom == nom_in) return i;
          }
          return -1;
     }

    /**
     *
     * @param index_in Index del producte
     * @return Es retorna el nom de producte representat per el index
     */
     public String getNomProd_index(int index_in) {
         return Cataleg_Productes.get(i).nom;
     }

     /**
      * 
      * @param nom Nom del producte a buscar
      * @return Retorna el producte amb nom = nom_in al parametre nom
      * @see idenx_prod(String nom_in)
      */
     public producte consultar_producte(String nom_in) {
          int index_out = Cataleg_Productes.index_prod(nom_in);
          if (index_out == -1) return null; //Que retornem si no hi es el producte
          else return Cataleg_Productes(index_out);
     }

     /**
      * Pre: El indexos han de representar productes que es troben al cataleg
      * @param index_prod1 Index del primer prodcute
      * @param index_prod2 Index del segon producte
      * @return La similitud entre el dos productes 
      */
     public double consultar_similitud_index(int index_prod1, int index_prod2) {
          return Cataleg_Productes(index_prod1).llista_Similituds(index_prod2);
     }
     /**
      * Descripcio: Retorna la similituds entre dos productes donats, si algun producte no es troba retorna -1
      * @param nom_prod1 Nom del primer producte
      * @param nom_prod2 Nom del segon producte
      * @return Integer igual al valor de la similitud entre el dos productes
      * @see consultar_similitud_index(int index_prod1, int index_prod2)
      */
     public double consultar_similitud_nom(Producte nom_prod1, Producte nom_prod2){
          int index_prod1 = Cataleg_Productes.index_prod(nom_prod1);
          if (index_prod1 == -1) return -1;
          
          int index_prod2 = Cataleg_Productes.index_prod(nom_prod2);
          if (index_prod2 == -1) return -1;

          return Cataleg_Productes.consultar_similitud_index(index_prod1, index_prod2);
     
     }

    /**
     * 
     * @param index_prod Index del producte 
     * @return Retornar una ArrayList, que representa el valor integer de les similituds del producte
     */
    public ArrayList<Double> consultar_similituds_producte_index(int index_prod) {
          return Cataleg_Productes(index_prod).llista_Similituds;
     }

     /**
      * 
      * @param nom_prod Nom del producte 
      * @return Retornar una ArrayList, que representa el valor integer de les similituds del producte, si no es troba retorna null
      * @see consultar_similituds_producte(int index_prod)
      */
    public ArrayList<Double> consultar_similituds_producte_string(String nom_prod) {
          int index_prod = Cataleg_Productes.index_prod(nom_prod);
          if (index_prod == -1) return null;

          return Cataleg_Productes.consultar_similituds_producte_index(index_prod);
     }

     /**
      * 
      * @return Integer que es igual al nombre de productes al cataleg
      */
     public int num_prod_act() {
          return Cataleg_Productes.size();;
     }

    /**
     *
     * @return El cataleg de productes
     */
    public ArrayList<Producte> consultar_cataleg(){
          return Cataleg_Productes;

     }

    /**
     * Transforma un arrayList de Double en un Double[]
     * @param aux ArrayList a transformar
     * @return Un double[] con el mateixos valors de aux.
     */
     private double[] transformArray(ArrayList<double> aux) {
        double[] ret = new double[aux.size()];
        for (int i = 0; i < aux.size(); ++i) {
            ret[i] = aux.get(i);
        }
        return ret;
     }

    /**
     * Transforma el cataleg Productes, en una matriu de similituds.
     * Cada fila respresenta de les similituds del productes.
     * @return Matriz de doubles de mida  Num_productes x Num_productes
     */
     public double[][] getMatriuSimilituds() {
         double[][] ret_MS = new double[Cataleg_Productes.size()][Cataleg_Productes.size()];

         for (int i = 0; i < Cataleg_Productes.size(); ++i) {
              double[] aux = Cataleg.transformArray(Cataleg_Productes.get(i).llista_Similituts)
              ret_MS[i] = aux;
         }
         return ret_MS;
     }



}