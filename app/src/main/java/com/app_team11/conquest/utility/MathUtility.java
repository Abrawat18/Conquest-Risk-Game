package com.app_team11.conquest.utility;

/**
 * Calculate the distance between the two selected points
 * Created by RADHEY on 10/17/2017.
 */

public class MathUtility {
    private static MathUtility mathUtility;
    private MathUtility(){

    }

    /**
     * Singleton creation for MathUtility
     * @return mathUtility
     */
    public static MathUtility getInstance(){
        if(mathUtility == null){
            mathUtility = new MathUtility();
        }
        return mathUtility;
    }

    /**
     * Returns the distance between two points on surface
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return distance
     */
    public double getDistance(double x1,double y1,double x2,double y2){
           return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

    }
}
