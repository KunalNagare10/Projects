package com.ebixcash.filedatatodatabase.repository;

import java.util.List;
import java.util.Map;

public class Select {

	static StringBuilder selectQuery(List<Map<String, String>> insertList, String tableName,
			String colAsPrKey, String ogPrKey) {

		StringBuilder selectQueryBuilder = new StringBuilder("SELECT ")
        		.append(ogPrKey).append(" FROM ").append(tableName)
        		.append(" WHERE ").append(ogPrKey).append(" IN (");
		 boolean firstValCHeck = true;
		for (Map<String, String> map : insertList) {
			String pKey = map.get(colAsPrKey);
			System.out.println(pKey);
			if (!firstValCHeck) {
				selectQueryBuilder.append(",");
			}
		    if (pKey != null) {
		    	if (isNumeric(pKey)) {
                    selectQueryBuilder.append(pKey);
                } else {
                    selectQueryBuilder.append("'").append(pKey).append("'");
                }
		    }else {
		    	selectQueryBuilder.append("NULL");
		    }
		    firstValCHeck=false;
		}
		selectQueryBuilder.append(")");
		return selectQueryBuilder;
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
