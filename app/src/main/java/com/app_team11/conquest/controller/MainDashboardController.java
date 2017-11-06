package com.app_team11.conquest.controller;

import android.app.Activity;
import android.content.Context;
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
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.view.GameLogActivity;
import com.app_team11.conquest.view.GamePlayActivity;
import com.app_team11.conquest.view.MainDashboardActivity;
import com.app_team11.conquest.view.MapEditorActivity;
import com.app_team11.conquest.view.MapSelectionAndInitializationActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Abhishek on 31-Oct-17.
 *
 * @version 1.0.0
 */

public class MainDashboardController {
    private static final int REQUEST_WRITE_STORAGE = 111;
    private static MainDashboardController mainDashboardController;
    private Context context;

    private MainDashboardController() {
    }

    public static MainDashboardController getInstance() {
        if (mainDashboardController == null) {
            mainDashboardController = new MainDashboardController();
        }
        return mainDashboardController;
    }

    public void initialization(Context context) {

        this.context = context;
        checkPermission();
    }


    /**
     * method to check for access/permissions to file manager in the system
     */
    public void checkPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MainDashboardController.REQUEST_WRITE_STORAGE);
        }
    }


    /**
     * method called on click of play game
     */
    public void playGame() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_FROM, Constants.VALUE_FROM_PLAY_GAME);
        Intent intent = new Intent(getActivity(), MapSelectionAndInitializationActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
        FileManager.getInstance().writeLog("Game Play started !!");
    }

    public void openGameLog() {
        Intent intent = new Intent(getActivity(), GameLogActivity.class);
        getActivity().startActivity(intent);
    }
    /**
     * method called on click of edit map
     */
    public void openMapEditor() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Map Editor")
                .setContentText("Select Option")
                .setConfirmText("Create Map")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(getActivity(), MapEditorActivity.class);
                        getActivity().startActivity(intent);
                    }
                })
                .setCancelText("Load Map")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.KEY_FROM, Constants.VALUE_FROM_EDIT_MAP);
                        Intent intent = new Intent(getActivity(), MapSelectionAndInitializationActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                })
                .show();
    }

    public void openSetting() {

    }

    public MainDashboardActivity getActivity() {
        return (MainDashboardActivity) context;
    }


}
