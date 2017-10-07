package com.app_team11.conquest.model;


import android.graphics.Point;

import com.app_team11.conquest.global.ConfigurableMessage;
import com.app_team11.conquest.global.Constants;

import java.util.ArrayList;
import java.util.List;


public class Territory {

    private String territoryName;
    private Point centerPoint;
    private Continent continent;
    private List<Territory> neighbourList;

    public Territory(String territoryName, int centerX, int centerY, Continent continent) {
        this.territoryName = territoryName;
        this.centerPoint = new Point(centerX,centerY);
        this.continent = continent;
        this.neighbourList = new ArrayList();
    }

    /**
     * to be called on click of add neighbours/connections
     *  validation1 before saving a map - Validation to check if the number of neighbours not greater than 10
     * @param terrObj
     * @return confirmationMessage
     */
    public ConfigurableMessage addRemoveNeighbourToTerr(Territory terrObj, char addRemoveFlag)
    {
        if(addRemoveFlag == 'A') {
            if (this.neighbourList.size() <= 9) {
                this.neighbourList.add(terrObj);
                return new ConfigurableMessage(Constants.MSGSUCCCODE,Constants.ADDREMTOLISTSUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSGFAILCODE,Constants.NEIGHBOURSIZEVALFAIL);

        }
        else if(addRemoveFlag == 'R')
        {
            if (this.neighbourList.size() >= 2 ) {
                this.neighbourList.remove(terrObj);
                return new ConfigurableMessage(Constants.MSGSUCCCODE,Constants.ADDREMTOLISTSUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSGFAILCODE,Constants.NEIGHBOURSIZEVALFAIL);
        }
        else
            return new ConfigurableMessage(Constants.MSGFAILCODE,Constants.INCORRECTFLAG);
    }

    /**
     * validation1 before saving a map - Validation to check if the number of neighbours not greater than 10
     * @param terrList
     * @return confirmationMessage
     */
    public ConfigurableMessage addNeighbourToTerr(List<Territory> terrList)
    {
        this.neighbourList.addAll(terrList);
        if(this.neighbourList.size()<=10) {
            return new ConfigurableMessage(Constants.MSGSUCCCODE,Constants.ADDREMTOLISTSUCCESS);
        }
        else
            return new ConfigurableMessage(Constants.MSGFAILCODE,Constants.NEIGHBOURSIZEVALFAIL);

    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }

    public Continent getContinent() {
           return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public List<Territory> getNeighbourList() {
        return neighbourList;
    }

    public void setNeighbourList(List<Territory> neighbourList) {
        this.neighbourList = neighbourList;
    }
}
