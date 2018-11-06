/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diegohp.umd.data;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import jpcsp.filesystems.umdiso.UmdIsoFile;
import jpcsp.filesystems.umdiso.UmdIsoReader;
import jpcsp.format.PSF;

/**
 *
 * @author diegohp
 */
public class UmdDAO {
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UmdDAO.class);
    
    public UmdDAO(){}

    public Umd getUmd(File file) throws IOException {

        try {

            if (!file.isDirectory()) {

                Umd umd = new Umd();
                PSF psf = new PSF();

                logger.info("Reading file: " + file.getName());
                UmdIsoReader iso = new UmdIsoReader(file.getPath());

                UmdIsoFile paramSfo = iso.getFile("PSP_GAME/param.sfo");
                byte[] sfo = new byte[(int) paramSfo.length()];
                paramSfo.read(sfo);
                paramSfo.close();
                ByteBuffer buf = ByteBuffer.wrap(sfo);
                psf.read(buf);
                
                byte[] icon0 = null;
                try{
                    UmdIsoFile icon0umd = iso.getFile("PSP_GAME/ICON0.PNG");
                    icon0 = new byte[(int) icon0umd.length()];
                    icon0umd.read(icon0);
                    icon0umd.close();
                }
                catch(FileNotFoundException e){
                    logger.warn(e.getMessage());
                    //assign the default icon
                }

                String title = psf.getString("TITLE");
                String id = psf.getString("DISC_ID");
                String version = psf.getString("DISC_VERSION");
                String firmware = psf.getString("PSP_SYSTEM_VER");
                
                umd.setId(id);
                umd.setTitle(title);
                umd.setVersion(version);
                umd.setFirmware(firmware);
                umd.setIcon0(icon0);
                umd.setFile(file);

                return umd;

            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
            // default icon
            //icons[rowIndex] = new ImageIcon(getClass().getResource("/jpcsp/images/icon0.png"));
        } catch (IOException e) {
            throw e;
        }

        return null;

    }
}
