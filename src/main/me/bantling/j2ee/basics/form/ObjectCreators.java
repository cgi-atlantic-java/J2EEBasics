package me.bantling.j2ee.basics.form;

import me.bantling.j2ee.basics.bean.Address;
import me.bantling.j2ee.basics.bean.Country;
import me.bantling.j2ee.basics.bean.Region;

/**
 * Implementations of {@link ObjectCreator}
 */
public interface ObjectCreators {
  public static final ObjectCreator<Address> ADDRESS_CREATOR =
    r -> {
      final RequestParametersHelper helper = new RequestParametersHelper(r);
      final int id = helper.getInt("id");
      final String line1 = helper.getString("line1");
      final String line2 = helper.getNullOrNonEmptyString("line2");
      final String city = helper.getString("city");
      final Country country = helper.getEnum("country", Country.class);
      final Region region = helper.getEnum("region", Region.class);
      final String postalCode = helper.getString("postalCode");
      
      return new Address(
        id,
        line1,
        line2,
        city,
        country,
        region,
        postalCode
      );
    };
}
