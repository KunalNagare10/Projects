package com.ebixcash.filedatatodatabase.service.processfile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.service.WriteData;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetCsvMetadata;

@Component
public class ProcessCsv {
	@Autowired
	private GetCsvMetadata getCsvMetadata;
	@Autowired
	private WriteData writeData;
	
	private List<Map<String, String>> list;
	private List<Map<String, String>> mismatchedData;
	private Map<String, String> dataMap;
	
	public List<Map<String, String>> parseCsv(String filePath) {
		System.out.println("inside parseCsv() of ProcessCsv");
		list = new ArrayList<>();
		mismatchedData=new ArrayList<>();
		
		dataMap =getCsvMetadata.getDataMap();
		
		File file=new File(filePath);
		String fileName = file.getName();
		if (dataMap==null) {
			 dataMap = writeData.getDataMapFromFile(fileName);
		}
		try(Reader reader = new FileReader(filePath);
				@SuppressWarnings("deprecation")
				CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
			List<String> headerNames = csvParser.getHeaderNames();
			for (CSVRecord csvRecord : csvParser) {
				
	            Map<String, String> valuesMap = new HashMap<>();
	            boolean isValidRecord = true;
	            
	            for (String header : headerNames) {
	                String value = csvRecord.get(header);
	                String expectedType = dataMap.get(header);
	                
	             // Check if value matches expected data type
                    if (valueMatchesDataType(value, expectedType)) {
                    	valuesMap.put(header, value);
                    }else {
                        isValidRecord = false;
                        handleMismatchedData(headerNames, csvRecord);
                        break; // Break the loop for this record
					}
	            }
				if (isValidRecord) {
					// Add the map only if all values within this object are valid
					list.add(valuesMap); 
				}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(list);
		DataHandlerMethods.generateErrorFile(mismatchedData);
		return list;
	}
	
	private boolean valueMatchesDataType(String stringValue, String expectedDataType) {
		// Logic to check if value matches the expected data type
		if (stringValue.isEmpty() || stringValue == null || expectedDataType == null) {
			return false; // Null value or expectedDataType indicates mismatch
		}
		String actualDataType = null;
		
		try {
			Integer.parseInt(stringValue);
			actualDataType = "Integer";
		} catch (NumberFormatException e) {
			try {
				Long.parseLong(stringValue);
				actualDataType = "Long";
			} catch (NumberFormatException e1) {
				try {
					Double.parseDouble(stringValue);
					actualDataType = "Double";
				} catch (NumberFormatException e2) {
					// Not a number, treat it as String
					actualDataType = "String";
				}
			}
		}
		if (actualDataType != null && (actualDataType.equalsIgnoreCase(expectedDataType))) {
			return true;
		} else {
			return false;
		}
	}
	private void handleMismatchedData(List<String> headerNames, CSVRecord csvRecord) {
		System.out.println("Inside handleMismatchedData() of ProcessCsv");
		Map<String, String> mismatchedValues = new HashMap<>();

		 for (String header : headerNames) {
             String value = csvRecord.get(header);
             mismatchedValues.put(header, value);
		 }
		mismatchedData.add(mismatchedValues);
	}
}
