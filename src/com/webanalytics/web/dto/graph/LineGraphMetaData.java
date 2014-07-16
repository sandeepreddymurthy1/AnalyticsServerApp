package com.webanalytics.web.dto.graph;

import java.util.List;
import java.util.Map;

public class LineGraphMetaData {

	private String xAxisLabel;
	private String yAxisLabel;
	private String title;
	private List<String> fields;
	private String xField;
	private List<String> yFields;
	
	private List<Map<String, Object>> data;
	

	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public List<String> getyFields() {
		return yFields;
	}
	public void setyFields(List<String> yFields) {
		this.yFields = yFields;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	public String getxAxisLabel() {
		return xAxisLabel;
	}
	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}
	public String getyAxisLabel() {
		return yAxisLabel;
	}
	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
