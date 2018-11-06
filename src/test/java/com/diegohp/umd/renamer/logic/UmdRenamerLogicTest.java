/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diegohp.umd.renamer.logic;


import com.diegohp.umd.data.Umd;
import com.diegohp.umd.data.UmdDAO;
import com.diegohp.umd.filerenamer.logic.UmdRenamerLogic;
import java.io.File;
import java.io.IOException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author diegohp
 */
public class UmdRenamerLogicTest extends TestCase {
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UmdRenamerLogicTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(UmdRenamerLogicTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testUmdRenamerLogic() throws IOException {
        
        String cubeFileFolder = "target/test-classes/com/diegohp/umd/filerenamer/logic/";
        String cubeFileName = "cube.cso";
        String cubeFilePath = cubeFileFolder + cubeFileName;
        System.out.println("Reading: " + cubeFilePath);
        File file = new File(cubeFilePath);
        assertTrue(file.exists());
        
        UmdRenamerLogic umdRenamerLogic = new UmdRenamerLogic();
        UmdDAO umdDAO = new UmdDAO();
        umdRenamerLogic.setUmdDAO(umdDAO);
        
        Umd umd = umdRenamerLogic.getUmd(file);
        assertNotNull(umd);
        
        assertTrue(umd.getTitle().equals("Cube sample"));
        System.out.println("Title: " + umd.getTitle());
        
        assertTrue(umd.getId().equals("UCJS10041"));
        System.out.println("ID: " + umd.getId());
        
        assertTrue(umd.getVersion().equals("1.00"));
        System.out.println("Version: " + umd.getVersion());
        
        assertTrue(umd.getFirmware().equals("1.50"));
        System.out.println("Firmware: " + umd.getFirmware());
        
        String formattedName = umdRenamerLogic.getFormattedName(umd);
        
        assertTrue(formattedName.equals("Cube sample (UCJS-10041)"));
        System.out.println("Formatted name: " + formattedName);
        
        umdRenamerLogic.rename(umd, cubeFileFolder, formattedName);
        assertFalse((new File(cubeFilePath)).exists());
        assertTrue(umd.getFile().exists());
        System.out.println("New file: " + umd.getFile().getPath());
        
    }
    
    
    
}
