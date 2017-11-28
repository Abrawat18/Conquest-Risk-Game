package com.app_team11.conquest.global;
/**
 * All the oonstants for the project are described here in this class
 * Created by Vasu on 06-10-2017.
 *
 */
public class Constants {

    public static final String KEY_CONTINENT_NAME = "name";
    public static final String KEY_TERRITORY_NAME = "name";
    public static final String KEY_CONTINENT_SCORE = "score";
    public static final String ASSETS_CONTINENT_FILE_NAME = "continent.json";
    public static final String ASSETS_TERRITORY_FILE_NAME = "territory.json";
    public static final String ROOT_MAP_DIR = "AllMaps";
    public static final String ROOT_LOG_DIR = "MapLog";
    public static final String ROOT_SER_DIR = "SerObject";
    public static final String KEY_FILE_PATH = "FilePath";
    public static final String KEY_FROM = "FromWhichActivity";
    public static final String VALUE_FROM_EDIT_MAP = "FromEditMap";
    public static final String VALUE_FROM_PLAY_GAME = "FromGamePlay";
    public static final String KEY_NO_OF_PLAYER = "NoOfPlayer";
    public static final String NEIGHBOUR_SIZE_VAL_FAIL = "Maximum and Minimum number of neighbours allowed is 10 and 1 respectively";
    public static final String CONT_SIZE_VAL_FAIL = "Minimum number of Continents in a map must be 1";
    public static final String TERR_SIZE_VAL_FAIL = "Maximum and Minimum number of Territory in a map must be 255 and 1";
    public static final String ADD_REM_TO_LIST_SUCCESS = "Data added/removed in the list successfully";
    public static final String MAP_FILE_PATH = "C:\\Users\\Vasu\\Desktop\\Conquest_Game\\";
    public static final String GAME_LOG = "log.txt";
    public static final String OBJECT_STATE = "serializable.ser";
    public static final String INCORRECT_FLAG = "Flag is incorrect";
    public static final int MSG_FAIL_CODE =0;
    public static final int MSG_SUCC_CODE =1;
    public static final String ARMY_INFANTRY = "infantry";
    public static final String ARMY_CAVALRY = "cavalry";
    public static final String ARMY_ARTILLERY = "artillery";
    public static final String DUPLICATE_CONTINENT="Continent already exists";
    public static final String DUPLICATE_TERRITORY="Territory already exists";
    public static final float TERRITORY_RADIUS = 50f;
    public static final String ARMY_ADDED_SUCCESS = "Armies are moved successfully";
    public static final String ARMY_ADDED_FAILURE = "Armies are movement failed as the count of armies for movement exceeded the allowed movement";
    public static final String PLAYER_ADDED_SUCCESS = "Players are added successfully";
    public static final String PLAYER_ADDED_FAILURE = "Players must be between 2 and 6";
    public static final String FORTIFICATION_SUCCESS = "Fortification is successful";
    public static final String FORTIFICATION_FAILURE = "Fortification is failed due to exceeded army count and/or current player not the owner of the territories";
    public static final String FORTIFICATION_NEIGHBOUR_FAILURE = "Fortification is failed due to the destinaton territory not a neighbour of the source";
    public static final String INCORRECT_TERRITORY = "Select only your territory !!";
    public static final String SELECT_TO_TERR = "Select to territory";
    public static final String TOAST_MSG_SAME_NEIGHBOUR_ERROR = "Neighbour cannot be same as source territory";
    public static final String NEIGHBOUR_ALREADY_EXISTS = "The selected neighbour already exists";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String TOAST_MSG_MAX_CARDS_SELECTION_ERROR = "You can not select more than 3 cards";
    public static final String NOT_ADJACENT_TERRITORY = "You can attack only adjacent territories";
    public static final String INSUFFUCIENT_ARMIES = "You don't have enough armies to attack";
    public static final String NO_ARMIES = "This territory doesn't have any armies";
    public static final String ATTACKER_DICE = "Attacker's number of dice should be less than the army count";
    public static final String CHOOSE_LESS_NUMBER_DICE  = "Defender reduce the number of dice";
    public static final String PLACE_MORE_ARMIES = "The number of armies you place should be more than the number of dice you last chose";
    public static final String LEAVE_ONE_ARMY = "You must leave atleast one army behind";
    public static final String ATTACKER_WON = "Attacker won";
    public static final String ATTACKER_LOST = "Attacker lost";
    public static final String PLAYER_WON="Congratulations! You won the game!";
    public static final String TOAST_ERROR_GRAPH_NOT_CONNECTED = "The map is not connected.";
    public static final String KEY_FROM_GAME_MODE = "FromGameMode";
    public static final String FROM_SINGLE_MODE_VALUE = "SingleGameMode";
    public static final String FROM_TOURNAMENT_MODE_VALUE = "TournamentGameMode";
    public static final String KEY_PLAYER_LIST = "PlayerList";
    public static final String KEY_NUMBER_GAMES = "numberOfGames";
    public static final String KEY_NUMBER_DRAWS = "numberOfDraws";
    public static final String KEY_SELECTED_MAP_LIST = "SelectedMapList";
    public static final String FORTIFICATION_FAILURE_STRATEGY = "Fortification could not happen as not able to find any match for fortifying territories";
    public static final String REINFORCEMENT_SUCCESS_STRATEGY = "Reinforcement successful";
    public static final int RANDOM_NUMBER_ATTACK_TIMES = 5;
    public static final String ATTACK_SUCCESS_STRATEGY = "Attack successful";
    public static final String ATTACK_FAIL_STRATEGY = "Attack could not succeed as their is no territory to attack";
    public static final String HUMAN_PLAYER_STRATEGY="Human";
    public static final String RANDOM_PLAYER_STRATEGY="Random";
    public static final String CHEATER_PLAYER_STRATEGY="Cheater";
    public static final String BENEVOLENT_PLAYER_STRATEGY="Benevolent";
    public static final String AGGRESSIVE_PLAYER_STRATEGY="Aggressive";
    public static final String KEY_TOURNAMENT_GAMES_COUNT = "gamesCount";
}
