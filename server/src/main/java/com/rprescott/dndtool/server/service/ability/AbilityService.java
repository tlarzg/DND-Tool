package com.rprescott.dndtool.server.service.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dndtool.model.AbilityDTO;
import com.rprescott.dndtool.server.database.ability.AbilityDAO;

@Service
public class AbilityService {
    
    @Autowired
    private AbilityDAO abilityDao;
    
    public void createNewAbility(AbilityDTO ability) {
        abilityDao.createNewAbility(ability);
    }
    
    public void retrieveAllAbilities() {
        // TODO: Do the work to be able to get all abilities.
    }

    @Autowired
    public void setAbilityDao(AbilityDAO abilityDao) {
        this.abilityDao = abilityDao;
    }

}
