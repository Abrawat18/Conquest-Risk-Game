package com.app_team11.conquest.utility;

import android.util.Log;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.ObserverType;
import com.app_team11.conquest.model.PhaseViewModel;
import com.app_team11.conquest.model.Territory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jaydeep on 11/27/2017.
 */

public class AttackPhaseUtility {

    private static final String TAG = AttackPhaseUtility.class.getSimpleName();
    private static AttackPhaseUtility instance;
    //TODO : check for capture territory ...
    private int numberOfDiceRolled;

    private AttackPhaseUtility(){

    }

    public static AttackPhaseUtility getInstance(){
        if(instance==null){
            instance = new AttackPhaseUtility();
        }
        return instance;
    }



    /**
     * Returns number of dice rolled
     *
     * @return
     */
    public int getNumberOfDiceRolled() {
        return numberOfDiceRolled;
    }

    /**
     * Set the number of dice rolled
     *
     * @param numberOfDiceRolled
     */
    public void setNumberOfDiceRolled(int numberOfDiceRolled) {
        this.numberOfDiceRolled = numberOfDiceRolled;
    }


    /**
     * Checks whether attack can be continued
     *
     * @param defenderTerritory
     * @return
     */
    public ConfigurableMessage canContinueAttackOnThisTerritory(Territory defenderTerritory) {
        if (defenderTerritory.getArmyCount() == 0)
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NO_ARMIES);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }

    /**
     * Checks whether player is attacking an already owned territory
     *
     * @param attackerTerritory
     * @param defenderTerritory
     * @return whether adjacent or not
     */

    public ConfigurableMessage isAdjacentTerritory(Territory attackerTerritory, Territory defenderTerritory) {
        if ((attackerTerritory.getNeighbourList().contains(defenderTerritory)) && (!attackerTerritory.getTerritoryOwner().equals(defenderTerritory.getTerritoryOwner()))) {
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NOT_ADJACENT_TERRITORY);
    }


    /**
     * Check for sufficient armies
     *
     * @param attackerTerritory
     * @return
     */
    public ConfigurableMessage hasSufficientArmies(Territory attackerTerritory) {
        if (attackerTerritory.getArmyCount() >= 2)
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.INSUFFUCIENT_ARMIES);
    }

    /**
     * Validate the attack
     *
     * @param attackerTerritory
     * @param defenderTerritory
     * @return
     */
    public ConfigurableMessage validateAttackBetweenTerritories(Territory attackerTerritory, Territory defenderTerritory) {
        ConfigurableMessage isAdjacenTerritories = isAdjacentTerritory(attackerTerritory, defenderTerritory);
        ConfigurableMessage hasSufficientArmiesForAttack = hasSufficientArmies(attackerTerritory);
        ConfigurableMessage canContinueAttack = canContinueAttackOnThisTerritory(defenderTerritory);

        if (isAdjacenTerritories.getMsgCode() == 0)
            return isAdjacenTerritories;
        else if (hasSufficientArmiesForAttack.getMsgCode() == 0) {
            return hasSufficientArmiesForAttack;
        } else if (canContinueAttack.getMsgCode() == 0) {
            return canContinueAttack;
        } else {
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
    }


    /**
     * The attack phase method
     *
     * @param attackerTerritory
     * @param defenderTerritory
     * @param attackerDice
     * @param defenderDice
     */
    public ConfigurableMessage attackPhase(Territory attackerTerritory, Territory defenderTerritory, int attackerDice, int defenderDice) {
        setNumberOfDiceRolled(attackerDice);
        if (attackerTerritory.getArmyCount() <= attackerDice) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACKER_DICE);
        } else if (defenderTerritory.getArmyCount() < defenderDice) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.CHOOSE_LESS_NUMBER_DICE);
        }
        String messageAttack = "Attacking City " + attackerTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(messageAttack);
        String messageDefender = "Attacked City " + defenderTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(messageDefender);

        //Validations are true for attacking and hence proceeding with logic
        //Load dice values
        List<Integer> attackerDiceValues = getRandomDiceValues(attackerDice);
        List<Integer> defenderDiceValues = getRandomDiceValues(defenderDice);

        try {
            Log.e(TAG, "Attacker Dice:" + attackerDiceValues.toString());
            Log.e(TAG, "Defender Dice:" + defenderDiceValues.toString());
        } catch (Exception e) {

        }
        int attackerDiceValue = 0, defenderDiceValue = 0;
        int attackerWonCounter = 0;

        Collections.sort(attackerDiceValues, Collections.reverseOrder());
        Collections.sort(defenderDiceValues, Collections.reverseOrder());

        String messageAttackDice = "Attacker Dice " + Arrays.toString(attackerDiceValues.toArray());
        PhaseViewModel.getInstance().addPhaseViewContent(messageAttackDice);
        String messageDefenderDice = "Defender Dice " + Arrays.toString(defenderDiceValues.toArray());
        PhaseViewModel.getInstance().addPhaseViewContent(messageDefenderDice);
        try {
            FileManager.getInstance().writeLog(messageAttack);
            FileManager.getInstance().writeLog(messageDefender);
            FileManager.getInstance().writeLog(messageAttackDice);
            FileManager.getInstance().writeLog(messageDefenderDice);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        while (defenderDiceValues.size() > 0) {
            attackerDiceValue = attackerDiceValues.get(0);
            defenderDiceValue = defenderDiceValues.get(0);

            //if attacker wins
            if (attackerDiceValue > defenderDiceValue) {
                defenderTerritory.setArmyCount(defenderTerritory.getArmyCount() - 1);
                attackerWonCounter++;
            } else {
                attackerTerritory.setArmyCount(attackerTerritory.getArmyCount() - 1);
                attackerWonCounter--;
            }
            attackerDiceValues.remove(0);
            defenderDiceValues.remove(0);
        }
        if (attackerWonCounter > 0) {
            String message = "Player" + attackerTerritory.getTerritoryOwner().getPlayerId() + " won";
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            try {
                FileManager.getInstance().writeLog(message);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ATTACKER_WON);
        } else {
            String message = "Player" + defenderTerritory.getTerritoryOwner().getPlayerId() + " won";
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            try {
                FileManager.getInstance().writeLog(message);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACKER_LOST);

        }
    }


    /**
     * This method is for conditions related to capturing a territory
     *
     * @param attackerTerritory
     * @param defenderTerritory
     * @param moveArmiesToCapturedTerritory
     * @return
     */
    public ConfigurableMessage captureTerritory(Territory attackerTerritory, Territory defenderTerritory, int moveArmiesToCapturedTerritory) {
        if (attackerTerritory.getArmyCount() - moveArmiesToCapturedTerritory == 0) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.LEAVE_ONE_ARMY);
        } else if (defenderTerritory.getArmyCount() == 0 && moveArmiesToCapturedTerritory < this.numberOfDiceRolled)
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.PLACE_MORE_ARMIES);
        else {
            attackerTerritory.setArmyCount(attackerTerritory.getArmyCount() - moveArmiesToCapturedTerritory);
            defenderTerritory.setArmyCount(moveArmiesToCapturedTerritory);
            defenderTerritory.setTerritoryOwner(attackerTerritory.getTerritoryOwner());
        }
        String message = "Player " + attackerTerritory.getTerritoryOwner().getPlayerId() + " has captured " + defenderTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(message);
        try {
            FileManager.getInstance().writeLog(message);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }


    /**
     * Generates random dice values depending on number of attacker/defender dice
     *
     * @param arraySize
     * @return randomly generated dice array
     */
    public static List<Integer> getRandomDiceValues(int arraySize) {
        List<Integer> randomArray = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            randomArray.add(1 + new Random().nextInt(6));
        }
        return randomArray;
    }

}
