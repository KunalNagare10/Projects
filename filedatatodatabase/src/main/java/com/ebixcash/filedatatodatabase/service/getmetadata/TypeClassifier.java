package com.ebixcash.filedatatodatabase.service.getmetadata;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Sheet;

public class TypeClassifier {
	static String convertCellValueToString(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}

	// for .csv handler case
	static String getDataType(String data) {
		if (isInteger(data)) {
			return "Integer";
		} else if (isLong(data)) {
			return "Long";
		} else if (isDouble(data)) {
			return "Double";
		} else if (isBoolean(data)) {
			return "Boolean";
		} else {
			return "String"; // Default to String if no specific type is identified
		}
	}

	private static boolean isBoolean(String value) {
		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
	}
	private static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	private static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	private static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	 static String getDataType(JsonArray jsonArray, String key) {
        for (JsonObject object : jsonArray.getValuesAs(JsonObject.class)) {
        	JsonValue value = object.get(key);
        	String stringValue;
        	if (value instanceof JsonString) {
        	    stringValue = ((JsonString) value).getString();
        	} else {
        	    stringValue = value.toString();
        	}
            try {
                Integer.parseInt(stringValue);
                return "Integer";
            } catch (NumberFormatException e1) {
                try {
                    Long.parseLong(stringValue);
                    return "Long";
                } catch (NumberFormatException e) {
                    try {
                        Double.parseDouble(stringValue);
                        return "Double";
                    } catch (NumberFormatException e2) {
                        // Not a number, treat it as String
                        return "String";
                    }
                }
            }
        }
        // If no value is found for the key, assume it's a String
        return "String";
    }
}
