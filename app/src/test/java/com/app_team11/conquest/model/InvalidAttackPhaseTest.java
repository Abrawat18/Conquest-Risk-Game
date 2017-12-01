package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import com.app_team11.conquest.global.Constants;


import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 06-Nov-17.
 * Checks for invalid scenario of attack phase
 *
 */

public class InvalidAttackPhaseTest {

    List<Territory> territoryList;
    Player player1,player2;
    Territory territory1,territory2;
    ConfigurableMessage cm;

    /**
     * Initalizes variables for the test
     */
    @Before
    public void setUp()
    {
        territoryList=new ArrayList<Territory>();
        player1=new Player();
        player1.setAvailableArmyCount(3);
        player1.setPlayerId(0);

        for(int i=0;i<2;i++)
        {
            territory1=new Territory("Territory"+(i+1));
            territory1.setTerritoryOwner(player1);
            territoryList.add(territory1);
        }
        player2=new Player();
        player2.setAvailableArmyCount(5);
        player2.setPlayerId(5);

        territory2=new Territory("3");
        territory2.setTerritoryOwner(player2);
        territory2.setNeighbourList(territoryList);

    }

    /**
     * test for invalid attack scenario
     */
    @Test
    public void invalidAttack()
    {
        cm=new Player().validateAttackBetweenTerritories(territoryList.get(0),territoryList.get(1));
        System.out.print(cm.getMsgCode()+"msg"+cm.getMsgText());
        assertEquals(Constants.NOT_ADJACENT_TERRITORY,cm.getMsgText());
    }

    @After
    public void cleanup()
    {
        territoryList=null;
        player1=null;
        player2=null;
        cm=null;
        territory1=null;
        territory2=null;
    }

}
