package com.app_team11.conquest.model;


import android.util.Log;

import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * primary class for Game play indicating data variables required to implement the game
 * Created by Vasu on 06-10-2017.
 * @version 1.0.0
 */
public class GameMap {
    private static final String TAG = GameMap.class.getSimpleName();
    private String imageName;
    private String wrapFlag;
    private String authorName;
    private String scrollLine;
    private String warnFlag;
    private List<Continent> continentList;
    private List<Territory> territoryList;
    private List<Player> playerList;
    private int noOfCardTradedCount;

    /**
     * Initialize the class members
     *
     */
    public GameMap() {
        this.continentList = new ArrayList<Continent>();
        this.territoryList = new ArrayList<Territory>();
    }

    /**
     * Initializa the class members
     * @param imageName
     * @param wrapFlag
     * @param authorName
     * @param scrollLine
     * @param warnFlag
     */
    public GameMap(String imageName, String wrapFlag, String authorName, String scrollLine, String warnFlag) {
        this.imageName = imageName;
        this.wrapFlag = wrapFlag;
        this.authorName = authorName;
        this.scrollLine = scrollLine;
        this.warnFlag = warnFlag;
        this.continentList = new ArrayList<Continent>();
        this.territoryList = new ArrayList<Territory>();
    }

    /**
     * Method is used to write map information to the file
     * @param file object containing file information
     */
    public void writeDataToFile(File file) {
        try {
            BufferedWriter writer = FileManager.getInstance().createWriter(file);
            writer.write("[Map]");
            writer.newLine();
            writer.write("image=" + this.imageName);
            writer.newLine();
            writer.write("wrap=" + this.wrapFlag);
            writer.newLine();
            writer.write("author=" + this.authorName);
            writer.newLine();
            writer.write("scroll=" + this.scrollLine);
            writer.newLine();
            writer.write("warn=" + this.warnFlag);
            writer.newLine();
            writer.newLine();
            writer.write("[Continents]");
            writer.newLine();
            Log.e(TAG, "CHECKING..!!");
            for (Continent contObj : this.getContinentList()) {
                writer.write(contObj.getContName().toString() + "=" + Integer.toString(contObj.getScore()));
                writer.newLine();
            }
            writer.newLine();
            writer.write("[Territories]");
            writer.newLine();
            for (Territory terrObj : this.getTerritoryList()) {
                StringBuffer finStr = new StringBuffer(terrObj.getTerritoryName().toString() + ", " + terrObj.getCenterPoint().x + ", " + terrObj.getCenterPoint().y + ", " + terrObj.getContinent().getContName().toString());
                for (int i = 0; i < terrObj.getNeighbourList().size(); i++) {
                    finStr.append(", " + terrObj.getNeighbourList().get(i).getTerritoryName().toString());
                }
                writer.write(finStr.toString());
                writer.newLine();
            }

            writer.close();
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Method is used to add or remove continents from the map based on validations for min and max continents allowed
     * Validation2
     *
     * @param contObj Continent object which needs to be added or removed from map
     * @param addRemoveFlag flag to indicate if the continent is needed to be added - 'A' or removed - 'R'
     * @return custom message
     */
    public ConfigurableMessage addRemoveContinentFromMap(Continent contObj, char addRemoveFlag) {
        if (addRemoveFlag == 'A') {
            boolean addFlag = true;
            if (null == this.continentList || this.continentList.size() < 32) {
                for (Continent obj : this.continentList) {
                    if (obj.getContName().equalsIgnoreCase(contObj.getContName())) {
                        addFlag = false;
                    }
                }
                if (addFlag == true) {
                    this.continentList.add(contObj);
                    return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ADD_REM_TO_LIST_SUCCESS);
                } else
                    return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.DUPLICATE_CONTINENT);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.CONT_SIZE_VAL_FAIL);
        } else if (addRemoveFlag == 'R') {
            if (this.continentList.size() > 1) {
                this.continentList.remove(contObj);
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ADD_REM_TO_LIST_SUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.CONT_SIZE_VAL_FAIL);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.INCORRECT_FLAG);

    }

