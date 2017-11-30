package com.app_team11.conquest.model;

import java.io.Serializable;

/**
 * This class defines the coordinate for the point
 * Created by Abhi on 28-11-2017.
 */

public class Point implements Serializable {
    public int x;
    public int y;

    /**
     *
     * @param x coordinate x is defined using this parameter
     * @param y coordinate y is defines using this parameter
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
