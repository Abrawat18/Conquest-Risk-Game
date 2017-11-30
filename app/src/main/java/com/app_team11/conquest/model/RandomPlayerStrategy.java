package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.AttackPhaseUtility;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * This class is responsible for the strategy for the random player
 * Created by Jaydeep on 11/26/2017.
 */

public class RandomPlayerStrategy extends Observable implements PlayerStrategyListener {
    /**
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Random player startup phase started !! ");
        if (gameMap.getTerrForPlayer(player) != null && gameMap.getTerrForPlayer(player).size() > 0) {
            List<Territory> terrPlayerList = gameMap.getTerrForPlayer(player);
            Collections.shuffle(terrPlayerList);
            terrPlayerList.get(0).addArmyToTerr(1, false);
            FileManager.getInstance().writeLog("Random player startup phase ended !! ");
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FAILURE);
    }

    /**
     * Class for the implementation of reinforcement phase in the random player strategy
     * @param reinforcementType : defines the type of reinforcement
     * @param gameMap : defines teh game map
     * @param player : defines the player
     * @return ConfigurableMessage  : returns the configurable message
     */
    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Random player Reinforcement phase started !! ");
        List<Territory> terrPlayerList = gameMap.getTerrForPlayer(player);
        Collections.shuffle(terrPlayerList);
        if (gameMap.getTerrForPlayer(player) != null && gameMap.getTerrForPlayer(player).size() > 0) {
            terrPlayerList.get(0).setArmyCount(terrPlayerList.get(0).getArmyCount() + (reinforcementType.getOtherTotalReinforcement()));

            if (reinforcementType.getMatchedTerritoryList() != null && reinforcementType.getMatchedTerritoryList().size() > 0) {
                reinforcementType.getMatchedTerritoryList().get(0).setArmyCount(reinforcementType.getMatchedTerritoryList().get(0).getArmyCount() + reinforcementType.getMatchedTerrCardReinforcement());
            }
            FileManager.getInstance().writeLog("Random player Reinforcement phase ended !! ");
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.REINFORCEMENT_SUCCESS_STRATEGY);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.REINFORCEMENT_FAILED_STRATEGY);
    }

    /**
     * Class for the implementation of attack phase in the random player strategy
     * @param gameMap : defines the game map
     * @param player : defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        ConfigurableMessage resultCode=null;
        FileManager.getInstance().writeLog("Random player attack phase started !! ");
        int randomAttackTime = 1 + new Random().nextInt(Constants.RANDOM_NUMBER_ATTACK_TIMES);
        List<Territory> terrPlayerList = gameMap.getTerrForPlayer(player);
        Collections.shuffle(terrPlayerList);
        List<Territory> neighbourTerrList = terrPlayerList.get(0).getNeighbourList();
        Collections.shuffle(neighbourTerrList);
        for (Territory defenderTerr : neighbourTerrList) {
            if (AttackPhaseUtility.getInstance().validateAttackBetweenTerritories(terrPlayerList.get(0), defenderTerr).getMsgCode() == Constants.MSG_SUCC_CODE) {
                while (randomAttackTime > 0) {
                    int attackerDice = 1;
                    int defenderDice = 1;
                    if (terrPlayerList.get(0).getArmyCount() == 2) {
                        attackerDice = 1;
                    } else if (terrPlayerList.get(0).getArmyCount() <= 3) {
                        attackerDice = 1 + new Random().nextInt(2);
                    } else {
                        attackerDice = 1 + new Random().nextInt(3);
                    }
                    if (defenderTerr.getArmyCount() <= 1) {
                        defenderDice = 1;
                    } else {
                        defenderDice = 1 + new Random().nextInt(2);
                    }

                    resultCode = AttackPhaseUtility.getInstance().attackPhase(terrPlayerList.get(0), defenderTerr, attackerDice, defenderDice);
                    if (resultCode.getMsgCode() == Constants.MSG_SUCC_CODE) {
                        if (defenderTerr.getArmyCount() == 0) {
                            AttackPhaseUtility.getInstance().captureTerritory(terrPlayerList.get(0), defenderTerr, (attackerDice + new Random().nextInt(terrPlayerList.get(0).getArmyCount() - attackerDice)));
                            ObserverType observerType = new ObserverType();
                            observerType.setObserverType(ObserverType.WORLD_DOMINATION_TYPE);
                            setChanged();
                            notifyObservers(observerType);
                            break;
                        }
                    }
                    randomAttackTime--;
                }
                break;
            }


        }
        FileManager.getInstance().writeLog("Random player attack phase ended !! ");
        return resultCode;
    }

    /**
     * Class defined the configurable message for fortification phase
     * @param gameMap : defines the game map
     * @param player : defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Random player fortification phase started !! ");
        boolean fortificationFlag = false;
        List<Territory> terrPlayerList = gameMap.getTerrForPlayer(player);
        Collections.shuffle(terrPlayerList);
        for (Territory territory : terrPlayerList) {
            List<Territory> neighbourTerrList = territory.getNeighbourList();
            Collections.shuffle(neighbourTerrList);
            for (Territory neighbourTerritory : neighbourTerrList) {
                if (neighbourTerritory.getTerritoryOwner().getPlayerId() == player.getPlayerId()) {
                    int fortifyRandomArmy = new Random().nextInt(neighbourTerritory.getArmyCount());
                    neighbourTerritory.setArmyCount(neighbourTerritory.getArmyCount() - fortifyRandomArmy);
                    territory.setArmyCount(territory.getArmyCount() + fortifyRandomArmy);
                    fortificationFlag = true;
                    break;
                }
            }
            if (fortificationFlag) {
                FileManager.getInstance().writeLog("Random player fortification phase ended !! ");
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            }
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }
}
