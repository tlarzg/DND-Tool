package com.dndtool.server.campaign;

import org.apache.ibatis.annotations.Param;

public interface CampaignDAO {
    
    /**
     * Creates an empty campaign in the database. The campaign will only have a name and description.
     * 
     * @param name - The name of the campaign.
     * @param description - A brief description of the campaign.
     */
    public void createNewCampaign(@Param("name") String name, @Param("description") String description);

}
