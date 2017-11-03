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
import com.app_team11.conquest.utility.MathUtility;

import org.json.JSONException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Jaydeep9101 on 06-Oct-17.
 *
 * @version 1.0.0
 */

//Todo : editor menu : 1. add continent 2. add territory 3. on terrirtory  selection : show continent or select continent , neighbour


public class MapEditorActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private static final String TAG = MapEditorActivity.class.getSimpleName();
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

        if (savedInstanceState == null) {
            initializeView();
            try {
                MapEditorController.getInstance().initialization(this);
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


    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            MapEditorController.getInstance().showMap();
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

        MapEditorController.getInstance().onTouch(v, event);
        return false;

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /*private void addTerritory() {
        hideAllLinearLayouts();
        linearAddTerritory.setVisibility(View.VISIBLE);
    }*/

    /**
     * method to allow the user to add a custom territory by taking input from the screen
     */
    /*private void addCustomTerritory() {
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

    *//**
     * method to allow the user to add a custom continent by taking input from the screen
     *//*
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
                            newContinent = new Continent(editCustomContinent.getText().toString(), Integer.parseInt(editContinentScore.getText().toString()), MapEditorActivity.this);
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

    }*/

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
     * method is used to save all the information taken from input and write to the file
     */
    /*private void saveToMap() {
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
*/
    /**
     * {@inheritDoc}
     *
     * @param v
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
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
        initializeView();
    }

    /**
     * {@inheritDoc}
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
            MapEditorController.getInstance().saveToMap();

        }
    }
}
