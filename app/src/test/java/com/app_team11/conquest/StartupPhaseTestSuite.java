package com.app_team11.conquest;

/**
 * Created by Nigel on 20-Oct-17.
 */
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.GameMapTest1;
import com.app_team11.conquest.model.GameMapTest2;
import com.app_team11.conquest.model.GameMapTest3;
import com.app_team11.conquest.model.GameMapTest4;
import com.app_team11.conquest.model.GameMapTest5;
import com.app_team11.conquest.model.GameMapTest6;
import com.app_team11.conquest.model.GameMapTest7;
import com.app_team11.conquest.model.TerritoryTest;
import com.app_team11.conquest.utility.InvalidMapTest;
import com.app_team11.conquest.utility.ReadMapUtilityTest;
import com.app_team11.conquest.utility.ValidMapTest;
import com.app_team11.conquest.model.CardTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


    @RunWith(Suite.class)

    @Suite.SuiteClasses({
            GameMapTest1.class,
            GameMapTest3.class,
            GameMapTest2.class,
            GameMapTest4.class,
            GameMapTest5.class,
            GameMapTest6.class,
            GameMapTest7.class,
            InvalidMapTest.class,
            ReadMapUtilityTest.class,
            ValidMapTest.class,
            CardTest.class
    })

    public class StartupPhaseTestSuite {
    }


