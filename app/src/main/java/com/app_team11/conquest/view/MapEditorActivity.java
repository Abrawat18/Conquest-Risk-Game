package com.app_team11.conquest.view;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.controller.MapEditorController;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.utility.MathUtility;

import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * This class is responsible for map editor activity view
 * Created by Jaydeep9101 on 06-Oct-17.
 * @version 1.0.0
 */

public class MapEditorActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    public Canvas canvas;
    private Button btnAddContenent;
    private Button btnAddTerritory;
    private Button btnAddCustomTerritory;
    private Button btnAddCustomContinent;
    public ListView listContinent;
    public ListView listSuggestContinent;
    public ListView listSuggestTerritory;
    public ListView listTerritory;
    public LinearLayout linearTerritory;
    public LinearLayout linearAddTerritory;
    private LinearLayout linearContinent;
    public LinearLayout linearAddContinent;
    public SurfaceView surface;

    /**
     * {@inheritDoc}
     *  This method is called on creation of the activity which allows user to edit the map
     * @param savedInstanceState When activity is reopened , this parameter is used for resuming to the resumed state
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_editor);

        if (savedInstanceState == null) {
            initializeView();
            try {
                MapEditorController.getInstance().initialization(this);
                FileManager.getInstance().writeLog("Map Editor activity has been launched.");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method to initialize the objects required for the start of map editor
     */
    private void initializeView() {
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);

        btnAddContenent = (Button) findViewById(R.id.btn_add_continent);
        btnAddCustomContinent = (Button) findViewById(R.id.btn_add_custom_continent);
        btnAddTerritory = (Button) findViewById(R.id.btn_add_territory);
        btnAddCustomTerritory = (Button) findViewById(R.id.btn_add_custom_territory);
        findViewById(R.id.btn_show_log).setOnClickListener(this);
        listContinent = (ListView) findViewById(R.id.list_continent);
        listTerritory = (ListView) findViewById(R.id.list_territory);
        listSuggestContinent = (ListView) findViewById(R.id.list_continent_suggest);
        listSuggestTerritory = (ListView) findViewById(R.id.list_territory_suggest);
        linearContinent = (LinearLayout) findViewById(R.id.linear_continent);
        linearTerritory = (LinearLayout) findViewById(R.id.linear_territory);
        linearAddContinent = (LinearLayout) findViewById(R.id.linear_add_continent);
        linearAddTerritory = (LinearLayout) findViewById(R.id.linear_add_territory);
        btnAddContenent.setOnClickListener(this);
        btnAddCustomContinent.setOnClickListener(this);
        btnAddTerritory.setOnClickListener(this);
        btnAddCustomTerritory.setOnClickListener(this);

        listSuggestContinent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MapEditorController.getInstance().onClickSuggestContinent(position);

            }
        });

        listContinent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                MapEditorController.getInstance().onLongClickContinent(position);
                return false;

            }

        });

        listSuggestTerritory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MapEditorController.getInstance().onClickSuggestTerritory(position);

            }
        });

        listTerritory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                MapEditorController.getInstance().onLongClickTerritory(position);

                return false;
            }

        });

        listContinent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MapEditorController.getInstance().onClickContinent(position);
            }
        });
    }

    /**
     * Surface call back for map editor
     */
    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            MapEditorController.getInstance().showMap();
        }

        /**
         * Surface destroyed surface holder
         * @param holder this parameter holds the view
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            holder.removeCallback(surfaceCallback);
        }

        /**
         * View for change surface
         * @param holder this parameter holds the view
         * @param format this parameter defines the format
         * @param width this parameter defines the width of the surface
         * @param height this parameter defines the height of the surface
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    };

    /**
     * OnTouch motion event Listener
     * @param v     : Initialize the view based on the layout defined in XML
     * @param event : MotionEvent
     * @return : Boolean - either true or false
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        MapEditorController.getInstance().onTouch(v, event);
        return false;

    }

    /**
     * Shows the toast message
     * @param msg : message is displayed
     */
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * method to hide all the layouts to allow the new layout to be loaded
     */
    public void hideAllLinearLayouts() {
        linearAddContinent.setVisibility(View.GONE);
        linearContinent.setVisibility(View.GONE);
        linearAddTerritory.setVisibility(View.GONE);
        linearTerritory.setVisibility(View.GONE);
    }

    /**
     *  This method is called when click event is passed.
     * @param v The view on which the click is done, that object of the view is called.
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_continent:
                MapEditorController.getInstance().addContinent();
                break;
            case R.id.btn_add_territory:
                MapEditorController.getInstance().addTerritory();
                break;
            case R.id.btn_add_custom_continent:
                MapEditorController.getInstance().addCustomContinent();
                break;
            case R.id.btn_add_custom_territory:
                MapEditorController.getInstance().addCustomTerritory();
                break;
            case R.id.btn_show_log:
                MapEditorController.getInstance().openGameLog();
                break;
        }
    }

    /**
     * {@inheritDoc}
     * When activity is paused or minimized this method is called to resume back
     */
    @Override
    protected void onResume() {
        super.onResume();
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);
        MapEditorController.getInstance().showMap();
    }

    /**
     * {@inheritDoc}
     * This method is called when the back button is pressed and it navigates back to the previous screen
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (linearAddContinent.getVisibility() == View.VISIBLE) {
            hideAllLinearLayouts();
            linearContinent.setVisibility(View.VISIBLE);
        } else if (linearTerritory.getVisibility() == View.VISIBLE) {
            hideAllLinearLayouts();
            linearContinent.setVisibility(View.VISIBLE);
        } else if (linearAddTerritory.getVisibility() == View.VISIBLE) {
            hideAllLinearLayouts();
            linearTerritory.setVisibility(View.VISIBLE);
        } else if (linearContinent.getVisibility() == View.VISIBLE) {
            FileManager.getInstance().writeLog("Saving map...");
            MapEditorController.getInstance().saveToMap();
            FileManager.getInstance().writeLog("Map Saved.");

        }
    }
}
