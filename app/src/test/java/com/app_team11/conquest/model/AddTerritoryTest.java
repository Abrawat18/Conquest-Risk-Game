package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 19-Oct-17.
 * Checks for invalid territory condition
 */

public class AddTerritoryTest
{
    private List<Continent> continentList;
    private List<Territory> territoryList;
    private GameMap gameMap;
    private ConfigurableMessage configurableMessage;

    private Continent continent;
    private Territory territory;
    private Player player;
    private Cards card;
    private int i;

    /**
     * Initalizes variables for the test
     */
    @Before
    public void setUp() {
        continentList=new ArrayList<Continent>();
        territoryList = new ArrayList<Territory>();
        ReinforcementType reinforcementArmy;
        gameMap = new GameMap();

        continent = new Continent("Test Continent 1", 7);
        continentList.add(continent);

        territory = new Territory("Test Territory 1", 0, 1, continent);
        territoryList.add(territory);

        for(int i=1;i<12;i++)
        {
            territory = new Territory("Test Territory"+i, 0, i, continent);
            territoryList.get(0).addRemoveNeighbourToTerr(territory,'A');
        }

        gameMap=new GameMap();
        gameMap.setContinentList(continentList);
        gameMap.setTerritoryList(territoryList);

    }

    /**
     * Checks whether one more neighbouring territory can be added to territory
     */
    @Test
    public void invalidTerritoryCondition()
    {
        territory=new Territory("Test territory",0,5,continent);
        configurableMessage=gameMap.getTerritoryList().get(0).addRemoveNeighbourToTerr(territory,'A');
        assertEquals(0,configurableMessage.getMsgCode());
    }

    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        territory=null;
        territoryList=null;
        card=null;
        configurableMessage=null;
    }

}
