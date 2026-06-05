package com.example.kick_4.service;

import com.example.kick_4.entity.User;
import com.example.kick_4.exception.ServiceException;

import java.util.Optional;

public interface UserService {
  boolean authenticate(String login, String password) throws ServiceException;
  Optional<User> findByLogin(String login) throws ServiceException;
  boolean register(User user) throws ServiceException;
}