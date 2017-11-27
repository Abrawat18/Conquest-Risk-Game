package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Jaydeep on 11/26/2017.
 */

public class RandomPlayerStrategy implements PlayerStrategyListener {

    @Override
    public ConfigurableMessage startupPhase(GameMap gameMap, Player player) {

        return null;
    }

    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        Collections.shuffle(gameMap.getTerrForPlayer(player));
        gameMap.getTerrForPlayer(player).get(0).setArmyCount(gameMap.getTerrForPlayer(player).get(0).getArmyCount()+(reinforcementType.getOtherTotalReinforcement()));

        if(reinforcementType.getMatchedTerritoryList()!=null) {
            reinforcementType.getMatchedTerritoryList().get(0).setArmyCount(reinforcementType.getMatchedTerritoryList().get(0).getArmyCount()+reinforcementType.getMatchedTerrCardReinforcement());
        }
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.REINFORCEMENT_SUCCESS_STRATEGY);
    }


    @Override
    public ConfigurableMessage attackPhase(GameMap gameMap, Player player) {


        return null;
    }

    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        boolean fortificationFlag = false;
        Collections.shuffle(gameMap.getTerrForPlayer(player));
        for (Territory territory : gameMap.getTerrForPlayer(player)) {
            Collections.shuffle(territory.getNeighbourList());
            for (Territory neighbourTerritory : territory.getNeighbourList()) {
                if (neighbourTerritory.getTerritoryOwner().getPlayerId() == player.getPlayerId()) {
                    int fortifyRandomArmy = new Random().nextInt(neighbourTerritory.getArmyCount());
                    neighbourTerritory.setArmyCount(neighbourTerritory.getArmyCount() - fortifyRandomArmy);
                    territory.setArmyCount(territory.getArmyCount() + fortifyRandomArmy);
                    fortificationFlag = true;
                    break;
                }
            }
            if(fortificationFlag){
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            }
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE_STRATEGY);
    }
}
