package com.app_team11.conquest.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.app_team11.conquest.R;
import com.app_team11.conquest.adapter.ContinentAdapter;
import com.app_team11.conquest.adapter.TerritoryAdapter;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;
import com.app_team11.conquest.utility.MapManager;
import com.app_team11.conquest.utility.MathUtility;
import com.app_team11.conquest.utility.ReadMapUtility;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 */

//Todo : editor menu : 1. add continent 2. add territory 3. on terrirtory  selection : show continent or select continent , neighbour


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
    private List<Continent> continentSuggestList;
    private List<Territory> territorySuggestList;
    private LinearLayout linearTerritory;
    private LinearLayout linearAddTerritory;
    private LinearLayout linearContinent;
    private LinearLayout linearAddContinent;
    private LinearLayout myLayout;
    private SurfaceView surface;
    private boolean isWaitingForUserTouchOnAddTerritory = false;
    private boolean isWaitingForUserTouchOnAddContinent = false;
    private EditText editCustomContinent;
    private EditText editCustomTerritory;
    private EditText editContinentScore;
    private Continent newContinent;
    private Territory newTerritory;
    private TerritoryAdapter territorySuggestAdapter;
    private ContinentAdapter continentSuggestAdapter;
    private ContinentAdapter continentAdapter;
    private TerritoryAdapter territoryAdapter;
    private Continent selectedContinent;
    private boolean isRequestToAddNeighbour;
    private Territory neighbourTerritoryFrom;
    private Territory neighbourTerritoryTo;
    String filePathToLoad = null;
    private Continent contColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_editor);

        if(savedInstanceState==null) {
            initializeView();
            try {
                initialization();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

        listSuggestContinent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapEditorActivity.this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("Are you sure you want to add " + continentSuggestList.get(position).getContName() + "?")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                ConfigurableMessage config = map.addRemoveContinentFromMap(continentSuggestList.get(position).copyContinent(), 'A');
                                if (config.getMsgCode() == 0) {
                                    Toast.makeText(MapEditorActivity.this, config.getMsgText(), Toast.LENGTH_SHORT).show();
                                }
                                continentAdapter.notifyDataSetChanged();
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        listContinent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapEditorActivity.this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("Are you sure you want to remove " + map.getContinentList().get(position).getContName() + "?")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                map.addRemoveContinentFromMap(map.getContinentList().get(position), 'R');
                                continentAdapter.notifyDataSetChanged();

                            }
                        })
                        .show();


                return false;
            }

        });

        listSuggestTerritory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapEditorActivity.this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("Are you sure you want to add " + territorySuggestList.get(position).getTerritoryName() + "?")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                newTerritory = territorySuggestList.get(position).copyTerritory();
                                Toast.makeText(MapEditorActivity.this, "Touch on Map to add territory", Toast.LENGTH_SHORT).show();
                                isWaitingForUserTouchOnAddTerritory = true;
                            }
                        })
                        .show();

            }
        });

        listTerritory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapEditorActivity.this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("Are you sure you want to remove " + map.getTerritoryList().get(position).getTerritoryName() + "?")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                newTerritory = map.getTerritoryList().get(position);
                                map.addRemoveTerritoryFromMap(newTerritory, 'R');
                                territoryAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();

                return false;
            }

        });

        listContinent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedContinent = map.getContinentList().get(position);
                setTerritoryAdapterForSelectedContinent();
                hideAllLinearLayouts();
                linearTerritory.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setTerritoryAdapterForSelectedContinent() {
        List<Territory> selectedTerritory = map.getTerrForCont(selectedContinent);
        setAdapterForTerritory(selectedTerritory);
    }

    private void initialization() throws JSONException {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                filePathToLoad = bundle.getString(Constants.KEY_FILE_PATH);
            }

        }
        if (!TextUtils.isEmpty(filePathToLoad)) {
            setMap(new ReadMapUtility(this).readFile(filePathToLoad));
        } else {
            setMap(new GameMap());
        }
        getSuggestedContinentList();
        getSuggestedTerritoryList();
    }

    private void getSuggestedTerritoryList() throws JSONException {
        territorySuggestList = MapManager.getInstance().getTerritoryListFromFile(this);
        territorySuggestAdapter = new TerritoryAdapter(this, territorySuggestList);
        listSuggestTerritory.setAdapter(territorySuggestAdapter);

        territoryAdapter = new TerritoryAdapter(this, map.getTerritoryList());
        listTerritory.setAdapter(territoryAdapter);

    }

    private void setAdapterForTerritory(List<Territory> territoryList) {
        territoryAdapter.setTerritoryList(territoryList);
        territoryAdapter.notifyDataSetChanged();
    }

    private void getSuggestedContinentList() throws JSONException {
        continentSuggestList = MapManager.getInstance().getContinentListFromFile(this);
        continentSuggestAdapter = new ContinentAdapter(this, continentSuggestList);
        listSuggestContinent.setAdapter(continentSuggestAdapter);

        continentAdapter = new ContinentAdapter(this, map.getContinentList());
        listContinent.setAdapter(continentAdapter);

    }

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            showMap();
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
     * @return : Boolean
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        if (isWaitingForUserTouchOnAddTerritory && newTerritory != null) {
            newTerritory.setCenterPoint((int) x, (int) y);
            newTerritory.setContinent(selectedContinent);
            ConfigurableMessage config = map.addRemoveTerritoryFromMap(newTerritory, 'A');
            setTerritoryAdapterForSelectedContinent();
            newTerritory = null;
            isWaitingForUserTouchOnAddTerritory = false;
            if (config.getMsgCode() == 0) {
                Toast.makeText(this, config.getMsgText(), Toast.LENGTH_SHORT).show();
            } else {
                showMap();
            }
        } else {
            for (Territory territory : map.getTerritoryList()) {
                double distanceFromTerritory = MathUtility.getInstance().getDistance(x, y, territory.getCenterPoint().x, territory.getCenterPoint().y);
                Log.e("Distance", String.valueOf(distanceFromTerritory));
                if (Constants.TERRITORY_RADIUS > distanceFromTerritory) {
                    if (!isRequestToAddNeighbour) {
                        isRequestToAddNeighbour = true;
                        neighbourTerritoryFrom = territory;
                    } else {
                        neighbourTerritoryTo = territory;
                        isRequestToAddNeighbour = false;
                        neighbourTerritoryFrom.addRemoveNeighbourToTerr(neighbourTerritoryTo, 'A');
                        showMap();
                    }
                }
            }

        }

        return false;

    }

    private void showMap() {

        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(15);
        canvas = surface.getHolder().lockCanvas();
        for (Territory territory : map.getTerritoryList()) {
            Paint paint = new Paint();
            paint.setColor(territory.getContinent().getContColor());
            canvas.drawCircle(territory.getCenterPoint().x, territory.getCenterPoint().y, Constants.TERRITORY_RADIUS, paint);
            for (Territory territoryNeighbour : territory.getNeighbourList()) {
                canvas.drawLine(territory.getCenterPoint().x, territory.getCenterPoint().y, territoryNeighbour.getCenterPoint().x, territoryNeighbour.getCenterPoint().y, linePaint);
            }
        }
        surface.getHolder().unlockCanvasAndPost(canvas);

    }

    private void setMap(GameMap map) {
        this.map = map;
    }


    private void loadMap() {

    }

    private void addTerritory() {
        hideAllLinearLayouts();
        linearAddTerritory.setVisibility(View.VISIBLE);
    }

    private void addCustomTerritory() {
        editCustomTerritory = new EditText(this);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Please Enter Territory Name")
                .setConfirmText("Ok")
                .setCustomView(editCustomTerritory)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (!TextUtils.isEmpty(editCustomTerritory.getText().toString())) {
                            newTerritory = new Territory(editCustomTerritory.getText().toString());
                            Toast.makeText(MapEditorActivity.this, "Touch on Map to add territory", Toast.LENGTH_SHORT).show();
                            isWaitingForUserTouchOnAddTerritory = true;
                        }
                    }
                })
                .show();
    }

    private void addContinent() {
        hideAllLinearLayouts();
        linearAddContinent.setVisibility(View.VISIBLE);
    }

    private void addCustomContinent() {
        myLayout = new LinearLayout(this);
        editCustomContinent = new EditText(this);
        editContinentScore = new EditText(this);
        editCustomContinent.setHint("Continent Name");
        editContinentScore.setHint("Continent Score");
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.addView(editCustomContinent);
        myLayout.addView(editContinentScore);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText(" Please Enter Continent Name")
                .setConfirmText("Ok")
                .setCustomView(myLayout)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (!TextUtils.isEmpty(editCustomContinent.getText().toString()) && !TextUtils.isEmpty(editContinentScore.getText().toString())) {
                            newContinent = new Continent(editCustomContinent.getText().toString(), Integer.parseInt(editContinentScore.getText().toString()),MapEditorActivity.this);
                        }
                        ConfigurableMessage message = map.addRemoveContinentFromMap(newContinent, 'A');
                        if (message.getMsgCode() == 1) {
                            Toast.makeText(MapEditorActivity.this, message.getMsgText(), Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO :: Do something with error
                            Toast.makeText(MapEditorActivity.this, message.getMsgText(), Toast.LENGTH_SHORT).show();
                        }
                        continentAdapter.notifyDataSetChanged();
                        editCustomContinent = null;
                        sweetAlertDialog.dismiss();
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

    private void saveToMap() {
        final EditText editMapName = new EditText(this);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapEditorActivity.this, SweetAlertDialog.NORMAL_TYPE);
        if (TextUtils.isEmpty(filePathToLoad)) {
            sweetAlertDialog.setCustomView(editMapName);
        }
        sweetAlertDialog.setTitleText("Do you want to save Map ?")
                .setConfirmText("Yes")
                .setCancelText("No")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        File mapFile = null;
                        if (!TextUtils.isEmpty(editMapName.getText())) {
                            mapFile = FileManager.getInstance().getMapFilePath(editMapName.getText().toString() + ".map");
                        } else if (!TextUtils.isEmpty(filePathToLoad)) {
                            mapFile = new File(filePathToLoad);
                        }
                        if (mapFile != null) {
                            map.writeDataToFile(mapFile);
                            MapEditorActivity.this.finish();
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        MapEditorActivity.this.finish();

                    }
                });

        sweetAlertDialog.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        initializeView();
    }

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
            saveToMap();

        }
    }
}
