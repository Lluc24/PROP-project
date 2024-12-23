package layers.presentation.views;

import layers.domain.Producte;
import layers.domain.excepcions.FormatInputNoValid;
import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.*;

/**
 * @Class VistaPrincipal
 * @Description Es la primera vista que es mostra a l'executar el programa
 * @see VistaGenerica
 * @author Lluc Santamaria Riba
 * @version 3.4
 *
 * @Information
 * Aquesta vista proporciona un menu de diversos botons:
 * <ul>
 *     <li><b>Gestionar cataleg:</b> Inicia la vista especialitzada en gestionar els productes, similituds i restriccions.</li>
 *     <li><b>Gestionar solucions:</b> Inicia la vista especialitzada en crear solucions, veure-les i modificar-les.</li>
 *     <li><b>Importar cataleg:</b> Permet carregar el cataleg des de disc.</li>
 *     <li><b>Importar solucions:</b> Permet carregar les solucions des de disc.</li>
 *     <li><b>Exportar cataleg:</b> Permet guardar el cataleg a disc.</li>
 *     <li><b>Exportar solucions:</b> Permet guardar les solucions a disc.</li>
 *     <li><b>Sortir:</b> Finalitza l'execucio del programa.</li>
 * </ul>
 */
public class VistaPrincipal extends VistaGenerica {

    /** Controlador del cataleg de la capa de presentacio. */
    private final CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions;

    /** Controlador de les solucions de la capa de presentacio. */
    private final CtrlVistaSolucions ctrlVistaSolucions;

    /** Text de l'etiqueta que apareix a sobre dels botons. */
    protected String textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
    /** Etiqueta que apareix a sobre dels botons. */
    protected JLabel etiquetaTriar;

    /** Text del boto per gestionar el cataleg. */
    protected String textBotoCataleg = "Gestionar cataleg";
    /** Boto per gestionar el cataleg. */
    protected Boto botoCataleg;

    /** Text del boto per gestionar les solucions. */
    protected String textBotoSolucions = "Gestionar solucions";
    /** Boto per gestionar les solucions. */
    protected Boto botoSolucions;

    /** Text del boto per guardar el cataleg a disc. */
    protected String textBotoExportarCataleg = "Exportar cataleg";
    /** Boto per guardar el cataleg a disc. */
    protected Boto botoExportarCataleg;

    /** Text del boto per guardar les solucions a disc. */
    protected String textBotoExportarSolucions = "Exportar solucions";
    /** Boto per guardar les solucions a disc. */
    protected Boto botoExportarSolucions;

    /** Text del boto per carregar el cataleg des de disc. */
    protected String textBotoImportarCataleg = "Importar cataleg";
    /** Boto per carregar el cataleg des de disc. */
    protected Boto botoImportarCataleg;

    /** Text del boto per carregar les solucions des de disc. */
    protected String textBotoImportarSolucions = "Importar solucions";
    /** Boto per carregar les solucions des de disc. */
    protected Boto botoImportarSolucions;

    /** Text del boto per sortir del sistema. */
    protected String textBotoSortir = "Sortir";
    /** Boto per sortir del sistema. */
    protected Boto botoSortir;

    /** Titol de l'avis d'importacio correcta. */
    protected String titolImportarOK = "Importacio correcta";
    /** Template del contingut de l'avis d'importacio correcta. */
    protected String templateTextImportarOK = "El fitxer %s s'ha importat correctament.\n" +
            "Les estructures de dades s'han actualitzat";
    /** Titol de l'avis d'importacio incorrecta. */
    protected String titolImportarKO = "Error d'importacio";
    /** Contingut de l'avis d'importacio incorrecta. */
    protected String textImportarKO = "No s'ha pogut importar el fitxer";

    /** Titol de l'avis d'exportacio correcta. */
    protected String titolExportarOK = "Exportacio correcta";
    /** Template del contingut de l'avis d'exportacio correcta. */
    protected String templateTextExportarOK = "El fitxer %s s'ha exportat correctament";
    /** Titol de l'avis d'exportacio incorrecta. */
    protected String titolExportarKO = "Error d'exportacio";
    /** Template del contingut de l'avis d'exportacio incorrecta. */
    protected String textExportarKO = "No s'ha pogut exportar el fitxer";


    /** Titol del dialeg per seleccionar el fitxer de cataleg a importar. */
    protected String titolJFileDialogImportarCataleg = "Obrir el fitxer del cataleg";
    /** Titol del dialeg per seleccionar el fitxer de les solucions a importar. */
    protected String titolJFileDialogImportarSolucions = "Obrir el fitxer de les solucions";
    /** Titol del dialeg per seleccionar el fitxer de cataleg a exportar. */
    protected String titolJFileDialogExportarCataleg = "Guardar el fitxer del cataleg";
    /** Titol del dialeg per seleccionar el fitxer de les solucions a exportar. */
    protected String titolJFileDialogExportarSolucions = "Guardar el fitxer de les solucions";

    /**
     * Constructora de la classe.
     *
     * @param ctrl1: Controlador del cataleg de la capa de presentacio passat com a superclasse.
     * @param ctrl2: Controlador de les solucions de la capa de presentacio passat com a superclasse.
     */
    public VistaPrincipal(CtrlVistaGeneric ctrl1, CtrlVistaGeneric ctrl2) {
        ctrlVistaCatalegAmbRestriccions = (CtrlVistaCatalegAmbRestriccions) ctrl1;
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl2;
    }

