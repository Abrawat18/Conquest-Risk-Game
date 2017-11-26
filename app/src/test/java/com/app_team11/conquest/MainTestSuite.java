package com.app_team11.conquest;

/**
 * Suite of all the game phases.
 * Created by Nigel on 20-Oct-17.
 */


    import org.junit.runner.RunWith;
    import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        StartupPhaseTestSuite.class,
        ReinforcementPhaseTestSuite.class,
        AttackPhaseTestSuite.class,
        FortificationPhaseTestSuite.class
})
    public class MainTestSuite {

    }

