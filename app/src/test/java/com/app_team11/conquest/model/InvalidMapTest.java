package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Nigel on 27-Nov-17.
 * Check for invalid map file
 */

public class InvalidMapTest
{
    private String filePath;
    private GameMap map;
    Boolean failed=false;
    Continent continent;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\InvalidMapFile.map";
        map=new GameMap();

        continent=new Continent();
        continent.setContName("Test Continent");
        continent.setScore(5);
    }

    /**
     * test for unconnected continent test
     * @throws Exception
     */
    @Test
    public void addUnconnectedContinentTest() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        map=readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        //Connected Graph, therefore assertion should be true.
        try{
            map.isGraphConnected();
        }
        catch(Exception e)
        {
            failed=true;
        }
        //since invalid map hence method fails
        assertTrue(failed);
    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}
