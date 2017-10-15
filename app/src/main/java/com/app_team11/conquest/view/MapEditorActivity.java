package com.app_team11.conquest.view;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.MapManager;

import org.json.JSONException;

import java.util.ArrayList;
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
    private ContinentAdapter continentAdapter;
    private TerritoryAdapter territoryAdapter;
    private Continent selectedContinent;

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

        listSuggestContinent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MapEditorActivity.this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("Are you sure you want to add " + continentSuggestList.get(position).getContName() + "?")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                map.addRemoveContinentFromMap(continentSuggestList.get(position), 'A');
                                continentAdapter.notifyDataSetChanged();

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
                                newTerritory = territorySuggestList.get(position);
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
                List<Territory> selectedTerritory = map.getTerrForCont(selectedContinent);
                setAdapterForTerritory(selectedTerritory);
                hideAllLinearLayouts();
                linearTerritory.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initialization() throws JSONException {
        territoryList = new ArrayList<>();
        continentList = new ArrayList<>();
        setMap(new GameMap());
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
            // Do some drawing when surface is ready
           /* canvas = holder.lockCanvas();
            canvas.drawColor(Color.RED);
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawCircle(120f, 130f, 100f, paint);
            holder.unlockCanvasAndPost(canvas);*/
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
            map.addRemoveTerritoryFromMap(newTerritory, 'A');
            newTerritory = null;
            isWaitingForUserTouchOnAddTerritory = false;
            showMap();
        }
        continentAdapter.notifyDataSetChanged();
        territoryAdapter.notifyDataSetChanged();
        return false;
    }

    private void showMap() {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas = surface.getHolder().lockCanvas();

        for (Territory territory : map.getTerritoryList()) {
            canvas.drawCircle(territory.getCenterPoint().x, territory.getCenterPoint().y, 100f, paint);
        }
        surface.getHolder().unlockCanvasAndPost(canvas);

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
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText(" Please Enter Continent Name")
                .setConfirmText("Ok")
                .setCustomView(editCustomContinent)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (!TextUtils.isEmpty(editCustomContinent.getText().toString())) {
                            newContinent = new Continent(editCustomContinent.getText().toString(), 1);
                        }
                        ConfigurableMessage message = map.addRemoveContinentFromMap(newContinent, 'A');
                        if (message.getMsgCode() == 1) {
                            Toast.makeText(MapEditorActivity.this, message.getMsgText(), Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO :: Do something with error
                            Toast.makeText(MapEditorActivity.this, message.getMsgText(), Toast.LENGTH_SHORT).show();
                        }
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
            super.onBackPressed();

        }
    }
}
