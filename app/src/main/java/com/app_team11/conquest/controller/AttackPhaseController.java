package com.app_team11.conquest.controller;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.view.GamePlayActivity;

/**
 * Attack phase Class - the main class of the game where actual conquest takes place
 * Created by Jaydeep9101 on 05-Nov-17.
 */

public class AttackPhaseController implements SurfaceOnTouchListner {

    private static AttackPhaseController instance;
    private Context context;

    private AttackPhaseController() {

    }

    /**
     * Getting the instance of AtackPhaseController
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
        initializationAttackPhase();
        FileManager.getInstance().writeLog("Game Attack phase started.");

    }

    /**
     * Writing log for Initialization of attack phase
     * Initializing attack phase
     */
    private void initializationAttackPhase() {
        FileManager.getInstance().writeLog("Attack phase initialized.");
    }


    /**
     * Getting the X and Y coordinate for the touch
     * {@inheritDoc}
     * @param v
     * @param event
     */
    @Override
    public void onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
    }

    /**
     * Creation of context for the GamePlayActivity
     * @return GamePlayActivity
     */
    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

}
