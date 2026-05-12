package com.example.kick_4.service.impl;

import com.example.kick_4.dao.impl.UserDaoImpl;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.CommonService;
import com.example.kick_4.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements CommonService<User>, UserService {
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
  private static final UserServiceImpl instance = new UserServiceImpl();
  private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

  private UserServiceImpl() {}

  public static UserServiceImpl getInstance() {
    return instance;
  }

  @Override
  public boolean insert(User user) throws ServiceException {
    if (user == null) throw new ServiceException("User object cannot be null");
    if (user.getLogin() == null || user.getLogin().isBlank()) {
      throw new ServiceException("Login cannot be empty");
    }
    if (user.getPassword() == null || user.getPassword().length() < User.MIN_PASSWORD_LENGTH) {
      throw new ServiceException("Password must be at least 6 characters");
    }

    try {
      return userDao.insert(user);
    } catch (DaoException e) {
      logger.error("Failed to insert user: {}", user.getLogin(), e);
      throw new ServiceException("Cannot create user", e);
    }
  }

  @Override
  public boolean delete(User user) throws ServiceException {
    if (user == null || user.getId() == null || user.getId() <= 0) {
      throw new ServiceException("Invalid user ID for deletion");
    }
    try {
      return userDao.delete(user);
    } catch (DaoException e) {
      logger.error("Failed to delete user with id: {}", user.getId(), e);
      throw new ServiceException("Cannot delete user", e);
    }
  }

  @Override
  public List<User> findAll() throws ServiceException {
    try {
      return userDao.findAll();
    } catch (DaoException e) {
      logger.error("Failed to fetch users", e);
      throw new ServiceException("Cannot fetch users", e);
    }
  }

  @Override
  public User update(User user) throws ServiceException {
    if (user == null || user.getId() == null || user.getId() <= 0) {
      throw new ServiceException("Invalid user ID for update");
    }
    if (user.getLogin() == null || user.getLogin().isBlank()) {
      throw new ServiceException("Login cannot be empty");
    }
    if (user.getPassword() != null && !user.getPassword().isBlank() && user.getPassword().length() < User.MIN_PASSWORD_LENGTH) {
      throw new ServiceException("Password must be at least 6 characters");
    }

    try {
      return userDao.update(user);
    } catch (DaoException e) {
      logger.error("Failed to update user with id: {}", user.getId(), e);
      throw new ServiceException("Cannot update user", e);
    }
  }

  @Override
  public boolean authenticate(String login, String password) throws ServiceException {
    try {
      boolean match = userDao.authenticate(login, password);
      logger.debug("Authentication {} for user: {}", match ? "successful" : "failed", login);
      return match;
    } catch (DaoException e) {
      logger.error("Database error during authentication for user: {}", login, e);
      throw new ServiceException("Authentication error", e);
    }
  }
}