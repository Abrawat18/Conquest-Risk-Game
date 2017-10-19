package com.app_team11.conquest.global;
/**
 * Created by Vasu on 06-10-2017.
 */
public class Constants {

    public static final String KEY_CONTINENT_NAME = "name";
    public static final String KEY_TERRITORY_NAME = "name";
    public static final String KEY_CONTINENT_SCORE = "score";
    public static final String ASSETS_CONTINENT_FILE_NAME = "continent.json";
    public static final String ASSETS_TERRITORY_FILE_NAME = "territory.json";
    public static final String ROOT_MAP_DIR = "AllMaps";
    public static final String KEY_FILE_PATH = "FilePath";
    public static final String KEY_FROM = "FromWhichActivity";
    public static final String VALUE_FROM_EDIT_MAP = "FromEditMap";
    public static final String VALUE_FROM_PLAY_GAME = "FromGamePlay";
    public static final String NEIGHBOURSIZEVALFAIL = "Maximum and Minimum number of neighbours allowed is 10 and 1 respectively";
    public static final String CONTSIZEVALFAIL = "Minimum number of Continents in a map must be 1";
    public static final String TERRSIZEVALFAIL = "Maximum and Minimum number of Territory in a map must be 255 and 1";
    public static final String ADDREMTOLISTSUCCESS = "Data added/removed in the list successfully";
    public static final String MAPFILEPATH = "C:\\Users\\Vasu\\Desktop\\Conquest_Game\\";
    public static final String INCORRECTFLAG = "Flag is incorrect";
    public static final int MSGFAILCODE=0;
    public static final int MSGSUCCCODE=1;
    public static final String ARMY_INFANTRY = "infantry";
    public static final String ARMY_CAVALRY = "cavalry";
    public static final String ARMY_ARTILLERY = "artillery";
    public static final String DUPLICATE_CONTINENT="Continent already exists";
    public static final String DUPLICATE_TERRITORY="Territory already exists";
    public static final float TERRITORY_RADIUS = 100f;
    public static final String ARMY_ADDED_SUCCESS = "Armies are moved successfully";
    public static final String ARMY_ADDED_FAILURE = "Armies are movement failed as the count of armies for movement exceeded the allowed movement";
    public static final String PLAYER_ADDED_SUCCESS = "Players are added successfully";
    public static final String PLAYER_ADDED_FAILURE = "Players must be between 2 and 6";
}
