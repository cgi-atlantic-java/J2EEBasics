package me.bantling.j2ee.basics.bean;

import static me.bantling.j2ee.basics.bean.Country.*;

public enum Region {
  BC(CAN, "British Columbia"),
  NS(CAN, "Nova Scotia"),
  CA(USA, "California"),
  TX(USA, "Texas");
  
  private final Country country;
  private final String toString;
  
  private Region(
    final Country country,
    final String toString
  ) {
    this.country = country;
    this.toString = toString;
  }
  
  public Country getCountry(
  ) {
    return country;
  }
  
  @Override
  public String toString(
  ) {
    return toString;
  }
}
