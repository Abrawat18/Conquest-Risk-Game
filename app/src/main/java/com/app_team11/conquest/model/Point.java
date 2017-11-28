package com.app_team11.conquest.model;

import java.io.Serializable;

/**
 * Created by Abhi on 28-11-2017.
 */

public class Point implements Serializable {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
