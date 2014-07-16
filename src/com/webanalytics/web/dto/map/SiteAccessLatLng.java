package com.webanalytics.web.dto.map;

public class SiteAccessLatLng extends LatLng{

	private String city;
	private Integer noOfHits;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getNoOfHits() {
		return noOfHits;
	}
	public void setNoOfHits(Integer noOfHits) {
		this.noOfHits = noOfHits;
	}
	
}
