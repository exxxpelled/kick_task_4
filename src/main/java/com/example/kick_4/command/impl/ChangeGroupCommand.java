package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.GroupServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeGroupCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("groupId");
    String newName = request.getParameter("groupName");

    try {
      Long id = Long.parseLong(idStr);
      Group group = new Group();
      group.setId(id);
      group.setName(newName);
      GroupServiceImpl.getInstance().update(group);
      request.setAttribute("successMsg", "Group updated successfully");
      return "redirect:controller?command=SHOW_ALL_GROUPS";
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid group ID format");
      return "pages/group/editGroup.jsp";
    } catch (ServiceException e) {
      throw new CommandException("Failed to update group", e);
    }
  }
}