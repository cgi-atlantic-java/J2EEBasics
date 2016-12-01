package me.bantling.j2ee.basics.validation;

import me.bantling.j2ee.basics.bean.Address;
import me.bantling.j2ee.basics.bean.Country;

public interface Validators {
  
  // ==== Static fields
  
  public static Validator<Address> ADDRESS_VALIDATOR =
    new ValidationBuilder<Address>().
      intGreaterThan("id", t -> t.getId(), 0).
      nonEmptyString("line1", t -> t.getLine1()).
      nullOrNonEmptyString("line2", t -> t.getLine2()).
      nonEmptyString("city", t -> t.getCity()).
      nonNull("country", t -> t.getCountry()).
      nonNull("region", t -> t.getRegion()).
      validCDNPostalCode(t -> t.getCountry() == Country.CAN, "postalCode", t -> t.getPostalCode()).
      validUSAZipCode(t -> t.getCountry() == Country.USA, "postalCode", t -> t.getPostalCode()).
    end();
}
