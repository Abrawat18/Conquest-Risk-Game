package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 27-Nov-17.
 * Test for aggressive player attack method
 */

public class PlayerTest10
{
    List<Territory> territoryList;
    Player attacker,defender;
    Territory attackerTerritory,defenderTerritory;
    Continent continent1,continent2;
    List<Continent> continentList;
    ConfigurableMessage configurableMessage;
    GameMap map;

    @Before
    public void setUp()
    {
        map=new GameMap();
        territoryList=new ArrayList<Territory>();
        List<Territory> neighbourList=new ArrayList<Territory>();
        attacker=new Player();
        defender=new Player();

        attacker.setAvailableArmyCount(10);
        attacker.setPlayerStrategy(new AggressivePlayerStrategy());
        attacker.setPlayerStrategyType("Aggressive");
        attacker.setPlayerId(0);

        continent1=new Continent();
        continent1.setScore(5);
        continent1.setContName("Test Continent");

        territoryList=new ArrayList<Territory>();
        attackerTerritory=new Territory("Territory1");
        attackerTerritory.setTerritoryOwner(attacker);
        attackerTerritory.setArmyCount(6);
        attackerTerritory.setContinent(continent1);


        defender=new Player();
        defender.setAvailableArmyCount(5);
        defender.setPlayerId(2);

        continent2=new Continent();
        continent2.setContName("Test continent 2");
        continent2.setScore(10);

        defenderTerritory=new Territory("Territory2");
        defenderTerritory.setArmyCount(5);
        defenderTerritory.setTerritoryOwner(defender);
        neighbourList.add(attackerTerritory);
        defenderTerritory.setNeighbourList(neighbourList);
        defenderTerritory.setContinent(continent2);

        territoryList.add(defenderTerritory);
        attackerTerritory.setNeighbourList(territoryList);
        continentList=new ArrayList<Continent>();
        continentList.add(continent1);
        continentList.add(continent2);

        map.setTerritoryList(territoryList);

        map.setContinentList(continentList);
        List<Player> playerList=new ArrayList<Player>();
        playerList.add(attacker);
        playerList.add(defender);
        map.setPlayerList(playerList);

    }

    @Test
    public void attackPhase()
    {
        System.out.println("For "+attackerTerritory.getTerritoryName()+" neighbours: ");
        for(Territory t:attackerTerritory.getNeighbourList()) {
            System.out.println(t.getTerritoryName());
        }
        System.out.println("For"+defenderTerritory.getTerritoryName()+" neighbours: ");
        for(Territory t:defenderTerritory.getNeighbourList())
            System.out.println(t.getTerritoryName());
        configurableMessage=attacker.attackPhase(map);
        //Attack successful
        System.out.println("cm"+configurableMessage.getMsgText());
        assertEquals(Constants.SUCCESS,configurableMessage.getMsgText());
    }

}
