package com.dndtool.server.campaign;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDAO {
    
    /**
     * Creates an empty campaign in the database. The campaign will only have a name and description.
     * 
     * @param name - The name of the campaign.
     * @param description - A brief description of the campaign.
     */
    public void createNewCampaign(@Param("name") String name, @Param("description") String description);
    
    /**
     * Retrieves all the campaigns a user is currently participating in.
     * 
     * @param userName - The user to find campaigns for.
     * @return The list of campaigns a user is currently participating in.
     */
    public List<BasicCampaignInfo> getCampaignsForUser(@Param("userName") String userName);

}
