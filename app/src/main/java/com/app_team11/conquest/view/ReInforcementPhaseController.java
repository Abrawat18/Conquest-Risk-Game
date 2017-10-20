package com.app_team11.conquest.view;

import android.content.Context;
import android.os.AsyncTask;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.ReinforcementType;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class ReInforcementPhaseController implements SurfaceOnTouchListner {

    private Context context;
    private static StartUpPhaseController startUpPhaseController;
    private AsyncTask<Void, Void, Void> asyncTask;
    private Territory selectedTerritory;
    private boolean waitForSelectTerritory;
    private static ReInforcementPhaseController reInforcementPhaseController;
    private int needToPlaceArmy;

    private ReInforcementPhaseController() {

    }

    /**
     * singleton implementation for Reinforcement controller
     * @return returns the class object
     */
    public static ReInforcementPhaseController getInstance() {
        if (reInforcementPhaseController == null) {
            reInforcementPhaseController = new ReInforcementPhaseController();
        }
        return reInforcementPhaseController;
    }

    /**
     * setting the context variable for reinforcement phase
     * @param context
     * @return
     */
    public ReInforcementPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    /**
     *method used to start the reinforcement phase in the play game
     */
    public void startReInforceMentPhase() {
        if (getActivity().getMap().getPlayerList().size() > 0) {
            getActivity().setPlayerTurn(getActivity().getMap().getPlayerList().get(0));
            waitForSelectTerritory = true;
            calculateReinforcementArmyForPlayer();
        }
    }

    /**
     * method to stop the reinforcement phase
     */
    public void stopReInforceMentPhase() {
        waitForSelectTerritory = false;
    }

    /**
     * {@inheritDoc}
     * @param v
     * @param event
     */
    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (waitForSelectTerritory) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            if (selectedTerritory != null && selectedTerritory.getTerritoryOwner().equals(getActivity().getPlayerTurn())) {
                if(needToPlaceArmy > 0) {
                    askUserToPlaceNoOfArmyToSelectedTerritory();
                }
            } else {
                getActivity().toastMessageFromBackground("Place army on correct territory !!");
            }
        }
    }

    /**
     * method to take inputs from user for placing the army in selected territory in reinforcement phase
     */
    private void askUserToPlaceNoOfArmyToSelectedTerritory() {
        final EditText editNoOfArmy = new EditText(getActivity());
        editNoOfArmy.setInputType(InputType.TYPE_CLASS_NUMBER);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Please Enter Territory Name")
                .setConfirmText("Ok")
                .setCustomView(editNoOfArmy)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (!TextUtils.isEmpty(editNoOfArmy.getText().toString())) {
                            int requestedToPlaceArmy = Integer.parseInt(editNoOfArmy.getText().toString());
                            if (needToPlaceArmy >= requestedToPlaceArmy) {
                                ConfigurableMessage configurableMessage = selectedTerritory.addArmyToTerr(requestedToPlaceArmy);
                                if (configurableMessage.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                    needToPlaceArmy -= requestedToPlaceArmy;
                                }
                                if (needToPlaceArmy == 0) {
                                    getActivity().setNextPlayerTurn();
                                    calculateReinforcementArmyForPlayer();
                                }
                                Toast.makeText(getActivity(), "Select territory to place Army :" + needToPlaceArmy, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Invalid army place count", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .show();
    }

    /**
     * method to call the function for calculation of reinforcement army on the start of the player turn
     */
    private void calculateReinforcementArmyForPlayer() {
        ReinforcementType reinforcementType = getActivity().getPlayerTurn().calcReinforcementArmy(getActivity().getMap(), false, getActivity().getMap().getNoOfCardTradedCount(), null);
        needToPlaceArmy = reinforcementType.getOtherTotalReinforcement();
        getActivity().getPlayerTurn().setAvailableArmyCount(getActivity().getPlayerTurn().getAvailableArmyCount()+needToPlaceArmy);
        Toast.makeText(getActivity(), "Place Army:" + needToPlaceArmy, Toast.LENGTH_SHORT).show();
    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

}
