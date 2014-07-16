package com.webanalytics.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.ReportIdentifier;
import com.webanalytics.web.dto.analytics.ReportData;
import com.webanalytics.web.dto.dashboard.AvailableExistingReport;
import com.webanalytics.web.dto.dashboard.DashboardMetaData;
import com.webanalytics.web.dto.dashboard.ReportPanelInfo;
import com.webanalytics.web.dto.setup.Configuration;
import com.webanalytics.web.util.Cache;
import com.webanalytics.web.worker.AppDataWorker;
import com.webanalytics.web.worker.ReportWorker;
import com.webanalytics.web.worker.SetupWorker;
@Controller
@RequestMapping( value="/dashboard")
public class DashboardController {

	@Autowired
	AppDataWorker appWorker = null;
	
	@Autowired
	ReportWorker reportWorker = null;
	
	@Autowired
	SetupWorker setupWorker = null;
	
	@RequestMapping( value = "/getMetaData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getMetaData(Model model){
		return new JsonResult(Cache.getAppData().getDashboardMetaData());
	}
	
	@RequestMapping( value = "/saveMetaData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult saveMetaData(@RequestBody DashboardMetaData metaData){
		Cache.getAppData().setDashboardMetaData(metaData);
		appWorker.updateAppData(Cache.getAppData());
		return new JsonResult("success");
	}
	
	@RequestMapping( value = "/getAvailableExistingReports.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getAvailableExistingReports(){
		
		AvailableExistingReport reports = new AvailableExistingReport();
		List<Configuration> allConfigs =  setupWorker.getConfigs();
		
		for( Configuration config : allConfigs ){
			for(ReportData reportData : reportWorker.getAllReportDataForConfig(config.getId())){
				ReportIdentifier reportIdentifier = new ReportIdentifier();
				reportIdentifier.setConfigName(config.getConfigurationName());
				reportIdentifier.setConfigId(config.getId());
				reportIdentifier.setReportName(reportData.getReportTitle());
				reportIdentifier.setReportId(reportData.getId());
				reports.getAvailableReports().add(reportIdentifier);
			}
		}

		AppData appData = Cache.getAppData();
		for( ReportPanelInfo panelInfo : appData.getDashboardMetaData().getReportPanelsInfo()){
			reports.getExistingReports().add(panelInfo);
			reports.getAvailableReports().remove(panelInfo);
		}
		
		
		return new JsonResult(reports);
	}

	
	
	
}
