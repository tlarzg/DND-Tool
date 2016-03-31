package com.dndtool.server.campaign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.dndtool.server.character.CharacterDTO;

public class CampaignDTO {

    private BasicCampaignInfo basicCampaignInfo;
    /** The characters in the campaign. */
    private List<CharacterDTO> characters;

    public List<CharacterDTO> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDTO> characters) {
        this.characters = characters;
    }
    
    public String getCampaignPassword() {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update((this.basicCampaignInfo.getCampaignId() + this.basicCampaignInfo.getName()).getBytes());
        String encryptedString = new String(messageDigest.digest());
        return encryptedString;
    }

}