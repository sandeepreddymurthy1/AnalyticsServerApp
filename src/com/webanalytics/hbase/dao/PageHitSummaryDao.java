package com.webanalytics.hbase.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.webanalytics.hbase.dto.AnalyticTableConstant;


public class PageHitSummaryDao {


	private static final Logger log = Logger.getLogger(PageHitSummaryDao.class);


	public PageHitSummaryDao() {
	
	}
	
	private static Scan mkScan() {
		Scan s = new Scan();
		s.addFamily(AnalyticTableConstant.PAGEHIT_COLUMN_FAMILY);
		return s;
	}



	public Map<String, Integer> getPageHitSummary(String appId)
			 {
		 HTablePool pool = null;
		 HTableInterface table = null;
		 try{
			 pool=	 new HTablePool();
			 table = pool.getTable(AnalyticTableConstant.DAILY_TABLE_NAME);

			ResultScanner resultScanner = table.getScanner(mkScan());
			Map<String, Integer> results = new HashMap<String, Integer>();
			while(true){
				Result result = resultScanner.next();
				NavigableMap<byte[], byte[]> nMap = result.getFamilyMap(AnalyticTableConstant.PAGEHIT_COLUMN_FAMILY);
				for( Entry<byte[], byte[]> entry : nMap.entrySet()){
					results.put(Bytes.toString(entry.getKey()),Bytes.toInt(entry.getValue()));
				}
				break;
			}
			
			return results;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
		 finally{
			 	try {
					table.close();
					pool.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		
	}

}
