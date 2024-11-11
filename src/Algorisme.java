import Cataleg;

//Classe Algorisme

public abstract class Algorisme {

     //Paràmetres comuns


     //

     //Creadora
     public Algorisme() {

     }

     //Getters i Setters per als paràmetres
     //public Y getX() {
          return X;
     }

     //public void setX(String X) {
          this.X = X;
     }

     /**
      *
      */
     public abstract int[] resoldre(Cataleg cataleg);
}