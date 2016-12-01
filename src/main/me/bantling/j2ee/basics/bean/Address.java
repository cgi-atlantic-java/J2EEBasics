package me.bantling.j2ee.basics.bean;

import java.util.Objects;

public class Address {
  
  // ==== Member fields
  
  private final int id;
  private final String line1;
  private final String line2;
  private final String city;
  private final Country country;
  private final Region region;
  private final String postalCode;
  
  // ==== Cons
  
  public Address(
    final int id,
    final String line1,
    final String line2,
    final String city,
    final Country country,
    final Region region,
    final String postalCode
  ) {
    this.id = id;
    this.line1 = line1;
    this.line2 = line2;
    this.city = city;
    this.country = country;
    this.region = region;
    this.postalCode = postalCode;
  }
  
  // ==== Accessors

  public int getId(
  ) {
    return id;
  }

  public String getLine1(
  ) {
    return line1;
  }

  public String getLine2(
  ) {
    return line2;
  }

  public String getCity(
  ) {
    return city;
  }

  public Country getCountry(
  ) {
    return country;
  }

  public Region getRegion(
  ) {
    return region;
  }

  public String getPostalCode(
  ) {
    return postalCode;
  }
  
  // ==== Object
  
  @Override
  public int hashCode(
  ) {
    return
      ((((((
      getId() ) * 31 +
      Objects.hashCode(getLine1()) ) * 31 +
      Objects.hashCode(getLine2()) ) * 31 +
      Objects.hashCode(getCity()) ) * 31 +
      Objects.hashCode(getCountry()) ) * 31 +
      Objects.hashCode(getRegion()) ) * 31 +
      Objects.hashCode(getPostalCode());
  }
  
  @Override
  public boolean equals(
    final Object o
  ) {
    boolean equals = o == this;
    
    if ((! equals) && (o instanceof Address)) {
      final Address obj = (Address)(o);
      
      equals =
        (getId() == obj.getId()) &&
        Objects.equals(getLine1(), obj.getLine1()) &&
        Objects.equals(getLine2(), obj.getLine2()) &&
        Objects.equals(getClass(), obj.getCity()) &&
        Objects.equals(getCountry(), obj.getCountry()) &&
        Objects.equals(getRegion(), obj.getRegion()) &&
        Objects.equals(getPostalCode(), obj.getPostalCode());
    }
    
    return equals;
  }
  
  @Override
  public String toString(
  ) {
    return new StringBuilder().
      append(Address.class.getSimpleName()).
      append("[id=").append(getId()).
      append(", line1=").append(getLine1()).
      append(", line2=").append(getLine2()).
      append(", city=").append(getCity()).
      append(", country=").append(getCountry()).
      append(", region=").append(getRegion()).
      append(", postalCode=").append(getPostalCode()).
      append(']').
      toString();
  }
}
