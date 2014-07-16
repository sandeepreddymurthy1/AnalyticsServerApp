package com.webanalytics.web.worker;

import org.springframework.beans.factory.annotation.Autowired;

import com.webanalytics.web.dao.AppDataDao;
import com.webanalytics.web.dto.AppData;

public class AppDataWorker {

	@Autowired
	AppDataDao dao = null;
	public void insertAppData(AppData appData){
		// Logic to add new 
		dao.insertAppData(appData);
	}
	
	public void updateAppData( AppData appData){
		dao.updateAppData(appData);
	}
}
