package com.app_team11.conquest.model;

import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.Observable;

/**
 * This class is responsible for the implementation of the HumanPlayerStrategy
 * Created by Jaydeep on 11/27/2017.
 */

public class HumanPlayerStrategy extends Observable implements PlayerStrategyListener {
    /**
     * Human Player Strategy defined for the startup phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {

        return null;
    }
    /**
     * Human Player Strategy defined for the reinforcement phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {

        return null;
    }

    /**
     * Human Player Strategy defined for the attack phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {

        return null;
    }
    /**
     * Human Player Strategy defined for the fortification phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {

        return null;
    }
}
