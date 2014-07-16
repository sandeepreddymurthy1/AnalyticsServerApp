package com.webanalytics.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.webanalytics.web.dto.dashboard.DashboardMetaData;

@XmlRootElement
public class AppData {

	private Integer appId;
	private String registeredEmail;
	private DashboardMetaData dashboardMetaData = new DashboardMetaData();
	

	private List<UserSuggestion> userSuggestions = new ArrayList<UserSuggestion>();

	private SystemInfo systemInfo;
	

	public String getRegisteredEmail() {
		return registeredEmail;
	}

	public void setRegisteredEmail(String registeredEmail) {
		this.registeredEmail = registeredEmail;
	}

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	public AppData() {

	}
	

	public static AppData createNewAppData() {
		AppData appData = new AppData();
		UserSuggestion userSuggestion = new UserSuggestion();
		userSuggestion.setKey("dashboard_empty_config");
		userSuggestion
				.setMessage("Please setup a new config which can imported in to the dashboard");
		appData.getUserSuggestions().add(userSuggestion);
		userSuggestion = new UserSuggestion();
		userSuggestion.setKey("dashboard_add_edit");
		userSuggestion
				.setKey("Click on edit to add or edit new reports to the dashboard.  " +
						"You can also edit the size of the each window by drag at the edges.  Please save the dashboard when you are done. ");
		appData.getUserSuggestions().add(userSuggestion);
		
		SystemInfo systemInfo = new SystemInfo();
		systemInfo.setCurrentMemoryUsage(16);
		systemInfo.setMaxMemoryAllowed(100);
		systemInfo.setTheme(SystemInfo.NEPTUNE);
		
		return appData;
	}
	
	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public List<UserSuggestion> getUserSuggestions() {
		return userSuggestions;
	}

	public void setUserSuggestions(List<UserSuggestion> userSuggestions) {
		this.userSuggestions = userSuggestions;
	}


	public DashboardMetaData getDashboardMetaData() {
		return dashboardMetaData;
	}

	public void setDashboardMetaData(DashboardMetaData dashboardMetaData) {
		this.dashboardMetaData = dashboardMetaData;
	}


}
