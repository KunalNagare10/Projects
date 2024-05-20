package com.ebixcash.filedatatodatabase.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebixcash.filedatatodatabase.metadata.Metadata;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetCsvMetadata;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetExcelMetadata;
import com.ebixcash.filedatatodatabase.service.getmetadata.GetJsonMetadata;
import com.ebixcash.filedatatodatabase.service.processfile.ProcessCsv;
import com.ebixcash.filedatatodatabase.service.processfile.ProcessExcel;
import com.ebixcash.filedatatodatabase.service.processfile.ProcessJson;

@Component
public class ServiceMethods {
	@Autowired
	private ProcessJson processJson;
    @Autowired
    private ProcessCsv processCsv;
    @Autowired
    private ProcessExcel processExcel;
	@Autowired
	private GetJsonMetadata jsonMetadata;
	@Autowired
	private GetCsvMetadata csvMetadata;
	@Autowired
	private GetExcelMetadata excelMetadata;
    
    @Autowired
	private WriteData writeData;
    
	List<Map<String, String>> generateDatalist(String fileType, String filePath) {
		List<Map<String, String>> dataList=new LinkedList<>();
		System.out.println("filePath: "+filePath);
		switch (fileType) {
		case "xlsx": {
			dataList = processExcel.parseExcel(filePath);
			break;
		}
		case "csv": {
			dataList = processCsv.parseCsv(filePath);
			break;
		}
		case "json": {
			dataList = processJson.parseJson(filePath);
			break;
		}
		default:
			System.out.println("File path not specified");
		}
		System.out.println("List before writing query->");
		System.out.println(dataList);
		return dataList;
	}

	List<Metadata> getMetadataOfFile(String fileType, String fname, String filePath) {

		switch (fileType) {
		case "xlsx": {
			if (writeData.checkIfFilePresent(fname+".json")) {
				System.out.println("Metadata already present");
				return writeData.getMetaDataOfExistingFile(fname+".json");
			}else {
				System.out.println("Generating Metadata..");
				List<Metadata> metadataOfExcelFile = excelMetadata.generateMetadata(filePath);
				writeData.writeDataToFile(metadataOfExcelFile, fname);
				return metadataOfExcelFile;
			}
		}
		case "csv": {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			if (writeData.checkIfFilePresent(fname+".json")) {
				System.out.println("Metadata already present");
				return writeData.getMetaDataOfExistingFile(fname+".json");
			}else {
				System.out.println("Generating Metadata..");
				List<Metadata> metadataOfCsvFile = csvMetadata.generateMetadata(filePath);
				System.out.println(metadataOfCsvFile);
				writeData.writeDataToFile(metadataOfCsvFile, fname);
				return metadataOfCsvFile;
			}
		}
		case "json": {
			if (writeData.checkIfFilePresent(fname+".json")) {
				System.out.println("Metadata already present");
				return writeData.getMetaDataOfExistingFile(fname+".json");
			}else {
				System.out.println("Generating Metadata..");
				List<Metadata> metadataOfJsonFile = jsonMetadata.generateMetadata(filePath);
				writeData.writeDataToFile(metadataOfJsonFile, fname);
				return metadataOfJsonFile;
			}
		}
		default:
			return null;
		}
	}
}
