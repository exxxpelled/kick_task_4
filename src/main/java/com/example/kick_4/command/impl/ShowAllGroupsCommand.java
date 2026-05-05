package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.GroupDaoImpl;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllGroupsCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    try {
      List<Group> groups = GroupDaoImpl.getInstance().findAll();
      request.setAttribute("groups", groups);
      return "pages/groups.jsp";
    } catch (DaoException e) {
      throw new CommandException("Failed to retrieve groups", e);
    }
  }
}