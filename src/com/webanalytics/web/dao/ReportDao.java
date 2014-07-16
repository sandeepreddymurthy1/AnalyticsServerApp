package com.webanalytics.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webanalytics.web.dto.analytics.ReportData;
import com.webanalytics.web.dto.db.GenericDBDTO;
import com.webanalytics.web.dto.setup.Configuration;
import com.webanalytics.web.util.JAXBContextHelper;

public class ReportDao extends BaseDao {

	public ReportData insertReport( Integer configId,ReportData reportData  ){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("xmlData", JAXBContextHelper.objectToXml(reportData));
		params.put("configId", configId);
		String sql = "INSERT INTO Report (xmlData,configId) VALUES (:xmlData:,:configId:)";
		Integer key = executeNamedCallableStatementWithKeys(sql, params);
		reportData.setId(key);
		return reportData;
		
	}
	
	public List<ReportData> getReportList( Integer configId ){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("configId", configId);
		String query = "select xmlData as stringUse1, id as intUse1 from Report where configId = :configId:";
		List<GenericDBDTO> results = (List<GenericDBDTO>)executeNamedPreparedStatement(query, params,GenericDBDTO.class);
		List<ReportData> allReports = new ArrayList<ReportData>();
		for( GenericDBDTO result : results ){
			String str = result.getString1();
			ReportData reportData = new ReportData();
			reportData = (ReportData)JAXBContextHelper.xmlToObject(str, ReportData.class);
			reportData.setId(result.getIntUse1());
			allReports.add(reportData);
		}
		return allReports;
	}
	
	public ReportData updateConfiguration(ReportData reportData){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("xmlData", JAXBContextHelper.objectToXml(reportData));
		params.put("id", reportData.getId());
		String sql = "UPDATE Report SET xmlData=:xmlData: WHERE id=:id:";
		executeNamedCallableStatement(sql, params);
		return reportData;
	}
	
	public void removeConfiguration(ReportData report){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", report.getId());
		String sql = "Delete from Report WHERE id=:id:";
		executeNamedCallableStatement(sql, params);
	}
}
