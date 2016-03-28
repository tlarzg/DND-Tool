package com.rprescott.dndtool.server.service.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dndtool.model.CampaignDTO;
import com.rprescott.dndtool.server.database.campaign.CampaignDAO;

@Service
public class CampaignService {
    
    private CampaignDAO campaignDao;
    
    public CampaignDTO retrieveCampaign(String campaignName) {
        // TODO: Actually retrieve a fully populated campaign.
        CampaignDTO campaign = new CampaignDTO();
        return campaign;
    }
    
    /**
     * Creates a basic campaign with the provided name and description.
     * 
     * @param name - The name of the campaign.
     * @param description - A brief description of the campaign.
     */
    public void createNewCampaign(String name, String description) {
        campaignDao.createNewCampaign(name, description);
    }
    
    @Autowired
    public void setCampaignDao(CampaignDAO campaignDao) {
        this.campaignDao = campaignDao;
    }

}
