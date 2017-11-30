package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Check for unconnected continent
 * Created by Nigel on 25-Nov-17.
 */

public class UnconnectedContinentTest {

    private String filePath;
    private boolean failed;
    private GameMap map;
    //Territories T1 and T4 are in the UnconnectedContinent.map. Test method first asserts
    //connected graph when they're disconnected and after they're connected.
    private Territory T1,T4=null;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\UnconnectedContinent.map";
        failed=false;
        map=new GameMap();
    }

    /**
     * Test to check for unconnected continent
     * @throws Exception in case file read error
     */
    @Test
    public void unconnectedContinentTest() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        map=readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        //T1 and T4 are disconnected in same continent hence map not connected and assertion false
        for(Territory terr : map.getTerritoryList())
        {
            System.out.println("Territory: "+terr.getTerritoryName());
            for(Territory neighbourTerr :  terr.getNeighbourList())
            {
                System.out.println("Territory Neighbour: " + neighbourTerr.getTerritoryName());
            }
        }
        assertFalse(map.isGraphConnected());
        for(Territory terr : map.getTerritoryList())
        {
            if(terr.getTerritoryName().equalsIgnoreCase("T4"))
                T1=terr;
            else if(terr.getTerritoryName().equalsIgnoreCase("T1"))
                T4=terr;
        }
        T4.addRemoveNeighbourToTerr(T1,'A');
        for(Territory terr : map.getTerritoryList())
        {
            System.out.println("Territory: "+terr.getTerritoryName());
            for(Territory neighbourTerr :  terr.getNeighbourList())
            {
                System.out.println("Territory Neighbour: " + neighbourTerr.getTerritoryName());
            }
        }
        //If connection made from T4 to T1 then map should be connected i.e. assertion should be true
        assertTrue(map.isGraphConnected());

        //added a new continent with one territory on the map without connections to other territories.
        Continent c=new Continent();
        c.setContName("TestC");
        c.setScore(50);

        Territory terr=new Territory();
        terr.setContinent(c);
        terr.setArmyCount(50);

        map.getTerritoryList().add(terr);
        map.getContinentList().add(c);
        //Hence, the map should not be connected i.e. assertion should be false
        assertFalse(map.isGraphConnected());

    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}
