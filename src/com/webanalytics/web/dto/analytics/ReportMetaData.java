package com.webanalytics.web.dto.analytics;

import java.util.ArrayList;
import java.util.List;

import com.webanalytics.web.dto.ReportIdentifier;

public class ReportMetaData extends ReportIdentifier{

	public static final String LINE_GRAPH_REPORT="lineGraphReport";
	public static final String LIST_REPORT = "listReport";
	public static final String MAP_REPORT = "mapReport";
	public static final String BAR_GRAPH_REPORT = "barGraphReport";
	public static final String PIE_GRAPH_REPORT = "pieGraphReport";
	
	
	private String title;
	private String reportType;
	private List<String> subreports = new ArrayList<String>();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public List<String> getSubreports() {
		return subreports;
	}
	public void setSubreports(List<String> subreports) {
		this.subreports = subreports;
	}
	
}
