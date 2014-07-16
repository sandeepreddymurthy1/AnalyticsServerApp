package com.webanalytics.hbase.util;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.log4j.Logger;

import com.webanalytics.hbase.dao.UsersDAO;
import com.webanalytics.hbase.dto.User;


public class UsersTool {

  private static final Logger log = Logger.getLogger(UsersTool.class);

 
  public static void main(String[] args) throws IOException {
 

    HTablePool pool = new HTablePool();
    UsersDAO dao = new UsersDAO(pool);

//    if ("get".equals(args[0])) {
//      log.debug(String.format("Getting user %s", args[1]));
//      User u = dao.getUser(args[1]);
//      System.out.println(u);
//    }

//    if ("add".equals(args[0])) {
//      log.debug("Adding user...");
//      dao.addUser(args[1], args[2], args[3], args[4]);
//      User u = dao.getUser(args[1]);
//      System.out.println("Successfully added user " + u);
//    }
//
//    if ("list".equals(args[0])) {
      List<User> users = dao.getUsers();
      log.info(String.format("Found %s users.", users.size()));
      for(User u : users) {
        System.out.println(u);
      }
   // }

    pool.closeTablePool(UsersDAO.TABLE_NAME);
  }
}
