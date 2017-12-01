package com.app_team11.conquest.utility;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * JSON Class is used for loading the preset values from JSON
 * Created by Jaydeep9101 on 08-Oct-17.
 */

public class JsonLoader {
    private static JsonLoader jsonLoader;

    private JsonLoader() {

    }

    /**
     * Singleton creation for JSONLoader
     * @return jsonLoader json loader is available
     */
    public static JsonLoader getInstance() {
        if (jsonLoader == null) {
            jsonLoader = new JsonLoader();
        }
        return jsonLoader;
    }

    /**
     * JSONArray for the loading of data from JSON file
     * @param context current running activity
     * @param fileName name of the file
     * @return JSONArray array of JSON file is returned
     * @throws JSONException exception handling for JSON
     */
    public JSONArray loadJSONFromAsset(Context context, String fileName) throws JSONException {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return new JSONArray(json);
    }
}
