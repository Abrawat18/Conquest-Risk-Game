package com.app_team11.conquest.interfaces;

import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public interface GamePhaseListner {
    void startupPhase(GameMap gameMap, Player player);
    void reInforcementPhase(GameMap gameMap, Player player);
    void attackPhase(GameMap gameMap, Player player);
    void fortificationPhase(GameMap gameMap, Player player);
}
