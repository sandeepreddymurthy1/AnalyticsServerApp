package com.webanalytics.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.web.dto.ConfigList;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.setup.Configuration;
import com.webanalytics.web.util.AnalyticUtil;
import com.webanalytics.web.util.Constant;
import com.webanalytics.web.worker.AppDataWorker;
import com.webanalytics.web.worker.SetupWorker;

@Controller
@RequestMapping( value="/setup")
public class SetupController {
	
	@Autowired
	AppDataWorker appDataWorker = null;
	
	@Autowired
	SetupWorker setupWorker = null;
	
	
	@RequestMapping( value = "/getAllConfigs.json")
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
	
	
	
	@RequestMapping( value = "/getEmptyConfiguration.json")
	public @ResponseBody JsonResult getEmptyConfiguration(Model model){
		
		Configuration configuration = new Configuration();
		configuration.setWebsiteTrackId(AnalyticUtil.generateKey());
		configuration.setScriptUrl(Constant.CONNECTOR_URL);
		configuration.setRefreshRate(10);
		return new JsonResult(configuration);
	}
	
	@RequestMapping( value = "/saveConfiguration.json")
	public @ResponseBody JsonResult saveConfiguration(@RequestBody Configuration configuration){
		setupWorker.saveConfiguration(configuration);
		return new JsonResult(configuration);
	}
	
	
	@RequestMapping( value = "/getConfiguration.json")
	public @ResponseBody JsonResult getConfiguration(@RequestBody ConfigList configuration){
		
		Configuration config = null;
		for( Configuration temp : setupWorker.getConfigs() ){
			if( temp.getId().equals(configuration.getId())){
				config = temp;
				break;
			}
		}
		return new JsonResult(config);
	}
	@RequestMapping( value = "/removeConfiguration.json")
	public @ResponseBody JsonResult removeConfiguration(@RequestBody Configuration configuration){
		setupWorker.removeConfiguration(configuration);
		return new JsonResult(configuration);
	}
	

	
	

}
