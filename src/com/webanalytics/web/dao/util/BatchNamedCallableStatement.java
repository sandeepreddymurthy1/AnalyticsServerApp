package com.webanalytics.web.dao.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class BatchNamedCallableStatement extends NamedCallableStatment {

	public BatchNamedCallableStatement(Connection conn, String sql, List<Map<String, Object>> params)
			throws SQLException {
		super(conn, sql);
		for( Map<String, Object> param : params ){
			bindParameters(param);
			cStmt.addBatch();
		}
	}

}
