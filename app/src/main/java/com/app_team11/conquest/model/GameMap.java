package com.app_team11.conquest.model;


import android.util.Log;

import com.app_team11.conquest.utility.ConfigurableMessage;
import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Vasu on 06-10-2017.
 */
public class GameMap {
    private static final String TAG = GameMap.class.getSimpleName();
    private String imageName;
    private String wrapFlag;
    private String authorName;
    private String scrollLine;
    private String warnFlag;
    private List<Continent> continentList;
    private List<Territory> territoryList;

    public GameMap() {
        this.continentList = new ArrayList<Continent>();
        this.territoryList = new ArrayList<Territory>();
    }

    public GameMap(String imageName, String wrapFlag, String authorName, String scrollLine, String warnFlag) {
        this.imageName = imageName;
        this.wrapFlag = wrapFlag;
        this.authorName = authorName;
        this.scrollLine = scrollLine;
        this.warnFlag = warnFlag;
        this.continentList = new ArrayList<Continent>();
        this.territoryList = new ArrayList<Territory>();
    }

    /**
     * Method to save final data to file on click of save
     *
     */
    public void writeDataToFile(File file) {
        try {
            BufferedWriter writer = FileManager.getInstance().createWriter(file);
            writer.write("[Map]");
            writer.newLine();
            writer.write("image=" + this.imageName);
            writer.newLine();
            writer.write("wrap=" + this.wrapFlag);
            writer.newLine();
            writer.write("author=" + this.authorName);
            writer.newLine();
            writer.write("scroll=" + this.scrollLine);
            writer.newLine();
            writer.write("warn=" + this.warnFlag);
            writer.newLine();
            writer.write("[Continents]");
            writer.newLine();
            Log.e(TAG, "CHECKING..!!");
            for (Continent contObj : this.getContinentList()) {
                writer.write(contObj.getContName().toString() + " " + Integer.toString(contObj.getScore()));
                writer.newLine();
            }
            writer.newLine();
            writer.write("[Territories]");
            writer.newLine();
            for (Territory terrObj : this.getTerritoryList()) {
                StringBuffer finStr = new StringBuffer(terrObj.getTerritoryName().toString() + ", " + terrObj.getCenterPoint().x + ", " + terrObj.getCenterPoint().y + ", " + terrObj.getContinent().getContName().toString());
                for (int i = 0; i < terrObj.getNeighbourList().size(); i++) {
                    finStr.append(", " + terrObj.getNeighbourList().get(i).getTerritoryName().toString());
                }
                writer.write(finStr.toString());
                writer.newLine();
            }

            writer.close();
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Method is used to add or remove continents from the map based on validations for min and max continents allowed
     * Validation2
     *
     * @param contObj
     * @param addRemoveFlag
     * @return
     */
    public ConfigurableMessage addRemoveContinentFromMap(Continent contObj, char addRemoveFlag) {
        if (addRemoveFlag == 'A') {
            boolean addFlag = true;
            if (null == this.continentList || this.continentList.size() < 32) {
                for(Continent obj : this.continentList){
                    if(obj.getContName().equalsIgnoreCase(contObj.getContName())) {
                        addFlag=false;
                    }
                }
                if(addFlag==true) {
                    this.continentList.add(contObj);
                    return new ConfigurableMessage(Constants.MSGSUCCCODE, Constants.ADDREMTOLISTSUCCESS);
                }
                else
                    return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.DUPLICATE_CONTINENT);
            } else
                return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.CONTSIZEVALFAIL);
        } else if (addRemoveFlag == 'R') {
            if (this.continentList.size() > 1) {
                this.continentList.remove(contObj);
                return new ConfigurableMessage(Constants.MSGSUCCCODE, Constants.ADDREMTOLISTSUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.CONTSIZEVALFAIL);
        } else
            return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.INCORRECTFLAG);

    }

    /**
     * @param terrObj
     * @param addRemoveFlag
     * @return
     */
    public ConfigurableMessage addRemoveTerritoryFromMap(Territory terrObj, char addRemoveFlag) {
        if (addRemoveFlag == 'A') {
            boolean addFlag = true;
            if (null == this.territoryList || this.territoryList.size() < 255) {
                for(Territory obj : this.territoryList){
                    if(obj.getTerritoryName().equalsIgnoreCase(terrObj.getTerritoryName())) {
                        addFlag=false;
                    }
                }
                if(addFlag==true) {
                    this.territoryList.add(terrObj);
                    return new ConfigurableMessage(Constants.MSGSUCCCODE, Constants.ADDREMTOLISTSUCCESS);
                }
                else
                    return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.DUPLICATE_TERRITORY);
            } else
                return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.TERRSIZEVALFAIL);
        } else if (addRemoveFlag == 'R') {
            if (this.territoryList.size() > 1) {
                this.territoryList.remove(terrObj);
                return new ConfigurableMessage(Constants.MSGSUCCCODE, Constants.ADDREMTOLISTSUCCESS);
            } else
                return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.TERRSIZEVALFAIL);
        } else
            return new ConfigurableMessage(Constants.MSGFAILCODE, Constants.INCORRECTFLAG);


    }

    public List<Territory> getTerrForCont(Continent contObj)
     {
         List<Territory> terrList = new ArrayList<Territory>();
         for(Territory obj: this.getTerritoryList()){
             if(obj.getContinent().getContName().equalsIgnoreCase(contObj.getContName())){
                 terrList.add(obj);
             }
         }
         return terrList;
     }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(List<Continent> continentList) {
        this.continentList = continentList;
    }

    public List<Territory> getTerritoryList() {
        return territoryList;
    }

    public void setTerritoryList(List<Territory> territoryList) {
        this.territoryList = territoryList;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getWrapFlag() {
        return wrapFlag;
    }

    public void setWrapFlag(String wrapFlag) {
        this.wrapFlag = wrapFlag;
    }

    public void setScrollLine(String scrollLine) {
        this.scrollLine = scrollLine;
    }

    public void setWarnFlag(String warnFlag) {
        this.warnFlag = warnFlag;
    }

}

