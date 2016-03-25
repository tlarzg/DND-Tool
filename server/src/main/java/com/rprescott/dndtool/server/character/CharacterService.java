package com.rprescott.dndtool.server.character;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.campaign.CampaignDTO;

@Service
public class CharacterService {

    public void createCharacter() {
        // TODO: Do the stuff required to create a character.
    }

    public void deleteCharacter() {
        // TODO: Do the stuff required to delete a character.
        // NOTE: A character should not be able to be deleted if it is in a campaign.
    }

    public List<CharacterDTO> retrieveCharactersInCampaign(CampaignDTO campaign) {
        // TODO: Get all the characters in the specified campaign regardless of the user.
        CharacterDTO character1 = new CharacterDTO();
        CharacterDTO character2 = new CharacterDTO();
        return Arrays.asList(character1, character2);
    }

    public List<CharacterDTO> retrieveCharactersByUser(String userName) {
        // TODO: Retrieve all the characters from the associated user regardless of the campaign.
        CharacterDTO character1 = new CharacterDTO();
        CharacterDTO character2 = new CharacterDTO();
        return Arrays.asList(character1, character2);
    }

}
