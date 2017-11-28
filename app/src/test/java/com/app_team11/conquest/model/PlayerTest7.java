package com.app_team11.conquest.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 07-Nov-17.
 * Check for dice methods
 */

public class PlayerTest7 {

    List<Integer> diceList;
    Player player;

    @Before
    public void setUp()
    {
        player=new Player();

        diceList=new ArrayList<Integer>();
        for(int i=0;i<3;i++)
        {
            diceList.add(i*45);
        }

    }

    @Test
    public void diceMethods()
    {
        assertEquals(90,player.getHighestValue(diceList));

        diceList=player.getRandomDiceValues(2);
        System.out.println("For 2 dice:\nDice Values:");
        for(int i : diceList)
            System.out.print(i+" ");
        System.out.println("\nHighest dice value for 2 dice: "+player.getHighestValue(diceList));

        diceList=player.getRandomDiceValues(3);
        System.out.println("\n\nFor 3 dice:\nDice Values:");
        for(int i : diceList)
            System.out.print(i+" ");
        System.out.println("\nHighest dice value for 3 dice: "+player.getHighestValue(diceList));

    }
}
