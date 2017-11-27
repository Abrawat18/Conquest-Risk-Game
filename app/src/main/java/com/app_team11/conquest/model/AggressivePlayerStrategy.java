package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Jaydeep on 11/27/2017.
 */

public class AggressivePlayerStrategy implements PlayerStrategyListener {
    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }


    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        boolean fortificationFlag = false;
        Collections.sort(gameMap.getTerrForPlayer(player), new Comparator<Territory>() {
            @Override public int compare(Territory t1, Territory t2) {
                return t2.getArmyCount()- t1.getArmyCount();
            }
        });
        for(Territory territory : gameMap.getTerrForPlayer(player)){
            Collections.sort(territory.getNeighbourList(), new Comparator<Territory>() {
                @Override public int compare(Territory t1, Territory t2) {
                    return t2.getArmyCount()- t1.getArmyCount();
                }
            });
            for(Territory neighbourTerr : territory.getNeighbourList()) {
                if (neighbourTerr.getTerritoryOwner() == player && neighbourTerr.getArmyCount()>1){
                    territory.setArmyCount(territory.getArmyCount()+(neighbourTerr.getArmyCount()-1));
                    neighbourTerr.setArmyCount(1);
                    fortificationFlag = true;
                    break;
                }
            }
            if(fortificationFlag){
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            }
            }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }
}
