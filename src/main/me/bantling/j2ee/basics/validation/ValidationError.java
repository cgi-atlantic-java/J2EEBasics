package me.bantling.j2ee.basics.validation;

import static java.util.Objects.requireNonNull;

/**
 * A single validation error
 */
public final class ValidationError {
  
  // ==== Member fields
  
  private final String id;
  private final String message;
  
  // ==== Cons
  
  public ValidationError(
    final String id,
    final String message
  ) {
    this.id = requireNonNull(id, "id");
    this.message = requireNonNull(message, "message");
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
    return id.hashCode() * 31 + message.hashCode();
  }
  
  @Override
  public boolean equals(
    final Object o
  ) {
    boolean equals = o == this;
    
    if ((! equals) && (o instanceof ValidationError)) {
      final ValidationError obj = (ValidationError)(o);
      
      equals = (id == obj.id) && message.equals(obj.message);
    }
    
    return equals;
  }
  
  @Override
  public String toString(
  ) {
    return new StringBuilder().
      append(ValidationError.class.getSimpleName()).
      append("[id=").append(id).
      append(", message=").append(message).
      toString();
  }
}
