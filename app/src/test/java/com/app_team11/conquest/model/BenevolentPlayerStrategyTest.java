package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 29-Nov-17.
 * Test for benevolent player attack phase
 */

public class BenevolentPlayerStrategyTest {
    List<Territory> territoryList;
    Player attacker,defender;
    Territory attackerTerritory,defenderTerritory;
    Continent continent1,continent2;
    List<Continent> continentList;
    List<Cards> cardList;
    Cards infantry,cavalry;
    ConfigurableMessage configurableMessage;
    GameMap map;

    @Before
    public void setUp()
    {
        map=new GameMap();
        territoryList=new ArrayList<Territory>();
        List<Territory> neighbourList=new ArrayList<Territory>();
        cardList=new ArrayList<Cards>();
        attacker=new Player();
        defender=new Player();
        infantry=new Cards(attackerTerritory, Constants.ARMY_INFANTRY);
        cavalry=new Cards(defenderTerritory,Constants.ARMY_CAVALRY);
        cardList.add(infantry);
        cardList.add(cavalry);

        attacker.setAvailableArmyCount(2);
        attacker.setPlayerStrategy(new BenevolentPlayerStrategy());
        attacker.setPlayerStrategyType("Benevolent");
        attacker.setPlayerId(0);

        continent1=new Continent();
        continent1.setScore(5);
        continent1.setContName("Test Continent");

        territoryList=new ArrayList<Territory>();
        attackerTerritory=new Territory("Territory1");
        attackerTerritory.setTerritoryOwner(attacker);
        attackerTerritory.setArmyCount(2);
        attackerTerritory.setContinent(continent1);



        defender=new Player();
        defender.setAvailableArmyCount(2);
        defender.setPlayerId(2);

        continent2=new Continent();
        continent2.setContName("Test continent 2");
        continent2.setScore(10);

        defenderTerritory=new Territory("Territory2");
        defenderTerritory.setArmyCount(1);
        defenderTerritory.setTerritoryOwner(defender);
        neighbourList.add(attackerTerritory);
        defenderTerritory.setNeighbourList(neighbourList);
        defenderTerritory.setContinent(continent2);

        territoryList.add(defenderTerritory);
        attackerTerritory.setNeighbourList(territoryList);
        continentList=new ArrayList<Continent>();
        continentList.add(continent1);
        continentList.add(continent2);

        territoryList.add(attackerTerritory);
        List<Player> playerList=new ArrayList<Player>();
        playerList.add(attacker);
        playerList.add(defender);
        map.setContinentList(continentList);
        map.setPlayerList(playerList);
        map.setTerritoryList(territoryList);
        map.setCardList(cardList);

    }

    /**
     * test for benevolent player startup phase
     */
    @Test
    public void benevolentStartupPhase() {
        //Startup Phase
        configurableMessage = attacker.startupPhase(map);
        assertEquals(Constants.SUCCESS, configurableMessage.getMsgText());
    }

    /**
     * test for benevolent player attack phase
     */
    @Test
    public void benevolentAttackPhase() {
        configurableMessage = attacker.attackPhase(map);
        //Benevolent player never attacks therefore its army count should be same
        assertEquals(1, defenderTerritory.getArmyCount());
    }

    /**
     * test for benevolent player reinforcement phase
     */
    @Test
    public void benevolentReinforcementPhase() {
        //Reinforcement phase
        configurableMessage = attacker.reInforcementPhase(map);
        assertEquals(Constants.REINFORCEMENT_SUCCESS_STRATEGY, configurableMessage.getMsgText());
    }

    /**
     * test for benevolent player fortification phase
     */
    @Test
    public void benevolentFortificationPhase(){
        //Fortification Phase
        configurableMessage=attacker.fortificationPhase(map);
        assertEquals(Constants.FORTIFICATION_SUCCESS,configurableMessage.getMsgText());

    }

}