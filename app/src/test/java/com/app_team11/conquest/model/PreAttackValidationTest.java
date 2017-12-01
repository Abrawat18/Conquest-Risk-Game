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
 * This file is for checking pre-attack validations to check for adjacent territory
 * Created by Nigel on 06-Nov-17.
 */

public class PreAttackValidationTest {

    List<Territory> territoryList;
    List<Player> playerList;
    Player player,player1;
    //Territories territory1 and territory2 are in the map. Test methods checks whether they're adjacent
    Territory territory1,territory2;
    ConfigurableMessage cm;

    /**
     * Initializes test data
     */
    @Before
    public void setUp()
    {
        territoryList=new ArrayList<Territory>();
        player=new Player();
        player.setAvailableArmyCount(3);
        player.setPlayerId(0);

        for(int i=0;i<2;i++)
        {
            territory1=new Territory("Territory"+(i+1));
            territory1.setTerritoryOwner(player);
            territoryList.add(territory1);
        }

        player1=new Player();
        player1.setAvailableArmyCount(5);
        player1.setPlayerId(5);

        territory2=new Territory("3");
        territory2.setTerritoryOwner(player1);
        territory2.setNeighbourList(territoryList);


    }

    /**
     * test for pre-validation attack
     */
    @Test
    public void preValidationForAttack()
    {
        cm=new Player().isAdjacentTerritory(territoryList.get(0),territoryList.get(1));
        assertEquals(Constants.MSG_FAIL_CODE,cm.getMsgCode());

        List<Territory> testList=new ArrayList<Territory>();
        testList.add(territory2);
        territoryList.get(1).addRemoveNeighbourToTerr(territory2,'A');
        System.out.println("attacker: ");

        cm=new Player().isAdjacentTerritory(territoryList.get(1),territory2);
        assertEquals(Constants.MSG_SUCC_CODE,cm.getMsgCode());


    }
    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        territoryList=null;
        player=null;
        player1=null;

    }
}
