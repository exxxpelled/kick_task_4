package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeStudentCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("studentId");
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String groupIdStr = request.getParameter("groupId");

    try {
      Long id = Long.parseLong(idStr);
      int groupId = Integer.parseInt(groupIdStr);
      Student student = new Student();
      student.setId(id);
      student.setName(name);
      student.setSurname(surname);
      student.setGroupId(groupId);
      StudentServiceImpl.getInstance().update(student);
      request.setAttribute("successMsg", "Student updated successfully");
      return "redirect:controller?command=SHOW_ALL_STUDENTS";
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid number format for ID or group");
      return "pages/student/editStudent.jsp";
    } catch (ServiceException e) {
      throw new CommandException("Failed to update student", e);
    }
  }
}