package com.example.kick_4.service;

import com.example.kick_4.exception.ServiceException;

public interface UserService {
  boolean authenticate(String login, String password) throws ServiceException;

}
