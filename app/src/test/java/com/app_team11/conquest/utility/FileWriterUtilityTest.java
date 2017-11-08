package com.app_team11.conquest.utility;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by Nigel on 07-Nov-17.
 * Validate file methods
 */

public class FileWriterUtilityTest {
    FileManager fileManager;
    String filePath;
    @Before
    public void setup()
    {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\Invalid test file.map";
        fileManager=FileManager.getInstance();
    }
    @Test
    public void validatefileMethods()
    {
        assertNotNull(fileManager.createWriter("Test"));
        File f=new File(System.getProperty("user.dir") + File.separator + filePath);
        assertNull(fileManager.getAllFileFromDir(f));
    }
}