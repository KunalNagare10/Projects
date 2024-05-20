package com.ebixcash.filedatatodatabase.repository;

import java.util.List;
import java.util.Map;

public class Update {

	public static Object updateQuery(String tableName, Map<String, String> dataMap,
			List<String> columns,String ogPrKey, String colAsPrKey,
			Map<String, String> displayColnDatabaseCol) {
	        StringBuilder queryBuilder = new StringBuilder("UPDATE ")
	                .append(tableName)
	                .append(" SET ");
	        // Append column names and values to set
	        boolean firstColumn = true;
	        String uniqueIdentifier = null;
	        for (String dbColName : columns) {
	            String fileColumn =displayColnDatabaseCol.get(dbColName);
	            String value=dataMap.get(fileColumn);
	            if(!(dbColName.equalsIgnoreCase(ogPrKey))) {
	                if (!firstColumn) {
	                    queryBuilder.append(", ");
	                }
	                queryBuilder.append(dbColName).append(" = ");
	                if (value != null) {
	                    if (isNumeric(value)) {
	                        queryBuilder.append(value);
	                    } else {
	                        queryBuilder.append("'").append(value).append("'");
	                    }
	                } else {
	                    queryBuilder.append("NULL"); // Handle null values
	                }
	                firstColumn = false;
	            }else if(dbColName.equalsIgnoreCase(ogPrKey)) {
	                uniqueIdentifier = value;
	            }
	        }
	        // Append condition for the update (assuming there's a unique identifier)
	        queryBuilder.append(" WHERE ").append(ogPrKey).append(" IN ");
	        if (uniqueIdentifier != null) {
	            if (isNumeric(uniqueIdentifier)) {
	                queryBuilder.append(uniqueIdentifier);
	            } else {
	                queryBuilder.append("'").append(uniqueIdentifier).append("'");
	            }
	        } else {
	            // Handle case where primary key is missing
	            queryBuilder.append("NULL");
	        }
	        return queryBuilder;
	}
	
    private static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}