package com.app_team11.conquest.model;

/**
 * Created by Nigel on 19-Oct-17.
 */
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CardTest {
    private String filePath;
    private boolean failed;
    private List<Cards> cardList;
    private Cards card;
    private int i;
    boolean tradeIn;

    @Before
    public void setUp() {
        filePath="D:\\APP build 1 docs\\3D.map";
        failed=false;
        cardList=new ArrayList<Cards>();
        tradeIn=false;
    }
    @Test
    public void validMapTest() {
        ReadMapUtility readTest = new ReadMapUtility();
        GameMap gameMap = readTest.readFile(filePath);
        assertNotNull(gameMap);

        gameMap.setPlayerList(readTest.assignArmies(3));
        assertEquals(3,gameMap.getPlayerList().size());

        for(i=0;i<=4;i++)
        {
            card=new Cards();
            card.setArmyType(Constants.ARMY_INFANTRY);
            card.setCardTerritory(gameMap.getTerritoryList().get(i));
            cardList.add(card);

            gameMap.getPlayerList().get(0).setOwnedCards(cardList);
        }
        gameMap.getPlayerList().get(0).checkCardsForTradeIn();
        assertTrue(gameMap.getPlayerList().get(0).getCardTradeIn());
    }
}
