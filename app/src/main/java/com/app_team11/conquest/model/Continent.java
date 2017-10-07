package com.app_team11.conquest.model;

public class Continent {

    private String contName;
    private int score;

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

    public Continent(String contName, int score) {
        this.contName = contName;
        this.score = score;
    }
}
