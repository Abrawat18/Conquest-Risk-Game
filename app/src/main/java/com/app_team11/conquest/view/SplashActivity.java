package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app_team11.conquest.R;
import com.app_team11.conquest.utility.FileManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main screen activity which shows logo for the game
 * Created by Jaydeep9101 on 06-Oct-17.
 */

public class SplashActivity extends Activity{
    /**
     * {@inheritDoc}
     *  This method is called on creation of the activity which shows the welcome screen
     * @param savedInstanceState When activity is reopened , this parameter is used for resuming to the resumed state
     *
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        openMainMenu();
        FileManager.getInstance().deleteLog();
    }

    /**
     * method to open the app splash screen
     */
    private void openMainMenu() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
