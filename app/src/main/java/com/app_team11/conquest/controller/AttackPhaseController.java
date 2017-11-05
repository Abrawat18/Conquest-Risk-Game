package com.app_team11.conquest.controller;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.app_team11.conquest.interfaces.SurfaceOnTouchListner;
import com.app_team11.conquest.view.GamePlayActivity;

/**
 * Created by Jaydeep9101 on 05-Nov-17.
 */

public class AttackPhaseController implements SurfaceOnTouchListner {

    private static AttackPhaseController instance;
    private Context context;

    private AttackPhaseController() {

    }

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
     * @return
     */
    public AttackPhaseController setContext(Context context) {
        this.context = context;
        getActivity().setSurfaceOnTouchListner(this);
        return getInstance();
    }
    public void startAttackPhase() {
        initializationAttackPhase();

    }

    private void initializationAttackPhase() {

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
    }


    public GamePlayActivity getActivity() {
        return (GamePlayActivity) context;
    }

}
