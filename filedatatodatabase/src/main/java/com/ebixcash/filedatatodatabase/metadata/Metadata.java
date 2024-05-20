package com.ebixcash.filedatatodatabase.metadata;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Metadata {
	private String columnName;
	private String dataType;
	private int maxColumnLength;
}

