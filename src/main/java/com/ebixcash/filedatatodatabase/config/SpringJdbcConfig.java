package com.ebixcash.filedatatodatabase.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class SpringJdbcConfig {
	 
	 public DataSource setDataSource(DatabaseProperties properties) {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setUrl(properties.getDbUrl());
	        dataSource.setUsername(properties.getDbUsername());
	        dataSource.setPassword(properties.getDbPassword());
	        return dataSource;
	    }
}
