package com.app_team11.conquest.utility;

import android.content.Context;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.Territory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Management of map
 * Created by Jaydeep9101 on 08-Oct-17.
 */

public class MapManager {
    private static MapManager mapManager;

    private MapManager() {

    }

    /**
     * Singleton creation for MapManager
     * @return mapManager
     */
    public static MapManager getInstance() {
        if (mapManager == null) {
            mapManager = new MapManager();
        }
        return mapManager;
    }

    /**
     * List of continent from file
     * @param context
     * @return list of continent
     * @throws JSONException
     */
    public List<Continent> getContinentListFromFile(Context context) throws JSONException {
        JSONArray continentJsonList = JsonLoader.getInstance().loadJSONFromAsset(context, Constants.ASSETS_CONTINENT_FILE_NAME);
        List<Continent> continentList = new ArrayList<>();
        for (int continentIndex = 0; continentIndex < continentJsonList.length(); continentIndex++) {
            continentList.add(new Continent(continentJsonList.getJSONObject(continentIndex).getString(Constants.KEY_CONTINENT_NAME), continentJsonList.getJSONObject(continentIndex).getInt(Constants.KEY_CONTINENT_SCORE),context));
        }
        return continentList;
    }

    /**
     * List of territory from file
     * @param context
     * @return list of territory
     * @throws JSONException
     */
    public List<Territory> getTerritoryListFromFile(Context context) throws JSONException {
        JSONArray territoryJsonList = JsonLoader.getInstance().loadJSONFromAsset(context, Constants.ASSETS_TERRITORY_FILE_NAME);
        List<Territory> territoryList = new ArrayList<>();
        for (int continentIndex = 0; continentIndex < territoryJsonList.length(); continentIndex++) {
            territoryList.add(new Territory(territoryJsonList.getJSONObject(continentIndex).getString(Constants.KEY_TERRITORY_NAME), 0, 0, null));
        }
        return territoryList;
    }
}
