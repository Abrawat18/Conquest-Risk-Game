package com.app_team11.conquest;

import com.app_team11.conquest.model.ReinforcementArmyTest;
import com.app_team11.conquest.model.PlayerTest9;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 20-Oct-17.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ReinforcementArmyTest.class, //reinforcement phase
        PlayerTest9.class //checks for reinforcement phase with different card scenario


})
public class ReinforcementPhaseTestSuite {
}
