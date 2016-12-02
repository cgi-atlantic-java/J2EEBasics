package me.bantling.j2ee.basics.bean;

import java.util.Objects;

/**
 * A simple street address
 */
public class Address {
  
  // ==== Instance fields
  
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

  public final int getId(
  ) {
    return id;
  }

  public final String getLine1(
  ) {
    return line1;
  }

  public final String getLine2(
  ) {
    return line2;
  }

  public final String getCity(
  ) {
    return city;
  }

  public final Country getCountry(
  ) {
    return country;
  }

  public final Region getRegion(
  ) {
    return region;
  }

  public final String getPostalCode(
  ) {
    return postalCode;
  }
  
  // ==== Object
  
  @Override
  public int hashCode(
  ) {
    return
      ((((((
      id ) * 31 +
      Objects.hashCode(line1) ) * 31 +
      Objects.hashCode(line2) ) * 31 +
      Objects.hashCode(city) ) * 31 +
      Objects.hashCode(country) ) * 31 +
      Objects.hashCode(region) ) * 31 +
      Objects.hashCode(postalCode);
  }
  
  @Override
  public boolean equals(
    final Object o
  ) {
    boolean equals = o == this;
    
    if ((! equals) && (o instanceof Address)) {
      final Address obj = (Address)(o);
      
      equals =
        (id == obj.id) &&
        Objects.equals(line1, obj.line1) &&
        Objects.equals(line2, obj.line2) &&
        Objects.equals(city, obj.city) &&
        Objects.equals(country, obj.country) &&
        Objects.equals(region, obj.region) &&
        Objects.equals(postalCode, obj.postalCode);
    }
    
    return equals;
  }
  
  @Override
  public String toString(
  ) {
    return new StringBuilder().
      append(Address.class.getSimpleName()).
      append("[id=").append(id).
      append(", line1=").append(line1).
      append(", line2=").append(line2).
      append(", city=").append(city).
      append(", country=").append(country).
      append(", region=").append(region).
      append(", postalCode=").append(postalCode).
      append(']').
      toString();
  }
}
