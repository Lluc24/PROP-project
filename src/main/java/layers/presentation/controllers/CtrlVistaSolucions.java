package layers.presentation.controllers;

import layers.domain.controllers.CtrlSolucions;
import layers.domain.excepcions.FormatInputNoValid;
import layers.domain.excepcions.IntercanviNoValid;
import layers.domain.excepcions.NomSolucioNoValid;
import layers.presentation.views.VistaGestioAlgorisme;
import layers.presentation.views.VistaInfoSolucio;
import layers.presentation.views.VistaPrincipal;
import layers.presentation.views.VistaPrincipalSolucions;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class CtrlVistaSolucions extends CtrlVistaGeneric {
    private CtrlSolucions ctrlSolucions = null;
    private VistaPrincipalSolucions vistaPplSols;
    private VistaGestioAlgorisme vistaGestioAlgorisme;
    private VistaInfoSolucio vistaInfoSolucio;
    private VistaPrincipal vistaPrincipal;
    private String solucioVisualitzant;
    private enum EstatVista {
        noInicialitzada,
        noVisible,
        esVisible
    }
    EstatVista[] controlVistes = {EstatVista.noInicialitzada,EstatVista.noInicialitzada,EstatVista.noInicialitzada};

    //Constructora
    public CtrlVistaSolucions(CtrlSolucions cs) {
        this.ctrlSolucions = cs;
        vistaPplSols = new VistaPrincipalSolucions(this);
        vistaGestioAlgorisme = new VistaGestioAlgorisme(this);
        vistaInfoSolucio = new VistaInfoSolucio(this);

    }

    public void executar(VistaPrincipal vs) {
        vistaPrincipal = vs;
        vistaPplSols.executar();
        vistaPrincipal.ocultar();
        controlVistes(0);
    }

    @Override
    public void sortirSistema(){
        System.exit(0);
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
    public boolean afegeixSolucio(String nom, int prodPrest){
        try{
            ctrlSolucions.creaSolucio(nom, prodPrest);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
            return false;
        }catch (FormatInputNoValid e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
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
        List<List<String>> solList = new ArrayList<>();
        try {
            ArrayList<ArrayList<String>> solArrayList= ctrlSolucions.getSolucio(s);
            solList = new ArrayList<>(solArrayList);
        }catch (NomSolucioNoValid e) {
            System.out.println(e.getMessage());
        }
        solucioVisualitzant = s;
        boolean mod = ctrlSolucions.esModificada(s);
        if (mod) s = (s + " - Modificada");
        else s = (s + " - Original");
        vistaInfoSolucio.executar(solList, s);
        controlVistes(1);
    }

    /**
     * L'usuari vol gestionar l'algorisme actual
     */
    public void canviarAlgorisme(){
        vistaGestioAlgorisme.executar();
        controlVistes(2);
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
    public void exportar(String path, String nomArxiu) throws FormatInputNoValid{
        ctrlSolucions.guardaSolucio(path, nomArxiu);
    }

    /**
     * L'usuari vol carregar les solucions des d'un fitxer al sistema.
     *
     * @param path lloc on esta el fitxer
     * @param nomArxiu nom del fitxer on es vol guardar
     * @throws FormatInputNoValid si algun dels paràmetres passats no son valids, es llença l'exepcio
     */
    public void importar(String path, String nomArxiu) throws FormatInputNoValid{
        ctrlSolucions.carregaSolucions(path,nomArxiu);
    }

    /**
     *L'usuari vol indtercanviar dos productes amb les seves posicions a la matriu
     *
     * @param index1i fila primer producte
     * @param index1j columna primer producte
     * @param index2i fila segon producte
     * @param index2j columna segon producte
     */
    public void intercanviarProductes(int index1i, int index1j, int index2i,int index2j){
        try{
            ctrlSolucions.modificarSolucio(index1i, index1j, index2i, index2j, solucioVisualitzant);
        }catch(IntercanviNoValid e){
            System.err.println(e.getMessage());
        }catch (NomSolucioNoValid e){
            System.err.println(e.getMessage());
        }catch (FormatInputNoValid e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * La vista ha demanat eliminar la solució que s'esta visualitzant.
     * La funcio fa que la vista principal de solucions mostri un missatge de confirmacio si s'elimina correctament.
     *
     */
    public void eliminarSolucio(){
        try {
            ctrlSolucions.eliminarSolucio(solucioVisualitzant);
        }catch (NomSolucioNoValid e){
            System.err.println(e.getMessage());
        }
        vistaInfoSolucio.ocultar();
        vistaPplSols.executar();
        vistaPplSols.panelInformatiu("La solucio '" +solucioVisualitzant+ "' s'ha eliminat correctament.");
    }

    /**
     * Quan es torna d'una vista volem que la vista s'elimini
     *
     * @param numVista el numero identificador de la vista
     */
    private void ocultarVista(int numVista){
        //VistaPrincipalSoluicons
        if (numVista == 0){
            vistaPplSols.ocultar();
        }
        //VistaInfoSolucio
        else if (numVista == 1){
            vistaInfoSolucio.ocultar();
        }
        //VistaGestioAlgorisme
        else if (numVista == 2){
            vistaGestioAlgorisme.ocultar();
        }
    }

    /**
     * Funcio que gestiona els possibles estats de les vistes
     *
     * @param numVista numero identificador de cada vista
     */
    public void controlVistes(int numVista){
        for(int i = 0; i < 3; i++) {
            if (controlVistes[i] == EstatVista.noInicialitzada) {
                if (i == numVista){
                    controlVistes[i] = EstatVista.esVisible;
                }
            } else {
                if (i == numVista && controlVistes[i] != EstatVista.esVisible) {
                    if (i == 0) vistaPplSols.executar();
                    controlVistes[i] = EstatVista.esVisible;
                } else if (i != numVista && controlVistes[i] == EstatVista.esVisible) {
                    ocultarVista(i);
                    controlVistes[i] = EstatVista.noVisible;
                }
            }
        }
        for (int i = 0; i < 3; ++i){
            if (controlVistes[i] == EstatVista.esVisible) System.out.print("visible, ");
            else if (controlVistes[i] == EstatVista.noVisible) System.out.print("no visible, ");
            else System.out.print("no inicialitzada, ");
        }
    }

    /**
     * El programa torna a la vista principal
     */
    public void tornar(){
        vistaPrincipal.executar();
        controlVistes(3);
    }
}

