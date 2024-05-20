package com.ebixcash.filedatatodatabase.config;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class DatabaseProperties {
	private String dbUrl;
    private String dbUsername;
    private String dbPassword;
}
