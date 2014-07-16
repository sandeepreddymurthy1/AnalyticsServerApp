package com.webanalytics.web.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;



import com.webanalytics.web.dao.util.DatabaseHelper;
import com.webanalytics.web.exception.DBException;

public class BaseDao {

	private static final Logger logger = Logger.getLogger(BaseDao.class.getName());
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	

	public void executeNamedCallableStatement(String sql,
			Map<String, Object> params) {

		DatabaseHelper helper = new DatabaseHelper();
		helper.setDataSource(dataSource);
		try {
			helper.executeNamedCallableStatement(sql, params);
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new DBException();
		} finally {
			helper.close();
		}
	}
	
	public Integer executeNamedCallableStatementWithKeys(String sql,
			Map<String, Object> params) {

		DatabaseHelper helper = new DatabaseHelper();
		helper.setDataSource(dataSource);
		try {
			return helper.executeNamedCallableStatementWithKeys(sql, params);
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new DBException();
		} finally {
			helper.close();
		}
	}
	
	

	public  List executeNamedPreparedStatement(String sql,
			Map<String, Object> params, Class cl) {
		DatabaseHelper helper = new DatabaseHelper();
		helper.setDataSource(dataSource);
		try {
			return helper.executeNamedPreparedStatement(sql, params, cl);
		} catch (Exception ex) {
			logger.error(ex,ex);
			 throw new DBException();
		} finally {
			helper.close();
		}

	}
	
	public void executeInsert(String tableName, List<String> fields, List<Object> values){
		DatabaseHelper helper = new DatabaseHelper();
		helper.setDataSource(dataSource);
		try {
			helper.executeInsert(tableName, fields, values);
		} catch (Exception ex) {
			logger.error(ex);
			 throw new DBException();
		} finally {
			helper.close();
		}
	}
}
