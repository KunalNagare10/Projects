package com.ebixcash.filedatatodatabase.service.processfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.service.WriteData;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetJsonMetadata;

@Component
public class ProcessJson {
	@Autowired
	private GetJsonMetadata getJsonMetadata;
	@Autowired
	private WriteData writeData;  
	private List<Map<String, String>> list;
	private List<Map<String, String>> mismatchedData;
	private Map<String, String> dataMap;

	public List<Map<String, String>> parseJson(String filePath) {
		System.out.println("inside parseJson() of ProcessJson");
		File file=new File(filePath);
		String fileName = file.getName();
		dataMap = getJsonMetadata.getDataMap();
		if (dataMap==null) {
			System.out.println("INSIDE IF()");
			System.out.println(fileName);
			 dataMap = writeData.getDataMapFromFile(fileName);
		}
		mismatchedData = new ArrayList<>();
		try {
			JsonReader jsonReader = Json.createReader(new FileReader(filePath));
			// Reading entire JSON file into a JsonStructure
			JsonStructure jsonStructure = jsonReader.read();
			return processingJson(jsonStructure);
		} catch (FileNotFoundException e) {
			System.out.println("Error Occured");
			e.printStackTrace();
		}
		return list;
	}

	private List<Map<String, String>> processingJson(JsonStructure jsonStructure) {
		// Initialize lists to store extracted data and mismatched data
		list = new ArrayList<>();

		// Check if the JSON structure is an array
		if (jsonStructure instanceof JsonArray) {
			JsonArray jsonArray = (JsonArray) jsonStructure;

			// Process each element in the JSON array
			if (!jsonArray.isEmpty()) {
				for (int i = 0; i < jsonArray.size(); i++) {
					// Initialize map to store values for each element
					Map<String, String> valuesMap = new HashMap<>();
					JsonObject employee = jsonArray.getJsonObject(i);

					// Flag to track if all values within this object are valid
					boolean isValidObject = true; 
					for (String empKey : employee.keySet()) {
						JsonValue value = employee.get(empKey);
						String expectedDataType = dataMap.get(empKey);
						// Check if the expected data type matches the actual value type
						if (expectedDataType != null) {
							if (DataHandlerMethods.
									valueMatchesDataType(value, expectedDataType)) {

								// Add value to the map
								if (value instanceof JsonString) {
									valuesMap.put(empKey, ((JsonString) value).getString());
								} else {
									valuesMap.put(empKey, value.toString());
								}
							} else {
								// Mark object as invalid if any mismatch occurs
								isValidObject = false; 
								handleMismatchedData(employee);
								break;
							}
						}
					}
					if (isValidObject) {
						// Add the map only if all values within this object are valid
						list.add(valuesMap); 
					}
				}
			}
		} else if (jsonStructure instanceof JsonObject) {
//			System.out.println("instance JsonObject");
			// Handle nested JSON objects
			JsonObject jsonObject = (JsonObject) jsonStructure;
			for (String key : jsonObject.keySet()) {
				JsonValue value = jsonObject.get(key);
				if (value instanceof JsonArray) {
					JsonArray jsonArray = (JsonArray) value;
					processingJson(jsonArray);
				}
			}
		}
		DataHandlerMethods.generateErrorFile(mismatchedData);
		// Return the list of extracted data
//		System.out.println(list);
		return list;
	}
	
	private void handleMismatchedData(JsonObject employee) {
		Map<String, String> valuesMap = new HashMap<>();

		for (String empKey : employee.keySet()) {
			JsonValue value = employee.get(empKey);
			// Check if the expected data type matches the actual value type
			// Add value to the map
			if (value instanceof JsonString) {
				valuesMap.put(empKey, ((JsonString) value).getString());
			} else {
				valuesMap.put(empKey, value.toString());
			}
		}
		mismatchedData.add(valuesMap);
	}
}