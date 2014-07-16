/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: DBException.java 4615 2010-09-10 16:38:47Z vamsi.chiluvri.osv $
 *
 * Description:
 * 
 * Story: 
 *
 * Defect:
 *
 */
package com.webanalytics.web.exception;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.webanalytics.web.dao.BaseDao;



public class DBException extends RuntimeException {

	private static final long serialVersionUID = 4124625101528200413L;
	private static final Logger logger = Logger.getLogger(DBException.class.getName());
	
	public DBException() {
		super();
	}

	public DBException(String msg) {
		logger.error(msg);
	}

	public DBException(String msg, Throwable t) {
		logger.error(msg);
		logger.error(t,t);
	}

	public DBException(SQLException e) {
		logger.error(e,e);
	}

	public String getMessage() {

		return "";
	}
}
