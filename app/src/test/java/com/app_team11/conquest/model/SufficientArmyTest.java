package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Nigel on 06-Nov-17.
 * Validation conditions to check for sufficient armies
 */

public class SufficientArmyTest {
    Territory attackerTerritory;
    ConfigurableMessage configurableMessage;

    /**
     * Initialize test data
     */
    @Before
    public void setUp()
    {
        attackerTerritory=new Territory();
        attackerTerritory.setArmyCount(3);
    }

    /**
     * test to check for sufficient armies before attack
     */
    @Test
    public void validateNumberOfArmies()
    {
        configurableMessage=new Player().hasSufficientArmies(attackerTerritory);
        assertEquals(Constants.MSG_SUCC_CODE,configurableMessage.getMsgCode());

        attackerTerritory.setArmyCount(1);
        configurableMessage=new Player().hasSufficientArmies(attackerTerritory);
        assertEquals(Constants.MSG_FAIL_CODE,configurableMessage.getMsgCode());
    }

    /**
     * Clean up the test data
     */
    @After
    public void cleanup()
    {
        attackerTerritory=null;
        configurableMessage=null;
    }
}
