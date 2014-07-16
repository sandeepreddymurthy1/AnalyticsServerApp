package com.webanalytics.web.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.webanalytics.web.dao.util.DataBinding;
import com.webanalytics.web.dao.util.Translator;

public class CityInfo {

	private Integer cityId;
	private String cityName;
	private Double lat;
	private Double lng;
	public Integer getCityId() {
		return cityId;
	}
	@DataBinding( columnName="city", type = Translator.INTEGER)
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	@DataBinding( columnName="name", type = Translator.STRING)
	public void setCityName(String cityName) {
		try {
			this.cityName = URLDecoder.decode(cityName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Double getLat() {
		return lat;
	}
	@DataBinding( columnName="lat", type = Translator.DOUBLE)
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	@DataBinding( columnName="lng", type = Translator.DOUBLE)
	public void setLng(Double lng) {
		this.lng = lng;
	}
}
