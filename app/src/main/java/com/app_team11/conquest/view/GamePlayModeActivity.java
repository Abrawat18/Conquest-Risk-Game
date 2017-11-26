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
 * Created by RADHEY on 11/25/2017.
 */

public class GamePlayModeActivity extends Activity implements View.OnClickListener {


    /**
     * {@inheritDoc}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
            initializeView();
    }

    private void initializeView() {
        findViewById(R.id.btn_single_mode).setOnClickListener(this);
        findViewById(R.id.btn_tournament_mode).setOnClickListener(this);

    }
    /**
     * {@inheritDoc}
     *
     * @param v view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single_mode:
                playSingleGame();
                FileManager.getInstance().writeLog("Launching Single Game Play");
            case R.id.btn_tournament_mode:
                playTournamentGame();
                FileManager.getInstance().writeLog("Launching Tournament Game Play");
        }


    }

    private void playTournamentGame() {

    }

    public void playSingleGame() {
        Intent intent = new Intent(this, GamePlayerTypeActivity.class);
        startActivity(intent);
        FileManager.getInstance().writeLog("Single Game Mode Started");
    }
}
