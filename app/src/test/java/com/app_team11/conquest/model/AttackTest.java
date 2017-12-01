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
 * Checks for Valid scenario of attack phase
 */

public class AttackTest {
    List<Territory> territoryList;
    Player player1,player2;
    Territory attackerTerritory,defenderTerritory;
    ConfigurableMessage cm;

    /**
     * Initalizes variables for the test
     */
    @Before
    public void setUp()
    {
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
        territoryList.get(1).addRemoveNeighbourToTerr(defenderTerritory,'A');

    }

    /**
     * test for valid attack
     */
    @Test
    public void validAttack()
    {
        cm=new Player().validateAttackBetweenTerritories(attackerTerritory,defenderTerritory);
        //all pre-attack validations are in order, hence assertion should be true
        assertEquals(Constants.SUCCESS,cm.getMsgText());
    }
}
