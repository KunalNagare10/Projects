package com.ebixcash.filedatatodatabase.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileToDatabaseRepository {

	private List<Map<String,Object>> selectQueryList;
	
	public void writeQuery(Map<String, List<String>> maps, String colAsPrKey,
			String ogPrKey, List<Map<String, String>> insertList,
			Map<String, String> displayColnDatabaseCol, DataSource dataSource) {
		
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		
		for (Entry<String, List<String>> keyValPair : maps.entrySet()) {
			String tableName = keyValPair.getKey();
			List<String> columns = keyValPair.getValue();
			
			System.out.println("writeQuery() of FileToDatabaseRepository");
			List<Object> primaryValuesList = new ArrayList<>();
			
			if (columns.contains(ogPrKey)) {
				System.out.println("**********************************SELECT QUERY***********"
						+ "*************************************");
				String selectQuery = Select.selectQuery(insertList,tableName,
						colAsPrKey,ogPrKey).toString();
				
		        System.out.println(selectQuery);
		        
		        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO---Connecting to a "
		        		+ "database"
		        		+ "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		        selectQueryList = jdbcTemplate.query(selectQuery, new DynamicRowMapper());
		        System.out.println(selectQueryList);
		       
		        for (Map<String, Object> map : selectQueryList) {
		        	//Column value coming from PLSQL database is in UPPERCASE so...
		            Object primeObject = map.get(ogPrKey.toUpperCase());    
		            if (primeObject != null) {
		            	primaryValuesList.add(primeObject);
		            }
		        }
		        System.out.println("EID List: " + primaryValuesList);
		        
		        if (primaryValuesList!=null && primaryValuesList.size()>0) {
		        	//Updating data list based on the data obtained from select query 
		            List<Map<String, String>> updateList = new ArrayList<>();
		            Iterator<Map<String, String>> iterator = insertList.iterator();
		            
		            while (iterator.hasNext()) {
		                Map<String, String> map = iterator.next();
		                for (Map.Entry<String, String> entry : map.entrySet()) {
		                    if (entry.getKey().equalsIgnoreCase(colAsPrKey)) {
		                        if (containsEID(primaryValuesList, entry.getValue())) {
		                        	updateList.add(map);
		                        	iterator.remove();
		                        }
		                        break;
		                    }
		                }
		            }
		            System.out.println(updateList);
//UPDATE Query
		    		System.out.println("***********************************Update Query******"
		            		+ "******************************************");
		    		for (Map<String, String> dataMap : updateList) {
		    			String updateQuery = Update.updateQuery(tableName,dataMap,
		    					columns,ogPrKey,colAsPrKey,displayColnDatabaseCol).toString();
		    			
		    		    System.out.println(updateQuery);
		    		    int i = jdbcTemplate.update(updateQuery);
		    		    System.out.println(i+" row(s) affected.");
		    		}
				}
		}
// 	INSERT Query
			System.out.println("***********************************INSERT QUERY***********"
					+ "*************************************");
			for (Map<String, String> dataMap : insertList) {
	            String insertQuery = Insert.insertQuery(tableName,columns,dataMap,
	            		displayColnDatabaseCol).toString();
	            System.out.println(insertQuery);
	             int i = jdbcTemplate.update(insertQuery);
	             System.out.println(i+" row(s) affected.");
	        }
		}
	}
	// Check if primaryValuesList contains the given primary key values
    private static boolean containsEID(List<Object> EIDList, String value) {
        for (Object obj : EIDList) {
            if (String.valueOf(obj).equals(value)) {
                return true;
            }
        }
        return false;
    }
}