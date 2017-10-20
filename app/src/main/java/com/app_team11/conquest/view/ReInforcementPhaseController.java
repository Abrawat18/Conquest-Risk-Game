package com.app_team11.conquest.view;

import android.content.Context;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.model.Player;
import com.app_team11.conquest.model.Territory;

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

    private ReInforcementPhaseController() {

    }

    public static ReInforcementPhaseController getInstance() {
        if (reInforcementPhaseController == null) {
            reInforcementPhaseController = new ReInforcementPhaseController();
        }
        return reInforcementPhaseController;
    }

    public ReInforcementPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }

    public void startReInforceMentPhase() {
        if(getActivity().getMap().getPlayerList().size()>0) {
            getActivity().setPlayerTurn(getActivity().getMap().getPlayerList().get(0));
        }
    }

    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        getActivity().setNextPlayerTurn();


        /*if (waitForSelectTerritory) {
            selectedTerritory = getActivity().getTerritoryAtSelectedPoint((int) x, (int) y);
            if (selectedTerritory != null && selectedTerritory.getTerritoryOwner().equals(getActivity().getPlayerTurn())) {
                selectedTerritory.addArmyToTerr(1);
            } else {
                getActivity().toastMessageFromBackground("Place army on correct territory !!");
            }
        }*/
    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

}
