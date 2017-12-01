package com.app_team11.conquest.model;

import java.io.Serializable;

/**
 * Card model for defining the card object.
 * Created by Vasu on 08-10-2017.
 * @version 1.0.0
 */

public class Cards implements Serializable{
    private Territory cardTerritory;
    private String armyType;
    private boolean isSelected;

    /**
     * If the card is selected or not
     * @return Checks if the card is selected or not
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Sets the selected card
     * @param selected checks if the card is selected or not
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Card parameters declaration
     * @param cardTerritory territory for a card
     * @param armyType type of army
     */
    public Cards(Territory cardTerritory, String armyType) {
        this.cardTerritory=cardTerritory;
        this.armyType=armyType;
    }

    /**
     * Returns the territory in card
     * @return cardTerritory card associated with territory
     */
    public Territory getCardTerritory() {
        return cardTerritory;
    }

    /**
     * Sets the territory in the card
     * @param cardTerritory card associated with the territory
     */
    public void setCardTerritory(Territory cardTerritory) {
        this.cardTerritory = cardTerritory;
    }

    /**
     * Returns the type of army
     * @return armyType type of army
     */
    public String getArmyType() {
        return armyType;
    }

    /**
     *Sets the type of army
     * @param armyType type of army
     */
    public void setArmyType(String armyType) {
        this.armyType = armyType;
    }
}
