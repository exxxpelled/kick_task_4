package com.example.kick_4.dao;

import com.example.kick_4.exception.DaoException;

public interface UserDao {
  String findPasswordHashByLogin(String login) throws DaoException;
  boolean authenticate(String login, String password) throws DaoException;
}
