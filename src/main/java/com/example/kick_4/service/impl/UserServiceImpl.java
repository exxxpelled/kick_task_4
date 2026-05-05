package com.example.kick_4.service.impl;

import com.example.kick_4.dao.impl.UserDaoImpl;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.UserService;

public class UserServiceImpl implements UserService {
  private UserServiceImpl() {
  }
  
  private static UserServiceImpl instance = new UserServiceImpl();
  
  public static UserServiceImpl getInstance() {
    return instance;
  }
  
  @Override
  public boolean authenticate(String login, String password) throws ServiceException {
    //todo validate login, password + md5
    UserDaoImpl userDao = UserDaoImpl.getInstance();
    boolean match = false;
    try {
      match = userDao.authenticate(login, password);
    } catch (DaoException e) {
      throw new ServiceException(e);
    }
    return match;
  }
}
