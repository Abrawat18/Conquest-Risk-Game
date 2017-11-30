package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public class CheaterPlayerStrategy extends Observable implements PlayerStrategyListener {

    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player startup phase started !! ");
        if (gameMap.getTerrForPlayer(player) != null && gameMap.getTerrForPlayer(player).size() > 0) {
            List<Territory> terrPlayerList = gameMap.getTerrForPlayer(player);
            Collections.shuffle(terrPlayerList);
            terrPlayerList.get(0).addArmyToTerr(1, false);
            FileManager.getInstance().writeLog("Cheater player startup phase ended !! ");
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FAILURE);
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player Reinforcement phase started !! ");
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            territory.setArmyCount(2 * territory.getArmyCount());
            FileManager.getInstance().writeLog("Territory " + territory.getTerritoryName().toString() + " has " + territory.getArmyCount() + " armies.");
        }
        FileManager.getInstance().writeLog("Cheater player Reinforcement phase ended !! ");
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }

    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player attack phase started !! ");
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                neighbourTerr.setTerritoryOwner(player);
                FileManager.getInstance().writeLog("Attacker Territory " + territory.getTerritoryName().toString() + " Conquers " + neighbourTerr.getTerritoryName().toString());
            }
        }
        FileManager.getInstance().writeLog("Cheater player attack phase ended !! ");
        ObserverType observerType = new ObserverType();
        observerType.setObserverType(ObserverType.WORLD_DOMINATION_TYPE);
        setChanged();
        notifyObservers(observerType);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        FileManager.getInstance().writeLog("Cheater player Fortification phase started !! ");
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            boolean isNeighbourOtherOwner = false;
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                if (neighbourTerr.getTerritoryOwner().getPlayerId() != player.getPlayerId()) {
                    isNeighbourOtherOwner = true;
                    FileManager.getInstance().writeLog("Territory fortified from --> " + neighbourTerr.getTerritoryName().toString() + " " +
                            "to " + territory.getTerritoryName().toString());
                    break;
                }
            }
            if (isNeighbourOtherOwner) {
                territory.setArmyCount(2 * territory.getArmyCount());
            }
        }
        FileManager.getInstance().writeLog("Cheater player Fortification phase ended !! ");
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }
}
