package com.app_team11.conquest.utility;

/**
 * Created by Nigel on 13-Oct-17.
 */

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
    String currentPart, line = null;
    String[] params;
    Territory t, tConnected, t2;
    Continent c;
    List<Territory> territoryList = new ArrayList<Territory>();
    List<Continent> continentList = new ArrayList<Continent>();
    List<Territory> connectedTerritories = new ArrayList<Territory>();
    List<Player> playerDetails = new ArrayList<Player>();
    boolean stop = false;
    Continent tempContinent = new Continent("tempContinent", 0);

    public List<Territory> currentTerritories() {
        return territoryList;
    }

    public GameMap readFile(String filePath) {

        try {
            FileReader f = new FileReader(filePath);
            Scanner sc = new Scanner(f);

            while (sc.hasNext()) {
                line = sc.nextLine();

                switch (findCurrentPart(line)) 
                {
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
                            c = new Continent(params[0], Integer.parseInt(params[1]));
                            continentList.add(c);
                            line = sc.nextLine();
                        }
                        break;

                    case "territory":
                        line = sc.nextLine();
                        while (!line.contains("[") && !line.isEmpty()) {
                            //for neighbours
                            params = line.split("\\,");
                            for (int i = 4; i < params.length; i++) 
                            {
                                if(ifTerritoryExists(params[i]))
                                {
                                    tConnected=searchTerritory(params[i]);
                                }
                                else
                                {
                                    createTerritory(params[i],0,0,null);
                                    tConnected=searchTerritory(params[i]);
                                }
                                connectedTerritories.add(tConnected);
                            }
                            //for main territory
                            if(ifTerritoryExists(params[0]))
                            {
                                updateTerritory(params[0],params[1],params[2],params[3],connectedTerritories);
                            }
                            else
                            {
                                createTerritory(params[0],Integer.parseInt(params[1]),Integer.parseInt(params[2]),setContinent(params[3]));
                            }
                            
                            
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
            e.printStackTrace();
        }
        GameMap gm=new GameMap();
        gm.setContinentList(continentList);
        gm.setTerritoryList(territoryList);
        return gm;

    }

    public Continent setContinent(String continentName) {
        int i = -1;
        Continent c1 = null;
        while (i < continentList.size() && !stop) {
            i++;
            if (continentName.equalsIgnoreCase(continentList.get(i).getContName().toLowerCase())) {
                System.out.println("Loop " + i);
                c1 = continentList.get(i);
                System.out.println("setContinent: " + c1.getContName());
                stop = true;
                break;
            }

        }
        System.out.println("returning continent: " + c1.getContName());
        return c1;
    }

    public boolean territoryExists(String tName) {
        for (int i = 0; i < territoryList.size(); i++) {
            if (tName.equals(territoryList.get(i).getTerritoryName())) {
                return true;
            }
        }
        return false;
    }


    public static String findCurrentPart(String line) {
        //System.out.println(line);
        if (line.contains("[Map]")) {
            return "map";
        } else if (line.contains("[Continents]")) {
            //System.out.println("returning continent");
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
        //System.out.println("Territory not found");
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
        t = new Territory(tName, X, Y, tempContinent);
        territoryList.add(t);
        }

    public void updateTerritory(String tName, String X, String Y, String continent, List<Territory> connectedT) {
        Territory tUpdate = searchTerritory(tName);
        Continent cont = searchContinent(continent);
        tUpdate.setCenterPoint(Integer.parseInt(X), Integer.parseInt(Y));
        tUpdate.setContinent(cont);
        tUpdate.addNeighbourToTerr(connectedT);
     }

    public void createTerritory(String tName, String X, String Y, String continent, List<Territory> connectedT) {
        Continent cont1 = searchContinent(continent);
        Territory tNew = new Territory(tName, Integer.parseInt(X), Integer.parseInt(Y), cont1);
        tNew.addNeighbourToTerr(connectedT);
        territoryList.add(tNew);
        
    }

    public List<Player> assignArmies(int noOfPlayers) {

        Player p = null;

        if (noOfPlayers == 3) {
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
            System.out.println("Tcount value: "+Tcount);
            while (Players.size()> 0) 
            {
                Territories.get(Tcount).setTerritoryOwner(Players.get(Pcount));
                Territories.get(Tcount).setArmyCount(1);
                int singleArmies=Players.get(Pcount).getAvailableArmyCount()-1;
                Players.get(Pcount).setAvailableArmyCount(singleArmies);
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

}
