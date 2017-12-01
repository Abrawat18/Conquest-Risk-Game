package com.app_team11.conquest.model;

import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nigel on 19-Oct-17.
 * Check for connected graph
 */

public class GraphTest {
    private String filePath;
    private boolean failed;
    GameMap gameMap;

    /**
     * Initalizes variables for the test
     */
    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\3D.map";
        failed=false;
        gameMap=new GameMap();
    }

    /**
     * test for connected graph test
     */
    @Test
    public void connectedGraphTest()
    {
        ReadMapUtility readTest=new ReadMapUtility();

        System.out.println(System.getProperty("user.dir") + File.separator + filePath);

        gameMap = readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);

        assertTrue(gameMap.isGraphConnected());

    }
    /**
     * Clean up the test data
     */
    @After
    public void clean()
    {
        filePath=null;
    }

}
