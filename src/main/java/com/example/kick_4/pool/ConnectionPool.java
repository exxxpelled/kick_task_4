package com.example.kick_4.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
  private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

  private static final int CONNECTIONS_AMOUNT = 10;
  private static final String DB_URL = "jdbc:postgresql://localhost:5432/kick_4";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "12345";

  private static ConnectionPool instance;
  private static final AtomicBoolean instanceCreated = new AtomicBoolean(false);
  private static final Lock lock = new ReentrantLock();

  private final BlockingDeque<Connection> freeConnections = new LinkedBlockingDeque<>(CONNECTIONS_AMOUNT);
  private final BlockingDeque<Connection> usedConnections = new LinkedBlockingDeque<>(CONNECTIONS_AMOUNT);

  static {
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
    } catch (SQLException e) {
      logger.fatal("Failed to register PostgreSQL driver", e);
      throw new ExceptionInInitializerError(e);
    }
  }

  private ConnectionPool() {
    Properties prop = new Properties();
    prop.put("user", DB_USER);
    prop.put("password", DB_PASSWORD);
    for (int i = 0; i < CONNECTIONS_AMOUNT; i++) {
      try {
        Connection connection = DriverManager.getConnection(DB_URL, prop);
        freeConnections.add(wrapConnection(connection));
        logger.debug("Connection {} created", i + 1);
      } catch (SQLException e) {
        logger.error("Failed to create connection #{}", i + 1, e);
        throw new ExceptionInInitializerError(e);
      }
    }
    logger.info("ConnectionPool initialized with {} connections", CONNECTIONS_AMOUNT);
  }

  private Connection wrapConnection(Connection real) {
    return (Connection) Proxy.newProxyInstance(
            real.getClass().getClassLoader(),
            new Class[]{Connection.class},
            (proxy, method, args) -> {
              if ("close".equals(method.getName())) {
                releaseConnection((Connection) proxy);
                return null;
              }
              return method.invoke(real, args);
            }
    );
  }

  public static ConnectionPool getInstance() {
    if (!instanceCreated.get()) {
      lock.lock();
      try {
        if (!instanceCreated.get()) {
          instance = new ConnectionPool();
          instanceCreated.set(true);
        }
      } finally {
        lock.unlock();
      }
    }
    return instance;
  }

  public Connection getConnection() {
    Connection connection = null;
    try {
      connection = freeConnections.take();
      usedConnections.put(connection);
    } catch (InterruptedException e) {
      logger.error("Interrupted while getting connection", e);
      Thread.currentThread().interrupt();
    }
    return connection;
  }

  public void releaseConnection(Connection connection) {
    try {
      usedConnections.remove(connection);
      freeConnections.put(connection);
    } catch (InterruptedException e) {
      logger.error("Interrupted while releasing connection", e);
      Thread.currentThread().interrupt();
    }
  }

  public void destroyPool() {
    logger.info("Destroying connection pool...");
    for (Connection conn : freeConnections) {
      try {
        conn.unwrap(Connection.class).close();
      } catch (SQLException e) {
        logger.error("Error closing connection during pool destroy", e);
      }
    }
    deregisterDrivers();
    logger.info("Connection pool destroyed");
  }

  private void deregisterDrivers() {
    try {
      DriverManager.deregisterDriver(DriverManager.getDriver(DB_URL));
    } catch (SQLException e) {
      logger.error("Error deregistering driver", e);
    }
  }
}