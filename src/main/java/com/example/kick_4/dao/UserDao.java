package com.example.kick_4.dao;

import com.example.kick_4.entity.User;
import com.example.kick_4.exception.DaoException;

import java.util.Optional;

public interface UserDao {
  Optional<User> findByLogin(String login) throws DaoException;
  boolean authenticate(String login, String password) throws DaoException;
}