package com.app_team11.conquest.utility;

import com.app_team11.conquest.model.GameMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Nigel on 19-Oct-17.
 */

public class ValidMapTest {
    private String filePath;
    private boolean failed;

    @Before
    public void setUp() {
        filePath="D:\\APP build 1 docs\\3D.map";
        failed=false;
    }
    @Test
    public void validMapTest()
    {
        ReadMapUtility readTest=new ReadMapUtility();
        GameMap gameMap=readTest.readFile(filePath);
        if(gameMap.getContinentList()!=null && gameMap.getTerritoryList()!=null && gameMap.getPlayerList()!=null)
            assertFalse(failed);

    }
    @After
    public void clean()
    {
        filePath=null;
    }

}
