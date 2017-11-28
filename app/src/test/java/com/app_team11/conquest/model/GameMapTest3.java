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


public class GameMapTest3 {


        List<Territory> territoryList;
        //3 players in the game
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
            defenderTerritory.setTerritoryOwner(player1);
            defenderTerritory.setNeighbourList(territoryList);

            List<Territory> testList=new ArrayList<Territory>();
            testList.add(defenderTerritory);
            territoryList.get(1).addRemoveNeighbourToTerr(defenderTerritory,'A');
            territoryList.add(2,defenderTerritory);
            map.setTerritoryList(territoryList);
        }

        @Test
        public void invalidEliminatedPlayer()
        {
            attackerTerritory=territoryList.get(1);
            cm=player3.validateAttackBetweenTerritories(attackerTerritory,defenderTerritory);
            assertEquals(Constants.SUCCESS,cm.getMsgText());

            cm=player3.attackPhase(territoryList.get(1),defenderTerritory,3,2);
            System.out.println(cm.getMsgText());

            if(cm.getMsgCode()==1)
            {
                defenderTerritory.setTerritoryOwner(territoryList.get(1).getTerritoryOwner());
                cm=map.eliminatedPlayer(territoryList.get(1),defenderTerritory);
                //Player has not been eliminated yet
                assertEquals(Constants.MSG_FAIL_CODE,cm.getMsgCode());
            }

        }
    }


