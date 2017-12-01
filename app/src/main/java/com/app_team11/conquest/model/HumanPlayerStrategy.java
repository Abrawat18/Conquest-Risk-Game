package com.app_team11.conquest.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.util.List;
import java.util.Observable;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
        int territoryIndex = 0;
        while (territoryIndex < gameMap.getTerrForPlayer(player).size()) {
            for (Player playerTemp : gameMap.getPlayerList()) {
                Territory territory = gameMap.getTerritoryList().get(territoryIndex++);
                territory.setTerritoryOwner(player);
                territory.addArmyToTerr(1, false);
                if (territoryIndex == gameMap.getTerritoryList().size()) {
                    break;
                }
            }
        }
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }
    /**
     * Human Player Strategy defined for the reinforcement phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage reInforcementPhase(ReinforcementType reinforcementType, GameMap gameMap, Player player) {
        if (gameMap.getPlayerTurn().getOwnedCards().size() < 5 || (player.getOwnedCards() != null && player.getOwnedCards().size() == 3)) {
            reinforcementType = gameMap.getPlayerTurn().calcReinforcementArmy(gameMap, gameMap.getNoOfCardTradedCount(), player.getOwnedCards());
            List<Territory> needToPlaceTerrArmyList;
            if (reinforcementType.getMatchedTerrCardReinforcement() != 0) {
                needToPlaceTerrArmyList = reinforcementType.getMatchedTerritoryList();
                gameMap.getPlayerTurn().setAvailableCardTerrCount(reinforcementType.getMatchedTerrCardReinforcement());
            }
            if (null != player.getOwnedCards())
                gameMap.increaseCardTradedCount();

        } else if (player.getOwnedCards() == null) {
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.REINFORCEMENT_SUCCESS_STRATEGY);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.REINFORCEMENT_FAILED_STRATEGY);
    }

    /**
     * Human Player Strategy defined for the attack phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage attackPhase(final GameMap gameMap, Player player) {
        Context contextAttack=null;
        LinearLayout linearInput = new LinearLayout(contextAttack);
        linearInput.setOrientation(LinearLayout.VERTICAL);
        final EditText editAttackDice = new EditText(contextAttack);
        final EditText editDefenderDice = new EditText(contextAttack);
        editAttackDice.setHint("Attacker dice");
        editDefenderDice.setHint("Defender dice");
        linearInput.addView(editAttackDice);
        linearInput.addView(editDefenderDice);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(contextAttack, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Please Enter Number of Dice")
                .setConfirmText("Ok")
                .setCustomView(linearInput)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (!TextUtils.isEmpty(editAttackDice.getText().toString()) && !TextUtils.isEmpty(editDefenderDice.getText().toString())) {
                            int attackerDice;
                            int defenderDice;
                            attackerDice = Integer.parseInt(editAttackDice.getText().toString());
                            defenderDice = Integer.parseInt(editDefenderDice.getText().toString());
                            if (attackerDice > 3 || attackerDice <= 0) {
                                editAttackDice.setError("Attacker dice should not be more than 3 or less than 1");
                            } else if (defenderDice > 2 || defenderDice <= 0) {
                                editDefenderDice.setError("Defender dice should not be more than 2 or less than 1");
                            } else {
                                sweetAlertDialog.dismiss();
                                ConfigurableMessage resultAttackPhase = gameMap.getPlayerTurn().attackPhase(gameMap.getTerritoryList().get(0), gameMap.getTerritoryList().get(1), attackerDice, defenderDice);
                                boolean phaseWonFlag;
                                boolean isRequestForAttack;
                                if (resultAttackPhase.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                    phaseWonFlag = true;
                                    if (gameMap.getTerritoryList().get(0).getArmyCount() == 0) {
                                        //army movement selection
                                        isRequestForAttack=true;
                                    }
                                } else {
                                    isRequestForAttack = false;
                                }
                            }
                        } else {
                            editAttackDice.setError("Please Enter Number of Attacker Dice");
                            editDefenderDice.setError("Please Enter Number of Defender Dice");
                        }
                    }
                }).setCancelText("Cancel")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACK_SUCCESS_STRATEGY);
    }
    /**
     * Human Player Strategy defined for the fortification phase
     * @param gameMap : parameter which defines the game map
     * @param player : parameter which defines the player
     * @return ConfigurableMessage : returns the configurable message
     */
    @Override
    public ConfigurableMessage fortificationPhase(GameMap gameMap, Player player) {
        boolean waitForSelectTerritory=true;
        Territory fortificationFromTerritory;
        Territory fortificationToTerritory;
        if (waitForSelectTerritory) {
            if (gameMap.getTerrForPlayer(player).get(0).getTerritoryOwner() != null && gameMap.getTerrForPlayer(player).get(0).getTerritoryOwner().equals(gameMap.getPlayerTurn())) {
                if (gameMap.getTerrForPlayer(player).get(1).getTerritoryOwner() != null) {
                    fortificationFromTerritory = gameMap.getTerrForPlayer(player).get(0);
                } else {
                    fortificationToTerritory = gameMap.getTerrForPlayer(player).get(1);
                }
            }
            else{
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE);
            }
        }
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
    }
}
