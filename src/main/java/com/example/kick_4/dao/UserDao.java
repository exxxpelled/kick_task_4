package com.example.kick_4.dao;

import com.example.kick_4.exception.DaoException;

public interface UserDao {
  boolean authenticate(String login, String password) throws DaoException;
}
