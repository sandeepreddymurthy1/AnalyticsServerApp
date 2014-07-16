package com.webanalytics.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name="Member")
public class Registration extends MemberProfile {

	private String password;
	private String retypePassword;

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
