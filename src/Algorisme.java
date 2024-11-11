import Cataleg

//Classe Algorisme

public abstract class Algorisme {
     public List<String> parametres;

     public Algorisme(List<String> parametres) {
          this.parametres = parametres;
     }

     //Obté els paràmetres de l'algorisme
     public List<String> getParametres() {
          return parametres;
     }

     // Estableix els paràmetres de l'algorisme
     public void setParametres(List<String> parametres) {
          this.parametres = parametres;
     }

     // Implementat per les subclasses per donar una solució
     public abstract Solució resoldre(Cataleg cataleg);
}