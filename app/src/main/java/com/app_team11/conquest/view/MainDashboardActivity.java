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
import com.app_team11.conquest.global.Constants;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 */

public class MainDashboardActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.btn_play_game).setOnClickListener(this);
        findViewById(R.id.btn_map_editor).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);

        checkPermission();
    }

    public void checkPermission(){
        boolean hasPermission = (ContextCompat.checkSelfPermission(MainDashboardActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(MainDashboardActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MainDashboardActivity.REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_game:
                playGame();
                break;
            case R.id.btn_map_editor:
                openMapEditor();
                break;
            case R.id.btn_settings:
                openSetting();
                break;
        }
    }

    private void playGame(){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_FROM,Constants.VALUE_FROM_PLAY_GAME);
        Intent intent = new Intent(MainDashboardActivity.this,MapSelectionAndInitializationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void openMapEditor(){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Map Editor")
                .setContentText("Select Option")
                .setConfirmText("Create Map")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(MainDashboardActivity.this,MapEditorActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelText("Load Map")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.KEY_FROM,Constants.VALUE_FROM_EDIT_MAP);
                        Intent intent = new Intent(MainDashboardActivity.this,MapSelectionAndInitializationActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void openSetting(){

    }



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
