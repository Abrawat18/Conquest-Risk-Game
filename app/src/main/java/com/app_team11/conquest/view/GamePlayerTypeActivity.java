package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity gives option to select the type of strategy for players
 * Created by RADHEY on 11/25/2017.
 */

public class GamePlayerTypeActivity extends Activity implements View.OnClickListener {
    ArrayList<String> playerTypeList = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;

    private Bundle bundle;

    /**
     * {@inheritDoc}
     *  This method is called on creation of the activity which allows user to choose the player type
     * @param savedInstanceState When activity is reopened , this parameter is used for resuming to the resumed state
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ListView lv;
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_player_type);
        findViewById(R.id.btn_add_player_type).setOnClickListener(this);
        findViewById(R.id.btn_next_mapselection).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.list_playertype);
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                playerTypeList);

        lv.setAdapter(arrayAdapter);
    }

    /**
     *  This method is called when click event is passed.
     * @param v The view on which the click is done, that object of the view is called.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_player_type:
                Spinner mySpinner = (Spinner) findViewById(R.id.planets_spinner);
                String text = mySpinner.getSelectedItem().toString();
                playerTypeList.add(text);
                arrayAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_next_mapselection:
                int playerSize = playerTypeList.size();
                Intent intent = new Intent(this, MapSelectionAndInitializationActivity.class);
                if (((playerSize >= 2 && playerSize <= 4) && (bundle.getString(Constants.KEY_FROM_GAME_MODE).equals(Constants.FROM_TOURNAMENT_MODE_VALUE))) || ((playerSize >= 2 && playerSize <= 6) && (bundle.getString(Constants.KEY_FROM_GAME_MODE).equals(Constants.FROM_SINGLE_MODE_VALUE)))) {
                    bundle.putStringArrayList(Constants.KEY_PLAYER_LIST, playerTypeList);
                    bundle.putInt(Constants.KEY_NO_OF_PLAYER, playerSize);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    FileManager.getInstance().writeLog("Map Selection Started");
                } else {
                    if (bundle.getString(Constants.KEY_FROM_GAME_MODE).equals(Constants.FROM_TOURNAMENT_MODE_VALUE)) {
                        Toast.makeText(this, "Players must be between 2 and 4 for tournament", Toast.LENGTH_SHORT).show();
                    } else if (bundle.getString(Constants.KEY_FROM_GAME_MODE).equals(Constants.FROM_SINGLE_MODE_VALUE)) {
                        Toast.makeText(this, "Players must be between 2 and 6 for Single Mode", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
