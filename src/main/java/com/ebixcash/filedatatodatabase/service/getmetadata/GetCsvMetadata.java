package com.ebixcash.filedatatodatabase.service.getmetadata;

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
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.metadata.Metadata;

import lombok.Data;

@Component
@Data
public class GetCsvMetadata {
	private List<Metadata> list;
	private  Map<String, String> dataMap;
	public List<Metadata> generateMetadata(String filePath) {
		System.out.println("inside generateMetadata() of GetCsvMetadata");
		list=new ArrayList<>();
		dataMap=new HashMap<>();
		try (Reader reader = new FileReader(filePath);
	             @SuppressWarnings("deprecation")
				CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

	            // Get the header (column names) from the CSV file
	            String[] headers = csvParser.getHeaderMap().keySet().toArray(new String[0]);

	            // Create a map to store data types and maximum length for each column
	            Map<String, String> dataTypes = new HashMap<String, String>();
	            Map<String, Integer> maxColumnLengths = new HashMap<String, Integer>();

	             // Iterate through each record to determine data types and maximum lengths
	            for (CSVRecord record : csvParser) {
	               for (String columnName : headers) {
	                    String columnValue = record.get(columnName);

	                    // If the column does not exist in the map, add it with the data type and the length of the current record
	                    if (!dataTypes.containsKey(columnName)) {
	                        dataTypes.put(columnName, TypeClassifier.getDataType(columnValue));
	                        maxColumnLengths.put(columnName, columnValue.length());
	                    } else {
	                        // Update the maximum length if the current record has a longer value
	                        int currentLength = columnValue.length();
	                        int maxLength = maxColumnLengths.get(columnName);
	                        if (currentLength > maxLength) {
	                            maxColumnLengths.put(columnName, currentLength);
	                        }
	                    }
	                }
	            }
	            // Print the output in the specified format
	            for (String columnName : headers) {
	                String dataType = dataTypes.get(columnName);
	                int maxLength = maxColumnLengths.get(columnName);
	                Metadata metadata=new Metadata();
	                metadata.setColumnName(columnName);
	                metadata.setDataType(dataType);
	                metadata.setMaxColumnLength(maxLength);
                 list.add(metadata);
                 dataMap.put(columnName, dataType);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
//		System.out.println(list);
		
		return list;
	}
}
