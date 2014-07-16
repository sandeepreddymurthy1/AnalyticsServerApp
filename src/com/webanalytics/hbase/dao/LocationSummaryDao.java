package com.webanalytics.hbase.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.webanalytics.hbase.dto.AnalyticTableConstant;

public class LocationSummaryDao extends BaseDao {

	private static final Logger log = Logger.getLogger(LocationSummaryDao.class);
	
	private static Scan mkScan() {
		Scan s = new Scan();
		s.addFamily(AnalyticTableConstant.LOCATION_COLUMN_FAMILY);
		return s;
	}

	public Map<Integer,Integer> getSummaryForDay(String appId) {
		
		try {
			getTable(AnalyticTableConstant.DAILY_TABLE_NAME);
			ResultScanner resultScanner = htable.getScanner(mkScan());
			Map<Integer, Integer> results = new HashMap<Integer, Integer>();
			while (true) {
				Result result = resultScanner.next();
				NavigableMap<byte[], byte[]> nMap = result
						.getFamilyMap(AnalyticTableConstant.LOCATION_COLUMN_FAMILY);
				for (Entry<byte[], byte[]> entry : nMap.entrySet()) {
					results.put(Bytes.toInt(entry.getKey()),
							Bytes.toInt(entry.getValue()));
				}
				break;
			}

			return results;
		} catch (Exception e) {
			log.error(e, e);
			return null;
		} finally {
			close();
		}

	}

	public Map<Integer,Integer> getSummaryComplete(String appId) {
	
		try {
			getTable(AnalyticTableConstant.DAILY_TABLE_NAME);

			ResultScanner resultScanner = htable.getScanner(mkScan());
			Map<Integer, Integer> results = new HashMap<Integer, Integer>();
			Iterator<Result> iterator = resultScanner.iterator();
			while (iterator.hasNext()) {
				Result result = iterator.next();
				NavigableMap<byte[], byte[]> nMap = result
						.getFamilyMap(AnalyticTableConstant.LOCATION_COLUMN_FAMILY);
				for (Entry<byte[], byte[]> entry : nMap.entrySet()) {
					Integer browser = Bytes.toInt(entry.getKey());
					Integer count = results.get(browser);
					if( results.get(browser) == null ){
						results.put(Bytes.toInt(entry.getKey()),
								Bytes.toInt(entry.getValue()));
					}else{
						results.put(Bytes.toInt(entry.getKey()),
								Bytes.toInt(entry.getValue())+count);
					}
					
				}
			}

			return results;
		} catch (Exception e) {
			log.error(e, e);
			return null;
		} finally {
			close();
		}

	}
	
	

}
