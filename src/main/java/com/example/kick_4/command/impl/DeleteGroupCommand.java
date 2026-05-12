package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.GroupServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteGroupCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("groupId");

    try {
      Long id = Long.parseLong(idStr);
      Group group = new Group();
      group.setId(id);
      boolean deleted = GroupServiceImpl.getInstance().delete(group);
      if (deleted) {
        request.setAttribute("successMsg", "Group deleted successfully");
      } else {
        request.setAttribute("errorMsg", "Group not found or already deleted");
      }
      return new ShowAllGroupsCommand().execute(request);
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid group ID format");
      return "pages/group/groups.jsp";
    } catch (ServiceException | CommandException e) {
      throw new CommandException("Failed to delete group", e);
    }
  }
}