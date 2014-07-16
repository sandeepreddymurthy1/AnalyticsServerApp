package com.webanalytics.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webanalytics.hbase.dao.LocationSummaryDao;
import com.webanalytics.web.dao.CityDao;
import com.webanalytics.web.dto.CityInfo;
import com.webanalytics.web.dto.JsonResult;
import com.webanalytics.web.dto.ReportIdentifier;
import com.webanalytics.web.dto.map.MapSiteMetaData;
import com.webanalytics.web.dto.map.SiteAccessLatLng;
import com.webanalytics.web.util.Cache;

@Controller
@RequestMapping( value="/location")
public class LocationInfoController {

	@Autowired
	LocationSummaryDao locationSummaryDao = null;
	
	@Autowired
	CityDao cityDao = null;
	
	@RequestMapping( value = "/getSiteAccessGeoData.json", method = RequestMethod.POST)
	public @ResponseBody JsonResult getSiteAccessGeoData(){
		
		MapSiteMetaData metaData = new MapSiteMetaData();
		metaData.setTitle("Total Site Access Geo");
		List<SiteAccessLatLng> latLngData = new ArrayList<SiteAccessLatLng>();
		Map<Integer, Integer> map =  locationSummaryDao.getSummaryComplete(""+Cache.getAppData().getAppId());
		for( Entry<Integer, Integer> entry : map.entrySet()){
			SiteAccessLatLng latLng = new SiteAccessLatLng();
			CityInfo info = cityDao.getCityInfoById(entry.getKey());
			if( info == null ) continue;
			latLng.setCity(info.getCityName());
			latLng.setLat(info.getLat());
			latLng.setLng(info.getLng());
			latLng.setNoOfHits(entry.getValue());
			latLngData.add(latLng);
		}
		metaData.setLatLngData(latLngData);
		
		return new JsonResult(metaData);
	}
}
