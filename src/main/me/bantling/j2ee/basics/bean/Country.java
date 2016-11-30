package me.bantling.j2ee.basics.bean;

public enum Country {
  CAN("Canada"),
  USA("United States");
  
  private final String toString;
  
  private Country(
    final String toString
  ) {
    this.toString = toString;
  }
  
  @Override
  public String toString(
  ) {
    return toString;
  }
}
