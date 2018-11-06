
package com.diegohp.umd.renamer.ui;

import com.diegohp.swing.ListTableModel;
import com.diegohp.swing.SwingHelper;
import com.diegohp.umd.data.Umd;
import com.diegohp.umd.data.UmdDAO;
import com.diegohp.umd.filerenamer.logic.UmdRenamerLogic;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author diegohp
 */
public class RenamerJFrame extends javax.swing.JFrame {

    /**
     * Creates new form RenamerJFrame
     */
    public RenamerJFrame() {
        
        this.umdRenamerLogic = new UmdRenamerLogic();
        this.umdRenamerLogic.setUmdDAO(new UmdDAO());
        this.umdListTableModel = new ListTableModel<Umd>() {

            @Override
            public Object getValueAt(int row, int col) {
                Umd umd = this.objects.get(row);
                if (col == 0) {
                    return umd.getId();
                }
                if (col == 1) {
                    return umd.getTitle();
                }
                if (col == 2) {
                    return umd.getVersion();
                }
                if (col == 3) {
                    return umd.getFirmware();
                }
                if (col == 4) {
                    return umd.getFile().getName();
                }
                return umdRenamerLogic.getFormattedName(umd) + umd.getExtension();
            }
        };

        List<String> columnsNames = new ArrayList<String>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/diegohp/umd/renamer/ui/resources");
        columnsNames.add(resourceBundle.getString("id"));
        columnsNames.add(resourceBundle.getString("title"));
        columnsNames.add(resourceBundle.getString("version"));
        columnsNames.add(resourceBundle.getString("firmware"));
        columnsNames.add(resourceBundle.getString("old_filename"));
        columnsNames.add(resourceBundle.getString("new_filename"));
        this.umdListTableModel.setColumnNames(columnsNames);

        this.initComponents();
        
        this.jTablePSPGames.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(!lse.getValueIsAdjusting()){
                    ListSelectionModel model = jTablePSPGames.getSelectionModel();
                    if(model.getLeadSelectionIndex() >= 0){
                        umdSelected = ((ListTableModel<Umd>)jTablePSPGames.getModel()).getObjectAt(model.getLeadSelectionIndex());
                        loadSelectedUmd();
                        jButtonUseTitleChanges.setEnabled(true);
                        logger.info("Selected PSP game from table with ID = " + umdSelected.getId());
                    }
                }
            }
        });
    }
    
    private void loadSelectedUmd(){
        if(this.umdSelected != null){
            logger.info("Loading PSP game with ID = " + this.umdSelected.getId());
            this.jTextFieldId.setText(this.umdSelected.getId());
            this.jTextFieldTitle.setText(this.umdSelected.getTitle());
            this.jTextFieldVersion.setText(this.umdSelected.getVersion());
            this.jTextFieldFirmware.setText(this.umdSelected.getFirmware());
            if(this.umdSelected.getIcon0() != null){
                this.jLabelIcon0.setIcon(new ImageIcon(this.umdSelected.getIcon0()));
            }
            else{
                logger.warn("No Icon0 found. Setting the default one to be displayed");
                this.jLabelIcon0.setIcon(new ImageIcon(getClass().getResource("/com/diegohp/umd/renamer/ui/icon0.png")));
            }
        }
        else{
            logger.info("No PSP game selected. Cleaning properties");
            this.jTextFieldId.setText("");
            this.jTextFieldTitle.setText("");
            this.jTextFieldVersion.setText("");
            this.jTextFieldFirmware.setText("");
            this.jLabelIcon0.setIcon(new ImageIcon(getClass().getResource("/com/diegohp/umd/renamer/ui/icon0.png")));
        }
    }

    public void askStartDirectory() {
        
        logger.info("Asking for directory were games files are located");
        
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/diegohp/umd/renamer/ui/resources");

        File directoryTemp = SwingHelper.chooseDirectory(resourceBundle.getString("select_folder_containing_images"), this.startDirectory);

        if (directoryTemp != null) {
            this.startDirectory = directoryTemp;
            this.umdListTableModel.removeAllObjects();
            this.umdSelected = null;
            this.loadSelectedUmd();
            this.jButtonRenameAll.setEnabled(false);
            this.jButtonUseTitleChanges.setEnabled(false);
            logger.info("Opening: " + this.startDirectory);
            this.jLabelActualFolder.setText(this.startDirectory.getPath());
            
            String[] fileNames = this.startDirectory.list();

            List<Umd> umdList = new ArrayList<Umd>();

            for (String fileName : fileNames) {
                if (fileName.endsWith(".iso") || fileName.endsWith(".cso")) {
                    File file = new File(this.startDirectory + "/" + fileName);
                    if (file.isFile() && !file.isHidden()) {
                        try {
                            Umd umd = umdRenamerLogic.getUmd(file);
                            umdList.add(umd);
                            logger.info("Added game from file \"" + file.getName() + "\" with ID = " + umd.getId());
                        } catch (IOException ex) {
                            logger.error("Error opening files" + file, ex);
                            SwingHelper.showErrorMessage(resourceBundle.getString("error_opening_file") + " " + file, ex.toString());
                        }
                    }
                }
            }

            if (umdList.isEmpty()) {
                logger.info("Folder does not contain PSP image files");
                SwingHelper.showWarningMessage(resourceBundle.getString("warning"), resourceBundle.getString("folder_does_not_contain_psp_images"));
            } else {
                this.umdListTableModel.addObjectList(umdList);
                this.jButtonRenameAll.setEnabled(true);
                logger.info("Added " + umdList.size() + " game(s) to the list. Data is ready to renaming process");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelInformation = new javax.swing.JPanel();
        jLabelId = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jLabelTitle = new javax.swing.JLabel();
        jTextFieldTitle = new javax.swing.JTextField();
        jLabelVersion = new javax.swing.JLabel();
        jTextFieldVersion = new javax.swing.JTextField();
        jLabelFirmware = new javax.swing.JLabel();
        jTextFieldFirmware = new javax.swing.JTextField();
        jButtonUseTitleChanges = new javax.swing.JButton();
        jPanelIcon = new javax.swing.JPanel();
        jLabelIcon0 = new javax.swing.JLabel();
        jPanelTableOfGames = new javax.swing.JPanel();
        jLabelActualFolderLabel = new javax.swing.JLabel();
        jLabelActualFolder = new javax.swing.JLabel();
        jButtonChangeFolder = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePSPGames = new javax.swing.JTable();
        jButtonRenameAll = new javax.swing.JButton();
        jLabelNote = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UMD File Renamer");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/diegohp/umd/renamer/ui/resources"); // NOI18N
        jPanelInformation.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("psp_game_information"))); // NOI18N

        jLabelId.setLabelFor(jTextFieldId);
        jLabelId.setText(bundle.getString("id") + ":"); // NOI18N

        jTextFieldId.setEditable(false);

        jLabelTitle.setLabelFor(jTextFieldTitle);
        jLabelTitle.setText(bundle.getString("title") + ":"); // NOI18N

        jLabelVersion.setLabelFor(jTextFieldVersion);
        jLabelVersion.setText(bundle.getString("version") + ":"); // NOI18N

        jTextFieldVersion.setEditable(false);

        jLabelFirmware.setLabelFor(jTextFieldFirmware);
        jLabelFirmware.setText(bundle.getString("firmware") + ":"); // NOI18N

        jTextFieldFirmware.setEditable(false);

        jButtonUseTitleChanges.setText(bundle.getString("use_title_changes")); // NOI18N
        jButtonUseTitleChanges.setEnabled(false);
        jButtonUseTitleChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUseTitleChangesActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelInformationLayout = new org.jdesktop.layout.GroupLayout(jPanelInformation);
        jPanelInformation.setLayout(jPanelInformationLayout);
        jPanelInformationLayout.setHorizontalGroup(
            jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelInformationLayout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelTitle)
                    .add(jLabelFirmware)
                    .add(jLabelId)
                    .add(jLabelVersion))
                .add(18, 18, 18)
                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelInformationLayout.createSequentialGroup()
                        .add(jTextFieldId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jPanelInformationLayout.createSequentialGroup()
                        .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelInformationLayout.createSequentialGroup()
                                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(jTextFieldFirmware, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .add(jTextFieldVersion))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButtonUseTitleChanges))
                            .add(jTextFieldTitle))
                        .addContainerGap())))
        );
        jPanelInformationLayout.setVerticalGroup(
            jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelInformationLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelId))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelTitle)
                    .add(jTextFieldTitle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelVersion)
                    .add(jTextFieldVersion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelInformationLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldFirmware, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelFirmware)
                    .add(jButtonUseTitleChanges))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelIcon.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("psp_game_icon"))); // NOI18N

        jLabelIcon0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/diegohp/umd/renamer/ui/icon0.png"))); // NOI18N
        jLabelIcon0.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        org.jdesktop.layout.GroupLayout jPanelIconLayout = new org.jdesktop.layout.GroupLayout(jPanelIcon);
        jPanelIcon.setLayout(jPanelIconLayout);
        jPanelIconLayout.setHorizontalGroup(
            jPanelIconLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelIconLayout.createSequentialGroup()
                .add(42, 42, 42)
                .add(jLabelIcon0)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanelIconLayout.setVerticalGroup(
            jPanelIconLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelIconLayout.createSequentialGroup()
                .add(26, 26, 26)
                .add(jLabelIcon0)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelTableOfGames.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("list_of_psp_games"))); // NOI18N

        jLabelActualFolderLabel.setLabelFor(jLabelActualFolder);
        jLabelActualFolderLabel.setText(bundle.getString("actual_folder") + ":"); // NOI18N

        jLabelActualFolder.setText(bundle.getString("no_folder_selected")); // NOI18N

        jButtonChangeFolder.setText(bundle.getString("change_folder")); // NOI18N
        jButtonChangeFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangeFolderActionPerformed(evt);
            }
        });

        jTablePSPGames.setModel(this.umdListTableModel);
        jTablePSPGames.setToolTipText(bundle.getString("app_do_not_change_game_properties")); // NOI18N
        jScrollPane1.setViewportView(jTablePSPGames);

        jButtonRenameAll.setText(bundle.getString("rename_all")); // NOI18N
        jButtonRenameAll.setEnabled(false);
        jButtonRenameAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRenameAllActionPerformed(evt);
            }
        });

        jLabelNote.setText(bundle.getString("app_do_not_change_game_properties")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanelTableOfGamesLayout = new org.jdesktop.layout.GroupLayout(jPanelTableOfGames);
        jPanelTableOfGames.setLayout(jPanelTableOfGamesLayout);
        jPanelTableOfGamesLayout.setHorizontalGroup(
            jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTableOfGamesLayout.createSequentialGroup()
                .add(jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelTableOfGamesLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1))
                    .add(jPanelTableOfGamesLayout.createSequentialGroup()
                        .add(jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanelTableOfGamesLayout.createSequentialGroup()
                                .add(jButtonChangeFolder)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonRenameAll))
                            .add(jPanelTableOfGamesLayout.createSequentialGroup()
                                .addContainerGap()
                                .add(jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanelTableOfGamesLayout.createSequentialGroup()
                                        .add(jLabelActualFolderLabel)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jLabelActualFolder))
                                    .add(jLabelNote))))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTableOfGamesLayout.setVerticalGroup(
            jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTableOfGamesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabelActualFolderLabel)
                    .add(jLabelActualFolder))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanelTableOfGamesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonChangeFolder)
                    .add(jButtonRenameAll))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 132, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabelNote)
                .add(54, 54, 54))
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText(bundle.getString("file")); // NOI18N

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText(bundle.getString("exit")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText(bundle.getString("help")); // NOI18N

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText(bundle.getString("about")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanelIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelInformation, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanelTableOfGames, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTableOfGames, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 263, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanelInformation, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanelIcon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(0, 13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        logger.info("Exiting from Application");
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jButtonChangeFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangeFolderActionPerformed
        this.askStartDirectory();
    }//GEN-LAST:event_jButtonChangeFolderActionPerformed

    private void jButtonUseTitleChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUseTitleChangesActionPerformed
        logger.info("Applying changes to the Tile of the game with ID = " + this.umdSelected.getId());
        String updatedName = this.jTextFieldTitle.getText();
        String invalidChars = "\\/:*?\"<>|™";
        boolean charReplaced = false;
        for(Character c : invalidChars.toCharArray()){
            if(updatedName.contains(c.toString())){
                updatedName = updatedName.replace(c.toString(), "");
                charReplaced = true;
            }
        }
        if(charReplaced){
            logger.warn("Some characters were replaced to avoid problems with the file system");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("com/diegohp/umd/renamer/ui/resources");
            SwingHelper.showInformationMessage(resourceBundle.getString("chars_replaced_title"), resourceBundle.getString("chars_replaced"));
        }
        this.umdSelected.setTitle(updatedName);
        this.jTextFieldTitle.setText(updatedName);
        this.umdListTableModel.fireTableDataChanged();
    }//GEN-LAST:event_jButtonUseTitleChangesActionPerformed

    private void jButtonRenameAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRenameAllActionPerformed
        logger.info("Renaming all file games...");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com/diegohp/umd/renamer/ui/resources");
        try{
            for(Umd umd : this.umdListTableModel.getObjects()){
                String formattedName = this.umdRenamerLogic.getFormattedName(umd);
                this.umdRenamerLogic.rename(umd, this.startDirectory.getPath(), formattedName);
            }
            logger.info("All file renamed!");
            this.umdListTableModel.fireTableDataChanged();            
            SwingHelper.showInformationMessage(resourceBundle.getString("files_rename_sucess_title"), resourceBundle.getString("files_rename_sucess"));
        }
        catch(Throwable t){
            logger.error("Error during renaming files.", t);
            SwingHelper.showInformationMessage(resourceBundle.getString("error_renaming_files"), t.toString());
        }
    }//GEN-LAST:event_jButtonRenameAllActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        AboutJDialog dialog = new AboutJDialog(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RenamerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RenamerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RenamerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RenamerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new RenamerJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButtonChangeFolder;
    private javax.swing.JButton jButtonRenameAll;
    private javax.swing.JButton jButtonUseTitleChanges;
    private javax.swing.JLabel jLabelActualFolder;
    private javax.swing.JLabel jLabelActualFolderLabel;
    private javax.swing.JLabel jLabelFirmware;
    private javax.swing.JLabel jLabelIcon0;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelNote;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JPanel jPanelIcon;
    private javax.swing.JPanel jPanelInformation;
    private javax.swing.JPanel jPanelTableOfGames;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePSPGames;
    private javax.swing.JTextField jTextFieldFirmware;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldTitle;
    private javax.swing.JTextField jTextFieldVersion;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RenamerJFrame.class);
    private UmdRenamerLogic umdRenamerLogic;
    private ListTableModel<Umd> umdListTableModel;
    private Umd umdSelected;
    private File startDirectory;
}
