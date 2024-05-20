package com.ebixcash.filedatatodatabase.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebixcash.filedatatodatabase.config.DatabaseProperties;
import com.ebixcash.filedatatodatabase.config.SpringJdbcConfig;
import com.ebixcash.filedatatodatabase.metadata.Metadata;
import com.ebixcash.filedatatodatabase.repository.FileToDatabaseRepository;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import lombok.Data;

@Service
@Data
public class FileToDbService {
	private String filePath;
	private String fileType;
	
	@Autowired
	private FileToDatabaseRepository repository;
	@Autowired
	private WriteData writeData;
	@Autowired
	private ServiceMethods serviceMethods;
	@Autowired
	private SpringJdbcConfig config;
	@Autowired
	private DatabaseProperties dbProp;

	public List<Metadata> getMetadata() {
		File file = new File(filePath);
		System.out.println(filePath);
		String fileName = file.getName();
		fileType = Files.getFileExtension(fileName);

		String[] split = fileName.split("\\.");
		String fname = split[0];
		
		System.out.println("fileType: "+fileType);
		return serviceMethods.getMetadataOfFile(fileType,fname,filePath);
	}

	public String dbQueryMap(String colAsPrKey, String ogPrKey, Map<String, String> params) {
		String response = null;
		System.out.println("dbQureryMap() method of service class");
		
		String dbUrl = params.get("dbUrl");
		dbProp.setDbUrl(dbUrl);
		String dbUsername = params.get("dbUsername");
		dbProp.setDbUsername(dbUsername);
		String dbPassword = params.get("dbPassword");
		dbProp.setDbPassword(dbPassword);
		
		String dbType = params.get("dbType");
		
		DataSource dataSource = config.setDataSource(dbProp);
		params.remove("dbType");
		params.remove("dbUrl");
		params.remove("dbUsername");
		params.remove("dbPassword");

		
		//key: Column name in file, value: column name given by user
		Map<String, String> displayColnDatabaseCol=new HashMap<>();
		//key:table , value will be columns to add of respective table
		Map<String, List<String>> maps=new HashMap<>();
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String[] parts = value.split("\\.");
			String columnName = parts[1];
			String tableName = parts[0];                                    
			
			 // Add column to the list of columns for this table
            if (maps.containsKey(tableName)) {
            	// Retrieve the list of columns for this table
                List<String> columnList = maps.get(tableName);
                // Add the new column to the list
                columnList.add(columnName);
            } else {
                List<String> columnsToAdd = new ArrayList<>();
                columnsToAdd.add(columnName);
                maps.put(tableName, columnsToAdd);
            }
            //key:-> db column name , value:-> actual col name
            displayColnDatabaseCol.put(columnName,key );
		}
		System.out.println("table & columns: "+maps); 
		// Just for response purpose
		if (maps!=null && maps.size() > 0) {
			response = "Success";
		}
		List<Map<String, String>> dataList = serviceMethods.generateDatalist(fileType, filePath);
		//Writing data to a file
		
		//Check if file with required content is present
		if (!writeData.checkFileContent(filePath)) {
			writeData.writeToJsonFile(filePath,maps,colAsPrKey,
					ogPrKey,displayColnDatabaseCol,fileType,dbUrl,dbUsername,dbPassword);
		}
		repository.writeQuery(maps,colAsPrKey,ogPrKey, dataList,displayColnDatabaseCol,dataSource);
		return response;
	}
	
	public String newMethod(String filePath2) {
		File file = new File(filePath2);
		String fileName = file.getName();
		String fileType2 = Files.getFileExtension(fileName);
		System.out.println("FileExtension-> "+fileType2);
		
		if (!writeData.checkIfFilePresent(fileName)) {
			System.out.println("File Not Present");
			return null;
		}
		List<Map<String, String>> dataList = serviceMethods.generateDatalist(fileType2, filePath2);
		JsonObject jsonData = writeData.readJsonFromFile(filePath2);
		System.out.println("jsonData: "+jsonData);
		Gson gson=new Gson();
		if (jsonData!=null) {
			String colAsPrKey = jsonData.get("colAsPrKey").getAsString();
			String ogPrKey = jsonData.get("ogPrKey").getAsString();
			
			String dbUrl = jsonData.get("dbUrl").getAsString();
			String dbUsername = jsonData.get("dbUsername").getAsString();
			String dbPassword = jsonData.get("dbPassword").getAsString();
			dbProp.setDbUrl(dbUrl);
			dbProp.setDbUsername(dbUsername);
			dbProp.setDbPassword(dbPassword);
			
			DataSource dataSource = config.setDataSource(dbProp);
			
			Map<String, List<String>> maps = gson.fromJson(jsonData
					.getAsJsonObject("maps"), new TypeToken<Map<String, List<String>>>() {}.getType());
			
			Map<String, String> databaseColnDisplayCol = gson.fromJson(jsonData.
					getAsJsonObject("databaseColnDisplayCol"), new TypeToken<Map<String, String>>() {}
					.getType());

			//read and set data source from written JSON file
            repository.writeQuery(maps,colAsPrKey,ogPrKey, dataList,databaseColnDisplayCol, dataSource);
            return "Success";
		}
		return null;
	}
}