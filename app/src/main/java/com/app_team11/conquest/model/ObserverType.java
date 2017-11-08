package com.app_team11.conquest.model;

/**
 * Created by Jaydeep9101 on 08-Nov-17.
 */

public class ObserverType {
    public static final int WORLD_DOMINATION_TYPE=1;
    public static final int PHASE_VIEW_UPDATE_TYPE=2;
    private int observerType;

    public int getObserverType() {
        return observerType;
    }

    public void setObserverType(int observerType) {
        this.observerType = observerType;
    }
}
