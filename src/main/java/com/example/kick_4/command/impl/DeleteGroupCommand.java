package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.GroupDaoImpl;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteGroupCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("groupId");
    if (idStr == null) {
      request.setAttribute("errorMsg", "Group ID is required");
      return "pages/groups.jsp";
    }

    try {
      Long id = Long.parseLong(idStr);
      Group group = new Group();
      group.setId(id);
      boolean deleted = GroupDaoImpl.getInstance().delete(group);
      if (deleted) {
        request.setAttribute("successMsg", "Group deleted successfully");
      } else {
        request.setAttribute("errorMsg", "Group not found");
      }
      return new ShowAllGroupsCommand().execute(request);
    } catch (NumberFormatException | DaoException e) {
      throw new CommandException("Failed to delete group", e);
    }
  }
}