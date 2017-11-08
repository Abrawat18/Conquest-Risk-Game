package com.app_team11.conquest;

import com.app_team11.conquest.model.TerritoryTest;
import com.app_team11.conquest.model.TerritoryTest1;
import com.app_team11.conquest.model.TerritoryTest2;
import com.app_team11.conquest.utility.FileWriterUtilityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 20-Oct-17.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TerritoryTest.class, //Fortify phase
        TerritoryTest1.class, //invalid Fortify Phase Test
        TerritoryTest2.class,  //Checks for valid fortification phase(adjacent territories)
        FileWriterUtilityTest.class

})
public class FortificationPhaseTestSuite {
}
