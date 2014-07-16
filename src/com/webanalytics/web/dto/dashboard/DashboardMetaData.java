package com.webanalytics.web.dto.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardMetaData {

	private Date lastUpdated;
	private List<ReportPanelInfo> reportPanelsInfo = new ArrayList<ReportPanelInfo>();
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public List<ReportPanelInfo> getReportPanelsInfo() {
		return reportPanelsInfo;
	}
	public void setReportPanelsInfo(List<ReportPanelInfo> reportPanelsInfo) {
		this.reportPanelsInfo = reportPanelsInfo;
	}
	
}
