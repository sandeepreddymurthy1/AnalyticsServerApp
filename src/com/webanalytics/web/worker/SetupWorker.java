package com.webanalytics.web.worker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webanalytics.web.dao.AppDataDao;
import com.webanalytics.web.dao.ConfigurationDao;
import com.webanalytics.web.dao.EnumDao;
import com.webanalytics.web.dao.ReportDao;
import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.analytics.ReportData;
import com.webanalytics.web.dto.enums.ReportTypeEnum;
import com.webanalytics.web.dto.setup.Configuration;
import com.webanalytics.web.util.Cache;

public class SetupWorker {

	@Autowired
	AppDataDao appDataDao = null;
	
	@Autowired
	ConfigurationDao configDao = null;
	
	@Autowired
	EnumDao enumDao = null;
	
	@Autowired
	ReportDao reportDao = null;

	public Configuration saveConfiguration(Configuration configuration) {
		AppData appData = Cache.getAppData();
		if (configuration.getId() == null || configuration.getId() == 0) {
			configuration = configDao.insertConfiguration(appData.getAppId(), 1, configuration);
			Cache.getConfigurations().add(configuration);
			// Add analytics report
			List<ReportTypeEnum> reportEnums =  enumDao.getAllReportTypes();
			for( ReportTypeEnum reportEnum : reportEnums){
				ReportData reportData = new ReportData();
				reportData.setConfigId(configuration.getId());
				reportData.setReportTitle(reportEnum.getDescription());
				reportData.setReportType(reportEnum.getReportType());
				reportData.setAppId(appData.getAppId());
				reportDao.insertReport(configuration.getId(), reportData);
			}
			
		}else{
			configuration = configDao.updateConfiguration(configuration);
			int idx = Cache.getConfigurations().indexOf(configuration);
			Cache.getConfigurations().set(idx, configuration);
		}
		return configuration;
	}
	
	public void removeConfiguration(Configuration configuration){
		configDao.removeConfiguration(configuration);
		// Remove Config from Cache
		Cache.getConfigurations().remove(configuration);

	}
	
	public List<Configuration> getConfigs(){
		List<Configuration> configs = Cache.getConfigurations();
		if( configs == null ){
			configs = configDao.getConfigList(Cache.getAppData().getAppId());
			Cache.getCacheHolder().setConfigs(configs);
		}
		return configs;
	}
	
	
}
