package com.app_team11.conquest.model;



import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Territory model class with name of territory,position,neighbours,owner,army count,etc
 * Created by Vasu on 06-10-2017.
 * @version 1.0.0
 */
public class Territory extends Observable implements Serializable{

    private String territoryName;
    private Point centerPoint;
    private Continent continent;
    private List<Territory> neighbourList;
    private Player territoryOwner;
    private int armyCount;
    public Boolean isVisited=false;

    /**
     * Territory declaration
     * @param territoryName : name of the territory is defined by the parameter
     */
    public Territory(String territoryName) {
        this.territoryName = territoryName;
        this.neighbourList = new ArrayList();
    }

    /**
     * variables declaration for territory
     * @param territoryName : name of the territory is defined
     * @param centerX : center coordinate X is available
     * @param centerY : center coordinate Y is available
     * @param continent : name of the continent is available
     */
    public Territory(String territoryName, int centerX, int centerY, Continent continent) {
        this.territoryName = territoryName;
        this.centerPoint = new Point(centerX, centerY);
        this.continent = continent;
        this.neighbourList = new ArrayList();
    }

    /**
     * territory array declaration
     */
    public Territory() {
        this.neighbourList = new ArrayList();
    }

    /**
     * method to prevent conflict between the json territory object and map territory object
     * @return copied territory object
     */
    public Territory copyTerritory() {
        Territory territory = new Territory();
        territory.setTerritoryName(this.getTerritoryName());
        territory.setCenterPoint(this.getCenterPoint());
        territory.setContinent(this.getContinent());
        territory.setArmyCount(this.getArmyCount());
        territory.setNeighbourList(this.getNeighbourList());
        territory.setTerritoryOwner(this.getTerritoryOwner());
        return territory;
    }

