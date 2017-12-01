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

    /**
     * Default Constructor for attack phase
     */
    private AttackPhaseController() {

    }

    /**
     * Flag set for won phase
     * @param phaseWonFlag : flag is set when the player wins the phase
     */
    public void setPhaseWonFlag(boolean phaseWonFlag) {
        this.phaseWonFlag = phaseWonFlag;
    }

    /**
     * Flag checking for won phase
     * @return phaseWonFlag : flag is set when the player wins the phase
     */
    public boolean isPhaseWonFlag() {
        return phaseWonFlag;
    }

    /**
     * Getting the instance of AtackPhaseController
     * @return mainDashboardController : returns to the main dashboard controller
     */
    public static AttackPhaseController getInstance() {
        if (instance == null) {
            instance = new AttackPhaseController();
        }
        return instance;
    }


    /**
     * setting the context variable for reinforcement phase
     * @param context sets the reference
     * @return getInstance current instance
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
        if(!getActivity().getPlayerTurn().isActive()){
            FileManager.getInstance().writeLog("Game over for player:"+getActivity().getPlayerTurn().getPlayerId()+" - "+getActivity().getPlayerTurn().getPlayerStrategyType());
            getActivity().onAttackPhaseStopped();
            return;
        }
        if (!isAttackPossible()) {
            FileManager.getInstance().writeLog("Attack not possible for player:"+getActivity().getPlayerTurn().getPlayerId()+" - "+getActivity().getPlayerTurn().getPlayerStrategyType());
            getActivity().toastMessageFromBackground("Attack not Possible. Changing to Fortification Phase.");
            getActivity().onAttackPhaseStopped();
            return;
        }

        // If not human player automatically select random tradIn cards from own card list
        if (!(getActivity().getPlayerTurn().getPlayerStrategyType()).equals(Constants.HUMAN_PLAYER_STRATEGY)) {
            getActivity().getPlayerTurn().attackPhase(getActivity().getMap());
            getActivity().onAttackPhaseStopped();
            return;
        }

        phaseWonFlag = false;

        PhaseViewModel.getInstance().clearString();
        PhaseViewModel.getInstance().addPhaseViewContent("Attack Phase Player :"+getActivity().getPlayerTurn().getPlayerId());
        initializationAttackPhase();
        FileManager.getInstance().writeLog("Attack phase initialized.");
        FileManager.getInstance().writeLog("Game Attack phase started.");

    }

    /**
     * Method to check if attack is possible
     * @return whether attack is possible(true/false)
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
     * @param v The view on which the click is done, that object of the view is called.
     * @param event This parameter sets the motion event
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

    /**
     * Attack Phase Configuration
     */
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
                            attackerDice = Integer.parseInt(editAttackDice.getText().toString());
                            defenderDice = Integer.parseInt(editDefenderDice.getText().toString());
                            if (attackerDice > 3 || attackerDice <= 0) {
                                editAttackDice.setError("Attacker dice should not be more than 3 or less than 1");
                            } else if (defenderDice > 2 || defenderDice <= 0) {
                                editDefenderDice.setError("Defender dice should not be more than 2 or less than 1");
                            } else {
                                sweetAlertDialog.dismiss();
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

    /**
     * Method which capture the won territory and moves army
     */
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
     * @return GamePlayActivity : returns to the game play activity
     */
    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }


}