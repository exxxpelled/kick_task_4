package com.example.kick_4.entity;

public class User extends AbstractEntity {
  public static final int MIN_PASSWORD_LENGTH = 4;

  private String login;
  private String password;
  private Role role = Role.USER;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isAdmin() {
    return Role.ADMIN == role;
  }
}