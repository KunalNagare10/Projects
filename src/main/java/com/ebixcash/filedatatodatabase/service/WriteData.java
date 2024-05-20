package com.ebixcash.filedatatodatabase.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.metadata.Metadata;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetCsvMetadata;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetExcelMetadata;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetJsonMetadata;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class WriteData {
	@Autowired
	private GetExcelMetadata getExcelMetadata;
	@Autowired
	private GetCsvMetadata getCsvMetadata;
	@Autowired
	private GetJsonMetadata getJsonMetadata;
	
	void writeDataToFile(List<Metadata> metadataOfFile, String fname) {
		Gson gson=new Gson();
		JsonObject jsonObject= new JsonObject();
		JsonArray metadataArray = new JsonArray();
		File createFile=new File(fname+".json");
		for (Metadata metadata : metadataOfFile) {
            JsonElement metadataJson = gson.toJsonTree(metadata);
            metadataArray.add(metadataJson);
        }
		jsonObject.add("metadataList", metadataArray);
		// JsonObject to JSON String Conversion
        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);

		try {
			if (createFile.createNewFile()) {
			    System.out.println("File created: " + createFile.getName());
			  }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 // Write JSON String to file
        try (FileWriter writer = new FileWriter(fname+".json")) {
            writer.write(jsonString);
            System.out.println("Data has been written to " + fname+".json");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing data to " + fname+".json");
        }
	}
	
	List<Metadata> getMetaDataOfExistingFile(String fname) {
		List<Metadata> metadata =null;
		JsonObject jsonObject = readJsonFromFile(fname);
		Gson gson=new Gson();
		Type metadataListType = new TypeToken<List<Metadata>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
	    metadata = gson.fromJson(jsonObject.get("metadataList"), metadataListType);
		return metadata;
	}

	void writeToJsonFile(String filePath, Map<String, List<String>> maps, String colAsPrKey,
			String ogPrKey, Map<String, String> displayColnDatabaseCol, String fileType,
			String dbUrl, String dbUsername, String dbPassword) {
		
		
		List<Metadata> metadataOfFile =null;
		Map<String, String> dataMap =null;    //columnName, datatType
		switch (fileType) {
		case "xlsx": {
			dataMap = getExcelMetadata.getDataMap();
			break;
		}
		case "csv": {
			dataMap = getCsvMetadata.getDataMap();
			break;
		}
		case "json": {
			dataMap= getJsonMetadata.getDataMap();
			break;
		}
		default:
			System.out.println("File path not specified");
		}
		
		File file = new File(filePath);
		String fileName = file.getName();
		String[] split = fileName.split("\\.");
		String fname = split[0];
		
		File createFile=new File(fname+".json");
		if (createFile.exists()) {
			metadataOfFile = getMetaDataOfExistingFile(fname+".json");
		}
		
		//Object creation to hold the data
		JsonObject jsonObject= new JsonObject();
		 // Serialize metadataList to JSON array
        JsonArray metadataArray = new JsonArray();
        Gson gson = new Gson();
        
        for (Metadata metadata : metadataOfFile) {
            JsonElement metadataJson = gson.toJsonTree(metadata);
            metadataArray.add(metadataJson);
        }
        jsonObject.add("metadataList", metadataArray);
        jsonObject.add("dataTypeMap", new Gson().toJsonTree(dataMap)); //columName,dataType
		jsonObject.addProperty("colAsPrKey",colAsPrKey);
		jsonObject.addProperty("ogPrKey", ogPrKey);
        jsonObject.add("maps", new Gson().toJsonTree(maps));  //key:table , value will be columns to add of respective table
        jsonObject.add("databaseColnDisplayCol", new Gson().toJsonTree(displayColnDatabaseCol));
        jsonObject.addProperty("dbUrl", dbUrl);
        jsonObject.addProperty("dbUsername", dbUsername);
        jsonObject.addProperty("dbPassword", dbPassword);
		
     // JsonObject to JSON String Conversion
        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);

		try {
			if (createFile.createNewFile()) {
			    System.out.println("File created: " + createFile.getName());
			  }
		} catch (IOException e) {
			e.printStackTrace();
		}
        // Write JSON String to file
        try (FileWriter writer = new FileWriter(fname+".json")) {
            writer.write(jsonString);
            System.out.println("Data has been written to " + fname+".json");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing data to " + fname+".json");
        }
	}

	public JsonObject readJsonFromFile(String filePath) {
		StringBuilder builder=new StringBuilder();
		File file=new File(filePath);
		String name = file.getName();
		if(!Files.getFileExtension(name).equals("json")) {
			String[] split = name.split("\\.");
			builder.append(split[0]).append(".json");
		}else {
			builder.append(name);
		}
		System.out.println("inside readJsonFromFile filePath"+builder.toString());
		try (FileReader reader = new FileReader(builder.toString())) {
			 JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject();
            } else {
                System.err.println("Invalid JSON file format: Root element is not a JsonObject");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading data from " + builder.toString());
        }
        return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getDataMapFromFile(String filePath) {
		StringBuilder builder=new StringBuilder();
		File file=new File(filePath);
		String name = file.getName();
		if(!Files.getFileExtension(name).equals("json")) {
			String[] split = name.split("\\.");
			builder.append(split[0]).append(".json");
		}else {
			builder.append(name);
		}
		System.out.println("inside getDataMapFromFile filePath:"+builder.toString());
		Gson gson=new Gson();
		JsonObject gsonJsonObject =readJsonFromFile(builder.toString());
		if (gsonJsonObject != null) {
			Map<String, String> dataMap = gson.fromJson(gsonJsonObject.get("dataTypeMap"), Map.class);
			System.out.println("dataTypeMap "+dataMap);
			return dataMap;
		}
		return null;
	}
	
	boolean checkIfFilePresent(String filePath) {
		StringBuilder builder=new StringBuilder();
		
		File file=new File(filePath);
		String name = file.getName();
		if(!Files.getFileExtension(name).equals("json")) {
			String[] split = name.split("\\.");
			builder.append(split[0]).append(".json");
		}else {
				builder.append(name);
		}
		File file1=new File(builder.toString());
		 if (file1.exists()) {
			return true;
		}else {
			return false;
		}
	}

	boolean checkFileContent(String filePath) {
		JsonObject jsonFromFile = readJsonFromFile(filePath);
		if (jsonFromFile != null && jsonFromFile.has("maps")) {
			return true;
		}else {
			return false;
		}
	}
}