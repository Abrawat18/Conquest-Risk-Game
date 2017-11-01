package com.app_team11.conquest.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nigel on 19-Oct-17.
 */

public class InvalidMapTest {

    private String filePath;
    private boolean failed;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\Invalid test file.map";
        failed=false;
    }
    @Test
    public void invalidMapTest() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        assertEquals(null,readTest.readFile(System.getProperty("user.dir") + File.separator + filePath));

    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}
