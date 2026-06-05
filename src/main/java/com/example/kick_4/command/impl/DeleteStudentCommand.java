package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteStudentCommand implements Command {

  private static final String PARAM_STUDENT_ID = "studentId";

  private static final String ATTR_SUCCESS_MSG = "successMsg";
  private static final String ATTR_ERROR_MSG = "errorMsg";

  private static final String PAGE_STUDENTS = "pages/student/students.jsp";

  private static final String MSG_DELETED = "Student deleted successfully";
  private static final String MSG_NOT_FOUND = "Student not found or already deleted";
  private static final String MSG_INVALID_ID = "Invalid student ID format";
  private static final String ERR_DELETE = "Failed to delete student";

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter(PARAM_STUDENT_ID);

    long id;
    try {
      id = Long.parseLong(idStr);
    } catch (NumberFormatException e) {
      request.setAttribute(ATTR_ERROR_MSG, MSG_INVALID_ID);
      return new Router(PAGE_STUDENTS);
    }

    Student student = new Student();
    student.setId(id);

    try {
      boolean deleted = StudentServiceImpl.getInstance().delete(student);
      if (deleted) {
        request.setAttribute(ATTR_SUCCESS_MSG, MSG_DELETED);
      } else {
        request.setAttribute(ATTR_ERROR_MSG, MSG_NOT_FOUND);
      }
      return new ShowAllStudentsCommand().execute(request);
    } catch (ServiceException e) {
      throw new CommandException(ERR_DELETE, e);
    }
  }
}