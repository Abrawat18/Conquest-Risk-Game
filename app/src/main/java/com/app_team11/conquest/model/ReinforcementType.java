package com.app_team11.conquest.model;

import java.util.List;

/**
 * This is model code responsible for storing the reinforcement armies based on card and armies from other sources
 * Created by Vasu on 08-10-2017.
 * @version 1.0.0
 */

public class ReinforcementType {

    private int matchedTerrCardReinforcement;
    private int otherTotalReinforcement;
    private List<Territory> matchedTerritoryList;

    /**
     * Returns the matched territory list
     * @return MatchedTerritoryList
     */
    public List<Territory> getMatchedTerritoryList() {
        return matchedTerritoryList;
    }

    /**
     * Sets the matched territory list
     * @param matchedTerritoryList
     */
    public void setMatchedTerritoryList(List<Territory> matchedTerritoryList) {
        this.matchedTerritoryList = matchedTerritoryList;
    }

    /**
     * Returns the reinforcement matched territory card
     * @return matchedTerrCardReinforcement
     */
    public int getMatchedTerrCardReinforcement() {
        return matchedTerrCardReinforcement;
    }

    /**
     * Sets the reinforcement matched territory card
     * @param matchedTerrCardReinforcement
     */
    public void setMatchedTerrCardReinforcement(int matchedTerrCardReinforcement) {
        this.matchedTerrCardReinforcement = matchedTerrCardReinforcement;
    }

    /**
     * Returns the other total reinforcement
     * @return otherTotalReinforcement
     */
    public int getOtherTotalReinforcement() {
        return otherTotalReinforcement;
    }

    /**
     * Sets other total reinforcement
     * @param otherTotalReinforcement
     */
    public void setOtherTotalReinforcement(int otherTotalReinforcement) {
        this.otherTotalReinforcement = otherTotalReinforcement;
    }
}
