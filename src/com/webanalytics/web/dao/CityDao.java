package com.webanalytics.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webanalytics.web.dto.CityInfo;
import com.webanalytics.web.dto.db.GenericDBDTO;

public class CityDao extends BaseDao {

	private Map<Integer, CityInfo> cityCache = new HashMap<Integer, CityInfo>();
	
	public CityInfo getCityInfoById(Integer id){
		CityInfo info = cityCache.get(id);
		if( info == null ){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			String query = "select * from cityByCountry where city = :id:";
			List<CityInfo> results = (List<CityInfo>)executeNamedPreparedStatement(query, params,CityInfo.class);
			if( results.size() > 0 ){
				info = results.get(0);
				cityCache.put(id, info);
				
			}else{
				System.out.println("City not found");
			}
			
		}
		return info;
	}
}
