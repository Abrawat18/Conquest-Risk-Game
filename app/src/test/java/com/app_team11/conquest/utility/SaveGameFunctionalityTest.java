package com.app_team11.conquest.utility;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.AggressivePlayerStrategy;
import com.app_team11.conquest.model.Cards;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Nigel on 30-Nov-17.
 * Test to check Save Game Functionality
 */

public class SaveGameFunctionalityTest {
    List<Territory> territoryList;
    Player attacker,defender;
    Territory attackerTerritory,defenderTerritory;
    Continent continent1,continent2;
    List<Continent> continentList;
    List<Cards> cardList;
    Cards infantry,cavalry;
    ConfigurableMessage configurableMessage;
    GameMap map;
    FileManager fileManager;
    String filePath;

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
        attacker.setPlayerStrategy(new AggressivePlayerStrategy());
        attacker.setPlayerStrategyType("Aggressive");
        attacker.setPlayerId(0);

        continent1=new Continent();
        continent1.setScore(5);
        continent1.setContName("Test Continent");

        territoryList=new ArrayList<Territory>();
        attackerTerritory=new Territory("Territory1");
        attackerTerritory.setTerritoryOwner(attacker);
        attackerTerritory.setArmyCount(5);
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
        fileManager=FileManager.getInstance();
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\testFile.ser";
    }

    @Test
    public void aggressiveStartupPhase() {
        //Startup Phase
        configurableMessage = attacker.startupPhase(map);
        assertEquals(Constants.SUCCESS, configurableMessage.getMsgText());
    }
    @Test
    public void aggressiveAttackPhase() {
        //Attack Phase
        configurableMessage = attacker.attackPhase(map);
        assertEquals(Constants.ATTACK_SUCCESS_STRATEGY, configurableMessage.getMsgText());
        System.out.println("Attacker Armies: "+attackerTerritory.getArmyCount());
    }
    @Test
    public void saveMapTest()
    {
        File file=new File(System.getProperty("user.dir") + File.separator + filePath);
        assertTrue(fileManager.writeObjectIntoFile(map,file));
    }
    @Test
    public void readFileTest()
    {
       String file= System.getProperty("user.dir") + File.separator + filePath;

       GameMap map1=new GameMap();
       map1=fileManager.readObjectFromFile(file);

       for(Territory territory : map1.getTerritoryList()) {
           System.out.println("\n======\nPlayer: "+territory.getTerritoryOwner().getPlayerId());
           System.out.println("Territory: "+territory.getTerritoryName());
           System.out.println("Armies: "+territory.getArmyCount());
           if (territory.getTerritoryName().equals("Territory1")) {
               assertEquals(2, territory.getArmyCount());
           }
       }
    }

}
