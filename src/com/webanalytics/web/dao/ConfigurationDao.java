package com.webanalytics.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webanalytics.web.dto.db.GenericDBDTO;
import com.webanalytics.web.dto.setup.Configuration;
import com.webanalytics.web.util.JAXBContextHelper;

public class ConfigurationDao  extends BaseDao {

	public Configuration insertConfiguration( Integer appId, Integer configTypeId,Configuration configuration  ){
	
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("xmlData", JAXBContextHelper.objectToXml(configuration));
		params.put("appId", appId);
		params.put("configEnumId", configTypeId);
		String sql = "INSERT INTO Configuration (xmlData,appId,configEnumId) VALUES (:xmlData:,:appId:,:configEnumId:)";
		Integer key = executeNamedCallableStatementWithKeys(sql, params);
		configuration.setId(key);
		return configuration;
		
	}
	
	public List<Configuration> getConfigList( Integer appId ){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		String query = "select xmlData as stringUse1, id as intUse1 from Configuration where appId = :appId:";
		List<GenericDBDTO> results = (List<GenericDBDTO>)executeNamedPreparedStatement(query, params,GenericDBDTO.class);
		List<Configuration> allConfigs = new ArrayList<Configuration>();
		for( GenericDBDTO result : results ){
			String str = result.getString1();
			Configuration configuration = new Configuration();
			configuration = (Configuration)JAXBContextHelper.xmlToObject(str, Configuration.class);
			configuration.setId(result.getIntUse1());
			allConfigs.add(configuration);
		}
		return allConfigs;
	}
	
	public Configuration updateConfiguration(Configuration configuration){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("xmlData", JAXBContextHelper.objectToXml(configuration));
		params.put("id", configuration.getId());
		String sql = "UPDATE Configuration SET xmlData=:xmlData: WHERE id=:id:";
		executeNamedCallableStatement(sql, params);
		return configuration;
	}
	
	public void removeConfiguration(Configuration configuration){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", configuration.getId());
		String sql = "Delete from Configuration WHERE id=:id:";
		executeNamedCallableStatement(sql, params);
	}
	
}
