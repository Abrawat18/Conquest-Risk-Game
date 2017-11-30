package com.app_team11.conquest.model;

import java.io.File;

/**
 * This class is reposible for the loading the map in the game.
 * Created by Vasu on 26-11-2017.
 */

public class MapFile {

    File mapFiles;
    boolean isSelected=false;

    /**
     * Parameterized constructor for mapFile class
     * @param mapFiles : parameter for the constructor
     */
    public MapFile(File mapFiles) {
        this.mapFiles = mapFiles;
    }

    /**
     * Getter for the mapFile class
     * @return mapFiles : parameter for mapFile class is returned
     */
    public File getMapFiles() {
        return mapFiles;
    }

    /**
     * Sets the mapFile class
     * @param mapFiles : parameter for mapFile class is set
     */
    public void setMapFiles(File mapFiles) {
        this.mapFiles = mapFiles;
    }

    /**
     * Checks if the map is selected
     * @return isSelected : returns the boolean value if the map is selected or not
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Setter for the selected map
     * @param selected : parameter for the selected map and it is boolean selection
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
