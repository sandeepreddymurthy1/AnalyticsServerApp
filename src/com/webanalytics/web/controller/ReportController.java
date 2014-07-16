package com.webanalytics.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.ReportIdentifier;
import com.webanalytics.web.dto.graph.BarGraphMetaData;
import com.webanalytics.web.dto.graph.LineGraphMetaData;
import com.webanalytics.web.dto.graph.PieGraphMetaData;
import com.webanalytics.web.dto.map.MapSiteMetaData;
import com.webanalytics.web.dto.map.SiteAccessLatLng;
import com.webanalytics.web.util.Cache;
import com.webanalytics.web.worker.ReportWorker;


@Controller
@RequestMapping( value="/report")
public class ReportController {

	@Autowired
	ReportWorker reportWorker = null;
	
	@RequestMapping( value = "/getReportMetaData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getReportMetaData(@RequestBody ReportIdentifier reportIdentifier){
		AppData appData = Cache.getAppData();
	//	ConfigAnalytics configAnalytics = appData.getAllConfigs().get(reportIdentifier.getConfigUId()).getConfigAnalytics();
	//	ReportMetaData reportMetaData = configAnalytics.getAllReports().get(reportIdentifier.getReportUId());
		return new JsonResult("");
	}
	
	@RequestMapping( value = "/getReportData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getReportData(@RequestBody ReportIdentifier reportIdentifier){
		LineGraphMetaData metaData = new LineGraphMetaData();
		metaData.setxAxisLabel("Month");
		metaData.setyAxisLabel("Page Hits");
		List<String> fields = new ArrayList<String>();
		fields.add("month");
		fields.add("home.jsp");
		fields.add("info.jsp");
		fields.add("contact.jsp");
		
		metaData.setFields(fields);
		metaData.setxField("month");
		List<String> yFields = new ArrayList<String>();
		yFields.add("home.jsp");
		yFields.add("info.jsp");
		yFields.add("contact.jsp");
		
		metaData.setyFields(yFields);
		
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("month", "January");
		row.put("home.jsp", 20);
		row.put("info.jsp", 30);
		row.put("contact.jsp", 22);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "February");
		row.put("home.jsp", 40);
		row.put("info.jsp", 22);
		row.put("contact.jsp", 12);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "March");
		row.put("home.jsp", 10);
		row.put("info.jsp", 33);
		row.put("contact.jsp", 10);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "April");
		row.put("home.jsp", 37);
		row.put("info.jsp", 34);
		row.put("contact.jsp", 22);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "May");
		row.put("home.jsp", 44);
		row.put("info.jsp", 34);
		row.put("contact.jsp", 22);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("month", "June");
		row.put("home.jsp", 44);
		row.put("info.jsp", 62);
		row.put("contact.jsp", 26);
		data.add(row);
		
		metaData.setData(data);
		return new JsonResult(metaData);
	}
	
	
	@RequestMapping( value = "/getPieData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getPieData(@RequestBody ReportIdentifier reportIdentifier){
		PieGraphMetaData metaData = reportWorker.getPieData();
		
		return new JsonResult(metaData);
	}
	
	@RequestMapping( value = "/getHorizontalBarData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getHorizontalBarData(@RequestBody ReportIdentifier reportIdentifier){
		
		BarGraphMetaData metaData = new BarGraphMetaData();
		metaData.setTitle("Site Access By City and Page");
		List<String> fields = new ArrayList<String>();
		fields.add("city");
		fields.add("home.jsp");
		fields.add("info.jsp");
		fields.add("contact.jsp");
		
		metaData.setFields(fields);
		metaData.setxField("city");
		List<String> yFields = new ArrayList<String>();
		yFields.add("home.jsp");
		yFields.add("info.jsp");
		yFields.add("contact.jsp");
		
		metaData.setyFields(yFields);
		
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("city", "Dallas");
		row.put("home.jsp", 20);
		row.put("info.jsp", 30);
		row.put("contact.jsp", 22);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("city", "Houston");
		row.put("home.jsp", 40);
		row.put("info.jsp", 22);
		row.put("contact.jsp", 12);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("city", "New York");
		row.put("home.jsp", 10);
		row.put("info.jsp", 33);
		row.put("contact.jsp", 10);
		data.add(row);
		
		row = new HashMap<String, Object>();
		row.put("city", "Chicago");
		row.put("home.jsp", 37);
		row.put("info.jsp", 34);
		row.put("contact.jsp", 22);
		data.add(row);
		
		metaData.setData(data);
		
		return new JsonResult(metaData);
	}
	
	@RequestMapping( value = "/getVerticalBarData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getVerticalBarData(@RequestBody ReportIdentifier reportIdentifier){
		
		return getHorizontalBarData(reportIdentifier);
	}
	
	
	
	
}
