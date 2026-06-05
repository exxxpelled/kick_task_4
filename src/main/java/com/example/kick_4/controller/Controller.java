package com.example.kick_4.controller;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.CommandType;
import com.example.kick_4.command.Router;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {

  private static final Logger logger = LogManager.getLogger(Controller.class);

  private static final String PAGE_ERROR_400 = "/pages/error/400.jsp";
  private static final String PAGE_ERROR_500 = "/pages/error/500.jsp";

  private static final String ATTR_ERROR_MSG = "errorMsg";

  @Override
  public void init() {
    ConnectionPool.getInstance();
    logger.info("+++ Servlet initialized : {}", this.getServletInfo());
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    response.setContentType("text/html;charset=UTF-8");

    String commandStr = request.getParameter("command");

    if (commandStr != null && commandStr.isBlank()) {
      response.sendError(400, "Missing command parameter");
      return;
    }

    Command command = CommandType.defineCommand(commandStr);

    try {
      Router router = command.execute(request);

      if (router == null) {
        logger.error("Command '{}' returned null router", commandStr);
        request.setAttribute(ATTR_ERROR_MSG, "Command returned no result");
        request.getRequestDispatcher(PAGE_ERROR_500).forward(request, response);
        return;
      }

      if (router.getType() == Router.Type.REDIRECT) {
        response.sendRedirect(request.getContextPath() + "/" + router.getPage());
      } else {
        request.getRequestDispatcher(router.getPage()).forward(request, response);
      }

    } catch (CommandException e) {
      logger.error("Command '{}' execution failed: {}", commandStr, e.getMessage(), e);
      request.setAttribute(ATTR_ERROR_MSG, e.getMessage());
      request.getRequestDispatcher(PAGE_ERROR_500).forward(request, response);
    }
  }

  @Override
  public void destroy() {
    ConnectionPool.getInstance().destroyPool();
    logger.info("--- Servlet destroyed : {}", this.getServletInfo());
  }
}