package com.example.kick_4.entity;

public class Student extends AbstractEntity {
  private String name;
  private String surname;
  private int groupNumber;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public int getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(int groupId) {
    this.groupNumber = groupId;
  }
}
