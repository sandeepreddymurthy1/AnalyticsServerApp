package com.webanalytics.web.dto;

public class UserSuggestion {

	private String key;
	private String message;
	private boolean remindMe = true;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isRemindMe() {
		return remindMe;
	}
	public void setRemindMe(boolean remindMe) {
		this.remindMe = remindMe;
	}
	
}
