package com.rprescott.dndtool.server.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
