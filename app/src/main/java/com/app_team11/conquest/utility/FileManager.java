package com.app_team11.conquest.utility;

import android.content.SharedPreferences;
import android.os.Environment;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.model.GameMap;
import com.app_team11.conquest.model.MapFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * File Manager Class is used for reading , writing of files and performing relevant operations
 * Created by Vasu on 06-10-2017.
 */
public class FileManager {

    private static FileManager fileManager;
    private SharedPreferences mPrefs;

    private FileManager() {

    }

    /**
     * Method to implement singleton pattern
     *
     * @return fileManager
     */
    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

    /**
     * Method to create BufferWriter object to enable writing
     *
     * @param filePath
     * @return
     */
    public BufferedWriter createWriter(String filePath) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "utf-8"));
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace();
        }

        return writer;

    }

    /**
     * Method to create BufferWriter object to enable writing
     *
     * @param file
     * @return writer
     */
    public BufferedWriter createWriter(File file) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "utf-8"));
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace();
        }

        return writer;

    }

    /**
     * Returns file from room map directory
     *
     * @return myDir.listFiles()
     */
    public List<MapFile> getFileFromRootMapDir() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + File.separator + Constants.ROOT_MAP_DIR);
        File[] mapArray= myDir.listFiles();
        List<MapFile> mapFileList = new ArrayList<>();
        for(File file : mapArray){
            mapFileList.add(new MapFile(file));
        }
        return mapFileList;
    }

    /**
     * Returns map file path
     *
     * @param finalName
     * @return file
     */
    public File getMapFilePath(String finalName) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + File.separator + Constants.ROOT_MAP_DIR);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(myDir, finalName);
        return file;
    }

    /**
     * Returns the file log path
     *
     * @param finalName
     * @return file
     */
    public File getLogPath(String finalName) {
        File file = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + File.separator + Constants.ROOT_LOG_DIR);
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            file = new File(myDir, finalName);
        } catch (Exception e) {

        }
        return file;
    }

    /**
     * Returns map file directory
     *
     * @return myDir
     */
    public File getMapFileDirectory() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/map");
        return myDir;
    }

    /**
     * Returns all files from directory
     *
     * @param dir
     * @return dir.listFiles()
     */
    public File[] getAllFileFromDir(File dir) {
        return dir.listFiles();
    }


    /**
     * Method to write the input text in the log file
     *
     * @param text
     */
    public void writeLog(String text) {
        BufferedWriter output = null;
        try {
            File filePath = getLogPath(Constants.GAME_LOG);
            output = new BufferedWriter(new FileWriter(filePath, true));
            output.write(text);
            output.newLine();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method to read the logs from the file
     *
     * @return list of string read from the file
     */
    public List<String> readLog() {
        Scanner scanner = null;
        ArrayList<String> list = new ArrayList<String>();
        File filePath = FileManager.getInstance().getLogPath(Constants.GAME_LOG);
        try {
            scanner = new Scanner(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            list.add(scanner.nextLine());
        }
        scanner.close();
        return list;
    }

    /**
     * Returns the file log path
     *
     * @param finalName
     * @return file
     */
    public File getSerializableFilePath(String finalName) {
        File file = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + File.separator + Constants.ROOT_GAME_DIR);
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            file = new File(myDir, finalName);
        } catch (Exception e) {

        }
        return file;
    }

    /**
     * @param gamemap
     */
    public void writeObjectIntoFile(GameMap gamemap) {
        File file = getSerializableFilePath(Constants.OBJECT_STATE);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(gamemap);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     */
    public GameMap readObjectFromFile(String fileName) {
        GameMap gameMap = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getSerializableFilePath(fileName)));
            try {
                gameMap = (GameMap) objectInputStream.readObject();
                objectInputStream.close();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gameMap;
    }


    /**
     * Method to delete log
     */
    public void deleteLog() {
        File filePath = getLogPath(Constants.GAME_LOG);
        if (filePath.exists()) {
            filePath.delete();
        }
    }


}
