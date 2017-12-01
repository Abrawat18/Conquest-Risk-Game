package com.app_team11.conquest.model;

import android.util.Log;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.interfaces.PlayerStrategyListener;
import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.utility.FileManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;


/**
 * Player model class with cards, score and owned territories information
 * Created by Vasu on 08-10-2017.
 *
 * @version 1.0.0
 */

public class Player extends Observable implements Serializable {

    private static final String TAG = Player.class.getSimpleName();
    private int playerId;
    private int availableArmyCount;
    private int availableCardTerrCount;
    private List<Cards> ownedCards = new ArrayList<Cards>();
    private Boolean cardTradeIn = false;
    private boolean isMyTurn;
    transient private PlayerStrategyListener playerStrategy;
    private String playerStrategyType;
    private boolean isActive = true;

    /**
     * Check for player's active state
     *
     * @return true : is player still active in game
     * false : if game over for player
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Setting player active state
     *
     * @param active : will set player active state
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * method to get the player strategy type string
     * @return player strategy type
     */
    public String getPlayerStrategyType() {
        return playerStrategyType;
    }

    /**
     * method to set the player strategy type string
     * @param playerStrategyType :  set player strategy type
     */
    public void setPlayerStrategyType(String playerStrategyType) {
        this.playerStrategyType = playerStrategyType;
    }

    /**
     * Method to get player strategy
     * @return the player's strategy
     */
    public PlayerStrategyListener getPlayerStrategy() {
        return playerStrategy;
    }

    /**
     * Method to set player strategy
     * @param playerStrategy player to be set with this strategy
     */
    public void setPlayerStrategy(PlayerStrategyListener playerStrategy) {
        this.playerStrategy = playerStrategy;
    }

    /**
     * Returns the available card territory count
     *
     * @return availableCardTerrCount
     */
    public int getAvailableCardTerrCount() {
        return availableCardTerrCount;
    }

    /**
     * Method to set available card territory count
     * @param availableCardTerrCount for the player
     */
    public void setAvailableCardTerrCount(int availableCardTerrCount) {
        this.availableCardTerrCount = availableCardTerrCount;
    }

    /**
     * Method to check if the player has got his turn
     *
     * @return whether player's turn(true/false)
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
     *
     * @return playerID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the ID of the player
     *
     * @param playerId
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the available army count
     *
     * @return availableArmyCount
     */
    public int getAvailableArmyCount() {
        return availableArmyCount;
    }

    /**
     * Sets the available army count
     *
     * @param availableArmyCount army count to set the player with
     */
    public void setAvailableArmyCount(int availableArmyCount) {
        this.availableArmyCount = availableArmyCount;
    }

    /**
     * Returns the Owned cards
     *
     * @return ownedCards
     */
    public List<Cards> getOwnedCards() {
        return ownedCards;
    }

    /**
     * Sets the owned cards
     *
     * @param ownedCards player's owned cards
     */
    public void setOwnedCards(List<Cards> ownedCards) {
        this.ownedCards = ownedCards;
    }

    /**
     * Add owned cards
     *
     * @param addCards add cards to player's existing cards
     */
    public void addOwnedCards(List<Cards> addCards) {
        this.getOwnedCards().addAll(addCards);
    }

    /**
     * Returns the trade in cards
     *
     * @return cardTradeIn
     */
    public Boolean getCardTradeIn() {
        return cardTradeIn;
    }

