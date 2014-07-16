package com.webanalytics.web.dto;

public class SystemInfo {
	
	public static final String NEPTUNE="neptune";
	public static final String CLASSIC="classic";
	public static final String GRAY = "grey";
	
	private String theme;
	private int currentMemoryUsage;// In GB
	private int maxMemoryAllowed; // In GB
	private String language;
	private int configCount;
	private String timeZone;
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getConfigCount() {
		return configCount;
	}
	public void setConfigCount(int configCount) {
		this.configCount = configCount;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public int getCurrentMemoryUsage() {
		return currentMemoryUsage;
	}
	public void setCurrentMemoryUsage(int currentMemoryUsage) {
		this.currentMemoryUsage = currentMemoryUsage;
	}
	public int getMaxMemoryAllowed() {
		return maxMemoryAllowed;
	}
	public void setMaxMemoryAllowed(int maxMemoryAllowed) {
		this.maxMemoryAllowed = maxMemoryAllowed;
	}
}
