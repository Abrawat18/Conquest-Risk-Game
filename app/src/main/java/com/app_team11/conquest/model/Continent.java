package com.app_team11.conquest.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;

import com.app_team11.conquest.R;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Continent Model class with variables Continent Name,Score & Continent Owner
 * Created by Vasu on 06-10-2017.
 * @version 1.0.0
 */
public class Continent implements Serializable{

    private String contName;
    private int score;
    private Player contOwner;
    private int contColor;
    private static final AtomicInteger count = new AtomicInteger(0);
    private int continentID;

    /**
     * Default Constructor
     */
    public Continent() {

    }

    /**
     * Returns the name of continent
     * @return contName name of the continent is returned
     */
    public String getContName() {
        return contName;
    }

    /**
     * Sets the name of the continent
     * @param contName sets the name of the continent
     */
    public void setContName(String contName) {
        this.contName = contName;
    }

    /**
     * Returns the score
     * @return score returns the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score
     * @param score sets the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the Owner of Continent
     * @return contOwner returns the continent owner
     */
    public Player getContOwner() {
        return contOwner;
    }

    /**
     * Sets the Owner of the Continent
     * @param contOwner parameter is defined for the owner of the continent
     */
    public void setContOwner(Player contOwner) {
        this.contOwner = contOwner;
    }

    /**
     * Initialize class members
     * @param contName name of the continent is defined using this parameter
     * @param score score is defined using this parameter
     */
    public Continent(String contName, int score, Context context) {
        this.contName = contName;
        this.score = score;
        if (context != null)
            setRandomColorToContinent(context);
    }

    /**
     * Initialize class members
     * @param ContName name of the continent is defined
     */
    public Continent(String ContName, Context context) {
        this.contName = getContName();
        setRandomColorToContinent(context);
    }

    /**
     * Initialize class members
     * @param ContName name of the continent is defined
     * @param score score score is defined using this parameter
     */
    public Continent(String ContName, int score) {
        this.contName = getContName();
        this.score = score;
    }

    /**
     * method to prevent conflict between the json continent object and map continent object
     * @return continent here continent is copied continent object
     */
    public Continent copyContinent() {
        Continent continent = new Continent();
        continent.setContName(this.getContName());
        continent.setScore(this.getScore());
        continent.setContOwner(this.getContOwner());
        continent.setContColor(this.getContColor());
        return continent;
    }

    /**
     * Sets the random color to continent
     * @param context running activity instance
     */
    public void setRandomColorToContinent(Context context) {
        continentID = count.incrementAndGet();
        int[] continentColor = context.getResources().getIntArray(R.array.continentColor);
        this.setContColor(continentColor[(continentID % continentColor.length)]);
    }

    /**
     * Returns color of the continent
     * @return contColor returns the continent color
     */
    public int getContColor() {
        return contColor;
    }

    /**
     * Sets the color of the continent
     * @param contColor parameter for the continent color
     */
    public void setContColor(int contColor) {
        this.contColor = contColor;
    }
}
