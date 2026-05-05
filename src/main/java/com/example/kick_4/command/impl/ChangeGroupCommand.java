package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.GroupDaoImpl;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeGroupCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("groupId");
    String newName = request.getParameter("groupName");

    if (idStr == null || newName == null || newName.isBlank()) {
      request.setAttribute("errorMsg", "Group ID and new name are required");
      return "pages/editGroup.jsp";
    }

    try {
      Long id = Long.parseLong(idStr);
      Group group = new Group();
      group.setId(id);
      group.setName(newName);
      GroupDaoImpl.getInstance().update(group);
      request.setAttribute("successMsg", "Group updated successfully");
      return new ShowAllGroupsCommand().execute(request);
    } catch (NumberFormatException | DaoException e) {
      throw new CommandException("Failed to update group", e);
    }
  }
}