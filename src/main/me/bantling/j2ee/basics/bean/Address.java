package me.bantling.j2ee.basics.bean;

import java.util.Objects;

import me.bantling.j2ee.basics.util.ValidationBuilder;
import me.bantling.j2ee.basics.util.Validator;

public class Address {
  
  // ==== Static methods
  
  public static Validator<Address> DEFAULT_VALIDATOR = new ValidationBuilder<Address>().
    nonNull("Line 1", t -> t.getLine1()).
    nullOrNonEmptyString("Line 2", t -> t.getLine2()).
    nonNull("City", t -> t.getCity()).
    nonNull("Country", t -> t.getCountry()).
    nonNull("Region", t -> t.getRegion()).
    validCDNPostalCode(t -> t.getCountry() == Country.CAN, "Postal Code", t -> t.getPostalCode()).
    validUSAZipCode(t -> t.getCountry() == Country.USA, "Zip Code", t -> t.getPostalCode()).
  end();
  
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
      append(", line2=").append(getLine1()).
      append(", city=").append(getClass()).
      append(", country=").append(getCountry()).
      append(", region=").append(getRegion()).
      append(", postalCode=").append(getPostalCode()).
      append(']').
      toString();
  }
}
