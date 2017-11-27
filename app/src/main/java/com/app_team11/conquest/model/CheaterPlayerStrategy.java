package com.app_team11.conquest.model;

import com.app_team11.conquest.interfaces.PlayerStrategyListener;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public class CheaterPlayerStrategy implements PlayerStrategyListener {

    @Override
    public void startupPhase(GameMap gameMap, Player player) {

    }

    @Override
    public void reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            territory.setArmyCount(2 * territory.getArmyCount());
        }
    }

    @Override
    public void attackPhase(GameMap gameMap, Player player) {
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            for (Territory neighbourTerr : territory.getNeighbourList()) {
                neighbourTerr.setTerritoryOwner(player);
            }
        }
    }

    @Override
    public void fortificationPhase(GameMap gameMap, Player player) {
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
    }
}
