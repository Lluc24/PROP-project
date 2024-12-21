package layers.presentation.views;

import layers.domain.excepcions.FormatInputNoValid;
import layers.presentation.controllers.CtrlVistaGeneric;
import layers.presentation.controllers.CtrlVistaCatalegAmbRestriccions;
import layers.presentation.controllers.CtrlVistaSolucions;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends VistaGenerica {
    private CtrlVistaCatalegAmbRestriccions ctrlVistaCatalegAmbRestriccions;
    private CtrlVistaSolucions ctrlVistaSolucions;

    protected String textEtiquetaTriar = "Quina funcionalitat vols realitzar?";
    protected JLabel etiquetaTriar;

    protected String textBotoCataleg = "Gestionar cataleg";
    protected Boto botoCataleg;

    protected String textBotoSolucions = "Gestionar solucions";
    protected Boto botoSolucions;

    protected String textBotoExportarCataleg = "Exportar cataleg";
    protected Boto botoExportarCataleg;

    protected String textBotoExportarSolucions = "Exportar solucions";
    protected Boto botoExportarSolucions;

    protected String textBotoImportarCataleg = "Importar cataleg";
    protected Boto botoImportarCataleg;

    protected String textBotoImportarSolucions = "Importar solucions";
    protected Boto botoImportarSolucions;

    protected String textBotoSortir = "Sortir";
    protected Boto botoSortir;

//    protected String titolGuiaImportarCataleg = "Guia per importar cataleg";
//    protected String textGuiaImportarCataleg = "A continuacio s'obrira un JFileChooser per\n" +
//            "escollir el fitxer del cataleg";
//    protected String titolGuiaImportarSolucions = "Guia per importar solucions";
//    protected String textGuiaImportarSolucions = "A continuacio s'obrira un JFileChooser per\n" +
//            "escollir el fitxer de les solucions";
    protected String titolImportarOK = "Importacio correcta";
    protected String templateTextImportarOK = "El fitxer %s s'ha importat correctament.\n" +
            "Les estructures de dades s'han actualitzat";
    protected String titolImportarKO = "Error d'importacio";
    protected String textImportarKO = "No s'ha pogut importar el fitxer";

//    protected String titolGuiaExportarCataleg = "Guia per exportar cataleg";
//    protected String textGuiaExportarCataleg = "A continuacio s'obrira un primer JFileChooser per\n" +
//            "guardar el fitxer de cataleg";
//    protected String titolGuiaExportarSolucions = "Guia per exportar solucions";
//    protected String textGuiaExportarSolucions = "A continuacio s'obrira un segon JFileChooser per\n" +
//            "guardar el fitxer de les solucions";
    protected String titolExportarOK = "Exportacio correcta";
    protected String templateTextExportarOK = "El fitxer %s s'ha exportat correctament";
    protected String titolExportarKO = "Error d'exportacio";
    protected String textExportarKO = "No s'ha pogut exportar el fitxer";


    protected String titolJFileDialogImportarCataleg = "Obrir el fitxer del cataleg";
    protected String titolJFileDialogImportarSolucions = "Obrir el fitxer de les solucions";
    protected String titolJFileDialogExportarCataleg = "Guardar el fitxer del cataleg";
    protected String titolJFileDialogExportarSolucions = "Guardar el fitxer de les solucions";

    public VistaPrincipal(CtrlVistaGeneric ctrl1, CtrlVistaGeneric ctrl2) {
        ctrlVistaCatalegAmbRestriccions = (CtrlVistaCatalegAmbRestriccions) ctrl1;
        ctrlVistaSolucions = (CtrlVistaSolucions) ctrl2;
    }

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

    @Override
    protected void sortirSistema() {
        ctrlVistaSolucions.sortirSistema();
    }

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

    @Override
    protected void itemAccionat(String textItem) {
        super.itemAccionat(textItem);
    }
}
