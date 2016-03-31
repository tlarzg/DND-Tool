package com.dndtool.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dndtool.server.ability.AbilityDTO;
import com.dndtool.server.ability.AbilityExistsException;
import com.dndtool.server.ability.AbilityService;
import com.dndtool.server.campaign.CampaignService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.dndtool.server.ContextConfiguration.class)
@WebAppConfiguration
public class PopulateDatabase {

    @Autowired
    private AbilityService abilityService;
    
    @Autowired
    private CampaignService campaignService;

    /**
     * Simple test just to get some data into the database with how frequently the schema is being updated.
     * @throws AbilityExistsException
     */
    @Test
    public void populateDatabase() {
        try {
            AbilityDTO fireball = new AbilityDTO("Fireball", "Pew Pew Pew");
            AbilityDTO frostbolt = new AbilityDTO("Frostbolt", "Pow Pow Pow");
            
            abilityService.createNewAbility(fireball);
            abilityService.createNewAbility(frostbolt);
        }
        catch (AbilityExistsException e) {
            e.printStackTrace();
        }
    
        campaignService.createNewCampaign("Beep", "Dungeons and Dragons bish");
        campaignService.createNewCampaign("Boop", "More Dargons");
        
    }

}
