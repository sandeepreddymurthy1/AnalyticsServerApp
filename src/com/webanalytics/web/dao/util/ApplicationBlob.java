/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id$
 *
 * Description:
 *
 * STORY: 
 *
 * DEFECT:
 *
 */
package com.webanalytics.web.dao.util;

import java.io.InputStream;
import java.io.Serializable;

public class ApplicationBlob implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -10619977196955477L;

	private InputStream inputStream = null;

	private long length;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}
}
