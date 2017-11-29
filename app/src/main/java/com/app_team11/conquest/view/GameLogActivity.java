package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ListView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.controller.GameLogController;
import com.app_team11.conquest.utility.FileManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for logging game activity
 * Created by Abhishek on 03-Nov-17.
 */

public class GameLogActivity extends Activity {

    public ListView listGameLog;

    /**
     * Creating view of Game Log
     * @param savedInstanceState : When activity is reopened , this parameter is used for resuming to the resumed state
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);
        initializeView();
        GameLogController.getInstance().initialization(this);
    }

    /**
     * method to initialize the view for Game log Activity on the screen
     */
    private void initializeView() {
        listGameLog = (ListView) findViewById(R.id.list_log);
    }
}
