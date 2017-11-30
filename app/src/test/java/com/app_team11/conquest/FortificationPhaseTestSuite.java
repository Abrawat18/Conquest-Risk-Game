package com.app_team11.conquest;

import com.app_team11.conquest.model.TerritoryTest;
import com.app_team11.conquest.model.FortifyPhaseTest;
import com.app_team11.conquest.model.ValidFortificationTest;
import com.app_team11.conquest.utility.FileWriterUtilityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 20-Oct-17.
 * Tests for fortification phase
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TerritoryTest.class,           //Fortify phase
        FortifyPhaseTest.class,        //invalid Fortify Phase Test
        ValidFortificationTest.class,  //Checks for valid fortification phase(adjacent territories)
        FileWriterUtilityTest.class    //FileWriterUtility functionality test

})
public class FortificationPhaseTestSuite {
}
