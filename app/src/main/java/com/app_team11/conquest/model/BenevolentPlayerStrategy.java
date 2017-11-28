package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

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
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        sortList(gameMap.getTerrForPlayer(player), true);
        gameMap.getTerrForPlayer(player).get(0).setArmyCount(gameMap.getTerrForPlayer(player).get(0).getArmyCount() + (reinforcementType.getOtherTotalReinforcement()));

        if (reinforcementType.getMatchedTerritoryList() != null) {
            sortList(reinforcementType.getMatchedTerritoryList(), true);
            reinforcementType.getMatchedTerritoryList().get(0).setArmyCount(reinforcementType.getMatchedTerritoryList().get(0).getArmyCount() + reinforcementType.getMatchedTerrCardReinforcement());

        }
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.REINFORCEMENT_SUCCESS_STRATEGY);
    }

    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ATTACK_SUCCESS_STRATEGY);
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        boolean fortificationFlag = false;
        sortList(gameMap.getTerrForPlayer(player), true);
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            sortList(territory.getNeighbourList(), false);
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                if (neighbourTerr.getTerritoryOwner().getPlayerId() == player.getPlayerId()) {
                    int diffAddTerrArmyCount = (neighbourTerr.getArmyCount() - territory.getArmyCount()) / 2;
                    territory.setArmyCount(territory.getArmyCount() + diffAddTerrArmyCount);
                    neighbourTerr.setArmyCount(neighbourTerr.getArmyCount() - diffAddTerrArmyCount);
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

    public void sortList(List<Territory> terrList, final boolean isWeakestSorted) {

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
    }
}
