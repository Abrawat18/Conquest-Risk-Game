package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 07-Nov-17.
 * Validate capture territory method
 */

public class CaptureTerritoryTest {
    List<Territory> territoryList;
    Player player1,player2,player3;
    Territory attackerTerritory,defenderTerritory;
    ConfigurableMessage configurableMessage;

    /**
     * Initalizes variables for the test
     */
    @Before
    public void setUp()
    {
        player1=new Player();
        player1.setAvailableArmyCount(10);
        player1.setPlayerId(0);
        player3=new Player();
        territoryList=new ArrayList<Territory>();

        for(int i=0;i<2;i++)
        {
            attackerTerritory=new Territory("Territory"+(i+1));
            attackerTerritory.setTerritoryOwner(player1);
            attackerTerritory.setArmyCount(6);
            territoryList.add(attackerTerritory);
        }
        player2=new Player();
        player2.setAvailableArmyCount(5);
        player2.setPlayerId(5);

        defenderTerritory=new Territory("3");
        defenderTerritory.setArmyCount(5);
        defenderTerritory.setTerritoryOwner(player2);
        defenderTerritory.setNeighbourList(territoryList);

        List<Territory> testList=new ArrayList<Territory>();
        testList.add(defenderTerritory);
        territoryList.get(1).addRemoveNeighbourToTerr(defenderTerritory,'A');

    }

    /**
     * test to capture territory
     */
    @Test
    public void captureTerritoryTest()
    {
        configurableMessage=player3.validateAttackBetweenTerritories(attackerTerritory,defenderTerritory);
        assertEquals(Constants.SUCCESS,configurableMessage.getMsgText());

        configurableMessage=player3.attackPhase(attackerTerritory,defenderTerritory,3,2);
        System.out.println(configurableMessage.getMsgText());

        if(configurableMessage.getMsgCode()==1)
        {
            configurableMessage=player3.captureTerritory(attackerTerritory,defenderTerritory,6);
            assertEquals(Constants.LEAVE_ONE_ARMY,configurableMessage.getMsgText());

            configurableMessage=player3.captureTerritory(attackerTerritory,defenderTerritory,5);
            assertEquals(Constants.SUCCESS,configurableMessage.getMsgText());

        }

    }
    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        territoryList=null;
       attackerTerritory=null;
        defenderTerritory=null;
       player1=null;
        player2=null;
        player3=null;
    }
}
