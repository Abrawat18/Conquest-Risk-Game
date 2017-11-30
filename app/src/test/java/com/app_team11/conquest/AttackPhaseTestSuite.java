package com.app_team11.conquest;

import com.app_team11.conquest.model.CardTest;
import com.app_team11.conquest.model.ValidEliminatedPlayer;
import com.app_team11.conquest.model.PlayerNotEliminatedTest;
import com.app_team11.conquest.model.PlayerWonGameTest;
import com.app_team11.conquest.model.GameMapTest5;
import com.app_team11.conquest.model.PreAttackValidationTest;
import com.app_team11.conquest.model.PlayerTest2;
import com.app_team11.conquest.model.PlayerTest3;
import com.app_team11.conquest.model.PlayerTest4;
import com.app_team11.conquest.model.PlayerTest5;
import com.app_team11.conquest.model.PlayerTest6;
import com.app_team11.conquest.model.PlayerTest7;
import com.app_team11.conquest.model.PlayerTest8;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 07-Nov-17.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        PreAttackValidationTest.class,  //Pre-attack validations to check for adjacent territory
        PlayerTest2.class,  //Validation conditions to check for sufficient armies
        PlayerTest3.class,  //Checks whether defender territory can be captured
        PlayerTest4.class,  //Checks for invalid scenario of attack phase
        PlayerTest5.class,  //Checks for Valid scenario of attack phase
        PlayerTest6.class,  //Checks for pre-attack validations
        PlayerTest7.class,  //check for dice methods
        PlayerTest8.class,  //validate captured territory
        CardTest.class,     //Checks whether cards can be traded
        ValidEliminatedPlayer.class, //check for eliminated Player
        PlayerWonGameTest.class, //Checks for player won the game conditions
        PlayerNotEliminatedTest.class, //Checks for scenario when player not eliminated
        GameMapTest5.class  //Checks for invalid player won the game condition


})
public class AttackPhaseTestSuite {
}
