package com.app_team11.conquest.utility;

import android.os.Environment;

import java.io.*;
import java.util.List;

/**File Manager Class is used for reading , writing of files and performing relevant operations
 * Created by Vasu on 06-10-2017.
 */
public class FileManager {

    private static FileManager fileManager;

    private FileManager() {

    }

    /**
     *
     * @return
     */
    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

    /**
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
     *
     * @param file
     * @return
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
     *
     * @param finalName
     * @return
     */
    public File getMapFilePath(String finalName) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/map");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(myDir, finalName);
        return file;
    }

    /**
     *
     * @return
     */
    public File getMapFileDirectory() {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/map");
        return myDir;
    }

    /**
     *
     * @param dir
     * @return
     */
    public File[] getAllFileFromDir(File dir) {
        return dir.listFiles();
    }


}
