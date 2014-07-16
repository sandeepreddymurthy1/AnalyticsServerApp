package com.webanalytics.web.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.ChangePassword;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.MemberProfile;
import com.webanalytics.web.dto.Registration;
import com.webanalytics.web.dto.SystemInfo;
import com.webanalytics.web.util.Cache;
import com.webanalytics.web.util.SecurityHolder;
import com.webanalytics.web.worker.AdminWorker;
import com.webanalytics.web.worker.AppDataWorker;


@Controller
@RequestMapping( value="/admin")
public class AdminController {
	
	static Logger logger = Logger.getLogger(AdminController.class);
	
	@Autowired
	AdminWorker worker = null;
	
	@Autowired
	AppDataWorker appDataWorker = null;
	
	@RequestMapping( value = "/getMemberProfile.json")
	public @ResponseBody JsonResult getMemberProfile(Model model){
		JsonResult result = new JsonResult();
		result.setResult(SecurityHolder.getCurrentSessionUser());
		return result;
	}
	

	@RequestMapping( value = "/getSystemInfo.json")
	public @ResponseBody JsonResult getSystemInfo(Model model){
		AppData appData = Cache.getAppData();
		if(appData.getSystemInfo() == null){
			SystemInfo systemInfo = new SystemInfo();
			systemInfo.setCurrentMemoryUsage(16);
			systemInfo.setMaxMemoryAllowed(100);
			systemInfo.setTheme(SystemInfo.NEPTUNE);
			systemInfo.setLanguage("English-US");
			systemInfo.setTimeZone("US-Central Time (GMT+5.00)");
			appData.setSystemInfo(systemInfo);
			appDataWorker.updateAppData(appData);
		}
		return new JsonResult(appData.getSystemInfo());
	}
	
	@RequestMapping( value = "/register.json")
	public @ResponseBody JsonResult saveMember(@RequestBody Registration registration){
		JsonResult result = new JsonResult();
		worker.registerMember(registration);
		return result;
	}
	
	@RequestMapping( value = "/login.json")
	public @ResponseBody JsonResult login(@RequestBody Registration registration){
		JsonResult result = new JsonResult();
		MemberProfile profile =  worker.login(registration.getEmail(), registration.getPassword());
		if( profile != null ){
			result.setSuccess(true);
			result.setResult(profile);
		}else{
			result.setSuccess(false);
		}
		SecurityHolder.setUserForSession(profile);
		return result;
	}
	
	@RequestMapping( value = "/changePassword.json")
	public @ResponseBody JsonResult changePassword(@RequestBody ChangePassword changePassword){
		JsonResult result = new JsonResult();
		boolean success = worker.updatePassword(SecurityHolder.getCurrentSessionUser().getEmail(), changePassword);
		if( success ){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping( value = "/logoff.json")
	public @ResponseBody JsonResult logOff(Model model){
		JsonResult result = new JsonResult();
		SecurityHolder.getCurrentSession().invalidate();
		return result;
	}

}
