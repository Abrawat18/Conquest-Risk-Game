package com.app_team11.conquest.model;

import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by Nigel on 19-Oct-17.
 * Checks whether the graph formed is connected
 */

public class UnconnectedGraphTest {

    private String filePath;
    private boolean failed;
    GameMap map;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\Invalid test file.map";
        failed=false;
        map=new GameMap();
    }

    /**
     * Test to check for unconnected territory
     * @throws Exception in case of file input/output error
     */
    @Test
    public void unconnectedGraphTest() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        map=readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        assertFalse(map.isGraphConnected());

    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}
