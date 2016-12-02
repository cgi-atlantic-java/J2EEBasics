package me.bantling.j2ee.basics.validation;

import java.util.Objects;

/**
 * A single validation error
 */
public final class ValidationError {
  
  // ==== Member fields
  
  private final String id;
  private final String message;
  
  // ==== Cons
  
  public ValidationError(
    final String name,
    final String message
  ) {
    this.id = name;
    this.message = message;
  }
  
  // ==== Accessors
  
  public final String getId(
  ) {
    return id;
  }
  
  public final String getMessage(
  ) {
    return message;
  }
  
  // ==== Object
  
  @Override
  public int hashCode(
  ) {
    return (Objects.hashCode(id)) * 31 + Objects.hashCode(message);
  }
  
  @Override
  public boolean equals(
    final Object o
  ) {
    boolean equals = o == this;
    
    if ((! equals) && (o instanceof ValidationError)) {
      final ValidationError obj = (ValidationError)(o);
      
      equals = Objects.equals(id, obj.id) && Objects.equals(message, obj.message);
    }
    
    return equals;
  }
  
  @Override
  public String toString(
  ) {
    return new StringBuilder().
      append(ValidationError.class.getSimpleName()).
      append("[name=").append(id).
      append(", message=").append(message).
      toString();
  }
}
