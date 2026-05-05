package com.example.kick_4.controller.listener;

import com.example.kick_4.pool.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
  private static Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ConnectionPool.getInstance();
    logger.info("+++ Context initialized : {}", sce.getServletContext().getServerInfo());
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    ConnectionPool.getInstance().destroyPool();
    logger.info("--- Context destroyed : {}", sce.getServletContext().getServerInfo());
  }
}