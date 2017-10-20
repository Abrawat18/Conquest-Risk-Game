package com.app_team11.conquest.utility;

/**
 * Created by Nigel on 13-Oct-17.
 */

import android.content.Context;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.Territory;
import com.app_team11.conquest.model.Continent;
import com.app_team11.conquest.model.Player;

public class ReadMapUtility {
    int noOfArmies;
    String line = null;
    String[] params;
    Territory t, tConnected;
    Continent c;
    List<Territory> territoryList = new ArrayList<Territory>();
    List<Continent> continentList = new ArrayList<Continent>();
    List<Territory> connectedTerritories = new ArrayList<Territory>();
    List<Player> playerDetails = new ArrayList<Player>();
    boolean stop = false;
    GameMap gm = new GameMap();
    List<Territory> tempT = new ArrayList<Territory>();

    Context context;

    public ReadMapUtility(Context context) {
        this.context = context;
    }

    public ReadMapUtility() {   }

    public List<Territory> currentTerritories() {
        return territoryList;
    }

    public GameMap readFile(String filePath)
    {
        try {
            FileReader f = new FileReader(filePath);
            Scanner sc = new Scanner(f);

            while (sc.hasNext()) {
                line = sc.nextLine();

                switch (findCurrentPart(line)) {
                    case "map":

                        line = sc.nextLine();
                        while (!line.contains("[") && !line.isEmpty()) {
                            line = sc.nextLine();

                        }
                        break;

                    case "continent":
                        line = sc.nextLine();
                        while (!line.contains("[") && !line.isEmpty() && sc.hasNext()) {
                            params = line.split("\\=");
                            c = new Continent(params[0], Integer.parseInt(params[1]),context);
                            continentList.add(c);
                            line = sc.nextLine();
                        }
                        break;

                    case "territory":
                        line = sc.nextLine();
                        while (!line.contains("[") && !line.isEmpty()) {
                            //for neighbours
                            params = line.trim().split("\\,");
                            for (int i = 4; i < params.length; i++) {
                                if (ifTerritoryExists(params[i].trim())) {
                                    tConnected = searchTerritory(params[i].trim());
                                } else {
                                    createTerritory(params[i].trim(), 0, 0, null);
                                    tConnected = searchTerritory(params[i].trim());
                                }
                                connectedTerritories.add(tConnected);
                            }
                            //for main territory
                            if (ifTerritoryExists(params[0].trim())) {
                                updateTerritory(params[0].trim(), params[1].trim(), params[2].trim(), params[3].trim(), connectedTerritories);
                            } else {
                                createTerritory(params[0].trim(), Integer.parseInt(params[1].trim()), Integer.parseInt(params[2].trim()), setContinent(params[3].trim()));
                            }
                            connectedTerritories.clear();

                            if (sc.hasNext()) {
                                line = sc.nextLine();

                            } else {
                                break;
                            }

                        }
                        break;

                }
            }

        } catch (Exception e) {
            System.out.println("Exception" + e);
            return null;
        }
        gm = new GameMap();
        gm.setContinentList(continentList);
        gm.setTerritoryList(territoryList);
        return gm;

    }

    public Continent setContinent(String continentName) {

        for (Continent continent : continentList) {
            if (continentName.trim().equals(continent.getContName().trim())) {
                return continent;
            }
        }
        return null;


    }

    public boolean territoryExists(String tName) {
        for (int i = 0; i < territoryList.size(); i++) {
            if (tName.equals(territoryList.get(i).getTerritoryName())) {
                return true;
            }
        }
        return false;
    }

    //find whether data falls in the Map,Territory or Continent section
    public static String findCurrentPart(String line) {
        if (line.contains("[Map]")) {
            return "map";
        } else if (line.contains("[Continents]")) {
            return "continent";
        } else {
            return "territory";
        }

    }


    public void printTerritoryList() {
        System.out.println("TerritoryList size: " + territoryList.size());
        for (int i = 0; i < territoryList.size(); i++) {
            System.out.println("===============Territory List=========================");
            System.out.println(territoryList.get(i).getTerritoryName() + "\t"
                    + territoryList.get(i).getCenterPoint() + "\t"
                    + territoryList.get(i).getContinent().getContName() + "\t"
            );

            if (territoryList.get(i).getNeighbourList().size() > 0)
                for (int j = 0; j < territoryList.get(i).getNeighbourList().size(); j++) {
                    System.out.println("Size: " + territoryList.get(i).getNeighbourList().size());
                    System.out.println("Neighbouring " + j + ": " + territoryList.get(i).getNeighbourList().get(j).getTerritoryName());
                }
            System.out.println("=====================================================");
        }
    }

    public void printContinentList() {
        System.out.println("====Continent List===");
        for (int i = 0; i < continentList.size(); i++) {
            System.out.println(continentList.get(i).getContName() + "\t"
                    + continentList.get(i).getScore() + "\t");
        }
    }

