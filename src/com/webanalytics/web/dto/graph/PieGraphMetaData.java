package com.webanalytics.web.dto.graph;

import java.util.Map;

public class PieGraphMetaData {

	private String title;
	private Map<String, Double> data;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Map<String, Double> getData() {
		return data;
	}
	public void setData(Map<String, Double> data) {
		this.data = data;
	}
}
