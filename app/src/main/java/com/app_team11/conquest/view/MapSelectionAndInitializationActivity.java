package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.MapSelectionAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.MapFile;
import com.app_team11.conquest.utility.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Map Selection and initialization
 * Created by RADHEY on 10/15/2017.
 *
 * @version 1.0.0
 */

public class MapSelectionAndInitializationActivity extends Activity {

    private ListView listMenu;
    private List<MapFile> mapFiles;
    Button btnPlayGame;
    private String fromWhichActivity;
    private String gameMode;
    private Intent intent = null;
    private Bundle bundle;
    private MapSelectionAdapter mapSelectionAdapter;

    /**
     * On create method for map selection
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_map_selection);
        initializeView();
        initialization();
        FileManager.getInstance().writeLog("Map selected.");

    }

    /**
     * method used to redirect the request if map as from map editor or game play
     */
    private void initialization() {
        if (bundle != null) {
            fromWhichActivity = bundle.getString(Constants.KEY_FROM);
            gameMode = bundle.getString(Constants.KEY_FROM_GAME_MODE);
        }
        if (gameMode.equals(Constants.FROM_SINGLE_MODE_VALUE)) {
            btnPlayGame.setVisibility(View.GONE);
        }
        mapFiles = FileManager.getInstance().getFileFromRootMapDir();
        mapSelectionAdapter = new MapSelectionAdapter(mapFiles, this);
        listMenu.setAdapter(mapSelectionAdapter);
    }

    /**
     * method to initialize the map according to the source of request
     */
    private void initializeView() {
        listMenu = (ListView) findViewById(R.id.list_map);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fromWhichActivity.equals(Constants.VALUE_FROM_EDIT_MAP)) {
                    bundle.putString(Constants.KEY_FILE_PATH, mapFiles.get(position).getMapFiles().getPath());
                    intent = new Intent(MapSelectionAndInitializationActivity.this, MapEditorActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else if (fromWhichActivity.equals(Constants.VALUE_FROM_PLAY_GAME) && (bundle.getString(Constants.KEY_FROM_GAME_MODE).equals(Constants.FROM_SINGLE_MODE_VALUE))) {
                    bundle.putString(Constants.KEY_FILE_PATH, mapFiles.get(position).getMapFiles().getPath());
                    intent = new Intent(MapSelectionAndInitializationActivity.this, GamePlayActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (fromWhichActivity.equals(Constants.VALUE_FROM_PLAY_GAME) && (bundle.getString(Constants.KEY_FROM_GAME_MODE).equals(Constants.FROM_TOURNAMENT_MODE_VALUE))) {
                    mapFiles.get(position).setSelected(!mapFiles.get(position).isSelected());
                    mapSelectionAdapter.notifyDataSetChanged();
                }
            }
        });
        btnPlayGame = (Button) findViewById(R.id.btn_play_game);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureTournamentMode();
            }
        });
    }

    public void configureTournamentMode() {
        LinearLayout linearInput = new LinearLayout(this);
        linearInput.setOrientation(LinearLayout.VERTICAL);
        final EditText editNumberOfGames = new EditText(this);
        final EditText editNumberOfDraws = new EditText(this);
        editNumberOfGames.setHint("number of games");
        editNumberOfDraws.setHint("number of draws");
        linearInput.addView(editNumberOfGames);
        linearInput.addView(editNumberOfDraws);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapSelectionAndInitializationActivity.this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Tournament Configuration")
                .setConfirmText("Ok")
                .setCustomView(linearInput)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (!TextUtils.isEmpty(editNumberOfDraws.getText().toString()) && !TextUtils.isEmpty(editNumberOfGames.getText().toString())) {
                            if ((Integer.parseInt(editNumberOfGames.getText().toString()) <= 5) && (Integer.parseInt(editNumberOfGames.getText().toString()) > 2) && (Integer.parseInt(editNumberOfDraws.getText().toString()) >= 10) && (Integer.parseInt(editNumberOfDraws.getText().toString()) <= 50)) {
                                sweetAlertDialog.dismiss();
                                bundle.putInt(Constants.KEY_NUMBER_GAMES, Integer.parseInt(editNumberOfGames.getText().toString()));
                                bundle.putInt(Constants.KEY_NUMBER_DRAWS, Integer.parseInt(editNumberOfDraws.getText().toString()));
                                ArrayList<String> mapFilePathList = new ArrayList<String>();
                                for (MapFile mapFile : mapFiles) {
                                    if (mapFile.isSelected()) {
                                        mapFilePathList.add(mapFile.getMapFiles().getPath());
                                    }
                                }
                                if (mapFilePathList.size() >= 1 && mapFilePathList.size() <= 5) {
                                    bundle.putStringArrayList(Constants.KEY_SELECTED_MAP_LIST, mapFilePathList);
                                    intent = new Intent(MapSelectionAndInitializationActivity.this, GamePlayActivity.class);
                                    intent.putExtras(bundle);
                                    //startActivity(intent);
                                } else {
                                    Toast.makeText(MapSelectionAndInitializationActivity.this, "Number of maps should be between 1 to 5", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MapSelectionAndInitializationActivity.this, "Invalid No of Draws or Games!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MapSelectionAndInitializationActivity.this, "Please enter Draws and Games", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setCancelText("Cancel")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }


    /**
     * Instance saving option
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.KEY_FROM, fromWhichActivity);
    }

    /**
     * Instance state restore
     *
     * @param savedInstanceState
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fromWhichActivity = savedInstanceState.getString(Constants.KEY_FROM);
    }
}
