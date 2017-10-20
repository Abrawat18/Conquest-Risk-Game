package com.app_team11.conquest.model;

import com.app_team11.conquest.utility.ReadMapUtility;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Nigel on 19-Oct-17.
 */

public class GameMapTest {
    private String filePath;
    private boolean failed;
    private File f;

    @Before
    public void setUp() {
        filePath="D:\\3D.map";
        failed=false;
        f=new File("D:\\APP build 1 docs\\WriteFile.map");
    }

    @Test
    public void gameMapFunctionalityTest()
    {
        ReadMapUtility readTest=new ReadMapUtility();
        GameMap gameMap=readTest.readFile(filePath);
        System.out.println("map is"+gameMap);
        gameMap.setAuthorName("Tester");
        gameMap.setImageName("Test Image");
        gameMap.setScrollLine("yes");
        gameMap.setWarnFlag("yes");
        gameMap.setWrapFlag("yes");

        try{
            gameMap.writeDataToFile(f);
        }
        catch (Exception e)
        {
            System.out.println("Failed...");
            failed=true;
        }
        assertFalse(failed);


    }

}
