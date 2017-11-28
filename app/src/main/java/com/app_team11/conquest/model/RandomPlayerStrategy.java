package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.AttackPhaseUtility;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.Collection;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public class RandomPlayerStrategy extends Observable implements PlayerStrategyListener {

    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {

        return null;
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        Collections.shuffle(gameMap.getTerrForPlayer(player));
        if (gameMap.getTerrForPlayer(player) != null && gameMap.getTerrForPlayer(player).size() > 0) {
            gameMap.getTerrForPlayer(player).get(0).setArmyCount(gameMap.getTerrForPlayer(player).get(0).getArmyCount() + (reinforcementType.getOtherTotalReinforcement()));

            if (reinforcementType.getMatchedTerritoryList() != null && reinforcementType.getMatchedTerritoryList().size()>0) {
                reinforcementType.getMatchedTerritoryList().get(0).setArmyCount(reinforcementType.getMatchedTerritoryList().get(0).getArmyCount() + reinforcementType.getMatchedTerrCardReinforcement());
            }
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.REINFORCEMENT_SUCCESS_STRATEGY);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.REINFORCEMENT_FAILED_STRATEGY);
    }


    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        int randomAttackTime = 1 + new Random().nextInt(Constants.RANDOM_NUMBER_ATTACK_TIMES);
        Collections.shuffle(gameMap.getTerrForPlayer(player));
        Collections.shuffle(gameMap.getTerrForPlayer(player).get(0).getNeighbourList());
        for (Territory defenderTerr : gameMap.getTerrForPlayer(player).get(0).getNeighbourList()) {
            if (AttackPhaseUtility.getInstance().validateAttackBetweenTerritories(gameMap.getTerrForPlayer(player).get(0), defenderTerr).getMsgCode() == Constants.MSG_SUCC_CODE) {
                while (randomAttackTime > 0) {
                    int attackerDice = 1;
                    int defenderDice = 1;
                    if (gameMap.getTerrForPlayer(player).get(0).getArmyCount() <= 3) {
                        attackerDice = 1 + new Random().nextInt(2);
                    } else {
                        attackerDice = 1 + new Random().nextInt(3);
                    }
                    if (defenderTerr.getArmyCount() <= 1) {
                        defenderDice = 1;
                    } else {
                        defenderDice = 1 + new Random().nextInt(2);
                    }

                    ConfigurableMessage resultCode = AttackPhaseUtility.getInstance().attackPhase(gameMap.getTerrForPlayer(player).get(0), defenderTerr, attackerDice, defenderDice);
                    if (resultCode.getMsgCode() == Constants.MSG_SUCC_CODE) {
                        if (defenderTerr.getArmyCount() == 0) {
                            AttackPhaseUtility.getInstance().captureTerritory(gameMap.getTerrForPlayer(player).get(0), defenderTerr, (attackerDice + new Random().nextInt(gameMap.getTerrForPlayer(player).get(0).getArmyCount() - attackerDice)));
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

        return null;
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        boolean fortificationFlag = false;
        Collections.shuffle(gameMap.getTerrForPlayer(player));
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            Collections.shuffle(territory.getNeighbourList());
            for (Territory neighbourTerritory : territory.getNeighbourList()) {
                if (neighbourTerritory.getTerritoryOwner().getPlayerId() == player.getPlayerId()) {
                    int fortifyRandomArmy = new Random().nextInt(neighbourTerritory.getArmyCount());
                    neighbourTerritory.setArmyCount(neighbourTerritory.getArmyCount() - fortifyRandomArmy);
                    territory.setArmyCount(territory.getArmyCount() + fortifyRandomArmy);
                    fortificationFlag = true;
                    break;
                }
            }
            if (fortificationFlag) {
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            }
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }
}
