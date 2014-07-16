/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: IDatabaseHelper.java 7755 2011-03-28 17:42:27Z sunil.desu.osv $
 *
 * Description:
 * 
 * Story: 
 *
 * Defect:
 *
 */
package com.webanalytics.web.dao.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import com.webanalytics.web.exception.DBException;


/**
 * $Id: IDatabaseHelper.java 7755 2011-03-28 17:42:27Z sunil.desu.osv $
 * Interface for database helper classes implementing the DML operations for
 * database
 */

public interface IDatabaseHelper {

	/**
	 * Data source name is set and createConnection is called. There must be a
	 * data source in the servlet container defined with the name.
	 * 
	 * @Method setDataSource
	 * @param ds
	 *            The JDBC datasource name
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 **/
	public void setDataSource(DataSource ds) throws DBException;

	/**
	 * close method closes all open cursors,statements and connections
	 * 
	 * @Method close
	 * @throws DBException
	 *             A generic Database Exception is thrown
	 * @return void
	 * 
	 **/
	public void close();

	
	public List<Object> executePreparedStmtWithCallbackMethod(List<String> fieldNames, String tableName, String whereClause,
			List<Object> parameters, Object targetObject, Method method)
			throws DBException;

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
	public List<Object> executeUniquePreparedStmt(String sql, List<Object> values, int numColumns);

	
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
	public int executeInsert(String tableName, List<String> fields, List<Object> values);

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
	public int executeDelete(String tableName, String whereClause);

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
	public int executeUpdate(String tableName, List<String> fields, List<Object> values, String whereClause);

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
	public void setAutoCommit(boolean a);

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
	public void commit();

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
			throws DBException;

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
	public void executeInsertBatch(String tableName, List<String> fields, List<List<Object>> values);

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
	public void executeStoredProcedure(String procedureName, List<Object> fields);

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
	public String executeStoredProcedureWithStringResult(String procedureName, List<Object> fields);

	/**
	 * executeStoredProcedureWithIntegerResult executes a stored procedure which
	 * returns a String
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of fields that are being passed to the stored procedure
	 * @return Integer The Integer that the stored procedure returns
	 * @throws DBException
	 */
	public int executeStoredProcedureWithIntegerResult(String procedureName, List<Object> fields);

	public int executeStoredProcedureForBlobWithIntegerResult(String procedureName, List<Object> fields);

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
	public List<Object> executeStoredProcedureWithInOutParams(String procedureName, List<Object> fields);

	/**
	 * overloaded executeStoredProcedureWithInOutParams executes a stored
	 * procedure that can take parameters in and provide a value back for those
	 * parameters
	 * 
	 * @param procedureName
	 *            Name of the stored procedure to execute
	 * @param fields
	 *            List of in fields that are being passed to the stored
	 *            procedure
	 * @param outParamSize
	 *            Size of the returned parameters
	 * @return List<Object> List of out parameters returned from the stored
	 *         procedure corresponding to fields
	 * @throws DBException
	 */
	public List<Object> executeStoredProcedureWithInOutParams(String procedureName, List<Object> fields,
			int outParamSize);

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
	public <T> List<T> executeStatement(String sql, Class<T> converterDto);

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

	public <T> List<T> executeNamedPreparedStatement(String sql, Map<String, Object> params, Class<T> converterDto);

	public <T> List<T> executeNamedPreparedStatement(String sql, Map<String, Object> params, String listParam,
			List<?> paramList, Class<T> converterDto);

	public <T> List<T> executeNamedPreparedStatement(String sql, Map<String, Object> params, String listParam,
			Set<?> paramList, Class<T> converterDto);

	public List<List<Object>> executeStatement(String sql, int numColumns);

	/**
	 * Execute callable statement with string value.
	 * 
	 * @param sql
	 *            the sql
	 * @param params
	 *            the params
	 * @return the string
	 */
	public String executeNamedCallableStatementWithStringValue(String sql, Map<String, Object> params);

	/**
	 * Execute callable statement.
	 * 
	 * @param sql
	 *            the sql
	 * @param params
	 *            the params
	 */
	public void executeNamedCallableStatement(String sql, Map<String, Object> params);


	
	/**
	 * 
	 * @param sql
	 * @param params
	 */
	public void executeBatchNamedCallableStatement(String sql, List<Map<String, Object>> params);

}
