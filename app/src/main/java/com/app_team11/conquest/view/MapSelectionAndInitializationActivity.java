package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.MapSelectionAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.io.File;

/**
 * Created by RADHEY on 10/15/2017.
 */

public class MapSelectionAndInitializationActivity extends Activity {

    private ListView listMenu;
    private File[] mapFiles;
    private String fromWhichActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_map_selection);


        initializeView();
        initialization();

    }

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

    private void initializeView() {
        listMenu = (ListView) findViewById(R.id.list_map);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString(Constants.KEY_FILE_PATH, mapFiles[position].getPath());


                Intent intent = null;

                if(fromWhichActivity.equals(Constants.VALUE_FROM_EDIT_MAP)){
                    intent = new Intent(MapSelectionAndInitializationActivity.this, MapEditorActivity.class);
                }else if(fromWhichActivity.equals(Constants.VALUE_FROM_PLAY_GAME)){
                    intent = new Intent(MapSelectionAndInitializationActivity.this, GamePlayActivity.class);
                }
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }
}
