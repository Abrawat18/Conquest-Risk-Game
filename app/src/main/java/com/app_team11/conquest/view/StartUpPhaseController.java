package com.app_team11.conquest.view;

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

import java.util.Collections;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class StartUpPhaseController implements SurfaceOnTouchListner {

    private Context context;
    private static StartUpPhaseController startUpPhaseController;
    private AsyncTask<Void, Void, Void> asyncTask;
    private Territory selectedTerritory;
    private boolean waitForSelectTerritory;
    private boolean notified;

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
        } else {
            Toast.makeText(context, "Invalid input please try again later !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void startStartUpPhase() {
        initializationStartupPhase();

    }

    public void initializationStartupPhase() {
        getDataFromBundleAndInitializeMap();
        randomlyAssignCountries();
        assignInitialArmy();
    }


    public void randomlyAssignCountries() {
        Collections.shuffle(getActivity().getMap().getTerritoryList());
        int territoryIndex = 0;
        while (territoryIndex < getActivity().getMap().getTerritoryList().size()) {
            for (Player player : getActivity().getMap().getPlayerList()) {
                Territory territory = getActivity().getMap().getTerritoryList().get(territoryIndex++);
                territory.setTerritoryOwner(player);
                territory.addArmyToTerr(1);
                if (territoryIndex == getActivity().getMap().getTerritoryList().size()) {
                    break;
                }
            }
        }
    }

    public void assignInitialArmy() {
        asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                boolean needToAssignArmy = true;
                while (needToAssignArmy) {
                    needToAssignArmy = false;
                    for (Player player : getActivity().getMap().getPlayerList()) {
                        try {
                            getActivity().setPlayerTurn(player);

                            selectedTerritory = null;
                            waitForSelectTerritory = true;

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Touch on territory to add army", Toast.LENGTH_SHORT).show();
                                }
                            });

                            notified = false;
                            synchronized (asyncTask) {
                                asyncTask.wait();
                            }
                            selectedTerritory.addArmyToTerr(1);
                            if (player.getAvailableArmyCount() > 0) {
                                needToAssignArmy = true;
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }

                return null;
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (waitForSelectTerritory) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            notified = true;
            synchronized (asyncTask) {
                asyncTask.notify();
            }
            waitForSelectTerritory = false;
        }
    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }
}
