package com.app_team11.conquest.utility;

import org.junit.After;
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
    /**
     * Initializes variables for the test
     */
    @Before
    public void setup()
    {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\Invalid test file.map";
        fileManager=FileManager.getInstance();
    }

    /**
     * test for file methods
     */
    @Test
    public void validatefileMethods()
    {
        try {
            assertNotNull(fileManager.createWriter("Test"));
            File f = new File(System.getProperty("user.dir") + File.separator + filePath);
            assertNull(fileManager.getAllFileFromDir(f));
        }
        catch(Exception e)
        {

        }
    }

    @After
    public void cleanup()
    {
        fileManager=null;
        filePath=null;
    }

}
