package com.app_team11.conquest.view;

import android.view.View;

import com.app_team11.conquest.R;
import com.app_team11.conquest.controller.MainDashboardController;
import com.app_team11.conquest.utility.FileManager;

/**
 * Created by RADHEY on 11/25/2017.
 */

public class GamePlayModeActivity{
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single_mode:
                MainDashboardController.getInstance().playGame();
                FileManager.getInstance().writeLog("Launching game play activity...");
                break;
            case R.id.btn_map_editor:
                MainDashboardController.getInstance().openMapEditor();
                FileManager.getInstance().writeLog("Launching Map editor activity...");
                break;
        }
    }
