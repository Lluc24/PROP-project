public class SolucioModificada extends Solucio {

    // Constructor
    public SolucioModificada(vector<Producte> s, Algorisme a, string n) {
        this.solucio = s;
        this.algorisme = a;
        this.nom = n;
    }

    public void intercanvia(Producte prod1, Producte prod2){
        Producte p_aux;
        bool un_trobat = false;
        for (Producte p : solucio){
            if (p == prod1){
                if (! un_trobat){
                    p_aux = prod1;
                    p = prod2;
                    un_trobat = true;
                }
                else p = p_aux;
            }
            else if (p == prod2){
                if (!un_trobat){
                    p_aux = prod2;
                    p = prod1;
                    un_trobat = true;
                }
                else p = p_aux;
            }
        }
    }
}
