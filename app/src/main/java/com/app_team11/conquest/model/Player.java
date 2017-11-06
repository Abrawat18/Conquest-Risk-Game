package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;


/**
 * Player model class with cards, score and owned territories information
 * Created by Vasu on 08-10-2017.
 *
 * @version 1.0.0
 */

public class Player extends Observable{

    private int playerId;
    private int availableArmyCount;
    private int availableCardTerrCount;
    private List<Cards> ownedCards = new ArrayList<Cards>();
    private Boolean cardTradeIn = false;
    private boolean isMyTurn;

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
     *
     * @param gameMap
     * @param cardTradeCount
     * @param tradeInCards
     * @return Reinforcement Army Count
     */
    public ReinforcementType calcReinforcementArmy(GameMap gameMap, int cardTradeCount, List<Cards> tradeInCards) {
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
            //// TODO: 08-10-2017 need to add the selected cards back to the total cards list
            //// TODO: 17-10-2017 what happens when the total cards in the inventory finishes and there is no card left to give to the player when he wins any territory
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
        reinforcementCount.setOtherTotalReinforcement(territoryArmy + continentArmy + cardArmy);
        return reinforcementCount;
    }


    /**
     * Checks whether player is attacking an already owned territory
     * @param attackerTerritory
     * @param defenderTerritory
     * @return
     */

    public ConfigurableMessage isAdjacentTerritory(Territory attackerTerritory, Territory defenderTerritory) {

        for (Territory neigbourTerritory : defenderTerritory.getNeighbourList()) {
            if (attackerTerritory.getTerritoryName().equals(neigbourTerritory.getTerritoryName()) && attackerTerritory.getTerritoryOwner() != neigbourTerritory.getTerritoryOwner())
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NOT_ADJACENT_TERRITORY);
    }

    /**
     * Check for sufficient armies
     * @param attackerTerritory
     * @return
     */
    public ConfigurableMessage hasSufficientArmies(Territory attackerTerritory) {
        if (attackerTerritory.getArmyCount() >= 2)
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.INSUFFUCIENT_ARMIES);
    }

    /**
     * Checks whether attack can be continued
     * @param defenderTerritory
     * @return
     */
    public ConfigurableMessage canContinueAttackOnThisTerritory(Territory defenderTerritory) {
        if (defenderTerritory.getArmyCount() == 0)
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NO_ARMIES);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }

    /**
     * Validate the attack
     * @param attackerTerritory
     * @param defenderTerritory
     * @return
     */
    public ConfigurableMessage validateAttackBetweenTerritories(Territory attackerTerritory, Territory defenderTerritory)
    {
        ConfigurableMessage isAdjacenTerritories = isAdjacentTerritory(attackerTerritory, defenderTerritory);
        ConfigurableMessage hasSufficientArmiesForAttack = hasSufficientArmies(attackerTerritory);
        ConfigurableMessage canContinueAttack = canContinueAttackOnThisTerritory(defenderTerritory);

        if (isAdjacenTerritories.getMsgCode()==0)
            return isAdjacenTerritories;
        else if(hasSufficientArmiesForAttack.getMsgCode()==0)
        {
            return hasSufficientArmiesForAttack;
        }
         else if(canContinueAttack.getMsgCode()==0)
        {
            return canContinueAttack;
        }
        else
        {
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        }
    }


    /**
     *  The attack phase method
     * @param attackerTerritory
     * @param defenderTerritory
     * @param attackerDice
     * @param defenderDice
     */
    public void attackPhase(Territory attackerTerritory, Territory defenderTerritory, int attackerDice,int defenderDice)
    {
        int attackerDiceValues[]=getRandomDiceValues(attackerDice);
        int defenderDiceValues[]=getRandomDiceValues(defenderDice);
        int attackerDiceValue=0,defenderDiceValue=0;

        ConfigurableMessage canAttack = validateAttackBetweenTerritories(defenderTerritory, attackerTerritory);
        //check if validations are true
        if (canAttack.getMsgText() == "SUCCESS" && attackerTerritory.getArmyCount() + 1 > attackerDice) {
            //Load dice values


            //check for each Dice value of attacker and defender
            for (int i = 0; i < attackerDiceValues.length; i++) {
                attackerDice = getHighestValue(attackerDiceValues);
                Boolean loop = true;

                for (int j = 0; j < defenderDiceValues.length && loop == true; j++) {
                    defenderDiceValue = getHighestValue(defenderDiceValues);
                    if (attackerDiceValue > defenderDiceValue) {
                        defenderTerritory.setArmyCount(defenderTerritory.getArmyCount() - 1);
                        if (defenderTerritory.getArmyCount() == 0) {
                            defenderTerritory.setTerritoryOwner(attackerTerritory.getTerritoryOwner());
                            //defenderTerritory.setArmyCount(defenderTerritory.getArmyCount()+attackerDice);
                            loop = false;
                        }
                    } else {
                        attackerTerritory.setArmyCount(attackerTerritory.getArmyCount() - 1);
                    }

                    if (attackerDiceValues.length > 0 && defenderDiceValues.length > 0) {
                        attackerDiceValues = deleteElement(attackerDiceValues, attackerDiceValue);
                        defenderDiceValues = deleteElement(defenderDiceValues, defenderDiceValue);
                    } else {
                        loop = false;
                    }

                }


            }
        }
    }

    /**
     * This method returns the highest value from a given list.
     * @param diceArray
     * @return Maximum element from the list
     */
    public int getHighestValue(int diceArray[])
    {
        int max=diceArray[0];
        for (int counter = 1; counter < diceArray.length; counter++)
        {
            if (diceArray[counter] > max)
            {
                max = diceArray[counter];
            }
        }
        return max;
    }

    /**
     * This method deletes the element from the list.
     * @param diceArray
     * @param element
     * @return the modified list
     */
    public int[] deleteElement(int diceArray[],int element)
    {
        for(int i=0; i<diceArray.length; i++)
        {
            if(diceArray[i] == element)
            {
                for(int j=i; j<(diceArray.length-1); j++)
                {
                    diceArray[j] = diceArray[j+1];
                }
                break;
            }
        }
        return diceArray;
    }


    /**
     * Generates random dice values depending on number of attacker/defender dice
     * @param arraySize
     * @return randomly generated dice array
     */
    public static int[] getRandomDiceValues(int arraySize)
    {
        int[] diceValues={1,2,3,4,5,6};
        int[] randomArray=new int[arraySize];
        int randomNumber=0;
        for(int i=0;i<arraySize;i++)
        {
            randomNumber=new Random().nextInt(diceValues.length);
            randomArray[i]=randomNumber;
        }
        return randomArray;
    }



}