    /**
     * to be called on click of add neighbours/connections
     * validation1 before saving a map - Validation to check if the number of neighbours not greater than 10
     * @param terrObj territory which is required to be added or removed
     * @param addRemoveFlag flag to point out whether the method to be used to add or remove territories
     * @return confirmationMessage : confirmation message is available due to this parameter
     */
    public ConfigurableMessage addRemoveNeighbourToTerr(Territory terrObj, char addRemoveFlag) {
        if (addRemoveFlag == 'A') {
            if (!this.getNeighbourList().contains(terrObj)) {
                if (this.neighbourList.size() <= 10 && terrObj.neighbourList.size() <= 10) {
                    this.neighbourList.add(terrObj);
                    terrObj.neighbourList.add(this);
                    try{
                        FileManager.getInstance().writeLog("Neighbour added between -> " + this.getTerritoryName() + " and " + terrObj.getTerritoryName());
                    }catch (Exception ex){

                    }
                    return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ADD_REM_TO_LIST_SUCCESS);
                } else
                    return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NEIGHBOUR_SIZE_VAL_FAIL);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NEIGHBOUR_ALREADY_EXISTS);
        } else if (addRemoveFlag == 'R') {
            if (this.neighbourList.size() >= 2 && terrObj.neighbourList.size() >= 2) {
                this.neighbourList.remove(terrObj);
                terrObj.neighbourList.remove(this);
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ADD_REM_TO_LIST_SUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NEIGHBOUR_SIZE_VAL_FAIL);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.INCORRECT_FLAG);
    }

    /**
     * validation1 before saving a map - Validation to check if the number of neighbours not greater than 10
     * @param terrList : list of territory is defined using this parameter
     */
    public void addNeighbourToTerr(List<Territory> terrList) {
        for (Territory objTerr : terrList) {
            addRemoveNeighbourToTerr(objTerr, 'A');
        }
    }

    /**
     * Method to add armies in territory selected and remove the same count from player
     * @param addedArmyCount count of armies to be added
     * @return custom message : custom message is returned
     */
    public ConfigurableMessage addArmyToTerr(int addedArmyCount, boolean isMatchedCardTerrArmy) {
        if ((this.getTerritoryOwner().getAvailableArmyCount() >= addedArmyCount) || isMatchedCardTerrArmy) {
            this.armyCount += addedArmyCount;
            if (!isMatchedCardTerrArmy)
                this.getTerritoryOwner().setAvailableArmyCount(this.getTerritoryOwner().getAvailableArmyCount() - addedArmyCount);
            else
                this.getTerritoryOwner().setAvailableCardTerrCount(this.getTerritoryOwner().getAvailableCardTerrCount() - addedArmyCount);
            String message=addedArmyCount+" armies added to "+this.getTerritoryName();
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            try {
                FileManager.getInstance().writeLog(message);
            } catch (Exception ex) {

            }
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ARMY_ADDED_SUCCESS);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ARMY_ADDED_FAILURE);
    }

    /**
     * Method is used to implement the fortification functionality
     * @param destTerritory the destination territory for fortification
     * @param currentPlayer the player who has requested fortification
     * @param countOfArmy number of armies to be moved
     * @return response message
     */
    public ConfigurableMessage fortifyTerritory(Territory destTerritory, Player currentPlayer, int countOfArmy) {
        if (this.getArmyCount() > countOfArmy && this.getTerritoryOwner().getPlayerId() == currentPlayer.getPlayerId()) {
            Boolean neighbourFlag = false;
            for (Territory obj : this.getNeighbourList()) {
                if (obj.getTerritoryName().equalsIgnoreCase(destTerritory.getTerritoryName())) {
                    this.armyCount -= countOfArmy;
                    destTerritory.armyCount += countOfArmy;
                    neighbourFlag = true;
                    break;
                }
            }
            if (neighbourFlag == true) {
                String message=this.getTerritoryName()+" has been fortified with "+countOfArmy+" armies.";
                PhaseViewModel.getInstance().addPhaseViewContent(message);
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_NEIGHBOUR_FAILURE);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE);
    }

    /**
     * Returns the name of territory
     * @return territoryName : name of the territory name is available
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * Sets the name of territory
     * @param territoryName : name of the territory is defined using this parameter
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }
    /**
     * Gives the center point
     * @return centerPoint : gives the center point
     */
    public Point getCenterPoint() {
        return centerPoint;
    }

    /**
     * Sets the center point
     * @param centerX : this parameter is for the X coordinate of the point
     * @param centerY : this parameter is for the Y coordinate of the point
     */
    public void setCenterPoint(int centerX, int centerY) {
        this.centerPoint = new Point(centerX, centerY);
    }

    /**
     * Returns the name of continent
     * @return continent : returns the name of continent
     */
    public Continent getContinent() {
        return continent;
    }
    /**
     * Sets  the name of continent
     * @return continentName : returns the name of continent
     */
    public void setContinent(Continent continent) {
        this.continent = continent;
    }
    /**
     * Returns the list of neighbour
     * @return neighbourList : list of neighbour is available
     */
    public List<Territory> getNeighbourList() {
        return neighbourList;
    }

    /**
     * Sets the neighbour list
     * @param neighbourList : list of neightbour is available
     */
    public void setNeighbourList(List<Territory> neighbourList) {
        this.neighbourList = neighbourList;
    }

    /**
     * Returns the owner of territory
     * @return territoryOwner : owner of the territory is returned
     */
    public Player getTerritoryOwner() {
        return territoryOwner;
    }

    /**
     * Sets the owner of territory
     * @param territoryOwner : owner of the territory is defined using this parameter
     */
    public void setTerritoryOwner(Player territoryOwner) {
        this.territoryOwner = territoryOwner;
    }

    /**
     * Returns the count of army
     * @return armyCount : number of army is returned
     */
    public int getArmyCount() {
        return armyCount;
    }

    /**
     * Sets the count of army
     * @param armyCount : number of army is set using this parameter
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    /**
     * Sets the center point
     * @param centerPoint : center point for the touch is defined
     */
    private void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }

}
