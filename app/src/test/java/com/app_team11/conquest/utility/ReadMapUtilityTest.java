package com.app_team11.conquest.utility;

import com.app_team11.conquest.model.GameMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by Nigel on 19-Oct-17.
 */

public class ReadMapUtilityTest {
    private String filePath;
    int armyCount;

    @Before
    public void setUp() {
        //filePath="D:\\Test Read Map File.map";
        filePath = "app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\Test Read Map File.map";
    }

    @Test
    public void setUpPhaseTest() {
        ReadMapUtility read = new ReadMapUtility();

        GameMap gameMap = read.readFile(System.getProperty("user.dir") + File.separator + filePath);
        assertNotNull(gameMap);
        System.out.println("Continent list: " + gameMap.getContinentList().size());
        System.out.println("Territory list size: " + gameMap.getTerritoryList().size());
        assertEquals(3, gameMap.getContinentList().size());
        assertEquals(6, gameMap.getTerritoryList().size());

        gameMap.setPlayerList(read.assignArmies(3));
        assertEquals(3, gameMap.getPlayerList().size());
        for (int i = 0; i < gameMap.getPlayerList().size(); i++)
            System.out.println("Player id: " + gameMap.getPlayerList().get(i).getPlayerId());


        read.randomlyAssignCountries(gameMap.getPlayerList(), gameMap.getTerritoryList());
        for (int i = 0; i < gameMap.getTerritoryList().size(); i++)
            System.out.println("Owner: " + gameMap.getTerritoryList().get(i).getTerritoryOwner().getPlayerId() +
                    "\tTerritory name: " + gameMap.getTerritoryList().get(i).getTerritoryName());
        assertEquals(33, gameMap.getPlayerList().get(0).getAvailableArmyCount());
    }

    @After
    public void cleanUp() {
        filePath = null;
    }

}
