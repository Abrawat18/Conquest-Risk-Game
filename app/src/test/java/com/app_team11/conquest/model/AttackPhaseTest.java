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
 * Created by Nigel on 06-Nov-17.
 * Checks for validations for the pre-attack phase
 */

public class AttackPhaseTest {
    List<Territory> territoryList;
    Player attacker,defender;
    Territory attackerTerritory,defenderTerritory;
    ConfigurableMessage cm;

    /**
     * Initalizes variables for the test
     */
    @Before
    public void setUp()
    {
        territoryList=new ArrayList<Territory>();
        attacker=new Player();
        attacker.setAvailableArmyCount(10);
        attacker.setPlayerId(0);

        for(int i=0;i<2;i++)
        {
            attackerTerritory=new Territory("Territory"+(i+1));
            attackerTerritory.setTerritoryOwner(attacker);
            attackerTerritory.setArmyCount(6);
            territoryList.add(attackerTerritory);
        }
        defender=new Player();
        defender.setAvailableArmyCount(5);
        defender.setPlayerId(5);

        defenderTerritory=new Territory("3");
        defenderTerritory.setArmyCount(5);
        defenderTerritory.setTerritoryOwner(defender);
        defenderTerritory.setNeighbourList(territoryList);

        List<Territory> testList=new ArrayList<Territory>();
        testList.add(defenderTerritory);
        territoryList.get(1).addRemoveNeighbourToTerr(defenderTerritory,'A');

    }

    /**
     * test for attack phase
     */
    @Test
    public void attackPhase()
    {
        cm=new Player().validateAttackBetweenTerritories(attackerTerritory,defenderTerritory);
        assertEquals(Constants.SUCCESS,cm.getMsgText());

        cm=new Player().attackPhase(attackerTerritory,defenderTerritory,3,2);
        System.out.println(cm.getMsgText());

    }

    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        territoryList=null;
        attacker=null;
        attackerTerritory=null;
        defender=null;
        defenderTerritory=null;
    }
}
