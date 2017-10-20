package com.app_team11.conquest.model;

/**
 * Card model for defining the card object.
 * Created by Vasu on 08-10-2017.
 * @version 1.0.0
 */

public class Cards {
    private Territory cardTerritory;
    private String armyType;

    // getters and setters of class method
    public Territory getCardTerritory() {
        return cardTerritory;
    }

    public void setCardTerritory(Territory cardTerritory) {
        this.cardTerritory = cardTerritory;
    }

    public String getArmyType() {
        return armyType;
    }

    public void setArmyType(String armyType) {
        this.armyType = armyType;
    }
}
