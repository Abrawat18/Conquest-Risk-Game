package com.app_team11.conquest;

import com.app_team11.conquest.model.CardTest;
import com.app_team11.conquest.model.ValidEliminatedPlayer;
import com.app_team11.conquest.model.PlayerNotEliminatedTest;
import com.app_team11.conquest.model.PlayerWonGameTest;
import com.app_team11.conquest.model.WinConditionTest;
import com.app_team11.conquest.model.PreAttackValidationTest;
import com.app_team11.conquest.model.SufficientArmyTest;
import com.app_team11.conquest.model.TerritoryCaptureTest;
import com.app_team11.conquest.model.InvalidAttackPhaseTest;
import com.app_team11.conquest.model.AttackTest;
import com.app_team11.conquest.model.AttackPhaseTest;
import com.app_team11.conquest.model.DiceMethodsTest;
import com.app_team11.conquest.model.CaptureTerritoryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 07-Nov-17.
 * Tests for attack phase
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        PreAttackValidationTest.class,  //Pre-attack validations to check for adjacent territory
        SufficientArmyTest.class,  //Validation conditions to check for sufficient armies
        TerritoryCaptureTest.class,  //Checks whether defender territory can be captured
        InvalidAttackPhaseTest.class,  //Checks for invalid scenario of attack phase
        AttackTest.class,  //Checks for Valid scenario of attack phase
        AttackPhaseTest.class,  //Checks for pre-attack validations
        DiceMethodsTest.class,  //check for dice methods
        CaptureTerritoryTest.class,  //validate captured territory
        CardTest.class,     //Checks whether cards can be traded
        ValidEliminatedPlayer.class, //check for eliminated Player
        PlayerWonGameTest.class, //Checks for player won the game conditions
        PlayerNotEliminatedTest.class, //Checks for scenario when player not eliminated
        WinConditionTest.class  //Checks for invalid player won the game condition


})
public class AttackPhaseTestSuite {
}
