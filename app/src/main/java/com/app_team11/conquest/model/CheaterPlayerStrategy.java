package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.Collections;
import java.util.Observable;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public class CheaterPlayerStrategy extends Observable implements PlayerStrategyListener {

    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {
        if(gameMap.getTerrForPlayer(player)!=null && gameMap.getTerrForPlayer(player).size()>0) {
            Collections.shuffle(gameMap.getTerrForPlayer(player));
            gameMap.getTerrForPlayer(player).get(0).addArmyToTerr(1,false);
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE,Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE,Constants.FAILURE);
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            territory.setArmyCount(2 * territory.getArmyCount());
        }
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE,Constants.SUCCESS);
    }

    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                neighbourTerr.setTerritoryOwner(player);
            }
        }
        ObserverType observerType = new ObserverType();
        observerType.setObserverType(ObserverType.WORLD_DOMINATION_TYPE);
        setChanged();
        notifyObservers(observerType);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE,Constants.SUCCESS);
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            boolean isNeighbourOtherOwner = false;
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                if (neighbourTerr.getTerritoryOwner().getPlayerId() != player.getPlayerId()) {
                    isNeighbourOtherOwner = true;
                    break;
                }
            }
            if (isNeighbourOtherOwner) {
                territory.setArmyCount(2 * territory.getArmyCount());
            }
        }
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE,Constants.SUCCESS);
    }
}
