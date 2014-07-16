package com.webanalytics.web.util;

import java.util.List;

import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.setup.Configuration;

public class Cache {

	private static ThreadLocal<CacheHolder> cacheHolder = new ThreadLocal<CacheHolder>();
	
	
	public static CacheHolder getCacheHolder(){
		return cacheHolder.get();
	}
	
	public static void setCacheHolder(CacheHolder holder ){
		Cache.cacheHolder.set(holder);
	}
	
	public static AppData getAppData(){
		return cacheHolder.get().getAppData();
	}
	
	public static List<Configuration> getConfigurations(){
		return cacheHolder.get().getConfigs();
	}
	
	public static void initializeCache(){
		CacheHolder holder = (CacheHolder)SecurityHolder.getCurrentSession().getAttribute("cache");
		setCacheHolder(holder);
//		setAppData(holder.getAppData());
//		setConfigurations(holder.getConfigs());
	}
	
	public static void createCache(AppData appData){
		CacheHolder cacheHolder = new CacheHolder();
		cacheHolder.setAppData(appData);
		SecurityHolder.getCurrentSession().setAttribute("cache",cacheHolder);
		//setAppData(appData);
	}
	
	public static void clearCache(){
		//setAppData(null);
		setCacheHolder(null);
		//setConfigurations(null);
	}
	
}
