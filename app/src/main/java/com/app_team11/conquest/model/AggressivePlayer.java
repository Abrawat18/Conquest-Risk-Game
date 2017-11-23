package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Created by Nigel on 22-Nov-17.
 */

public class AggressivePlayer extends Observable
{
    private static final String TAG = Player.class.getSimpleName();
    private int playerId;
    private int availableArmyCount;
    private int availableCardTerrCount;
    private List<Cards> ownedCards = new ArrayList<Cards>();
    private Boolean cardTradeIn = false;
    private boolean isMyTurn;

    /**
     * Returns the available card territory count
     * @return availableCardTerrCount
     */
    public int getAvailableCardTerrCount() {
        return availableCardTerrCount;
    }

    public void setAvailableCardTerrCount(int availableCardTerrCount) {
        this.availableCardTerrCount = availableCardTerrCount;
    }

    /**
     * Method to check if the player has got his turn
     * @return
     */
    public boolean isMyTurn() {
        return isMyTurn;
    }

    /**
     * Gives turn to the player
     * @param myTurn
     */
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    /**
     * Returns the player ID
     * @return playerID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the ID of the player
     * @param playerId
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the available army count
     * @return availableArmyCount
     */
    public int getAvailableArmyCount() {
        return availableArmyCount;
    }

    /**
     * Sets the available army count
     * @param availableArmyCount
     */
    public void setAvailableArmyCount(int availableArmyCount) {
        this.availableArmyCount = availableArmyCount;
    }

    /**
     * Returns the Owned cards
     * @return ownedCards
     */
    public List<Cards> getOwnedCards() {
        return ownedCards;
    }

    /**
     * Sets the owned cards
     * @param ownedCards
     */
    public void setOwnedCards(List<Cards> ownedCards) {
        this.ownedCards = ownedCards;
    }

    /**
     * Add owned cards
     * @param addCards
     */
    public void addOwnedCards(List<Cards> addCards) {
        this.getOwnedCards().addAll(addCards);
    }

    /**
     * Returns the trade in cards
     * @return cardTradeIn
     */
    public Boolean getCardTradeIn() {
        return cardTradeIn;
    }

    /**
     * Sets the trade in cards
     * @param cardTradeIn
     */
    public void setCardTradeIn(Boolean cardTradeIn) {
        this.cardTradeIn = cardTradeIn;
    }

    /**
     * Method to check if the player can trade in the cards
     */
    public void checkCardsForTradeIn() {
        int infantryCount = 0;
        int cavalryCount = 0;
        int artilleryCount = 0;

        for (Cards obj : this.ownedCards) {
            if (null != obj.getArmyType() && obj.getArmyType().toString().equalsIgnoreCase(Constants.ARMY_INFANTRY)) {
                infantryCount++;
            } else if (null != obj.getArmyType() && obj.getArmyType().toString().equalsIgnoreCase(Constants.ARMY_CAVALRY)) {
                cavalryCount++;
            } else if (null != obj.getArmyType() && obj.getArmyType().toString().equalsIgnoreCase(Constants.ARMY_ARTILLERY)) {
                artilleryCount++;
            }

        }
        if (infantryCount >= 3 || cavalryCount >= 3 || artilleryCount >= 3 || (infantryCount >= 1 && cavalryCount >= 1 && artilleryCount >= 1))
            this.cardTradeIn = true;

    }

