package com.webanalytics.hbase.dao;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;

public class BaseDao {

	private static final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM                     = "hbase.zookeeper.quorum";
	private static final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT                 = "hbase.zookeeper.property.clientPort";

	private static String hbaseZookeeperQuorum="ec2-54-200-7-213.us-west-2.compute.amazonaws.com";
	private static Integer hbaseZookeeperClientPort=2181;
	
	protected HTableInterface htable;
	private HTablePool pool;
	private HTablePool getPool(){
		try {
			Configuration conf = HBaseConfiguration.create();
			conf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeperQuorum);
			conf.set(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, ""+hbaseZookeeperClientPort);

			pool = new HTablePool(conf,10);
			return pool;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	protected HTableInterface getTable(byte[] table){
		getPool();
		htable = pool.getTable(table);
		return htable;
	}
	
	protected void close(){
		try {
			htable.close();
			pool.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
