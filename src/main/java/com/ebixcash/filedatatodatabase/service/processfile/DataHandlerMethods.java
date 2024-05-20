package com.ebixcash.filedatatodatabase.service.processfile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.json.JsonString;
import javax.json.JsonValue;

public class DataHandlerMethods {

	static void generateErrorFile(List<Map<String, String>> data) {
		try {
			FileWriter fWriter = new FileWriter("error.txt");
			fWriter.write(data.toString());
			// Closing the file writing connection
			fWriter.close();
			System.out.println("File is created successfully with the content.");
			System.out.println(data);
		}
		
		catch (IOException e) {
			// Print the exception
			System.out.print(e.getMessage());
		}
	}
	static boolean valueMatchesDataType(String stringValue, String expectedDataType) {
		// Logic to check if value matches the expected data type
		if (stringValue == null || expectedDataType == null) {
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
	
	static boolean valueMatchesDataType(JsonValue value, String expectedDataType) {
		// Logic to check if value matches the expected data type
		String actualDataType = null;
		String stringValue;
		if (value != null && expectedDataType != null) {
			if (value instanceof JsonString) {
				stringValue = ((JsonString) value).getString();
			} else {
				stringValue = value.toString();
				if (stringValue.isEmpty()) {
					return false;
				}
			}
		}else {
			return false; // Null value or expectedDataType indicates mismatch
		}
		try {
			Integer.parseInt(stringValue);
			actualDataType = "Integer";
		} catch (NumberFormatException e1) {
			try {
				Long.parseLong(stringValue);
				actualDataType = "Long";
			} catch (NumberFormatException e) {
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
}
