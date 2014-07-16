package com.webanalytics.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.webanalytics.web.dto.enums.ReportTypeEnum;

public class EnumDao extends BaseDao{

	public List<ReportTypeEnum> getAllReportTypes(){
		Map<String, Object> params = new HashMap<String, Object>();
		String query = "select * from ReportEnum ";
		List<ReportTypeEnum> results = executeNamedPreparedStatement(query, params,ReportTypeEnum.class);
		return results;
	}
}
