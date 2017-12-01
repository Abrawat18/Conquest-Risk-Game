package com.app_team11.conquest;

/**
 * Suite of all the game phases.
 * Created by Nigel on 20-Oct-17.
 */


    import org.junit.runner.RunWith;
    import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        StartupPhaseTestSuite.class,       //tests for startup phase
        ReinforcementPhaseTestSuite.class, //tests for reinforcement phase
        AttackPhaseTestSuite.class,        //tests for attack phase
        FortificationPhaseTestSuite.class, //tests for fortification phase
        TournamentModeTestSuite.class,      //tests for tournament mode
        SaveGameTestSuite.class             //tests for save/load map
})
/**
 * Test suite which contains all test classes
 */
    public class MainTestSuite {

    }

