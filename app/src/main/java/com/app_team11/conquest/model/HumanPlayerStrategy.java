package com.app_team11.conquest.model;

import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

/**
 * Created by Jaydeep on 11/27/2017.
 */

public class HumanPlayerStrategy implements PlayerStrategyListener {
    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {

        return null;
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {

        return null;
    }


    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {

        return null;
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {

        return null;
    }
}
