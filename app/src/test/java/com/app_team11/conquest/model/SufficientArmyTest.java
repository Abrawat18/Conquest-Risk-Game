package com.app_team11.conquest.model;

import com.app_team11.conquest.global.Constants;
import com.app_team11.conquest.utility.ConfigurableMessage;

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
    ConfigurableMessage cm;

    @Before
    public void setUp()
    {
        attackerTerritory=new Territory();
        attackerTerritory.setArmyCount(3);
    }
    @Test
    public void validateNumberOfArmies()
    {
        cm=new Player().hasSufficientArmies(attackerTerritory);
        assertEquals(Constants.MSG_SUCC_CODE,cm.getMsgCode());

        attackerTerritory.setArmyCount(1);
        cm=new Player().hasSufficientArmies(attackerTerritory);
        assertEquals(Constants.MSG_FAIL_CODE,cm.getMsgCode());
    }
}
