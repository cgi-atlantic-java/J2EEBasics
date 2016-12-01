package me.bantling.j2ee.basics.bean;

public enum Country {
  CAN("Canada", "Province", "Postal Code"),
  USA("United States", "State", "Zip Code");
  
  private final String toString;
  private final String regionLabel;
  private final String postalCodeLabel;
  
  private Country(
    final String toString,
    final String regionLabel,
    final String postalCodeLabel
  ) {
    this.toString = toString;
    this.regionLabel = regionLabel;
    this.postalCodeLabel = postalCodeLabel;
  }
  
  @Override
  public String toString(
  ) {
    return toString;
  }
  
  public String regionLabel(
  ) {
    return regionLabel;
  }
  
  public String postalCodeLabel(
  ) {
    return postalCodeLabel;
  }
}
