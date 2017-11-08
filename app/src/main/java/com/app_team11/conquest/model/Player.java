package com.app_team11.conquest.model;

import android.util.Log;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;


/**
 * Player model class with cards, score and owned territories information
 * Created by Vasu on 08-10-2017.
 *
 * @version 1.0.0
 */

public class Player extends Observable {

    private static final String TAG = Player.class.getSimpleName();
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
     *
     * @return
     */
    public boolean isMyTurn() {
        return isMyTurn;
    }

    /**
     * Gives turn to the player
     *
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
        PhaseViewModel.getInstance().clearString();
        PhaseViewModel.getInstance().addPhaseViewContent(message);
        if(reinforcementCount.getMatchedTerrCardReinforcement()!=0) {
            String message2 = "Matched Territory Armies: " + reinforcementCount.getMatchedTerrCardReinforcement();
            PhaseViewModel.getInstance().addPhaseViewContent(message2);
        }
        return reinforcementCount;
    }


    /**
     * Checks whether player is attacking an already owned territory
     * @param attackerTerritory
     * @param defenderTerritory
     * @return whether adjacent or not
     */

    public ConfigurableMessage isAdjacentTerritory(Territory attackerTerritory, Territory defenderTerritory) {
        if ((attackerTerritory.getNeighbourList().contains(defenderTerritory)) && (!attackerTerritory.getTerritoryOwner().equals(defenderTerritory.getTerritoryOwner()))) {
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
    public ConfigurableMessage validateAttackBetweenTerritories(Territory attackerTerritory, Territory defenderTerritory) {
        ConfigurableMessage isAdjacenTerritories = isAdjacentTerritory(attackerTerritory, defenderTerritory);
        ConfigurableMessage hasSufficientArmiesForAttack = hasSufficientArmies(attackerTerritory);
        ConfigurableMessage canContinueAttack = canContinueAttackOnThisTerritory(defenderTerritory);

        if (isAdjacenTerritories.getMsgCode() == 0)
            return isAdjacenTerritories;
        else if (hasSufficientArmiesForAttack.getMsgCode() == 0) {
            return hasSufficientArmiesForAttack;
        } else if (canContinueAttack.getMsgCode() == 0) {
            return canContinueAttack;
        } else {
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
    public ConfigurableMessage attackPhase(Territory attackerTerritory, Territory defenderTerritory, int attackerDice, int defenderDice) {
        setNumberOfDiceRolled(attackerDice);
        if (attackerTerritory.getArmyCount() <= attackerDice) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACKER_DICE);
        } else if (defenderTerritory.getArmyCount() < defenderDice) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.CHOOSE_LESS_NUMBER_DICE);
        }
        PhaseViewModel.getInstance().clearString();
        String messageAttack = "Attacking City "+attackerTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(messageAttack);
        String messageDefender = "Attacked City "+defenderTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(messageDefender);

        //Validations are true for attacking and hence proceeding with logic
        //Load dice values
        List<Integer> attackerDiceValues = getRandomDiceValues(attackerDice);
        List<Integer> defenderDiceValues = getRandomDiceValues(defenderDice);

        try {
            Log.e(TAG, "Attacker Dice:" + attackerDiceValues.toString());
            Log.e(TAG, "Defender Dice:" + defenderDiceValues.toString());
        }
        catch(Exception e)
        {

        }
        int attackerDiceValue = 0, defenderDiceValue = 0;
        int attackerWonCounter = 0;

        Collections.sort(attackerDiceValues, Collections.reverseOrder());
        Collections.sort(defenderDiceValues, Collections.reverseOrder());

        String messageAttackDice = "Attacker Dice "+Arrays.toString(attackerDiceValues.toArray());
        PhaseViewModel.getInstance().addPhaseViewContent(messageAttackDice);
        String messageDefenderDice = "Defender Dice "+Arrays.toString(defenderDiceValues.toArray());
        PhaseViewModel.getInstance().addPhaseViewContent(messageDefenderDice);

        while (defenderDiceValues.size() > 0) {
            attackerDiceValue = attackerDiceValues.get(0);
            defenderDiceValue = defenderDiceValues.get(0);

            //if attacker wins
            if (attackerDiceValue > defenderDiceValue) {
                defenderTerritory.setArmyCount(defenderTerritory.getArmyCount() - 1);
                attackerWonCounter++;
            } else {
                attackerTerritory.setArmyCount(attackerTerritory.getArmyCount() - 1);
                attackerWonCounter--;
            }
            attackerDiceValues.remove(0);
            defenderDiceValues.remove(0);
        }
        if (attackerWonCounter > 0) {
            String message = "Player"+attackerTerritory.getTerritoryOwner().getPlayerId()+" won";
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ATTACKER_WON);
        } else {
            String message = "Player"+defenderTerritory.getTerritoryOwner().getPlayerId()+" won";
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACKER_LOST);

        }
    }

    private int numberOfDiceRolled;

    public int getNumberOfDiceRolled() {
        return numberOfDiceRolled;
    }

    public void setNumberOfDiceRolled(int numberOfDiceRolled) {
        this.numberOfDiceRolled = numberOfDiceRolled;
    }

    /**
     * This method is for conditions related to capturing a territory
     * @param attackerTerritory
     * @param defenderTerritory
     * @param moveArmiesToCapturedTerritory
     * @return
     */
    public ConfigurableMessage captureTerritory(Territory attackerTerritory, Territory defenderTerritory, int moveArmiesToCapturedTerritory) {
        if (attackerTerritory.getArmyCount() - moveArmiesToCapturedTerritory == 0) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.LEAVE_ONE_ARMY);
        } else if (defenderTerritory.getArmyCount() == 0 && moveArmiesToCapturedTerritory < this.numberOfDiceRolled)
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.PLACE_MORE_ARMIES);
        else {
            attackerTerritory.setArmyCount(attackerTerritory.getArmyCount() - moveArmiesToCapturedTerritory);
            defenderTerritory.setArmyCount(moveArmiesToCapturedTerritory);
            defenderTerritory.setTerritoryOwner(attackerTerritory.getTerritoryOwner());
        }
        String message = "Player " + attackerTerritory.getTerritoryOwner().getPlayerId() + " has captured " + defenderTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(message);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }


    /**
     * This method returns the highest value from a given list.
     * @param diceArray
     * @return Maximum element from the list
     */
    public int getHighestValue(List<Integer> diceArray) {
        Collections.sort(diceArray, Collections.reverseOrder());
        return diceArray.get(0);
    }

    /**
     * This method deletes the element from the list.
     * @param diceArray
     * @param element
     * @return the modified list
     */
    public int[] deleteElement(int diceArray[], int element) {
        for (int i = 0; i < diceArray.length; i++) {
            if (diceArray[i] == element) {
                for (int j = i; j < (diceArray.length - 1); j++) {
                    diceArray[j] = diceArray[j + 1];
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
    public static List<Integer> getRandomDiceValues(int arraySize) {
        List<Integer> randomArray = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            randomArray.add(1 + new Random().nextInt(6));
        }
        return randomArray;
    }

    /**
     * Method is used to implement the fortification functionality
     * @param destTerritory the destination territory for fortification
     * @param countOfArmy number of armies to be moved
     * @return response message
     */
    public ConfigurableMessage fortifyTerritory(Territory fromTerritory,Territory destTerritory, int countOfArmy) {
        if (fromTerritory.getArmyCount() > countOfArmy && fromTerritory.getTerritoryOwner().getPlayerId() == this.getPlayerId()) {
            Boolean neighbourFlag = false;
            for (Territory obj : fromTerritory.getNeighbourList()) {
                if (obj.getTerritoryName().equalsIgnoreCase(destTerritory.getTerritoryName())) {
                    fromTerritory.setArmyCount(fromTerritory.getArmyCount() - countOfArmy);
                    destTerritory.setArmyCount(destTerritory.getArmyCount() + countOfArmy);
                    neighbourFlag = true;
                    break;
                }
            }
            if (neighbourFlag == true) {
                String message=fromTerritory.getTerritoryName()+" has been fortified with "+countOfArmy+" armies.";
                PhaseViewModel.getInstance().clearString();
                PhaseViewModel.getInstance().addPhaseViewContent(message);
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_NEIGHBOUR_FAILURE);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE);
    }


}
