package com.rprescott.dndtool.server.ability;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan(basePackageClasses = AbilityConfiguration.class)
@MapperScan(basePackageClasses = AbilityConfiguration.class, annotationClass = Repository.class)
public class AbilityConfiguration {

}
