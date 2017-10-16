package com.app_team11.conquest.utility;

import android.os.Environment;

import java.io.*;
import java.util.List;

/**
 * Created by Vasu on 06-10-2017.
 */
public class FileManager {

    private static FileManager fileManager;

    private FileManager() {

    }

    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

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

    public File getMapFilePath(String finalName) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/map");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(myDir, finalName);
        return file;
    }

    public File getMapFileDirectory() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/map");
        return myDir;
    }

    public File[] getAllFileFromDir(File dir) {
        return dir.listFiles();
    }


}
