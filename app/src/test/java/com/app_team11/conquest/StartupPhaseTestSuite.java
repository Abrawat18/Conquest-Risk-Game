package com.app_team11.conquest;

/**
 * Created by Nigel on 20-Oct-17.
 * Tests for map creation & startup phase methods
 */
import com.app_team11.conquest.model.ReadFileTest;
import com.app_team11.conquest.model.UnconnectedContinentTest;
import com.app_team11.conquest.model.ConnectedGraphTest;
import com.app_team11.conquest.model.InvalidMapTest;
import com.app_team11.conquest.model.AddTerritoryTest;
import com.app_team11.conquest.model.RemoveTerritoryTest;
import com.app_team11.conquest.model.UnconnectedGraphTest;
import com.app_team11.conquest.utility.ReadMapUtilityTest;
import com.app_team11.conquest.model.GraphTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


    @RunWith(Suite.class)

    @Suite.SuiteClasses({
            ReadFileTest.class, //check for file contents
            AddTerritoryTest.class, // Tests the max territory limit
            RemoveTerritoryTest.class, // Test for remove territory
            ReadMapUtilityTest.class, //Checks for the inital army count
            GraphTest.class, //Check for connected graph
            UnconnectedGraphTest.class, //Checks whether the graph formed is connected
            UnconnectedContinentTest.class, //Check for connected graph after adding continent
            ConnectedGraphTest.class, //Check for connected territories and continents
            InvalidMapTest.class //Check for invalid map file
    })

    public class StartupPhaseTestSuite {
    }


