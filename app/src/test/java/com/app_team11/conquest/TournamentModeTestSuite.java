package com.app_team11.conquest;

import com.app_team11.conquest.model.AddTerritoryTest;
import com.app_team11.conquest.model.AggressivePlayerStrategyTest;
import com.app_team11.conquest.model.BenevolentPlayerStrategyTest;
import com.app_team11.conquest.model.CheaterPlayerStrategyTest;
import com.app_team11.conquest.model.ConnectedGraphTest;
import com.app_team11.conquest.model.GraphTest;
import com.app_team11.conquest.model.InvalidMapTest;
import com.app_team11.conquest.model.RandomPlayerStrategyTest;
import com.app_team11.conquest.model.ReadFileTest;
import com.app_team11.conquest.model.RemoveTerritoryTest;
import com.app_team11.conquest.model.UnconnectedContinentTest;
import com.app_team11.conquest.model.UnconnectedGraphTest;
import com.app_team11.conquest.utility.ReadMapUtilityTest;
import com.app_team11.conquest.utility.SaveGameFunctionalityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 30-Nov-17.
 * Tests to check the player strategies in tournament mode
 */


    @RunWith(Suite.class)

    @Suite.SuiteClasses({
            AggressivePlayerStrategyTest.class, //test for aggressive player strategy methods
            BenevolentPlayerStrategyTest.class, //test for benevolent player strategy methods
            RandomPlayerStrategyTest.class,     //test for random player strategy methods
            CheaterPlayerStrategyTest.class,     //test for cheater player strategy methods
            SaveGameFunctionalityTest.class      //test for save game functionality
    })
    public class TournamentModeTestSuite
{

}
