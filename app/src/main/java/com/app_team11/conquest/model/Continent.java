package com.app_team11.conquest.model;
/**
 * Created by Vasu on 06-10-2017.
 */
public class Continent {

    private String contName;
    private int score;
    private Player contOwner;

    public String getContName() {
        return contName;
    }

    public void setContName(String contName) {
        this.contName = contName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player getContOwner() {
        return contOwner;
    }

    public void setContOwner(Player contOwner) {
        this.contOwner = contOwner;
    }

    public Continent(String contName, int score) {
        this.contName = contName;
        this.score = score;
    }
}
