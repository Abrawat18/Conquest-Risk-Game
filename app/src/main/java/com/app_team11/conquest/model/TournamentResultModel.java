package com.app_team11.conquest.model;

/**
 * Created by Vasu on 27-11-2017.
 */

public class TournamentResultModel {

    private String playMap;
    private Player playerWon;
    private int gameNumber;

    /**
     * method to return map on which game played
     * @return map on which game played
     */
    public String getPlayMap() {
        return playMap;
    }

    /**
     * method to set map on which game played
     * @param playMap map on which game played
     */
    public void setPlayMap(String playMap) {
        this.playMap = playMap;
    }

    /**
     * method to return player who the given game for given map
     * @return player who the given game for given map
     */
    public Player getPlayerWon() {
        return playerWon;
    }

    /**
     * method to set which player won the given game on given map
     * @param playerWon
     */
    public void setPlayerWon(Player playerWon) {
        this.playerWon = playerWon;
    }

    /**
     * method to return game number for the given map
     * @return game number for the given map
     */
    public int getGameNumber() {
        return gameNumber;
    }

    /**
     *
     * @param gameNumber
     */
    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }
}
