package com.app_team11.conquest.model;

import java.util.List;

/**
 * Created by Vasu on 08-10-2017.
 * @version 1.0.0
 */

public class ReinforcementType {

    private int matchedTerrCardReinforcement;
    private int otherTotalReinforcement;
    private List<Territory> matchedTerritoryList;

    public List<Territory> getMatchedTerritoryList() {
        return matchedTerritoryList;
    }

    public void setMatchedTerritoryList(List<Territory> matchedTerritoryList) {
        this.matchedTerritoryList = matchedTerritoryList;
    }

    public int getMatchedTerrCardReinforcement() {
        return matchedTerrCardReinforcement;
    }

    public void setMatchedTerrCardReinforcement(int matchedTerrCardReinforcement) {
        this.matchedTerrCardReinforcement = matchedTerrCardReinforcement;
    }

    public int getOtherTotalReinforcement() {
        return otherTotalReinforcement;
    }

    public void setOtherTotalReinforcement(int otherTotalReinforcement) {
        this.otherTotalReinforcement = otherTotalReinforcement;
    }
}
