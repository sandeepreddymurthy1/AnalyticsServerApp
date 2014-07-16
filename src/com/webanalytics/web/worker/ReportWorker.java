package com.webanalytics.web.worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.webanalytics.hbase.dao.PageHitSummaryDao;
import com.webanalytics.web.dao.ReportDao;
import com.webanalytics.web.dto.analytics.ReportData;
import com.webanalytics.web.dto.graph.PieGraphMetaData;


public class ReportWorker {

	@Autowired
	PageHitSummaryDao pageHitSummaryDao = null;
	
	@Autowired
	ReportDao reportDao = null;
	
	public PieGraphMetaData getPieData(){
		PieGraphMetaData metaData = new PieGraphMetaData();
		metaData.setTitle("Site Access");
		Map<String, Double> data = new HashMap<String, Double>();
		Map<String, Integer> result = pageHitSummaryDao.getPageHitSummary("");
		for( Entry<String,Integer> entry : result.entrySet() ){
			String str = entry.getKey();
			String[] split = str.split("/");
			data.put(split[split.length-1], new Double(entry.getValue()));
		}
		metaData.setData(data);
		return metaData;
	}
	
	public List<ReportData> getAllReportDataForConfig(Integer configId){
		
		return reportDao.getReportList(configId);
	}
}
