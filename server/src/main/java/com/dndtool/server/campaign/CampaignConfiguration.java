package com.dndtool.server.campaign;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan(basePackageClasses = CampaignConfiguration.class)
@MapperScan(basePackageClasses = CampaignConfiguration.class, annotationClass = Repository.class)
public class CampaignConfiguration {

}
