package com.app_team11.conquest.utility;

import java.io.Serializable;

/**
 * This class manages all the 4 phases of game
 * Created by Jaydeep9101 on 05-Nov-17.
 */

public class GamePhaseManager implements Serializable {

    public static final int PHASE_STARTUP = 1;
    public static final int PHASE_REINFORCEMENT = 2;
    public static final int PHASE_ATTACK = 3;
    public static final int PHASE_FORTIFICATION = 4;
    public int currentPhase = 0;

    /**
     * Constructor for GamePhaseManager
     * initialize currentPhase
     */
    public GamePhaseManager() {
        currentPhase = PHASE_STARTUP;
    }
    /**
     * Method which resets the current active phase
     */
    public void resetCurrentPhase(){
        currentPhase=0;
    }

    /**
     * Returns the current phase
     * @return currentPhase the current phase
     */
    public int getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Sets the current phase
     * @param currentPhase the current phase
     */
    public void setCurrentPhase(int currentPhase) {
        currentPhase = currentPhase;
    }

    /**
     * Method which changes the phase in sequence
     */
    public void changePhase() {
        if(currentPhase == 0){
            currentPhase = PHASE_STARTUP;
        }
        else if (currentPhase == PHASE_STARTUP) {
            currentPhase = PHASE_REINFORCEMENT;
        } else if (currentPhase == PHASE_REINFORCEMENT) {
            currentPhase = PHASE_ATTACK;
        } else if (currentPhase == PHASE_ATTACK) {
            currentPhase = PHASE_FORTIFICATION;
        } else if (currentPhase == PHASE_FORTIFICATION) {
            currentPhase = PHASE_REINFORCEMENT;
        }
    }
}
