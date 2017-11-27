package com.app_team11.conquest.model;

import java.io.File;

/**
 * Created by Vasu on 26-11-2017.
 */

public class MapFile {

    File mapFiles;
    boolean isSelected=false;

    public MapFile(File mapFiles) {
        this.mapFiles = mapFiles;
    }

    public File getMapFiles() {
        return mapFiles;
    }

    public void setMapFiles(File mapFiles) {
        this.mapFiles = mapFiles;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
