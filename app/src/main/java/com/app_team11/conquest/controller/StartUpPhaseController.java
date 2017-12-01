package com.app_team11.conquest.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.PhaseViewModel;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.utility.GamePhaseManager;
import com.app_team11.conquest.utility.ReadMapUtility;
import com.app_team11.conquest.view.GamePlayActivity;

import java.util.Collections;
import java.util.List;

/**
 * Startup phase implementation
 * Created by Jaydeep9101 on 19-Oct-17.
 *
 * @version 1.0.0
 */


public class StartUpPhaseController implements SurfaceOnTouchListner {

    private Context context;
    private static StartUpPhaseController startUpPhaseController;
    private AsyncTask<Void, Void, Void> asyncTask;
    private Territory selectedTerritory;
    private boolean waitForSelectTerritory;
    private boolean needToStop = false;

    /**
     * Default Constructor
     */
    private StartUpPhaseController() {

    }

    /**
     * Singleton for StartUp Phase
     * @return startUpPhaseController : This parameter returns the start up phase controller
     */
    public static StartUpPhaseController getInstance() {
        if (startUpPhaseController == null) {
            startUpPhaseController = new StartUpPhaseController();
        }
        return startUpPhaseController;
    }

    /**
     * Context setting for StartUp Phase
     * @param context : Context reference for the start up phase controller.
     */
    public StartUpPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    /**
     * Stop start up phase method
     */
    public void stopStartupPhase() {
        getActivity().onStartupPhaseFinished();
    }


    /**
     * StartUp Phase initialization
     */
    public void startStartUpPhase() {
        initializationStartupPhase();
        FileManager.getInstance().writeLog("Game Startup phase started.");
    }

    /**
     * method to assign territories and armies to territories in the startup phase
     */
    public void initializationStartupPhase() {
        randomlyAssignCountries();
        if (getActivity().getMap().getPlayerList().size() > 0) {
            FileManager.getInstance().writeLog("Assigning initial armies to each territory on start up...");
            getActivity().setPlayerTurn(getActivity().getMap().getPlayerList().get(0));
        }
        getActivity().worldDominationViewSet();
        getActivity().updateDominationView();
        getActivity().getMap().assignCards();
        assignInitialArmy();

    }



    /**
     * method to randomly assign territories to each player
     */
    public void randomlyAssignCountries() {
        Collections.shuffle(getActivity().getMap().getTerritoryList());
        FileManager.getInstance().writeLog("Randomly assigning territories to each player!!");
        int territoryIndex = 0;
        while (territoryIndex < getActivity().getMap().getTerritoryList().size()) {
            for (Player player : getActivity().getMap().getPlayerList()) {
                Territory territory = getActivity().getMap().getTerritoryList().get(territoryIndex++);
                territory.setTerritoryOwner(player);
                territory.addArmyToTerr(1, false);
                if (territoryIndex == getActivity().getMap().getTerritoryList().size()) {
                    break;
                }
            }
        }
    }

    /**
     * method to assign initial armies to each territory on start up
     */
    public void assignInitialArmy() {

        if (!isArmyLeftToAssignForAnyPlayers()) {
            waitForSelectTerritory = false;
            stopStartupPhase();
            return;
        }

        getActivity().showMap();
        if(!getActivity().getPlayerTurn().getPlayerStrategyType().equals(Constants.HUMAN_PLAYER_STRATEGY)){
            waitForSelectTerritory=false;
            getActivity().getPlayerTurn().startupPhase(getActivity().getMap());
            getActivity().setNextPlayerTurn();
            assignInitialArmy();
        }else {
            waitForSelectTerritory = true;
        }
    }

    /**
     * Method to check if army is left to assign for any players
     * @return boolean : true or false is returned based on the outcome of checks being done in the method
     */
    public boolean isArmyLeftToAssignForAnyPlayers() {
        for (final Player player : getActivity().getMap().getPlayerList()) {
            if (player.getAvailableArmyCount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param v : Initialize the view based on the layout defined in XML
     * @param event : Defines the event for the on touch
     */
    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (waitForSelectTerritory) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            if (selectedTerritory != null && selectedTerritory.getTerritoryOwner().equals(getActivity().getPlayerTurn())) {
                PhaseViewModel.getInstance().clearString();
                PhaseViewModel.getInstance().addPhaseViewContent("StartUp Phase Player :" + getActivity().getPlayerTurn().getPlayerId());
                selectedTerritory.addArmyToTerr(1, false);
                getActivity().setNextPlayerTurn();
                assignInitialArmy();
            } else {
                getActivity().toastMessageFromBackground("Place army on correct territory !!");
            }
        }

    }


    /**
     * GamePlay Activity Method
     * @return GamePlayActivity : Returns the game play activity when this getter is called.
     */
    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }
}
