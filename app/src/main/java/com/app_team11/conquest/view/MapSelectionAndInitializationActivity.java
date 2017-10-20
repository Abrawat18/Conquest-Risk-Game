package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.MapSelectionAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by RADHEY on 10/15/2017.
 * @version 1.0.0
 */

public class MapSelectionAndInitializationActivity extends Activity {

    private ListView listMenu;
    private File[] mapFiles;
    private String fromWhichActivity;
    private Intent intent = null;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selection);
        initializeView();
        initialization();

    }

    /**
     * method used to redirect the request if map as from map editor or game play
     */
    private void initialization() {
        Intent intent = getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle !=null){
                fromWhichActivity = bundle.getString(Constants.KEY_FROM);
            }
        }
        mapFiles = FileManager.getInstance().getFileFromRootMapDir();
        MapSelectionAdapter mapSelectionAdapter = new MapSelectionAdapter(mapFiles, this);
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

                final Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_FILE_PATH, mapFiles[position].getPath());



                if(fromWhichActivity.equals(Constants.VALUE_FROM_EDIT_MAP)){
                    intent = new Intent(MapSelectionAndInitializationActivity.this, MapEditorActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else if(fromWhichActivity.equals(Constants.VALUE_FROM_PLAY_GAME)){
                    intent = new Intent(MapSelectionAndInitializationActivity.this, GamePlayActivity.class);
                    final EditText editNoOfPlayer  = new EditText(MapSelectionAndInitializationActivity.this);
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapSelectionAndInitializationActivity.this, SweetAlertDialog.NORMAL_TYPE);
                    sweetAlertDialog.setTitleText(" Please Enter No of Player")
                            .setConfirmText("Ok")
                            .setCustomView(editNoOfPlayer)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if (!TextUtils.isEmpty(editNoOfPlayer.getText().toString())) {
                                        bundle.putInt(Constants.KEY_NO_OF_PLAYER,Integer.parseInt(editNoOfPlayer.getText().toString()));
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        sweetAlertDialog.dismiss();
                                    }else {
                                        editNoOfPlayer.setError("Please enter no of players");
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

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.KEY_FROM,fromWhichActivity);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fromWhichActivity = savedInstanceState.getString(Constants.KEY_FROM);
    }
}
