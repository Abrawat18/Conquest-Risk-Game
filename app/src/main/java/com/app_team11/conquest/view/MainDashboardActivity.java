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

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 * @version 1.0.0
 */

public class MainDashboardActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 111;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.btn_play_game).setOnClickListener(this);
        findViewById(R.id.btn_map_editor).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);


        MainDashboardController.getInstance().initialization(this);
    }

    /**
     * {@inheritDoc}
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_game:
                MainDashboardController.getInstance().playGame();
                break;
            case R.id.btn_map_editor:
                MainDashboardController.getInstance().openMapEditor();
                break;
            case R.id.btn_settings:
                MainDashboardController.getInstance().openSetting();
                break;
        }
    }

    /**
     * {@inheritDoc}
     * @param requestCode
     * @param permissions
     * @param grantResults
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
