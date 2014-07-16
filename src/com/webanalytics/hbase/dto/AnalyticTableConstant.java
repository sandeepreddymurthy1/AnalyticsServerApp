package com.webanalytics.hbase.dto;

import org.apache.hadoop.hbase.util.Bytes;

public class AnalyticTableConstant {


	public static final byte[] DAILY_TABLE_NAME = Bytes.toBytes("DailyAnalyticTable");
	public static final byte[] WEEKLY_TABLE_NAME = Bytes.toBytes("WeeklyAnalyticTable");
	public static final byte[] MONTHLY_TABLE_NAME = Bytes.toBytes("MonthlyAnalyticTable");
	public static final byte[] QUATERLY_TABLE_NAME = Bytes.toBytes("QuaterlyAnalyticTable");
	public static final byte[] HALF_YEARLY_TABLE_NAME = Bytes.toBytes("HalfYearlyAnalyticTable");
	public static final byte[] COMPLETE_TABLE_NAME = Bytes.toBytes("CompleteAnalyticTable");
	public static final byte[] PAGEHIT_COLUMN_FAMILY = Bytes.toBytes("pageHit");
	public static final byte[] LOCATION_COLUMN_FAMILY = Bytes.toBytes("locationInfo");
	public static final byte[] BROWSER_COLUMN_FAMILY = Bytes.toBytes("browserInfo");
	public static final byte[] SOCIAL_REFERER_COLUMN_FAMILY = Bytes.toBytes("socialReferer");
	public static final byte[] UNIQUE_VISIT = Bytes.toBytes("uniqueVisit");
	public static final byte[] OS_COLUMN_FAMILY = Bytes.toBytes("osInfo");
	
	
	
}
