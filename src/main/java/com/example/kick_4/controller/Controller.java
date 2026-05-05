package com.example.kick_4.controller;

import java.io.*;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.CommandType;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
  private static Logger logger = LogManager.getLogger(Controller.class);

  @Override
  public void init() {
    ConnectionPool.getInstance();
    logger.info("+++ Servlet initialized : {}", this.getServletInfo());
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");

    String commandStr = request.getParameter("command");
    Command command = CommandType.defineCommand(commandStr);
    String page = null;
    try {
      page = command.execute(request);
      request.getRequestDispatcher(page).forward(request, response);
      //response.sendRedirect(request.getContextPath() + "/" + page);
    } catch (CommandException e) {
      response.sendError(500);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

  }

  @Override
  public void destroy() {
    ConnectionPool.getInstance().destroyPool();
    logger.info("--- Servlet destroyed : {}", this.getServletInfo());
  }
}