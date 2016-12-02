package me.bantling.j2ee.basics.validation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

/**
 * Build a {@link Validator} from zero or more validations. A single value can have multiple validations, and multiple
 * values can be validated.
 */
public class ValidatorBuilder<T> {
  private static final Pattern VALID_CDN_POSTAL_CODE = Pattern.compile("[A-Z][0-9][A-Z] [0-9][A-Z][0-9]");
  private static final Pattern VALID_USA_ZIP_CODE = Pattern.compile("[0-9]{5}(-[0-9]{4})?");
  
  private final List<Function<T, ValidationError>> validations;
  
  {
    this.validations = new LinkedList<>();
  }
  
  public ValidatorBuilder<T> intGreaterThan(
    final String name,
    final ToIntFunction<T> value,
    final int min
  ) {
    validations.add(
      t -> value.applyAsInt(t) > min ?
        null :
        new ValidationError(name, "Must be >= ".concat(Integer.toString(min)))
    );
      
    return this;
  }
  
  public ValidatorBuilder<T> nonNull(
    final String name,
    final Function<T, ?> value
  ) {
    validations.add(t -> value.apply(t) != null ? null : new ValidationError(name, "Cannot be null"));
    
    return this;
  }
  
  public ValidatorBuilder<T> nonEmptyString(
    final String name,
    final Function<T, String> value
  ) {
    validations.add(t -> {
      final String str = value.apply(t);
      
      return (str != null) && (str.length() > 0) ? null : new ValidationError(name, "Cannot be empty");
    });
    
    return this;
  }
  
  public ValidatorBuilder<T> nullOrNonEmptyString(
    final String name,
    final Function<T, String> str
  ) {
    validations.add(t -> ! "".equals(str.apply(t)) ? null : new ValidationError(name, "Cannot be null or empty"));
    
    return this;
  }
  
  public ValidatorBuilder<T> validCDNPostalCode(
    final Predicate<T> isCanada,
    final String name,
    final Function<T, String> str
  ) {
    validations.add(t -> {
      final String s = str.apply(t);
      
      return
        (! isCanada.test(t)) ||
        (
          (s != null) &&
          VALID_CDN_POSTAL_CODE.matcher(s).matches()
        ) ?
        null :
        new ValidationError(name, "Invalid Canadian postal code");
    });
    
    return this;
  }
  
  public ValidatorBuilder<T> validCDNPostalCode(
    final String name,
    final Function<T, String> str
  ) {
    return validCDNPostalCode(t -> true, name, str);
  }
  
  public ValidatorBuilder<T> validUSAZipCode(
    final Predicate<T> isUSA,
    final String name,
    final Function<T, String> str
  ) {
    validations.add(t -> {
      final String s = str.apply(t);
      
      return
        (! isUSA.test(t)) ||
        (
          (s != null) &&
          VALID_USA_ZIP_CODE.matcher(s).matches()
        ) ?
        null :
        new ValidationError(name, "Invalid US zip code");
    });
    
    return this;
  }
  
  public ValidatorBuilder<T> validUSAZipCode(
    final String name,
    final Function<T, String> str
  ) {
    return validUSAZipCode(t -> true, name, str);
  }
  
  public Validator<T> end(
  ) {
    return t -> {
      final List<ValidationError> errors = new LinkedList<>();
      
      for (final Function<T, ValidationError> validation : validations) {
        final ValidationError error = validation.apply(t);
        
        if (error != null) {
          errors.add(error);
        }
      }
      
      return errors;
    };
  }
}
