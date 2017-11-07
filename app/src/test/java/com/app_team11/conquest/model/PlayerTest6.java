package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 06-Nov-17.
 * Checks for attack phase
 */

public class PlayerTest6 {
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
        player.setAvailableArmyCount(10);
        player.setPlayerId(0);

        for(int i=0;i<2;i++)
        {
            territory=new Territory("Territory"+(i+1));
            territory.setTerritoryOwner(player);
            territory.setArmyCount(6);
            territoryList.add(territory);
        }
        player1=new Player();
        player1.setAvailableArmyCount(5);
        player1.setPlayerId(5);

        territory3=new Territory("3");
        territory3.setArmyCount(5);
        territory3.setTerritoryOwner(player1);
        territory3.setNeighbourList(territoryList);

        List<Territory> testList=new ArrayList<Territory>();
        testList.add(territory3);
        territoryList.get(1).addRemoveNeighbourToTerr(territory3,'A');

    }

    @Test
    public void attackPhase()
    {
        cm=new Player().validateAttackBetweenTerritories(territoryList.get(1),territory3);
        assertEquals(Constants.SUCCESS,cm.getMsgText());

        cm=new Player().attackPhase(territoryList.get(1),territory3,3,2);
        System.out.println(cm.getMsgText());

    }
}
