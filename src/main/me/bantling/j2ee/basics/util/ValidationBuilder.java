package me.bantling.j2ee.basics.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Build a validator from zero or more validations. A single value can have multiple validations, and multiple values
 * can be validated. As a {@link Supplier}, the validator simply returns a List of String error messages. If the List
 * is empty, it means there were no errors.
 */
public class ValidationBuilder<T> {
  private static final Pattern VALID_CDN_POSTAL_CODE = Pattern.compile("[A-Z][0-9][A-Z] [0-9][A-Z][0-9]");
  private static final Pattern VALID_USA_ZIP_CODE = Pattern.compile("[0-9]{5}(-[0-9]{4})?");
  
  private final List<Function<T, Validation>> validations;
  
  {
    this.validations = new LinkedList<>();
  }
  
  public ValidationBuilder<T> nonNull(
    final String name,
    final Function<T, ?> value
  ) {
    validations.add(t -> value.apply(t) != null ? null : new Validation(name, "Cannot be null"));
    
    return this;
  }
  
  public ValidationBuilder<T> nullOrNonEmptyString(
    final String name,
    final Function<T, String> str
  ) {
    validations.add(t -> ! "".equals(str.apply(t)) ? null : new Validation(name, "Cannot be null or empty"));
    
    return this;
  }
  
  public ValidationBuilder<T> validCDNPostalCode(
    final Predicate<T> isCanada,
    final String name,
    final Function<T, String> str
  ) {
    validations.add(t -> {
      final String s = str.apply(t);
      
      return isCanada.test(t) && (s != null) && VALID_CDN_POSTAL_CODE.matcher(s).matches() ?
        null :
        new Validation(name, "Is not a valid Canadian postal code");
    });
    
    return this;
  }
  
  public ValidationBuilder<T> validCDNPostalCode(
    final String name,
    final Function<T, String> str
  ) {
    return validCDNPostalCode(t -> true, name, str);
  }
  
  public ValidationBuilder<T> validUSAZipCode(
    final Predicate<T> isUSA,
    final String name,
    final Function<T, String> str
  ) {
    validations.add(t -> {
      final String s = str.apply(t);
      
      return isUSA.test(t) && (s != null) && VALID_USA_ZIP_CODE.matcher(s).matches() ?
        null :
        new Validation(name, "Is not a valid US zip code");
    });
    
    return this;
  }
  
  public ValidationBuilder<T> validUSAZipCode(
    final String name,
    final Function<T, String> str
  ) {
    return validUSAZipCode(t -> true, name, str);
  }
  
  public Validator<T> end(
  ) {
    return t -> {
      final List<Validation> errors = new LinkedList<>();
      
      for (final Function<T, Validation> validation : validations) {
        final Validation error = validation.apply(t);
        
        if (error != null) {
          errors.add(error);
        }
      }
      
      return errors;
    };
  }
}
