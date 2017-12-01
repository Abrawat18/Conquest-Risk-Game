package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 07-Nov-17.
 * Checks whether defender territory can be captured
 */

public class TerritoryCaptureTest {
    Territory defenderTerritory;
    ConfigurableMessage configurableMessage;

    /**
     * Initialize test data
     */
    @Before
    public void setUp()
    {
        defenderTerritory=new Territory();
        defenderTerritory.setArmyCount(3);
    }

    /**
     * test to check whether a territory can be attacked
     */
    @Test
    public void validateCanBeAttacked()
    {
        configurableMessage=new Player().canContinueAttackOnThisTerritory(defenderTerritory);
        assertEquals(Constants.MSG_SUCC_CODE,configurableMessage.getMsgCode());

        defenderTerritory.setArmyCount(defenderTerritory.getArmyCount()-3);
        configurableMessage=new Player().canContinueAttackOnThisTerritory(defenderTerritory);
        assertEquals(Constants.MSG_FAIL_CODE,configurableMessage.getMsgCode());


    }
    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        defenderTerritory=null;
        configurableMessage=null;
        defenderTerritory=null;
    }

}
