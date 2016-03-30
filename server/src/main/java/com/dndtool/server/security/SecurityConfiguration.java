package com.dndtool.server.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = SecurityConfiguration.class)
@MapperScan(basePackageClasses = SecurityConfiguration.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SrpAuthenticationFilter srpFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login/challenge").permitAll()
                .antMatchers("/login").hasRole("PRE_AUTH_USER")
                .antMatchers("/**").permitAll()
            .and().addFilterBefore(srpFilter, UsernamePasswordAuthenticationFilter.class).csrf()
            .and().sessionManagement();
        
        // XXX: Reenable me when we are passing CSRF headers through rest calls (When we actually have the web app part set up)
        http.csrf().disable();
    }
}
