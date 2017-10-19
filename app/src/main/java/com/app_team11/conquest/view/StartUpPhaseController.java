package com.app_team11.conquest.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.utility.ReadMapUtility;

import java.util.List;

/**
 * Created by Jaydeep9101 on 19-Oct-17.
 */

public class StartUpPhaseController implements SurfaceOnTouchListner{

    private Context context;
    private static StartUpPhaseController startUpPhaseController;

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
            getActivity().setMap(new ReadMapUtility().readFile(filePathToLoad));
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
        assignArmyBasedOnNoOfPlayer();
    }

    private void assignArmyBasedOnNoOfPlayer() {


    }

    private void initializePlayers() {

    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

    @Override
    public void onTouch(View v, MotionEvent event) {

    }
}
