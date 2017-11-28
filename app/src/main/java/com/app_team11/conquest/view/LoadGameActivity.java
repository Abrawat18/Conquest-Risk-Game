package com.app_team11.conquest.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app_team11.conquest.R;
import com.app_team11.conquest.utility.FileManager;

/**
 * Created by RADHEY on 11/28/2017.
 */

public class LoadGameActivity extends Activity{
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle =getIntent().getExtras();
        setContentView(R.layout.activity_load_game);




        FileManager.getInstance().writeLog("Map selected.");
    }
}
