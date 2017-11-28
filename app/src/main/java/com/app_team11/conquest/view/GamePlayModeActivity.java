package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.app_team11.conquest.R;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

/**
 * This class helps in navigating to the selected mode of
 * Created by RADHEY on 11/25/2017.
 */

public class GamePlayModeActivity extends Activity implements View.OnClickListener {


    private Bundle bundle;

    /**
     * {@inheritDoc}
     *  This method is called on creation of the activity which allows user to choose the game play mode
     * @param savedInstanceState When activity is reopened , this parameter is used for resuming to the resumed state
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_game_mode);
        initializeView();
    }

    /**
     * Initialize the view based on the layout defined in XML
     */
    private void initializeView() {
        findViewById(R.id.btn_single_mode).setOnClickListener(this);
        findViewById(R.id.btn_tournament_mode).setOnClickListener(this);

    }

    /**
     *  This method is called when click event is passed.
     * @param v The view on which the click is done, that object of the view is called.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single_mode:
                playSingleGame();
                FileManager.getInstance().writeLog("Launching Single Game Play");
                break;
            case R.id.btn_tournament_mode:
                playTournamentMode();
                FileManager.getInstance().writeLog("Launching Tournament Game Play");
                break;
        }


    }

    /**
     * Method is called when single mode for playing game is selected
     */
    public void playSingleGame() {
        Intent intent = new Intent(this, GamePlayerTypeActivity.class);
        bundle.putString(Constants.KEY_FROM_GAME_MODE, Constants.FROM_SINGLE_MODE_VALUE);
        intent.putExtras(bundle);
        startActivity(intent);
        FileManager.getInstance().writeLog("Single Game Mode Started");
    }

    /**
     * Method is called when tournament mode for playing game is selected
     */
    public void playTournamentMode() {
        Intent intent = new Intent(this, GamePlayerTypeActivity.class);
        bundle.putString(Constants.KEY_FROM_GAME_MODE, Constants.FROM_TOURNAMENT_MODE_VALUE);
        intent.putExtras(bundle);
        startActivity(intent);
        FileManager.getInstance().writeLog("Tournament Game Mode Started");
    }
}
