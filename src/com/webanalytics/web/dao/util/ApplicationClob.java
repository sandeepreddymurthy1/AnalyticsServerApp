package com.webanalytics.web.dao.util;

import java.io.Serializable;

public class ApplicationClob implements Serializable {

	private static final long serialVersionUID = -1929995248448857848L;
	private String log;

	public ApplicationClob(String log) {
		this.log = log;
	}

	public int getLength() {
		if (log == null)
			return 0;
		return log.length();
	}

	public String getString() {
		return log;
	}

}
