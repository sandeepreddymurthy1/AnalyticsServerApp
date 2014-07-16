package com.webanalytics.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.db.GenericDBDTO;
import com.webanalytics.web.util.JAXBContextHelper;

public class AppDataDao extends BaseDao {

	public void insertAppData( AppData appData){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("xmlData", JAXBContextHelper.objectToXml(appData));
		params.put("registeredEmail", appData.getRegisteredEmail());
		String sql = "INSERT INTO AppInfo (xmlData,registeredEmail) VALUES (:xmlData:,:registeredEmail:)";
		Integer key = executeNamedCallableStatementWithKeys(sql, params);
		System.out.println(key);
	}
	
	public AppData getMemberAppData( String emailId ){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("registeredEmail", emailId);
		String query = "select xmlData as stringUse1, registeredEmail as stringUse2, id as intUse1 from AppInfo where registeredEmail = :registeredEmail:";
		List<GenericDBDTO> results = (List<GenericDBDTO>)executeNamedPreparedStatement(query, params,GenericDBDTO.class);
		AppData appData = null;
		if( results.size() > 0 ){
			GenericDBDTO result = results.get(0);
			String str = result.getString1();
			appData = (AppData)JAXBContextHelper.xmlToObject(str, AppData.class);
			appData.setRegisteredEmail(result.getString2());
			appData.setAppId(result.getIntUse1());
		}
		return appData;
	}
	
	public AppData getMemberAppData( Integer id ){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String query = "select xmlData as stringUse1, registeredEmail as stringUse2, id as intUse1 from AppInfo where id = :id:";
		List<GenericDBDTO> results = (List<GenericDBDTO>)executeNamedPreparedStatement(query, params,GenericDBDTO.class);
		AppData appData = null;
		if( results.size() > 0 ){
			GenericDBDTO result = results.get(0);
			String str = result.getString1();
			appData = (AppData)JAXBContextHelper.xmlToObject(str, AppData.class);
			appData.setRegisteredEmail(result.getString2());
			appData.setAppId(result.getIntUse1());
		}
		return appData;
	}
	
	public void updateAppData( AppData appData ){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("xmlData", JAXBContextHelper.objectToXml(appData));
		params.put("id", appData.getAppId());
		String sql = "UPDATE AppInfo SET xmlData=:xmlData: WHERE id=:id:";
		executeNamedCallableStatement(sql, params);
	}
	
}
