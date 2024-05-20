package com.ebixcash.filedatatodatabase.service.processfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.service.WriteData;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetExcelMetadata;

@Component
public class ProcessExcel {
	@Autowired
	private WriteData writeData;
	@Autowired
	private GetExcelMetadata getExcelMetadata;
	private List<Map<String, String>> mismatchedData;
	private List<Map<String, String>> list;
	private Map<String, String> dataMap;
	
	public List<Map<String, String>> parseExcel(String filePath) {
		System.out.println("inside parseExcel() of ProcessExcel");
		list = new ArrayList<>();
		mismatchedData= new ArrayList<>();
		dataMap =getExcelMetadata.getDataMap();
		
		File file=new File(filePath);
		String fileName = file.getName();
		
		StringBuilder builder=new StringBuilder();
		String[] split = fileName.split("\\.");
		builder.append(split[0]).append(".json");
			
		if (dataMap==null) {
			 dataMap = writeData.getDataMapFromFile(builder.toString());
		}
        try (ReadableWorkbook wb = new ReadableWorkbook(new File(filePath))) {
            Sheet sheet = wb.getFirstSheet();
            List<Row> rows = sheet.read();
            Row headerRow = rows.get(0);
            
            List<String> columnNames = new LinkedList<>();
            for (int i = 0; i < headerRow.getCellCount(); i++) {
                columnNames.add(headerRow.getCell(i).getText());
            }
         // Iterate over rows skipping the header row
            for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) {
            	boolean isValidObject = true;
                Row currentRow = rows.get(rowIndex);
                Map<String, String> rowData = new HashMap<>();
                for (int columnIndex = 0; columnIndex < columnNames.size(); columnIndex++) {
                    String columnName = columnNames.get(columnIndex);
                    String expectedDataType = dataMap.get(columnName);
                    
                    Cell cell = currentRow.getCell(columnIndex);
                    String cellValue=null;
                    if (cell!=null) {
                    	cellValue= cell.getText();
					}
                    if (DataHandlerMethods.valueMatchesDataType(cellValue, expectedDataType)) {
                    	rowData.put(columnName, cellValue);
                    }else {
						isValidObject = false; // Mark object as invalid if any mismatch occurs
						handleMismatchedData(columnNames,currentRow,columnNames.size());
						break;
					}
                }
                if (isValidObject) {
                    list.add(rowData);					
				}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataHandlerMethods.generateErrorFile(mismatchedData);
        return list;
	}

	private void handleMismatchedData(List<String> columnNames, Row row, int end) {
		System.out.println("Inside handleMismatchedData() of ProcessExcel");
		Map<String, String> mismatchedValues = new HashMap<>();
		for (int cIndex = 0; cIndex < end; cIndex++) {
            String columnName = columnNames.get(cIndex);
            Cell cell = row.getCell(cIndex);
            String cellValue=null;
            if (cell!=null) {
            	cellValue= cell.getText();
            	mismatchedValues.put(columnName, cellValue);
			}
		}
		mismatchedData.add(mismatchedValues);
	}
}
