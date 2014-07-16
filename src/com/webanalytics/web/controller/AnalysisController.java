package com.webanalytics.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.web.dao.ConfigurationDao;
import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.ConfigList;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.analytics.ReportData;
import com.webanalytics.web.dto.setup.Configuration;
import com.webanalytics.web.util.Cache;
import com.webanalytics.web.worker.ReportWorker;
import com.webanalytics.web.worker.SetupWorker;


@Controller
@RequestMapping( value="/analysis")
public class AnalysisController {

	@Autowired
	SetupWorker setupWorker = null;
	
	@Autowired
	ReportWorker reportWorker = null;
	
	@RequestMapping( value = "/getAllConfigs.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getAllConfigs(Model model){
		List<ConfigList> configList = new ArrayList<ConfigList>();
		for(Configuration configuration : setupWorker.getConfigs() ){
			ConfigList list = new ConfigList();
			list.setConfigurationName(configuration.getConfigurationName());
			list.setId(configuration.getId());
			configList.add(list);
		}
		return new JsonResult(configList);
	}
	
	@RequestMapping( value = "/getAnalyticsForConfig.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getAnalyticsForConfig(@RequestBody ConfigList config){
		
		List<ReportData> results = reportWorker.getAllReportDataForConfig(config.getId());
		return new JsonResult(results);
	}
	

	
}
