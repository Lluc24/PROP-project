package ??;
import ????;

public class GestioSolucions {
    // Llista de solucions
    private list<Solucio> solucions;//llista de solucions que tracta
    private Cataleg cataleg;// relació amb el catàleg
    private Algorisme algorismeAct; //algorisme de la solució que esta tractant
    private list<Vorac> vorac; //relació amb els algorismes de tipus voraç
    private list<Aproximacio> aproximacio; //relació amb els algorismes de tipus aproximacio


    // Constructor
    public void gestioAlgorisme(string tipusAlgorisme) {

        //la classe cataleg crida aquesta funcio passant-me el tipus d'algorisme,
        // jo creo una instància de l'algorisme del tipus especificat
        // aquest algorisme creat passa a ser algorismeAct (aixo de moment no)

        if (tipusAlgorisme == vorac){
            bool trobat = false;
            for (Vorac v: vorac & !trobat){
                trobat = true;
                algorismeAct = v;
                /*
                if (v.getParametre() == parametre) {
                    algorismeAct = v;
                    trobat = true;
                }
                */
            }
            if (!trobat) {
                Vorac v = new Vorac();
                vorac.add(v);
                algorismeAct = v;
            }
        }
        else if (tipusAlgorime == aproximacio){
            bool trobat = false;
            for (Aproximacio a: aproximacio & !trobat){
                trobat = true;
                algorismeAct = a;
                /*
                if (v.getParametre() == parametre) {
                    algorismeAct = v;
                    trobat = true;
                }
                */
            }
            if (!trobat) {
                Aproximacio a = new Aproximacio();
                aproximacio.add(a);
                algorismeAct = a;
            }
        }
        else {
            System.out.println("GestioSolucio: error al especificar el tipus d'algorisme");
        }

    }

    public void creaSolucio(const &vector<Productes> productes, const& vector<vector<int>> similituds, string nomSolucio){
        //la classe cataleg crida aquesta funcio passant-me la matriu de similituds i nomSolucio
        //crido a la funcio publica Resol de l'algorismeAct passant-li com a paràmetre similituds i nomSolucio
        for (Solucio s: solucions){
            if (s.getNom() == nomSolucio) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        algorismeAct.resol(productes, similituds, nomSolucio);
    }

    public void modificarSolucio (Producte prod1, Producte prod2, string nomSolucioAnt, string nomSolucioNova){
        //busco una solucio al del conjunt de solucions que tingui nom = nomSolucio
        //si no n'hi ha cap, aviso del error
        //si la trobo, en creo una copia amb nom nomSolucioNova , cridant a la funció copia(nomSolucioNova) de la classe solucio
        //afegeixo la nova solucioModificada a la llista de solucions
        //un cop creada la copia, crido a la funcio esModificada() de la copia
        //i despres crido a la funcio intercanvia(prod1,prod2)

        for (Solucio s: solucions){
            if (s.getNom() == nomSolucioNova) System.out.println("GestioSolucions: error ja existeix una solucio amb aquest nom");
        }
        bool trobat = false;
        for (Solucio s: solucions){
            if (s.getNom() == nomSolucioAnt){
                trobat = true;
                SolucioModificada solMod = SolucioModificada(s);
                solucions.add(solMod);
                solmod.intercanvia(prod1, prod2);
            }
        }
        if (!trobat)System.out.println("GestioSolucions: error no existeix una solucio amb aquest nom");
    }

    // Afegir una nova solució
    public void afegeixSolucio(vector<Productes> solucio, string tipusAlgorisme, string nom) {
//         la clase algorisme et passa la solucio  sol
//        has de fer que sol sigui una nova instància del conjunt de solucions
        //també s'ha d'afegir aquesta nova solucio a la llista solucions (atribut privat d'aquesta mateixa classe)
        Solucio s = new Solucio(solucio, tipusAlgorisme, nom);
        solucions.add(solucio);
    }

    // Eliminar una solució
    public void eliminarSolucio(string nomSolucio) {
        //busco una solucio al del conjunt de solucions que tingui nom = nomSolucio
        //si no n'hi ha cap, aviso del error
        //si la trobo, elimino la instància i la elimino de la meva llista de solucions
        for (Solucio s: solucions){
            if (s.getNom() == nomSolucio) solucions.remove(s);
        }
    }

    // Obtenir totes les solucions
    public List<Solucio> mostrarSolucions() {
        //per cada Solucio de la llista solucions, s'ha de cridar a la seva funcio publica mostrarSolucio()
        for (Solucio s: solucions) s.mostrarSolucio;
    }
}
