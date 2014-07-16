package com.webanalytics.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class AnalyticUtil {

	public static String generateKey(){
		 UUID idOne = UUID.randomUUID();
		return idOne.toString();
	}
	
	public static Map<String, Double> convertToDoubleMap(Map<String, Integer> input){
		Map<String, Double> data = new HashMap<String, Double>();
		for( Entry<String,Integer> entry : input.entrySet() ){
			data.put(entry.getKey(), new Double(entry.getValue()));
		}
		return data;
	}
}
