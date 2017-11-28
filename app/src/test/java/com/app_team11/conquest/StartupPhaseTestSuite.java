package com.app_team11.conquest;

/**
 * Created by Nigel on 20-Oct-17.
 */
import com.app_team11.conquest.model.GameMapTest1;
import com.app_team11.conquest.model.GameMapTest10;
import com.app_team11.conquest.model.GameMapTest11;
import com.app_team11.conquest.model.GameMapTest6;
import com.app_team11.conquest.model.GameMapTest7;
import com.app_team11.conquest.model.GameMapTest9;
import com.app_team11.conquest.utility.ReadMapUtilityTest;
import com.app_team11.conquest.model.GameMapTest8;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


    @RunWith(Suite.class)

    @Suite.SuiteClasses({
            GameMapTest9.class, //Checks whether the graph formed is connected
            GameMapTest1.class, //check for file contents
            GameMapTest6.class, // Tests the max territory limit
            GameMapTest7.class, // Test for remove territory
            ReadMapUtilityTest.class, //Checks army count
            GameMapTest8.class, //Check for connected graph
            GameMapTest10.class, //Check for unconnected graph
            GameMapTest11.class //Check for connected graph after adding continent
    })

    public class StartupPhaseTestSuite {
    }


