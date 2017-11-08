package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Nigel on 19-Oct-17.
 * Check for file read
 */

public class GameMapTest1 {
    private String filePath;
    private boolean failed;
    private File f;
    private Territory territory;
    private Continent continent;
    private int currentSize;

    @Before
    public void setUp() {
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\3D.map";
        failed=false;
        continent=new Continent("Test Continent",5);
        territory=new Territory("Test Territory",0,1,continent);
        currentSize=0;
    }

    @Test
    public void gameMapFunctionalityTest() {
        ReadMapUtility readTest = new ReadMapUtility();
        GameMap gameMap = readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        System.out.println("map is" + gameMap);
        gameMap.setAuthorName("Tester");
        gameMap.setImageName("Test Image");
        gameMap.setScrollLine("yes");
        gameMap.setWarnFlag("yes");
        gameMap.setWrapFlag("yes");

        System.out.println("Continent list: " + gameMap.getContinentList().size());
        System.out.println("Territory list size: " + gameMap.getTerritoryList().size());
        assertEquals(2, gameMap.getContinentList().size());
        assertEquals(13, gameMap.getTerritoryList().size());

        gameMap.setPlayerList(readTest.assignArmies(3));
        assertEquals(3, gameMap.getPlayerList().size());
        for (int i = 0; i < gameMap.getPlayerList().size(); i++)
            System.out.println("Player id: " + gameMap.getPlayerList().get(i).getPlayerId());


        readTest.randomlyAssignCountries(gameMap.getPlayerList(), gameMap.getTerritoryList());
        for (int i = 0; i < gameMap.getTerritoryList().size(); i++)
            System.out.println("Owner: " + gameMap.getTerritoryList().get(i).getTerritoryOwner().getPlayerId() +
                    "\tTerritory name: " + gameMap.getTerritoryList().get(i).getTerritoryName());

        System.out.println("Territory 1 neighbours list size: " + gameMap.getTerritoryList().get(0).getNeighbourList().size());
        currentSize = gameMap.getTerritoryList().get(0).getNeighbourList().size();
        gameMap.getTerritoryList().get(0).addRemoveNeighbourToTerr(territory, 'A');
        System.out.println("Territory 1 neighbours list size after adding territory: " + gameMap.getTerritoryList().get(0).getNeighbourList().size());
        assertEquals(currentSize + 1, gameMap.getTerritoryList().get(0).getNeighbourList().size());



    }

}
