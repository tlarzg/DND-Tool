package com.dndtool.server.campaign;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private CampaignDAO campaignDao;
    
    /**
     * Creates a basic campaign with the provided name and description.
     *
     * @param name - The name of the campaign.
     * @param description - A brief description of the campaign.
     */
    public void createNewCampaign(String name, String description) {
        campaignDao.createNewCampaign(name, description);
    }
    
    /**
     * Retrieves a list of campaigns a user is currently participating in.
     * 
     * @param userName - The username to find the campaigns for.
     * @return A list of campaigns a user is currently participating in.
     */
    public List<BasicCampaignInfo> getCampaignsForUser(String userName) {
        return campaignDao.getCampaignsForUser(userName);
    }

    @Autowired
    public void setCampaignDao(CampaignDAO campaignDao) {
        this.campaignDao = campaignDao;
    }

}
