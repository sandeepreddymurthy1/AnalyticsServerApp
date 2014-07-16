package com.webanalytics.web.util;

import java.util.List;

import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.setup.Configuration;

public class CacheHolder {

	private AppData appData = null;
	private List<Configuration> configs = null;
	public AppData getAppData() {
		return appData;
	}
	public void setAppData(AppData appData) {
		this.appData = appData;
	}
	public List<Configuration> getConfigs() {
		return configs;
	}
	public void setConfigs(List<Configuration> configs) {
		this.configs = configs;
	}
}
