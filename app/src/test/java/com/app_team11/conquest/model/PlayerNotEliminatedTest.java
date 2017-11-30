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
 * Checks for scenario when player not eliminated
 */


public class PlayerNotEliminatedTest {


    List<Territory> territoryList;
    Player player1,player2,player3;
    Territory attackerTerritory,defenderTerritory;
    ConfigurableMessage cm;
    GameMap map;

    @Before
    public void setUp()
    {
        map=new GameMap();
        player3=new Player();
        territoryList=new ArrayList<Territory>();
        player1=new Player();
        player1.setAvailableArmyCount(10);
        player1.setPlayerId(0);

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
        attackerTerritory.addRemoveNeighbourToTerr(defenderTerritory,'A');
        territoryList.add(2,defenderTerritory);
        map.setTerritoryList(territoryList);
    }

    /**
     * test scenario to check when player is not eliminated
     */
    @Test
    public void invalidEliminatedPlayer()
    {
        cm=player3.validateAttackBetweenTerritories(attackerTerritory,defenderTerritory);
        assertEquals(Constants.SUCCESS,cm.getMsgText());

        cm=player3.attackPhase(attackerTerritory,defenderTerritory,3,2);
        System.out.println(cm.getMsgText());

        if(cm.getMsgCode()==1)
        {
            defenderTerritory.setTerritoryOwner(attackerTerritory.getTerritoryOwner());
            cm=map.eliminatedPlayer(attackerTerritory,defenderTerritory);
            //Player has not been eliminated yet
            assertEquals(Constants.MSG_FAIL_CODE,cm.getMsgCode());
        }

    }
}


