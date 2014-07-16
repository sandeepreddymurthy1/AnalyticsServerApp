package com.webanalytics.web.dto.analytics;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportData {

	private Integer id;
	private Integer configId;
	private Integer appId;
	private String reportType;
	
	private String reportTitle;
	private boolean timeBased;
	private Date defaultStartDate;
	private Date defaultEndDate;
	private ReportMetaData metaData;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public boolean isTimeBased() {
		return timeBased;
	}
	public void setTimeBased(boolean timeBased) {
		this.timeBased = timeBased;
	}
	public Date getDefaultStartDate() {
		return defaultStartDate;
	}
	public void setDefaultStartDate(Date defaultStartDate) {
		this.defaultStartDate = defaultStartDate;
	}
	public Date getDefaultEndDate() {
		return defaultEndDate;
	}
	public void setDefaultEndDate(Date defaultEndDate) {
		this.defaultEndDate = defaultEndDate;
	}
	public ReportMetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(ReportMetaData metaData) {
		this.metaData = metaData;
	}
	
}
