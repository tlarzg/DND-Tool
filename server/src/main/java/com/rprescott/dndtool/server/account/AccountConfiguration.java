package com.rprescott.dndtool.server.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
@ComponentScan(basePackageClasses = AccountConfiguration.class)
@MapperScan(basePackageClasses = AccountConfiguration.class, annotationClass = Repository.class)
public class AccountConfiguration {
}