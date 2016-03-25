package com.rprescott.dndtool.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rprescott.dndtool.server.ability.AbilityDTO;
import com.rprescott.dndtool.server.ability.AbilityExistsException;
import com.rprescott.dndtool.server.ability.AbilityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.rprescott.dndtool.server.ContextConfiguration.class)
public class PopulateDatabase {

    @Autowired
    private AbilityService abilityService;

    /**
     * Simple test just to get some data into the database with how frequently the schema is being updated.
     * @throws AbilityExistsException
     */
    @Test
    public void populateDatabase() throws AbilityExistsException {
        AbilityDTO fireball = new AbilityDTO("Fireball", "Pew Pew Pew");
        AbilityDTO frostbolt = new AbilityDTO("Frostbolt", "Pow Pow Pow");
        abilityService.createNewAbility(fireball);
        abilityService.createNewAbility(frostbolt);
    }

}
