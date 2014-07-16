package com.webanalytics.web.dto.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.webanalytics.web.dto.ReportIdentifier;

public class AvailableExistingReport {

	private List<ReportIdentifier> availableReports = new ArrayList<ReportIdentifier>();
	private List<ReportIdentifier> existingReports = new ArrayList<ReportIdentifier>();
	public List<ReportIdentifier> getAvailableReports() {
		return availableReports;
	}
	public void setAvailableReports(List<ReportIdentifier> availableReports) {
		this.availableReports = availableReports;
	}
	public List<ReportIdentifier> getExistingReports() {
		return existingReports;
	}
	public void setExistingReports(List<ReportIdentifier> existingReports) {
		this.existingReports = existingReports;
	}
}
