/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: NamedCallableStatment.java 8271 2011-05-10 19:01:10Z rob.darling.osv $
 *
 * Description:
 *
 * Story:
 *
 * Defect:
 */
package com.webanalytics.web.dao.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.webanalytics.web.exception.DBException;


public class NamedCallableStatment {

	private static Map<Class<?>, List<Method>> cachedMethods = new HashMap<Class<?>, List<Method>>();
	protected CallableStatement cStmt;
	private String originalSql;
	private String replacedSql;
	private Map<String, List<Integer>> indexMap = new HashMap<String, List<Integer>>();
	private Pattern pattern = Pattern.compile(":(.*?):");
	private Matcher matcher = null;

	/**
	 * Instantiates a new named callable statment.
	 * 
	 * @param conn
	 *            the conn
	 * @param sql
	 *            the sql
	 * @throws SQLException
	 *             the sQL exception
	 */
	public NamedCallableStatment(Connection conn, String sql) throws SQLException {
		this.originalSql = sql;
		parseSql();
		cStmt = conn.prepareCall(this.replacedSql);
	}

	/**
	 * Instantiates a new named callable statment.
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
	public NamedCallableStatment(Connection conn, String sql, Map<String, Object> params) throws SQLException {
		this.originalSql = sql;
		parseSql();
		cStmt = conn.prepareCall(this.replacedSql);
		
		bindParameters(params);
	}

	/**
	 * Gets the callable stmt.
	 * 
	 * @return the callable stmt
	 */
	public CallableStatement getCStmt() {
		return cStmt;
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
	protected void bindParameters(Map<String, Object> params) throws SQLException {

		if (params == null)
			return;
		for (Entry<String, Object> entry : params.entrySet()) {
			List<Integer> indexes = indexMap.get(entry.getKey().toLowerCase().trim());
			if (indexes == null) {
				throw new DBException();
			}
			for (Integer index : indexes) {
				if (entry.getValue() instanceof ApplicationClob) {
					ApplicationClob applicationClob = (ApplicationClob) entry.getValue();
					cStmt.setCharacterStream(index, new StringReader(applicationClob.getString()),
							applicationClob.getLength());
				}
				else if (entry.getValue() instanceof ApplicationBlob) {
					ApplicationBlob applicationBlob = (ApplicationBlob) entry.getValue();
					if (applicationBlob.getInputStream() == null) {
						cStmt.setNull(index, Types.BLOB);
					}
					else {
						try {
							cStmt.setBinaryStream(index, applicationBlob.getInputStream(), (int)applicationBlob.getLength());
						}
						finally {
							if (applicationBlob.getInputStream() != null)
								try {
									applicationBlob.getInputStream().close();
								}
								catch (IOException e) {
									throw new DBException();
								}
						}
					}
				}
				else
					cStmt.setObject(index, entry.getValue());
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
		return cStmt.executeQuery();
	}
}
