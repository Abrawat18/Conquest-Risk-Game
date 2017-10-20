package com.app_team11.conquest.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 * @version 1.0.0
 *
 * */

public class FortificationPhaseController implements SurfaceOnTouchListner {


    private Context context;
    private static FortificationPhaseController fortificationPhaseController;
    private AsyncTask<Void, Void, Void> asyncTask;
    private Territory selectedTerritory;
    private boolean waitForSelectTerritory;
    private Territory fortificationFromTerritory;
    private Territory fortificationToTerritory;

    private FortificationPhaseController() {

    }

    /**
     * method to implement singleton architecture for class
     * @return FortificationController object
     */
    public static FortificationPhaseController getInstance() {
        if (fortificationPhaseController == null) {
            fortificationPhaseController = new FortificationPhaseController();
        }
        return fortificationPhaseController;
    }

    /**
     * method to set the context to controller
     * @param context
     * @return FortificationController Object
     */
    public FortificationPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    /**
     * method to initiate the fortification phase
     */
    public void startFortificationPhase() {
        if (getActivity().getMap().getPlayerList().size() > 0) {
            getActivity().setPlayerTurn(getActivity().getMap().getPlayerList().get(0));
            waitForSelectTerritory = true;
            initializeForNextPlayer();
        }
    }

    /**
     * method to stop the fortification phase
     */
    public void stopFortificationPhase() {
        waitForSelectTerritory = false;
    }

    /**
     * {@inheritDoc}
     * @param v view
     * @param event event
     */
    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (waitForSelectTerritory) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            if (selectedTerritory != null && selectedTerritory.getTerritoryOwner().equals(getActivity().getPlayerTurn())) {
                if (fortificationFromTerritory == null) {
                    fortificationFromTerritory = selectedTerritory;
                    getActivity().toastMessageFromBackground(Constants.FORTIFICATION_SELECT_TO_TERR);
                } else {
                    fortificationToTerritory = selectedTerritory;
                    askUserToMoveNoOfArmyToSelectedTerritory();
                }
            } else {
                getActivity().toastMessageFromBackground(Constants.FORTIFICATION_INCORRECT_TERRITORY);
                initializeForNextPlayer();
            }
        }
    }

    /**
     * method to bring a pop up to ask the user to enter the armies to be moved
     */
    private void askUserToMoveNoOfArmyToSelectedTerritory() {
        final EditText editNoOfArmy = new EditText(getActivity());
        editNoOfArmy.setInputType(InputType.TYPE_CLASS_NUMBER);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Enter no of Army to move")
                .setConfirmText("Ok")
                .setCustomView(editNoOfArmy)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (!TextUtils.isEmpty(editNoOfArmy.getText().toString())) {
                            int requestedToPlaceArmy = Integer.parseInt(editNoOfArmy.getText().toString());
                            ConfigurableMessage configurableMessage = fortificationFromTerritory.fortifyTerritory(fortificationToTerritory,getActivity().getPlayerTurn(),requestedToPlaceArmy);
                            if (configurableMessage.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                getActivity().setNextPlayerTurn();
                                initializeForNextPlayer();
                                getActivity().showMap();
                            } else {
                                getActivity().toastMessageFromBackground(configurableMessage.getMsgText());
                                initializeForNextPlayer();
                            }
                        }
                    }
                })
                .show();
    }

    /**
     * intitialize the fortification phase for the next player
     */
    private void initializeForNextPlayer() {
        fortificationFromTerritory  =null;
        fortificationToTerritory = null;
        getActivity().toastMessageFromBackground("Select from territory");
    }

    private GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

}
