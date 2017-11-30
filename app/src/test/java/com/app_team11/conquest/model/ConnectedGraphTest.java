package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Nigel on 25-Nov-17.
 * Check for connected graph after adding continent
 */

public class ConnectedGraphTest {

    private String filePath;
    private GameMap map;
    private Territory Terr1c,TerrConnect=null;
    Continent continent;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\3D.map";
        map=new GameMap();
        continent=new Continent();
        continent.setContName("Test Continent");
        continent.setScore(5);
        TerrConnect=new Territory();
    }
    @Test
    public void addUnconnectedContinentTest() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        map=readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        //Connected Graph, therefore assertion should be true.
        assertTrue(map.isGraphConnected());
        for(Territory terr : map.getTerritoryList())
        {
            if(terr.getTerritoryName().equalsIgnoreCase("1c"))
                Terr1c=terr;

        }
        map.addRemoveContinentFromMap(continent,'A');

        TerrConnect.setTerritoryName("Test territory");
        TerrConnect.setContinent(continent);
        TerrConnect.addRemoveNeighbourToTerr(Terr1c,'A');
        //Since continent is now connected, assertion should be true as well
        assertTrue(map.isGraphConnected());


    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}
