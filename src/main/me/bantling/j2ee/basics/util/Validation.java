package me.bantling.j2ee.basics.util;

public class Validation {
  private final String name;
  private final String message;
  
  public Validation(
    final String name,
    final String message
  ) {
    this.name = name;
    this.message = message;
  }
  
  public String getName(
  ) {
    return name;
  }
  
  public String getMessage(
  ) {
    return message;
  }
}
