package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nigel on 05-Nov-17.
 * Reinforcement phase with cards
 */

public class ReinforceWithCardsTest {
    private List<Territory> territoryList;
    private List<Player> playerList;
    private List<Cards> cardListOwned;
    private List<Cards> cardList;
    private List<Continent> continentList;
    private GameMap map;
    private ReinforcementType reinforcementArmy;

    private Continent continent;
    private Territory territory;
    private Player player;
    private Cards card;
    private int i;

    @Before
    public void setUp()
    {

        reinforcementArmy=new ReinforcementType();
        continentList=new ArrayList<Continent>();
        territoryList=new ArrayList<Territory>();
        playerList=new ArrayList<Player>();
        cardList=new ArrayList<Cards>();
        cardListOwned=new ArrayList<Cards>();
        ReinforcementType reinforcementArmy;
        map=new GameMap();

        continent=new Continent("Test Continent 1",7);
        continentList.add(continent);

        for(int i=1;i<=2;i++) {
            player = new Player();
            player.setPlayerId(i);
            player.setCardTradeIn(true);
            player.setAvailableArmyCount(5);
            playerList.add(player);
        }
        continent.setContOwner(playerList.get(0));
        //assign 6 territories to player
        for(i=1;i<7;i++)
        {
            territory=new Territory("Test Territory "+i,0,i,continent);
            territory.setTerritoryOwner(playerList.get(0));
            territoryList.add(territory);
        }
        for(int j=1;j<4;j++) {
            card=new Cards(territoryList.get(j), Constants.ARMY_ARTILLERY);
            cardListOwned.add(card);

        }
        for(i=0;i<3;i++)
        {
            card=new Cards(territoryList.get(i+3),Constants.ARMY_ARTILLERY);
            cardList.add(card);
        }

        playerList.get(0).setOwnedCards(cardList);

        map=new GameMap();
        map.setContinentList(continentList);
        map.setTerritoryList(territoryList);
        map.setPlayerList(playerList);

    }
    @Test
    public void calcReinforcementArmy() throws Exception
    {
        reinforcementArmy=playerList.get(0).calcReinforcementArmy(map,1,cardListOwned);
        System.out.println(reinforcementArmy.getOtherTotalReinforcement()+" "+reinforcementArmy.getMatchedTerrCardReinforcement());
        assertEquals(2,reinforcementArmy.getMatchedTerrCardReinforcement());
        assertEquals(14,reinforcementArmy.getOtherTotalReinforcement());
    }

}


