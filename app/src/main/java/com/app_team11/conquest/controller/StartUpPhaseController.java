package com.app_team11.conquest.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ReadMapUtility;
import com.app_team11.conquest.view.GamePlayActivity;

import java.util.Collections;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 * @version 1.0.0
 */


public class StartUpPhaseController implements SurfaceOnTouchListner {

    private Context context;
    private static StartUpPhaseController startUpPhaseController;
    private AsyncTask<Void, Void, Void> asyncTask;
    private Territory selectedTerritory;
    private boolean waitForSelectTerritory;
    private boolean needToStop=false;
    private StartUpPhaseController() {

    }

    public static StartUpPhaseController getInstance() {
        if (startUpPhaseController == null) {
            startUpPhaseController = new StartUpPhaseController();
        }
        return startUpPhaseController;
    }

    public StartUpPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    public void stopStartupPhase(){
        try {
            needToStop = true;
            asyncTask.notify();
            asyncTask.cancel(true);
        }catch (Exception ex){
            asyncTask.cancel(true);
        }
    }
    /**
     * method to initialize map for game play, set the number of players
     */
    private void getDataFromBundleAndInitializeMap() {
        String filePathToLoad = null;
        Intent intent = getActivity().getIntent();
        int noOfPlayer = 0;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                filePathToLoad = bundle.getString(Constants.KEY_FILE_PATH);
                noOfPlayer = bundle.getInt(Constants.KEY_NO_OF_PLAYER);
            }

        }
        if (!TextUtils.isEmpty(filePathToLoad) && noOfPlayer > 0) {
            getActivity().setMap(new ReadMapUtility(getActivity()).readFile(filePathToLoad));
            getActivity().getMap().addPlayerToGame(noOfPlayer);
            getActivity().initializePlayerAdapter();
        } else {
            Toast.makeText(context, "Invalid input please try again later !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void startStartUpPhase() {
        initializationStartupPhase();

    }

    /**
     * method to assign territories and armies to territories in the startup phase
     */
    public void initializationStartupPhase() {
        getDataFromBundleAndInitializeMap();
        randomlyAssignCountries();
        assignInitialArmy();
    }

    /**
     * method to randomly assign territories to each player
     */
    public void randomlyAssignCountries() {
        Collections.shuffle(getActivity().getMap().getTerritoryList());
        int territoryIndex = 0;
        while (territoryIndex < getActivity().getMap().getTerritoryList().size()) {
            for (Player player : getActivity().getMap().getPlayerList()) {
                Territory territory = getActivity().getMap().getTerritoryList().get(territoryIndex++);
                territory.setTerritoryOwner(player);
                territory.addArmyToTerr(1,false);
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
        asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (getActivity().getMap().getPlayerList().size() > 0) {
                    getActivity().setPlayerTurn(getActivity().getMap().getPlayerList().get(0));
                }
                Toast.makeText(context, "Touch on territory to add army", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                boolean needToAssignArmy = true;
                while (needToAssignArmy && !needToStop) {
                    needToAssignArmy = false;
                    for (final Player player : getActivity().getMap().getPlayerList()) {
                        try {

                            selectedTerritory = null;
                            waitForSelectTerritory = true;
                            getActivity().setPlayerTurn(player);

                            synchronized (asyncTask) {
                                asyncTask.wait();
                            }
                            if(needToStop){
                                break;
                            }
                            selectedTerritory.addArmyToTerr(1,false);
                            if (player.getAvailableArmyCount() > 0) {
                                needToAssignArmy = true;
                            }
                            getActivity().showMap();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getActivity().onStartupPhaseFinished();
            }
        };
        asyncTask.execute();
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
                waitForSelectTerritory = false;
                synchronized (asyncTask) {
                    asyncTask.notify();
                }
            } else {
                getActivity().toastMessageFromBackground("Place army on correct territory !!");
            }
        }

    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }
}
