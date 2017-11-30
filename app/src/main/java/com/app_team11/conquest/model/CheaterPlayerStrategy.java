package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.util.Collections;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public class CheaterPlayerStrategy implements PlayerStrategyListener {

    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player startup phase started !! ");
        if(gameMap.getTerrForPlayer(player)!=null && gameMap.getTerrForPlayer(player).size()>0) {
            Collections.shuffle(gameMap.getTerrForPlayer(player));
            gameMap.getTerrForPlayer(player).get(0).addArmyToTerr(1,false);
            FileManager.getInstance().writeLog("Cheater player startup phase ended !! ");
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE,Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE,Constants.FAILURE);
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player Reinforcement phase started !! ");
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            territory.setArmyCount(2 * territory.getArmyCount());
        }
        FileManager.getInstance().writeLog("Cheater player Reinforcement phase ended !! ");
        return null;
    }

    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player attack phase started !! ");
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                neighbourTerr.setTerritoryOwner(player);
            }
        }
        FileManager.getInstance().writeLog("Cheater player attack phase ended !! ");
        return null;
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player Fortification phase started !! ");
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
        FileManager.getInstance().writeLog("Cheater player Fortification phase ended !! ");
        return null;
    }
}
