package com.dndtool.server.ability;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbilityService {

    @Autowired
    private AbilityDAO abilityDao;

    /**
     * Creates a new ability and inserts it into the database.
     *
     * @param ability - The ability to create.
     *
     * @throws AbilityExistsException If an ability with that name already exists.
     */
    public void createNewAbility(AbilityDTO ability) throws AbilityExistsException {
        if (findAbilityByName(ability.getName()) == null) {
            abilityDao.createNewAbility(ability);
        }
        else {
            throw new AbilityExistsException();
        }
    }

    /**
     * Obtains a list of all abilities in the database.
     *
     * @return - A list of all abilities in the database.
     */
    public List<AbilityDTO> retrieveAllAbilities() {
        return abilityDao.retrieveAllAbilities();
    }

    /**
     * Finds an ability with the associated name.
     *
     * @param abilityName - The name of the ability to find.
     * @return The ability with the specified name or <b>null</b> if no ability exists with that name.
     */
    public AbilityDTO findAbilityByName(String ablilityName) {
        return abilityDao.findAbilityByName(ablilityName);
    }

    /**
     * Autowired setter for Spring.
     *
     * @param abilityDao - The instance of the AbilityDAO to set.
     */
    @Autowired
    public void setAbilityDao(AbilityDAO abilityDao) {
        this.abilityDao = abilityDao;
    }
}