    /**
     * Method is used to add or remove territories from the map
     *
     * @param terrObj       Territory object for the territory that is needed to be removed
     * @param addRemoveFlag flag to indicate if the territory needs to be removed - 'R' or added - 'A'
     * @return custom message
     */
    public ConfigurableMessage addRemoveTerritoryFromMap(Territory terrObj, char addRemoveFlag) {
        if (addRemoveFlag == 'A') {
            boolean addFlag = true;
            if (null == this.territoryList || this.territoryList.size() < 255) {
                for (Territory obj : this.territoryList) {
                    if (obj.getTerritoryName().equalsIgnoreCase(terrObj.getTerritoryName())) {
                        addFlag = false;
                    }
                }
                if (addFlag == true) {
                    this.territoryList.add(terrObj);
                    return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ADD_REM_TO_LIST_SUCCESS);
                } else
                    return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.DUPLICATE_TERRITORY);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.TERR_SIZE_VAL_FAIL);
        } else if (addRemoveFlag == 'R') {
            if (this.territoryList.size() > 1) {
                this.territoryList.remove(terrObj);
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ADD_REM_TO_LIST_SUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.TERR_SIZE_VAL_FAIL);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.INCORRECT_FLAG);


    }

    /**
     * This method is used to return the territories for the given continent
     *
     * @param contObj
     * @return Territory List
     */
    public List<Territory> getTerrForCont(Continent contObj) {
        List<Territory> terrList = new ArrayList<Territory>();
        for (Territory obj : this.getTerritoryList()) {
            if (obj.getContinent().getContName().equalsIgnoreCase(contObj.getContName())) {
                terrList.add(obj);
            }
        }
        return terrList;
    }

    /**
     * Method to add players to game and assign them intitial armies
     *
     * @param playersCount number of players to be added for game
     * @return Configurable Message
     */
    public ConfigurableMessage addPlayerToGame(int playersCount) {
        if (playersCount >= 2 && playersCount <= 6) {
            List<Player> playerList = new ArrayList<Player>();
            int armyCount = 0;
            if (playersCount == 2)
                armyCount = 40;
            else if (playersCount == 3) {
                armyCount = 35;
            } else if (playersCount == 4) {
                armyCount = 30;
            } else if (playersCount == 5) {
                armyCount = 25;
            } else if (playersCount == 6) {
                armyCount = 20;
            }
            for (int i = 1; i <= playersCount; i++) {
                Player playerObj = new Player();
                playerObj.setPlayerId(i);
                playerObj.setAvailableArmyCount(armyCount); //adding initial one army each to every territory from the player's count of army
                playerList.add(playerObj);
            }
            this.setPlayerList(playerList);

            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.PLAYER_ADDED_SUCCESS);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.PLAYER_ADDED_FAILURE);
    }

    /**
     * The method is used to return the territory list owned by the given Player
     *
     * @param playerObj
     * @return Territory List
     */
    public List<Territory> getTerrForPlayer(Player playerObj) {
        List<Territory> terrList = new ArrayList<Territory>();
        for (Territory obj : this.getTerritoryList()) {
            if (playerObj.getPlayerId() == obj.getTerritoryOwner().getPlayerId()) {
                terrList.add(obj);
            }
        }
        return terrList;
    }

    /**
     * Method to increase the number of card traded count
     *
     */
    public void increaseCardTradedCount(){
        this.noOfCardTradedCount ++;
    }

    /**
     * Return the number of traded cards
     * @return
     */
    public int getNoOfCardTradedCount() {
        return noOfCardTradedCount;
    }


    /**
     * method to set the turn of the current player for game play
     * @param player object of the player to whom the turn is to be placed
     */
    public void changeCurrentPlayerTurn(Player player) {
        for (Player playerFromList : getPlayerList()) {
            playerFromList.setMyTurn(false);
        }
        player.setMyTurn(true);
    }

    /**
     * Returns the name of the author
     * @return AuthorName
     */
    public String getAuthorName() {
        return authorName;
    }
/**
 * Sets the name of author
 */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     *Returns the list of continent
     * @return ContinentList
     */
    public List<Continent> getContinentList() {
        return continentList;
    }

    /**
     * Sets the list of continent in the Player
     * @param continentList
     */
    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    /**
     * Sets the list of territory
     * @return TerritoryList
     */
    public List<Territory> getTerritoryList() {
        return territoryList;
    }

    /**
     * Sets the List of Territory
     * @param territoryList
     */
    public void setTerritoryList(List<Territory> territoryList) {
        this.territoryList = territoryList;
    }

    /**
     * Sets the player list
     * @return playerList
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Sets the Player List
     * @param playerList
     */
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * Returns the name of the Image
     * @return ImageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets the name of the Image
     * @param imageName
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Returns the wrap property
     * @return wrapFlag
     */
    public String getWrapFlag() {
        return wrapFlag;
    }

    /**
     *Sets the wrap property
     * @param wrapFlag
     */
    public void setWrapFlag(String wrapFlag) {
        this.wrapFlag = wrapFlag;
    }

    /**
     * Sets the scroll Line
     * @param scrollLine
     */
    public void setScrollLine(String scrollLine) {
        this.scrollLine = scrollLine;
    }

    /**
     * Sets the wrap property
     * @param warnFlag
     */
    public void setWarnFlag(String warnFlag) {
        this.warnFlag = warnFlag;
    }

}

