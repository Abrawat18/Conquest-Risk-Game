package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Test to check whether territories are getting randomly assigned to players
 * Created by Nigel on 30-Nov-17.
 */

public class TerritoryAssignmentTest {
    private List<Continent> continentList;
    private List<Territory> territoryList;
    private GameMap gameMap;
    private ConfigurableMessage cm;
    private Player attackerPlayer,defenderPlayer;
    private List<Player> playerList;
    private Continent continent;
    private Territory territory;
    private int attackerTerritories,defenderTerritories;

    /**
     * Initialize test data
     */
    @Before
    public void setUp()
    {
        continentList=new ArrayList<Continent>();
        territoryList = new ArrayList<Territory>();
        ReinforcementType reinforcementArmy;

        attackerPlayer=new Player();
        defenderPlayer=new Player();
        attackerPlayer.setAvailableArmyCount(5);
        attackerPlayer.setPlayerId(0);
        defenderPlayer.setAvailableArmyCount(7);
        defenderPlayer.setPlayerId(1);
        playerList=new ArrayList<Player>();
        playerList.add(attackerPlayer);
        playerList.add(defenderPlayer);

        continent = new Continent("Test Continent 1", 7);
        continentList.add(continent);


        for(int i=0;i<8;i++) {
            territory = new Territory("Test Territory"+i, i+1, i+5, continent);
            territoryList.add(territory);
        }

        gameMap=new GameMap();
        gameMap.setContinentList(continentList);
        gameMap.setTerritoryList(territoryList);
        gameMap.setPlayerList(playerList);
        attackerTerritories=0;
        defenderTerritories=0;
    }


    /**
     * test to check whether territory is removed successfully
     */
    @Test
    public void randomlyAssignTerritories()
    {
        new ReadMapUtility().randomlyAssignCountries(gameMap.getPlayerList(),gameMap.getTerritoryList());

        for(Territory territory:gameMap.getTerritoryList())
            if(territory.getTerritoryOwner().getPlayerId()==0)
                attackerTerritories++;
            else
                defenderTerritories++;

        assertEquals(territoryList.size()/2,attackerTerritories);
        assertEquals(territoryList.size()/2,defenderTerritories);
    }

    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        territoryList=null;
        territory=null;
        continent=null;
        continentList=null;
        playerList=null;
        attackerTerritories=0;
        defenderTerritories=0;
    }
}

