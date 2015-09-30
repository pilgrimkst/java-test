package com.javatest.refactor.dao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({AdressSqlRequests.class,DataSourceProperties.class})
@EnableAutoConfiguration
@PropertySource("classpath:/service.properties")
@ComponentScan("com.javatest.refactor")
public class ServiceConfig {

    @Autowired
    DataSourceProperties properties;

    @Bean
    DataSource dataSource() {
        return new DriverManagerDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
