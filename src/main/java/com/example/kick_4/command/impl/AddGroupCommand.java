package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.GroupServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class AddGroupCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String groupName = request.getParameter("groupName");
    Group group = new Group();
    group.setName(groupName);

    try {
      GroupServiceImpl.getInstance().insert(group);
      request.setAttribute("successMsg", "Group '" + groupName + "' added successfully");
      return "redirect:controller?command=SHOW_ALL_GROUPS";
    } catch (ServiceException e) {
      throw new CommandException("Failed to add group", e);
    }
  }
}