    /**
     * Sets the trade in cards
     *
     * @param cardTradeIn whether cards have to be traded
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
     * @param gameMap map object
     * @param cardTradeCount card trade count
     * @param tradeInCards number of trade in cards
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
                gameMap.getCardList().addAll(tradeInCards);
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
     * Checks whether player is attacking an already owned territory
     *
     * @param attackerTerritory the attacker's territory
     * @param defenderTerritory the defender's territory
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
     *
     * @param attackerTerritory the attacker's territory
     * @return whether the territory has sufficient armies or not
     */
    public ConfigurableMessage hasSufficientArmies(Territory attackerTerritory) {
        if (attackerTerritory.getArmyCount() >= 2)
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
        return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.INSUFFUCIENT_ARMIES);
    }

    /**
     * Checks whether attack can be continued
     *
     * @param defenderTerritory the defender territory
     * @return whether attack can proceed on a territory
     */
    public ConfigurableMessage canContinueAttackOnThisTerritory(Territory defenderTerritory) {
        if (defenderTerritory.getArmyCount() == 0)
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.NO_ARMIES);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }

    /**
     * Validate the attack
     *
     * @param attackerTerritory the attacker's territory
     * @param defenderTerritory the defender's territory
     * @return ConfigurableMessage message for configurable message
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
     * Method to call respective startup phase of a player's strategy
     * @param gameMap map object
     * @return the respective strategy's startup phase
     */
    public ConfigurableMessage startupPhase(GameMap gameMap) {
        return getPlayerStrategy().startupPhase(gameMap, this);

    }

    /**
     * Method for reinforcement phase
     * @param gameMap map object
     * @return the reinforcement phase to be used according to strategy
     */
    public ConfigurableMessage reInforcementPhase(GameMap gameMap) {
        if (getPlayerStrategyType() != Constants.HUMAN_PLAYER_STRATEGY) {
            List<Cards> tradInCardList = null;
            List<Cards> tradInCardUniqueList = new ArrayList<>();
            List<Cards> tradInCardDistinctList = new ArrayList<>();
            ;
            if (this.getOwnedCards().size() >= 5) {
                List<Cards> ownCardList = getOwnedCards();
                HashMap<String, List<Cards>> tradInCardMapList = new HashMap<>();
                for (Cards cards : ownCardList) {
                    if(tradInCardMapList.containsKey(cards.getArmyType())) {
                        tradInCardMapList.get(cards.getArmyType()).add(cards);
                    }else {
                        List<Cards> armyCardList = new ArrayList<>();
                        armyCardList.add(cards);
                        tradInCardMapList.put(cards.getArmyType(),armyCardList);
                    }
                }

                Iterator it = tradInCardMapList.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    List<Cards> cardsList =(List<Cards>)pair.getValue();
                    if(cardsList.size()>=3){
                        tradInCardUniqueList.addAll(cardsList);
                        break;
                    }
                    tradInCardDistinctList.add(cardsList.get(0));
                    if(tradInCardDistinctList.size()>=3){
                        break;
                    }
                    it.remove();
                }

                if(tradInCardUniqueList.size() ==3){
                    tradInCardList = tradInCardUniqueList;
                }else{
                    tradInCardList = tradInCardDistinctList;
                }

            }

            ReinforcementType reinforcementType = calcReinforcementArmy(gameMap, gameMap.getNoOfCardTradedCount(), tradInCardList);
            return getPlayerStrategy().reInforcementPhase(reinforcementType, gameMap, this);
        }
        return null;
    }


    /**
     * Method to carry out strategic attack
     * @param gameMap map object
     * @return whether operation was successful
     */
    public ConfigurableMessage attackPhase(GameMap gameMap) {
        return getPlayerStrategy().attackPhase(gameMap, this);

    }

    /**
     * Method to carry out strategic fortification phase
     * @param gameMap map object
     * @return whether operation was successful
     */
    public ConfigurableMessage fortificationPhase(GameMap gameMap) {
        return getPlayerStrategy().fortificationPhase(gameMap, this);

    }


    /**
     * The attack phase method
     *
     * @param attackerTerritory the attacker's territory
     * @param defenderTerritory the defender's territory
     * @param attackerDice number of dice for attacker
     * @param defenderDice number of dice for defender
     * @return whether operation was successful
     */
    public ConfigurableMessage attackPhase(Territory attackerTerritory, Territory defenderTerritory, int attackerDice, int defenderDice) {
        setNumberOfDiceRolled(attackerDice);
        if (attackerTerritory.getArmyCount() <= attackerDice) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACKER_DICE);
        } else if (defenderTerritory.getArmyCount() < defenderDice) {
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.CHOOSE_LESS_NUMBER_DICE);
        }
        String messageAttack = "Attacking City " + attackerTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(messageAttack);
        String messageDefender = "Attacked City " + defenderTerritory.getTerritoryName();
        PhaseViewModel.getInstance().addPhaseViewContent(messageDefender);

        //Validations are true for attacking and hence proceeding with logic
        //Load dice values
        List<Integer> attackerDiceValues = getRandomDiceValues(attackerDice);
        List<Integer> defenderDiceValues = getRandomDiceValues(defenderDice);

        try {
            Log.e(TAG, "Attacker Dice:" + attackerDiceValues.toString());
            Log.e(TAG, "Defender Dice:" + defenderDiceValues.toString());
        } catch (Exception e) {

        }
        int attackerDiceValue = 0, defenderDiceValue = 0;
        int attackerWonCounter = 0;

        Collections.sort(attackerDiceValues, Collections.reverseOrder());
        Collections.sort(defenderDiceValues, Collections.reverseOrder());

        String messageAttackDice = "Attacker Dice " + Arrays.toString(attackerDiceValues.toArray());
        PhaseViewModel.getInstance().addPhaseViewContent(messageAttackDice);
        String messageDefenderDice = "Defender Dice " + Arrays.toString(defenderDiceValues.toArray());
        PhaseViewModel.getInstance().addPhaseViewContent(messageDefenderDice);
        try {
            FileManager.getInstance().writeLog(messageAttack);
            FileManager.getInstance().writeLog(messageDefender);
            FileManager.getInstance().writeLog(messageAttackDice);
            FileManager.getInstance().writeLog(messageDefenderDice);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        while (defenderDiceValues.size() > 0 && attackerDiceValues.size() > 0) {
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
            String message = "Player" + attackerTerritory.getTerritoryOwner().getPlayerId() + " won";
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            try {
                FileManager.getInstance().writeLog(message);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.ATTACKER_WON);
        } else {
            String message = "Player" + defenderTerritory.getTerritoryOwner().getPlayerId() + " won";
            PhaseViewModel.getInstance().addPhaseViewContent(message);
            try {
                FileManager.getInstance().writeLog(message);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.ATTACKER_LOST);

        }
    }

    private int numberOfDiceRolled;

    /**
     * Returns number of dice rolled
     *
     * @return the number of dice rolled
     */
    public int getNumberOfDiceRolled() {
        return numberOfDiceRolled;
    }

    /**
     * Set the number of dice rolled
     *
     * @param numberOfDiceRolled set number of dice to be rolled
     */
    public void setNumberOfDiceRolled(int numberOfDiceRolled) {
        this.numberOfDiceRolled = numberOfDiceRolled;
    }

    /**
     * This method is for conditions related to capturing a territory
     *
     * @param attackerTerritory the attacker's territory
     * @param defenderTerritory the defender's territory
     * @param moveArmiesToCapturedTerritory number of armies to move to territory
     * @return whether operation was successful
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
        try {
            FileManager.getInstance().writeLog(message);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        ObserverType observerType = new ObserverType();
        observerType.setObserverType(ObserverType.WORLD_DOMINATION_TYPE);
        setChanged();
        notifyObservers(observerType);
        return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.SUCCESS);
    }


    /**
     * This method returns the highest value from a given list.
     *
     * @param diceArray the dice values
     * @return Maximum element from the list
     */
    public int getHighestValue(List<Integer> diceArray) {
        Collections.sort(diceArray, Collections.reverseOrder());
        return diceArray.get(0);
    }

    /**
     * This method deletes the element from the list.
     *
     * @param diceArray dice values
     * @param element element to be deleted
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
     *
     * @param arraySize size of array
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
     * Method is used to implement the `fication functionality
     *
     * @param destTerritory the destination territory for fortification
     * @param countOfArmy   number of armies to be moved
     * @return response message
     */
    public ConfigurableMessage fortifyTerritory(Territory fromTerritory, Territory destTerritory, int countOfArmy) {
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
                String message = fromTerritory.getTerritoryName() + " has been fortified with " + countOfArmy + " armies.";
                try {
                    FileManager.getInstance().writeLog(fromTerritory.getTerritoryName() + " has been fortified with " + countOfArmy + " armies.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PhaseViewModel.getInstance().addPhaseViewContent(message);
                return new ConfigurableMessage(Constants.MSG_SUCC_CODE, Constants.FORTIFICATION_SUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_NEIGHBOUR_FAILURE);
        } else
            return new ConfigurableMessage(Constants.MSG_FAIL_CODE, Constants.FORTIFICATION_FAILURE);
    }


}
