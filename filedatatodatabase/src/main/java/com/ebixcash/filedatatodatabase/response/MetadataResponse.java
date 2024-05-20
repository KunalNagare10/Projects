package com.ebixcash.filedatatodatabase.response;

import java.util.List;

import com.ebixcash.filedatatodatabase.metadata.Metadata;

import lombok.Data;

@Data
public class MetadataResponse {
	private List<Metadata> metadata;
}
