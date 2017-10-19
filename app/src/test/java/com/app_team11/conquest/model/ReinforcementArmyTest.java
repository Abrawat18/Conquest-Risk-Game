package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by RADHEY on 10/19/2017.
 */
public class ReinforcementArmyTest {
    String fileName;
    ReinforcementType reinforcementArmy;
    @Before
    public void setUp()
    {
        fileName="C:\\Users\\RADHEY\\Desktop\\as.map";
        reinforcementArmy=new ReinforcementType();
    }
    @Test
    public void calcReinforcementArmy() throws Exception
    {
        ReadMapUtility read=new ReadMapUtility();
        GameMap map=read.readFile(fileName);
        assertNotNull(map);



        List<Player> playersList=new ArrayList<Player>();
        playersList=read.assignArmies(3);
        map.setPlayerList(playersList);
        playersList=read.randomlyAssignCountries(map.getPlayerList(),map.getTerritoryList());
        playersList=read.assignArmies(3);

        int i=0;
        for(Territory territory:map.getTerritoryList())
        {
            System.out.println("\nTerritory "+territory.getTerritoryName()+"  Owner: "+territory.getTerritoryOwner().getPlayerId());
        }
        for(Player player: playersList)
        {
            i++;
            reinforcementArmy= player.calcReinforcementArmy(map,false,0,null);
            System.out.print("Player"+i+"  "+reinforcementArmy.getOtherTotalReinforcement()+"\n");
        }



    }

}