package com.webanalytics.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.hbase.dao.PageHitSummaryDao;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.graph.PieGraphMetaData;
import com.webanalytics.web.util.Cache;

@Controller
@RequestMapping( value="/relativeSiteUsage")
public class RelativeSiteAccessController {

	@Autowired
	PageHitSummaryDao pageHitSummaryDao = null;
	
	@RequestMapping( value = "/getUsageComplete.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getUsageComplete(){
	//	Map<String, Integer> result = pageHitSummaryDao.getPageHitSummary(""+Cache.getAppData().getAppId());
		PieGraphMetaData metaData = new PieGraphMetaData();
		Map<String, Double> data = new HashMap<String, Double>();
//		for( Entry<String,Integer> entry : result.entrySet() ){
//			String str = entry.getKey();
//			String[] split = str.split("/");
//			data.put(split[split.length-1], new Double(entry.getValue()));
//		}
		data.put("index.jsp", new Double(100));
		data.put("contact.jsp",new Double(20));
		data.put("services", new Double(40));
		data.put("products", new Double(30));
		metaData.setData(data);
		return new JsonResult(metaData);
	}

}