    public boolean ifTerritoryExists(String territoryName) {
        for (int i = 0; i < territoryList.size(); i++) {
            if (territoryList.get(i).getTerritoryName().equalsIgnoreCase(territoryName))
                return true;
        }
        return false;
    }

    public Territory searchTerritory(String territoryName) {
        for (int i = 0; i < territoryList.size(); i++)
            if (territoryList.get(i).getTerritoryName().equalsIgnoreCase(territoryName)) {
                //System.out.println("Territory Found");
                return territoryList.get(i);
            }
        return null;
    }

    public Continent searchContinent(String continentName) {
        for (int i = 0; i < continentList.size(); i++)
            if (continentList.get(i).getContName().equalsIgnoreCase(continentName)) {
                return continentList.get(i);
            }
        return null;
    }

    public void createTerritory(String tName, int X, int Y, Continent cont) {
        t = new Territory(tName.trim(), X, Y, cont);
        territoryList.add(t);
    }

    public void updateTerritory(String tName, String X, String Y, String continent, List<Territory> connectedT) {
        Territory tUpdate = searchTerritory(tName);
        Continent cont = searchContinent(continent);
        tUpdate.setCenterPoint(Integer.parseInt(X), Integer.parseInt(Y));
        tUpdate.setContinent(cont);
        tempT = new ArrayList<Territory>();
        tempT.add(tUpdate);

        tUpdate.addNeighbourToTerr(connectedT);
    }

    public void createTerritory(String tName, String X, String Y, String continent, List<Territory> connectedT) {
        Continent cont1 = searchContinent(continent);
        Territory tNew = new Territory(tName, Integer.parseInt(X), Integer.parseInt(Y), cont1);
        tempT = new ArrayList<Territory>();
        tempT.add(tNew);
        tNew.setNeighbourList(connectedT);
        territoryList.add(tNew);
    }

    /**
     * This method takes an input for number of players and assigns armies accordingly.
     * @param noOfPlayers
     * @return
     */
    public List<Player> assignArmies(int noOfPlayers) {

        Player p = null;
        if (noOfPlayers == 2)
            noOfArmies = 40;
        else if (noOfPlayers == 3) {
            noOfArmies = 35;
        } else if (noOfPlayers == 4) {
            noOfArmies = 30;
        } else if (noOfPlayers == 5) {
            noOfArmies = 25;
        } else if (noOfPlayers == 6) {
            noOfArmies = 20;
        }
        //***Add condition when noOfPlayers=2
        for (int i = 1; i <= noOfPlayers; i++) {
            p = new Player();
            p.setPlayerId(i);
            p.setAvailableArmyCount(noOfArmies);
            playerDetails.add(p);
        }
        return playerDetails;
    }


    public static Player getFirstPlayer(List<Player> Players) {
        int rnd = new Random().nextInt(Players.size());
        return Players.get(rnd);
    }


     public List<Player> randomlyAssignCountries(List<Player> Players, List<Territory> Territories) {
        int Tcount = 0,Pcount=0;
        Collections.shuffle(Territories);
        while (Territories.size() > 0 && Tcount<Territories.size())
        {
            while (Players.size()> 0)
            {
                Territories.get(Tcount).setTerritoryOwner(Players.get(Pcount));
                Territories.get(Tcount).addArmyToTerr(1);
                if (Pcount == Players.size()-1)
                {
                    Pcount = -1;
                }

                break;
            }
            Tcount++;
            Pcount++;

        }
        return Players;
    }

    /**
     * This method assigns armies to the territories based on the respective user's choice
     * @param pList
     */
    public void armyAssignment(List<Player> pList) {
        Player p = new Player();
        List<Territory> temp = new ArrayList<Territory>();
        Scanner sc = new Scanner(System.in);
        Boolean loop = true;
        int tNumber;
        boolean needToAssignArmy = true;
        while (needToAssignArmy) {
            needToAssignArmy = false;
            for (int i = 0; i < pList.size(); i++) {
                System.out.println("==========Player " + (i + 1) + "===============");
                System.out.println("Available armies: " + pList.get(i).getAvailableArmyCount());
                p.setPlayerId(i + 1);
                temp = gm.getTerrForPlayer(p);
                if (pList.get(i).getAvailableArmyCount() == 0) {
                    break;
                }
                System.out.println("\nTerritories:");
                for (int k = 0; k < temp.size(); k++) {
                    System.out.println(k + 1 + "." + temp.get(k).getTerritoryName() + "\n");
                }
                System.out.println("Select territory number: ");
                tNumber = sc.nextInt();
                tNumber = tNumber - 1;

                if (pList.get(i).getAvailableArmyCount() > 0) {
                    temp.get(tNumber).addArmyToTerr(1);
                    if(pList.get(i).getAvailableArmyCount() > 0) {
                        needToAssignArmy = true;
                    }
                }
            }
        }
    }

}
