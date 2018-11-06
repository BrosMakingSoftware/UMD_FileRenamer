/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diegohp.umd.data;

import java.io.File;

/**
 *
 * @author diegohp
 */
public class Umd {
    
    private String id;
    private String title;
    private String version;
    private String firmware;
    private byte[] icon0;
    private File file;
    
    
    public Umd(){
        this.id = "";
        this.title = "";
        this.version = "";
        this.firmware = "";
        this.icon0 = null;
        this.file = null;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the firmware
     */
    public String getFirmware() {
        return firmware;
    }

    /**
     * @param firmware the firmware to set
     */
    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    /**
     * @return the icon0
     */
    public byte[] getIcon0() {
        return icon0;
    }

    /**
     * @param icon0 the icon0 to set
     */
    public void setIcon0(byte[] icon0) {
        this.icon0 = icon0;
    }

    /**
     * @return the fileName
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @param file the fileName to set
     */
    public void setFile(File file) {
        this.file = file;
    }
    
    public String getExtension(){
        String extension = null;
        if(this.getFile().getPath().endsWith(".iso")){
            extension = ".iso";
        }
        else if(this.getFile().getPath().endsWith(".cso")){
            extension = ".cso";
        }
        return extension;
    }
    
    
}
