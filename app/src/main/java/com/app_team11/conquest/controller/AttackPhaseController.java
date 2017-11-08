package com.app_team11.conquest.controller;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.PhaseViewModel;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.view.GamePlayActivity;
import com.app_team11.conquest.view.MapSelectionAndInitializationActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Attack phase Class - the main class of the game where actual conquest takes place
 * Created by Jaydeep9101 on 05-Nov-17.
 */

public class AttackPhaseController implements SurfaceOnTouchListner {

    private static AttackPhaseController instance;
    private Context context;
    private Territory fromTerritory;
    private Territory toTerritory;
    private boolean isRequestForAttack;
    private Territory selectedTerritory;
    private int attackerDice;
    private int defenderDice;
    private boolean phaseWonFlag;

    private AttackPhaseController() {

    }

    public void setPhaseWonFlag(boolean phaseWonFlag) {
        this.phaseWonFlag = phaseWonFlag;
    }

    public boolean isPhaseWonFlag() {
        return phaseWonFlag;
    }

    /**
     * Getting the instance of AtackPhaseController
     *
     * @return mainDashboardController
     */
    public static AttackPhaseController getInstance() {
        if (instance == null) {
            instance = new AttackPhaseController();
        }
        return instance;
    }


    /**
     * setting the context variable for reinforcement phase
     *
     * @param context
     * @return getInstance()
     */
    public AttackPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    /**
     * Writing log when the attack phase gets started
     * Initializing attack phase
     */
    public void startAttackPhase() {
        phaseWonFlag = false;
        if (!isAttackPossible()) {
            getActivity().toastMessageFromBackground("Attack not Possible. Changing to Fortification Phase.");
            getActivity().onAttackPhaseStopped();
            return;
        }
        PhaseViewModel.getInstance().clearString();
        PhaseViewModel.getInstance().addPhaseViewContent("Attack Phase Player :"+getActivity().getPlayerTurn().getPlayerId());
        initializationAttackPhase();
        FileManager.getInstance().writeLog("Attack phase initialized.");
        FileManager.getInstance().writeLog("Game Attack phase started.");

    }

    /**
     * Method to check if attack is possible
     *
     * @return
     */
    private boolean isAttackPossible() {
        for (Territory territory : getActivity().getMap().getTerrForPlayer(getActivity().getPlayerTurn())) {
            if (territory.getArmyCount() > 1) {
                for (Territory neighbourTerr : territory.getNeighbourList()) {
                    if (neighbourTerr.getTerritoryOwner().getPlayerId() != getActivity().getPlayerTurn().getPlayerId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Writing log for Initialization of attack phase
     * Initializing attack phase
     */
    private void initializationAttackPhase() {
        isRequestForAttack = true;
        fromTerritory = null;
        toTerritory = null;
        getActivity().toastMessageFromBackground("Select from territory");
    }


    /**
     * Getting the X and Y coordinate for the touch
     * {@inheritDoc}
     *
     * @param v
     * @param event
     */
    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (isRequestForAttack) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            if (selectedTerritory != null) {
                if (fromTerritory != null) {
                    toTerritory = selectedTerritory;
                    //Attack
                    ConfigurableMessage configurableMessage = getActivity().getPlayerTurn().validateAttackBetweenTerritories(fromTerritory, toTerritory);
                    if (configurableMessage.getMsgCode() == Constants.MSG_FAIL_CODE) {
                        getActivity().toastMessageFromBackground(configurableMessage.getMsgText());
                        initializationAttackPhase();
                    } else {
                        configureAttackPhase();
                    }


                } else if (fromTerritory == null && selectedTerritory.getTerritoryOwner().equals(getActivity().getPlayerTurn())) {
                    fromTerritory = selectedTerritory;
                    getActivity().toastMessageFromBackground(Constants.SELECT_TO_TERR);
                } else {
                    getActivity().toastMessageFromBackground(Constants.INCORRECT_TERRITORY);
                }
            }
        }

    }

    private void configureAttackPhase() {
        //Dice selection...
        LinearLayout linearInput = new LinearLayout(getActivity());
        linearInput.setOrientation(LinearLayout.VERTICAL);
        final EditText editAttackDice = new EditText(getActivity());
        final EditText editDefenderDice = new EditText(getActivity());
        editAttackDice.setHint("Attacker dice");
        editDefenderDice.setHint("Defender dice");
        linearInput.addView(editAttackDice);
        linearInput.addView(editDefenderDice);

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Please Enter Number of Dice")
                .setConfirmText("Ok")
                .setCustomView(linearInput)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (!TextUtils.isEmpty(editAttackDice.getText().toString()) && !TextUtils.isEmpty(editDefenderDice.getText().toString())) {
                            sweetAlertDialog.dismiss();
                            attackerDice = Integer.parseInt(editAttackDice.getText().toString());
                            defenderDice = Integer.parseInt(editDefenderDice.getText().toString());
                            if (attackerDice > 3 || attackerDice <= 0) {
                                editAttackDice.setError("Attacker dice should not be more than 3 or less than 1");
                            } else if (defenderDice > 2 || defenderDice <= 0) {
                                editDefenderDice.setError("Defender dice should not be more than 2 or less than 1");
                            } else {
                                ConfigurableMessage resultAttackPhase = getActivity().getPlayerTurn().attackPhase(fromTerritory, toTerritory, attackerDice, defenderDice);
                                getActivity().toastMessageFromBackground(resultAttackPhase.getMsgText());
                                getActivity().showMap();
                                if (resultAttackPhase.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                    phaseWonFlag = true;
                                    if (toTerritory.getArmyCount() == 0) {
                                        //army movement selection
                                        captureWonTerritoryAndSendArmy();
                                    }
                                } else {
                                    //select new attack
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


    }

    private void captureWonTerritoryAndSendArmy() {
        final EditText editNoOfArmy = new EditText(getActivity());
        editNoOfArmy.setInputType(InputType.TYPE_CLASS_NUMBER);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Enter no of Army to move")
                .setConfirmText("Ok")
                .setCustomView(editNoOfArmy)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (!TextUtils.isEmpty(editNoOfArmy.getText().toString())) {
                            int requestedToPlaceArmy = Integer.parseInt(editNoOfArmy.getText().toString());
                            if (requestedToPlaceArmy > 0) {
                                ConfigurableMessage configurableMessage = getActivity().getPlayerTurn().captureTerritory(fromTerritory, toTerritory, requestedToPlaceArmy);
                                getActivity().toastMessageFromBackground(configurableMessage.getMsgText());
                                if (configurableMessage.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                    getActivity().showMap();
                                    ConfigurableMessage configurableMessage1 = getActivity().getMap().playerWonTheGame(getActivity().getPlayerTurn());
                                    if (configurableMessage1.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                        getActivity().toastMessageFromBackground(configurableMessage1.getMsgText());
                                        endGame(getActivity().getPlayerTurn());
                                        //code to end game
                                    }
                                    sweetAlertDialog.dismiss();
                                } else {
                                    editNoOfArmy.setError("Invalid Input!!");
                                }
                            } else {
                                editNoOfArmy.setError("Invalid Input!!");
                            }
                        }
                    }
                })
                .show();
    }

    /**
     * Creation of context for the GamePlayActivity
     *
     * @return GamePlayActivity
     */
    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

    public void endGame(Player playerWon) {

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Game Ends! Player" + playerWon.getPlayerId() + " Won the game!!")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        getActivity().finish();
                    }
                })
                .show();
    }
}
