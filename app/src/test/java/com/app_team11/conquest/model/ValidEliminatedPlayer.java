package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 07-Nov-17.
 * Check for when player is eliminated completely from the game
 */

public class ValidEliminatedPlayer {
    List<Territory> territoryList;
    Player player1,player2,player3;
    Territory territory,territory3;
    ConfigurableMessage cm;
    GameMap map;

    @Before
    public void setUp()
    {
        map=new GameMap();
        player1=new Player();
        player1.setAvailableArmyCount(10);
        player1.setPlayerId(0);
        player3=new Player();
        territoryList=new ArrayList<Territory>();

        for(int i=0;i<2;i++)
        {
            territory=new Territory("Territory"+(i+1));
            territory.setTerritoryOwner(player1);
            territory.setArmyCount(6);
            territoryList.add(territory);
        }
        player2=new Player();
        player2.setAvailableArmyCount(5);
        player2.setPlayerId(5);

        territory3=new Territory("3");
        territory3.setArmyCount(5);
        territory3.setTerritoryOwner(player2);
        territory3.setNeighbourList(territoryList);

        List<Territory> testList=new ArrayList<Territory>();
        testList.add(territory3);
        territoryList.get(1).addRemoveNeighbourToTerr(territory3,'A');
        territoryList.add(2,territory3);
        map.setTerritoryList(territoryList);
    }

    @Test
    public void validEliminatedPlayer()
    {
        cm=player3.validateAttackBetweenTerritories(territoryList.get(1),territory3);
        assertEquals(Constants.SUCCESS,cm.getMsgText());

        cm=player3.attackPhase(territoryList.get(1),territory3,3,2);
        System.out.println(cm.getMsgText());

        if(cm.getMsgCode()==1)
        {
            territory3.setTerritoryOwner(territoryList.get(1).getTerritoryOwner());
            cm=map.eliminatedPlayer(territoryList.get(1),territory3);
            assertEquals(Constants.MSG_FAIL_CODE,cm.getMsgCode());
        }

    }
}
