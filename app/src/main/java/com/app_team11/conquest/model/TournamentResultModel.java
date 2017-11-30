package com.app_team11.conquest.model;

import java.io.Serializable;

/**
 * This class is responsible for displaying the result of the tournament
 * Created by Vasu on 27-11-2017.
 */

public class TournamentResultModel implements Serializable{

    private String playMap;
    private Player playerWon;
    private int gameNumber;

    /**
     * method to return map on which game played
     * @return playMap : map on which game played
     */
    public String getPlayMap() {
        return playMap;
    }

    /**
     * method to set map on which game played
     * @param playMap : map on which game played
     */
    public void setPlayMap(String playMap) {
        this.playMap = playMap;
    }

    /**
     * method to return player who the given game for given map
     * @return playerWon :  name of the player who won
     */
    public Player getPlayerWon() {
        return playerWon;
    }

    /**
     * method to set which player won the given game on given map
     * @param playerWon : name of the player who won
     */
    public void setPlayerWon(Player playerWon) {
        this.playerWon = playerWon;
    }

    /**
     * method to return game number for the given map
     * @return gameNumber : game number for the given map
     */
    public int getGameNumber() {
        return gameNumber;
    }

    /**
     * Method to set the game number
     * @param gameNumber : game number for the given map
     */
    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }
}
