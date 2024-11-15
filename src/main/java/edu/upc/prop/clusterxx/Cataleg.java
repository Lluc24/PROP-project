package edu.upc.prop.clusterxx;
import java.util.ArrayList;
/**
 * @Class Cataleg
 * @Description Cataleg representa el conjunt de productes que es troben dins del sistema
 * @see Producte
 * @author Alejandro Lorenzo Navarro
 * @version 2.2
 *
 * @Information
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
    public void afegir_producte(String new_nom, Pair<String, Double>[] llista_simi) {
        //Existe producto con new_nom
        if (find_prod(new_nom)) return; //Error

        //Nova instancia producta
        int new_index = Cataleg_Productes.size();
        ArrayList<Double> new_simi = new ArrayList<>();

        //Afegim les similituds a la llista
        for (int i = 0; i < llista_simi.length; ++i) {
            int index_in = get_index_prod(llista_simi[i].first);
            if (valida_index(index_in)) {
                new_simi.add(index_in, llista_simi[i].second); //Afegim a la nova llista de similituds
                Cataleg_Productes.get(index_in).addSimiProd(new_index, llista_simi[i].second); //La similitud del producte preexisten amb el nou
            }
        }

        new_simi.add(new_index, 0.0);
        Producte new_prod = new Producte(new_index, new_nom, new_simi);
        Cataleg_Productes.add(new_index, new_prod);

    }



    /**
      * Descripció: S'elimina un producte, i totes les similituds associades determinat.
      * Warning: Tots el indexos inicialitzats localment abans de l'execucio de la funció seran erronis
      * @param index_out Index del producte a eliminar
      */
     public void eliminar_producte_index(int index_out) {
         if (!valida_index(index_out)) return; //Error;

         Cataleg_Productes.remove(index_out);

          //Eliminem totes les similituds associades al producte
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               Cataleg_Productes.get(i).remSimiProd(index_out);
          }

          //Actualitzem tots els index del productes
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               Cataleg_Productes.get(i).setIndex(i);
          }
     }

     /**
      * Descripció: S'elimina un producte, i totes les similituds associades determinat si aquest es troba dins del cataleg
      * Warning: Tots el indexos inicialitzats localment abans de l'execucio de la funció seran erronis
      * @param nom_out Nom del producte que es vol eliminar
      */
     public void eliminar_producte_nom(String nom_out) {
          
          //Busquem l'index del producte a eliminar
          int index_out = get_index_prod(nom_out);
          
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
         if (valida_index(index_prod1) && valida_index(index_prod2)) {
             //Modifiquem les similituds
             Cataleg_Productes.get(index_prod1).setSimiProd(index_prod2,new_simi);
             Cataleg_Productes.get(index_prod2).setSimiProd(index_prod1, new_simi);
         }  //error
     }

     /**
      *  Descripció: Modifica la similituds entre el dos productes donats tal que es igual a la similitud donada
      *  Si algun dels productes no es troba al cataleg es es dona error
      * @param nom_prod1 String que representa el nom del primer producte
      * @param nom_prod2 String que representa el nom del segon producte
      * @param new_simi Nova similitud entre el dos productes
      */
     public void editar_similitud(String nom_prod1, String nom_prod2, double new_simi) {
          int index1 = get_index_prod(nom_prod1);
          if (index1 == -1) return; //error


          int index2 = get_index_prod(nom_prod2);
          if (index2 == -1) return; //error

         editar_similitud_index(index1, index2, new_simi);
     }

     //Consultores
     /**
      * 
      * @param nom_in Nom del producte a buscar
      * @return True si es troba dins del cataleg, si no FALSE
      */
     public boolean find_prod(String nom_in){
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               if (Cataleg_Productes.get(i).getNom().equals(nom_in)) return true;
          }
          return false;

     }

     /**
      * Valida si el index rebut es valid, i per tant es pot fer servir per accedir al cataleg
      * @param index_in Index a validar
      * @return TRUE si es valid, FALSE si no
      */
     public boolean valida_index(int index_in) {
         return (index_in < Cataleg_Productes.size() && index_in >= 0);
     }

     /**
      * 
      * @param nom_in Nom del producte a buscar
      * @return Retorna el index del producte amb el nom =  nom_in, si no es troba el producte retorna -1
      */
     public int get_index_prod(String nom_in){
          for (int i = 0; i < Cataleg_Productes.size(); ++i) {
               if (Cataleg_Productes.get(i).getNom().equals(nom_in)) return i;
          }
          return -1;
     }

    /**
     *
     * @param index_in Index del producte
     * @return Es retorna el nom de producte representat per el index
     */
     public String getNomProd_index(int index_in) {
         if (!valida_index(index_in)) return null; //error
         return Cataleg_Productes.get(index_in).getNom();
     }

    /**
     *
     * @param index_in Index del prodcucte a retornar
     * @return Producte amb index = index_in
     */
     public Producte getProd_index(int index_in) {
         if (!valida_index(index_in)) return null;
         return Cataleg_Productes.get(index_in);
     }

     /**
      * @param nom_in Nom del producte a buscar
      * @return Retorna el producte amb nom = nom_in al parametre nom
      */
     public Producte getProd_nom(String nom_in) {
          int index_out = get_index_prod(nom_in);
          if (index_out == -1) return null; //Que retornem si no hi es el producte
          else return Cataleg_Productes.get(index_out);
     }

     /**
      * Pre: El indexos han de representar productes que es troben al cataleg
      * @param index_prod1 Index del primer prodcute
      * @param index_prod2 Index del segon producte
      * @return La similitud entre el dos productes 
      */
     public double getSimilitud_index(int index_prod1, int index_prod2) {
          return Cataleg_Productes.get(index_prod1).getSimilituds().get(index_prod2);
     }
     /**
      * Descripcio: Retorna la similituds entre dos productes donats, si algun producte no es troba retorna -1
      * @param nom_prod1 Nom del primer producte
      * @param nom_prod2 Nom del segon producte
      * @return Integer igual al valor de la similitud entre el dos productes
      */
     public double getSimilitud_nom(String nom_prod1, String nom_prod2){
          int index_prod1 = get_index_prod(nom_prod1);
          if (index_prod1 == -1) return -1;
          
          int index_prod2 = get_index_prod(nom_prod2);
          if (index_prod2 == -1) return -1;

          return getSimilitud_index(index_prod1, index_prod2);
     
     }

    /**
     * 
     * @param index_prod Index del producte 
     * @return Retornar una ArrayList, que representa el valor integer de les similituds del producte
     */
    public ArrayList<Double> getAllSimilitudsProd_index(int index_prod) {
          return Cataleg_Productes.get(index_prod).getSimilituds();
     }

     /**
      * 
      * @param nom_prod Nom del producte 
      * @return Retornar una ArrayList, que representa el valor integer de les similituds del producte, si no es troba retorna null
      */
    public ArrayList<Double> getAllSimilitudsProd_nom(String nom_prod) {
          int index_prod = get_index_prod(nom_prod);
          if (index_prod == -1) return null;

          return getAllSimilitudsProd_index(index_prod);
     }

     /**
      * 
      * @return Integer que es igual al nombre de productes al cataleg
      */
     public int num_prod_act() {
          return Cataleg_Productes.size();
     }

    /**
     *
     * @return El cataleg de productes
     */
    public ArrayList<Producte> getCataleg(){
          return Cataleg_Productes;

     }

    /**
     * Transforma un arrayList de Double en un Double[]
     * @param aux ArrayList a transformar
     * @return Un double[] con el mateixos valors de aux.
     */
     static private double[] transformArray(ArrayList<Double> aux) {
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
              double[] aux = Cataleg.transformArray(Cataleg_Productes.get(i).getSimilituds());
              ret_MS[i] = aux;
         }
         return ret_MS;
     }



}