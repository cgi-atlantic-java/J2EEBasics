package me.bantling.j2ee.basics.util;

import me.bantling.j2ee.basics.bean.Address;
import me.bantling.j2ee.basics.bean.Country;

public class Validators {
  
  // ==== Static fields
  
  public static Validator<Address> DEFAULT_ADDRESS_VALIDATOR =
    new ValidationBuilder<Address>().
      nonNull("line1", t -> t.getLine1()).
      nullOrNonEmptyString("line2", t -> t.getLine2()).
      nonNull("city", t -> t.getCity()).
      nonNull("country", t -> t.getCountry()).
      nonNull("region", t -> t.getRegion()).
      validCDNPostalCode(t -> t.getCountry() == Country.CAN, "postalCode", t -> t.getPostalCode()).
      validUSAZipCode(t -> t.getCountry() == Country.USA, "postalCode", t -> t.getPostalCode()).
    end();
}
