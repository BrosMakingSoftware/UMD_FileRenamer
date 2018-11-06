package com.diegohp.umd.filerenamer;

import com.diegohp.umd.renamer.ui.LanguageSelectorJDialog;
import com.diegohp.umd.renamer.ui.RenamerJFrame;
import javax.swing.UIManager;

/**
 * Hello world!
 *
 */
public class App {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting UMD_FileRenamer");

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
                LanguageSelectorJDialog languageSelectorJFrame = new LanguageSelectorJDialog(new javax.swing.JFrame(), true);
                languageSelectorJFrame.setVisible(true);
                
                RenamerJFrame renamerJFrame = new RenamerJFrame();
                renamerJFrame.setVisible(true);
                
                renamerJFrame.askStartDirectory();
            }
        });
    }
}
