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
     * @return mathUtility current instance is available
     */
    public static MathUtility getInstance(){
        if(mathUtility == null){
            mathUtility = new MathUtility();
        }
        return mathUtility;
    }

    /**
     * Returns the distance between two points on surface
     * @param x1 coordinate x1 is defined
     * @param y1 coordinate y1 is defined
     * @param x2 coordinate x2 is defined
     * @param y2 coordinate y2 is defined
     * @return distance between two coordinate is available
     */
    public double getDistance(double x1,double y1,double x2,double y2){
           return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

    }
}
