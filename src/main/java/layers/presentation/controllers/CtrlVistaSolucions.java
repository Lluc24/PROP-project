package layers.presentation.controllers;

import layers.domain.controllers.CtrlGeneric;
import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.NomSolucioNoValid;
import layers.presentation.views.VistaGestioAlgorisme;
import layers.presentation.views.VistaInfoSolucio;
import layers.presentation.views.VistaPrincipalSolucions;

import java.util.ArrayList;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    private CtrlSolucions ctrlSolucions = null;
    private VistaPrincipalSolucions vistaPplSols;
    private VistaGestioAlgorisme vistaGestioAlgorisme;
    private VistaInfoSolucio vistaInfoSolucio;

    //Constructora
    public CtrlVistaSolucions(CtrlSolucions cs) {
        this.ctrlSolucions = cs;
        vistaPplSols = new VistaPrincipalSolucions(this);
        vistaGestioAlgorisme = new VistaGestioAlgorisme(this);
        vistaInfoSolucio = new VistaInfoSolucio();
        //vistaInfoSolucio = new VistaInfoSolucio(this);
    }

    @Override
    public void executar() {
        vistaPplSols.executar();
    }

    /**
     * La vista vol saber les solucions actuals al sistema
     *
     * @return retorna un vector amb els noms de les solucions
     */
    public ArrayList<String> getSolucions() {
        return ctrlSolucions.getSolucionsNom();
    }

    /**
     * La vista vol saber de quin tipus és l'algorisme actual
     *
     * @return retorna el tipus del algorisme
     */
    public String getAlgorismeAct(){
        return ctrlSolucions.getAlgorismeAct();
    }

    /**
     * La vista vol afegir una nova solucio al sistema
     *
     * @param nom nom de la nova solucio
     * @param prodPrest numero de productes per prestatge de la nova solucio
     */
    public void afegeixSolucio(String nom, int prodPrest){
        try{
            ctrlSolucions.creaSolucio(nom, prodPrest);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * La vista vol canviar l'algorisme actual
     *
     * @param tipusAlgorisme el tipus del nou algorisme que vol com a actual
     */
    public void gestioAlgorisme(String tipusAlgorisme){
        try {
            ctrlSolucions.gestioAlgorisme(tipusAlgorisme);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * La vista vol mostrar una solucio especifica
     *
     * @param s nom de la solucio especifica que vol mostrar
     */
    public void mostrarSolucio(String s){
        try {
            ArrayList<ArrayList<String>> solList = ctrlSolucions.getSolucio(s);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }
        //vistaInfoSolucio.executar(this,solList);
        try {
            ctrlSolucions.carregaSolucions("/home/lali/Escritorio/q5/prop/", "test1.txt");
        }catch (FormatInputNoValid e){
            System.err.println(e.getMessage());
        }

    }

    /**
     * L'usuari vol gestionar l'algorisme actual
     */
    public void canviarAlgorisme(){
        vistaGestioAlgorisme.executar();
        try {
            ctrlSolucions.guardaSolucio("/home/lali/Escritorio/q5/prop/", "test1.txt");
        }catch (FormatInputNoValid e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param nom nom d'una solucio
     * @return retorna cert si al sistema existeix una solucio amb nom igual a nom. Fals altrament.
     */
    public Boolean existeixSolucio(String nom) {
        return ctrlSolucions.existeixSolucio(nom);
    }

    /**
     * L'algorisme actual és de tipus greedy i l'usuari vol canviar-ne els paràmetres
     *
     * @param idx index del producte amb el que comencar a buscar la millor solucio
     * @param iter numero d'iteracions fins a tornar una solucio
     */
    public void setParametres(int idx, int iter){
        try {
            ctrlSolucions.setParametres(idx, iter);
        }catch (FormatInputNoValid e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * L'usuari vol guardar les solucions actuals del sistema en un fitxer.
     *
     * @param path lloc on esta el fitxer
     * @param nomArxiu nom del fitxer on es vol guardar
     * @throws FormatInputNoValid si algun dels paràmetres passats no son valids, es llença l'exepcio
     */
    public void guardarSolucions(String path, String nomArxiu) throws FormatInputNoValid{
        ctrlSolucions.guardaSolucio(path, nomArxiu);
    }

    /**
     * L'usuari vol carregar les solucions des d'un fitxer al sistema.
     *
     * @param path lloc on esta el fitxer
     * @param nomArxiu nom del fitxer on es vol guardar
     * @throws FormatInputNoValid si algun dels paràmetres passats no son valids, es llença l'exepcio
     */
    public void carregarSolucions(String path, String nomArxiu) throws FormatInputNoValid{
        ctrlSolucions.carregaSolucions(path,nomArxiu);
    }
}
