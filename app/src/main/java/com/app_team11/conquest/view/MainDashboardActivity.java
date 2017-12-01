package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.controller.MainDashboardController;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Main Dash board activity view
 * Created by Jaydeep9101 on 06-Oct-17.
 * @version 1.0.0
 */

public class MainDashboardActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 111;

    /**
     * {@inheritDoc}
     * @param savedInstanceState saves the current instance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.btn_play_game).setOnClickListener(this);
        findViewById(R.id.btn_map_editor).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);
        findViewById(R.id.btn_show_log).setOnClickListener(this);


        MainDashboardController.getInstance().initialization(this);
        FileManager.getInstance().writeLog("Dashboard has been launched!");
    }

    /**
     * {@inheritDoc}
     * @param v The view on which the click is done, that object of the view is called.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_game:
                MainDashboardController.getInstance().playGame();
                FileManager.getInstance().writeLog("Launching game play activity...");
                break;
            case R.id.btn_map_editor:
                MainDashboardController.getInstance().openMapEditor();
                FileManager.getInstance().writeLog("Launching Map editor activity...");
                break;
            case R.id.btn_settings:
                MainDashboardController.getInstance().openSetting();
                FileManager.getInstance().writeLog("Launching setting activity...");
                break;
            case R.id.btn_show_log:
                MainDashboardController.getInstance().openGameLog();
                break;
        }
    }

    /**
     * {@inheritDoc}
     * @param requestCode variable which stores the request code
     * @param permissions definition of permissions in this parameter
     * @param grantResults gives the results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Please provide this permission to store images into your sd card", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
