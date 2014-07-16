package com.webanalytics.web.dto.dashboard;

import com.webanalytics.web.dto.ReportIdentifier;

public class ReportPanelInfo extends ReportIdentifier {

	private double startXAxis;
	private double startYAxis;
	private double width;
	private double height;
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getStartXAxis() {
		return startXAxis;
	}
	public void setStartXAxis(double startXAxis) {
		this.startXAxis = startXAxis;
	}
	public double getStartYAxis() {
		return startYAxis;
	}
	public void setStartYAxis(double startYAxis) {
		this.startYAxis = startYAxis;
	}
	
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	
}
