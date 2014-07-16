/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: NamedPreparedStatement.java 5195 2010-10-18 20:08:36Z sunil.desu.osv $
 *
 * Description:
 *
 * Story:
 *
 * Defect:
 */
package com.webanalytics.web.dao.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NamedPreparedStatement {

	private static Map<Class<?>, List<Method>> cachedMethods = new HashMap<Class<?>, List<Method>>();
	private PreparedStatement pStmt;
	private String originalSql;
	private String replacedSql;
	private Map<String, List<Integer>> indexMap = new HashMap<String, List<Integer>>();
	private Pattern pattern = Pattern.compile(":(.*?):");
	private Matcher matcher = null;

	/**
	 * Instantiates a new named prepared statement.
	 * 
	 * @param conn
	 *            the conn
	 * @param sql
	 *            the sql
	 * @throws SQLException
	 *             the sQL exception
	 */
	public NamedPreparedStatement(Connection conn, String sql) throws SQLException {
		this.originalSql = sql;
		parseSql();
		pStmt = conn.prepareStatement(this.replacedSql);
	}

	/**
	 * Instantiates a new named prepared statement.
	 * 
	 * @param conn
	 *            the conn
	 * @param sql
	 *            the sql
	 * @param params
	 *            the params
	 * @throws SQLException
	 *             the sQL exception
	 */
	public NamedPreparedStatement(Connection conn, String sql, Map<String, Object> params) throws SQLException {
		this.originalSql = sql;
		parseSql();
		pStmt = conn.prepareStatement(this.replacedSql);
		bindParameters(params);
	}

	
	/**
	 *  
	 * @param conn Connection
	 * @param sql String
	 * @param obj which to bind input parameter
	 * @throws SQLException
	 */
	
	public NamedPreparedStatement(Connection conn, String sql, Object paramObject,HashMap<String, Object> overrideParams ) throws SQLException{
		this.originalSql = sql;
		parseSql();
		pStmt = conn.prepareStatement(this.replacedSql);
		
	}
	
	/**
	 * Gets the prepared stmt.
	 * 
	 * @return the p stmt
	 */
	public PreparedStatement getPStmt() {
		return pStmt;
	}

	/**
	 * Parses the sql.
	 */
	private void parseSql() {
		matcher = pattern.matcher(this.originalSql);
		int index = 1;
		this.replacedSql = this.originalSql;
		while (matcher.find()) {
			String temp = matcher.group().toLowerCase().substring(1, matcher.group().length() - 1).trim();
			List<Integer> idxList = indexMap.get(temp);
			if (idxList == null) {
				idxList = new ArrayList<Integer>();
				// Store the string in lower case and remove brackets for ends
				indexMap.put(temp, idxList);
			}
			idxList.add(index);
			index++;
			this.replacedSql = this.replacedSql.replaceFirst(":(.*?):", "?");
		}
	}

	/**
	 * Bind parameters.
	 * 
	 * @param params
	 *            the params
	 * @throws SQLException
	 *             the sQL exception
	 */
	private void bindParameters(Map<String, Object> params) throws SQLException {

		if (params == null) {
			return;
		}
		for (Entry<String, Object> entry : params.entrySet()) {
			List<Integer> indexes = indexMap.get(entry.getKey().toLowerCase().trim());
			if (indexes == null) {
				throw new RuntimeException("SQL Bind Exception");
			}
			for (Integer index : indexes) {
				pStmt.setObject(index, entry.getValue());
			}
		}
	}
	
	
	
	
	/**
	 * Execute query.
	 * 
	 * @return the result set
	 * @throws SQLException
	 *             the sQL exception
	 */
	public ResultSet executeQuery() throws SQLException {
		return pStmt.executeQuery();
	}

}
