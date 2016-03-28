package com.dndtool.model;

import java.util.List;

public class CampaignDTO {

    /** Autogenerated ID from the database. */
    private long campaignId;
    /** The name of the campaign. */
    private String name;
    /** A brief summary of the campaign. */
    private String description;
    /** The characters in the campaign. */
    private List<CharacterDTO> characters;

    public long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(long campaignId) {
        this.campaignId = campaignId;
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

    public List<CharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDTO> characters) {
        this.characters = characters;
    }

}
