package com.example.kick_4.command;

public class Router {
  private String page;
  private Type type = Type.FORWARD;

  public enum Type {
    FORWARD,
    REDIRECT
  }

  public Router() {
  }

  public Router(String page) {
    this.page = page;
  }

  public Router(String page, Type type) {
    this.page = page;
    this.type = type;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public Type getType() {
    return type;
  }

  public void setRedirect() {
    this.type = Type.REDIRECT;
  }

  public void setForward() {
    this.type = Type.FORWARD;
  }

  public void setType(Type type) {
    this.type = type;
  }
}