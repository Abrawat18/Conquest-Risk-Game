package com.app_team11.conquest;

import com.app_team11.conquest.model.CardTest;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.GameMapTest2;
import com.app_team11.conquest.model.GameMapTest3;
import com.app_team11.conquest.model.GameMapTest4;
import com.app_team11.conquest.model.GameMapTest5;
import com.app_team11.conquest.model.PlayerTest;
import com.app_team11.conquest.model.PlayerTest1;
import com.app_team11.conquest.model.PlayerTest2;
import com.app_team11.conquest.model.PlayerTest3;
import com.app_team11.conquest.model.PlayerTest4;
import com.app_team11.conquest.model.PlayerTest5;
import com.app_team11.conquest.model.PlayerTest6;
import com.app_team11.conquest.model.PlayerTest7;
import com.app_team11.conquest.model.PlayerTest8;
import com.app_team11.conquest.model.TerritoryTest1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Nigel on 07-Nov-17.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        PlayerTest1.class,  //Pre-attack validations to check for adjacent territory
        PlayerTest2.class,  //Validation conditions to check for sufficient armies
        PlayerTest3.class,  //Checks whether defender territory can be captured
        PlayerTest4.class,  //Checks for invalid scenario of attack phase
        PlayerTest5.class,  //Checks for Valid scenario of attack phase
        PlayerTest6.class,  //Checks for pre-attack validations
        PlayerTest7.class,  //check for dice methods
        PlayerTest8.class,  //validate captured territory
        CardTest.class,     //Checks whether cards can be traded
        GameMapTest2.class, //check for eliminated Player
        GameMapTest4.class, //Checks for player won the game conditions
        GameMapTest3.class, //Checks for scenario when player not eliminated
        GameMapTest5.class  //Checks for invalid player won the game condition


})
public class AttackPhaseTestSuite {
}
