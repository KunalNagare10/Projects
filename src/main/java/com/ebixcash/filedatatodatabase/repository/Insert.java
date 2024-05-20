package com.ebixcash.filedatatodatabase.repository;

import java.util.List;
import java.util.Map;

public class Insert {
		public static StringBuilder insertQuery(String tableName, List<String> columns,
		        Map<String, String> dataMap, Map<String, String> displayColnDatabaseCol) {

		    StringBuilder queryBuilder = new StringBuilder("INSERT INTO ")
		            .append(tableName).append("(");
		    
		    // Append column names
		    boolean firstColumn = true;
		    for (String columnName : columns) {
		        if (!firstColumn) {
		            queryBuilder.append(",");
		        }
		        queryBuilder.append(columnName);
		        firstColumn = false;
		    }
		    queryBuilder.append(") VALUES (");

		    // Append values
		    boolean firstValue = true;
		    for (String columnName : columns) {
		        if (!firstValue) {
		            queryBuilder.append(",");
		        }
		        String fileColumn = displayColnDatabaseCol.get(columnName);
		        String value=dataMap.get(fileColumn);
		        if (value != null && isNumeric(value)) {
		            queryBuilder.append(value);
		        } else {
		            queryBuilder.append("'").append(value).append("'");
		        }
		        firstValue = false;
		    }
		    queryBuilder.append(")");
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
