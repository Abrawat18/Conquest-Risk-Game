package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Check for unconnected graph
 * Created by Nigel on 25-Nov-17.
 */

public class GameMapTest10 {

    private String filePath;
    private boolean failed;
    private GameMap map;
    private Territory T1,T4=null;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\UnconnectedContinent.map";
        failed=false;
        map=new GameMap();
    }
    @Test
    public void invalidMapTest() throws Exception
    {
        ReadMapUtility readTest=new ReadMapUtility();
        map=readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        //T1 and T4 are disconnected in same continent hence map not connected.
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
        //If connection made from T4 to T1 then map should be connected
        assertTrue(map.isGraphConnected());

    }

    @After
    public void testResult()
    {
        filePath=null;
    }


}
