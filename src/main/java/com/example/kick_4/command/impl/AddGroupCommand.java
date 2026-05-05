package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.GroupDaoImpl;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class AddGroupCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String groupName = request.getParameter("groupName");
    if (groupName == null || groupName.isBlank()) {
      request.setAttribute("errorMsg", "Group name cannot be empty");
      return "pages/addGroup.jsp";
    }

    Group group = new Group();
    group.setName(groupName);

    try {
      GroupDaoImpl.getInstance().insert(group);
      request.setAttribute("successMsg", "Group '" + groupName + "' added successfully");
      return new ShowAllGroupsCommand().execute(request);
    } catch (DaoException e) {
      throw new CommandException("Failed to add group", e);
    }
  }
}