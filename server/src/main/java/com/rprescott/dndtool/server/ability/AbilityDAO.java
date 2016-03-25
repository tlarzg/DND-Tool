package com.rprescott.dndtool.server.ability;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityDAO {

    /**
     * Creates a new ability and stores it in the database.
     *
     * @param ability - The ability to store.
     */
    void createNewAbility(AbilityDTO ability);

    /**
     * Retrieves a list of all abilities in the database.
     *
     * @return A list of all abilities in the database.
     */
    List<AbilityDTO> retrieveAllAbilities();

    /**
     * Finds an ability with the associated name.
     *
     * @param abilityName - The name of the ability to find.
     * @return The ability with the specified name or <b>null</b> if no ability exists with that name.
     */
    AbilityDTO findAbilityByName(@Param("name") String abilityName);

}
