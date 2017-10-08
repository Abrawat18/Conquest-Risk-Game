package com.app_team11.conquest.utility;

import java.io.*;

public class FileManager {

    private static FileManager fileManager;
    private FileManager(){

    }
    public static FileManager getInstance(){
        if(fileManager==null){
            fileManager  = new FileManager();
        }
        return fileManager;
    }
    public BufferedWriter createWriter(String filePath)
    {   BufferedWriter writer=null;
        try {
             writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "utf-8"));
        }
        catch(FileNotFoundException fnf){
            fnf.printStackTrace();
        }

        catch(UnsupportedEncodingException uex){
            uex.printStackTrace();
        }

            return writer;

    }
}
