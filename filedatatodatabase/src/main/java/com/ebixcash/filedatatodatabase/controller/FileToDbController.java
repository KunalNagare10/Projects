package com.ebixcash.filedatatodatabase.controller;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebixcash.filedatatodatabase.config.DatabaseProperties;
import com.ebixcash.filedatatodatabase.config.SpringJdbcConfig;
import com.ebixcash.filedatatodatabase.metadata.Metadata;
import com.ebixcash.filedatatodatabase.response.MetadataResponse;
import com.ebixcash.filedatatodatabase.service.FileToDbService;

@RestController
@CrossOrigin("*")
public class FileToDbController {
	@Autowired
	private FileToDbService dbService;
	
	
	@PostMapping("/getData")
	public ResponseEntity<MetadataResponse> getMetadata(@RequestParam String filePath) {
		dbService.setFilePath(filePath);
		
		 List<Metadata> metadata = dbService.getMetadata();
		 if (metadata!=null && metadata.size()>0) {
				MetadataResponse metadataResponse=new MetadataResponse();
				metadataResponse.setMetadata(metadata);
				return new ResponseEntity<MetadataResponse>(metadataResponse, HttpStatus.OK);
			}else {
				MetadataResponse metadataResponse=new MetadataResponse();
				metadataResponse.setMetadata(null);
				return new ResponseEntity<MetadataResponse>(metadataResponse, HttpStatus.NO_CONTENT);
			}
	}

	@PostMapping("/send")
	public ResponseEntity<String> insertOp(@RequestBody Map<String, String> params) {
		
		System.out.println("Controller /send endpoint");
		
		String colAsPrKey = params.get("primaryKey");
		params.put("dbUrl", "jdbc:oracle:thin:@10.165.187.30:1522/orcl12c");
		params.put("dbUsername", "DEV_LOS");
		params.put("dbPassword", "DEV_LOS");
		String colWithOgPrValue = params.get(colAsPrKey);
		String[] parts = colWithOgPrValue.split("\\.");
		String ogPrKey = parts[1];
		System.out.println("primaryKey: "+ogPrKey);
		params.remove("primaryKey");
		
		String string = dbService.dbQueryMap(colAsPrKey,ogPrKey, params);
		 if (string!=null) {
				return new ResponseEntity<String>(string, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(string, HttpStatus.NO_CONTENT);
			}
	}
	
	@PostMapping("/sendagain")
	public ResponseEntity<String> insertData(@RequestParam String filePath) {

		System.out.println(filePath);
		String string = dbService.newMethod(filePath);
		System.out.println("string: "+string);
		if (string!=null) {
			return new ResponseEntity<String>(string, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Metadata is not available."
					+ " Please Generate Metadata first.", HttpStatus.NOT_FOUND);
		}
	}
}