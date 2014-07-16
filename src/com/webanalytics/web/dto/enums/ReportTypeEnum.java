package com.webanalytics.web.dto.enums;

import com.webanalytics.web.dao.util.DataBinding;
import com.webanalytics.web.dao.util.Translator;

public class ReportTypeEnum {

	private Integer id;
	private String configEnumType;

	private String reportType;
	private String description;
	public Integer getId() {
		return id;
	}
	@DataBinding(columnName="id", type=Translator.INTEGER)
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReportType() {
		return reportType;
	}
	@DataBinding(columnName="reportType", type=Translator.STRING)
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getDescription() {
		return description;
	}
	@DataBinding(columnName="reportDescription", type=Translator.STRING)
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getConfigEnumType() {
		return configEnumType;
	}
	
	@DataBinding(columnName="configEnumType", type=Translator.STRING)
	public void setConfigEnumType(String configEnumType) {
		this.configEnumType = configEnumType;
	}
}
