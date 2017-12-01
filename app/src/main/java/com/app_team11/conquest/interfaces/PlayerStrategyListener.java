package com.app_team11.conquest.interfaces;

import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.ReinforcementType;
import com.app_team11.conquest.utility.ConfigurableMessage;

/**
 * This interface defines the listener for the type of player strategy
 * Created by Jaydeep on 11/26/2017.
 */

public interface PlayerStrategyListener{
    /**
     * Interface for the startupphase
     * @param gameMap parameter for the game map
     * @param player parameter for the plater
     * @return ConfigurableMessage custom message
     */
    ConfigurableMessage startupPhase(GameMap gameMap, Player player);

    /**
     * Interface for the reinforcement phase
     * @param gameMap parameter for the game map
     * @param player parameter for the plater
     * @return ConfigurableMessage custom message
     */
    ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player);

    /**
     * Interface for the attack phase
     * @param gameMap parameter for the game map
     * @param player parameter for the plater
     * @return ConfigurableMessage custom message
     */
    ConfigurableMessage attackPhase(GameMap gameMap, Player player);

    /**
     * Interface for the fortification phase
     * @param gameMap parameter for the game map
     * @param player parameter for the plater
     * @return ConfigurableMessage custom message
     */
    ConfigurableMessage fortificationPhase(GameMap gameMap, Player player);
}
