package com.app_team11.conquest.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app_team11.conquest.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        openMainMenu();
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
