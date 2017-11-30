package com.app_team11.conquest.interfaces;

import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.ReinforcementType;
import com.app_team11.conquest.utility.ConfigurableMessage;

/**
 * This interface defines the listner for the type of player strategy
 * Created by Jaydeep on 11/26/2017.
 */

public interface PlayerStrategyListener{
    ConfigurableMessage startupPhase(GameMap gameMap, Player player);

    ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player);

    ConfigurableMessage attackPhase(GameMap gameMap, Player player);

    ConfigurableMessage fortificationPhase(GameMap gameMap, Player player);
}
