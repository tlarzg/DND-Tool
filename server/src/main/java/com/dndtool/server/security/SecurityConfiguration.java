package com.dndtool.server.security;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = SecurityConfiguration.class)
@MapperScan(basePackageClasses = SecurityConfiguration.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final NonRedirectingAuthenticationHandler authHandler = new NonRedirectingAuthenticationHandler();

    @Autowired
    private CredentialsService credentialsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").authenticated()
            .and().sessionManagement()
            .and().formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/api/login")
                .failureHandler(authHandler)
                .successHandler(authHandler)
            .and().logout().deleteCookies("JSESSIONID")
            // XXX: Eventually, we'll want to pass the token down in the Login Request. For now, just disable the
            // Cross site reference checking.
            .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(credentialsService).passwordEncoder(passwordEncoder());
    }

    private static class NonRedirectingAuthenticationHandler
        implements AuthenticationFailureHandler, AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getLocalizedMessage());
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
