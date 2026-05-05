package com.example.kick_4.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
  private static ConnectionPool instance;
  private static final int CONNECTIONS_AMOUNT = 10;
  private BlockingDeque<Connection> freeConnections = new LinkedBlockingDeque<>(CONNECTIONS_AMOUNT);
  private BlockingDeque<Connection> usedConnections = new LinkedBlockingDeque<>(CONNECTIONS_AMOUNT);

  static {
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
    } catch (SQLException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  private ConnectionPool() {
    String url = "jdbc:postgresql://localhost:5432/kick_4";
    Properties prop = new Properties();
    prop.put("user", "postgres");
    prop.put("password", "12345");
    for (int i = 0; i < CONNECTIONS_AMOUNT; i++) {
      try {
        Connection connection = DriverManager.getConnection(url, prop);
        freeConnections.add(connection);
      } catch (SQLException e) {
        throw new ExceptionInInitializerError(e);
      }
    }
  }

  public static ConnectionPool getInstance() {
    //todo multithreaded
    instance = new ConnectionPool();
    return instance;
  }

  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = freeConnections.take();
      usedConnections.put(connection);
    } catch (InterruptedException e) {
      //todo log
      Thread.currentThread().interrupt();
    }
    return connection;
  }

  public void releaseConnection(Connection connection) {
    try {
      usedConnections.remove(connection);
      freeConnections.put(connection);
    } catch (InterruptedException e) {
      //todo log
      Thread.currentThread().interrupt();
    }
  }

  public void destroyPool() {
    for (int i = 0; i < CONNECTIONS_AMOUNT; i++) {
      try {
        freeConnections.take().close();
      } catch (SQLException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void deregisterDriver() {
    //todo
  }
}
