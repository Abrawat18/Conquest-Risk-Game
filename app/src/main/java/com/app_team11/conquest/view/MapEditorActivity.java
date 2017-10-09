package com.app_team11.conquest.view;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.ContinentAdapter;
import com.app_team11.conquest.adapter.TerritoryAdapter;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.MapManager;

import org.json.JSONException;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 */

//Todo : editor menu : 1. add contenent 2. add teritory 3. on terirtory  selection : show contenent or select contenet , neighbour


public class MapEditorActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private static final String TAG = MapEditorActivity.class.getSimpleName();
    private GameMap map;
    private Canvas canvas;
    private Button btnAddContenent;
    private Button btnAddTerritory;
    private Button btnAddCustomTerritory;
    private Button btnAddCustomContinent;
    private ListView listContinent;
    private ListView listSuggestContinent;
    private ListView listSuggestTerritory;
    private ListView listTerritory;
    private List<Continent> continentList;
    private List<Territory> territoryList;
    private List<Continent> continentSuggestList;
    private List<Territory> territorySuggestList;
    private LinearLayout linearTerritory;
    private LinearLayout linearAddTerritory;
    private LinearLayout linearContinent;
    private LinearLayout linearAddContinent;
    private SurfaceView surface;
    private boolean isWaitingForUserTouchOnAddTerritory = false;
    private boolean isWaitingForUserTouchOnAddContinent = false;
    private EditText editCustomContinent;
    private EditText editCustomTerritory;
    private Continent newContinent;
    private Territory newTerritory;
    private TerritoryAdapter territorySuggestAdapter;
    private ContinentAdapter continentSuggestAdapter;
    private ContinentAdapter continentAdapterr;
    private TerritoryAdapter territoryAdapterr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_editor);

        initializeView();

        try {
            initialization();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initializeView() {
        surface = (SurfaceView) findViewById(R.id.surface);
        surface.setOnTouchListener(this);
        surface.getHolder().addCallback(surfaceCallback);

        btnAddContenent = (Button) findViewById(R.id.btn_add_continent);
        btnAddCustomContinent = (Button) findViewById(R.id.btn_add_custom_continent);
        btnAddTerritory = (Button) findViewById(R.id.btn_add_territory);
        btnAddCustomTerritory = (Button) findViewById(R.id.btn_add_custom_territory);
        listContinent = (ListView) findViewById(R.id.list_continent);
        listTerritory = (ListView) findViewById(R.id.list_territory);
        listSuggestContinent = (ListView) findViewById(R.id.list_continent_suggest);
        listSuggestTerritory = (ListView) findViewById(R.id.list_territory_suggest);
        editCustomContinent = new EditText(this);
        editCustomTerritory = new EditText(this);
        linearContinent = (LinearLayout) findViewById(R.id.linear_continent);
        linearTerritory = (LinearLayout) findViewById(R.id.linear_territory);
        linearAddContinent = (LinearLayout) findViewById(R.id.linear_add_continent);
        linearAddTerritory = (LinearLayout) findViewById(R.id.linear_add_territory);
        btnAddContenent.setOnClickListener(this);
        btnAddCustomContinent.setOnClickListener(this);
        btnAddTerritory.setOnClickListener(this);
        btnAddCustomTerritory.setOnClickListener(this);
    }

    private void initialization() throws JSONException {
        setMap(new GameMap());
        getSuggestedContinentList();
        getSuggestedTerritoryList();
    }

    private void getSuggestedTerritoryList() throws JSONException {
        territorySuggestList = MapManager.getInstance().getTerritoryListFromFile(this);
        territorySuggestAdapter = new TerritoryAdapter(this, territorySuggestList);
        listSuggestTerritory.setAdapter(territorySuggestAdapter);

        territoryAdapterr  = new TerritoryAdapter(this,map.getTerritoryList());
        listTerritory.setAdapter(territoryAdapterr);

    }

    private void getSuggestedContinentList() throws JSONException {
        continentSuggestList = MapManager.getInstance().getContinentListFromFile(this);
        continentSuggestAdapter = new ContinentAdapter(this, continentSuggestList);
        listSuggestContinent.setAdapter(continentSuggestAdapter);

        continentAdapterr  = new ContinentAdapter(this,map.getContinentList());
        listContinent.setAdapter(continentAdapterr);

    }

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // Do some drawing when surface is ready
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.RED);
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawCircle(120f, 130f, 100f, paint);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

           holder.removeCallback(surfaceCallback);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    };

    /**
     * OnTouch motion event Listener
     *
     * @param v     : view
     * @param event : MotionEvent
     * @return      : Boolean
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        if (isWaitingForUserTouchOnAddTerritory && newTerritory == null) {
            if (!editCustomTerritory.getText().toString().equals("")) {
                newTerritory = new Territory(editCustomTerritory.getText().toString(),(int) x, (int) y,null);
            }
            map.addRemoveTerritoryFromMap(newTerritory, 'A');
            newTerritory = null;
            isWaitingForUserTouchOnAddTerritory = false;
        } else if (isWaitingForUserTouchOnAddContinent && newContinent == null) {

            if (!editCustomContinent.getText().toString().equals("")) {
                newContinent = new Continent(editCustomContinent.getText().toString(),1);
            }
            map.addRemoveContinentFromMap(newContinent, 'A');
            newContinent = null;
            isWaitingForUserTouchOnAddContinent = false;
        }
        hideAllLinearLayouts();
        linearContinent.setVisibility(View.VISIBLE);
        continentAdapterr.notifyDataSetChanged();
        territoryAdapterr.notifyDataSetChanged();
        return false;
    }


    private void setMap(GameMap map) {
        this.map = map;
    }


    private void createNewMap() {

    }

    private void loadMap() {

    }

    private void addTerritory() {
        hideAllLinearLayouts();
        linearAddTerritory.setVisibility(View.VISIBLE);
    }

    private void addCustomTerritory() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Please Enter Territory Name")
                .setConfirmText("Ok")
                .setCustomView(editCustomTerritory)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Toast.makeText(MapEditorActivity.this, "Touch on Map to add territory", Toast.LENGTH_SHORT).show();
                        isWaitingForUserTouchOnAddTerritory = true;
                    }
                })
                .show();
    }

    private void addContinent() {
        hideAllLinearLayouts();
        linearAddContinent.setVisibility(View.VISIBLE);
    }

    private void addCustomContinent() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText(" Please Enter Continent Name")
                .setConfirmText("Ok")
                .setCustomView(editCustomContinent)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Toast.makeText(MapEditorActivity.this, "Touch on Map to add continent", Toast.LENGTH_SHORT).show();
                        isWaitingForUserTouchOnAddContinent = true;
                    }
                })
                .show();

    }

    private void hideAllLinearLayouts() {
        linearAddContinent.setVisibility(View.GONE);
        linearContinent.setVisibility(View.GONE);
        linearAddTerritory.setVisibility(View.GONE);
        linearTerritory.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_continent:
                addContinent();
                break;
            case R.id.btn_add_territory:
                addTerritory();
                break;
            case R.id.btn_add_custom_continent:
                addCustomContinent();
                break;
            case R.id.btn_add_custom_territory:
                addCustomTerritory();
                break;
        }
    }
}
