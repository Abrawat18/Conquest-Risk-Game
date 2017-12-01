package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Nigel on 10/19/2017.
 * Scenarios for valid and invalid fortification phases
 */
public class TerritoryTest {
    private List<Territory> territoryList;
    private List<Continent> continentList;
    private Continent continent;
    private Territory territory;
    private Player player;
    private int i;

    @Before
    public void setup()
    {
        continentList=new ArrayList<Continent>();
        territoryList=new ArrayList<Territory>();
        continent=new Continent("Test Continent 1",7);
        continentList.add(continent);


            player = new Player();
            player.setPlayerId(i);
            player.setCardTradeIn(true);


        for(i=1;i<=3;i++)
        {
            territory=new Territory("Test Territory "+i,0,i,continent);
            territory.setTerritoryOwner(player);
            territoryList.add(territory);
        }

        territoryList.get(0).addRemoveNeighbourToTerr(territoryList.get(1),'A');
        territoryList.get(1).addRemoveNeighbourToTerr(territoryList.get(0),'A');

        territoryList.get(1).addRemoveNeighbourToTerr(territoryList.get(2),'A');
        territoryList.get(2).addRemoveNeighbourToTerr(territoryList.get(1),'A');

        //assign territories to players
        territoryList.get(0).setTerritoryOwner(player);
        territoryList.get(0).setArmyCount(15);
        territoryList.get(1).setTerritoryOwner(player);
        territoryList.get(1).setArmyCount(0);
        territoryList.get(2).setTerritoryOwner(player);
        territoryList.get(2).setArmyCount(3);

    }

    /**
     * test to check fortification phase
     */
    @Test
    public void fortificationTestCases()
    {
        //Valid scenario
        assertEquals(Constants.MSG_SUCC_CODE,territoryList.get(0).fortifyTerritory(territoryList.get(1), player, 10).getMsgCode());

        //Exceeded number of armies, Hence should fail
        assertEquals(Constants.MSG_FAIL_CODE,territoryList.get(2).fortifyTerritory(territoryList.get(1), player, 10).getMsgCode());
    }
    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        territoryList=null;
        continent=null;
        continentList=null;
        player=null;
        territory=null;
    }


}