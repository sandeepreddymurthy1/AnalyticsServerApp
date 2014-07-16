/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: DatabaseHelper.java 7756 2011-03-28 17:56:07Z sunil.desu.osv $
 *
 * Description:
 * 
 * Story: 
 *
 * Defect:
 *
 */
package com.webanalytics.web.dao.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.webanalytics.web.dao.BaseDao;
import com.webanalytics.web.exception.DBException;


/**
 * $Id: DatabaseHelper.java 7756 2011-03-28 17:56:07Z sunil.desu.osv $ The
 * <code>DatabaseHelper</code> class is a helper class written to make
 * connections using connection pools and then execute some commonly used
 * database activities such as select, insert, delete and update.
 * 
 */
public class DatabaseHelper implements IDatabaseHelper {
	
	private static final Logger logger = Logger.getLogger(BaseDao.class.getName());
	private Connection conn = null;
	private ResultSet rslt = null;
	private Statement stmt = null;
	private PreparedStatement pStmt = null;
	private CallableStatement cStmt = null;
	private static ThreadLocal<Boolean> transactionBased = new ThreadLocal<Boolean>();
	private boolean xa = true;
	
	private static final int MAX_INPUT_ARG = 1000;

	/**
	 * Empty constructor</p>
	 * 
	 **/
	public DatabaseHelper() {
		super();
	}

	public void setDataSource(DataSource ds)
			throws DBException {
			try {
				conn = ds.getConnection();
			}
			catch (SQLException e) {
				throw new DBException(e);
			}

	}

	

