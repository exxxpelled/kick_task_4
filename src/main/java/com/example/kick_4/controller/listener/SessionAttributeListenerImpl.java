package com.example.kick_4.controller.listener;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {
  private static Logger logger = LogManager.getLogger(SessionAttributeListenerImpl.class);

  @Override
  public void attributeAdded(HttpSessionBindingEvent sbe) {
    logger.info("+++Attributes added : {}", sbe.getSession().getAttributeNames());
  }

  @Override
  public void attributeRemoved(HttpSessionBindingEvent sbe) {
    logger.info("---Attributes removed : {}", sbe.getSession().getAttributeNames());
  }

  @Override
  public void attributeReplaced(HttpSessionBindingEvent sbe) {
    logger.info("###Attributes replaced : {}", sbe.getSession().getAttributeNames());
  }
}