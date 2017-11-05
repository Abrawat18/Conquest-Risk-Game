package com.app_team11.conquest.utility;

/**
 * Created by Jaydeep9101 on 05-Nov-17.
 */

public class GamePhaseManager {

    private static GamePhaseManager instace;
    public static final int PHASE_STARTUP = 1;
    public static final int PHASE_REINFORCEMENT = 2;
    public static final int PHASE_ATTACK = 3;
    public static final int PHASE_FORTIFICATION = 4;
    public int currentPhase = 0;

    private GamePhaseManager() {

    }

    public static GamePhaseManager getInstance() {
        if (instace == null) {
            instace = new GamePhaseManager();
        }
        return instace;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        currentPhase = currentPhase;
    }

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
