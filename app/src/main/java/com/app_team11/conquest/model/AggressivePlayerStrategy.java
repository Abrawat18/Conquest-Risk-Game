package com.app_team11.conquest.model;

import com.app_team11.conquest.interfaces.PlayerStrategyListener;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Jaydeep on 11/27/2017.
 */

public class AggressivePlayerStrategy implements PlayerStrategyListener {
    @Override
    public void startupPhase(GameMap gameMap, Player player) {

    }

    @Override
    public void reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {

    }


    @Override
    public void attackPhase(GameMap gameMap, Player player) {

    }

    @Override
    public void fortificationPhase(GameMap gameMap, Player player) {
        Collections.sort(gameMap.getTerrForPlayer(player), new Comparator<Territory>() {
            @Override public int compare(Territory t1, Territory t2) {
                return t2.getArmyCount()- t1.getArmyCount();
            }
        });
        for(Territory territory : gameMap.getTerrForPlayer(player)){

        }
    }
}
