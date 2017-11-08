package com.app_team11.conquest.model;


import android.graphics.Point;

import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Territory model class with name of territory,position,neighbours,owner,army count,etc
 * Created by Vasu on 06-10-2017.
 * @version 1.0.0
 */
public class Territory extends Observable{

    private String territoryName;
    private Point centerPoint;
    private Continent continent;
    private List<Territory> neighbourList;
    private Player territoryOwner;
    private int armyCount;
    public Boolean isVisited=false;

    /**
     * Territory declaration
     * @param territoryName
     */
    public Territory(String territoryName) {
        this.territoryName = territoryName;
        this.neighbourList = new ArrayList();
    }

    /**
     * variables declaration for territory
     * @param territoryName
     * @param centerX
     * @param centerY
     * @param continent
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
     * @return confirmationMessage
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
     * @param terrList
     */
    public void addNeighbourToTerr(List<Territory> terrList) {
        for (Territory objTerr : terrList) {
            addRemoveNeighbourToTerr(objTerr, 'A');
        }
    }

    /**
     * Method to add armies in territory selected and remove the same count from player
     *
     * @param addedArmyCount count of armies to be added
     * @return custom message
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
     * @return territory Name
     */
    public String getTerritoryName() {
        return territoryName;
    }

    /**
     * Sets the name of territory
     * @param territoryName
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }
    /**
     * Gives the center point
     * @return gives the center point
     */
    public Point getCenterPoint() {
        return centerPoint;
    }

    /**
     * Sets the center point
     * @param centerX
     * @param centerY
     */
    public void setCenterPoint(int centerX, int centerY) {
        this.centerPoint = new Point(centerX, centerY);
    }

    /**
     * Returns the name of continent
     * @return returns the name of continent
     */
    public Continent getContinent() {
        return continent;
    }
    /**
     * Returns the name of continent
     * @return returns the name of continent
     */
    public void setContinent(Continent continent) {
        this.continent = continent;
    }
    /**
     * Sets the list of neighbour
     * @return neighbourList
     */
    public List<Territory> getNeighbourList() {
        return neighbourList;
    }

    /**
     * Sets the neighbour list
     * @param neighbourList
     */
    public void setNeighbourList(List<Territory> neighbourList) {
        this.neighbourList = neighbourList;
    }

    /**
     * Returns the owner of territory
     * @return territoryOwner
     */
    public Player getTerritoryOwner() {
        return territoryOwner;
    }

    /**
     * Sets the owner of territory
     * @param territoryOwner
     */
    public void setTerritoryOwner(Player territoryOwner) {
        this.territoryOwner = territoryOwner;
    }

    /**
     * Returns the count of army
     * @return armyCount
     */
    public int getArmyCount() {
        return armyCount;
    }

    /**
     * Sets the count of army
     * @param armyCount
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }

    /**
     * Sets the center point
     * @param centerPoint
     */
    private void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }




}
