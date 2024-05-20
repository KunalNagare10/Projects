package com.ebixcash.filedatatodatabase.service.getmetadata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.metadata.Metadata;

import lombok.Data;

@Component
@Data
public class GetJsonMetadata {
	private List<Metadata> list;
	private Map<String, String> dataMap;
	public List<Metadata> generateMetadata(String filePath) {
		dataMap=new HashMap<>();
		System.out.println("inside generateMetadata() of GetJsonMetadata");
        try {
            JsonReader jsonReader = Json.createReader(new FileReader(filePath));
            // Reading entire JSON file into a JsonStructure
            JsonStructure jsonStructure = jsonReader.read();
            list=new ArrayList<>();
             return processingJson(jsonStructure);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		return null;
    }
	 private List<Metadata> processingJson(JsonStructure jsonStructure) {
	    	
	        if (jsonStructure instanceof JsonArray) {
	            JsonArray jsonArray = (JsonArray) jsonStructure;
	            if (!jsonArray.isEmpty()) {
	                JsonObject sampleObject = jsonArray.getJsonObject(0);
	                Set<String> keys = sampleObject.keySet();
	                int[] maxColumnLengths = new int[keys.size()];

	                for (JsonObject object : jsonArray.getValuesAs(JsonObject.class)) {
	                    int index = 0;
	                    for (String key : keys) {
	                    	JsonValue value = object.get(key);
	                    	String stringValue;
	                    	if (value instanceof JsonString) {
	                    	    stringValue = ((JsonString) value).getString();
	                    	} else {
	                    	    stringValue = value.toString();
	                    	}
	                        maxColumnLengths[index] = Math.max(maxColumnLengths[index], stringValue.length());
	                        index++;
	                    }
	                }
	                int index = 0;
	                for (String key : keys) {
	                	String dataType =TypeClassifier.getDataType(jsonArray, key);
	                    Metadata metadata=new Metadata();
	                    metadata.setColumnName(key);
	                    metadata.setDataType(dataType);
	                    metadata.setMaxColumnLength(maxColumnLengths[index]);
		                list.add(metadata);
		                dataMap.put(key, dataType);
	                    index++;
	                }
	            }
	        } else if (jsonStructure instanceof JsonObject) {
	            JsonObject jsonObject = (JsonObject) jsonStructure;

	            for (String key : jsonObject.keySet()) {
	                JsonValue value = jsonObject.get(key);

	                if (value instanceof JsonArray) {
	                    JsonArray jsonArray = (JsonArray) value;
	                    processingJson(jsonArray);
	                }
	            }
	        }
			return list;
	    }
}
