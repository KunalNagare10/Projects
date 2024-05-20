package com.ebixcash.filedatatodatabase.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class DynamicRowMapper implements RowMapper<Map<String, Object>> {
	private Map<String, Object> row;
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		 row = new HashMap<>();
         // Retrieve metadata to dynamically get column names
         int columnCount = rs.getMetaData().getColumnCount();
         for (int i = 1; i <= columnCount; i++) {
             String columnName = rs.getMetaData().getColumnName(i);
             Object value = rs.getObject(i);
             row.put(columnName, value);
         }
         return row;
	}
}
