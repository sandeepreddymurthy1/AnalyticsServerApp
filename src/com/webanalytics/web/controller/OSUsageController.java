package com.webanalytics.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.hbase.dao.OSSummaryDao;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.graph.PieGraphMetaData;
import com.webanalytics.web.util.AnalyticUtil;
import com.webanalytics.web.util.Cache;

@Controller
@RequestMapping( value="/osUsage")
public class OSUsageController {


	@Autowired
	OSSummaryDao osSummaryDao = null;
	
	@RequestMapping( value = "/getUsageDaily.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getUsageDaily(Model model){
		Map<String, Integer> result = osSummaryDao.getSummaryForDay(""+Cache.getAppData().getAppId());
		PieGraphMetaData metaData = new PieGraphMetaData();
		metaData.setData(AnalyticUtil.convertToDoubleMap(result));
		return new JsonResult(metaData);
	}
	
	@RequestMapping( value = "/getUsageWeekly.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getUsageWeekly(){
		Map<String, Integer> result = osSummaryDao.getSummaryComplete(""+Cache.getAppData().getAppId());
		PieGraphMetaData metaData = new PieGraphMetaData();
		metaData.setData(AnalyticUtil.convertToDoubleMap(result));
		return new JsonResult(metaData);
	}
	@RequestMapping( value = "/getUsageMonthly.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getUsageMonthly(){
		Map<String, Integer> result = osSummaryDao.getSummaryComplete(""+Cache.getAppData().getAppId());
		PieGraphMetaData metaData = new PieGraphMetaData();
		metaData.setData(AnalyticUtil.convertToDoubleMap(result));
		return new JsonResult(metaData);
	}
	@RequestMapping( value = "/getUsageComplete.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getUsageComplete(){
		Map<String, Integer> result = osSummaryDao.getSummaryComplete(""+Cache.getAppData().getAppId());
		PieGraphMetaData metaData = new PieGraphMetaData();
		metaData.setData(AnalyticUtil.convertToDoubleMap(result));
		return new JsonResult(metaData);
	}
}
