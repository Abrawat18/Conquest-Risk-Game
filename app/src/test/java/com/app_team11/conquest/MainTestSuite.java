package com.app_team11.conquest;

/**
 * Created by Nigel on 20-Oct-17.
 */


    import org.junit.runner.RunWith;
    import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        StartupPhaseTestSuite.class,
        ReinforcementPhaseTestSuite.class,
        FortificationPhaseTestSuite.class
})
    public class MainTestSuite {

    }

