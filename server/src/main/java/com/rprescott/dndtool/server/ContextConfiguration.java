package com.rprescott.dndtool.server;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com")
public class ContextConfiguration {
    
    /**
     * Configures the datasource to be used by the server.
     * 
     * @return The datasource bean.
     */
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/DND Server");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }
    
    /**
     * Configures the MapperScannerConfigurer by setting the base package to scan for Mappers.
     * 
     * @return The MapperScannerConfigurer bean.
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.rprescott.dndtool.server.database");
        return configurer;
    }
    
    /**
     * Configures the SqlSessionFactoryBean by identifying the datasource and the mybatis configuration.
     * 
     * @return The SqlSessionFactoryBean generated.
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new ClassPathResource("com/rprescott/dndtool/server/config/configuration.xml"));
        return sqlSessionFactory;
    }

}