    /**
     * This method is used to calculate the total number of reinforcement armies
     * @param gameMap
     * @param cardTradeCount
     * @param tradeInCards
     * @return Reinforcement Army Count
     */
    public ReinforcementType    calcReinforcementArmy(GameMap gameMap, int cardTradeCount, List<Cards> tradeInCards) {
        int ownedTerritoryCount = 0;
        int territoryArmy = 3;
        int continentArmy = 0;
        int cardArmy = 0;
        ReinforcementType reinforcementCount = new ReinforcementType();
        boolean ownedTerritoryCard = false;
        for (Territory terrObj : gameMap.getTerritoryList()) {
            if (terrObj.getTerritoryOwner().getPlayerId() == this.playerId) {
                ownedTerritoryCount++;
            }
        }
        if (ownedTerritoryCount > 9) //calculating territory army only when owned territory count > 9, else default value is set to 3
            territoryArmy = ownedTerritoryCount / 3;

        for (Continent contObj : gameMap.getContinentList()) {
            if (contObj.getContOwner() != null && contObj.getContOwner().getPlayerId() == this.playerId) {
                continentArmy += contObj.getScore();
            }
        }

        if (tradeInCards != null && tradeInCards.size() == 3) {

            if ((tradeInCards.get(0).getArmyType().equals(tradeInCards.get(1).getArmyType()) && tradeInCards.get(1).getArmyType().equals(tradeInCards.get(2).getArmyType())) || (!tradeInCards.get(0).getArmyType().equals(tradeInCards.get(1).getArmyType()) && !tradeInCards.get(1).getArmyType().equals(tradeInCards.get(2).getArmyType()) && !tradeInCards.get(2).getArmyType().equals(tradeInCards.get(1).getArmyType()))) {
                switch (cardTradeCount) {
                    case 1:
                        cardArmy = 4;
                        break;
                    case 2:
                        cardArmy = 6;
                        break;
                    case 3:
                        cardArmy = 8;
                        break;
                    case 4:
                        cardArmy = 10;
                        break;
                    case 5:
                        cardArmy = 12;
                        break;
                    case 6:
                        cardArmy = 15;
                        break;
                }
                if (cardTradeCount > 6)
                    cardArmy = 15 + (cardTradeCount - 6) * 5; //for trade count more than 6

                List<Territory> matchedTerr = new ArrayList<Territory>();
                for (Territory terrObj : gameMap.getTerritoryList()) {
                    for (Cards cardObj : tradeInCards) {
                        if ((terrObj.getTerritoryOwner().getPlayerId() == this.getPlayerId()) && terrObj.getTerritoryName().equalsIgnoreCase(cardObj.getCardTerritory().getTerritoryName())) {
                            reinforcementCount.setMatchedTerrCardReinforcement(2); //adding the count of armies for cards having the territory owned by the player
                            matchedTerr.add(terrObj);
                        }
                    }
                    reinforcementCount.setMatchedTerritoryList(matchedTerr);
                }
                this.getOwnedCards().removeAll(tradeInCards);
                setChanged();
                notifyObservers(this);
            }
        }
        reinforcementCount.setOtherTotalReinforcement(territoryArmy + continentArmy + cardArmy);
        String message = "Armies reinforced: " + reinforcementCount.getOtherTotalReinforcement();
        try {
            FileManager.getInstance().writeLog(message);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        PhaseViewModel.getInstance().addPhaseViewContent(message);
        if (reinforcementCount.getMatchedTerrCardReinforcement() != 0) {
            String message2 = "Matched Territory Armies: " + reinforcementCount.getMatchedTerrCardReinforcement();
            try {
                FileManager.getInstance().writeLog(message2);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            PhaseViewModel.getInstance().addPhaseViewContent(message2);
        }
        return reinforcementCount;
    }

    /**
     * Method to find territories of the agressive player
     * @param map
     * @param player
     * @return the related territories of a player
     */
    public List<Territory> getPlayerTerritories(GameMap map,Player player)
    {
        List<Territory> playerTerritories=new ArrayList<Territory>();
        for(Territory territory:map.getTerritoryList())
            if(territory.getTerritoryOwner().getPlayerId()==player.getPlayerId())
                playerTerritories.add(territory);
        return playerTerritories;
    }

    public Territory getStrongestTerritory(List<Territory> playerTerritoryList)
    {
        Territory strongestTerritory=null;
        List<Integer> armyList=new ArrayList<Integer>();
        for(Territory territory:playerTerritoryList)
        {
            armyList.add(territory.getArmyCount());
        }
        for(Territory territory:playerTerritoryList)
        {
            if(territory.getArmyCount()== Collections.max(armyList))
            {
                strongestTerritory=territory;
                break;
            }
        }
        return strongestTerritory;
    }





}
