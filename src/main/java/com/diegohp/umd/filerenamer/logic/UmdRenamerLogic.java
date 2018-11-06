/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diegohp.umd.filerenamer.logic;

import com.diegohp.umd.data.Umd;
import com.diegohp.umd.data.UmdDAO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diegohp
 */
public class UmdRenamerLogic {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UmdRenamerLogic.class);
    private UmdDAO umdDAO;

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public Umd getUmd(File file) throws IOException {
        if (file != null && !file.isDirectory()) {
            return this.umdDAO.getUmd(file);
        }
        return null;
    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     */
    public List<Umd> getUmdList(File path) throws IOException {

        File[] files = path.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                String lower = file.getName().toLowerCase();
                if (lower.endsWith(".cso") || lower.endsWith(".iso")) {
                    return true;
                }
                return false;
            }
        });

        List<Umd> list = new ArrayList<Umd>();

        for (File f : files) {
            Umd umd = this.getUmd(f);
            if (umd != null) {
                list.add(umd);
            }
        }

        return list;

    }

    public String getFormattedName(Umd umd) {
        String name = umd.getTitle() + " (" + umd.getId().substring(0, 4) + "-" + umd.getId().substring(4) + ")";
        String invalidChars = "\\/:*?\"<>|™";
        for(Character c : invalidChars.toCharArray()){
            name = name.replace(c.toString(), "");
        }
        return name;
    }

    public String getFormattedName(Umd umd, String format) {
        return "";
    }

    public void rename(Umd umd, String folder, String newFileName) {
        File newFile = new File(folder + File.separator + newFileName + umd.getExtension());
        umd.getFile().renameTo(newFile);
        umd.setFile(newFile);
    }

    private void copyFile(String inFile, String outFile) {
        try {
            File f1 = new File(inFile);
            File f2 = new File(outFile);
            InputStream in = new FileInputStream(f1);

            //For Append the file.
            //  OutputStream out = new FileOutputStream(f2,true);

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param umdDAO the umdDAO to set
     */
    public void setUmdDAO(UmdDAO umdDAO) {
        this.umdDAO = umdDAO;
    }
}
