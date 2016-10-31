package com.mooneyserver.iocomp;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.DbUtils;

public class DBQuery {

  private static final HikariDataSource ds
    = new HikariDataSource(new HikariConfig("/db/connection-pool-config.properties"));
  private static final ResultSetHandler<List<Pub>> hdlr
    = new BeanListHandler<Pub>(Pub.class);

  private static final String GET_ALL_PUBS = "SELECT * FROM pub";
  private static final String GET_PUB = "SELECT * FROM pub WHERE id = ?";

  @SneakyThrows
  public List<Pub> getAllPubs() {
    Connection connection = ds.getConnection();
    try {
      QueryRunner run = new QueryRunner();
      return run.query(connection, GET_ALL_PUBS, hdlr);
    } finally {
      DbUtils.close(connection);  
    }
  }
  
  @SneakyThrows
  public List<Pub> getPubById(String id) {
    Connection connection = ds.getConnection();
    try {
      QueryRunner run = new QueryRunner();
      return run.query(connection, GET_PUB, hdlr, id);
    } finally {
      DbUtils.close(connection);  
    }
  }
}
