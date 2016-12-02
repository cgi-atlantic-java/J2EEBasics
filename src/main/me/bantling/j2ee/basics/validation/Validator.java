package me.bantling.j2ee.basics.validation;

import java.util.List;

@FunctionalInterface
public interface Validator<T> {
  public List<ValidationError> validate(
    final T object
  );
}
