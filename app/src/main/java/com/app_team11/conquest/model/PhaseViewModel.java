package com.app_team11.conquest.model;

import java.util.List;
import java.util.Observable;

/**
 * Created by Abhishek on 05-Nov-17.
 */

public class PhaseViewModel extends Observable {

    private List<String> listPhaseViewContent;
    private static PhaseViewModel phaseViewModel;

    private PhaseViewModel() {
    }

    public static PhaseViewModel getInstance() {
        if (phaseViewModel == null) {
            phaseViewModel = new PhaseViewModel();
        }
        return phaseViewModel;
    }

    public void addPhaseViewContent(String content) {
        listPhaseViewContent.add(content);
        setChanged();
        notifyObservers();
    }
}
