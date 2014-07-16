package com.webanalytics.hbase.dto;

public abstract class User {

  public String user;
  public String name;
  public String email;
  public String password;
  public long tweetCount;

  @Override
  public String toString() {
    return String.format(
                         "<User: %s, %s, %s, %s>",
                         user, name, email, tweetCount);
  }
}
