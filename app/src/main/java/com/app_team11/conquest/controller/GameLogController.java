package com.app_team11.conquest.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.app_team11.conquest.adapter.GameLogAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.view.GameLogActivity;
import com.app_team11.conquest.view.MainDashboardActivity;
import com.app_team11.conquest.view.MapEditorActivity;
import com.app_team11.conquest.view.MapSelectionAndInitializationActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Abhishek on 03-Nov-17.
 *
 * @version 1.0.0
 */

public class GameLogController {
    private static GameLogController gameLogController;
    private GameLogAdapter gameLogAdapter;

    private Context context;

    private GameLogController() {
    }

    public static GameLogController getInstance() {
        if (gameLogController == null) {
            gameLogController = new GameLogController();
        }
        return gameLogController;
    }

    public void initialization(Context context) {

        this.context = context;
        List<String> list = FileManager.getInstance().readLog();
        gameLogAdapter = new GameLogAdapter(getActivity(), list);
        getActivity().listGameLog.setAdapter(gameLogAdapter);
    }

    public GameLogActivity getActivity() {
        return (GameLogActivity) context;
    }
}
