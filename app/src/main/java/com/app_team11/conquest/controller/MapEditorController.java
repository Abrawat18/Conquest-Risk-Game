package com.app_team11.conquest.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.app_team11.conquest.view.GameLogActivity;
import com.app_team11.conquest.view.MainDashboardActivity;
import com.app_team11.conquest.view.MapEditorActivity;

import org.json.JSONException;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * For editing and making new maps this controller class is called
 * Created by Abhishek on 31-Oct-17.
 * @version 1.0.0
 */

//Todo : editor menu : 1. add continent 2. add territory 3. on terrirtory  selection : show continent or select continent , neighbour


public class MapEditorController {

    private static final String TAG = MapEditorController.class.getSimpleName();
    private GameMap map;
    public List<Continent> continentSuggestList;
    public List<Territory> territorySuggestList;
    private boolean isWaitingForUserTouchOnAddTerritory = false;
    private boolean isWaitingForUserTouchOnAddContinent = false;
    public Territory newTerritory;
    private Continent newContinent;
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
    private Context context;
    private static MapEditorController mapEditorController;
    private EditText editCustomContinent;
    private EditText editCustomTerritory;
    private EditText editContinentScore;
    private LinearLayout myLayout;

    /**
     * Default Constructor
     */
    private MapEditorController() {

    }

    /**
     * Getting the instance of MapEditorController
     * @return MapEditorController : default constructor is resturned
     */
    public static MapEditorController getInstance() {
        if (mapEditorController == null) {
            mapEditorController = new MapEditorController();
        }
        return mapEditorController;
    }

    /**
     * setting the adapter for continents
     */
    public void setTerritoryAdapterForSelectedContinent() {
        List<Territory> selectedTerritory = map.getTerrForCont(selectedContinent);
        setAdapterForTerritory(selectedTerritory);
    }

