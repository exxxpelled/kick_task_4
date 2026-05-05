package com.example.kick_4.controller.listener;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionCreateListenerImpl implements HttpSessionListener {
  private static Logger logger = LogManager.getLogger(SessionCreateListenerImpl.class);

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    logger.info("Session created : {}", se.getSession().getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    logger.info("Session destroyed : {}", se.getSession().getId());
  }

}