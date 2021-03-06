package com.dndtool.server.ability;

public class AbilityDTO {

    /** Autogenerated ID from the database. */
    private long abilityId;
    /** Name of the ability. */
    private String name;
    /** Description of the ability. */
    private String description;

    public AbilityDTO() {

    }

    public AbilityDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(long abilityId) {
        this.abilityId = abilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}