    /**
     * Metode executar que construeix la vista amb els seus components i la fa visible.
     */
    @Override
    public void executar() {
        titolFrame = "Vista Principal";
        ajuda = "Estas a la vista principal. Des d'aquesta vista pots provar qualsevol de les quatre funcionalitats " +
                "utilitzant els botons correstponents.\n" +
                "Gestionar cataleg: Permet gestionar els productes i similituds de la prestatgeria.\n" +
                "Gestionar solucions: Permet gestionar les solucions existents i calcular una nova.\n" +
                "Gestionar restriccions: Permet gestionar les restriccions entre productes.\n" +
                "Sortir: Finalitza l'aplicacio.";
        super.executar();
    }

    /**
     * Metode inicialitzarComponents que inicialitza i insereix els components en la vista.
     */
    @Override
    protected void inicialitzarComponents() {
        teBotoTornar = false;

        // Inicialitzem la superclase
        super.inicialitzarComponents();

        // Inicialitzem el panel com a BoxLayout ordenat verticalment
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // Inicialitzem l'etiqueta descriptiva
        etiquetaTriar = new JLabel(textEtiquetaTriar);
        etiquetaTriar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(etiquetaTriar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Gestionar cataleg
        botoCataleg = new Boto(textBotoCataleg);
        botoCataleg.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoCataleg);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Gestionar solucions
        botoSolucions = new Boto(textBotoSolucions);
        botoSolucions.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoSolucions);
        add(Box.createRigidArea(new Dimension(0, 40)));

        // Inicialitzem el boto Importar cataleg
        botoImportarCataleg = new Boto(textBotoImportarCataleg);
        botoImportarCataleg.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoImportarCataleg);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Importar solucions
        botoImportarSolucions = new Boto(textBotoImportarSolucions);
        botoImportarSolucions.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoImportarSolucions);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Exportar cataleg
        botoExportarCataleg = new Boto(textBotoExportarCataleg);
        botoExportarCataleg.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoExportarCataleg);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Exportar solucions
        botoExportarSolucions = new Boto(textBotoExportarSolucions);
        botoExportarSolucions.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoExportarSolucions);
        add(Box.createRigidArea(new Dimension(0, 40)));

        // Inicialitzem el boto Sortir
        botoSortir = new Boto(textBotoSortir);
        botoSortir.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoSortir);

        add(Box.createVerticalGlue());
    }

    /**
     * Metode per acabar l'execucio de la aplicacio.
     */
    @Override
    protected void sortirSistema() {
        ctrlVistaSolucions.sortirSistema();
    }

    /**
     * Metode botoAccionat executat cada vegada que s'acciona un boto de la vista.
     * El boto de la vista que s'ha accionat es diferencia pel text que hi ha en aquest.
     * @param textBoto: El text que hi ha en el boto que s'ha accionat.
     */
    @Override
    protected void botoAccionat(String textBoto) {
        if (textBoto.equals(textBotoCataleg)) {
            ctrlVistaCatalegAmbRestriccions.executar(this);
        }
        else if (textBoto.equals(textBotoSolucions)) {
            ctrlVistaSolucions.executar(this);
        }
        else if (textBoto.equals(textBotoImportarCataleg)) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXT Files", "txt");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(filter);
            chooser.setDialogTitle(titolJFileDialogImportarCataleg);
            int returnVal = chooser.showOpenDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length() - nomFitxer.length());
                try {
                    ctrlVistaCatalegAmbRestriccions.importar(pathToFile, nomFitxer);
                    JOptionPane.showMessageDialog(
                            frameVista,
                            String.format(templateTextImportarOK, nomFitxer),
                            titolImportarOK,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            e.getMessage(),
                            titolImportarKO,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(frameVista,
                        textImportarKO,
                        titolImportarKO,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (textBoto.equals(textBotoImportarSolucions)) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXT Files", "txt");
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(filter);
            chooser.setDialogTitle(titolJFileDialogImportarSolucions);
            int returnVal = chooser.showOpenDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length() - nomFitxer.length());
                try {
                    ctrlVistaSolucions.importar(pathToFile, nomFitxer);
                    JOptionPane.showMessageDialog(
                            frameVista,
                            String.format(templateTextImportarOK, nomFitxer),
                            titolImportarOK,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            e.getMessage(),
                            titolImportarKO,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(frameVista,
                        textImportarKO,
                        titolImportarKO,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (textBoto.equals(textBotoExportarCataleg)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle(titolJFileDialogExportarCataleg);
            int returnVal = chooser.showSaveDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length() - nomFitxer.length());
                try {
                    ctrlVistaCatalegAmbRestriccions.exportar(pathToFile, nomFitxer);
                    JOptionPane.showMessageDialog(
                            frameVista,
                            String.format(templateTextExportarOK, nomFitxer),
                            titolExportarOK,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            e.getMessage(),
                            titolExportarKO,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(frameVista,
                        textExportarKO,
                        titolExportarKO,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (textBoto.equals(textBotoExportarSolucions)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle(titolJFileDialogExportarSolucions);
            int returnVal = chooser.showSaveDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length() - nomFitxer.length());
                try {
                    ctrlVistaSolucions.exportar(pathToFile, nomFitxer);
                    JOptionPane.showMessageDialog(
                            frameVista,
                            String.format(templateTextExportarOK, nomFitxer),
                            titolExportarOK,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            e.getMessage(),
                            titolExportarKO,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(frameVista,
                        textExportarKO,
                        titolExportarKO,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (textBoto.equals(textBotoSortir)) {
            sortir();
        }
        else {
            super.botoAccionat(textBoto);
        }
    }

    /**
     * Metode itemAccionat executat cada vegada que s'acciona un item de la barra de menu de la vista.
     * L'item de la vista que s'ha accionat es diferencia pel text que hi ha en aquest.
     * @param textItem: Text que hi ha en l'item que s'ha accionat.
     */
    @Override
    protected void itemAccionat(String textItem) {
        super.itemAccionat(textItem);
    }
}
