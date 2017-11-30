package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jaydeep on 11/27/2017.
 */

public class BenevolentPlayerStrategy implements PlayerStrategyListener {
    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Benevolent player startup phase started !! ");
        if (gameMap.getTerrForPlayer(player) != null && gameMap.getTerrForPlayer(player).size() > 0) {
            List<Territory> territoryList = sortList(gameMap.getTerrForPlayer(player),true);
            territoryList.get(0).addArmyToTerr(1, false);
            FileManager.getInstance().writeLog("Benevolent player startup phase ended !! ");
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FAILURE);
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Benevolent player Reinforcement phase started !! ");
        if (gameMap.getTerrForPlayer(player) != null) {
            sortList(gameMap.getTerrForPlayer(player), true).get(0).setArmyCount(sortList(gameMap.getTerrForPlayer(player), true).get(0).getArmyCount() + (reinforcementType.getOtherTotalReinforcement()));
            if (reinforcementType.getMatchedTerritoryList() != null) {
                sortList(reinforcementType.getMatchedTerritoryList(), true);
                reinforcementType.getMatchedTerritoryList().get(0).setArmyCount(reinforcementType.getMatchedTerritoryList().get(0).getArmyCount() + reinforcementType.getMatchedTerrCardReinforcement());
            }
            FileManager.getInstance().writeLog("Benevolent player Reinforcement phase ended !! ");
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.REINFORCEMENT_SUCCESS_STRATEGY);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.REINFORCEMENT_FAILED_STRATEGY);
    }

    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ATTACK_SUCCESS_STRATEGY);
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Benevolent player Fortification phase started !! ");
        boolean fortificationFlag = false;
        if (gameMap.getTerrForPlayer(player) != null) {
            for (Territory territory : sortList(gameMap.getTerrForPlayer(player), true)) {
                if (territory.getNeighbourList() != null) {
                    for (Territory neighbourTerr : sortList(territory.getNeighbourList(), false)) {
                        if (neighbourTerr.getTerritoryOwner().getPlayerId() == player.getPlayerId()) {
                            int diffAddTerrArmyCount = (neighbourTerr.getArmyCount() - territory.getArmyCount()) / 2;
                            territory.setArmyCount(territory.getArmyCount() + diffAddTerrArmyCount);
                            FileManager.getInstance().writeLog("Territory fortified from --> " + neighbourTerr.getTerritoryName().toString() + " " +
                                    "to " + territory.getTerritoryName().toString());
                            neighbourTerr.setArmyCount(neighbourTerr.getArmyCount() - diffAddTerrArmyCount);
                            fortificationFlag = true;
                            break;
                        }
                    }
                    if (fortificationFlag) {
                        FileManager.getInstance().writeLog("Benevolent player Fortification phase ended !! ");
                        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
                    }
                }
            }
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }

    public List<Territory> sortList(List<Territory> terrList, final boolean isWeakestSorted) {

        Collections.sort(terrList, new Comparator<Territory>() {
            @Override
            public int compare(Territory t1, Territory t2) {
                if (isWeakestSorted) {
                    return t1.getArmyCount() - t2.getArmyCount();
                } else {
                    return t2.getArmyCount() - t1.getArmyCount();

                }
            }
        });
        return terrList;
    }
}
