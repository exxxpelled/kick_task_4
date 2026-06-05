package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class AddStudentCommand implements Command {

  private static final String PARAM_NAME = "name";
  private static final String PARAM_SURNAME = "surname";
  private static final String PARAM_GROUP_NUMBER = "groupNumber";

  private static final String ATTR_SUCCESS_MSG = "successMsg";
  private static final String ATTR_ERROR_MSG = "errorMsg";

  private static final String PAGE_ADD_STUDENT = "pages/student/addStudent.jsp";

  private static final String MSG_SUCCESS = "Student added successfully";
  private static final String MSG_FAILED = "Failed to add student";
  private static final String MSG_INVALID_GROUP = "Invalid group number format";
  private static final String ERR_ADD = "Failed to add student";

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String name = request.getParameter(PARAM_NAME);
    String surname = request.getParameter(PARAM_SURNAME);
    String groupNumberStr = request.getParameter(PARAM_GROUP_NUMBER);

    int groupNumber;
    try {
      groupNumber = Integer.parseInt(groupNumberStr);
    } catch (NumberFormatException e) {
      request.setAttribute(ATTR_ERROR_MSG, MSG_INVALID_GROUP);
      return new Router(PAGE_ADD_STUDENT);
    }

    Student student = new Student();
    student.setName(name);
    student.setSurname(surname);
    student.setGroupNumber(groupNumber);

    try {
      boolean success = StudentServiceImpl.getInstance().insert(student);
      if (success) {
        request.setAttribute(ATTR_SUCCESS_MSG, MSG_SUCCESS);
        return new ShowAllStudentsCommand().execute(request);
      } else {
        request.setAttribute(ATTR_ERROR_MSG, MSG_FAILED);
        return new Router(PAGE_ADD_STUDENT);
      }
    } catch (ServiceException e) {
      throw new CommandException(ERR_ADD, e);
    }
  }
}