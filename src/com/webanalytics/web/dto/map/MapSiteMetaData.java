package com.webanalytics.web.dto.map;

import java.util.List;

public class MapSiteMetaData {

	private String title;
	private List<SiteAccessLatLng> latLngData;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<SiteAccessLatLng> getLatLngData() {
		return latLngData;
	}
	public void setLatLngData(List<SiteAccessLatLng> latLngData) {
		this.latLngData = latLngData;
	}
}
