package com.app_team11.conquest.model;
/**
 * Continent Model class with variables Continent Name,Score & Continent Owner
 * Created by Vasu on 06-10-2017.
 */
public class Continent {

    private String contName;
    private int score;
    private Player contOwner;

    public Continent() {

    }

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

    /**
     * Initialize class members
     * @param contName
     * @param score
     *
     */
    public Continent(String contName, int score) {
        this.contName = contName;
        this.score = score;
    }

    /**
     *
     * @param ContName
     */
    public  Continent(String ContName){
        this.contName = getContName();
    }
    public Continent copyContinent(){
        Continent continent = new Continent();
        continent.setContName(this.getContName());
        continent.setScore(this.getScore());
        continent.setContOwner(this.getContOwner());
        return continent;
    }
}
