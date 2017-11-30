package com.app_team11.conquest;

import com.app_team11.conquest.model.ReinforcementArmyTest;
import com.app_team11.conquest.model.ReinforceWithCardsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 20-Oct-17.
 * Tests for reinforcement phase
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ReinforcementArmyTest.class, //reinforcement phase
        ReinforceWithCardsTest.class //checks for reinforcement phase with different card scenario


})
public class ReinforcementPhaseTestSuite {
}
