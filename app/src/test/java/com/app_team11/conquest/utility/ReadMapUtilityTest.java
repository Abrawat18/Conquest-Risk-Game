package com.app_team11.conquest.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by RADHEY on 10/19/2017.
 */
public class ReadMapUtilityTest {
    String filePath;
    boolean failed=false;
   @Before
   public void setUp() {
       filePath="C:\\Users\\RADHEY\\Desktop\\as.map";

   }
    @Test
    public void readFile() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        assertEquals(null,readTest.readFile(filePath));

    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}