package com.app_team11.conquest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Phase view model class for different phases
 * Created by Abhishek on 05-Nov-17.
 */

public class PhaseViewModel extends Observable {

    private List<String> listPhaseViewContent = new ArrayList<>();
    private static PhaseViewModel phaseViewModel;

    /**
     * Default Constructor
     */
    private PhaseViewModel() {
    }

    /**
     * returns the phase view content
     * @return getListPhaseViewContent
     */
    public List<String> getListPhaseViewContent() {
        return listPhaseViewContent;
    }

    /**
     * Singleton creation for PhaseViewModel
     * @return PhaseViewModel
     */
    public static PhaseViewModel getInstance() {
        if (phaseViewModel == null) {
            phaseViewModel = new PhaseViewModel();
        }
        return phaseViewModel;
    }

    /**
     * Make the list empty
     */
    public void clearString()
    {
        if(null!=listPhaseViewContent) {
            listPhaseViewContent.clear();
        }
    }

    /**
     * Addition of phase view content
     * @param content
     */
    public void addPhaseViewContent(String content) {
        listPhaseViewContent.add(content);
        setChanged();
        notifyObservers();
    }
}