    /**
     * decides whether the map is loaded from the file or a new map is to be created
     * @throws JSONException : Throws exception for JSON
     */
    public void initialization(Context context) throws JSONException {
        this.context = context;
        editCustomContinent = new EditText(getActivity());
        editCustomTerritory = new EditText(getActivity());
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                filePathToLoad = bundle.getString(Constants.KEY_FILE_PATH);
            } else {
                filePathToLoad = "";
            }

        }
        if (!TextUtils.isEmpty(filePathToLoad)) {
            setMap(new ReadMapUtility(getActivity()).readFile(filePathToLoad));
            if (map == null || !map.isGraphConnected()) {
                showToast(Constants.TOAST_ERROR_GRAPH_NOT_CONNECTED);
                getActivity().finish();
            }
        } else {
            setMap(new GameMap());
        }
        getSuggestedContinentList();
        getSuggestedTerritoryList();
    }

    /**
     * Setting continent for the map
     * @param position : The parameter is position of the view
     */
    public void onClickContinent(int position) {
        selectedContinent = map.getContinentList().get(position);
        MapEditorController.getInstance().setTerritoryAdapterForSelectedContinent();
        getActivity().hideAllLinearLayouts();
        getActivity().linearTerritory.setVisibility(View.VISIBLE);
    }

    /**
     * Placing territory in continent
     * @param position : parameter which defines the position for the territory
     */
    public void onLongClickTerritory(final int position) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
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
    }

    /**
     * Confirmation of placement of territory
     * @param position : Parameter for the territory position
     */
    public void onClickSuggestTerritory(final int position) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Are you sure you want to add " + MapEditorController.getInstance().territorySuggestList.get(position).getTerritoryName() + "?")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        newTerritory = MapEditorController.getInstance().territorySuggestList.get(position).copyTerritory();
                        Toast.makeText(getActivity(), "Touch on Map to add territory", Toast.LENGTH_SHORT).show();
                        isWaitingForUserTouchOnAddTerritory = true;
                    }
                })
                .show();
    }

    /**
     * Confirmation to remove the continent
     * @param position : defines the position for the on click continent
     */
    public void onLongClickContinent(final int position) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
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
    }

    /**
     * Confirmation to add continent
     * @param position : Parameter for the suggested continent position
     */
    public void onClickSuggestContinent(final int position) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Are you sure you want to add " + MapEditorController.getInstance().continentSuggestList.get(position).getContName() + "?")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ConfigurableMessage config = map.addRemoveContinentFromMap(MapEditorController.getInstance().continentSuggestList.get(position).copyContinent(), 'A');
                        if (config.getMsgCode() == 0) {
                            Toast.makeText(getActivity(), config.getMsgText(), Toast.LENGTH_SHORT).show();
                        }
                        continentAdapter.notifyDataSetChanged();
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }


    /**
     * method to fetch the list of territories from json file
     *
     * @throws JSONException : Exception handling for JSON
     */
    private void getSuggestedTerritoryList() throws JSONException {
        territorySuggestList = MapManager.getInstance().getTerritoryListFromFile(getActivity());
        territorySuggestAdapter = new TerritoryAdapter(getActivity(), territorySuggestList);
        getActivity().listSuggestTerritory.setAdapter(territorySuggestAdapter);

        territoryAdapter = new TerritoryAdapter(getActivity(), map.getTerritoryList());
        getActivity().listTerritory.setAdapter(territoryAdapter);

    }

    /**
     * adapter for territory
     * @param territoryList : parameter for the list of territories
     */
    private void setAdapterForTerritory(List<Territory> territoryList) {
        territoryAdapter.setTerritoryList(territoryList);
        territoryAdapter.notifyDataSetChanged();
    }

    /**
     * method to fetch the list of continents from json file
     *
     * @throws JSONException : Exception handling for JSON
     */
    private void getSuggestedContinentList() throws JSONException {
        continentSuggestList = MapManager.getInstance().getContinentListFromFile(getActivity());
        continentSuggestAdapter = new ContinentAdapter(getActivity(), continentSuggestList);
        getActivity().listSuggestContinent.setAdapter(continentSuggestAdapter);

        continentAdapter = new ContinentAdapter(getActivity(), map.getContinentList());
        getActivity().listContinent.setAdapter(continentAdapter);

    }

    /**
     * Surface initialization for the map
     */
    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            showMap();
        }

        /**
         *
         * @param holder : Holder parameter for the surface
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

            holder.removeCallback(surfaceCallback);
        }

        /**
         * Changes the surface dimensions
         * @param holder : defines the surface holder
         * @param format : defines the format of the surface
         * @param width : defines the width of the surface
         * @param height : defines the height of the surface
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    };

    /**
     * Showing the message
     * @param msg : Parameter for displaying the message
     */
    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * method is creating and drawing the map object on the screen
     */
    public void showMap() {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(15);
        getActivity().canvas = getActivity().surface.getHolder().lockCanvas();
        if (getActivity().canvas != null) {
            for (Territory territory : map.getTerritoryList()) {
                Paint paint = new Paint();
                paint.setColor(territory.getContinent().getContColor());
                getActivity().canvas.drawCircle(territory.getCenterPoint().x, territory.getCenterPoint().y, Constants.TERRITORY_RADIUS, paint);
                for (Territory territoryNeighbour : territory.getNeighbourList()) {
                    getActivity().canvas.drawLine(territory.getCenterPoint().x, territory.getCenterPoint().y, territoryNeighbour.getCenterPoint().x, territoryNeighbour.getCenterPoint().y, linePaint);
                }
            }
            getActivity().surface.getHolder().unlockCanvasAndPost(getActivity().canvas);
        }

    }

    /**
     * Set the map on the screen
     * @param map : Parameter for setting Game map
     */
    private void setMap(GameMap map) {
        this.map = map;

    }

    /**
     * Loads the map on the screen
     */
    private void loadMap() {

    }

    /**
     * Addition of territory on the map
     */
    public void addTerritory() {
        getActivity().hideAllLinearLayouts();

        getActivity().linearAddTerritory.setVisibility(View.VISIBLE);
    }

    /**
     * method to allow the user to add a custom territory by taking input from the screen
     */
    public void addCustomTerritory() {
        editCustomTerritory = new EditText(getActivity());
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText("Please Enter Territory Name")
                .setConfirmText("Ok")
                .setCustomView(editCustomTerritory)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (!TextUtils.isEmpty(editCustomTerritory.getText().toString())) {
                            newTerritory = new Territory(editCustomTerritory.getText().toString());
                            Toast.makeText(getActivity(), "Touch on Map to add territory", Toast.LENGTH_SHORT).show();
                            isWaitingForUserTouchOnAddTerritory = true;
                        }
                    }
                })
                .show();
    }

    /**
     * Addition of continent
     */
    public void addContinent() {
        getActivity().hideAllLinearLayouts();
        getActivity().linearAddContinent.setVisibility(View.VISIBLE);
    }

    /**
     * method to allow the user to add a custom continent by taking input from the screen
     */
    public void addCustomContinent() {
        myLayout = new LinearLayout(getActivity());
        editCustomContinent = new EditText(getActivity());
        editContinentScore = new EditText(getActivity());
        editCustomContinent.setHint("Continent Name");
        editContinentScore.setHint("Continent Score");
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.addView(editCustomContinent);
        myLayout.addView(editContinentScore);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setTitleText(" Please Enter Continent Name")
                .setConfirmText("Ok")
                .setCustomView(myLayout)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (!TextUtils.isEmpty(editCustomContinent.getText().toString()) && !TextUtils.isEmpty(editContinentScore.getText().toString())) {
                            newContinent = new Continent(editCustomContinent.getText().toString(), Integer.parseInt(editContinentScore.getText().toString()), getActivity());
                        }
                        ConfigurableMessage message = map.addRemoveContinentFromMap(newContinent, 'A');
                        if (message.getMsgCode() == 1) {
                            Toast.makeText(getActivity(), message.getMsgText(), Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO :: Do something with error
                            Toast.makeText(getActivity(), message.getMsgText(), Toast.LENGTH_SHORT).show();
                        }
                        continentAdapter.notifyDataSetChanged();
                        editCustomContinent = null;
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();

    }

    /**
     * method is used to save all the information taken from input and write to the file
     */
    public void saveToMap() {

        if (map.isGraphConnected()) {
            final EditText editMapName = new EditText(getActivity());
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
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
                                getActivity().finish();
                            }
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            getActivity().finish();

                        }
                    });

            sweetAlertDialog.show();
        } else {
            showToast(Constants.TOAST_ERROR_GRAPH_NOT_CONNECTED);
        }
    }

    /**
     * Game log method
     */
    public void openGameLog() {
        Intent intent = new Intent(getActivity(), GameLogActivity.class);
        getActivity().startActivity(intent);
    }

    /**
     * Motion event capture
     *
     * @param v : Defines the view when touch is called on the screen
     * @param event : Defines the event when the touch is called on the screen
     * @return boolean : true or false value is returned
     */
    public boolean onTouch(View v, MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        if (isWaitingForUserTouchOnAddTerritory && newTerritory != null) {
            newTerritory.setCenterPoint((int) x, (int) y);
            newTerritory.setContinent(selectedContinent);
            ConfigurableMessage config = map.addRemoveTerritoryFromMap(newTerritory, 'A');
            MapEditorController.getInstance().setTerritoryAdapterForSelectedContinent();
            newTerritory = null;
            isWaitingForUserTouchOnAddTerritory = false;
            if (config.getMsgCode() == 0) {
                Toast.makeText(getActivity(), config.getMsgText(), Toast.LENGTH_SHORT).show();
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
                        if (neighbourTerritoryTo != neighbourTerritoryFrom) {
                            ConfigurableMessage msg = neighbourTerritoryFrom.addRemoveNeighbourToTerr(neighbourTerritoryTo, 'A');
                            if (msg.getMsgCode() == Constants.MSG_SUCC_CODE) {
                                showMap();
                            } else
                                showToast(msg.getMsgText());

                        } else {
                            showToast(Constants.TOAST_MSG_SAME_NEIGHBOUR_ERROR);
                        }
                    }
                }
            }

        }

        return false;

    }

    /**
     * Returns the MapEditorActivity
     * @return MapEditorActivity : Map Editor activity is returned when this getter is called.
     */
    public MapEditorActivity getActivity() {
        return (MapEditorActivity) context;
    }

}
