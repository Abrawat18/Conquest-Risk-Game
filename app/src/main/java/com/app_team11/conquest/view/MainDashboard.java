package com.app_team11.conquest.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.app_team11.conquest.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 */

public class MainDashboard extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.btn_play_game).setOnClickListener(this);
        findViewById(R.id.btn_map_editor).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);

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
                    }
                })
                .setCancelText("Load Map")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void openSetting(){

    }
}
