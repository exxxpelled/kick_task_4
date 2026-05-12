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
  private static final Logger logger = LogManager.getLogger(Controller.class);

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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    processRequest(req, resp);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    response.setContentType("text/html;charset=UTF-8");

    String commandStr = request.getParameter("command");
    Command command = CommandType.defineCommand(commandStr);

    if (command == null) {
      response.sendError(400, "Unknown command");
      return;
    }

    try {
      String result = command.execute(request);

      if (result != null && result.startsWith("redirect:")) {
        response.sendRedirect(result.substring("redirect:".length()));
      } else if (result != null) {
        request.getRequestDispatcher(result).forward(request, response);
      } else {
        response.sendError(500, "Command returned null view");
      }
    } catch (CommandException e) {
      logger.error("Command execution failed", e);
      request.setAttribute("errorMsg", e.getMessage());
      request.getRequestDispatcher("pages/error.jsp").forward(request, response);
    }
  }

  @Override
  public void destroy() {
    ConnectionPool.getInstance().destroyPool();
    logger.info("--- Servlet destroyed : {}", this.getServletInfo());
  }
}