package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.Cards;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 07-Nov-17.
 * Checks for valid fortification phase(adjacent territories)
 */

public class TerritoryTest2 {
    private List<Territory> territoryList;
    private List<Player> playerList;
    private List<Continent> continentList;
    private GameMap map;
    private ConfigurableMessage cm;
    private Continent continent;
    private Territory territory,territory1;
    private Player player;
    private Cards card;
    private int i;

    @Before
    public void setup()
    {
        continentList=new ArrayList<Continent>();
        territoryList=new ArrayList<Territory>();
        playerList=new ArrayList<Player>();
        map=new GameMap();
        cm=new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
        continent=new Continent("Test Continent 1",7);
        continentList.add(continent);

        for(int i=1;i<=2;i++) {
            player = new Player();
            player.setPlayerId(i);
            player.setCardTradeIn(true);
            playerList.add(player);
        }

        for(i=1;i<=2;i++)
        {
            territory=new Territory("Test Territory "+i,0,i,continent);
            territory.setTerritoryOwner(playerList.get(i-1));
            territoryList.add(territory);
        }
        territory1=new Territory("Test Territory 3");
        territory1.setTerritoryOwner(playerList.get(0));
        territory1.addNeighbourToTerr(territoryList);
        territory1.setArmyCount(10);

    }

    @Test
    public void validFortifyPhase()
    {
        cm=territoryList.get(0).fortifyTerritory(territory, playerList.get(0), 11);
        System.out.println(cm.getMsgText());

        assertEquals(0,playerList.get(0).fortifyTerritory(territoryList.get(0), territoryList.get(1), 10).getMsgCode());
    }
}
