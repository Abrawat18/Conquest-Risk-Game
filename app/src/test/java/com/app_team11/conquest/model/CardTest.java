package com.app_team11.conquest.model;

/**
 * Created by Nigel on 19-Oct-17.
 * checks whether cards can be traded
 */
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
        filePath="app\\src\\test\\java\\com\\app_team11\\conquest\\resources\\3D.map";
        failed=false;
        cardList=new ArrayList<Cards>();
        tradeIn=false;
    }

    /**
     * test for card functionality
     */
    @Test
    public void cardTest() {
        ReadMapUtility readTest = new ReadMapUtility();
        GameMap gameMap = readTest.readFile(System.getProperty("user.dir") + File.separator + filePath);
        assertNotNull(gameMap);

        gameMap.setPlayerList(readTest.assignArmies(3));
        assertEquals(3,gameMap.getPlayerList().size());

        for(i=0;i<=4;i++)
        {
            card=new Cards(gameMap.getTerritoryList().get(i),Constants.ARMY_INFANTRY);
            cardList.add(card);

            gameMap.getPlayerList().get(0).setOwnedCards(cardList);
        }
        gameMap.getPlayerList().get(0).checkCardsForTradeIn();
        assertTrue(gameMap.getPlayerList().get(0).getCardTradeIn());
    }
}
