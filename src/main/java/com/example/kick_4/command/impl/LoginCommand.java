package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.UserService;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {

  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";

  private static final String ATTR_LOGIN_MSG = "login_msg";
  private static final String SESSION_USER = "user";
  private static final String SESSION_USER_NAME = "user_name";
  private static final String SESSION_ROLE = "user_role";
  private static final String SESSION_CURRENT_PAGE = "current_page";

  private static final String PAGE_MAIN = "pages/main.jsp";
  private static final String PAGE_LOGIN = "pages/auth/login.jsp";

  private static final String MSG_WRONG_CREDENTIALS = "Incorrect login or password";

  private final UserService userService = UserServiceImpl.getInstance();

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String login = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);

    try {
      Optional<User> optUser = userService.findByLogin(login);

      if (optUser.isPresent() && optUser.get().getPassword().equals(password)) {
        User user = optUser.get();
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_USER, user);
        session.setAttribute(SESSION_USER_NAME, user.getLogin());
        session.setAttribute(SESSION_ROLE, user.getRole().name());

        Router router = new Router(PAGE_MAIN);
        session.setAttribute(SESSION_CURRENT_PAGE, router.getPage());
        return router;
      } else {
        request.setAttribute(ATTR_LOGIN_MSG, MSG_WRONG_CREDENTIALS);
        return new Router(PAGE_LOGIN);
      }
    } catch (ServiceException e) {
      throw new CommandException("Authentication error", e);
    }
  }
}