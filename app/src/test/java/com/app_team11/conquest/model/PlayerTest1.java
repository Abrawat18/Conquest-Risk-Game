package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * This file tests the attack phase validations for invalid and valid adjacent territory
 * Created by Nigel on 06-Nov-17.
 */

public class PlayerTest1 {

    List<Territory> territoryList;
    List<Player> playerList;
    Player player,player1;
    Territory territory,territory3;
    ConfigurableMessage cm;

    @Before
    public void setUp()
    {
        territoryList=new ArrayList<Territory>();
        player=new Player();
        player.setAvailableArmyCount(3);
        player.setPlayerId(0);

        for(int i=0;i<2;i++)
        {
            territory=new Territory("Territory"+(i+1));
            territory.setTerritoryOwner(player);
            territoryList.add(territory);
        }

        player1=new Player();
        player1.setAvailableArmyCount(5);
        player1.setPlayerId(5);

        territory3=new Territory("3");
        territory3.setTerritoryOwner(player1);
        territory3.setNeighbourList(territoryList);


    }

    @Test
    public void validateForAttack()
    {
        cm=new Player().isAdjacentTerritory(territoryList.get(0),territoryList.get(1));
        assertEquals(Constants.MSG_FAIL_CODE,cm.getMsgCode());

        List<Territory> testList=new ArrayList<Territory>();
        testList.add(territory3);
        territoryList.get(1).addRemoveNeighbourToTerr(territory3,'A');
        System.out.println("attacker: ");

        cm=new Player().isAdjacentTerritory(territoryList.get(1),territory3);
        assertEquals(Constants.MSG_SUCC_CODE,cm.getMsgCode());


    }
}