	/**
	 * setAutoCommit sets the committing nature of the connection to true or
	 * false. This method is useful if a transaction needs to be executed. The
	 * correct way to use this is
	 * <p>
	 * <code>
	 * 		try {
	 * 			setAutoCommit(false);
	 * 			.... // Do some database actions
	 * 			commit();
	 * 		}
	 * 		catch(Exception e) {
	 * 			rollback();
	 * 		}
	 * 		finally{
	 * 			setAutoCommit(true);
	 * 		}
	 * </code>
	 * 
	 * @Method setAutoCommit
	 * @param a
	 *            true or false
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 * @see commit()
	 * @see rollback()
	 **/
	public void setAutoCommit(boolean a)
			throws DBException {

		try {
			conn.setAutoCommit(a);
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * commit commits to the database all actions corresponding to that
	 * connection.
	 * 
	 * @Method commit
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 * @see setAutoCommit()
	 **/
	public void commit()
			throws DBException {

		try {
			conn.commit();
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	/**
	 * rollback rolls back all actions from the database.
	 * 
	 * @Method rollback
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 * @see setAutoCommit()
	 **/
	public void rollback()
			throws DBException {

		try {
			conn.rollback();
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}
	/**
	 * close method closes all open cursors,statements and connections
	 * 
	 * @Method close
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 **/
	public void close() {
		try {
			if (rslt != null)
				rslt.close();
			if (stmt != null)
				stmt.close();
			if (pStmt != null)
				pStmt.close();
			if (cStmt != null)
				cStmt.close();

		}
		catch (SQLException ex) {
			
			throw new DBException(ex);
		}

		// Handle close for non transaction methods
		if (!xa || transactionBased.get() == null || !transactionBased.get().booleanValue()) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DBException(e);
			}
		}
	}

	/**
	 * executeSelect executes a regular query. It is NOT recommended to use this
	 * method because it does not use a preparedStatement
	 * 
	 * @Method executeSelect
	 * @param sql
	 *            The sql to be executed
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return ResultSet The resultset from running the query
	 * 
	 **/
	public ResultSet executeSelect(String sql)
			throws DBException {
		try {
			stmt = conn.createStatement();
			rslt = executeStatement(sql);

			return rslt;
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * executePreparedStmt executes a prepared Statement It takes as arguments
	 * the sql with question marks, a List of values to set for the question
	 * marks and number of columns being returned for that query.
	 * 
	 * It returns a List of records.
	 * 
	 * @Method executePreparedStmt
	 * @param sql
	 *            The sql to be executed
	 * @param List
	 *            <Object> The values that will be set for the question marks
	 * @param numColumns
	 *            The number of columns in the query result
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return List<List<Object>> The resultset as a List
	 * 
	 **/
	public List<List<Object>> executePreparedStmt(String sql, List<Object> values, int numColumns)
			throws DBException {
		List<List<Object>> returnList = new ArrayList<List<Object>>();

		try {
			pStmt = conn.prepareStatement(sql);

			for (int i = 0; i < values.size(); i++)
				pStmt.setObject(i + 1, values.get(i));

			rslt = executePreparedStatement();

			while (rslt.next()) {
				List<Object> aRow = new ArrayList<Object>(numColumns);
				for (int j = 0; j < numColumns; j++)
					aRow.add(rslt.getObject(j + 1));

				returnList.add(aRow);
			}

			return returnList;
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * executePreparedStmt executes a prepared Statement It takes as arguments
	 * the sql with question marks, a List of values to set for the question
	 * marks and number of columns being returned for that query.
	 * 
	 * It returns a List of records.
	 * 
	 * @Method executePreparedStmt
	 * @param sql
	 *            The sql to be executed
	 * @param List
	 *            <Object> The values that will be set for the question marks
	 * @param numColumns
	 *            The number of columns in the query result
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return List<List<Object>> The resultset as a List
	 * 
	 **/
	public List<List<Object>> executeStatement(String sql, int numColumns)
			throws DBException {
		List<List<Object>> returnList = new ArrayList<List<Object>>();

		try {
			stmt = conn.createStatement();
			rslt = executeStatement(sql);

			while (rslt.next()) {
				List<Object> aRow = new ArrayList<Object>(numColumns);
				for (int j = 0; j < numColumns; j++)
					aRow.add(rslt.getObject(j + 1));

				returnList.add(aRow);
			}

			return returnList;
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * Overloaded method to get a List of HashMap objects back. This method
	 * makes it easier for the client to pass a list of fields, table names,
	 * whereClause and then use the field names to query the returned object
	 * 
	 * @param fieldNames
	 * @param tableName
	 * @param whereClause
	 * @param parameters
	 * @return
	 * @throws DBException
	 */

	
	

	/**
	 * executeUniquePreparedStmt executes a prepared Statement It takes as
	 * arguments the sql with question marks, a List of values to set for the
	 * question marks and number of columns being returned for that query.
	 * 
	 * It returns a single record as a List<Object>. If the query returns more
	 * than one record, the method will return only the first row. If the query
	 * does not return a result, then null is returned.
	 * 
	 * @Method executePreparedStmt
	 * @param sql
	 *            The sql to be executed
	 * @param List
	 *            <Object> The values that will be set for the question marks
	 * @param numColumns
	 *            The number of columns in the query result
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return List<List<Object>> The resultset as a List
	 * 
	 **/
	public List<Object> executeUniquePreparedStmt(String sql, List<Object> values, int numColumns)
			throws DBException {

		List<List<Object>> returnList = executePreparedStmt(sql, values, numColumns);
		if (returnList.size() > 0)
			return returnList.get(0);
		else
			return null;

	}

	/**
	 * getCSVString is a private convenience method used by the DatabaseHelper
	 * class to formulate strings separated by commas. These strings will be
	 * used in insert , update or stored procedure statements.
	 * 
	 * @Method getCSVString
	 * @param values
	 *            A List<String> of the values to be separated by commas
	 * @param quote
	 *            Boolean indicating whether to enclose values above in quotes
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return String Returns a string such as ?,?,?,?,?,?
	 * 
	 **/
	private String getCSVString(List<String> values, boolean quote) {

		// Returns a comma separated string
		// if "quote" is true , then the strings are enclosed in single quotes
		// else they're not

		StringBuffer csvStr = new StringBuffer();

		for (int i = 0; i < values.size() - 1; i++) {

			if (quote)
				csvStr.append("'" + (values.get(i)).trim() + "'");
			else
				csvStr.append((values.get(i)).trim());
			csvStr.append(",");
		}

		if (values.size() > 0) {

			if (quote)
				csvStr.append("'" + (values.get(values.size() - 1)).trim() + "'");
			else
				csvStr.append((values.get(values.size() - 1)).trim());
		}

		return csvStr.toString();
	}

	/**
	 * getCannedList is a private convenience method used by the DatabaseHelper
	 * class. It takes a String and a size and returns a List of size with the
	 * string repeated over and over.
	 * 
	 * @Method getCannedList
	 * @param size
	 *            Number of elements to put in the List<String>
	 * @param content
	 *            String to put in the List
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return List<String> Returns a string such as ?,?,?,?,?,?
	 * 
	 **/
	private List<String> getCannedList(int size, String content) {
		List<String> questionList = new ArrayList<String>();
		for (int i = 0; i < size; i++)
			questionList.add(content);

		return questionList;
	}

	/**
	 * executeInsert inserts a record into a table given the list of fields and
	 * values.
	 * 
	 * @Method executeInsert
	 * @param tableName
	 *            Name of the table to insert data into
	 * @param fields
	 *            List<String> of field names in the table
	 * @param values
	 *            List<Object> of values. Size of fields and values MUST match
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return int Returns number of rows inserted or 0 if none inserted
	 * 
	 **/
	public int executeInsert(String tableName, List<String> fields, List<Object> values)
			throws DBException {

		String insertStr =
				"INSERT INTO " + tableName + " ( " + getCSVString(fields, false) + " ) " + " VALUES " + " ( "
						+ getCSVString(getCannedList(fields.size(), "?"), false) + " ) ";

		try {

			pStmt = conn.prepareStatement(insertStr);

			// Set the values from the values List
			for (int i = 0; i < fields.size(); i++) {

				Object o = values.get(i);

				if (o == null)
					pStmt.setNull(i + 1, java.sql.Types.VARCHAR);
				else
					pStmt.setObject(i + 1, values.get(i));
			}

			return pStmt.executeUpdate();

		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	/**
	 * executeDelete deletes from the table based on the whereClause
	 * 
	 * @Method executeDelete
	 * @param tableName
	 *            Name of the table to delete data from
	 * @param whereClause
	 *            where condition applied to query to delete
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return int Returns number of rows deleted or 0
	 * 
	 **/
	public int executeDelete(String tableName, String whereClause)
			throws DBException {

		String deleteStr = "DELETE " + tableName + " WHERE " + whereClause;

		try {

			pStmt = conn.prepareStatement(deleteStr);

			return pStmt.executeUpdate();

		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	/**
	 * executeUpdate updates table, sets fields to values based on whereClause
	 * 
	 * @Method executeUpdate
	 * @param tableName
	 *            Name of the table to update
	 * @param fields
	 *            List<String> of field names in the table
	 * @param values
	 *            List<Object> of values. Size of fields and values MUST match
	 * @param whereClause
	 *            where condition applied to query to update
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return int Returns number of rows updated or 0
	 * 
	 **/
	public int executeUpdate(String tableName, List<String> fields, List<Object> values, String whereClause)
			throws DBException {

		String updateStr = "UPDATE " + tableName + " SET ";

		// Append fieldName = ? , fieldName = ? ..... string to update String
		updateStr = updateStr + getCSVString(getCannedList(fields.size(), " = ?"), false);

		// Append where clause
		updateStr = updateStr + " WHERE " + whereClause;

		try {

			pStmt = conn.prepareStatement(updateStr);

			// Set the values from the values List
			for (int i = 0; i < fields.size(); i++) {

				Object o = values.get(i);

				if (o == null)
					pStmt.setNull(i + 1, java.sql.Types.VARCHAR);
				else
					pStmt.setObject(i + 1, values.get(i));
			}

			return pStmt.executeUpdate();

		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	

	/**
	 * executeInsertBatch inserts several records into the database at the same
	 * time as part of a batch insert.
	 * 
	 * @Method executeInsertBatch
	 * @param tableName
	 *            Name of the table to insert data into
	 * @param fields
	 *            List<String> of field names in the table
	 * @param values
	 *            List of List<Object>. Represents all records in the current
	 *            batch
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 **/
	public void executeInsertBatch(String tableName, List<String> fields, List<List<Object>> values)
			throws DBException {

		// Since the JDBC driver could continue to insert
		// even after a prior insert fails, remember to wrap
		// a call to executeInsertBatch inside autoCommit(false) - commit()
		// statement
		// to mimic a transaction so all statements may be rolled back when an
		// error
		// occurs.
		String insertStr =
				"INSERT INTO " + tableName + " ( " + getCSVString(fields, false) + " ) " + " VALUES " + " ( "
						+ getCSVString(getCannedList(fields.size(), "?"), false) + " ) ";

		try {
			pStmt = conn.prepareStatement(insertStr);

			// Values List is a List of Lists .
			// Each List contains list of values

			for (int i = 0; i < values.size(); i++) {

				List<Object> eachValueList = values.get(i);

				for (int j = 0; j < fields.size(); j++) {

					Object o = eachValueList.get(j);

					if (o == null)
						pStmt.setNull(j + 1, java.sql.Types.VARCHAR);
					else
						pStmt.setObject(j + 1, eachValueList.get(j));
				}

				// Add this to the batch
				pStmt.addBatch();
			}

			// Execute batch only if there are more than 0 records to be
			// inserted
			if (values.size() > 0) {
				pStmt.executeBatch();
			}

		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * executeStoredProcedure executes a stored procedure with the passed name
	 * and parameter values.
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of fields that are being passed to the stored procedure
	 * @throws DBException
	 */
	public void executeStoredProcedure(String procedureName, List<Object> fields)
			throws DBException {

		try {
			String callableSql =
					" { call " + procedureName + " ( " + getCSVString(getCannedList(fields.size(), "?"), false)
							+ " ) }";

			cStmt = conn.prepareCall(callableSql);
			// Set the values from the values List
			for (int i = 0; i < fields.size(); i++) {

				Object o = fields.get(i);

				if (o == null)
					cStmt.setNull(i + 1, java.sql.Types.VARCHAR);
				else
					cStmt.setObject(i + 1, fields.get(i));
			}

			executeCallableStatement();
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * executeStoredProcedureWithStringResult executes a stored procedure which
	 * returns a String
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of fields that are being passed to the stored procedure
	 * @return String The String that the stored procedure returns
	 * @throws DBException
	 */
	public String executeStoredProcedureWithStringResult(String procedureName, List<Object> fields)
			throws DBException {

		try {
			String callableSql =
					" {? = call " + procedureName + "( " + getCSVString(getCannedList(fields.size(), "?"), false)
							+ ") }";

			cStmt = conn.prepareCall(callableSql);
			cStmt.registerOutParameter(1, Types.VARCHAR);
			// Set the values from the values List
			for (int i = 0; i < fields.size(); i++) {

				Object o = fields.get(i);

				if (o == null)
					cStmt.setNull(i + 2, Types.VARCHAR);
				else
					cStmt.setObject(i + 2, fields.get(i));
			}

			executeCallableStatement();
			return cStmt.getString(1);
		}
		catch (SQLException ex) {
			LOG.log(Level.WARN, "Failure running stored proc", ex);
			throw new DBException(ex);
		}
	}

	/**
	 * executeStoredProcedureWithNumberResult executes a stored procedure which
	 * returns an Integer
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of fields that are being passed to the stored procedure
	 * @return String The String that the stored procedure returns
	 * @throws DBException
	 */
	public int executeStoredProcedureWithIntegerResult(String procedureName, List<Object> fields)
			throws DBException {

		try {
			String callableSql =
					" {? = call " + procedureName + "( " + getCSVString(getCannedList(fields.size(), "?"), false)
							+ ") }";

			cStmt = conn.prepareCall(callableSql);
			cStmt.registerOutParameter(1, Types.INTEGER);
			// Set the values from the values List
			for (int i = 0; i < fields.size(); i++) {

				Object o = fields.get(i);

				if (o == null)
					cStmt.setNull(i + 2, Types.INTEGER);
				else
					cStmt.setObject(i + 2, fields.get(i));
			}

			executeCallableStatement();
			// retVal = cStmt.getString(1);
			return cStmt.getInt(1);
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * executeStoredProcedureWithNumberResult executes a stored procedure which
	 * returns an Integer
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of fields that are being passed to the stored procedure
	 * @return String The String that the stored procedure returns
	 * @throws DBException
	 */
	public int executeStoredProcedureForBlobWithIntegerResult(String procedureName, List<Object> fields)
			throws DBException {

		try {
			String callableSql =
					" {? = call " + procedureName + "( " + getCSVString(getCannedList(fields.size(), "?"), false)
							+ ") }";

			cStmt = conn.prepareCall(callableSql);
			cStmt.registerOutParameter(1, Types.INTEGER);
			// Set the values from the values List

			for (int i = 0; i < fields.size(); i++) {

				Object o = fields.get(i);

				if (o == null)
					cStmt.setNull(i + 2, Types.INTEGER);
				else {
					if (o instanceof ApplicationBlob) {
						ApplicationBlob applicationBlob = (ApplicationBlob) fields.get(i);
						if (applicationBlob.getInputStream() == null) {
							cStmt.setNull(i + 2, Types.BLOB);
						}
						else {
							try {
								cStmt.setBinaryStream(i + 2, applicationBlob.getInputStream(), applicationBlob
										.getLength());
							}
							finally {
								if (applicationBlob.getInputStream() != null)
									applicationBlob.getInputStream().close();
							}
						}
					}
					else
						cStmt.setObject(i + 2, fields.get(i));
				}
			}
			executeCallableStatement();

			return cStmt.getInt(1);
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
		catch (IOException e) {
			throw new DBException();
		}

	}

	/**
	 * executeStoredProcedureWithInOutParams executes a stored procedure that
	 * can take parameters in and provide a value back for those parameters
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of in fields that are being passed to the stored
	 *            procedure
	 * @return List<Object> List of out parameters returned from the stored
	 *         procedure corresponding to fields
	 * @throws DBException
	 */

	public List<Object> executeStoredProcedureWithInOutParams(String procedureName, List<Object> fields)
			throws DBException {

		List<Object> retVal = new ArrayList<Object>(fields.size());

		try {
			String callableSql =
					" {call " + procedureName + "( " + getCSVString(getCannedList(fields.size(), "?"), false) + ") }";

			cStmt = conn.prepareCall(callableSql);
			// Set the values from the fields List
			for (int i = 0; i < fields.size(); i++) {

				Object o = fields.get(i);

				if (o == null)
					cStmt.setNull(i + 1, Types.VARCHAR);
				else
					cStmt.setObject(i + 1, fields.get(i));

				cStmt.registerOutParameter(i + 1, Types.VARCHAR);
			}

			executeCallableStatement();
			// Get values from the CallableStatement and put them in return List
			for (int i = 0; i < fields.size(); i++) {

				if (cStmt.getObject(i + 1) == null || cStmt.wasNull())
					retVal.add(null);
				else
					retVal.add(cStmt.getObject(i + 1));
			}

			return retVal;
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	/**
	 * executeStoredProcedureWithInOutParams executes a stored procedure that
	 * can take parameters in and provide a value back for those parameters
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of in fields that are being passed to the stored
	 *            procedure
	 * @return List<Object> List of out parameters returned from the stored
	 *         procedure corresponding to fields
	 * @throws DBException
	 */

	public List<Object> executeStoredProcedureWithInOutParams(String procedureName, List<Object> fields,
			int outParamSize)
			throws DBException {

		List<Object> retVal = new ArrayList<Object>(fields.size());

		try {
			String callableSql =
					" {call " + procedureName + "( "
							+ getCSVString(getCannedList(fields.size() + outParamSize, "?"), false) + ") }";

			cStmt = conn.prepareCall(callableSql);
			// Set the values from the fields List
			for (int i = 0; i < fields.size(); i++) {

				Object o = fields.get(i);

				if (o == null)
					cStmt.setNull(i + 1, Types.VARCHAR);
				else
					cStmt.setObject(i + 1, fields.get(i));
			}

			for (int i = 0; i < outParamSize; i++) {

				cStmt.registerOutParameter(fields.size() + i + 1, Types.VARCHAR);
			}

			executeCallableStatement();
			// Get values from the CallableStatement and put them in return List
			for (int i = 0; i < outParamSize; i++) {

				if (cStmt.getObject(fields.size() + i + 1) == null || cStmt.wasNull())
					retVal.add(null);
				else
					retVal.add(cStmt.getObject(fields.size() + i + 1));
			}

			return retVal;
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	/**
	 * Executes a simple statements like select.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            The sql to be executed
	 * @param converterDto
	 *            the converter dto
	 * @return the list
	 */
	public <T> List<T> executeStatement(String sql, Class<T> converterDto) {

		try {
			stmt = conn.createStatement();
			rslt = executeStatement(sql);
			return Translator.convert(rslt, converterDto);
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	/**
	 * Executes prepared statement with Named Parameters.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param sql
	 *            SQL for executing
	 * @param params
	 *            Map of Parameters
	 * @param converterDto
	 *            the converter dto
	 * @return the list
	 */

	public <T> List<T> executeNamedPreparedStatement(String sql, Map<String, Object> params, Class<T> converterDto) {

		try {
			NamedPreparedStatement nPStmt = new NamedPreparedStatement(conn, sql, params);
			pStmt = nPStmt.getPStmt();
			rslt = executePreparedStatement();
			return Translator.convert(rslt, converterDto);
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}

	}

	public <T> List<T> executeNamedPreparedStatement(String inSql,
			Map<String, Object> params, String listParam, List<?> paramList,
			Class<T> converterDto) {
		String sql = inSql;
		if (listParam == null || listParam.equals("") || paramList == null
				|| paramList.size() == 0) {
			return new ArrayList<T>();
		} else {
			if (paramList.size() <= MAX_INPUT_ARG) {
				StringBuilder temp = new StringBuilder();
				int i=0;
				for( Object obj : paramList ){
					temp.append(":"+listParam+i+":"+",");
					params.put(""+listParam+i, obj);
					i++;
				}
				temp.deleteCharAt(temp.length()-1);
				sql = sql.replace(":" + listParam + ":",
						temp.toString());
				return executeNamedPreparedStatement(sql, params, converterDto);
			} else {
				String tempSql = sql;
				List<T> result = new ArrayList<T>();
				for (int i = 0; i <= paramList.size() / MAX_INPUT_ARG; i++) {
					 Map<String,Object> tempParameterList = new HashMap<String, Object>(params);
					if (i != paramList.size() / MAX_INPUT_ARG){ // not last records
						StringBuilder temp = new StringBuilder();
						int j=0;
						for( Object obj : paramList.subList(
								i * MAX_INPUT_ARG, (i + 1) * MAX_INPUT_ARG) ){
							temp.append(":"+listParam+j+":"+",");
							tempParameterList.put(""+listParam+j, obj);
							j++;
						}
						temp.deleteCharAt(temp.length()-1);
						tempSql = tempSql.replace(":" + listParam + ":",
								temp);
						
					}
					else{
						StringBuilder temp = new StringBuilder();
						if( paramList.subList(
								i * MAX_INPUT_ARG, paramList.size()).size() == 0  )
							continue;
						int j=0;
						for( Object obj :paramList.subList(
								i * MAX_INPUT_ARG, paramList.size())){
							temp.append(":"+listParam+j+":"+",");
							tempParameterList.put(""+listParam+j, obj);
							j++;
						}
						temp.deleteCharAt(temp.length()-1);
						tempSql = tempSql.replace(":" + listParam + ":",
								temp);
					}

					result.addAll(executeNamedPreparedStatement(tempSql,
							tempParameterList, converterDto));
					tempSql = sql;
				}
				return result;
			}

		}
	}


	public String getCsvStringFromList(List<?> list) {
		return list.toString().substring(1, list.toString().length() - 1);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeNamedPreparedStatement(String sql, Map<String, Object> params, String listParam,
			Set<?> paramList, Class<T> converterDto) {
		List list = new ArrayList();
		list.addAll(paramList);
		Collections.sort(list);
		return executeNamedPreparedStatement(sql, params, listParam, list, converterDto);

	}

	/**
	 * Execute callable statement with string value.
	 * 
	 * @param sql
	 *            the sql
	 * @param params
	 *            the params
	 * @return the string
	 */
	public String executeNamedCallableStatementWithStringValue(String sql, Map<String, Object> params) {

		try {
			NamedCallableStatment nCStmt = new NamedCallableStatment(conn, sql, params);
			cStmt = nCStmt.getCStmt();
			cStmt.registerOutParameter(1, Types.VARCHAR);
			executeCallableStatement();
			return cStmt.getString(1);
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	/**
	 * Execute callable statement.
	 * 
	 * @param sql
	 *            the sql
	 * @param params
	 *            the params
	 */
	public void executeNamedCallableStatement(String sql, Map<String, Object> params) {

		try {
			NamedCallableStatment nCStmt = new NamedCallableStatment(conn, sql, params);
			cStmt = nCStmt.getCStmt();
			executeCallableStatement();
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}
	/**
	 * Execute callable statement.
	 * 
	 * @param sql
	 *            the sql
	 * @param params
	 *            the params
	 */
	public Integer executeNamedCallableStatementWithKeys(String sql, Map<String, Object> params) {

		try {
			NamedCallableStatment nCStmt = new NamedCallableStatment(conn, sql, params);
			cStmt = nCStmt.getCStmt();
			executeCallableStatement();
			ResultSet generatedKeys = cStmt.getGeneratedKeys();
			if( generatedKeys.next()){
				return generatedKeys.getInt(1);
			}else
				return -1;
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	private ResultSet executePreparedStatement() throws SQLException{
		ResultSet rs = null;
		long startTime = System.currentTimeMillis();
		rs = pStmt.executeQuery();
		long diff = System.currentTimeMillis() - startTime;
		
		return rs;
	}
	
	private void executeCallableStatement() throws SQLException{
		long startTime = System.currentTimeMillis();
		cStmt.execute();
		long diff = System.currentTimeMillis() - startTime;
		
	}
	
	private void executeBatchCallableStatement() throws SQLException{
		long startTime = System.currentTimeMillis();
		cStmt.executeBatch();
		long diff = System.currentTimeMillis() - startTime;
		
	}

	private ResultSet executeStatement(String sql) throws SQLException{
		long startTime = System.currentTimeMillis();
		ResultSet rs =  stmt.executeQuery(sql);
		long diff = System.currentTimeMillis() - startTime;
		
		return rs;
	}
	
	
	public void executeBatchNamedCallableStatement(String sql, List<Map<String, Object>> params){
		try {
			NamedCallableStatment nCStmt = new BatchNamedCallableStatement(conn, sql, params);
			cStmt = nCStmt.getCStmt();
			executeBatchCallableStatement();
		}
		catch (SQLException ex) {
			throw new DBException(ex);
		}
	}
	
	/**
	 * @return the transactionBased
	 */
	public static ThreadLocal<Boolean> getTransactionBased() {
		return transactionBased;
	}

	private final static Logger LOG = Logger.getLogger(DatabaseHelper.class.getName());

	@Override
	public List<Object> executePreparedStmtWithCallbackMethod(
			List<String> fieldNames, String tableName, String whereClause,
			List<Object> parameters, Object targetObject, Method method)
			throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

}
