package com.ebixcash.filedatatodatabase.service.getmetadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.metadata.Metadata;

import lombok.Data;

@Component
@Data
public class GetExcelMetadata {
	private List<Metadata> list;
	private Map<String, String> dataMap;
	public List<Metadata> generateMetadata(String filePath) {
		System.out.println("inside generateMetadata() of GetExcelMetadata.");
		list=new ArrayList<>();
		dataMap=new HashMap<>();
		try {
			ReadableWorkbook wb = new ReadableWorkbook(new File(filePath));
	        Sheet rs = wb.getFirstSheet();
	//read() method will not read empty rows
	        List<Row> rows = rs.read();
	        
	        if (rows.size() > 1) { // Ensure there is more than one row (header + at least one data row)
	            Row headerRow = rows.get(0);
	            Row sampleRow = rows.get(1);

	            // Track maximum length for each column
	            int[] maxColumnLengths = new int[headerRow.getCellCount()];

	            // Iterate through each row, starting from the second row
	            for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) {
	                Row row = rows.get(rowIndex);

	                // Iterate through each cell in the row
	                for (int i = 0; i < row.getCellCount(); i++) {
	                    Cell cell = row.getCell(i);
	                    
	                    if (cell !=null) {
	                    	// Update maximum length for the column
		                    int cellLength = cell.getText().length();
		                    if (cellLength > maxColumnLengths[i]) {
		                        maxColumnLengths[i] = cellLength;
		                    }
						}
	                }
	            }
	            // Print the column name, data type, and maximum length for each column
	            for (int i = 0; i < headerRow.getCellCount(); i++) {
	                Cell headerCell = headerRow.getCell(i);
	                String columnName = headerCell.getText();
	                
	                String dataType="N/A";
	                if (i < sampleRow.getCellCount()) {
	                	Cell sampleCell = sampleRow.getCell(i);
	                    if (sampleCell !=null) {
	                    	String data = sampleCell.getText();
	                    	 dataType = TypeClassifier.getDataType(data);
						}
					}
	                Metadata metadata=new Metadata();
	                metadata.setColumnName(columnName);
	                metadata.setDataType(dataType);
	                metadata.setMaxColumnLength(maxColumnLengths[i]);
	                list.add(metadata);
	                dataMap.put(columnName, dataType);
	            }
	        }
//	         Close the workbook
	        wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
