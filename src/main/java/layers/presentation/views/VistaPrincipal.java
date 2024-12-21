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

    protected String textBotoExportar = "Exportar";
    protected Boto botoExportar;

    protected String textBotoImportar = "Importar";
    protected Boto botoImportar;

    protected String textBotoSortir = "Sortir";
    protected Boto botoSortir;

    protected String textJFileDialogImportarCataleg = "Obrir el fitxer del cataleg";
    protected String textJFileDialogImportarSolucions = "Obrir el fitxer de les solucions";
    protected String textJFileDialogExportarCataleg = "Guardar el fitxer del cataleg";
    protected String textJFileDialogExportarSolucions = "Guardar el fitxer de les solucions";

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
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Importar
        botoImportar = new Boto(textBotoImportar);
        botoImportar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoImportar);
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Inicialitzem el boto Importar
        botoExportar = new Boto(textBotoExportar);
        botoExportar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(botoExportar);
        add(Box.createRigidArea(new Dimension(0, 10)));

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
        else if (textBoto.equals(textBotoImportar)) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "TXT Files", "txt");

            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(filter);
            chooser.setDialogTitle(textJFileDialogImportarCataleg);
            int returnVal = chooser.showOpenDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length()-nomFitxer.length());
                try {
                    ctrlVistaCatalegAmbRestriccions.importar(pathToFile, nomFitxer);
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            "El format del fitxer " + nomFitxer + " es invalid",
                            "Format incorrecte",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            chooser.setDialogTitle(textJFileDialogImportarSolucions);
            returnVal = chooser.showOpenDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length()-nomFitxer.length());
                try {
                    ctrlVistaSolucions.importar(pathToFile, nomFitxer);
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            "El format del fitxer " + nomFitxer + " es invalid",
                            "Error al importar",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        else if (textBoto.equals(textBotoExportar)) {
            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle(textJFileDialogExportarCataleg);
            int returnVal = chooser.showSaveDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length()-nomFitxer.length());
                try {
                    ctrlVistaCatalegAmbRestriccions.exportar(pathToFile, nomFitxer);
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            "No es pot exportar a fitxer. Comprova que el path " + path + " sigui valid",
                            "Error a l'exportar",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            chooser.setDialogTitle(textJFileDialogExportarSolucions);
            returnVal = chooser.showSaveDialog(frameVista);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String nomFitxer = chooser.getSelectedFile().getName();
                String path = chooser.getSelectedFile().getPath();
                String pathToFile = path.substring(0, path.length()-nomFitxer.length());
                try {
                    ctrlVistaSolucions.exportar(pathToFile, nomFitxer);
                } catch (FormatInputNoValid e) {
                    JOptionPane.showMessageDialog(frameVista,
                            "No es pot exportar a fitxer. Comprova que el path " + path + " sigui valid",
                            "Error a l'exportar",
                            JOptionPane.WARNING_MESSAGE);
                }
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
